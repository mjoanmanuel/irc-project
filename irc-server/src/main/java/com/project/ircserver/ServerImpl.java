/**
 * 
 */
package com.project.ircserver;

import static com.project.ircserver.Connector.DEFAULT_IRC_PORT;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import com.project.ircclient.IRCClient;

/**
 * @author jmendoza Dec 1, 2011
 */
public final class ServerImpl {

	private static Server ircServer;
	
	private static final boolean RUN = true;
	static {
		if (ircServer == null) {
			try {
				ircServer = new Server(DEFAULT_IRC_PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void start() {
		while (RUN) {
			try {
				final IRCClient client = (IRCClient)ircServer.accept();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void stop(){
		try {
			// stop it by brute force (: don't care!
			ircServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
