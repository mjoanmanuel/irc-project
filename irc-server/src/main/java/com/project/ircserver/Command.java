package com.project.ircserver;

/**
 * Command is responsible of channel operators avaible commands.
 * 
 * @author mjoanmanuel@gmail.com
 */
public enum Command {

    /** Bann a client from the channel. */
    KICK("/KICK"),
    /** Change the channel mode. */
    MODE("/MODE"),
    /** Invite a client to an invite channel (see mode +i notation). */
    INVITE("/INVITE"),
    /** Change the channel topic with a mode +t. */
    TOPIC("/TOPIC"),
    /** Join to other channel. */
    JOIN("/JOIN"),
    /** poke your name. */
    ME("/ME"),
    /** Leave the channel. */
    LEAVE("/LEAVE"),
    /** Send a private message. */
    MSG("/MSG");

    private String text;

    Command(final String text) {
	this.text = text;
    }

    public String getText() {
	return text;
    }
}
