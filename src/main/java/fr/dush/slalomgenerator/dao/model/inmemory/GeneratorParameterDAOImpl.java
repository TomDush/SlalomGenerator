package fr.dush.slalomgenerator.dao.model.inmemory;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.google.common.base.Strings;

import fr.dush.slalomgenerator.dao.model.IGeneratorParameterDAO;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;

@Named("generatorParameterDAO")
public class GeneratorParameterDAOImpl implements IGeneratorParameterDAO {

	private Map<String, GeneratorParameter> parameters = newHashMap();

	@Override
	public void save(GeneratorParameter param) {
		checkArgument(Strings.isNullOrEmpty(param.getName()), "GeneratorParameter's name must not be null. (param : %s)", param);
		parameters.put(param.getName(), param);
	}

	@Override
	public void delete(GeneratorParameter param) {
		parameters.remove(param.getName());
	}

	@Override
	public List<GeneratorParameter> findAll() {
		return newArrayList(parameters.values());
	}

	@Override
	public void restore(Collection<GeneratorParameter> parameters) {
		this.parameters = newHashMap();
		for (GeneratorParameter p : parameters) {
			save(p);
		}

	}

}
