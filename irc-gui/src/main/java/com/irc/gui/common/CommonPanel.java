package com.irc.gui.common;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

/**
 * CommonPanel is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public abstract class CommonPanel extends JPanel {

	/**
	 * Action is responsible of
	 * 
	 * @author mjoanmanuel@gmail.com
	 */
	public class Action extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			onClick();
		}

	}

	private static final long serialVersionUID = 1L;

	protected abstract void init();

	protected abstract void onClick();

}
