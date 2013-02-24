package fr.dush.slalomgenerator.dto.model;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Figures extends ArrayList<Figure> {

	public Figures() {
		super();
	}

	public Figures(Collection<? extends Figure> c) {
		super(c);
	}

	public Figures(int initialCapacity) {
		super(initialCapacity);
	}

	public Figures(Iterable<? extends Figure> c) {
		super();

		for (Figure f : c) {
			add(f);
		}
	}
}
