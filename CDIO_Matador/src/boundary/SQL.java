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

import com.mysql.jdbc.PreparedStatement;

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

	public void useDB(String dbName) {
		SQL.dbName = "CDIO_"+dbName;
	}
	
	public void updateUser(String username, String password) {
		SQL.username = username;
		SQL.password = password;
	}

// ------------------------------------------------------------------------------------------------------------------	
//										>>> Data access objects <<<<
// ------------------------------------------------------------------------------------------------------------------
	
	
	public int getPosition(int playerID) throws SQLException { 
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT position FROM "+dbName+".player WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public String getPlayerName(int playerID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT name FROM "+dbName+".player WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public int getBalance(int playerID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT balance FROM "+dbName+".player NATURAL JOIN "+dbName+".bank WHERE playerID = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getJailTime(int playerID) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT jail_time FROM "+dbName+".player WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public String getVehicleColour(int playerID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT vehicle_colour FROM "+dbName+".player NATURAL JOIN "+dbName+".vehicle WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public String getVehicleType(int playerID) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT vehicle_type FROM "+dbName+".player NATURAL JOIN "+dbName+".vehicel WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public int getCardId(int position)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT card_id FROM "+dbName+".chanceCard WHERE position = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, position);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getCardPosition(int cardID) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT position FROM "+dbName+".chanceCard WHERE card_ID = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, cardID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getFieldHouseCount(Territory territory) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT house_count FROM "+dbName+".property WHERE field_id = " + territory.getFieldId()+";");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public boolean hasHotel(Territory territory) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELCET hotel FROM "+dbName+".property WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, territory.getFieldId());
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getBoolean(1);
	}

	public boolean isMortgaged(Territory territory)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT mortgage FROM "+dbName+".property WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, territory.getFieldId());
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getBoolean(1);
	}
	
	public int getOwner(int field_id) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT player_id FROM "+dbName+".property WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, field_id);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}


// ------------------------------------------------------------------------------------------------------------------	
//   										>>> Data transfer objects <<<<
// ------------------------------------------------------------------------------------------------------------------
	
	
	public void updatePosition(Player player) throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "UPDATE "+dbName+".player SET position = ? WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, player.getPosition());
		stmt.setInt(2, player.getPlayerID());
		stmt.executeUpdate();
		myCon.close();
	}

	public void setBalance(Player player)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "UPDATE "+dbName+".bank SET balance = ? WHERE account_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, player.getBalance());
		stmt.setInt(2, player.getAccountID());
		stmt.executeUpdate();
		myCon.close();
	}

	public void setJailTime(Player player)throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "UPDATE "+dbName+".player SET jail_time = ? WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, player.getJailTime());
		stmt.setInt(2, player.getPlayerID());
		stmt.executeUpdate();
		myCon.close();
	}


	public void setCardPosition() throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("  ");
		myCon.close();
	}


	public void setHouseCount() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}

	public void buildHotel()throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}

	public void mortgage()throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}


	public void setVehicleColour() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}

	public void setVehicleType() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("");
		myCon.close();
	}




	public void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement(); 
		createAccount(aId, balance);
		createVehicle(vId, vColor, vType);
		stmt.executeUpdate("insert into "+dbName+".player values(" +id+ ","+vId+","+aId+",'"+name+ "',"+position+","
				+ jailTime +"," +isActive+ ");" 
				); 
		myCon.close();
	}

	public void createAccount(int aId, int balance) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("INSERT INTO "+dbName+".bank VALUES("+aId+","+balance+");");
		myCon.close();

	}

	public void createVehicle(int vId, String vColor, String vType) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("INSERT INTO "+dbName+".vehicle VALUES("+vId+",'"+vColor+"','"+vType+"');");
		myCon.close();
	}



	public void createNewDB(String dbName) throws IOException,SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
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
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("INSERT INTO "+dbName+".chancecard VALUES(" + card.getCardID()+");");
		myCon.close();
	}

	public String[] getActiveGames() throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
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