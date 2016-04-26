package Test;

import org.junit.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.fields.Territory;
import entity.fields.Ownable;

public class TerritoryTest {

	private Player player1;
	private Territory territory;
	
	@Before
	public void setUp(Texts text) throws Exception {
				
		territory = new Territory(1, text);
		player1 = new Player("John", "grøn", "bil");
		
	}
	
	@Test
	public void testBuy(){
		territory.buyProperty(player1);
		
		Object expected = this.player1;
		Object actual = territory.getOwner();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testMortgage(){
		
	}
	
}
