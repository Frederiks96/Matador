package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import controller.PropertiesController;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Ownable;

public class FleetTest {

	private Player player1;
	private Player player2;
	private Texts text;
	private GUI_Commands gui;
	private GameBoard board;

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		player1 = new Player("John", "grøn", "bil");
		board = new GameBoard();
		board.setupBoard(text);

	}
	
	@Test
	public void testBuyFleet(){
		player1.setPosition(5);
		board.landOnField(player1, text, gui);
		
		Player expected = this.player1;
		Player actual = board.getOwner(player1.getPosition());
		assertEquals(expected, actual);

		int expectedBalance = 30000 - ((Ownable)(board.getLogicField(player1.getPosition()))).getPrice();
		int actualBalance = player1.getBalance();
		assertEquals(expectedBalance, actualBalance);
	}
	
	

}
