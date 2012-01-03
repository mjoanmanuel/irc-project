package com.project.ircserver;

import static com.project.ircserver.Connector.DEFAULT_IRC_PORT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.project.ircserver.channel.Channel;

/**
 * Unit test for simple IRCConnector.
 * 
 * @author jmendoza Dec 1, 2011
 */
public class IRCConnectorTest {

	private static final String HOST = "irc.freenode.com";
	private Connector mockConnector;

	@Before
	public void setUp() {
		try {
			mockConnector = new Connector(HOST, DEFAULT_IRC_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateIRCConnectionSuccess() {
		final Socket connected = mockConnector.connect();
		Assert.assertNotNull(connected);
		
	}

	public void testConnectToHostAvaibleChannel() {
		final Channel channel = mockConnector.getChannel(" devs ");
		channel.sendMsg(" hi kindleit (: !");
	}
}
