package com.project.ircgui.panel.setting;

import static com.project.ircgui.factory.ComponentFactory.createButton;
import static com.project.ircgui.factory.ComponentFactory.createCheckBox;
import static com.project.ircgui.factory.ComponentFactory.createComboBox;
import static com.project.ircgui.factory.ComponentFactory.createLabel;
import static com.project.ircgui.factory.ComponentFactory.createTextField;
import static com.project.ircserver.Connector.DEFAULT_IRC_PORT;
import static java.awt.FlowLayout.CENTER;
import static java.lang.String.valueOf;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.project.ircgui.common.CommonPanel;
import com.project.ircgui.utils.I18nUtils;

/**
 * ServerPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class SettingPanel extends CommonPanel {

    private static final long serialVersionUID = 1L;
    private static final String OPTIONAL = "optional";
    private static final String PORT = "port";
    private static final String CHANNEL = "channel";
    private static final String NICKNAME = "nickname";
    private static final String SERVERS = "servers";
    private static final String CONNECT = "connect";
    private static final String I18N_FILE = SettingPanel.class
	    .getCanonicalName();

    private I18nUtils i18n = new I18nUtils(I18N_FILE);
    private JComboBox avaibleServers;
    private JTextField portTextField;
    private JTextField nickname = createTextField();
    private JTextField channel = createTextField();

    public SettingPanel() {
	setLayout(new FlowLayout(CENTER));
	init();
    }

    @Override
    protected void init() {
	userInfo();
	serverInfo();
	submitInfo();
    }

    @Override
    protected void onClick() {
	if (validateInformation()) {
	    final String host = avaibleServers.getSelectedItem().toString();
	    // final Connector conn = new Connector(host, 6667);
	    // final Channel channel = conn.getChannel(this.channel.getText());

	}
    }

    private void userInfo() {
	add(createLabel(i18n.getString(NICKNAME)));
	add(nickname);
	add(createLabel(i18n.getString(CHANNEL)));
	add(channel);
    }

    private void serverInfo() {
	add(createLabel(i18n.getString(SERVERS)));
	avaibleServers = createComboBox(getAvaibleServers());
	add(avaibleServers);

	add(createLabel(i18n.getString(PORT)));
	portTextField = createTextField();
	portTextField.setText(valueOf(DEFAULT_IRC_PORT));
	portTextField.setEnabled(false);
	add(portTextField);
	add(createCheckBox(i18n.getString(OPTIONAL), checkBoxActionListener()));
    }

    private void submitInfo() {
	add(createButton(i18n.getString(CONNECT), new Action()));
    }

    // TODO: get all avaible irc servers.
    private Object[] getAvaibleServers() {
	final List<String> servers = new ArrayList<String>();
	servers.add("irc.kitsd.com");
	servers.add("irc.freenode.com");
	servers.add("irc.codehaus.com");
	servers.add("irc.maven.com");
	return servers.toArray();
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

    private boolean validateInformation() {
	return false;
    }

}
