/**
 * 
 */
package com.project.ircserver;

import static com.project.ircserver.utils.ProtocolUtils.createClient;
import static com.project.ircserver.utils.ProtocolUtils.readCfgMessage;
import static java.lang.System.out;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.project.ircclient.Client;
import com.project.ircserver.channel.Channel;
import com.project.ircserver.protocol.Protocol;

/**
 * Server is an local IRC server.
 * 
 * @author jmendoza Dec 1, 2011
 */
public class Server extends ServerSocket {

    /** Default IRC port defined by RFC document. */
    public static final int DEFAULT_IRC_PORT = 6667;

    private static final int NICKNAME = 0;
    private static final int CHANNEL_NAME = 1;

    private final boolean ALWAYS_LISTENING = true;
    // This property holds a channel with registered clients.
    private Map<String, Channel> channels = new HashMap<String, Channel>();
    /** All servers must have the following information about all clients. */
    private final Protocol protocol = new Protocol(channels);

    private int port;

    public Server(final int port) throws IOException {
	super(port);
	this.port = port;
    }

    /** Start the server. */
    public void start() {
	while (ALWAYS_LISTENING) {
	    listening();
	}
    }

    private void listening() {
	Socket socket;
	try {
	    // This is part of RFC 1.2
	    socket = this.accept();
	    // set the server where the client is connected, just RFC
	    // specification.
	    if (!connectClient(socket)) {
		// TODO send an error msg to the socket why couldn't connect.
	    }

	} catch (final IOException e) {
	    out.println(" Couldn't connect to port " + port
		    + " try another one.");
	}
    }

    public Map<String, Channel> getChannels() {
	return channels;
    }

    private boolean connectClient(final Socket socket) {
	final String[] cfg = readCfgMessage(socket);
	final String channelname = cfg[CHANNEL_NAME];
	String nickname = cfg[NICKNAME];
	Channel channel = protocol.findChannel(channelname);

	if (!protocol.isChannelRegistered(channelname)) {
	    // If the channel doesn't exist, is created implicitly with the
	    // client as operator by default.
	    if (!protocol.validateChannelName(channelname)) {
		return false;
	    }
	    channel = protocol.createChannel(new Channel(channelname));
	    channels.put(channelname, channel);

	    if (!protocol.validateNickname(nickname)) {
		channels.remove(channelname);
		return false;
	    }

	    nickname = "@".concat(nickname);
	}

	final Client client = createClient(this, channel, nickname, socket);

	protocol.registerClient(channelname, nickname, client);
	channel.addClient(client);
	new Worker(client, channel, protocol).start();

	return true;
    }

}
