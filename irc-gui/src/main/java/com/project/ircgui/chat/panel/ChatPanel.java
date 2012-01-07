package com.project.ircgui.chat.panel;

import static com.project.ircgui.factory.ComponentFactory.createButton;
import static com.project.ircgui.factory.ComponentFactory.createLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.project.ircgui.utils.I18nUtils;

/**
 * ChatPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ChatPanel extends JPanel {

    private static final String FILE_I18N = ChatPanel.class.getCanonicalName();
    private static final I18nUtils properties = new I18nUtils(FILE_I18N);

    private static final long serialVersionUID = 1L;

    public ChatPanel() {
	setLayout(new GridLayout(5, 1));
	add(createLabel(properties.getString("nickname")));
	add(createLabel("[nickname_here]"));
	add(createRecieveMessageTextArea());
	add(createSendMessageTextArea());
	add(createLabel(properties.getString("messageLabel")));
	add(createButton(properties.getString("sendMessage"),
		new AbstractAction() {

		    private static final long serialVersionUID = 1L;

		    public void actionPerformed(ActionEvent e) {
			// TODO send message.
		    }
		}));
	setVisible(true);
    }

    private JTextArea createSendMessageTextArea() {
	return new JTextArea();
    }

    private JTextArea createRecieveMessageTextArea() {
	return new JTextArea();
    }
}
