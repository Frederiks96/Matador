package Test;

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
	public void setUp(Texts text) throws Exception {
		player = new Player("Mads","green","ufo");
		territory = new Territory(1,null,text);
		sql = new SQL();
	}


	@Test 
	public void getBalancetest() {
		try {
			player.updateBalance(-5000);
			sql.setBalance(player);
			
			int expected = player.getBalance();
			int actual = sql.getBalance(player);
			
			assertEquals(expected, actual);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
