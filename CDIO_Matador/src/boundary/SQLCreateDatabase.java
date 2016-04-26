package boundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class SQLCreateDatabase {

	public void executeDBScripts() throws IOException,SQLException {
			BufferedReader in = new BufferedReader(new FileReader("newDB.txt"));
			String str;
			StringBuffer sb = new StringBuffer();
			while ((str = in.readLine()) != null) {
				sb.append(str + "\n ");
			}
			in.close();
			stmt.executeUpdate(sb.toString());
	}
}
