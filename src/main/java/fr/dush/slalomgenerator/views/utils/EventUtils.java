package fr.dush.slalomgenerator.views.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.AbstractButton;

import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.events.UIEvent;
import fr.dush.slalomgenerator.events.model.ModelEvent;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;
import fr.dush.slalomgenerator.exceptions.EventCreation;

/**
 * Static utilities class initialized by Spring.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Named
public class EventUtils {

	private static EventBus bus;

	@Inject
	public EventUtils(EventBus bus) {
		EventUtils.bus = bus;
	}

	/**
	 * Fire event of specified event type.
	 *
	 * @param source
	 * @param eventClass
	 * @throws EventCreation Invalid event class.
	 */
	public static void fireEvent(Object source, Class<? extends UIEvent> eventClass) throws EventCreation {
		try {
			final UIEvent event = eventClass.newInstance();
			event.setSource(source);

			bus.post(event);
		} catch (Exception e) {
			throw new EventCreation("Can't instanciate " + eventClass + " post by " + source + ".", e);
		}
	}

	/**
	 * Fire model event : event have class argument.
	 *
	 * @param source
	 * @param eventClass
	 * @param clazz
	 */
	public static <Type> void fireEvent(Object source, Class<? extends ModelEvent> eventClass, Class<Type> clazz) {
		try {
			final Constructor<? extends ModelEvent> constructor = eventClass.getConstructor(Class.class);

			final UIEvent event = constructor.newInstance(clazz);
			event.setSource(source);

			bus.post(event);
		} catch (Exception e) {
			throw new EventCreation("Can't instanciate " + eventClass + " post by " + source + ".", e);
		}
	}

	/**
	 * Fire model event : event have class argument.
	 *
	 * @param source
	 * @param eventClass
	 * @param clazz
	 */
	public static <Type> void fireModelObjectEvent(Object source, Class<? extends ModelObjectEvent> eventClass, Type object) {
		try {
			final Constructor<? extends ModelEvent> constructor = eventClass.getConstructor(Object.class);

			final UIEvent event = constructor.newInstance(object);
			event.setSource(source);

			bus.post(event);
		} catch (Exception e) {
			throw new EventCreation("Can't instanciate " + eventClass + " post by " + source + ".", e);
		}
	}

	/**
	 * Connect source to EventBus : when action is performed, event (of expected class) is post to bus.
	 *
	 * @param source
	 * @param eventClass
	 */
	public static void connect(final AbstractButton source, final Class<? extends UIEvent> eventClass) {
		source.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireEvent(source, eventClass);
			}
		});

	}

	/**
	 * Connect source to EventBus : when action is performed, event (of expected class), with model class attribute, is post to bus.
	 *
	 * @param source
	 * @param eventClass
	 */
	public static void connect(final AbstractButton source, final Class<? extends ModelEvent> eventClass, final Class<?> clazz) {
		source.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireEvent(source, eventClass, clazz);
			}
		});

	}

	/**
	 * Connect source to EventBus : when action is performed, post event (of expected class), with model's class and object attributes.
	 *
	 * @param source
	 * @param eventClass
	 */
	public static void connectModelObjectEvent(final AbstractButton source, final Class<? extends ModelObjectEvent> eventClass, final IValueProvider provider) {
		source.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireModelObjectEvent(source, eventClass, provider.getValue());
			}
		});

	}
}
