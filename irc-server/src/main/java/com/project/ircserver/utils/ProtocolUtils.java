package com.project.ircserver.utils;

import static com.project.ircserver.Command.INVITE;
import static com.project.ircserver.Command.JOIN;
import static com.project.ircserver.Command.KICK;
import static com.project.ircserver.Command.LEAVE;
import static com.project.ircserver.Command.ME;
import static com.project.ircserver.Command.MODE;
import static com.project.ircserver.Command.MSG;
import static com.project.ircserver.Command.TOPIC;
import static com.project.ircserver.protocol.Protocol.MESSAGE;
import static com.project.ircserver.protocol.Protocol.PREFIX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map.Entry;

import com.project.ircclient.Client;
import com.project.ircserver.Server;
import com.project.ircserver.channel.Channel;

/**
 * ProtocolUtils is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ProtocolUtils {

    private static final int COMMAND = 3;

    private static final String COMMAND_PREFIX = "/";

    private static final boolean AUTO_FLUSH = true;

    private static final String COMMA = ",";

    public static final int CHANNEL_NAME = 0;

    public static final int NICKNAME = 0;

    /** Represents an empty string. */
    public static final String EMPTY = "";

    /** Goes after prefix. */
    public static final String SPACE = " ";

    // /MSG [validNickname] [message]
    // TODO
    /** Send a private message to a specific client. */
    public static void sendPrivateMessage(final Channel channel,
	    final Client from, final String message) {
	final Client to = channel.getClients().get("[buddynick]");
	sendMessage(to, message);
    }

    // TODO CHECK IOEXCEPTION.
    /** Send a message to all clients in the channel. */
    public static String sendGlobalMessage(final Channel channel,
	    final Client client, final String message) {
	final Iterator<Entry<String, Client>> iterator = channel.getClients()
		.entrySet().iterator();

	while (iterator.hasNext()) {
	    final Entry<String, Client> current = iterator.next();
	    sendMessage(current.getValue(), message);
	}

	return message;
    }

    // TODO
    public static void joinChannel() {
    }

    // TODO
    public static void inviteClient() {
    }

    // TODO
    public static void kickClient() {
    }

    // TODO
    public static void leaveChannel() {
    }

    // TODO
    public static void changeChannelMode() {
    }

    // TODO
    public static void changeChannelTopic() {
    }

    private static void sendMessage(final Client client, final String message) {
	PrintWriter writer = null;
	try {
	    writer = new PrintWriter(client.getSocket().getOutputStream(),
		    AUTO_FLUSH);
	    writer.println(message);
	} catch (final IOException ex) {
	    ex.printStackTrace();
	}
    }

    /** Extract the prefix from the input. */
    public static String[] extract(final String message) {
	if (!message.startsWith(COMMAND_PREFIX)) {
	    return new String[] { EMPTY, EMPTY, message };
	}

	String prefix = EMPTY;
	String msg = EMPTY;
	// could be nickname or channelname
	String option = EMPTY;

	final String[] decode = message.split(SPACE);

	prefix = decode[PREFIX];

	if (MSG.equals(prefix)) {
	    option = decode[NICKNAME];
	    // TODO add message
	    msg = decode[MESSAGE];

	} else if (JOIN.equals(prefix)) {
	    option = decode[CHANNEL_NAME];
	    msg = EMPTY;
	} else if (LEAVE.equals(prefix)) {
	    option = decode[CHANNEL_NAME];
	    msg = EMPTY;
	} else if (TOPIC.equals(prefix)) {
	    option = decode[CHANNEL_NAME];
	    // TODO add message
	    // this is the topic channel
	    msg = decode[MESSAGE];
	}

	return new String[] { prefix, option, msg };
    }

    /** Handle the user input. */
    public static String proccesInput(final Channel channel,
	    final Client client, final String prefix, final String message) {
	final StringBuilder result = new StringBuilder();

	if (ME.equals(prefix)) {
	    sendGlobalMessage(channel, client, message);
	} else if (JOIN.equals(prefix)) {
	    // joinChannel();
	} else if (INVITE.equals(prefix)) {
	    // inviteClient();
	} else if (KICK.equals(prefix)) {
	    // kickClient();
	} else if (LEAVE.equals(prefix)) {
	    // leaveChannel();
	} else if (MODE.equals(prefix)) {
	    // changeChannelMode();
	} else if (MSG.equals(prefix)) {
	    sendPrivateMessage(channel, client, message);
	    // message,
	    // "from[yournick]");
	} else if (TOPIC.equals(prefix)) {
	    // ProtocolUtils.changeChannelTopic();
	} else {
	    result.append(sendGlobalMessage(channel, client, message));
	}

	return result.toString();
    }

    /** Create a client instance with proper configuration. */
    public static Client createClient(final Server server,
	    final Channel channel, final String nickname, final Socket socket) {
	final Client client = new Client();
	client.setNickname(nickname);
	client.setChannelName(channel.getChannelName());
	client.setServer(server);
	client.setSocket(socket);
	return client;
    }

    public static String[] readCfgMessage(final Socket socket) {
	BufferedReader read = null;
	try {
	    read = new BufferedReader(new InputStreamReader(
		    socket.getInputStream()));
	    return read.readLine().split(COMMA);
	} catch (final IOException e) {
	    e.printStackTrace();
	}

	return null;
    }

}
