package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import boundary.SQL;
import controller.Controller;
import entity.Player;
import entity.Texts;
import entity.Texts.language;

public class TerritoryTest {

	private Player player1;
	private Player player2;
	private Texts text;
	private SQL sql;
	private GUI_Commands gui;
	private Controller con;
	
	@Before
	public void setUp() throws Exception {
		con = new Controller();
		text = new Texts(language.Dansk);
		sql = new SQL();
		gui = new GUI_Commands();
		player1 = new Player("John", "grøn", "bil");
		player2 = new Player("Jens", "gul", "bil");
		con.setupBoard();
	}
	
	
	
	@Test
	public void testBuy(){
		player1.setPosition(1);
		con.landedOn(player1);
		
		Player expected = this.player1;
		Player actual = con.getOwner(1);
		
		assertEquals(expected, actual);
		
		int expectedBalance = 30000 - con.getPrice(1);
		int actualBalance = player1.getBalance();
		
		assertEquals(expectedBalance, actualBalance);
		}
	
	@Test
	public void testRent(){
		player1.setPosition(1);
		player2.setPosition(1);
		con.landedOn(player1);
		con.landedOn(player2);
		
		int expected = 30000 - con.getRent(1);
		int actual = player2.getBalance();
		
		assertEquals(expected, actual);
	}
	
//	@Test
//	public void testMortgage(){
//		
//		territory.setOwner(player1);
//		territory.mortgage(text, gui);
//		
//		boolean expected = true;
//		boolean actual = territory.isMortgaged();
//		
//		assertEquals(expected, actual);
//	}
	
}
