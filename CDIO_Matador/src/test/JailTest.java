package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Refuge;

public class JailTest {

	private Player player1;
	private Player player2;
	private Texts text;
	private GUI_Commands gui;
	private GameBoard board;
	private Refuge refuge; 

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		player1 = new Player("Oliver", "grøn", "bil");
		board = new GameBoard();
		board.setupBoard(text);
	}
	
	@Test
	public void playerGoToJail(){
		player1.setPosition(30);
		board.landOnField(player1, text, gui);
		
		int expected = 10;
		int actual = player1.getPosition();
		
		assertEquals(actual,expected);
	}
	
	
	

}