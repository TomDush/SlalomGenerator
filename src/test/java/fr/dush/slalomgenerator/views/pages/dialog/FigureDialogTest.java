package fr.dush.slalomgenerator.views.pages.dialog;

import static com.google.common.collect.Lists.*;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.junit.Test;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;

public class FigureDialogTest extends AbstractJunitTest {

	@Inject
	private ResourceBundle bundle;

	@Test
	public void testDialog() throws Exception {
		new FigureDialog<Figure>(bundle, null, new Figure(), newArrayList("name", "description", "plotSize", "startingDirection", "inverseDirection", "aboutTurn")).setVisible(true);
	}
}
