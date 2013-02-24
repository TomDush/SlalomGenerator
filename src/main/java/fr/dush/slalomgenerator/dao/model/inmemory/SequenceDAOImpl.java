package fr.dush.slalomgenerator.dao.model.inmemory;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Maps.*;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import com.google.common.base.Strings;

import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dto.model.Sequence;

@Named
public class SequenceDAOImpl implements ISequenceDAO {

	private Map<String, Sequence> sequences = newHashMap();

	@Override
	public void save(Sequence sequence) {
		checkArgument(!Strings.isNullOrEmpty(sequence.getName()), "Sequence name must be filled.");
		sequences.put(sequence.getName(), sequence);
	}

	@Override
	public void delete(Sequence sequence) {
		sequences.remove(sequence.getName());
	}

	@Override
	public List<Sequence> findAll() {
		return newArrayList(sequences.values());
	}

	@Override
	public void deleteAll() {
		sequences.clear();
	}

	@Override
	public void restore(List<Sequence> sequences) {
		deleteAll();
		for (Sequence s : sequences) {
			save(s);
		}
	}

}
