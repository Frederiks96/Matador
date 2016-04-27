package boundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Player;
import entity.fields.Territory;

public class SQL implements DAO, DTO {

	private Connection myCon;
	private String username = "root";
	private String password = "";

	public SQL() throws SQLException {
		this.myCon = DriverManager.getConnection("jdbc:mysql://localhost/Matador",username,password);
	}
	
	public void updateUser(String username, String password) {
		this.username = username;
		this.password = password;
		
	}

	public boolean establishedConnection()throws SQLException{
		return !myCon.isClosed();
	}
	
	
//-------------------------------------
//    >>>>  Data access objects  <<<< 
//-------------------------------------
	public int getPosition(int playerID) throws SQLException { 
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT position FROM player WHERE player_id = '" +playerID+ "'");
		rs.next();
		return rs.getInt(1);
	}
	
	public String getPlayerName(int playerID) throws SQLException {
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT name FROM player WHERE player_id = '"+playerID+"'");
		rs.next();
		return rs.getString(1);
	}

	public int getBalance(int playerID)throws SQLException {
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT balance FROM player NATURAL JOIN bank WHERE playerID = '" +playerID+ "'");
		rs.next();
		return rs.getInt(1);
	}

	public int getJailTime(int playerID) throws SQLException{
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT jail_time FROM player WHERE player_id = '" +playerID+"'");
		rs.next();
		return rs.getInt(1);
	}

	public String getVehicleColour(int playerID)throws SQLException {
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT vehicle_colour FROM player NATURAL JOIN vehicle WHERE player_id = '" +playerID+ "'");
		rs.next();
		return rs.getString(1);
	}

	public String getVehicleType(int playerID) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT vehicle_type FROM player NATURAL JOIN vehicel WHERE player_id = '" +playerID+ "'");
		rs.next();
		return rs.getString(1);
	}
	
	public int getCardId(int position)throws SQLException {
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT card_id FROM chanceCard WHERE position = '" + position + "'");
		rs.next();
		return rs.getInt(1);
	}

	public int getCardPosition(int cardID) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT position FROM chanceCard WHERE card_ID = '" + cardID + "'");
		rs.next();
		return rs.getInt(1);
	}

	
	@Override
	public int getFieldHouseCount(Territory territory) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT house_count FROM Property WHERE field_id = '" + territory.getFieldId()+"'");
		rs.next();
		return rs.getInt(1);
	}

	public boolean hasHotel(Territory territory) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELCET hotel FROM Property WHERE field_id = '" +territory.getFieldId()+"'");
		rs.next();
		return rs.getBoolean(1);
	}

	public boolean isMortgaged(Territory territory)throws SQLException {
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT mortgage FROM Property WHERE field_id = '" + territory.getFieldId()+"'");
		rs.next();
		return rs.getBoolean(1);
	}


	
	
	
// ----------------------------------	
//   >>> Data transfer objects <<<<
// ----------------------------------
	public void updatePosition() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setBalance(Player player)throws SQLException {
		Statement stmt = myCon.createStatement(); 
		stmt.executeUpdate("update bank set balance ="+player.getBalance()+" where account_id="+player.getAccountID());		

	}

	public void setJailTime()throws SQLException {
		// TODO Auto-generated method stub

	}

	public void setCardId() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setCardPosition() throws SQLException{
		// TODO Auto-generated method stub
	}

	public void setFieldId() throws SQLException{
		// TODO Auto-generated method stub
	}

	public void setHouseCount() throws SQLException{
		// TODO Auto-generated method stub
	}

	public void buildHotel()throws SQLException {
		// TODO Auto-generated method stub
	}

	public void mortgage()throws SQLException {
		// TODO Auto-generated method stub
	}

	public void setVehicleID() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setVehicleColour() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setVehicleType() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setAccountId() throws SQLException{
		// TODO Auto-generated method stub

	}


	public void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType) throws SQLException {
		Statement stmt = myCon.createStatement(); 
		createAccount(aId, balance);
		createVehicle(vId, vColor, vType);
		stmt.executeUpdate("insert into player values(" +id+ ","+vId+","+aId+",'"+name+ "',"+position+","
				+ jailTime +"," +isActive+ ");" 
				); 

	}

	public void createAccount(int aId, int balance) throws SQLException {
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("insert into Bank values("+aId+","+balance+");");

	}

	public void createVehicle(int vId, String vColor, String vType) throws SQLException {
		Statement stmt = myCon.createStatement();
		stmt.executeUpdate("insert into Vehicle values("+vId+",'+vColor+','+vType+');");
	}


	public void createNewDB(String dbName) throws IOException,SQLException {
		Statement stmt = myCon.createStatement();
		BufferedReader in = new BufferedReader(new FileReader("newDB.sql"));
		String str;
		StringBuffer sb = new StringBuffer();
		sb.append("create database "+dbName+";");
		while ((str = in.readLine()) != null) {
			sb.append(str + "\n ");
		}
		in.close();
		stmt.executeUpdate(sb.toString());
	}





}