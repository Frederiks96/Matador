package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Brewery;
import entity.fields.Tax;

public class TaxTest {

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
		board.landOnField(player1, text, gui);

		int actual = player1.getBalance();
		int expected = 27000;

		assertEquals(actual,expected);
	
	}

	@Test
	public void payTax4000(){
		player1.setPosition(4);
		board.landOnField(player1, text, gui);

		int actual = player1.getBalance();
		int expected = 26000;

		assertEquals(actual,expected);
	}

}




//hvivs det er left button pressed er det false, hvis højre er 10 %







