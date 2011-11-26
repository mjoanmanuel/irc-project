package com.project.ircgui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.project.ircgui.panel.miscellaneous.MiscellaneousPanel;
import com.project.ircgui.panel.server.ServerPanel;
import com.project.ircgui.panel.user.UserPanel;
import com.project.ircgui.utils.I18nUtils;

/**
 * IRCFrame is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class IRCFrame extends JFrame {

    private static final String I18N_FILE = "com.project.ircgui.IRCFrame";
    private static final long serialVersionUID = 1L;
    private I18nUtils i18n = new I18nUtils();

    public IRCFrame() {
	init();
	i18n.setI18n(I18N_FILE);
	add(ircConfigurationTabs());
    }

    private JTabbedPane ircConfigurationTabs() {
	JTabbedPane tabs = new JTabbedPane();

	tabs.addTab(i18n.getString("server"), new ServerPanel());
	tabs.addTab(i18n.getString("userInfo"), new UserPanel());
	tabs.addTab(i18n.getString("miscellaneous"), new MiscellaneousPanel());

	return tabs;
    }

    protected void init() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(400, 400);
	setVisible(true);
    }

}
