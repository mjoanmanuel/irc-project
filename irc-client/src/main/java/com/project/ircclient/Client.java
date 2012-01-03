package com.project.ircclient;

import java.net.Socket;

import com.project.ircserver.Server;

/**
 * Client is an IRCClient IRC
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Client extends Socket {

    private static final String OPERATOR_IDENTIFIER = "@";
    private String nickname; // holds the nickname for the client.
    private Server server; // client connected to the irc server.
    // Operator can do whatever he/she wants. TODO : see if we can implement
    // this.
    private boolean isOperator;

    public Client() {
    }

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
	return getInetAddress().getHostName();
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

}
