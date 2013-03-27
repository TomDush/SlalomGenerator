package fr.dush.slalomgenerator.business.event;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

/**
 * Controllers are proxyfied to catch @Subscribe methods. Thus, they mustn't register themself. This object register all classes which have @Subscribe
 * annotation.
 *
 * @author Thomas Duchatelle
 */
@Named
public class EventListenersRegister {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventListenersRegister.class);

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private EventBus bus;

	@PostConstruct
	public void registerAll() throws IOException {
		LOGGER.debug("Starting to register classes...");

		// Find all project classes.
		ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
		ImmutableSet<ClassInfo> classes = classPath.getTopLevelClassesRecursive("fr.dush.slalomgenerator");

		// For each class, subscribe it if it has Subscribe method
		for (ClassInfo clazzInfo : classes) {
			final Class<?> clazz = clazzInfo.load();
			if (isListener(clazz)) {

				if (clazz.isAnnotationPresent(Named.class)) {

					try {
						for (Object bean : applicationContext.getBeansOfType(clazz).values()) {
							bus.register(bean);
						}
					} catch (BeansException e) {
						LOGGER.warn("{} is not spring bean.", clazz, e);
					}

				} else {
					LOGGER.info("{} must be proxyfied...", clazz);
					// TODO proxified other methods...
				}
			}
		}
	}

	/**
	 * Test if at least one method has @Subscribe annotation
	 *
	 * @param clazz
	 * @return
	 */
	private boolean isListener(Class<?> clazz) {
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(Subscribe.class)) return true;
		}

		return false;
	}

}
