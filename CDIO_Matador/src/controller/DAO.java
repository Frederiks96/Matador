package controller;

import java.sql.SQLException;

import entity.Player;
import entity.fields.Territory;

public interface DAO {
	
	// Data Acces object - getters
	
	int getPosition(Player player) throws SQLException;
	String getPlayerName(int player_id) throws SQLException;
	int getBalance(Player player)throws SQLException;
	int getJailTime()throws SQLException;
	int getCardId()throws SQLException;
	int getCardPosition()throws SQLException;
	int getFieldId()throws SQLException;
	int getFieldHouseCount(Territory territory)throws SQLException;
	boolean hasHotel(Territory territory)throws SQLException;
	boolean isMortgaged(Territory territory)throws SQLException;
	int getVehicleID(Player player)throws SQLException;
	String getVehicleColour()throws SQLException;
	String getVehicleType()throws SQLException;
	int getAccountId()throws SQLException;

}
