package entity;

import java.sql.SQLException;

import main.Player;

public interface DAO {
	
	// Data Acces object - getters
	
	int getPosition(Player player) throws SQLException;
	int getBalance()throws SQLException;
	int getJailTime()throws SQLException;
	int getCardId()throws SQLException;
	int getCardPosition()throws SQLException;
	int getFieldId()throws SQLException;
	int getHouseCount()throws SQLException;
	boolean hasHotel()throws SQLException;
	boolean isMortgaged()throws SQLException;
	int getVehicleID()throws SQLException;
	String getVehicleColour()throws SQLException;
	String getVehicleType()throws SQLException;
	int getAccountId()throws SQLException;

}
