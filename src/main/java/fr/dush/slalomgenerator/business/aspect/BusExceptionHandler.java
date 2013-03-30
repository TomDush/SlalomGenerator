package fr.dush.slalomgenerator.business.aspect;

import javax.inject.Inject;
import javax.inject.Named;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.events.generic.ExceptionEvent;
import fr.dush.slalomgenerator.events.generic.FunctionalErrorEvent;
import fr.dush.slalomgenerator.exceptions.ViewException;

/**
 * Handle exceptions thrown by bus subscribed method.
 *
 * <p>
 * This methods must NOT throw any exception. If they will, EventBus log exception and thats all : event is lost and error is silent.
 * </p>
 *
 * <p>
 * This aspect catch exception thrown by any of this methods and create event to treat them.
 * </p>
 *
 * @author Thomas Duchatelle
 *
 */
@Named
@Aspect
public class BusExceptionHandler {

	@Inject
	private EventBus bus;

	@Around("@annotation(com.google.common.eventbus.Subscribe) || @within(com.google.common.eventbus.Subscribe)")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {

		try {
			// Execute method (which treat event)
			return pjp.proceed();

		} catch (ViewException e) {
			// If exception, post it in event bus
			bus.post(new FunctionalErrorEvent(pjp.getThis(), e.getMessage()));
			return null;

		} catch (Exception e) {
			// If exception, post it in event bus
			bus.post(new ExceptionEvent(pjp.getThis(), e));
			return null;
		}
	}
}
