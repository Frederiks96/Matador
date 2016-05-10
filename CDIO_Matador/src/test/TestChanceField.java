package test;

import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import boundary.SQL;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TestChanceField {
	
	private GameBoard board;
	private Texts text;
	private static SQL sql;
	private Player player1;
	
	@BeforeClass // Ét SQL objekt (og dermed én connection) til fælles for hele testklassen, som ikke skal åbnes og lukkes løbende
	public static void setUpBeforeClass() throws Exception {
		sql = new SQL();
		sql.getConnection();
		sql.createNewDB("JUNITTESTKORT");
		sql.useDB("JUNITTESTKORT");
	}

	@AfterClass // Connection lukkes efterfølgende 
	public static void tearDownAfterClass() throws Exception {
		sql.dropDataBase(); // Smider databasen, således at testen kan køres flere gange, uden at man får fejl
		sql.closeConnection();
	}

	@Before
	public void setUp() throws Exception {
		player1 = new Player("John","Grøn","UFO");
		board = new GameBoard();
		text = new Texts(language.Dansk);
		board.setupBoard(text);
		
	}

	@Test
	public void test() throws SQLException {
		board.createCardDeck(text, sql);
		for (int i = 0; i < 65; i++) {
			System.out.println(board.drawCard(player1));
		}
		System.out.println(board.drawCard(player1));
	}

}
