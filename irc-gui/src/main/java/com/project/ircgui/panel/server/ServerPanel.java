package com.project.ircgui.panel.server;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.project.ircgui.common.CommonPanel;

/**
 * ServerPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class ServerPanel extends CommonPanel {

    class ConnectAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public void actionPerformed(ActionEvent e) {
	    onClick();
	}
    }

    private static final long serialVersionUID = 1L;

    public ServerPanel() {
	super();
    }

    // TODO
    public void onClick() {
    }

    @Override
    protected void init() {
	JLabel serverNameLabel = new JLabel("IRC Server:");
	// TODO get list irc servers
	JComboBox serversCombBox = new JComboBox();
	JButton connectToServerbtn = new JButton(new ConnectAction());
	connectToServerbtn.setText("Connect");

	add(serverNameLabel);
	add(serversCombBox);
	add(connectToServerbtn);
    }

}
