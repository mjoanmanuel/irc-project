package com.project.ircserver.utils;

import static com.project.ircserver.Command.JOIN;
import static com.project.ircserver.Command.KICK;
import static com.project.ircserver.Command.LEAVE;
import static com.project.ircserver.Command.MODE;
import static com.project.ircserver.Command.MSG;
import static com.project.ircserver.Command.TOPIC;
import static com.project.ircserver.protocol.Protocol.MESSAGE;
import static com.project.ircserver.protocol.Protocol.PREFIX;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map.Entry;

import com.project.ircclient.Client;
import com.project.ircserver.Command;
import com.project.ircserver.Server;
import com.project.ircserver.channel.Channel;

/**
 * ProtocolUtils is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ProtocolUtils {

    private static final int CHANNEL_MODE = 1;

    private static final int HAS_MESSAGE = 3;

    public static final int CHANNEL_NAME = 1;

    public static final int NICKNAME = 1;

    private static final String COMMAND_PREFIX = "/";

    private static final boolean AUTO_FLUSH = true;

    private static final String COMMA = ",";

    /** Represents an empty string. */
    public static final String EMPTY = "";

    /** Goes after prefix. */
    public static final String SPACE = " ";

    /** Send a private message to a specific client. */
    public static void sendPrivateMessage(final Channel channel,
	    final Client from, final String toNickname, final String message) {
	if (!isClientConnected(channel, toNickname)) {
	    // TODO an error msg that client is not avaible.
	    return;
	}

	final String msg = format(" -> %s %s", from.getNickname(), message);
	sendMessage(channel.findClient(toNickname), msg);
    }

    /** Send a message to all clients in the channel. */
    public static void sendGlobalMessage(final Channel channel,
	    final String message) {
	final Iterator<Entry<String, Client>> iterator = channel.getClients()
		.entrySet().iterator();

	while (iterator.hasNext()) {
	    final Entry<String, Client> current = iterator.next();
	    sendMessage(current.getValue(), message);
	}
    }

    // TODO
    public static void joinChannel() {
    }

    // TODO
    public static void invite() {
    }

    public static void kick(final Channel channel, final Client client,
	    final String nickname, final String message) {
	if (!client.isOperator() && !isClientConnected(channel, nickname)) {
	    return;
	}
	final String msg = format(" *** %s has been kicked off %s by %s",
		nickname, channel.getChannelName(), client.getNickname());
	closeConnection(channel, nickname);
	sendGlobalMessage(channel, msg);
    }

    public static void leaveChannel(final Channel channel, final Client client) {
	closeConnection(channel, client.getNickname());
    }

    // TODO
    public static void changeChannelMode() {
    }

    public static void changeChannelTopic(final Channel channel,
	    final Client client, final String option, final String newTopic) {
	channel.setChannelTopic(newTopic);
	final String msg = format(" *** %s has changed the topic on %s to %s ",
		client.getNickname(), channel.getChannelName(), newTopic);
	sendGlobalMessage(channel, msg);
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
	// nickname or channelname
	String option = EMPTY;

	final String[] decode = message.split(SPACE);

	prefix = decode[PREFIX];

	if (MSG.toString().equals(prefix)) {
	    option = decode[NICKNAME];
	    msg = decodeMessage(MESSAGE, decode);

	} else if (JOIN.toString().equals(prefix)) {
	    option = decode[CHANNEL_NAME];

	} else if (LEAVE.toString().equals(prefix)) {
	    option = decode[CHANNEL_NAME];

	} else if (TOPIC.toString().equals(prefix)) {
	    option = decode[CHANNEL_NAME];
	    // this is the topic channel
	    msg = decodeMessage(MESSAGE, decode);

	} else if (KICK.toString().equals(prefix)) {
	    option = decode[NICKNAME];
	    final boolean hasReason = decode.length > HAS_MESSAGE;
	    msg = hasReason ? decodeMessage(MESSAGE, decode) : EMPTY;

	} else if (MODE.toString().equals(prefix)) {
	    option = decode[CHANNEL_MODE];
	}

	return new String[] { prefix, option, msg };
    }

    private static String decodeMessage(int position, final String[] decode) {
	final StringBuilder message = new StringBuilder();
	for (; position < decode.length; position++) {
	    message.append(decode[position] + SPACE);
	}

	return message.toString();
    }

    /** Handle the user input. */
    public static String proccesInput(final Channel channel,
	    final Client client, final Command prefix, final String option,
	    final String message) {
	final String nickname = client.getNickname();

	switch (prefix) {
	case ME:
	    final String compoundMessage = String.format(" %s -> %s %s ",
		    nickname, nickname, message);
	    sendGlobalMessage(channel, compoundMessage);
	    break;
	case JOIN:
	    joinChannel();
	    break;
	case INVITE:
	    invite();
	case KICK:
	    kick(channel, client, option, message);
	    break;
	case LEAVE:
	    leaveChannel(channel, client);
	case MODE:
	    changeChannelMode();
	    break;
	case MSG:
	    sendPrivateMessage(channel, client, option, message);
	case TOPIC:
	    changeChannelTopic(channel, client, option, message);
	    break;
	default:
	    final String msg = format(" %s -> %s ", nickname, message);
	    sendGlobalMessage(channel, msg);
	    break;
	}

	return message;
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

    public static boolean isValidCommand(final String prefix) {
	for (final Command elem : Command.values()) {
	    if (elem.toString().equals(prefix))
		return true;
	}

	return false;
    }

    private static boolean isClientConnected(final Channel channel,
	    final String nickname) {
	return channel.getClients().get(nickname) != null;
    }

    private static void closeConnection(final Channel channel,
	    final String nickname) {
	try {
	    if (!channel.hasClient(nickname)) {
		return;
	    }
	    channel.findClient(nickname).getSocket().close();
	    channel.removeClient(nickname);
	} catch (final IOException ex) {
	    ex.printStackTrace();
	}
    }

}
