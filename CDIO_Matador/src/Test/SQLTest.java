package Test;

import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;

import entity.Player;
import boundary.SQL;

public class SQLTest {

	@Test
	public void test() {
		int a = 0;
		try {
			SQL sql = new SQL();
			Player player = new Player("Mads","green","ufo");
			a = sql.getBalance(player);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(a,10);
		
	}
	
	
	

}
