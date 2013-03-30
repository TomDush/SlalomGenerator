package fr.dush.slalomgenerator.dao.model.inmemory;

import static com.google.common.base.Preconditions.*;

import java.io.Serializable;

import javax.inject.Named;

import com.google.common.base.Function;
import com.google.common.base.Strings;

import fr.dush.slalomgenerator.dao.model.IGeneratorParameterDAO;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;

@Named("generatorParameterDAO")
public class GeneratorParameterDAOImpl extends GenericDAOImpl<GeneratorParameter> implements IGeneratorParameterDAO {

	public GeneratorParameterDAOImpl() {
		super(new Function<GeneratorParameter, Serializable>() {

			@Override
			public Serializable apply(GeneratorParameter input) {
				return input.getName();
			}
		});
	}

	@Override
	public void save(GeneratorParameter param) {
		checkArgument(!Strings.isNullOrEmpty(param.getName()), "GeneratorParameter's name must not be null. (param : %s)", param);
		super.save(param);
	}

}
