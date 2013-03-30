package fr.dush.slalomgenerator.dao.model.inmemory;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import javax.inject.Named;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dto.model.Figure;

@Named("figureDao")
public class FigureDAOImpl extends GenericDAOImpl<Figure> implements IFigureDAO {

	public FigureDAOImpl() {
		super(new Function<Figure, Serializable>() {

			@Override
			public Serializable apply(Figure input) {
				return input.getName();
			}
		});
	}

	@Override
	public List<Figure> findAll() {
		return Ordering.from(getFigureComparator()).sortedCopy(super.findAll());
	}

	private static Comparator<Figure> getFigureComparator() {
		return new Comparator<Figure>() {

			@Override
			public int compare(Figure o1, Figure o2) {
				return Ordering.natural().compare(o1.getName(), o2.getName());
			}
		};
	}

}
