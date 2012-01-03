/**
 * 
 */
package com.project.ircserver;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author jmendoza Dec 1, 2011
 */
public class Server extends ServerSocket {

	/** Max client allowed per channel. */
	public static final int MAX_CLIENTS_ALLOWED = 30;
	
	public Server(int port) throws IOException {
		super(port);
	}

}
