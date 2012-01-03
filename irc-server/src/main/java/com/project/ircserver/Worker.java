package com.project.ircserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.project.ircclient.Client;
import com.project.ircserver.protocol.Protocol;

/**
 * Worker is responsible of handling client I/O.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Worker extends Thread {

    private Client client;
    private Protocol protocol;

    public Worker(final Client client, final Protocol protocol) {
	this.client = client;
	this.protocol = protocol;
    }

    @Override
    public synchronized void start() {
	super.start();
	PrintWriter sender = null;
	BufferedReader receiver = null;

	try {
	    sender = new PrintWriter(client.getOutputStream());
	    receiver = new BufferedReader(new InputStreamReader(
		    client.getInputStream()));
	    String receiveMessageString = "";
	    while ((receiveMessageString = receiver.readLine()) != null) {
		protocol.handleMessage(receiveMessageString);
	    }
	} catch (final IOException ex) {
	    ex.printStackTrace();
	} finally {
	    if (isOutPutOpen(sender) && isInputOpen(receiver)) {
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

    private boolean isOutPutOpen(final PrintWriter sendMessage) {
	return sendMessage != null;
    }

    private boolean isInputOpen(final BufferedReader receiveMessage) {
	return receiveMessage != null;
    }

}
