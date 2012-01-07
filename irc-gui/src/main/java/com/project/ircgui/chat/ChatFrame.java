/**
 * 
 */
package com.project.ircgui.chat;

import static com.project.ircgui.factory.ComponentFactory.createMenu;
import static com.project.ircgui.factory.ComponentFactory.createMenuItem;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.project.ircgui.chat.panel.ChatPanel;
import com.project.ircgui.chat.panel.ClientPanel;
import com.project.ircgui.factory.ComponentFactory;
import com.project.ircgui.utils.I18nUtils;

/**
 * @author jmendoza Dec 1, 2011
 */
public class ChatFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String I18N_FILE = ChatFrame.class.getCanonicalName();
    private static final I18nUtils properties = new I18nUtils(I18N_FILE);

    public ChatFrame() {
	init();
	final Container container = getContentPane();
	container.add(new ClientPanel(), WEST);
	container.add(new ChatPanel(), EAST);
	pack();
    }

    private void createMenuBar() {
	final JMenuBar menuBar = ComponentFactory.createMenuBar();
	createFileMenu(menuBar);
	createHelpMenu(menuBar);
	setJMenuBar(menuBar);
    }

    private void init() {
	createMenuBar();
	setTitle(properties.getString("chatTitle"));
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(400, 400);
	setVisible(true);
    }

    private void createFileMenu(final JMenuBar menuBar) {
	final JMenu fileMenu = createMenu(properties.getString("file"));
	final JMenuItem fileItem = createMenuItem(properties.getString("exit"),
		null);
	fileMenu.add(fileItem);
	menuBar.add(fileMenu);
    }

    private void createHelpMenu(final JMenuBar menuBar) {
	final JMenu helpMenu = createMenu(properties.getString("help"));
	helpMenu.add(createMenuItem(properties.getString("troubleshoot"),
		troubleshootAction()));
	helpMenu.add(createMenuItem(properties.getString("about"), exitAction()));
	menuBar.add(helpMenu);
    }

    private ActionListener exitAction() {
	return new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		// this.dispose();
	    }
	};
    }

    private ActionListener troubleshootAction() {
	return new ActionListener() {

	    public void actionPerformed(ActionEvent e) {

	    }
	};
    }

}
