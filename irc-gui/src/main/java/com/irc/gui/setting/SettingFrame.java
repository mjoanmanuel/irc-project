package com.irc.gui.setting;

import static com.irc.gui.factory.ComponentFactory.createMenu;
import static com.irc.gui.factory.ComponentFactory.createMenuBar;
import static com.irc.gui.factory.ComponentFactory.createMenuItem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.irc.gui.common.CommonFrame;
import com.irc.gui.panel.miscellaneous.MiscellaneousPanel;
import com.irc.gui.panel.setting.SettingPanel;
import com.irc.gui.utils.I18nUtils;

/**
 * IRCFrame is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class SettingFrame extends CommonFrame {

	private static final String I18N_FILE = SettingFrame.class.getCanonicalName();
	private static final long serialVersionUID = 1L;
	private I18nUtils i18n = new I18nUtils(I18N_FILE);

	public SettingFrame() {
		super();
		setTitle(i18n.getString("appTitle"));
		setLayout(new GridLayout(4, 2));
		createMenubar();
		ircConfiguration();
	}

	private void createMenubar() {
		final JMenuBar menuBar = createMenuBar();
		createFileMenu(menuBar);
		// Create more menus if necessary.
		createHelpMenu(menuBar);
		setJMenuBar(menuBar);
	}

	private void createFileMenu(final JMenuBar menuBar) {
		final JMenu fileMenu = createMenu(i18n.getString("file"));
		final JMenuItem fileItem = createMenuItem(i18n.getString("exit"),
				exitAction());
		fileMenu.add(fileItem);
		menuBar.add(fileMenu);
	}

	private void createHelpMenu(final JMenuBar menuBar) {
		final JMenu helpMenu = createMenu(i18n.getString("help"));
		helpMenu.add(createMenuItem(i18n.getString("troubleshoot"),
				troubleshootAction()));
		helpMenu.add(createMenuItem(i18n.getString("about"), exitAction()));
		menuBar.add(helpMenu);
	}

	private ActionListener troubleshootAction() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			}
		};
	}

	private ActionListener exitAction() {
		return new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				SettingFrame.this.dispose();
			}
		};
	}

	private void ircConfiguration() {
		add(new SettingPanel());
		add(new MiscellaneousPanel());
	}

}
