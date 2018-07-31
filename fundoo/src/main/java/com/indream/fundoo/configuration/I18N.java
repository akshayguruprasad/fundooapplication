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
public class I18N {

	private Locale locale = Locale.ENGLISH;

	final public String getMessage(String message) {
		ResourceBundleMessageSource messageSource = getSource();
		return messageSource.getMessage(message, null, locale);
	}

	final public void setLocaleValue(Locale locale) {
		this.locale = locale;
	}

	@Bean
	public ResourceBundleMessageSource getSource() {
		ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
		bundleMessageSource.addBasenames("message");
		bundleMessageSource.setCacheSeconds(1);
		return bundleMessageSource;
	}

}