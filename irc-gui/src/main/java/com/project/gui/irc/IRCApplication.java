package com.project.gui.irc;

import static com.project.ircserver.Server.DEFAULT_IRC_PORT;

import java.io.IOException;

import com.project.ircserver.Server;

/**
 * IRCApplication is responsible of.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class IRCApplication {

    private static Server server;

    public static void main(String[] args) {
	createServer();
    }

    public static void createServer() {
	try {
	    server = new Server(DEFAULT_IRC_PORT);
	    server.start();
	} catch (final IOException e) {
	    System.out.println(" Change server port!. ");
	    e.printStackTrace();
	}
    }

}
