package com.project.ircserver.channel;

import static java.lang.String.format;

import java.io.PrintStream;
import java.io.Serializable;

/**
 * IRCChannel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Channel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String channelName;
	private PrintStream writeToClient;

	public Channel(final String channelName, final PrintStream writeToClient) {
		this.channelName = channelName;
		this.writeToClient = writeToClient;
		sendJoinMsg();
	}

	public Channel setChannel(final String channelName) {
		this.channelName = channelName;
		return this;
	}

	public String getChannel() {
		return channelName;
	}

	public Channel setIrcClient(final PrintStream writeToClient) {
		this.writeToClient = writeToClient;
		return this;
	}

	public PrintStream getIrcClient() {
		return writeToClient;
	}

	public void sendMsg(final String msg) {
		writeToClient.println(msg);
	}

	private void sendJoinMsg() {
		// send join msg to connected client.
		writeToClient.println(format("JOIN # '%s' ", channelName));
	}

}
