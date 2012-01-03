package com.project.ircgui.factory;

import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

/**
 * ComponentFactory is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public final class ComponentFactory {

	/**
	 * 
	 */
	private static final int DEFAULT_TXTFIELD_WIDTH = 100;
	/**
	 * 
	 */
	private static final int DEFAULT_TXTFIELD_HEIGHT = 25;

	public static JLabel createLabel(final String text) {
		return new JLabel(text);
	}

	public static JMenuBar createMenuBar() {
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setVisible(true);
		menuBar.setSize(100, 100);
		return menuBar;
	}

	public static JMenu createMenu(final String menuTitle,
			final JMenuItem... menuItems) {
		final JMenu menu = new JMenu(menuTitle);
		for (final JMenuItem menuItem : menuItems) {
			menu.add(menuItem);
		}
		return menu;
	}

	public static JMenuItem createMenuItem(final String menuItemTitle,
			final ActionListener action) {
		final JMenuItem item = new JMenuItem();
		item.setText(menuItemTitle);
		if (action != null) {
			item.addActionListener(action);
		}
		return item;
	}

	public static JComboBox createComboBox(final Object... items) {
		final JComboBox comboBox = new JComboBox(items);
		comboBox.setEditable(true);
		return comboBox;
	}

	public static JTextField createTextField() {
		final JTextField textField = new JTextField();
		textField.setSize(DEFAULT_TXTFIELD_WIDTH, DEFAULT_TXTFIELD_HEIGHT);
		return textField;
	}

	public static JButton createButton(final String text,
			final AbstractAction action) {
		final JButton button = new JButton(text);
		if (action != null) {
			button.addActionListener(action);
		}
		return button;
	}

	public static JCheckBox createCheckBox(final String text,
			final ActionListener action) {
		final JCheckBox checkBox = new JCheckBox(text);
		if (action != null) {
			checkBox.addActionListener(action);
		}
		return checkBox;
	}

}
