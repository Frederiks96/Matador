package Test;

import org.junit.*;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
	
	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		territory = new Territory(1, null, text);
		player1 = new Player("John", "grøn", "bil", sql);
		player2 = new Player("Jens", "gul", "bil", sql);
		
	}
	
	
	
	@Test
	public void testBuy(){
		territory.buyProperty(player1, text);
		territory.landOnField(player2, text);
		
		Player expected = this.player1;
		Player actual = territory.getOwner();
		
		assertEquals(expected, actual);
		
		int expectedBalance = 30000 - territory.getRent();
		int actualBalance = player1.getBalance();
		
		assertEquals(expectedBalance, actualBalance);
		
		int expectedAfterPayment = 30000 - territory.getRent();
		}
	
	@Test
	public void testMortgage(){
		
		territory.setOwner(player1);
		territory.mortgage(text);
		
		boolean expected = true;
		boolean actual = territory.isMortgaged();
		
		assertEquals(expected, actual);
	}
	
}
