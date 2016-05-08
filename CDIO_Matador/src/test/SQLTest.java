package test;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import entity.Player;
import entity.Texts;
import entity.fields.Territory;
import boundary.SQL;

public class SQLTest {

	Player player;
	Territory territory;
	SQL sql;

	@Before
	public void setUp() throws Exception {
		sql = new SQL();
		sql.useDB("Hej");
		player = new Player("Mads","green","ufo");
	}


	@Test 
	public void getBalancetest() {
		try {
			player.updateBalance(-5000);
			long start = System.currentTimeMillis();
			sql.setBalance(player);
			long finish = System.currentTimeMillis();
			double result = (finish-start);
			System.out.println("The time was: "+result);
			
			int expected = player.getBalance();
			int actual = sql.getBalance(player.getPlayerID());
			
			assertEquals(expected, actual);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void getPositiontest(){
		try{
			player.setPosition(4);
			long start = System.currentTimeMillis();
			sql.getPosition(1);
			long finish = System.currentTimeMillis();
			double result = (finish-start);
			System.out.println("The time was: " + result);
			
			int expected = player.getPosition();
			int actual = sql.getPosition(player.getPlayerID());
			
			assertEquals(expected, actual);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getJailTime(){
		
	}
	
	@Test 
	public void getFromDB() {
		String navn = "";
		String v_colour = "";
		String v_type = "";
		try {
			navn = sql.getPlayerName(1);
			v_colour = sql.getVehicleColour(1);
			v_type = sql.getVehicleType(1);
		} catch (SQLException s) {
			
		}
		assertEquals(navn,"JOHN");
		assertEquals(v_type,"UFO");
		assertEquals(v_colour,"GREEN");
	}

}
//	@Test //House-count Test
//
//	public void houseCountTest(){
//		int a = 0;
//		try{
//			SQL sql = new SQL();
//
//
//			//public Territory(int id, Player owner, Texts text)
//		}
//
//	}
//
//
//}
