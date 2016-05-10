package test;

import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import boundary.GUI_Commands;
import static org.junit.Assert.*;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestGetProperties {

	private GUI_Commands gui;
	private Texts text;
	private Player player;
	private String[] aha;
	private GameBoard board;
	
	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		player = new Player("John","Yellow","Ufo");
		aha = new String[28];
		board = new GameBoard();
		board.setupBoard(text);
		gui = new GUI_Commands();
		aha[0] = "Rødovrevej";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws SQLException {
		player.setPosition(1);
		board.landOnField(player, text, gui);
		assertEquals(board.getOwnedProperties(player).get(0),aha[0]);
	}

}
