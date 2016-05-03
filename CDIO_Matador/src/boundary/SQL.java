package boundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entity.ChanceCard;
import entity.Player;
import entity.fields.AbstractFields;
import entity.fields.Territory;

public class SQL implements DAO, DTO {

	private static String username = "root";
	private static String password = "";
	private static String dbName = "Matador";

	public SQL() throws SQLException {
	}

	public void useDB(String dbName) throws SQLException {
		SQL.dbName = "CDIO_"+dbName;
	}
	
	public void updateUser(String username, String password) {
		SQL.username = username;
		SQL.password = password;
	}

	//-------------------------------------
	//    >>>>  Data access objects  <<<< 
	//-------------------------------------
	public int getPosition(int playerID) throws SQLException { 
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT position FROM player WHERE player_id = '" +playerID+ "'");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public String getPlayerName(int playerID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT name FROM player WHERE player_id = '"+playerID+"'");
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public int getBalance(int playerID)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT balance FROM player NATURAL JOIN bank WHERE playerID = '" +playerID+ "'");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getJailTime(int playerID) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT jail_time FROM player WHERE player_id = '" +playerID+"'");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public String getVehicleColour(int playerID)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT vehicle_colour FROM player NATURAL JOIN vehicle WHERE player_id = '" +playerID+ "'");
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public String getVehicleType(int playerID) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT vehicle_type FROM player NATURAL JOIN vehicel WHERE player_id = '" +playerID+ "'");
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public int getCardId(int position)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT card_id FROM chanceCard WHERE position = '" + position + "'");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getCardPosition(int cardID) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT position FROM chanceCard WHERE card_ID = '" + cardID + "'");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	@Override
	public int getFieldHouseCount(Territory territory) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT house_count FROM Property WHERE field_id = '" + territory.getFieldId()+"'");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public boolean hasHotel(Territory territory) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELCET hotel FROM Property WHERE field_id = '" +territory.getFieldId()+"'");
		myCon.close();
		rs.next();
		return rs.getBoolean(1);
	}

	public boolean isMortgaged(Territory territory)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT mortgage FROM Property WHERE field_id = '" + territory.getFieldId()+"'");
		myCon.close();
		rs.next();
		return rs.getBoolean(1);
	}
	
	public int getOwner(int field_id) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT player_id FROM Property WHERE field_id = " + field_id+";");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}





	// ----------------------------------	
	//   >>> Data transfer objects <<<<
	// ----------------------------------
	public void updatePosition(Player player) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("UPDATE player SET position = " + player.getPosition()+ " WHERE player_id = " + player.getPlayerID());
		myCon.close();
	}

	public void setBalance(Player player)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement(); 
		stmt.executeUpdate("UPDATE bank SET balance = " + player.getBalance()+ " WHERE account_id = " + player.getAccountID());	
		myCon.close();
	}

	public void setJailTime(Player player)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("UPDATE player SET jail_time = " + player.getJailTime()+ " WHERE player_id = " + player.getPlayerID() );
		myCon.close();
	}


	public void setCardPosition() throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("  ");
		myCon.close();
	}


	public void setHouseCount() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}

	public void buildHotel()throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}

	public void mortgage()throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}


	public void setVehicleColour() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}

	public void setVehicleType() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}




	public void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement(); 
		createAccount(aId, balance);
		createVehicle(vId, vColor, vType);
		stmt.executeUpdate("insert into player values(" +id+ ","+vId+","+aId+",'"+name+ "',"+position+","
				+ jailTime +"," +isActive+ ");" 
				); 
		myCon.close();
	}

	public void createAccount(int aId, int balance) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("INSERT INTO Bank VALUES("+aId+","+balance+");");
		myCon.close();

	}

	public void createVehicle(int vId, String vColor, String vType) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("INSERT INTO Vehicle VALUES("+vId+",'"+vColor+"','"+vType+"');");
		myCon.close();
	}



	public void createNewDB(String dbName) throws IOException,SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+SQL.dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS CDIO_"+dbName+" DEFAULT CHARACTER SET utf8;");
		stmt.executeUpdate("USE CDIO_"+dbName+";");
		useDB(dbName);
		BufferedReader in = new BufferedReader(new FileReader("newDB.sql"));
		String str;
		while ((str = in.readLine()) != null) {
			if (!str.equals(""))
				stmt.executeUpdate(str);
		}
		in.close();
		myCon.close();
	}

	public void createChanceCard(ChanceCard card) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("INSERT INTO chancecard VALUES(" + card.getCardID()+");");
		myCon.close();
	}

	public String[] getActiveGames() throws SQLException {
		long start = System.nanoTime();
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE 'CDIO%';");
		ArrayList<String> gamesFromDB = new ArrayList<String>();
		while (rs.next()) {
			gamesFromDB.add(rs.getString(1));
		}
		String[] games = new String[gamesFromDB.size()];
		for (int i = 0; i < gamesFromDB.size(); i++) {
			String str = gamesFromDB.get(i);
			String temp = str.substring(5, str.length());
			games[i] = temp;
		}
		myCon.close();
		long slut = System.nanoTime();
		double result = (slut-start)/1000000;
		System.out.println(result + " milisekunder");
		return games;
	}

	@Override
	public void setVehicleID(Player player) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVehicleColour(Player player) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVehicleType(Player player) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAccountId(Player player) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCardId(ChanceCard card) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFieldId(AbstractFields field) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHouseCount(AbstractFields field) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildHotel(Territory territory) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mortgage(AbstractFields field) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getOwner(AbstractFields field) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}



}