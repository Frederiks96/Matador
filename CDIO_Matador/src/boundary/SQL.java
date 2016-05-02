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
import entity.fields.Territory;

public class SQL implements DAO, DTO {

	private static String username = "root";
	private static String password = "";
	private static String dbName;

	public SQL() {
	}

	public void useDB(String dbName) {
		SQL.dbName = "CDIO_"+dbName;
	}

	public void updateUser(String username, String password) {
		SQL.username = username;
		SQL.password = password;
	}

	public void dropDataBase() throws SQLException{
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("DROP DATABASE "+dbName);
		myCon.close();
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

	public int getJailTime(int playerID) throws SQLException {
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

	public String getVehicleType(int playerID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT vehicle_type FROM "+dbName+".player NATURAL JOIN "+dbName+".vehicel WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, playerID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getString(1);
	}

	public int getCardId(int position) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT card_id FROM "+dbName+".chanceCard WHERE position = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, position);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getCardPosition(int cardID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT position FROM "+dbName+".chanceCard WHERE card_ID = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, cardID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public int getFieldHouseCount(Territory territory) throws SQLException {	
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT house_count FROM "+dbName+".property WHERE field_id = " + territory.getID()+";");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}

	public boolean isMortgaged(Territory territory) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT mortgage FROM "+dbName+".property WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, territory.getID());
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getBoolean(1);
	}

	public int getOwner(int fieldID) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "SELECT player_id FROM "+dbName+".property WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(query);
		stmt.setInt(1, fieldID);
		ResultSet rs = stmt.executeQuery();
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}
	
	public int countPlayers() throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery ("SELECT COUNT(player_id) FROM "+dbName+".player");
		myCon.close();
		rs.next();
		return rs.getInt(1);
	}
	
	public String[] getActiveGames() throws SQLException { // Skal denne have prepared statement? ---- er det ikke et DAO????
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
	

	// ------------------------------------------------------------------------------------------------------------------	
	//   										>>> Data transfer objects <<<<
	// ------------------------------------------------------------------------------------------------------------------

	
	public void setPosition(Player player) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".player SET position = ? WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, player.getPosition());
		stmt.setInt(2, player.getPlayerID());
		stmt.executeUpdate();
		myCon.close();
	}

	public void setBalance(Player player) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".bank SET balance = ? WHERE account_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, player.getBalance());
		stmt.setInt(2, player.getAccountID());
		stmt.executeUpdate();
		myCon.close();
	}

	public void setJailTime(Player player) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".player SET jail_time = ? WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, player.getJailTime());
		stmt.setInt(2, player.getPlayerID());
		stmt.executeUpdate();
		myCon.close();
	}

	public void setCardPosition(int position, String card_id) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".chancecard SET position = ? WHERE card_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, position);
		stmt.setString(2, card_id);
		stmt.executeUpdate();
		myCon.close();
	}

	public void setHouseCount(int field_id, int house_count) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".property SET house_count = ? WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, house_count);
		stmt.setInt(2, field_id);
		stmt.executeUpdate();
		myCon.close();
	}

	public void setMortgage(int field_id, boolean mortgaged) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".property SET mortgage = ? WHERE field_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setBoolean(1, mortgaged);
		stmt.setInt(2, field_id);
		stmt.executeUpdate();
		myCon.close();
	}

	public void setIsAlive(Player player) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".player SET is_active = ? WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setBoolean(1, player.isAlive());
		stmt.setInt(2, player.getPlayerID());
		stmt.executeUpdate();
		myCon.close();
	}

	public void setTurn(Player player) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "UPDATE "+dbName+".player SET turn = ? WHERE player_id = ?";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setBoolean(1, player.isTurn());
		stmt.setInt(2, player.getPlayerID());
		stmt.executeUpdate();
		myCon.close();
	}
	
	public void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType, boolean turn) throws SQLException {
		createAccount(aId, balance);
		createVehicle(vId, vColor, vType);
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "INSERT INTO "+dbName+".player VALUES(?,?,?,?,?,?,?,?)";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, id);
		stmt.setInt(2, vId);
		stmt.setInt(3, aId);
		stmt.setString(4, name);
		stmt.setInt(5, position);
		stmt.setInt(6, jailTime);
		stmt.setBoolean(7, isActive);
		stmt.setBoolean(8, turn);
		stmt.executeUpdate();
		myCon.close();
	}

	public void createAccount(int aId, int balance) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "INSERT INTO "+dbName+".bank VALUES(?,?)";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, aId);
		stmt.setInt(2, balance);
		stmt.executeUpdate();
		myCon.close();
	}

	public void createVehicle(int vId, String vColor, String vType) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "INSERT INTO "+dbName+".vehicle VALUES(?,?,?)";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setInt(1, vId);
		stmt.setString(2, vColor);
		stmt.setString(3, vType);
		stmt.executeUpdate();
		myCon.close();
	}

	public void createNewDB(String dbName) throws IOException,SQLException { // Mangler prepared Statement - skal denne have interface?
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String query = "";

		
		
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS CDIO_"+dbName+" DEFAULT CHARACTER SET utf8;");
		stmt.executeUpdate("USE CDIO_"+dbName+";");
		useDB(dbName);
		BufferedReader in = new BufferedReader(new FileReader("newDB.sql"));
		String str;
		while ((str = in.readLine()) != null) {
			if (!str.equals("")) {
				stmt.executeUpdate(str);
			}
		}
		in.close();
		myCon.close();
	}

	public void createChanceCard(ChanceCard card) throws SQLException {
		Connection myCon = DriverManager.getConnection("jdbc:mysql://localhost/",username,password);
		String update = "INSERT INTO "+dbName+".chancecard VALUES(?)";
		java.sql.PreparedStatement stmt = myCon.prepareStatement(update);
		stmt.setString(1, card.getCardID());
		stmt.executeUpdate();
		myCon.close();
	}

	
}