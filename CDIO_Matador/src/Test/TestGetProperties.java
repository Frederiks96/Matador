package Test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import controller.Controller;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.AbstractFields;

public class TestGetProperties {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws SQLException {
		Texts text = new Texts(language.Dansk);
		Controller con = new Controller();
		Player player = new Player("John","Yellow","Ufo");
		String[] aha = new String[28];
		aha[0] = "Rødovrevej";
		AbstractFields[] fields = con.getFields();
		fields[1].landOnField(player, text);
		assertEquals(con.getOwnedProperties(player)[0],aha[0]);
	}

}
