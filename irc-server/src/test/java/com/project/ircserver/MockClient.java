package com.project.ircserver;

import static com.project.ircserver.Connector.DEFAULT_IRC_PORT;
import static java.lang.System.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * MockClient is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class MockClient {

    private static final boolean AUTO_FLUSH = true;
    private static final String DERP = "derp1";
    private static final String TEST_CHANNEL = "#testchannel";
    private static PrintWriter sender;
    private static BufferedReader reader;
    private static BufferedReader response = new BufferedReader(
	    new InputStreamReader(in));

    public static void main(String[] args) {
	Socket mockClient = null;// = createMockClient();
	try {
	    mockClient = new Socket(InetAddress.getLocalHost(),
		    DEFAULT_IRC_PORT);
	    sender = new PrintWriter(mockClient.getOutputStream(), AUTO_FLUSH);

	    sendCfg(sender);

	    reader = new BufferedReader(new InputStreamReader(
		    mockClient.getInputStream()));

	    String read = "";
	    // System.out.println(" mock chat: -> " + read);
	    // sender.println("");

	    while ((read = reader.readLine()) != null) {
		System.out.println(" -> " + read);
		final String userInput = response();
		System.out.println(userInput);
		sender.println(userInput);
	    }

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public static String response() throws IOException {
	return response.readLine();
    }

    public static void sendMessage(final String message) {
	sender.println(message);
    }

    private static void sendCfg(final PrintWriter sender) {
	sender.println(DERP + "," + TEST_CHANNEL);
    }

}
