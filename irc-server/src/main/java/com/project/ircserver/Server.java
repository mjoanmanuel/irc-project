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
    private Channel channel;

    public Server(final int port) throws IOException {
	super(port);
	this.port = port;
    }

    /** Connect and register this nickname to the server. */
    public boolean connect(final String channelName, final String nickname) {
	while (listening) {
	    if (protocol.isChannelRegistered(channelName)) {
		// If the channel doesn't exist, is created implicitly.
		protocol.registerChannel(channelName, null);
	    }
	    if (protocol.isNickNameRegistered(nickname)) {
		connectNewClient(nickname);
		return true;
	    }

	    // TODO : reconnect with other nickname.
	    return false;
	}
    }

    public Client findClientByNickname(final String nickname) {
	return protocol.findClientByNickname(nickname);
    }

    private void connectNewClient(final String nickname) {
	Client client;
	try {
	    // This is part of RFC 1.2
	    client = (Client) this.accept(); // we get the socket.
	    client.setNickname(nickname); // set the nickname.
	    client.setServer(this); // set the server where the client is
				    // connected.
	    protocol.registerClient(nickname, client);

	    new Worker(client, protocol).start();
	} catch (IOException e) {
	    out.println(" Couldn't connect to port " + port + " try other one.");
	}
    }
}
