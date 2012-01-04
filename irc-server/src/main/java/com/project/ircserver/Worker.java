package com.project.ircserver;

import static java.lang.String.format;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.project.ircclient.Client;
import com.project.ircserver.channel.Channel;
import com.project.ircserver.protocol.Protocol;

/**
 * Worker is responsible of handling client I/O.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Worker extends Thread {

    private Client client;
    private Channel channel;
    private Protocol protocol;

    public Worker(final Client client, final Channel channel,
	    final Protocol protocol) {
	this.client = client;
	this.channel = channel;
	this.protocol = protocol;
    }

    @Override
    public synchronized void run() {
	super.run();
	PrintWriter sender = null;
	BufferedReader receiver = null;

	try {
	    sender = new PrintWriter(client.getSocket().getOutputStream());
	    receiver = new BufferedReader(new InputStreamReader(client
		    .getSocket().getInputStream()));
	    String receiveMessageString = "";
	    while ((receiveMessageString = receiver.readLine()) != null) {
		final String response = protocol.handleInput(channel, client,
			receiveMessageString);
		out.println(format(" channel: %s, nickname: %s, message: %s",
			channel.getChannelName(), client.getNickname(),
			response));

		sender.write(response);
	    }
	} catch (final IOException ex) {
	    ex.printStackTrace();
	} finally {
	    if (isOutputOpen(sender) && isInputOpen(receiver)) {
		closeOutputResource(sender);
		closeInputResource(receiver);
	    }
	}
    }

    private void closeOutputResource(final PrintWriter sendMessage) {
	sendMessage.close();
    }

    private void closeInputResource(final BufferedReader receiveMessage) {
	try {
	    receiveMessage.close();
	} catch (IOException e) {
	}
    }

    private boolean isOutputOpen(final PrintWriter sendMessage) {
	return sendMessage != null;
    }

    private boolean isInputOpen(final BufferedReader receiveMessage) {
	return receiveMessage != null;
    }

}
