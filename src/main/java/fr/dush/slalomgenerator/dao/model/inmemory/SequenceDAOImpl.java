package fr.dush.slalomgenerator.dao.model.inmemory;

import java.io.Serializable;

import javax.inject.Named;

import com.google.common.base.Function;

import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dto.model.Sequence;

@Named("sequenceDAO")
public class SequenceDAOImpl extends GenericDAOImpl<Sequence> implements ISequenceDAO {

	public SequenceDAOImpl() {
		super(new Function<Sequence, Serializable>() {

			@Override
			public Serializable apply(Sequence input) {
				return input.getName();
			}
		});
	}

}
