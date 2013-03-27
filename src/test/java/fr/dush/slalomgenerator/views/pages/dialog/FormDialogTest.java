package fr.dush.slalomgenerator.views.pages.dialog;

import static com.google.common.collect.Lists.*;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;

public class FormDialogTest extends AbstractJunitTest {

	@Inject
	private ResourceBundle bundle;

	@Inject
	private EventBus bus;

	@Test
	public void testDialog() throws Exception {
		new FormDialog<Figure>(null, bundle, bus, new Figure(), newArrayList("name", "description", "plotSize", "startingDirection", "inverseDirection",
				"aboutTurn", "probability")).setVisible(true);
	}
}
