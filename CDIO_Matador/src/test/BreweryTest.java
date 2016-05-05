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
import entity.dicecup.DiceCup;
import entity.fields.Brewery;
import entity.fields.Fleet;
import entity.fields.Ownable;

public class BreweryTest {

	private Player player1;
	private Player player2;
	private Texts text;
	private GUI_Commands gui;
	private GameBoard board;
	DiceCup dice;

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		player1 = new Player("Oliver", "grøn", "bil");
		player2 = new Player("Frederik", "blå", "ufo");
		board = new GameBoard();
		board.setupBoard(text);
		dice = new DiceCup();
	}

		@Test
		public void testBuyBrewery(){
			((Brewery)board.getLogicField(12)).buyProperty(player1, text, gui);
			
			Player expected = this.player1;
			Player actual = board.getOwner(12);
			assertEquals(expected, actual);
			
			int expectedBalance = 30000 - ((Brewery)board.getLogicField(12)).getPrice();
			int actualBalance = player1.getBalance();
			assertEquals(expectedBalance, actualBalance);
	}
		
		@Test
		public void payRentBrewery(){
			
			
			
			board.setOwner(12, player1,gui);
			player2.setPosition(4);
			player2.setPosition(12);
			//board.landOnField(player2, text, gui);
			
			
			int actualPlayer1Balance = player1.getBalance();
			int expectedPlayer1Balance = 30000 + ((Brewery)board.getLogicField(12)).getRent(board);
			
			assertEquals(actualPlayer1Balance,expectedPlayer1Balance);
			
			int actualPlayer2Balance = player2.getBalance();
			int expectedPlayer2Balance = 30000 - ((Brewery)board.getLogicField(12)).getRent(board);
			
			assertEquals(actualPlayer2Balance,expectedPlayer2Balance);


		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
}
