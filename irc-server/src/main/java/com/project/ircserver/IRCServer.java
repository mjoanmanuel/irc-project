package com.project.ircserver;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;

/**
 * IRCServer is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 * 
 *         TODO add timeout
 */
public class IRCServer extends ServerSocket implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serverName;
    private String host; // TODO implement correctly
    private int port;

    public IRCServer(final String serverName, final String host, final int port)
	    throws IOException {
	super(port);
	this.serverName = serverName;
	this.host = host;
	this.port = port;
    }

    public String getServerName() {
	return serverName;
    }

    public String getHost() {
	return host;
    }

    public int getPort() {
	return port;
    }

}
