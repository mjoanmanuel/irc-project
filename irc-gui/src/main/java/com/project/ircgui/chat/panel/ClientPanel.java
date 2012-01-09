package com.project.ircgui.chat.panel;

import static com.project.ircgui.factory.ComponentFactory.createLabel;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

    private DefaultListModel listModel = new DefaultListModel();

    public ClientPanel(final String channelname) {
	setLayout(new BorderLayout());
	add(createLabel(properties.getString("online")), NORTH);
	add(createConnectedClientList(channelname), CENTER);
    }

    private JScrollPane createConnectedClientList(final String channelname) {
	final JList list = new JList(listModel);
	return new JScrollPane(list);
    }

}
