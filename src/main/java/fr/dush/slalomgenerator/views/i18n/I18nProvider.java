package fr.dush.slalomgenerator.views.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class I18nProvider {


	@Bean
	public ResourceBundle getLocalBundle() {
		Locale locale = Locale.getDefault();

		return ResourceBundle.getBundle("slalom-generator", locale);
	}

}
