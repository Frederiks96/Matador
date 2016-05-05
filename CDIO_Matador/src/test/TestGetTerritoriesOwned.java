package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestGetTerritoriesOwned {
	
	private GUI_Commands gui;
	private Player player1;
	private GameBoard board;
	private Texts text;

	@Before
	public void setUp() throws Exception {
		gui = new GUI_Commands();
		player1 = new Player("John","Green","UFO");
		board = new GameBoard();
		text = new Texts(language.Dansk);
		board.setupBoard(text);
	}
	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		player1.setPosition(1);
		board.landOnField(player1, text, gui);
		player1.setPosition(3);
		board.landOnField(player1, text, gui);
		player1.setPosition(6);
		board.landOnField(player1, text, gui);
		gui.getUserSelection("Vælg din grunde", board.getOwnedProperties(player1));
	}

}
