package test;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestChanceField {
	
	private Player player1;
	private GameBoard board;
	private Texts text;
	private GUI_Commands gui;
	

	@Before
	public void setUp() throws Exception {
		player1 = new Player("John","Grøn","UFO");
		board = new GameBoard();
		text = new Texts(language.Dansk);
		board.setupBoard(text);
		gui = new GUI_Commands();
	}

	@Test
	public void test() {
		player1.setPosition(2);
		board.createCardDeck(text);
		gui.addPlayer(player1.getName(), player1.getBalance());
		gui.setCar(player1.getPosition(), player1.getName());
		for (int i = 0; i < 35; i++) {
			board.landOnField(player1, text, gui);
			player1.setPosition(2);
			gui.setBalance(player1.getName(), player1.getBalance());
		}
	}

}
