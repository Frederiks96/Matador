package test;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.dicecup.DiceCup;
import entity.fields.Brewery;

public class taxTest {

	private Player player1;
	private Texts text;
	private GUI_Commands gui;
	private GameBoard board;

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		player1 = new Player("Oliver", "grøn", "bil");
		board = new GameBoard();
		board.setupBoard(text);

	}
	
	
	@Test
	public void payTax10(){
		player1.setPosition(4);
		board.landOnField(player, text, gui);
		
	}

}










