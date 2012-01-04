package com.project.ircserver;

import static com.project.ircserver.Connector.DEFAULT_IRC_PORT;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * ServerTest is responsible of testing the server.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ServerTest {

    private Server server;

    @Before
    public void setUp() {
	try {
	    server = new Server(DEFAULT_IRC_PORT);
	} catch (final IOException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void createServerInstance() {
	final boolean hasCreateServer = server != null;
	Assert.assertTrue(hasCreateServer);
	Assert.assertTrue(closeServerConnection());
    }

    @Test
    public void testAcceptConnections() {
	server.start();

	// maybe wont reach this line..
	Assert.assertTrue(closeServerConnection());
    }

    private boolean closeServerConnection() {
	try {
	    server.close();
	    return true;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return false;
    }

}
