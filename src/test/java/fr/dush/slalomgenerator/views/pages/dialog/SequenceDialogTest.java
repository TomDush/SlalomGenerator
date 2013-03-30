package fr.dush.slalomgenerator.views.pages.dialog;

import static com.google.common.collect.Lists.*;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;

@Ignore("Dev display tests")
public class SequenceDialogTest extends AbstractJunitTest {

	@Inject
	private ResourceBundle bundle;

	@Inject
	private EventBus bus;

	@Test
	public void testDisplaySequence() throws Exception {
		// ** Data preparation
		Figure f1 = new Figure("first");
		Figure f2 = new Figure("second");

		Sequence s = new Sequence("MyName");
		s.setFigures(new Figures(newArrayList(f1, f2, f1)));

		// Display
		new SequenceDialog(bundle, bus, s, true).setVisible(true);
		Thread.sleep(50000);
	}
}
