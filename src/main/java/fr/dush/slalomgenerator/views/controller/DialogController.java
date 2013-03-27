package fr.dush.slalomgenerator.views.controller;

import static com.google.common.collect.Lists.*;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.events.model.ModelEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmDeleteEvent;
import fr.dush.slalomgenerator.events.model.dialog.CreateRequestEvent;
import fr.dush.slalomgenerator.events.model.dialog.DeleteRequestEvent;
import fr.dush.slalomgenerator.events.model.dialog.EditRequestEvent;
import fr.dush.slalomgenerator.exceptions.ViewException;
import fr.dush.slalomgenerator.views.pages.dialog.FormDialog;

@Named
public class DialogController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DialogController.class);

	@Inject
	private EventBus bus;

	@Inject
	private ResourceBundle bundle;

	@Subscribe
	public void displayEditDialog(EditRequestEvent event) throws ViewException {
		LOGGER.debug("displayEditDialog (event = {})", event);

		displayDialog(event, event.getObject());
	}

	@Subscribe
	public void displayNewDialog(CreateRequestEvent event) throws ViewException, InstantiationException, IllegalAccessException {
		displayDialog(event, event.getClazz().newInstance());
	}

	@Subscribe
	public void displayDeleteDialog(DeleteRequestEvent event) throws ViewException {
		String question = bundle.getString("dialog.delete.confirm." + event.getClazz().getSimpleName().toLowerCase()) + " : ";

		if (event.getObject() instanceof Figure) question += ((Figure) event.getObject()).getName();
		if (event.getObject() instanceof GeneratorParameter) question += ((GeneratorParameter) event.getObject()).getName();
		if (event.getObject() instanceof Sequence) question += ((Sequence) event.getObject()).getName();

		// Ask before display
		final int confirm = JOptionPane.showConfirmDialog(null, question);
		if (JOptionPane.OK_OPTION == confirm) {
			bus.post(new ConfirmDeleteEvent(this, event.getObject()));
		}
	}

	/**
	 * Configure and display dialog for {@link Figure} or {@link GeneratorParameter}.
	 *
	 * @param event
	 * @param objectToEdit
	 */
	private void displayDialog(ModelEvent event, Object objectToEdit) {

		// ** If display Figure
		if (Figure.class.equals(event.getClazz())) {
			final FormDialog<Figure> dialog = new FormDialog<Figure>(null, bundle, bus, (Figure) objectToEdit, newArrayList("name", "description", "plotSize",
					"startingDirection", "inverseDirection", "aboutTurn", "probability"));
			dialog.setNewObject(event instanceof CreateRequestEvent);
			dialog.setVisible(true);
		}

		// ** If display Generator
		if (GeneratorParameter.class.equals(event.getClazz())) {
			final FormDialog<GeneratorParameter> dialog = new FormDialog<GeneratorParameter>(null, bundle, bus, (GeneratorParameter) objectToEdit, newArrayList(
					"name", "plotNumber", "turnbackNumber", "turnbackInLastPlot", "maxFigures"));
			dialog.setNewObject(event instanceof CreateRequestEvent);
			dialog.setVisible(true);
		}
	}

}
