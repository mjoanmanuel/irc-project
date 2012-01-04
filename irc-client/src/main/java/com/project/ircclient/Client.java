package com.project.ircclient;

import java.net.Socket;

import com.project.ircserver.Server;

/**
 * Client is an IRCClient IRC
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Client {

    private static final String OPERATOR_IDENTIFIER = "@";

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

    public String getNickname() {
	return nickname;
    }

    public String getRealHost() {
	return socket.getInetAddress().getHostName();
    }

    public Client setServer(final Server server) {
	this.server = server;
	return this;
    }

    public Server getServer() {
	return server;
    }

    public boolean isOperator() {
	return isOperator;
    }

    private boolean hasOperatorIdentifier(final String nickname) {
	return nickname.contains(OPERATOR_IDENTIFIER);
    }

    public Client setChannelName(final String channelname) {
	this.channelname = channelname;
	return this;
    }

    public String getChannelName() {
	return channelname;
    }

    public Client setSocket(final Socket socket) {
	this.socket = socket;
	return this;
    }

    public Socket getSocket() {
	return socket;
    }

}
