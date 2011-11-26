package com.project.ircgui.panel.user;

import static com.project.ircgui.factory.ComponentFactory.createLabel;
import static com.project.ircgui.factory.ComponentFactory.createTextField;

import java.awt.Dimension;

import com.project.ircgui.common.CommonPanel;
import com.project.ircgui.factory.ComponentFactory;
import com.project.ircgui.utils.I18nUtils;

public class UserPanel extends CommonPanel {

    private static final long serialVersionUID = 1L;
    private static final String CONNECT = "connect";
    private static final String CHANNEL = "channel";
    private static final String NICKNAME = "nickname";
    private static final String I18N_FILE = "com.project.ircgui.panel.user.UserPanel";
    private static final Dimension DEFAULT_SIZE = new Dimension(100, 50);
    private I18nUtils i18n = new I18nUtils();

    public UserPanel() {
	i18n.setI18n(I18N_FILE);
	init();
    }

    @Override
    protected void init() {
	add(createLabel(i18n.getString(NICKNAME)));
	add(createTextField(DEFAULT_SIZE));
	add(ComponentFactory.createLabel(i18n.getString(CHANNEL)));
	add(createTextField(DEFAULT_SIZE));
	add(ComponentFactory.createButton(CONNECT, new Action()));
    }

    @Override
    protected void onClick() {
    }

}
