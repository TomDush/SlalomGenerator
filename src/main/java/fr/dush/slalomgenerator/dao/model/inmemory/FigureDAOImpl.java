package fr.dush.slalomgenerator.dao.model.inmemory;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dto.model.Figure;

@Named("figureDao")
public class FigureDAOImpl implements IFigureDAO {

	public Set<Figure> figures = Sets.newHashSet();

	@Override
	public void save(Figure figure) {
		figures.add(figure);
	}

	@Override
	public void delete(Figure figure) {
		figures.remove(figure);
	}

	@Override
	public List<Figure> findAll() {
		return Ordering.from(getFigureComparator()).sortedCopy(figures);
	}

	private static Comparator<Figure> getFigureComparator() {
		return new Comparator<Figure>() {

			@Override
			public int compare(Figure o1, Figure o2) {
				return Ordering.natural().compare(o1.getName(), o2.getName());
			}
		};
	}

	@Override
	public void deleteAll() {
		figures.clear();
	}

	@Override
	public void restore(List<Figure> figures) {
		this.figures = Sets.newCopyOnWriteArraySet(figures);
	}

}
