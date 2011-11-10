package com.project.ircserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * IRCMockClient is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class IRCMockClient {

    public static void main(String[] args) {
	Socket mockClient = null;
	try {
	    mockClient = new Socket(InetAddress.getLocalHost(), 8080);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
