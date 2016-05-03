package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import controller.Controller;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.AbstractFields;

public class TestGetTerritoriesOwned {
	
	Controller con;
	GUI_Commands gui;
	Player player1;
	GameBoard board;
	Texts text;
	AbstractFields[] fields;

	@Before
	public void setUp() throws Exception {
		con = new Controller();
		gui = new GUI_Commands();
		player1 = new Player("John","Green","UFO");
		board = new GameBoard();
		text = new Texts(language.Dansk);
		board.setupBoard(text);
		fields = board.getLogicFields();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fields[1].landOnField(player1, text, gui);
		fields[3].landOnField(player1, text, gui);
		fields[6].landOnField(player1, text, gui);
		gui.getUserSelection("Vælg din grunde", con.getTerritoriesOwned(player1,fields));
	}

}
