package com.irc.server.utils;

import static com.irc.server.Command.INVITE;
import static com.irc.server.Command.JOIN;
import static com.irc.server.Command.KICK;
import static com.irc.server.Command.LEAVE;
import static com.irc.server.Command.ME;
import static com.irc.server.Command.MODE;
import static com.irc.server.Command.MSG;
import static com.irc.server.Command.NICK;
import static com.irc.server.Command.TOPIC;
import static com.irc.server.protocol.Protocol.MESSAGE;
import static com.irc.server.protocol.Protocol.PREFIX;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map.Entry;

import com.irc.client.Client;
import com.irc.server.Command;
import com.irc.server.Server;
import com.irc.server.channel.Channel;
import com.irc.server.protocol.Protocol;

/**
 * ProtocolUtils is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ProtocolUtils {

    /** Represents an empty string. */
    public static final String EMPTY = "";
    /** Goes after prefix. */
    public static final String SPACE = " ";
    public static final int CHANNEL_NAME = 1;
    public static final int NICKNAME = 1;

    private static final boolean IS_NOT_JOIN = false;
    private static final String GLOBAL_MSG_FORMAT = " %s -> %s ";
    private static final String ME_MSG_FORMAT = " %s -> * %s %s ";
    private static final String I18N_FILE = ProtocolUtils.class
	    .getCanonicalName();
    private static final I18nUtils properties = new I18nUtils(I18N_FILE);
    private static final int CHANNEL_MODE = 1;
    private static final int HAS_MESSAGE = 3;
    private static final String COMMAND_PREFIX = "/";
    private static final boolean AUTO_FLUSH = true;
    private static final String COMMA = ",";

    /** Send a private message to a specific client. */
    public static void sendPrivateMessage(final Channel channel,
	    final Client from, final String toNickname, final String message,
	    final boolean isJoin) {
	if (!isClientConnected(channel, toNickname)) {
	    sendMessage(
		    from,
		    format(properties.getString("nicknameNotFound"), toNickname));
	    return;
	}

	final String pm = properties.getString("privateMessage");
	final String msgFrom = format(pm, toNickname, message);
	final String msgTo = format(pm, from.getNickname(), message);
	if (!isJoin) {
	    sendMessage(from, msgFrom);
	    sendMessage(channel.findClient(toNickname), msgTo);
	} else {
	    sendMessage(from, msgFrom);
	}
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

    // TODO : use client.getJoinChannelCount() MAX = 10;
    /** Join the client to a specific channel. */
    public static void joinChannel(final Protocol protocol,
	    final String channelname, final Client client) {
	if (!protocol.isChannelRegistered(channelname)) {
	    if (!protocol.validateChannelName(channelname)) {
		sendMessage(
			client,
			format(properties.getString("channelNotFound"),
				channelname));
		return;
	    }
	    protocol.createChannel(channelname);
	    final String operator = "@".concat(client.getNickname());
	    protocol.registerClient(channelname, operator, client);
	} else {
	    protocol.registerClient(channelname, client.getNickname(), client);
	}
    }

    public static void invite(final Channel channel, final Client client,
	    final String nickname, final String channelname) {
	if (!isClientConnected(channel, nickname)) {
	    sendMessage(client,
		    format(properties.getString("nicknameNotFound"), nickname));
	    return;
	}

	final String msg = String.format(
		properties.getString("inviteToChannel"), client.getNickname(),
		channelname);
	sendMessage(client, msg);
    }

    public static void kick(final Channel channel, final Client client,
	    final String nickname, final String message) {
	if (!client.isOperator() && !isClientConnected(channel, nickname)) {
	    return;
	}
	final String msg = format(properties.getString("kickFromChannel"),
		nickname, channel.getChannelName(), client.getNickname());
	closeConnection(channel, nickname);
	sendGlobalMessage(channel, msg);
    }

    public static void leaveChannel(final Channel channel, final Client client) {
	final String msg = format(properties.getString("leaveChannel"),
		client.getNickname(), channel.getChannelName());
	closeConnection(channel, client.getNickname());
	sendGlobalMessage(channel, msg);
    }

    // TODO handle MODE propertly.
    public static void changeChannelMode(final Channel channel,
	    final Client client, final String newChannelMode) {
	if (!client.isOperator()) {
	    sendMessage(client,
		    format(properties.getString("changeChannelModeErr")));
	    return;
	}
	final String msg = format(properties.getString("changeChannelMode"),
		newChannelMode, channel.getChannelName(), client.getNickname());
	sendGlobalMessage(channel, msg);

    }

    public static void retrieveChannelMode(final Channel channel,
	    final Client client) {
	final String msg = String.format(properties.getString("channelMode"),
		channel.getChannelName(), channel.getChannelMode());
	sendMessage(client, msg);
    }

    public static void changeChannelTopic(final Channel channel,
	    final Client client, final String option, final String newTopic) {
	if (!client.isOperator()) {
	    sendMessage(client,
		    format(properties.getString("changeChannelTopicErr")));
	    return;
	}

	channel.setChannelTopic(newTopic);
	final String msg = format(properties.getString("changeChannelTopic"),
		client.getNickname(), channel.getChannelName(), newTopic);
	sendGlobalMessage(channel, msg);
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
	    final boolean hasChannelname = decode.length > 1;
	    if (hasChannelname) {
		option = decode[CHANNEL_NAME];
	    }

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
	    option = decodeMessage(MESSAGE, decode);

	} else if (INVITE.toString().equals(prefix)) {
	    option = decode[NICKNAME];
	    msg = decodeMessage(CHANNEL_NAME, decode);
	} else if (ME.toString().equals(prefix)) {
	    msg = decodeMessage(MESSAGE - 1, decode);
	} else if (NICK.toString().equals(prefix)) {
	    option = decode[NICKNAME];
	}

	return new String[] { prefix, option, msg };
    }

    /** Handle the user input. */
    public static String proccesInput(final Protocol protocol,
	    final Channel channel, final Client client, final Command prefix,
	    final String option, final String message) {
	final String nickname = client.getNickname();

	switch (prefix) {
	case ME:
	    final String compoundMessage = format(ME_MSG_FORMAT, nickname,
		    nickname, message);
	    sendGlobalMessage(channel, compoundMessage);
	    break;
	case JOIN:
	    joinChannel(protocol, option, client);
	    break;
	case INVITE:
	    invite(channel, client, option, message);
	    break;
	case KICK:
	    kick(channel, client, option, message);
	    break;
	case LEAVE:
	    leaveChannel(channel, client);
	    break;
	case MODE:
	    if (!message.isEmpty()) {
		changeChannelMode(channel, client, message);
	    }

	    retrieveChannelMode(channel, client);
	    break;
	case MSG:
	    sendPrivateMessage(channel, client, option, message, IS_NOT_JOIN);
	    break;
	case TOPIC:
	    changeChannelTopic(channel, client, option, message);
	    break;

	case NICK:
	    changeNickname(protocol, channel, client, option);
	    break;
	default:
	    final String msg = format(GLOBAL_MSG_FORMAT, nickname, message);
	    sendGlobalMessage(channel, msg);
	    break;
	}

	return message;
    }

    /** Change the user nickname. */
    public static void changeNickname(final Protocol protocol,
	    final Channel channel, final Client client, final String newNickname) {
	if (protocol.isNickNameEnabled(channel.getChannelName(), newNickname)) {
	    sendMessage(
		    client,
		    format(properties.getString("changeNicknameErr"),
			    newNickname));
	    return;
	} else if (protocol.validateNickname(newNickname)) {
	    sendMessage(
		    client,
		    format(properties.getString("changeNicknameValidErr"),
			    newNickname));
	    return;
	}
	final String oldNickname = client.getNickname();
	final String msg = format(properties.getString("changeNickname"),
		oldNickname, newNickname);
	// update client nickname in channel.
	channel.findClient(oldNickname).setNickname(newNickname);
	sendGlobalMessage(channel, msg);
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

    private static String decodeMessage(int position, final String[] decode) {
	final StringBuilder message = new StringBuilder();
	for (; position < decode.length; position++) {
	    message.append(decode[position] + SPACE);
	}

	return message.toString();
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

}
