package com.irc.server.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * I18nUtils is responsible of
 * 
 * @author mjoanmanuel@gmail.com
 */
public class I18nUtils {

	private static final String DEFAULT_COUNTRY = "ES";
	private static final String DEFAULT_LANGUAGE = "es";

	private ResourceBundle i18n;
	private String fileBundle;

	public I18nUtils(final String fileBundle) {
		this.fileBundle = fileBundle;
		setI18n(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
	}

	/**
	 * @param i18nFile
	 *          must be a valid resource file.
	 * @param locale
	 *          current locale
	 * @param language
	 *          must be es or en or so..
	 * @return
	 */
	public void setI18n(final String language, final String country) {
		Locale locale = Locale.getDefault();

		if (language != null && country != null) {
			locale = new Locale(language, country);
		} else {
			locale = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);
		}

		i18n = ResourceBundle.getBundle(fileBundle, locale);
	}

	public String getString(final String key) {
		return i18n.getString(key);
	}

}
