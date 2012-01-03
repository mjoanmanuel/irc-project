/**
 * 
 */
package com.project.ircgui;

import javax.swing.JFrame;

import com.project.ircgui.utils.I18nUtils;

/**
 * @author jmendoza Dec 1, 2011
 */
public class ChatFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String I18N_FILE = ChatFrame.class.getCanonicalName();
	private static final I18nUtils i18n = new I18nUtils(I18N_FILE);

	public ChatFrame() {
		init();
	}

	private void init() {
		setTitle(i18n.getString("chatTitle"));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 400);
		setVisible(true);
	}

}
