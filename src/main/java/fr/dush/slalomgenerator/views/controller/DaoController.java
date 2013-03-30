package fr.dush.slalomgenerator.views.controller;

import java.awt.Window;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dao.model.IGeneratorParameterDAO;
import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmDeleteEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmEditEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmNewEvent;
import fr.dush.slalomgenerator.exceptions.ViewException;

@Named
public class DaoController {

	@Inject
	private IFigureDAO figureDAO;

	@Inject
	private IGeneratorParameterDAO generatorParameterDAO;

	@Inject
	private ISequenceDAO sequenceDAO;

	/**
	 * Call DAO when received edition confirmed event
	 *
	 * @param event
	 * @throws ViewException
	 */
	@Subscribe
	public void saveEditedObject(ConfirmEditEvent event) throws ViewException {
		saveObject(event);
	}

	/**
	 * Call DAO when received new confirmed event
	 *
	 * @param event
	 * @throws ViewException
	 */
	@Subscribe
	public void saveEditedObject(ConfirmNewEvent event) throws ViewException {
		saveObject(event);
	}

	@Subscribe
	public void deleteObject(ConfirmDeleteEvent event) {
		// DAO updated : can close dialog
		boolean ok = false;

		if (Figure.class.equals(event.getClazz())) {
			figureDAO.delete((Figure) event.getObject());
			ok = true;
		}

		if (GeneratorParameter.class.equals(event.getClazz())) {
			generatorParameterDAO.delete((GeneratorParameter) event.getObject());
			ok = true;
		}

		if (event.getObject() instanceof Sequence) {
			sequenceDAO.delete((Sequence) event.getObject());
			ok = true;
		}

		// Close dialog.
		closeWindow(event, ok);
	}

	private void saveObject(ModelObjectEvent event) {
		// DAO updated : can close dialog
		boolean ok = false;

		try {
			if (event.getObject() instanceof Figure) {
				figureDAO.save((Figure) event.getObject());
				ok = true;
			}

			if (event.getObject() instanceof GeneratorParameter) {
				generatorParameterDAO.save((GeneratorParameter) event.getObject());
				ok = true;
			}

			if (event.getObject() instanceof Sequence) {
				sequenceDAO.save((Sequence) event.getObject());
				ok = true;
			}

		} catch (IllegalArgumentException e) {
			// If illegal arg, it's because name isn't filled. Wrapped in ViewException to be treat by handler as functional exception.
			throw new ViewException(e.getMessage(), e);
		}

		// Close dialog.
		closeWindow(event, ok);
	}

	private void closeWindow(ModelObjectEvent event, boolean ok) {
		if (ok && event.getSource() instanceof Window) {
			((Window) event.getSource()).dispose();
		}
	}
}
