package com.project.ircserver;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

import com.project.ircserver.channel.IRCChannel;

/**
 * IRCServer is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 * 
 *         TODO add timeout
 */
public class IRCServer extends Socket implements Serializable {

    public static final int DEFAULT_IRC_PORT = 6667;
    private static final long serialVersionUID = 1L;
    private String host;
    private List<IRCChannel> channels; // TODO
    private int port;

    public IRCServer(final String host) throws IOException {
	this(host, DEFAULT_IRC_PORT);
    }

    public IRCServer(final String host, final int port) throws IOException {
	super(host, port);
	this.host = host;
	this.port = port;
    }

    public String getHost() {
	return host;
    }

    public List<IRCChannel> getIRCChannels() {
	return channels;
    }

    public int getPort() {
	return port;
    }

}
