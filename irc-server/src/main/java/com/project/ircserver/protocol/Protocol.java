package com.project.ircserver.protocol;

import static com.project.ircserver.utils.ProtocolUtils.EMPTY;
import static com.project.ircserver.utils.ProtocolUtils.extract;
import static com.project.ircserver.utils.ProtocolUtils.proccesInput;

import java.util.HashMap;
import java.util.Map;

import com.project.ircclient.Client;
import com.project.ircserver.Command;
import com.project.ircserver.channel.Channel;

/**
 * Protocol is responsible of handling messages.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Protocol {

    /** Client input message. */
    private static final int MESSAGE = 1;

    /** Holds the IRC command {@link Command}. */
    private static final int PREFIX = 0;

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
     * Holds the clients in the server making them unique.
     * 
     * @param String
     *            - is the nickname.
     * 
     * @param Client
     *            - hold the client Entity.
     */
    private final Map<String, Client> clients = new HashMap<String, Client>(0);

    /**
     * Holds the channels in the server making them unique.
     * 
     * @param String
     *            is the channel name
     * @param Channel
     *            hold the channel Entity containing a list of clients.
     */
    private final Map<String, Channel> channels = new HashMap<String, Channel>(
	    0);

    public Protocol() {
    }

    /** IRC Protocol said that proper nickname is lenght of 9. */
    public boolean validateNickname(final String nickname) {
	return nickname.matches(NICKNAME_REGEX);
    }

    public boolean validateChannelName(final String channelName) {
	return channelName.matches(CHANNELNAME_REGEX);
    }

    public Client findClientByNickname(final String nickname) {
	return clients.get(nickname);
    }

    public Channel findChannel(final String channelName) {
	return channels.get(channelName);
    }

    /** Puts a new client into the clients Map. */
    public void registerClient(final String nickname, final Client client) {
	clients.put(nickname, client);
    }

    public Channel createChannel(final Channel channel) {
	channels.put(channel.getChannelName(), channel);
	return channel;
    }

    /** Updates the clients Map. */
    public void updateClients(final HashMap<String, Client> newClients) {
	newClients.putAll(newClients);
    }

    /** Validates if the nickname passed is already registered */
    public boolean isNickNameRegistered(final String nickname) {
	return clients.containsKey(nickname);
    }

    public boolean isChannelRegistered(final String channelname) {
	return channels.containsKey(channelname);
    }

    public String handleInput(final Channel channel, final Client client,
	    final String input) {
	// Empty messages are ignored implitly.
	if (!EMPTY.equals(input)) {
	    final String[] extracted = extract(input);
	    final String prefix = extracted[PREFIX];
	    final String message = extracted[MESSAGE];

	    return proccesInput(channel, client, prefix, message);
	}

	return EMPTY;
    }

}
