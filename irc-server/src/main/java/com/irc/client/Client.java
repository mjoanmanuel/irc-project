package com.irc.client;

import java.net.Socket;

import com.irc.server.Server;

/**
 * Client is responsible of a IRC client entity.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Client {

    private static final String OPERATOR_IDENTIFIER = "@";
    private static final Long MAX_JOIN_CHANNEL = 10L;

    private Long joinChannel;
    private String nickname; // holds the nickname for the client.
    private Server server; // client connected to the irc server.
    // Operator can do whatever he/she wants. TODO : see if we can implement
    // this.
    private String channelname;
    private Socket socket;
    private boolean isOperator;

    public Client setNickname(final String nickname) {
	this.nickname = nickname;
	if (hasOperatorIdentifier(nickname)) {
	    isOperator = true;
	}
	return this;
    }

    /** @return client's nickname. */
    public String getNickname() {
	return nickname;
    }

    /**
     * (RFC Specification.)
     * 
     * @return the host where the client is connected.
     */
    public String getRealHost() {
	return socket.getInetAddress().getHostName();
    }

    public Client setServer(final Server server) {
	this.server = server;
	return this;
    }

    /**
     * Each client must have the asociated server.
     * 
     * @return server where the client is connected to.
     */
    public Server getServer() {
	return server;
    }

    /**
     * Indicate if the client is the channel owner.
     * 
     * @return true if is the channel owner otherwise false.
     */
    public boolean isOperator() {
	return isOperator;
    }

    /**
     * Indicate if the client has the '@' operator identifier.
     * 
     * @return true if is the owner otherwise false.
     * */
    private boolean hasOperatorIdentifier(final String nickname) {
	return nickname.contains(OPERATOR_IDENTIFIER);
    }

    public Client setChannelName(final String channelname) {
	this.channelname = channelname;
	return this;
    }

    // TODO is this necessary?.
    /** @return the channel name. */
    public String getChannelName() {
	return channelname;
    }

    public Client setSocket(final Socket socket) {
	this.socket = socket;
	return this;
    }

    /** @return the socket for I/O communication. */
    public Socket getSocket() {
	return socket;
    }

    public Client setJoinChannel(final Long channels) {
	this.joinChannel = channels;
	return this;
    }

    /**
     * Represents how many channels is this client connected to.
     * 
     * @return numbers of channels of this client connected to.
     */
    public Long getJoinChannel() {
	return joinChannel;
    }

    /**
     * Indicate if the client can join the channel.
     * 
     * @return true if can connect to other channel otherwise false.
     */
    public boolean canJoinChannel() {
	return joinChannel != MAX_JOIN_CHANNEL;
    }
}
