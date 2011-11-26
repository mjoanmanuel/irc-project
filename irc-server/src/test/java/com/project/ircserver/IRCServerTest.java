package com.project.ircserver;

import static com.project.ircserver.IRCServer.DEFAULT_IRC_PORT;

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

    private static final String HOST = "irc.myirchost.com";
    private IRCServer server;

    @Before
    public void setUp() {
	try {
	    server = new IRCServer(HOST);
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
	    mockClient = new Socket(server.getHost(), DEFAULT_IRC_PORT);
	    mockClient.connect(server.getLocalSocketAddress());
	} catch (IOException e) {
	    e.printStackTrace();
	}
	Assert.assertNotNull(mockClient);
    }
}
