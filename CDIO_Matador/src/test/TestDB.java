package test;

import static org.junit.Assert.*;

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
	private Player player1;
	private Player player2;
	private GameBoard board;
	private Texts text;

	@BeforeClass // Ét SQL objekt (og dermed én connection) til fælles for hele testklassen, som ikke skal åbnes og lukkes løbende
	public static void setUpBeforeClass() throws Exception {
		sql = new SQL();
		sql.getConnection();
		sql.createNewDB("SQLTEST");
		sql.useDB("SQLTEST");
	}

	@AfterClass // Connection lukkes efterfølgende 
	public static void tearDownAfterClass() throws Exception {
		//sql.dropDataBase(); // Smider databasen, således at testen kan køres flere gange, uden at man får fejl
		sql.closeConnection();
	}

	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		player1 = new Player("Mikkel","Grøn","Bil");
		player2 = new Player("Lars","Rød","UFO");
		board = new GameBoard();
		board.setupBoard(text);
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public void testCreatePlayer() throws SQLException {
//		sql.createPlayer(player1);
//		sql.createPlayer(player2);
//
//		// ######################################
//		//				Test player1
//		// ######################################
//
//		String expectedName = player1.getName();
//		String expectedColour = player1.getVehicleColour();
//		String expectedType = player1.getVehicleType();
//		int expectedBalance = player1.getBalance();
//		int expectedPosition = player1.getPosition();
//		int expectedJailTime = player1.getJailTime();
//		boolean expectedIsAlive = player1.isAlive();
//		boolean expectedTurn = player1.isTurn();
//
//		String actualName = null;
//		String actualColour = null;
//		String actualType = null;
//		int actualBalance = -1;
//		int actualPosition = -1;
//		int actualJailTime = -2;
//		boolean actualIsAlive = false;
//		boolean actualTurn = true;
//
//		actualName = sql.getPlayerName(player1.getPlayerID());
//		actualColour = sql.getVehicleColour(player1.getPlayerID());
//		actualType = sql.getVehicleType(player1.getPlayerID());
//		actualBalance = sql.getBalance(player1.getPlayerID());
//		actualPosition = sql.getPosition(player1.getPlayerID());
//		actualJailTime = sql.getJailTime(player1.getPlayerID());
//		actualIsAlive = sql.getIsAlive(player1.getPlayerID());
//		actualTurn = sql.getTurn(player1.getPlayerID());
//
//		assertEquals(expectedName,actualName);
//		assertEquals(expectedColour,actualColour);
//		assertEquals(expectedType,actualType);
//		assertEquals(expectedBalance,actualBalance);
//		assertEquals(expectedPosition,actualPosition);
//		assertEquals(expectedJailTime,actualJailTime);
//		assertEquals(expectedTurn,actualTurn);
//		assertEquals(expectedIsAlive,actualIsAlive);
//
//		// ######################################
//		//				Test player2
//		// ######################################
//
//		expectedName = player2.getName();
//		expectedColour = player2.getVehicleColour();
//		expectedType = player2.getVehicleType();
//		expectedBalance = player2.getBalance();
//		actualBalance = 0;
//		actualPosition = -1;
//		actualJailTime = -2;
//		actualIsAlive = false;
//		actualTurn = true;
//
//		actualName = sql.getPlayerName(player2.getPlayerID());
//		actualColour = sql.getVehicleColour(player2.getPlayerID());
//		actualType = sql.getVehicleType(player2.getPlayerID());
//		actualBalance = sql.getBalance(player2.getPlayerID());
//		actualPosition = sql.getPosition(player2.getPlayerID());
//		actualJailTime = sql.getJailTime(player2.getPlayerID());
//		actualIsAlive = sql.getIsAlive(player2.getPlayerID());
//		actualTurn = sql.getTurn(player2.getPlayerID());
//
//		assertEquals(expectedName,actualName);
//		assertEquals(expectedColour,actualColour);
//		assertEquals(expectedType,actualType);
//		assertEquals(expectedBalance,actualBalance);
//		assertEquals(expectedPosition,actualPosition);
//		assertEquals(expectedJailTime,actualJailTime);
//		assertEquals(expectedTurn,actualTurn);
//		assertEquals(expectedIsAlive,actualIsAlive);
//	}

	@Test
	public void testSetBalance() throws SQLException {
		sql.createPlayer(player1);
		player1.updateBalance(-10);
		sql.setBalance(player1);
		
		int expected = player1.getBalance();
		int actual = 0;
		
		actual = sql.getBalance(player1.getPlayerID());
		
		assertEquals(expected,actual);
	}

}
