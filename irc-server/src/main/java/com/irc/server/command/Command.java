package com.irc.server.command;

/**
 * Command is responsible of channel operators avaible commands.
 * 
 * @author mjoanmanuel@gmail.com
 */
public enum Command {

    /** Bann a client from the channel. */
    KICK {
	@Override
	public String toString() {
	    return "/KICK";
	}
    },
    /** Change the channel mode. */
    MODE {
	public String toString() {
	    return "/MODE";
	}
    },
    /** Invite a client to an invite channel (see mode +i notation). */
    INVITE {
	@Override
	public String toString() {
	    return "/INVITE";
	}
    },
    /** Change the channel topic with a mode +t. */
    TOPIC {
	@Override
	public String toString() {
	    return "/TOPIC";
	}
    },
    /** Join to other channel. */
    JOIN {
	@Override
	public String toString() {
	    return "/JOIN";
	}
    },
    /** poke your name. */
    ME {
	@Override
	public String toString() {
	    return "/ME";
	}
    },
    /** Leave the channel. */
    LEAVE {
	@Override
	public String toString() {
	    return "/LEAVE";
	}
    },
    /** Send a private message. */
    MSG {
	@Override
	public String toString() {
	    return "/MSG";
	}
    },
    /** Change the user nickname. */
    NICK {
	@Override
	public String toString() {
	    return "/NICK";
	}
    },

    /** A unknow command. */
    NONCOMMAND {
	@Override
	public String toString() {
	    return "NONCOMMAND";
	}
    };
}
