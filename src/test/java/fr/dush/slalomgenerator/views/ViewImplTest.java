package fr.dush.slalomgenerator.views;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.slalomgenerator.engine.AbstractJunitTest;


public class ViewImplTest extends AbstractJunitTest{

	@Inject
	private IView view;

	@Test
	public void testDisplay() throws Exception {
		view.displayHome();

		view.waitEndProgram();
	}
}

