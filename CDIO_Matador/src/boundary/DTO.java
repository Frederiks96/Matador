package boundary;

import java.sql.SQLException;

import entity.ChanceCard;
import entity.Player;
import entity.fields.Territory;

public interface DTO {
	
	// Data Transfer Object - Setters
	
	void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType, boolean turn) throws SQLException;
	void createAccount(int aId, int balance) throws SQLException;
	void createVehicle(int vId, String vColour, String vType) throws SQLException;
	void createChanceCard(ChanceCard card) throws SQLException;
	
	void setPosition(Player player) throws SQLException;
	void setBalance(Player player) throws SQLException;
	void setJailTime(Player player) throws SQLException;
	void setVehicleColour(Player player) throws SQLException;
	void setVehicleType(Player player) throws SQLException;
	void setCardPosition(int position, String card_id) throws SQLException;
	void setHouseCount(int field_id, int house_count) throws SQLException;
	void buildHotel(Territory territory) throws SQLException; 
	void setMortgage(int field_id, boolean mortgaged) throws SQLException; 
	void setIsAlive(Player player) throws SQLException;
	void setTurn(Player player) throws SQLException;	

}
