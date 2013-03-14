package fr.dush.slalomgenerator.dao.utils;

import java.io.IOException;

import javax.inject.Named;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;

@SuppressWarnings("serial")
@Named
public class FigureListDeserializer extends StdDeserializer<Figures> {

	public FigureListDeserializer() {
		super(Figures.class);
	}

	@Override
	public Figures deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		Iterable<String> figureNames = Splitter.on(",").omitEmptyStrings().trimResults().split(jp.getText());

		return new Figures(Iterables.transform(figureNames, new Function<String, Figure>() {

			@Override
			public Figure apply(String input) {
				return new Figure(input);
			}
		}));
	}

}
