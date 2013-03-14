package fr.dush.slalomgenerator.dao.utils;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import fr.dush.slalomgenerator.dto.model.Figures;

@Configuration
public class JsonProvider {

	@Inject
	private FigureListSerializer serializer;

	@Inject
	private FigureListDeserializer deserializer;

	@Bean
	public ObjectMapper provideMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

		// Adding serializer
		SimpleModule serializersModule = new SimpleModule("slalomModule", new Version(1, 0, 0, null, null, null));
		serializersModule.addSerializer(serializer);
		serializersModule.addDeserializer(Figures.class, deserializer);

		objectMapper.registerModule(serializersModule);

		return objectMapper;
	}

}
