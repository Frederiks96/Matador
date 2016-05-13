package entity;

import java.sql.SQLException;
import java.util.ArrayList;

import boundary.GUI_Commands;
import boundary.SQL;
import controller.AuctionController;
import controller.Controller;
import entity.dicecup.DiceCup;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.ChanceField;
import entity.fields.Fleet;
import entity.fields.Ownable;
import entity.fields.Refuge;
import entity.fields.Tax;
import entity.fields.Territory;

/**
 * Class that manages the board and fields and delegates the responsibilities of the fields.
 * @author Frederik
 *
 */

public class GameBoard {

	private int hotelCount;
	private int houseCount; 
	private AbstractFields[] logicFields = new AbstractFields[40];
	private CardStack deck;
	private DiceCup dicecup = new DiceCup();
	private Controller con;

	public GameBoard(Controller con) {
		this.con = con;
	}

	public GameBoard() {
	}

	/**
	 * Creates all the fields on the board. If the field is ownable, the owner will be set to null.
	 * 
	 * @param text - The text object that specifies which texts should be stored in the fields.
	 */

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

	/**
	 * Loads the last saved state of a gameboard from the database.  
	 * 
	 * @param players - The player array containing the players of the game
	 * @param gui - GUI object to access methods in the GUI_Commands class
	 * @param sql - SQL class object to retreive the state of the board from the database.
	 * @throws SQLException
	 */

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

	/**
	 * 
	 * Returns an ArrayList of Strings containing the names of the properties that the player owns
	 * 
	 * @param player - The player who's properties should be returned
	 * @return properties
	 */

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

	/**
	 * Returns an ArrayList of Strings containing the names of the properties that are not currently built on, which the player owns.
	 * 
	 * @param player - The player who's properties should be returned
	 * @return properties
	 */

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

	/**
	 * Calculates total net-worth of a player.
	 * This includes his/hers balance and the resale value of properties and buildings.
	 * 
	 * @param player 
	 * @return totalworth
	 */

	public int netWorth(Player player) {
		int totalworth = 0;
		totalworth += player.getBalance();

		for (int i = 0; i < logicFields.length; i++) {
			if(logicFields[i] instanceof Ownable) {
				if (((Ownable)(logicFields[i])).isOwned()) {
					if (((Ownable)(logicFields[i])).getOwner().equals(player)) {
						if (!((Ownable)(logicFields[i])).isMortgaged()) {
							totalworth += ((Ownable)(logicFields[i])).getPrice()*0.5;
							if (logicFields[i] instanceof Territory)
								totalworth += (int) getHouseCount(i)*((Territory)(logicFields[i])).getHousePrice()*0.5;
						}
					}
				}
			}
		}
		return totalworth;
	}

	/**
	 * Calculates the total amount of buildings saved in the database. Differentiating between hotels and houses.
	 * If a field has 5 buildings this corresponds to 1 Hotel and no Houses. 
	 * 
	 * @param sql - The SQL object through which it calculates the total amount of houses and hotels
	 * @throws SQLException
	 */

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

	/**
	 * Returns the field object at field index i
	 * 
	 * @param i - The index of the fields array
	 * @return field
	 */

	public AbstractFields getLogicField(int i) {
		return this.logicFields[i];
	}

	/**
	 * Returns an AbstractFields array containing the fields of the board.
	 * 
	 * @return logicFields
	 */

	public AbstractFields[] getLogicFields() {
		return this.logicFields;
	}

	/**
	 * Checks if a player owns all properties of the same colour.
	 * 
	 * @param owner - The player 
	 * @param COLOUR - The colour of the fields
	 * @return True if he owns all, otherwise false
	 */

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

	/**
	 * Returns an AbstractField object by using the name of the field as an identifier
	 * 
	 * @param propertyName - The name of the field
	 * @return field
	 */

	public AbstractFields getProperty(String propertyName) {
		for (int i = 0; i < logicFields.length; i++) {
			if (propertyName.equals(logicFields[i].getName())) {
				return logicFields[i];
			}
		}
		return null;
	}

	/**
	 * Stores the state of the game board in the database.
	 * This includes saving the properties' owner, whether they are mortgaged and whether they have houses/hotels  
	 * It also includes updating the positions of the cards in the database
	 * @param sql - The SQL object through which the board saves the state in the database
	 * @throws SQLException
	 */

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


	/**
	 * Delegates the chain of events that occurs to a specific field when the player lands on the field.
	 * 
	 * @param player – The player who lands on the field
	 * @param text – The text object that specifies which texts should be shown to the user through the GUI
	 * @param gui – The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice 
	 */

	public void landOnField(Player player, Texts text, GUI_Commands gui) {
		logicFields[player.getPosition()].landOnField(player, text, gui, this);
	}

	/**
	 * Generates a new deck of cards and shuffles them
	 * 
	 * @param text - The Texts object that is used to give the cards a text
	 * @param sql - The SQL object through which the board stores the cards in the database 
	 * @throws SQLException
	 */

	public void createCardDeck(Texts text, SQL sql) throws SQLException {
		deck = new CardStack();
		deck.newDeck(text);
		deck.shuffle();
		deck.createCards(sql);
	}

	/**
	 * Generates a new deck of cards, and loads the position of each card corresponding to the position stored in the database
	 * 
	 * @param text - The Texts object that is used to give the cards a text 
	 * @param sql - The SQL object through which the board loads the cards from the database
	 * @throws SQLException
	 */

	public void loadCardDeck(Texts text, SQL sql) throws SQLException {
		deck = new CardStack();
		try {
			deck.loadCards(text, sql);
		} catch (SQLException s) {
			deck.newDeck(text);
		}

	}

	/**
	 * Returns the amount of hotels currently built on the board
	 * 
	 * @return hotelCount
	 */

	public int getHotelCount(){
		return hotelCount;
	}

	/**
	 * Adds a hotel to the board.
	 */

	public void addHotel(){
		hotelCount++;
	}

	/**
	 * Removes a hotel from the board.
	 */

	public void subtractHotel(){
		hotelCount--;
	}

	/**
	 * Returns the amount of houses currently built on the board.
	 * 
	 * @return houseCount
	 */

	public int getHouseCount(){
		return houseCount;
	}

	/**
	 *  Adds a house to the board.
	 */

	public void addHouse(){
		houseCount++;
	}

	/**
	 *  Removes a house from the board.
	 */

	public void subtractHouse(){
		houseCount--;
	}

	/**
	 * Returns the number of houses built on a specific field
	 * 
	 * @param field_id – The ID of the field
	 * @return houseCount
	 */

	public int getHouseCount(int field_id) {
		return ((Territory)logicFields[field_id]).getHouseCount();
	}

	/**
	 * Checks if the field is an instance of Ownable
	 * 
	 * @param field_id – The ID of the field
	 * @return true if instance of Ownable, otherwise returns false
	 */

	public boolean isOwnable(int field_id) {
		return this.logicFields[field_id] instanceof Ownable;
	}

	/**
	 * Returns the owner of a specific field
	 * 
	 * @param field_id – The ID of the field
	 * @return owner if the field is an instance of Ownable, otherwise null
	 */

	public Player getOwner(int field_id) {
		if (isOwnable(field_id)) {
			return ((Ownable)logicFields[field_id]).getOwner();
		}
		return null;
	}

	/**
	 * Sets a Player as owner of a field. 
	 * It will only set a player as owner of a Territory, if the territory has no houses or hotels.
	 * 
	 * @param field_id – The ID of the field
	 * @param owner - The player who will become owner
	 * @param gui - The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice 
	 */

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

	/**
	 * Returns the dicecup object
	 * @return dicecup
	 */

	public DiceCup getDiceCup(){
		return dicecup;
	}

	/**
	 * Draws a card from the deck, and returns the text from the card
	 * 
	 * @param player - player who draws the card
	 * @return text
	 */

	public String drawCard(Player player){
		return deck.draw(player);

	}

	/**
	 * Lets the player choose which properties he/she wishes to mortgage or sell houses/hotels from. The player will keep selling, until 
	 * he/she has raised enough money, to pay his/hers debt.
	 * 
	 * @param player - the player who is in debt
	 * @param debt - The debt of the player
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 * @param gui - The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice 
	 */

	public void probateCourt(Player player, int debt, Texts text, GUI_Commands gui) {
		gui.showMessage(text.getString("probateCourt"));
		ArrayList<String> properties = getOwnedProperties(player);
		String[] propertiesArr = new String[properties.size()];
		properties.toArray(propertiesArr);
		String choice = "";
		String button = "";
		do {
			do {
				choice = gui.getUserSelection(text.getFormattedString("currentAmount",debt,player.getBalance())+
						" "+text.getString("chooseProperty"), propertiesArr);
			} while (((Ownable)getProperty(choice)).isMortgaged()==true);

			if (getProperty(choice) instanceof Territory) {
				if (((Territory)getProperty(choice)).getHouseCount()>0) {
					button = gui.getUserButtonPressed("", text.getStrings("mortgage","manageBuildings","back"));
				} else {
					button = gui.getUserButtonPressed("", text.getStrings("mortgage","back"));
				}
			} else {
				button = gui.getUserButtonPressed("", text.getStrings("mortgage","back"));
			}

			if (button.equals(text.getString("mortgage"))) {
				((Ownable)getProperty(choice)).mortgage(text, gui);
			} else if (button.equals(text.getString("manageBuildings"))) {
				String sell = gui.getUserButtonPressed("", text.getStrings("house-","hotel-","back"));
				if (sell.equals(text.getString("house-"))) {
					((Territory)getProperty(choice)).sellHouse(gui);
				} else if (sell.equals(text.getString("hotel-"))) {
					((Territory)getProperty(choice)).sellHotel(gui);
				}
			}
		} while (player.getBalance()<debt || button.equals(text.getString("back")));
	}

	/**
	 * Removes all buildings from the territories a certain player owns
	 * 
	 * @param player - owner of the territories
	 * @param gui - The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice 
	 */

	private void removeAllBuildings(Player player, GUI_Commands gui){
		for (int i = 0; i < 40; i++){
			if(logicFields[i] instanceof Territory){
				if (((Territory)logicFields[i]).getOwner() == player){

					while(((Territory)logicFields[i]).getHouseCount() > 0){
						if(((Territory)logicFields[i]).getHouseCount()==5)
							((Territory)logicFields[i]).sellHotel(gui);
						((Territory)logicFields[i]).sellHouse(gui);
					}
				}
			}
		}
	}

	/**
	 * Declares the player as bankrupt and transfers all of value to his creditor. 
	 * 
	 * @param player - The player who goes bankrupt
	 * @param creditor - The player's creditor
	 * @param gui - The GUI_Commands object, that controls the GUI and shows messages to the user and gets the user's choice
	 * @param text - The text object that specifies which texts should be shown to the user through the GUI
	 */

	public void bankrupt(Player player, Player creditor, GUI_Commands gui, Texts text) {
		player.bankrupt();
		if (creditor!=null) {
			removeAllBuildings(player, gui);
			creditor.updateBalance(player.getBalance());
			player.updateBalance(-player.getBalance());

			for (int i = 0; i < 40; i++) {
				if(logicFields[i] instanceof Territory){
					if(((Territory)logicFields[i]).getOwner().equals(player)){
						gui.removeOwner(i);
						((Territory)logicFields[i]).setOwner(creditor, gui);
					}
				}	
			}


		} else {
			removeAllBuildings(player, gui);
			player.updateBalance(-player.getBalance());
			String[] properties = new String[getOwnedProperties(player).size()];
			getOwnedProperties(player).toArray(properties);
			for (int i = 0; i < properties.length; i++) {
				getAuctionController().auction(getPlayers(), getProperty(properties[i]), gui, text);
			}
		}
	}

	/**
	 * Returns the players participating in the game as an array
	 * @return players
	 */

	public Player[] getPlayers() {
		return con.getPlayers();
	}

	/**
	 * Returns the action controller object
	 * @return Auction controller
	 */

	public AuctionController getAuctionController() {
		return con.getAuctionController();
	}

}