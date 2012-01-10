package com.irc.gui;

import javax.swing.SwingUtilities;

import com.irc.gui.chat.ChatFrame;

/**
 * IRCClient is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class IRCClient {

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		new ChatFrame();
	    }
	});
    }

}
