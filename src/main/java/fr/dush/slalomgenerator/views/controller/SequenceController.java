package fr.dush.slalomgenerator.views.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.business.generator.IGenerator;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.events.model.GenerateEvent;
import fr.dush.slalomgenerator.events.model.dialog.EditRequestEvent;
import fr.dush.slalomgenerator.exceptions.GeneratorException;
import fr.dush.slalomgenerator.views.pages.dialog.SequenceDialog;

/**
 * Receive {@link GenerateEvent}, generate sequences and display this new sequence, or older sequences.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Named
public class SequenceController {

	@Inject
	private IGenerator generator;

	@Inject
	private EventBus bus;

	@Inject
	private ResourceBundle bundle;

	@Subscribe
	public void generateNewSequence(GenerateEvent event) throws GeneratorException {
		final Sequence sequence = generator.generate((GeneratorParameter) event.getObject());

		new SequenceDialog(bundle, bus, sequence, true).setVisible(true);
	}

	@Subscribe
	public void generateNewSequence(EditRequestEvent event) throws GeneratorException {
		if (Sequence.class.equals(event.getClazz())) {
			new SequenceDialog(bundle, bus, (Sequence) event.getObject(), false).setVisible(true);
		}
	}
}
