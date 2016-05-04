package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import boundary.SQL;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Ownable;

public class TerritoryTest {

	private Player player1;
	private Player player2;
	private Texts text;
	private SQL sql;
	private GUI_Commands gui;
	private GameBoard board;
	
	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		sql = new SQL();
		gui = new GUI_Commands();
		player1 = new Player("John", "grøn", "bil");
		player2 = new Player("Jens", "gul", "bil");
		board = new GameBoard();
		board.setupBoard(text);
	}
	
	
	
	@Test
	public void testBuy(){
		player1.setPosition(1);
		board.landOnField(player1, text, gui);
		
		Player expected = this.player1;
		Player actual = board.getOwner(player1.getPosition());
		
		assertEquals(expected, actual);
		
		int expectedBalance = 30000 - ((Ownable)(board.getLogicField(player1.getPosition()))).getPrice();
		int actualBalance = player1.getBalance();
		
		assertEquals(expectedBalance, actualBalance);
		}
	
	@Test
	public void testRent(){
		player1.setPosition(1);
		player2.setPosition(1);
		board.landOnField(player1, text, gui);
		board.landOnField(player2, text, gui);
		
		int expected = 30000 - ((Ownable)(board.getLogicField(player1.getPosition()))).getRent();
		int actual = player2.getBalance();
		assertEquals(expected, actual);
		
		expected = 30000 + ((Ownable)(board.getLogicField(player1.getPosition()))).getRent() - ((Ownable)(board.getLogicField(player1.getPosition()))).getPrice();
		actual = player1.getBalance();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMortgage(){
		player1.setPosition(1);
		board.landOnField(player1, text, gui);
		((Ownable)(board.getLogicField(player1.getPosition()))).mortgage(text, gui);
		
		boolean expected = true;
		boolean actual = ((Ownable)(board.getLogicField(player1.getPosition()))).isMortgaged();
		
		assertEquals(expected, actual);
	}
	
}
