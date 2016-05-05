package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import controller.TradeController;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestTradeController {
	
	private Player player1;
	private Player player2;
	private GameBoard board;
	private GUI_Commands gui;
	private Texts text;
	private TradeController trade;
	

	@Before
	public void setUp() throws Exception {
		player1 = new Player("John","Rød","UFO");
		player2 = new Player("Jacob","Grøn","Bil");
		board = new GameBoard();
		gui = new GUI_Commands();
		text = new Texts(language.Dansk);
		trade = new TradeController();
		board.setupBoard(text);
	}

	@Test
	public void testTradeController() throws InterruptedException {
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.addPlayer(player2.getName(), player2.getBalance());
		Thread.sleep(4000);
		board.setOwner(1, player1, gui);
		player1.addTerritory();
		board.setOwner(3, player2, gui);
		player2.addTerritory();
		trade.suggestDeal(player1, player2, text, board, gui);
		
		assertEquals(board.getOwner(1),player2);
		assertEquals(board.getOwner(3),player1);
		
	}

}
