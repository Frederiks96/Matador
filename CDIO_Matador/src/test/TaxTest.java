package test;

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

		int actualPlayer1Balance = player1.getBalance();
		int expectedPlayer1Balance = 30000 - ((Tax)board.getLogicField(4)).landOnField(player1, chosefine, gui, board);
		
		
	}

}










