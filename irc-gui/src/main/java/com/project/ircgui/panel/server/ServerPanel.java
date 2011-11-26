package com.project.ircgui.panel.server;

import static com.project.ircgui.factory.ComponentFactory.createButton;
import static com.project.ircgui.factory.ComponentFactory.createCheckBox;
import static com.project.ircgui.factory.ComponentFactory.createComboBox;
import static com.project.ircgui.factory.ComponentFactory.createLabel;
import static com.project.ircgui.factory.ComponentFactory.createTextField;
import static com.project.ircserver.IRCServer.DEFAULT_IRC_PORT;
import static java.awt.FlowLayout.CENTER;
import static java.lang.String.valueOf;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import com.project.ircgui.common.CommonPanel;
import com.project.ircgui.utils.I18nUtils;

/**
 * ServerPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ServerPanel extends CommonPanel {

    private static final String OPTIONAL = "optional";
    private static final String PORT = "port";
    private static final long serialVersionUID = 1L;
    private static final String SERVERS = "servers";
    private static final String CONNECT = "connect";
    private static final String I18N_FILE = "com.project.ircgui.panel.server.ServerPanel";

    private I18nUtils i18n = new I18nUtils();

    public ServerPanel() {
	i18n.setI18n(I18N_FILE);
	setLayout(new FlowLayout(CENTER));
	init();
    }

    @Override
    protected void init() {
	serverInfo();
	submitInfo();
    }

    private void serverInfo() {
	add(createLabel(i18n.getString(SERVERS)));
	add(createComboBox(getAvaibleServers()));

	add(createLabel(i18n.getString(PORT)));
	final JTextField portTextField = createTextField();
	portTextField.setText(valueOf(DEFAULT_IRC_PORT));
	portTextField.setEnabled(false);
	add(portTextField);
	add(createCheckBox(i18n.getString(OPTIONAL), checkBoxActionListener()));
    }

    /** Simple action to enable Port textField when checked. */
    private ActionListener checkBoxActionListener() {
	return new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		for (final Component component : getComponents()) {
		    if (component instanceof JTextField) {
			component.setEnabled(!component.isEnabled());
		    }
		}
	    }
	};
    }

    private void submitInfo() {
	add(createButton(i18n.getString(CONNECT), new Action()));
    }

    // TODO: get all avaible irc servers.
    private Object[] getAvaibleServers() {
	final List<String> servers = new ArrayList<String>();
	return servers.toArray();
    }

    // TODO: onSubmit serverInfo
    @Override
    protected void onClick() {
    }

}
