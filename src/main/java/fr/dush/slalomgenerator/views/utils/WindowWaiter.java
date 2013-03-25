package fr.dush.slalomgenerator.views.utils;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowWaiter extends WindowAdapter implements WindowListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(WindowWaiter.class);

	@Getter
	private boolean finish = false;

	public WindowWaiter(Window rootFrame) {
		rootFrame.addWindowListener(this);
	}

	@Override
	public synchronized void windowClosed(WindowEvent e) {
		LOGGER.debug("Main Window closed {}", e);

		finish = true;
		this.notifyAll();
	}

	public synchronized void waitClosed() throws InterruptedException {
		while (!isFinish()) {
			wait();
		}
	}

}