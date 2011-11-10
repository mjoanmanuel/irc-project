package com.project.ircserver;

import java.io.IOException;
import java.net.Socket;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App. <br/>
 * How to run this test: <br/>
 * 1. Run this test <br/>
 * 2. Run IRCMockClient <br/>
 * 3. Have a blast! (:
 */
public class IRCServerTest {

    private static final int PORT = 8080;
    private static final String SERVER_NAME = "localhost";
    private static final String HOST = "irc.localhost.com";
    private IRCServer server;

    @Before
    public void setUp() {
	try {
	    server = new IRCServer(SERVER_NAME, HOST, PORT);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void testCreateIRCServer() {
	Assert.assertNotNull(server);

	try {
	    server.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void testAcceptMockupClient() {
	Socket mockClient = null;
	try {
	    mockClient = server.accept();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	Assert.assertNotNull(mockClient);
    }
}
