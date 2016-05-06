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
		sql.dropDataBase(); // Smider databasen, således at testen kan køres flere gange, uden at man får fejl
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

	@Test
	public void testCreatePlayer() {
		try {
			sql.createPlayer(player1);
			sql.createPlayer(player2);
			System.out.println("Create succeeded");
		} catch (SQLException s) {
			System.out.println("SQLException i createPlayer");
		}
		
		String expected = player1.getName();
		String actual = "";
		
		try {
		actual = sql.getPlayerName(player1.getPlayerID());
		System.out.println("Get 1 succeeded");
		} catch (SQLException s) {
			System.out.println("SQLException i getPlayerName nr. 1");
		}
		
		assertEquals(expected,actual);
		
		expected = player2.getName();
		
		try {
			actual = sql.getPlayerName(player2.getPlayerID());
			System.out.println("Get 2 succeeded");
		} catch (SQLException s) {
			System.out.println("SQLException i getPlayerName nr. 1");
		}
		
		assertEquals(expected,actual);
	}

}
