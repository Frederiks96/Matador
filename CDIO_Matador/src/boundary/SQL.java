package boundary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.DAO;
import controller.DTO;
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


	// >>>> Data acces objects <<<< //

	public int getPosition(Player player) throws SQLException { 
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT position FROM player WHERE player_id = '"+player.getPlayerID()+"'");
		rs.next();
		return rs.getInt(1);
	}
	
	public String getPlayerName(int player_id) throws SQLException {
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT name FROM player WHERE player_id = '"+player_id+"'");
		rs.next();
		return rs.getString(1);
	}

	public int getBalance(Player player)throws SQLException {
		Statement stmt = myCon.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT balance FROM bank WHERE account_id = '" + player.getAccountID()+"'");
		rs.next();
		return rs.getInt(1);
	}

	public int getJailTime() throws SQLException{
		
		return 0;
	}

	public int getCardId()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCardPosition() throws SQLException{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getFieldId()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFieldHouseCount(Territory territory) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("Select house_count from Property where field_id = '" + territory.getFieldId()+"'");
		rs.next();
		return rs.getInt(1);
	}

	public boolean hasHotel(Territory territory) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("Select hotel from Property where field_id = '" +territory.getFieldId()+"'");
		rs.next();
		return rs.getBoolean(1);
	}

	public boolean isMortgaged(Territory territory)throws SQLException {
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("Select mortgage from Property where field_id = '" + territory.getFieldId()+"'");
		rs.next();
		return rs.getBoolean(1);
	}

	public int getVehicleID(Player player) throws SQLException{
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("Select vehicle_id from Player where player_id = '" + player.getPlayerID()+"'");
		rs.next();
		return rs.getInt(1);
	}

	public String getVehicleColour()throws SQLException {
		Statement stmt = myCon.createStatement();
		ResultSet rs = stmt.executeQuery("Select vehicle_id from Player where player_id = '" + player.getPlayerID()+"'");

		
		
		return null;
	}

	public String getVehicleType() throws SQLException{
		// TODO Auto-generated method stub
		return null;
	}

	public int getAccountId()throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	} 

	// ----------------------------------	
	//   >>> Data transfer objects <<<<
	// ----------------------------------

	public void updatePosition() throws SQLException{
		// TODO Auto-generated method stub

	}

	public void setBalance(Player player)throws SQLException {
		Statement stmt = myCon.createStatement(); 
		stmt.executeQuery("update bank set balance ="+player.getBalance()+" where account_id="+player.getAccountID());		

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
		stmt.executeQuery("insert into player values(" +id+ ","+name+ ","+position+","
				+ jailTime +"," +isActive+ ");" 
				); 

	}

	public void createAccount(int aId, int balance) throws SQLException {
		Statement stmt = myCon.createStatement();
		stmt.executeQuery("insert into Bank values("+aId+","+balance+");");

	}

	public void createVehicle(int vId, String vColor, String vType) throws SQLException {
		Statement stmt = myCon.createStatement();
		stmt.executeQuery("insert into Vehicle values("+vId+","+vColor+","+vType+");");
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