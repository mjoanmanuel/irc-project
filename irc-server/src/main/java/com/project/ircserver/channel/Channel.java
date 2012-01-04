package com.project.ircserver.channel;

import static com.project.ircserver.utils.ProtocolUtils.sendGlobalMessage;
import static java.lang.String.format;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.project.ircclient.Client;

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

    private static final long serialVersionUID = 1L;

    private String channelName;

    private HashMap<String, Client> clients = new HashMap<String, Client>();
    // This property holds a channel with registered clients.
    private Map<String, HashMap<String, Client>> channel = new HashMap<String, HashMap<String, Client>>();

    public Channel(final String channelName) {
	this.channelName = channelName;
    }

    // Add client to the channel and send a JOIN MSG to all other clients.
    public void addClient(final Client client) {
	clients.put(client.getNickname(), client);
	channel.put(channelName, clients);
	sendJoinMsg(client);
    }

    public Channel setChannelName(final String channelName) {
	this.channelName = channelName;
	return this;
    }

    public String getChannelName() {
	return channelName;
    }

    public void sendJoinMsg(final Client client) {
	sendGlobalMessage(this, client, format("JOIN %s ", channelName));
    }

    /** @return a Map containing all the clients in the specify channel. */
    public HashMap<String, Client> getClients() {
	return channel.get(channelName);
    }

}
