				package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import controller.PropertiesController;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Ownable;
import entity.fields.Territory;

public class TerritoryTest {

	private Player player1;
	private Player player2;
	private Texts text;
	private GUI_Commands gui;
	private GameBoard board;
	private PropertiesController manager;

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		gui = new GUI_Commands();
		player1 = new Player("John", "grøn", "bil");
		player2 = new Player("Jens", "gul", "bil");
		board = new GameBoard();
		board.setupBoard(text);
		manager = new PropertiesController();
	}

	@Test
	public void testBuy() {
		player1.setPosition(1);
		board.landOnField(player1, text, gui);
		
		Player expected = this.player1;
		Player actual = board.getOwner(player1.getPosition());
		assertEquals(expected, actual);

		int expectedBalance = 30000 - 1200;
		int actualBalance = player1.getBalance();
		assertEquals(expectedBalance, actualBalance);
	}
	
	@Test
	public void testMortgage() {
		player1.setPosition(1);
		board.landOnField(player1, text, gui);
		
		((Ownable)(board.getLogicField(player1.getPosition()))).mortgage(text, gui);
		
		int expected = 29400;
		int actual = player1.getBalance();
		
		assertEquals(expected, actual);

		boolean expected1 = true;
		boolean actual1 = ((Ownable)(board.getLogicField(player1.getPosition()))).isMortgaged();

		assertEquals(expected1, actual1);
	}

	@Test
	public void testRentNoHouses() {
		player2.setPosition(1);
		board.setOwner(1, player1, gui);
		board.landOnField(player2, text, gui);

		int expected = 30000 - 50;
		int actual = player2.getBalance();
		assertEquals(expected, actual);

		expected = 30000+50;
		actual = player1.getBalance();
		assertEquals(expected, actual);
	}

	@Test
	public void testRentHasAll() {
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		player2.setPosition(1);
		board.landOnField(player2, text, gui);

		int expected = 30000 - 2*50;
		int actual = player2.getBalance();
		assertEquals(expected, actual);

	}

	@Test
	public void testRentOneHouse() {
		gui.showMessage("testRentOneHouse");
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		((Territory) board.getLogicField(1)).setHouseCount(1);
		
		player2.setPosition(1);
		board.landOnField(player2, text, gui);
		
		int expected = 30000 - 250;
		int actual = player2.getBalance();
		assertEquals(expected, actual);
	}

	@Test
	public void testRentTwoHouses() {
		gui.showMessage("testRentTwoHouses");
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		((Territory) board.getLogicField(1)).setHouseCount(2);
		
		player2.setPosition(1);
		board.landOnField(player2, text, gui);
		
		int expected = 30000 - 750;
		int actual = player2.getBalance();
		assertEquals(expected, actual);
	}

	@Test
	public void testRentThreeHouses() {
		gui.showMessage("testRentThreeHouses");
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		((Territory) board.getLogicField(1)).setHouseCount(3);
		
		player2.setPosition(1);
		board.landOnField(player2, text, gui);
		
		int expected = 30000 - 2250;
		int actual = player2.getBalance();
		assertEquals(expected, actual);
	}

	@Test
	public void testRentFourHouses() {
		gui.showMessage("testRentFourHouses");
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		((Territory) board.getLogicField(1)).setHouseCount(4);
		
		player2.setPosition(1);
		board.landOnField(player2, text, gui);
		
		int expected = 30000 - 4000;
		int actual = player2.getBalance();
		assertEquals(expected, actual);
	}

	@Test
	public void testRentHotel() {
		gui.showMessage("testRentHotel");
		board.setOwner(1, player1, gui);
		board.setOwner(3, player1, gui);
		((Territory) board.getLogicField(1)).setHouseCount(5);
		
		player2.setPosition(1);
		board.landOnField(player2, text, gui);
		
		int expected = 30000 - 6000;
		int actual = player2.getBalance();
		assertEquals(expected, actual);
	}

}
