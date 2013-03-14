package fr.dush.slalomgenerator.dao.utils;

import static com.google.common.collect.Collections2.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.base.Function;
import com.google.common.base.Joiner;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;

@Named
public class FigureListSerializer extends StdSerializer<List<Figure>> {

	@SuppressWarnings("unchecked")
	protected FigureListSerializer() {
		super((Class<List<Figure>>) (Object) Figures.class);
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(FigureListSerializer.class);

	@Override
	public void serialize(List<Figure> figures, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {

		final Collection<String> list = transform(figures, new Function<Figure, String>() {

			@Override
			public String apply(Figure f) {
				return f.getName();
			}
		});

		final String serializedString = Joiner.on(",").join(list);

		LOGGER.info("'{}' serialization of : {}", serializedString, figures);
		jgen.writeString(serializedString);

	}

}
