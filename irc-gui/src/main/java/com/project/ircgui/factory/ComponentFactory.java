package com.project.ircgui.factory;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * ComponentFactory is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public final class ComponentFactory {

    public static JLabel createLabel(final String text) {
	return new JLabel(text);
    }

    public static JComboBox createComboBox(final Object... items) {
	return new JComboBox(items);
    }

    public static JTextField createTextField() {
	final JTextField textField = new JTextField();
	return textField;
    }

    public static JTextField createTextField(final Dimension size) {
	final JTextField textField = new JTextField();
	textField.setSize(size);
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
