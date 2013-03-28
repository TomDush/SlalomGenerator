package fr.dush.slalomgenerator.views;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import fr.dush.slalomgenerator.engine.AbstractJunitTest;

@Ignore("Open window...")
@ContextConfiguration(locations = { "classpath:spring/persistence-mock.xml" })
public class ViewImplTest extends AbstractJunitTest {

	@Inject
	private IView view;

	@Test
	public void testDisplay() throws Exception {
		view.displayHome();

		view.waitEndProgram();
	}
}
