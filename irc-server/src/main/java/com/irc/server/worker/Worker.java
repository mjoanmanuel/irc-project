package com.irc.server.worker;

import static java.lang.String.format;
import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map.Entry;

import com.irc.client.Client;
import com.irc.server.channel.Channel;
import com.irc.server.protocol.Protocol;

/**
 * Worker is responsible of handling client I/O.
 * 
 * @author mjoanmanuel@gmail.com
 */
public class Worker extends Thread {

    private static final String GET_LIST_COMMAND = "[get]";
    private static final String LOG_MSG = " channel: %s, nickname: %s, message: %s";
    private static final String END_COMMAND = "[end]";
    private static final boolean AUTO_FLUSH = true;

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
	    sender = new PrintWriter(client.getSocket().getOutputStream(),
		    AUTO_FLUSH);
	    receiver = new BufferedReader(new InputStreamReader(client
		    .getSocket().getInputStream()));
	    String receiveMessageString = "";

	    while ((receiveMessageString = receiver.readLine()) != null) {
		if (receiveMessageString.equals(GET_LIST_COMMAND)) {
		    sendList(sender); // send online users.
		} else {
		    final String response = protocol.handleInput(channel,
			    client, receiveMessageString);
		    // LOGGING INTO SERVER OUTPUT.
		    out.println(format(LOG_MSG, channel.getChannelName(),
			    client.getNickname(), response));

		    sender.write(response);
		}
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

    private void sendList(final PrintWriter sendMessage) {
	final Iterator<Entry<String, Client>> iterator = channel.getClients()
		.entrySet().iterator();
	while (iterator.hasNext()) {
	    final Entry<String, Client> current = iterator.next();
	    sendMessage.println(current.getKey());
	}
	sendMessage.println(END_COMMAND);
    }

    private void closeOutputResource(final PrintWriter sendMessage) {
	sendMessage.close();
    }

    private void closeInputResource(final BufferedReader receiveMessage) {
	try {
	    receiveMessage.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private boolean isOutputOpen(final PrintWriter sendMessage) {
	return sendMessage != null;
    }

    private boolean isInputOpen(final BufferedReader receiveMessage) {
	return receiveMessage != null;
    }

}
