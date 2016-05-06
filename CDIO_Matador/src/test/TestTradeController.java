package test;

import static org.junit.Assert.*;

import org.junit.After;
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
	public void testTradeControllerTerritory() {
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.addPlayer(player2.getName(), player2.getBalance());
		board.setOwner(1, player1, gui);
		board.setOwner(6, player1, gui);
		board.setOwner(8, player1, gui);
		board.setOwner(9, player1, gui);
		board.setOwner(3, player2, gui);
		trade.suggestDeal(player1, player2, text, board, gui);
		
		assertEquals(board.getOwner(1),player2);
		assertEquals(board.getOwner(3),player1);
		assertEquals(1,player1.getNumTerritoriesOwned());
		assertEquals(4,player2.getNumTerritoriesOwned());
		
	}
	
	@Test
	public void testTradeControllerFleet() {
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.addPlayer(player2.getName(), player2.getBalance());
		board.setOwner(5, player1, gui);
		board.setOwner(15, player2, gui);
		trade.suggestDeal(player1, player2, text, board, gui);
		
		assertEquals(board.getOwner(5),player2);
		assertEquals(board.getOwner(15),player1);
		assertEquals(1,player1.getNumFleetsOwned());
		assertEquals(1,player2.getNumFleetsOwned());
	}
	
	@Test
	public void testTradeControllerBrewery() {
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.addPlayer(player2.getName(), player2.getBalance());
		board.setOwner(12, player1, gui);
		board.setOwner(28, player2, gui);
		trade.suggestDeal(player1, player2, text, board, gui);
		
		assertEquals(board.getOwner(12),player2);
		assertEquals(board.getOwner(28),player1);
		assertEquals(1,player1.getNumBreweriesOwned());
		assertEquals(1,player2.getNumBreweriesOwned());
	}
	
	@Test
	public void testTradeControllerBreweryAndTerritory() {
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.addPlayer(player2.getName(), player2.getBalance());
		
		assertEquals(0,player1.getNumBreweriesOwned());
		assertEquals(0,player1.getNumTerritoriesOwned());
		assertEquals(0,player2.getNumTerritoriesOwned());
		assertEquals(0,player2.getNumBreweriesOwned());
		
		board.setOwner(1, player1, gui);
		board.setOwner(28, player2, gui);
		
		assertEquals(0,player1.getNumBreweriesOwned());
		assertEquals(1,player1.getNumTerritoriesOwned());
		assertEquals(0,player2.getNumTerritoriesOwned());
		assertEquals(1,player2.getNumBreweriesOwned());
		
		trade.suggestDeal(player1, player2, text, board, gui);
		
		assertEquals(board.getOwner(1),player2);
		assertEquals(board.getOwner(28),player1);
		assertEquals(1,player1.getNumBreweriesOwned());
		assertEquals(0,player1.getNumTerritoriesOwned());
		assertEquals(1,player2.getNumTerritoriesOwned());
		assertEquals(0,player2.getNumBreweriesOwned());
	}
	
	@Test
	public void testTradeControllerFleetAndTerritory() {
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.addPlayer(player2.getName(), player2.getBalance());
		board.setOwner(1, player1, gui);
		board.setOwner(5, player2, gui);
		trade.suggestDeal(player1, player2, text, board, gui);
		
		assertEquals(board.getOwner(1),player2);
		assertEquals(board.getOwner(5),player1);
		assertEquals(1,player1.getNumFleetsOwned());
		assertEquals(0,player1.getNumTerritoriesOwned());
		assertEquals(1,player2.getNumTerritoriesOwned());
		assertEquals(0,player2.getNumFleetsOwned());
	}
	
	@After
	public void tearDown() throws Exception {
		for (int i = 0; i < board.getLogicFields().length; i++) {
			gui.removeOwner(i);
		}
	}

}
