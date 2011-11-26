package com.project.ircserver.channel;

import java.io.Serializable;

import com.project.ircclient.IRCClient;

/**
 * IRCChannel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class IRCChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String channel;
    private IRCClient ircClient;

    public IRCChannel(final String channel, final IRCClient ircClient) {
	this.channel = channel;
	this.setIrcClient(ircClient);
    }

    public IRCChannel setChannel(String channel) {
	this.channel = channel;
	return this;
    }

    public String getChannel() {
	return channel;
    }

    public IRCChannel setIrcClient(IRCClient ircClient) {
	this.ircClient = ircClient;
	return this;
    }

    public IRCClient getIrcClient() {
	return ircClient;
    }
}
