package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import controller.AuctionController;
import controller.Controller;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Ownable;

public class TestAuction {
	
	private GameBoard board;
	private GUI_Commands gui;
	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private Player player5;
	private Player player6;
	private Player[] players;
	private Texts text;
	private AuctionController auction;

	@Before
	public void setUp() throws Exception {
		board = new GameBoard();
		text = new Texts(language.Dansk);
		board.setupBoard(text);
		players = new Player[6];
		player1 = new Player("1","Grøn","UFO"); players[0] = player1;
		player2 = new Player("2","Grøn","UFO"); players[1] = player2;
		player3 = new Player("3","Grøn","UFO"); players[2] = player3;
		player4 = new Player("4","Grøn","UFO"); players[3] = player4;
		player5 = new Player("5","Grøn","UFO"); players[4] = player5;
		player6 = new Player("6","Grøn","UFO"); players[5] = player6;
		gui = new GUI_Commands();
		auction = new AuctionController();
	}

	@After
	public void tearDown() throws Exception {
		gui.removeOwner(1);
	}

//	@Test
//	public void testAuctionAllSayNo() {
//		player1.setPosition(1);
//		board.landOnField(player1, text, gui);
//		auction.auction(players, board.getLogicField(1), gui, text);
//		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),null);
//	}
	
	@Test
	public void testAuctionPlayer1SayYes() {
		player1.setPosition(1);
		board.landOnField(player1, text, gui);
		auction.auction(players, board.getLogicField(1), gui, text);
		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),player1);
	}
	
//	@Test
//	public void testAuctionPlayer2SayYes() {
//		player1.setPosition(1);
//		board.landOnField(player1, text, gui);
//		auction.auction(players, board.getLogicField(1), gui, text);
//		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),player2);
//	}
//	
//	@Test
//	public void testAuctionPlayer3SayYes() {
//		player1.setPosition(1);
//		board.landOnField(player1, text, gui);
//		auction.auction(players, board.getLogicField(1), gui, text);
//		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),player3);
//	}
//	
//	@Test
//	public void testAuctionPlayer4SayYes() {
//		player1.setPosition(1);
//		board.landOnField(player1, text, gui);
//		auction.auction(players, board.getLogicField(1), gui, text);
//		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),player4);
//	}
//	
//	@Test
//	public void testAuctionPlayer5SayYes() {
//		player1.setPosition(1);
//		board.landOnField(player1, text, gui);
//		auction.auction(players, board.getLogicField(1), gui, text);
//		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),player5);
//	}
//	
//	@Test
//	public void testAuctionPlayer6SayYes() {
//		player1.setPosition(1);
//		board.landOnField(player1, text, gui);
//		auction.auction(players, board.getLogicField(1), gui, text);
//		assertEquals(((Ownable)(board.getLogicField(1))).getOwner(),player6);
//	}

}
