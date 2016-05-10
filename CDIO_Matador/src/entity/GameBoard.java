package entity;

import java.sql.SQLException;
import java.util.ArrayList;

import boundary.GUI_Commands;
import boundary.SQL;
import entity.dicecup.DiceCup;
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
	private DiceCup dicecup = new DiceCup();



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

	public void loadBoard(Player[] players, GUI_Commands gui, SQL sql) throws SQLException {
		for  (int i = 0; i < this.logicFields.length; i++) {
			if (logicFields[i] instanceof Ownable) {
				if (sql.getOwnerID(i)>0) {
					((Ownable)logicFields[i]).setOwner(players[sql.getOwnerID(i)-1], gui);
				}

				if (sql.isMortgaged(logicFields[i])) {
					((Ownable)logicFields[i]).setMortgage(true);
					if (((Ownable)logicFields[i]) instanceof Fleet) {
						((Ownable)logicFields[i]).getOwner().mortgageFleet();
					} else if (((Ownable)logicFields[i]) instanceof Brewery) {
						((Ownable)logicFields[i]).getOwner().mortgageBrewery();
					}
				}

				if (logicFields[i] instanceof Territory) {
					((Territory)logicFields[i]).setHouseCount(sql.getFieldHouseCount((Territory)logicFields[i]));
					if(sql.getFieldHouseCount((Territory)logicFields[i])==5){
						gui.setHotel(i, true);
					} else {
						gui.setHouse(i, ((Territory)logicFields[i]).getHouseCount());
					}
				}
			}
		}
	}

	public ArrayList<String> getOwnedProperties(Player player) {
		ArrayList<String> properties = new ArrayList<String>();
		for (int i = 0; i < logicFields.length; i++) {
			if (logicFields[i] instanceof Ownable) {
				if (((Ownable)(logicFields[i])).isOwned()) {
					if (((Ownable)(logicFields[i])).getOwner().equals(player)) {
						properties.add(logicFields[i].getName());
					}
				}
			}
		}
		return properties;
	}

	public ArrayList<String> getOwnedUnbuiltProperties(Player player) {
		ArrayList<String> properties = new ArrayList<String>();
		for (int i = 0; i < logicFields.length; i++) {
			if (logicFields[i] instanceof Ownable) {
				if (((Ownable)(logicFields[i])).isOwned()) {
					if (((Ownable)(logicFields[i])).getOwner().equals(player)) {
						if (logicFields[i] instanceof Territory) {
							if (getHouseCount(i)==0) {
								properties.add(logicFields[i].getName());
							}
						} else {
							properties.add(logicFields[i].getName());
						}
					}
				}
			}
		}
		return properties;
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

	public void countBuildings(SQL sql) throws SQLException {
		for (int i = 0; i<40; i++) {
			if (logicFields[i] instanceof Territory) {
				if( sql.getFieldHouseCount((Territory)logicFields[i])  == 5) {
					// HAS A HOTEL
					hotelCount++;
				}
				if(  sql.getFieldHouseCount((Territory)logicFields[i]) < 5) {
					// HAS HOUSES
					houseCount += sql.getFieldHouseCount((Territory)logicFields[i]);
				}
			}
		}
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
		for(int i = 0; i < 40; i++) {
			if (logicFields[i] instanceof Ownable) {
				if (((Ownable)logicFields[i]).isOwned()) {
					sql.setMortgage(i, ((Ownable)(logicFields[i])).isMortgaged()); 
					sql.setOwner(i, ((Ownable)(logicFields[i])).getOwner().getPlayerID());

					if (logicFields[i] instanceof Territory) {
						sql.setHouseCount(i, ((Territory)(logicFields[i])).getHouseCount());
					}
				}
			}
		}
		deck.updateCards(sql);
	}

	public void landOnField(Player player, Texts text, GUI_Commands gui) {
		logicFields[player.getPosition()].landOnField(player, text, gui, this);
	}

	public void createCardDeck(Texts text, SQL sql) throws SQLException {
		deck = new CardStack();
		deck.newDeck(text);
		deck.shuffle();
		deck.createCards(sql);
	}

	public void loadCardDeck(Texts text, SQL sql) throws SQLException {
		deck = new CardStack();
		try {
			deck.loadCards(text, sql);
		} catch (SQLException s) {
			deck.newDeck(text);
		}

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

	public DiceCup getDiceCup(){
		return dicecup;
	}

	public String drawCard(Player player){
		return deck.draw(player);

	}

	public void probateCourt(Player player) {
		ArrayList<String> properties = getOwnedProperties(player);
		






	}

}