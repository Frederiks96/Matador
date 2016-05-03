package test;

import org.junit.*;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import boundary.GUI_Commands;
import boundary.SQL;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Territory;
import entity.fields.Ownable;

public class TerritoryTest {

	private Player player1;
	private Player player2;
	private Territory territory;
	private Texts text;
	SQL sql;
	GUI_Commands gui;
	
	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		territory = new Territory(1, null, text);
		sql = new SQL();
		gui = new GUI_Commands();
		player1 = new Player("John", "grøn", "bil");
		player2 = new Player("Jens", "gul", "bil");
		
	}
	
	
	
	@Test
	public void testBuy(){
		territory.buyProperty(player1, text, gui);
		
		Player expected = this.player1;
		Player actual = territory.getOwner();
		
		assertEquals(expected, actual);
		
		int expectedBalance = 30000 - territory.getPrice();
		int actualBalance = player1.getBalance();
		
		assertEquals(expectedBalance, actualBalance);
		}
	
	@Test
	public void testRent(){
		territory.buyProperty(player1, text, gui);
		territory.landOnField(player2, text, gui);
		
		int expected = 30000 - territory.getRent();
		int actual = player2.getBalance();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMortgage(){
		
		territory.setOwner(player1);
		territory.mortgage(text, gui);
		
		boolean expected = true;
		boolean actual = territory.isMortgaged();
		
		assertEquals(expected, actual);
	}
	
}
