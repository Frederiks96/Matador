package boundary;

import java.sql.SQLException;

import entity.ChanceCard;
import entity.Player;
import entity.fields.AbstractFields;
import entity.fields.Territory;

public interface DTO {
	
	// Data Transfer Object - Setters
	
	void createPlayer(int id, String name, int position, int jailTime, boolean isActive, int aId, 
			int balance, int vId, String vColor, String vType) throws SQLException;
	void createAccount(int aId, int balance) throws SQLException;
	void createVehicle(int vId, String vColour, String vType) throws SQLException;
	void createChanceCard(ChanceCard card) throws SQLException;
	
	void updatePosition(Player player) throws SQLException;
	void setBalance(Player player) throws SQLException;
	void setJailTime(Player player) throws SQLException;
	void setVehicleID(Player player) throws SQLException; // Ikke nødvendig?
	void setVehicleColour(Player player) throws SQLException;
	void setVehicleType(Player player) throws SQLException;
	void setAccountId(Player player) throws SQLException; // Ikke nødvendig?
	
	
	void setCardId(ChanceCard card) throws SQLException; // Ikke nødvendig?
	void setCardPosition(int position, String card_id) throws SQLException;
	
	void setFieldId(AbstractFields field) throws SQLException; // Ikke nødvendig?
	void setHouseCount(AbstractFields field) throws SQLException;
	void buildHotel(Territory territory) throws SQLException;
	void mortgage(AbstractFields field) throws SQLException;
	
	

}
