package com.irc.server.protocol;

import static com.irc.server.command.Command.NONCOMMAND;
import static com.irc.server.command.Command.valueOf;
import static com.irc.server.utils.ProtocolUtils.EMPTY;
import static com.irc.server.utils.ProtocolUtils.extract;
import static com.irc.server.utils.ProtocolUtils.isValidCommand;
import static com.irc.server.utils.ProtocolUtils.proccesInput;

import java.util.Map;

import com.irc.client.Client;
import com.irc.server.channel.Channel;
import com.irc.server.command.Command;

/**
 * Protocol is responsible of handling messages.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Protocol {

    /** Client input message. */
    public static final int MESSAGE = 2;

    /** Migth be nickname or channel. */
    public static final int OPTION = 1;

    /** Holds the IRC command {@link Command}. */
    public static final int PREFIX = 0;

    /**
     * Represents an regular expression for validating nicknames from a-z | A-Z
     * containing 0-9 and must start with a character as minimun and must has 9
     * as maximum.
     */
    private static final String NICKNAME_REGEX = "[a-z0-9A-Z0-9]{1,9}";

    // TODO
    /** Must match the patter #|& channelName1298734 without space */
    private static final String CHANNELNAME_REGEX = "((#|&{1})[a-z0-9A-Z0-9]{1,200})";

    /**
     * Holds the channels in the server making them unique.
     * 
     * @param String
     *            is the channel name
     * @param Channel
     *            hold the channel Entity containing a list of clients.
     */
    private final Map<String, Channel> channels;

    public Protocol(final Map<String, Channel> channels) {
	this.channels = channels;
    }

    /** IRC Protocol said that proper nickname is lenght of 9. */
    public boolean validateNickname(final String nickname) {
	return nickname.matches(NICKNAME_REGEX);
    }

    public boolean validateChannelName(final String channelName) {
	return channelName.matches(CHANNELNAME_REGEX);
    }

    public Client findClient(final String channelname, final String nickname) {
	return findChannel(channelname).getClients().get(nickname);
    }

    public Channel findChannel(final String channelName) {
	return channels.get(channelName);
    }

    /** Puts a new client into the clients Map. */
    public void registerClient(final String channelname, final String nickname,
	    final Client client) {
	final Channel channel = channels.get(channelname);
	channel.addClient(client);
    }

    public Channel createChannel(final String channelname) {
	final Channel channel = new Channel(channelname);
	channels.put(channelname, channel);
	return channel;
    }

    /** Validates if the nickname passed is already registered */
    public boolean isNickNameEnabled(final String channelname,
	    final String nickname) {
	return findChannel(channelname).getClients().containsKey(nickname);
    }

    public boolean isChannelRegistered(final String channelname) {
	return channels.containsKey(channelname);
    }

    public String handleInput(final Channel channel, final Client client,
	    final String input) {
	// Empty messages are ignored implitly.
	if (!EMPTY.equals(input)) {
	    final String[] extracted = extract(input);
	    final String tmp = extracted[PREFIX];
	    final int length = tmp.length();
	    final Command prefix = isValidCommand(tmp) ? valueOf(tmp.substring(
		    1, length)) : NONCOMMAND;
	    final String message = extracted[MESSAGE];
	    final String option = extracted[OPTION];

	    return proccesInput(this, channel, client, prefix, option, message);
	}

	return EMPTY;
    }
}
