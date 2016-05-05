package entity;

import java.sql.SQLException;

import boundary.GUI_Commands;
import boundary.SQL;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.ChanceField;
import entity.fields.Fleet;
import entity.fields.Ownable;
import entity.fields.Refuge;
import entity.fields.Tax;
import entity.fields.Territory;

public class GameBoard {

	private int hotelCount;
	private int houseCount; 
	private AbstractFields[] logicFields = new AbstractFields[40];
	private CardStack deck;


	public void setupBoard(Texts text) {
		for  (int i = 0; i < logicFields.length; i++){
			if (i == 5 || i==15 || i==25 || i==35){
				this.logicFields[i] = new Fleet(i,null, text);
			} else if (i == 4 || i == 38){
				this.logicFields[i] = new Tax(i, text);
			} else if (i == 0 || i == 10 || i == 20 || i == 30){ 
				this.logicFields[i] = new Refuge(i, text);
			} else if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36){
				this.logicFields[i] = new ChanceField(i,text);
			} else if (i == 12 || i == 28){
				this.logicFields[i] = new Brewery(i,null, text);
			} else {
				this.logicFields[i] = new Territory(i,null, text);
			}
		}
	}

	public void setupBoard(Texts text, String gameName, Player[] players, GUI_Commands gui, SQL sql) throws SQLException {
		Player owner;
		for  (int i = 0; i < this.logicFields.length; i++){
			if (sql.getOwnerID(i)!=0) {
				owner = players[sql.getOwnerID(i)-1];
			} else {
				owner = null;
			}

			if (i == 5 || i==15 || i==25 || i==35){
				this.logicFields[i] = new Fleet(i,owner, text);
				if (owner!=null) {
					gui.setOwner(i, owner.getName());
				}
			} else if (i == 4 || i == 38){
				this.logicFields[i] = new Tax(i, text);
			} else if (i == 0 || i == 10 || i == 20 || i == 30){ 
				this.logicFields[i] = new Refuge(i, text);
			} else if (i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36){
				this.logicFields[i] = new ChanceField(i,text);
			} else if (i == 12 || i == 28){
				this.logicFields[i] = new Brewery(i,owner, text);
				if (owner!=null) {
					gui.setOwner(i, owner.getName());
				}
			} else {
				this.logicFields[i] = new Territory(i,owner, text);
				if (owner!=null) {
					gui.setOwner(i, owner.getName());
				}
				int house = sql.getFieldHouseCount(((Territory)this.logicFields[i]));
				((Territory) (this.logicFields[i])).setHouseCount(house);
				if (house<4 && house>0){
					gui.setHouse(i, house);
				} else if (house == 5) {
					gui.setHotel(i, true);
				}
			}
		}
	}

	public String[] getOwnedProperties(Player player) {
		int numPropertiesOwned = player.getNumBreweriesOwned()+player.getNumFleetsOwned()+player.getNumTerritoryOwned();
		if (numPropertiesOwned>0) {
			String [] properties = new String[numPropertiesOwned];
			int j = 0;
			for (int i = 0; i < logicFields.length; i++) {
				if (logicFields[i] instanceof Ownable) {
					if (((Ownable)(logicFields[i])).isOwned()) {
						if (((Ownable)(logicFields[i])).getOwner().equals(player)) {
							properties[j] = logicFields[i].getName();
							j++;
						}
					}
				}
			}
			return properties;
		}
		return null;
	}

	public String[] getOwnedUnbuiltProperties(Player player) {
		String[] tempProperties;
		String[] properties = null;
		int numPropertiesOwned = player.getNumBreweriesOwned()+player.getNumFleetsOwned()+player.getNumTerritoryOwned();
		if (numPropertiesOwned>0) {
			tempProperties = new String[numPropertiesOwned];
			int j = 0;
			for (int i = 0; i < logicFields.length; i++) {
				if (logicFields[i] instanceof Ownable) {
					if (((Ownable)(logicFields[i])).isOwned()) {
						if (((Ownable)(logicFields[i])).getOwner().equals(player)) {
							if (logicFields[i] instanceof Territory) {
								if (getHouseCount(i)==0) {
									tempProperties[j] = logicFields[i].getName();
									j++;
								}
							} else {
								tempProperties[j] = logicFields[i].getName();
								j++;
							}
						}
					}
				}
			}

			if (tempProperties.length==1) {
				properties = new String[1];
			} else {
				for (int i = 0; i < tempProperties.length; i++) {
					if (tempProperties[i]==null) {
						properties = new String[i-1];
					}
				}
			}

			for (int i = 0; i < properties.length; i++) {
				properties[i]=tempProperties[i];
			}
			return properties;
		}
		return null;
	}

	public int netWorth(Player player) {
		int totalworth = 0;
		totalworth += player.getBalance();

		for (int i = 0; i < logicFields.length; i++) {
			if(logicFields[i] instanceof Ownable) {
				if (((Ownable)(logicFields[i])).isMortgaged())
					totalworth += (((Ownable)(logicFields[i])).getPrice()*0.5*0.9);
				else {
					totalworth += ((Ownable)(logicFields[i])).getPrice();
					if (logicFields[i] instanceof Territory)
						totalworth += (int) getHouseCount(i)*((Territory)(logicFields[i])).getHousePrice()*0.5;
				}
			}
		}
		return totalworth;
	}

	public void countCountBuildings(){
		//TODO
	}

	public AbstractFields getLogicField(int i) {
		return this.logicFields[i];
	}

	public AbstractFields[] getLogicFields() {
		return this.logicFields;
	}

	public boolean hasAll(Player owner, String COLOUR) {
		int j = 0;
		for (int i = 0; i < logicFields.length; i++) {
			if (logicFields[i] instanceof Territory) {
				if (((Territory) (logicFields[i])).getColour().equals(COLOUR)) {
					if (((Territory) (logicFields[i])).getOwner()!=null) {
						if (((Territory) (logicFields[i])).getOwner().equals(owner))
							j++;
					}
				}
			}
		}
		if (COLOUR.equals("BLUE") || COLOUR.equals("PURPLE")) {
			return j==2;
		}
		return j==3;
	}

	public AbstractFields getProperty(String propertyName) {
		for (int i = 0; i < logicFields.length; i++) {
			if (propertyName.equals(logicFields[i].getName())) {
				return logicFields[i];
			}
		}
		return null;
	}

	public void saveBoard(SQL sql) throws SQLException {
		for(int i = 0; i < logicFields.length; i++){
			if (logicFields[i] instanceof Ownable){
				sql.setMortgage(i, ((Ownable)(logicFields[i])).isMortgaged()); 
				if (logicFields[i] instanceof Territory)
					sql.setHouseCount(i, ((Territory)(logicFields[i])).getHouseCount());
			}
		}
	}

	public void landOnField(Player player, Texts text, GUI_Commands gui) {
		logicFields[player.getPosition()].landOnField(player, text, gui, this);
	}

	public void createCardDeck(Texts text) {
		deck = new CardStack();
		deck.shuffle();
		deck.newDeck(text);
	}

	public void loadCardDeck(Texts text) throws SQLException {
		deck = new CardStack();
		deck.loadCards(text);
	}

	public int getHotelCount(){
		return hotelCount;
	}

	public void addHotel(){
		hotelCount++;
	}

	public void subtractHotel(){
		hotelCount--;
	}

	public int getHouseCount(){
		return houseCount;
	}

	public void addHouse(){
		houseCount++;
	}

	public void subtractHouse(){
		houseCount--;
	}

	public int getHouseCount(int field_id) {
		return ((Territory)logicFields[field_id]).getHouseCount();
	}

	public boolean isOwnable(int field_id) {
		return this.logicFields[field_id] instanceof Ownable;
	}

	public Player getOwner(int field_id) {
		if (isOwnable(field_id)) {
			return ((Ownable)logicFields[field_id]).getOwner();
		}
		return null;
	}

	public void setOwner(int field_id, Player owner, GUI_Commands gui) {
		if (isOwnable(field_id)) {
			if (logicFields[field_id] instanceof Territory) {
				if (getHouseCount(field_id)==0) {
					((Ownable)logicFields[field_id]).setOwner(owner, gui);
				}
			} else {
				((Ownable)logicFields[field_id]).setOwner(owner, gui);
			}
		}
	}


}