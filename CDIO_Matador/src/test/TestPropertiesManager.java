package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestPropertiesManager {

	private Player player1;
	private GameBoard board;
	private GUI_Commands gui;
	private Texts text;

	@Before
	public void setUp() throws Exception {
		player1 = new Player("John","Grøn","Bil");
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		board = new GameBoard();
		board.setupBoard(text);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWishesToBuyHouseButNotAllPropsOwnedOfSameColour() {
		
		
		
		
	}

}
