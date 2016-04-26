package Test;

import org.junit.*;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.Territory;
import entity.fields.Ownable;

public class TerritoryTest {

	private Player player1;
	private Territory territory;
	private Texts text;
	
	@Before
	public void setUp() throws Exception {
		text = new Texts(language.Dansk);
		territory = new Territory(1, null, text);
		player1 = new Player("John", "grøn", "bil");
		
	}
	
	@Test
	public void testBuy(){
		territory.buyProperty(player1, text);
		
		Player expected = this.player1;
		Player actual = territory.getOwner();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMortgage(){
		territory.mortgage(text);
		
		boolean expected = true;
		boolean actual = territory.isMortgaged();
		
		assertEquals(expected, actual);
	}
	
}
