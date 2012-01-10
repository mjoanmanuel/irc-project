package com.irc.server.channel;

import static com.irc.server.ChannelMode.PUBLIC;
import static com.irc.server.utils.ProtocolUtils.sendGlobalMessage;
import static com.irc.server.utils.ProtocolUtils.sendPrivateMessage;
import static java.lang.String.format;

import java.io.Serializable;
import java.util.HashMap;

import com.irc.client.Client;
import com.irc.server.ChannelMode;
import com.irc.server.utils.I18nUtils;
import com.irc.server.utils.ProtocolUtils;

/**
 * Channel is responsible of an IRC channel.
 * 
 * A channel is a named group of one or more clients which will all receive
 * messages addressed to that channel. The channel is created implicitly when
 * the first client joins it, and the channel ceases to exist when the last
 * client leaves it. While channel exists, any client can reference the channel
 * using the name of the channel.
 * 
 * Channels names are strings (beginning with a '&' or '#' character) of length
 * up to 200 characters. Apart from the the requirement that the first character
 * being either '&' or '#'; the only restriction on a channel name is that it
 * may not contain any spaces (' '), a control G (^G or ASCII 7), or a comma
 * (',' which is used as a list item separator by the protocol).
 * 
 * There are two types of channels allowed by this protocol. One is a
 * distributed channel which is known to all the servers that are connected to
 * the network. These channels are marked by the first character being a only
 * clients on the server where it exists may join it. These are distinguished by
 * a leading '&' character. On top of these two types, there are the various
 * channel modes available to alter the characteristics of individual channels.
 * See section 4.2.3 (MODE command) for more details on this.
 * 
 * To create a new channel or become part of an existing channel, a user is
 * required to JOIN the channel. If the channel doesn't exist prior to joining,
 * the channel is created and the creating user becomes a channel operator. If
 * the channel already exists, whether or not your request to JOIN that channel
 * is honoured depends on the current modes of the channel. For example, if the
 * channel is invite-only, (+i), then you may only join if invited. As part of
 * the protocol, a user may be a part of several channels at once, but a limit
 * of ten (10) channels is recommended as being ample for both experienced and
 * novice users. See section 8.13 for more information on this.
 * 
 * If the IRC network becomes disjoint because of a split between two servers,
 * the channel on each side is only composed of those clients which are
 * connected to servers on the respective sides of the split, possibly ceasing
 * to exist on one side of the split. When the split is healed, the connecting
 * servers announce to each other who they think is in each channel and the mode
 * of that channel. If the channel exists on both sides, the JOINs and MODEs are
 * interpreted in an inclusive manner so that both sides of the new connection
 * will agree about which clients are in the channel and what modes the channel
 * has.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Channel implements Serializable {

    private static final boolean IS_JOIN = true;
    private static final String I18N_FILE = ProtocolUtils.class
	    .getCanonicalName();
    private static final I18nUtils properties = new I18nUtils(I18N_FILE);

    private static final long serialVersionUID = 1L;

    private String channelName;
    private String channelTopic;
    private ChannelMode channelMode;

    private HashMap<String, Client> clients = new HashMap<String, Client>();

    public Channel(final String channelName) {
	this(channelName, properties.getString("welcomeMsg"), PUBLIC);
    }

    public Channel(final String channelName, final String channelTopic) {
	this(channelName, channelTopic, PUBLIC);
    }

    public Channel(final String channelName, final String channelTopic,
	    final ChannelMode mode) {
	this.channelName = channelName;
	this.channelTopic = channelTopic;
	this.channelMode = mode;
    }

    /** Add client to the channel and send a JOIN MSG to all other clients. */
    public void addClient(final Client client) {
	clients.put(client.getNickname(), client);
	sendJoinMsg(client);
    }

    /** Verify if the client is in the channel. */
    public boolean hasClient(final String nickname) {
	return clients.get(nickname) != null;
    }

    public Client findClient(final String nickname) {
	return clients.get(nickname);
    }

    /** Remove the client with [nickname] from the channel. */
    public void removeClient(final String nickname) {
	clients.remove(nickname);
    }

    public Channel setChannelName(final String channelName) {
	this.channelName = channelName;
	return this;
    }

    public String getChannelName() {
	return channelName;
    }

    public void sendJoinMsg(final Client client) {
	sendGlobalMessage(
		this,
		format(properties.getString("joinMsg"), client.getNickname(),
			channelName));
	sendPrivateMessage(
		this,
		client,
		client.getNickname(),
		format(properties.getString("topicMsg"), channelName,
			channelTopic), IS_JOIN);
	sendGlobalMessage(this, "[onlinelist]");
    }

    public Channel setChannelTopic(String channelTopic) {
	this.channelTopic = channelTopic;
	return this;
    }

    public String getChannelTopic() {
	return channelTopic;
    }

    public Channel setChannelMode(ChannelMode channelMode) {
	this.channelMode = channelMode;
	return this;
    }

    public ChannelMode getChannelMode() {
	return channelMode;
    }

    public HashMap<String, Client> getClients() {
	return clients;
    }

}
