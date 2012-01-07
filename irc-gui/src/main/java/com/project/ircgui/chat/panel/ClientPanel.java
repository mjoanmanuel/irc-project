package com.project.ircgui.chat.panel;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import com.project.ircgui.factory.ComponentFactory;
import com.project.ircgui.utils.I18nUtils;

/**
 * ClientPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ClientPanel extends JPanel {

    private static final String I18N_FILE = ClientPanel.class
	    .getCanonicalName();
    private static final I18nUtils properties = new I18nUtils(I18N_FILE);

    private static final long serialVersionUID = 1L;

    public ClientPanel() {
	add(createPrivateMessageButton(properties.getString("privateMessage")));
	add(createConnectedClientList());
    }

    private JList createConnectedClientList() {
	final JList list = new JList();
	return list;
    }

    private JButton createPrivateMessageButton(final String title) {
	final JButton button = ComponentFactory.createButton(title,
		new AbstractAction() {

		    private static final long serialVersionUID = 1L;

		    public void actionPerformed(final ActionEvent e) {
			// TODO open a private message frame.
		    }
		});
	return button;
    }
}
