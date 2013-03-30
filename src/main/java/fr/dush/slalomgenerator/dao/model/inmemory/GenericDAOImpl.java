package fr.dush.slalomgenerator.dao.model.inmemory;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

import fr.dush.slalomgenerator.dao.model.IGenericDAO;

public abstract class GenericDAOImpl<Type> implements IGenericDAO<Type> {

	@Inject
	private ResourceBundle bundle;

	/** Function to get id from object */
	private final Function<Type, Serializable> idFunction;

	/** Object list */
	private Map<Serializable, Type> datas = newHashMap();

	public GenericDAOImpl(Function<Type, Serializable> idFunction) {
		this.idFunction = idFunction;
	}

	@Override
	public void save(Type figure) {
		// Generate ID
		final Serializable id = idFunction.apply(figure);
		// If id mustn't be null, and if it's String, mustn't be empty.
		checkArgument(id instanceof String ? !Strings.isNullOrEmpty((String) id) : null != id, bundle.getString("error.namenotfilled"));

		// Add value
		datas.put(id, figure);
	}

	@Override
	public void delete(Type figure) {
		datas.remove(idFunction.apply(figure));
	}

	@Override
	public List<Type> findAll() {
		// ** Clear invalid pair
		// Predicate to determine if id is incoherent with object
		final Predicate<Entry<Serializable, Type>> invalidMappingPredicate = new Predicate<Entry<Serializable, Type>>() {

			@Override
			public boolean apply(Entry<Serializable, Type> input) {
				return !input.getKey().equals(idFunction.apply(input.getValue()));
			}
		};

		final Iterable<Entry<Serializable, Type>> idsToDelete = newHashSet(Iterables.filter(datas.entrySet(), invalidMappingPredicate));
		for (Entry<Serializable, Type> entry : idsToDelete) {
			datas.remove(entry.getKey());
		}

		// Return clean results
		return newArrayList(datas.values());
	}

	@Override
	public void deleteAll() {
		datas.clear();
	}

	@Override
	public void restore(List<Type> objects) {
		deleteAll();
		for (Type t : objects) {
			save(t);
		}

	}

}
