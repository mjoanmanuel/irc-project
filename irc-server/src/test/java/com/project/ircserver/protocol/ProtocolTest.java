package com.project.ircserver.protocol;

import junit.framework.Assert;

import org.junit.Test;

/**
 * ProtocolTest is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ProtocolTest {

    private final Protocol protocol = new Protocol();

    @Test
    public void testValidateNicknames() {
	final String characterNickname = "lederp";
	final String characterAndNumberNickname = "derp123";
	// is wrong because has 10 digits.
	final String wrongNickName = "12345678910";
	final String otherWrongNickname = "empty12345678910";
	// won't accept this.. your argument is invalid! (: 9gag bro.
	final String emptyNickname = "";

	// correct
	Assert.assertTrue(protocol.validateNickname(characterNickname));
	// correct
	Assert.assertTrue(protocol.validateNickname(characterAndNumberNickname));
	// wrong
	Assert.assertFalse(protocol.validateNickname(wrongNickName));
	// totally wrong!
	Assert.assertFalse(protocol.validateNickname(otherWrongNickname));
	// even deserv a test..
	Assert.assertFalse(protocol.validateNickname(emptyNickname));
    }

    // TODO
    @Test
    public void testValidateChannelname() {
	final String wrongChannelName = "derpsChannel";
	final String otherWrongChannelName = "derpsChannel trueStory";
	final String correctChannelname = "#derphinaChannel";
	final String otherCorrectChannelName = "&lesexytime";

	Assert.assertFalse(protocol.validateChannelName(wrongChannelName));
	Assert.assertFalse(protocol.validateChannelName(otherWrongChannelName));
	Assert.assertTrue(protocol.validateChannelName(correctChannelname));
	Assert.assertTrue(protocol.validateChannelName(otherCorrectChannelName));
    }
}
