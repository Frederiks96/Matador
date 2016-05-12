package test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boundary.SQL;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestDB {

	private static SQL sql;
	private static Player player1;
	private static Player player2;
	private GameBoard board;
	private Texts text;

	@BeforeClass // Ét SQL objekt (og dermed én connection) til fælles for hele testklassen, som ikke skal åbnes og lukkes løbende
	public static void setUpBeforeClass() throws Exception {
		sql = new SQL();
		sql.getConnection();
		player1 = new Player("Mikkel","Grøn","Bil");
		player2 = new Player("Lars","Rød","UFO");
	}

	@AfterClass // Connection lukkes efterfølgende 
	public static void tearDownAfterClass() throws Exception {
		sql.closeConnection();
	}

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		board = new GameBoard();
		board.setupBoard(text);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreatePlayer() throws Exception {
		sql.createNewDB("SQLTEST");
		sql.useDB("SQLTEST");
		sql.createPlayer(player1);
		sql.createPlayer(player2);

		// ######################################
		//				Test player1
		// ######################################

		String expectedName = player1.getName();
		String expectedColour = player1.getVehicleColour();
		String expectedType = player1.getVehicleType();
		int expectedBalance = player1.getBalance();
		int expectedPosition = player1.getPosition();
		int expectedJailTime = player1.getJailTime();
		boolean expectedIsAlive = player1.isAlive();
		boolean expectedTurn = player1.isTurn();

		String actualName = null;
		String actualColour = null;
		String actualType = null;
		int actualBalance = -1;
		int actualPosition = -1;
		int actualJailTime = -2;
		boolean actualIsAlive = false;
		boolean actualTurn = true;

		actualName = sql.getPlayerName(player1.getPlayerID());
		actualColour = sql.getVehicleColour(player1.getPlayerID());
		actualType = sql.getVehicleType(player1.getPlayerID());
		actualBalance = sql.getBalance(player1.getPlayerID());
		actualPosition = sql.getPosition(player1.getPlayerID());
		actualJailTime = sql.getJailTime(player1.getPlayerID());
		actualIsAlive = sql.getIsAlive(player1.getPlayerID());
		actualTurn = sql.getTurn(player1.getPlayerID());

		assertEquals(expectedName,actualName);
		assertEquals(expectedColour,actualColour);
		assertEquals(expectedType,actualType);
		assertEquals(expectedBalance,actualBalance);
		assertEquals(expectedPosition,actualPosition);
		assertEquals(expectedJailTime,actualJailTime);
		assertEquals(expectedTurn,actualTurn);
		assertEquals(expectedIsAlive,actualIsAlive);

		// ######################################
		//				Test player2
		// ######################################

		expectedName = player2.getName();
		expectedColour = player2.getVehicleColour();
		expectedType = player2.getVehicleType();
		expectedBalance = player2.getBalance();
		actualBalance = 0;
		actualPosition = -1;
		actualJailTime = -2;
		actualIsAlive = false;
		actualTurn = true;

		actualName = sql.getPlayerName(player2.getPlayerID());
		actualColour = sql.getVehicleColour(player2.getPlayerID());
		actualType = sql.getVehicleType(player2.getPlayerID());
		actualBalance = sql.getBalance(player2.getPlayerID());
		actualPosition = sql.getPosition(player2.getPlayerID());
		actualJailTime = sql.getJailTime(player2.getPlayerID());
		actualIsAlive = sql.getIsAlive(player2.getPlayerID());
		actualTurn = sql.getTurn(player2.getPlayerID());

		assertEquals(expectedName,actualName);
		assertEquals(expectedColour,actualColour);
		assertEquals(expectedType,actualType);
		assertEquals(expectedBalance,actualBalance);
		assertEquals(expectedPosition,actualPosition);
		assertEquals(expectedJailTime,actualJailTime);
		assertEquals(expectedTurn,actualTurn);
		assertEquals(expectedIsAlive,actualIsAlive);
		
		sql.dropDataBase();
	}

	@Test
	public void testSetBalance() throws Exception {
		sql.createNewDB("SQLTEST");
		sql.useDB("SQLTEST");
		sql.createPlayer(player1);
		sql.createPlayer(player2);
		player1.updateBalance(-10);
		sql.setBalance(player1);
		
		int expected = player1.getBalance();
		int actual = sql.getBalance(player1.getPlayerID());
		assertEquals(expected,actual);
		
		expected = player2.getBalance();
		actual = sql.getBalance(player2.getPlayerID());
		assertEquals(expected,actual);
		
		sql.dropDataBase();
	}
	
	@Test
	public void testLoadPlayer() throws Exception {
		sql.createNewDB("SQLTEST");
		sql.useDB("SQLTEST");
		sql.createPlayer(player1);
		sql.createPlayer(player2);
		
		player1.setPosition(10);
		sql.setPosition(player1);
		player1.setBalance(28000);
		sql.setBalance(player1);
		player1.setTurn(true);
		sql.setTurn(player1);
		
		player2.setPosition(12);
		sql.setPosition(player2);
		player2.setBalance(20000);
		sql.setBalance(player2);
		player2.setTurn(true);
		sql.setTurn(player2);
		player2.setJailTime(2);
		sql.setJailTime(player2);
		
		Player player3 = new Player(sql.getPlayerName(1),sql.getVehicleColour(1),sql.getVehicleType(1));
		Player player4 = new Player(sql.getPlayerName(2),sql.getVehicleColour(2),sql.getVehicleType(2));
		
		player3.setPosition(sql.getPosition(1));
		player3.setBalance(sql.getBalance(1));
		player3.setTurn(sql.getTurn(1));
		player4.setPosition(sql.getPosition(2));
		player4.setBalance(sql.getBalance(2));
		player4.setTurn(sql.getTurn(2));
		player4.setJailTime(sql.getJailTime(2));
		
		
		assertEquals(player1.getName(),player3.getName());
		assertEquals(player1.getBalance(),player3.getBalance());
		assertEquals(player1.getJailTime(),player3.getJailTime());
		assertEquals(player1.getNumBreweriesOwned(),player3.getNumBreweriesOwned());
		assertEquals(player1.getNumFleetsOwned(),player3.getNumFleetsOwned());
		assertEquals(player1.getNumTerritoriesOwned(),player3.getNumTerritoriesOwned());
		assertEquals(player1.getPosition(),player3.getPosition());
		
		assertEquals(player2.getName(),player4.getName());
		assertEquals(player2.getBalance(),player4.getBalance());
		assertEquals(player2.getJailTime(),player4.getJailTime());
		assertEquals(player2.getNumBreweriesOwned(),player4.getNumBreweriesOwned());
		assertEquals(player2.getNumFleetsOwned(),player4.getNumFleetsOwned());
		assertEquals(player2.getNumTerritoriesOwned(),player4.getNumTerritoriesOwned());
		assertEquals(player2.getPosition(),player4.getPosition());
		
		sql.dropDataBase();
		
	}
	

}
