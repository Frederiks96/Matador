package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;

public class TestGUI {
	private GUI_Commands gui;

	@Before
	public void setUp() throws Exception {
		gui = new GUI_Commands();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		gui.addPlayer("John", 20);
		gui.addPlayer("Helene", 20);
		gui.setOwner(1, "John");
		Thread.sleep(2000);
		gui.removeOwner(1);
		gui.setOwner(1, "Helene");
		Thread.sleep(4000);
	}

}
