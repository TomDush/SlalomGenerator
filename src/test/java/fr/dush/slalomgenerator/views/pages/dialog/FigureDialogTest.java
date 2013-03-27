package fr.dush.slalomgenerator.views.pages.dialog;

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
		new FigureDialog(bundle, null, new Figure()).setVisible(true);
	}
}
