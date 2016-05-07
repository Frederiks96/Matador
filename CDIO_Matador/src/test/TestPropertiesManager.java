package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import controller.PropertiesController;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestPropertiesManager {

	private Player player1;
	private GameBoard board;
	private GUI_Commands gui;
	private Texts text;
	private PropertiesController prop;

	@Before
	public void setUp() throws Exception {
		player1 = new Player("John","Grøn","Bil");
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		board = new GameBoard();
		board.setupBoard(text);
		prop = new PropertiesController();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWishesToBuyHouseButNotAllPropsOwnedOfSameColour() {
		board.setOwner(1, player1, gui);
		int expected = 0;
		int actual = board.getHouseCount(1);
		
		assertEquals(expected,actual);
		
		prop.manage(gui, player1, text, board);
		
		actual = board.getHouseCount(1);
		assertEquals(expected,actual);
	}
	
	@Test
	public void testWishesToBuyUnEven() {
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		int expected = 0;
		int actual = board.getHouseCount(1);
		assertEquals(expected,actual);
		
		actual = board.getHouseCount(3);
		assertEquals(expected,actual);
		
		prop.manage(gui, player1, text, board);
		
		actual = board.getHouseCount(1);
		expected = 1;
		assertEquals(expected,actual);
	}
	
	

}
