package com.project.ircserver;

import static java.lang.String.format;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import com.project.ircserver.channel.Channel;

/**
 * IRCServer is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 * 
 *         TODO add timeout
 */
public class Connector implements Serializable {

	/** Default IRC port defined by RFC document. */
	public static final int DEFAULT_IRC_PORT = 6667;
	private static final long serialVersionUID = 1L;
	private String host;
	private int port;
	private PrintStream writeTo = null;

	public Connector(final String host) throws UnknownHostException, IOException {
		this(host, DEFAULT_IRC_PORT);
	}

	public Connector(final String host, final int port)
			throws UnknownHostException, IOException {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public Channel getChannel(final String channelName) {
		return new Channel(channelName, writeTo);
	}

	public int getPort() {
		return port;
	}

	public Socket connect() {
		Socket socket;
		try {
			socket = new Socket(host, DEFAULT_IRC_PORT);
			writeTo = new PrintStream(socket.getOutputStream());
			return socket;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void register() {
		final String nickname = "test";
		final String hosted = "locahost";
		writeTo.println(format("nickname: '%s', host: '%s'", nickname, hosted));
	}

}
