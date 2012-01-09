package com.project.gui.irc;

import static com.project.ircserver.Server.DEFAULT_IRC_PORT;

import java.io.IOException;

import com.project.ircgui.chat.ChatFrame;
import com.project.ircserver.Server;
import com.project.ircserver.channel.Channel;

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

    public static void createChatFrame() {
	new ChatFrame();
    }

    public static Channel findChannel(final String channelname) {
	return null;
    }

}
