package boundary;

import java.io.IOException;
import java.sql.SQLException;

import entity.ChanceCard;
import entity.Player;
import entity.fields.AbstractFields;

public interface DTO {
	
	// Data Transfer Object - "Setters"
	
	void createPlayer(Player player)										 	throws SQLException;
	void createAccount(int aId, int balance) 									throws SQLException;
	void createVehicle(int vId, String vColour, String vType) 					throws SQLException;
	void createChanceCard(ChanceCard card) 										throws SQLException;
//	void createProperties(AbstractFields field);								throws SQLException;
	void setPosition(Player player) 											throws SQLException;
	void setBalance(Player player) 												throws SQLException;
	void setJailTime(Player player) 											throws SQLException;
	void setCardPosition(int position, String card_id) 							throws SQLException;
	void setHouseCount(int field_id, int house_count) 							throws SQLException;
	void setMortgage(int field_id, boolean mortgaged) 							throws SQLException; 
	void setIsAlive(Player player) 												throws SQLException;
	void setTurn(Player player) 												throws SQLException;
	void createNewDB(String dbName)												throws SQLException, IOException;

}
