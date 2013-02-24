package fr.dush.slalomgenerator.dao.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JsonProvider {

	@Bean
	public ObjectMapper provideMapper() {
		ObjectMapper objectMapper = new  ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		return objectMapper;
	}

}
