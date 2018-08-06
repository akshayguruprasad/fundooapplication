/**locale
 * 
 */
package com.indream.fundoo.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author Akshay
 *
 */
@Configuration
public class I18N implements I18NSpec {

	private Locale locale = Locale.ENGLISH;

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getMessage(String message) {
		ResourceBundleMessageSource messageSource = getSource();
		return messageSource.getMessage(message, null, locale);
	}

	@Bean
	public ResourceBundleMessageSource getSource() {
		ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		bundleMessageSource.addBasenames("message");
		bundleMessageSource.setCacheSeconds(1);
		return bundleMessageSource;
	}

}