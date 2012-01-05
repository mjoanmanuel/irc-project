package com.project.ircserver;

/**
 * ChannelMode is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public enum ChannelMode {

    /** Default channel mode. */
    PUBLIC,
    /** TODO */
    PRIVATE,
    /** TODO is same as private */
    SECRET,
    /** Only Operator can change topic. */
    TOPIC_CONTROL,
    /** TODO */
    CHANNEL_OPS,
    /** TODO. */
    MODERATED,
    /** Can join the channel only if operator permited. */
    INVITE_ONLY,
    /** N Number of client can join the channel. */
    LIMITED,
}
