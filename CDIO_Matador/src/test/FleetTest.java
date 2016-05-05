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
import entity.fields.Fleet;
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
		player1 = new Player("Oliver", "grøn", "bil");
		player2 = new Player("Frederik", "blå", "ufo");
		board = new GameBoard();
		board.setupBoard(text);

	}
	
	@Test
	public void testBuyFleet(){
		((Fleet)board.getLogicField(5)).buyProperty(player1, text, gui);
		
		Player expected = this.player1;
		Player actual = board.getOwner(5);
		assertEquals(expected, actual);
		
		int expectedBalance = 30000 - ((Fleet)board.getLogicField(5)).getPrice();
		int actualBalance = player1.getBalance();
		assertEquals(expectedBalance, actualBalance);

	}
	
	@Test
	public void landOnFleetOneOwner(){
		
		
		board.setOwner(5, player1);
		board.landOnField(player2, text, gui);
		
		int actualPlayer1Balance = player1.getBalance();
		int expectedPlayer1Balance = 30000 + ((Fleet)board.getLogicField(5)).getPrice();
		
		assertEquals(actualPlayer1Balance,expectedPlayer1Balance);
		
		int actualPlayer2Balance = player2.getBalance();
		int expectedPlayer2Balance = 29500;
		
		assertEquals(actualPlayer2Balance,expectedPlayer2Balance);

		
		
	}
	
	
	
	

}
