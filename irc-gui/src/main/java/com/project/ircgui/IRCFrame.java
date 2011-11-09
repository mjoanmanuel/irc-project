package com.project.ircgui;

import java.awt.GridLayout;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.project.ircgui.panel.miscellaneous.MiscellaneousPanel;
import com.project.ircgui.panel.server.ServerPanel;
import com.project.ircgui.panel.userinfo.UserInfoPanel;

/**
 * IRCFrame is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class IRCFrame extends JFrame {

    private static final String DEFAULT_I18N_FILE = "com.project.ircgui.IRCFrame";
    private static final String DEFAULT_COUNTRY = "ES";
    private static final String DEFAULT_LANGUAGE = "es";
    private static final long serialVersionUID = 1L;

    /** Class to use i18n */
    private ResourceBundle i18n;

    public IRCFrame() {
	init();
	add(ircConfigurationTabs());
    }

    /**
     * @param i18nFile
     *            must be a valid resource file.
     * @param locale
     *            current locale
     * @param language
     *            must be es or en or so..
     * @return
     */
    public ResourceBundle setI18n(final String i18nFile, final String language,
	    final String country) {
	Locale locale = Locale.getDefault();

	if (language != null && country != null) {
	    locale = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
	}

	i18n = ResourceBundle.getBundle(i18nFile, locale);
	if (i18nFile != null && i18n != null) {
	    return i18n;
	}
	return null;
    }

    private JTabbedPane ircConfigurationTabs() {
	JTabbedPane tabs = new JTabbedPane();

	tabs.addTab(i18n.getString("server"), new ServerPanel());
	tabs.addTab(i18n.getString("userInfo"), new UserInfoPanel());
	tabs.addTab(i18n.getString("miscellaneous"), new MiscellaneousPanel());

	return tabs;
    }

    private JComponent makePanel(final String text) {
	JPanel panel = new JPanel(false);
	JLabel filler = new JLabel(text);
	filler.setHorizontalAlignment(JLabel.CENTER);
	panel.setLayout(new GridLayout(1, 1));
	panel.add(filler);
	return panel;
    }

    protected void init() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(400, 400);
	setVisible(true);
	setI18n(DEFAULT_I18N_FILE, null, null);
    }

}
