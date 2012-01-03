/**
 * 
 */
package com.project.ircserver;

import static java.lang.System.out;

import java.io.IOException;
import java.net.ServerSocket;

import com.project.ircclient.Client;
import com.project.ircserver.channel.Channel;
import com.project.ircserver.protocol.Protocol;

/**
 * Server is an local IRC server.
 * 
 * @author jmendoza Dec 1, 2011
 */
public class Server extends ServerSocket {

    /** Max client allowed per channel. */
    public static final int MAX_CLIENTS_ALLOWED = 30;
    private final boolean listening = true;
    /** All servers must have the following information about all clients. */
    private final Protocol protocol = new Protocol();
    private int port;

    public Server(final int port) throws IOException {
	super(port);
	this.port = port;
    }

    /** Connect and register this nickname to the server. */
    public boolean connect(final String channelName, final String nickname) {
	while (listening) {
	    if (!protocol.isChannelRegistered(channelName)) {
		// If the channel doesn't exist, is created implicitly with the
		// client as operator by default.
		connectNewClient(channelName, "@".concat(nickname), true);
	    }
	    if (protocol.isNickNameRegistered(nickname)) {
		connectNewClient(channelName, nickname, false);
		return true;
	    }

	    // TODO : reconnect with other nickname.
	    return false;
	}
    }

    public Client findClientByNickname(final String nickname) {
	return protocol.findClientByNickname(nickname);
    }

    private void connectNewClient(final String channelName,
	    final String nickname, final boolean isNewChannel) {
	Client client;
	try {
	    // This is part of RFC 1.2
	    client = (Client) this.accept();
	    // set the nickname.
	    client.setNickname(nickname);
	    // set the server where the client is connected, just RFC
	    // specification.
	    client.setServer(this);

	    Channel channel = null;
	    if (isNewChannel) {
		channel = createChannel(channelName, nickname, client);
	    } else {
		channel = addToChannel(channelName, nickname, client);
	    }

	    protocol.registerClient(nickname, client);
	    new Worker(client, channel, protocol).start();

	} catch (IOException e) {
	    out.println(" Couldn't connect to port " + port
		    + " try another one.");
	}
    }

    private Channel addToChannel(final String channelName,
	    final String nickname, Client client) {
	final Channel channel = protocol.findChannelByName(channelName);
	channel.addClient(nickname, client);
	return channel;
    }

    private Channel createChannel(final String channelName,
	    final String nickname, Client client) {
	final Channel channel = protocol.registerChannel(channelName,
		new Channel(channelName));
	channel.addClient(nickname, client);
	return channel;
    }
}
