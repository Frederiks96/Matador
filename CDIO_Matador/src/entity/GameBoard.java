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
 * Class that sets up new or already running gameboard.
 * @author madswerner
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
	 * Creates all the fields on the board by making objects of the abstractField class.
	 * Depending on which type of field it is, the object is set to being either a Fleet, Tax, Refuge and so forth. 
	 * The method takes a text object as parameter, the object is used in the constructor for each field,
	 * to access the method in the text class that collects the information about the field in the fieldInfo text file.
	 * 
	 * @param text - Information of the field.
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
	 * Loads everything board related from the database, when starting a saved game.
	 * loads and sets the owners of the properties on the board and builds houses
	 * and hotels on the fields.  
	 * 
	 * @param players - owner of the fields
	 * @param gui - GUI object to access methods in the GUI_Commands class
	 * @param sql - SQL class object to access methods in SQL class
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
	 * Returns a string array of names of the properties that the player owns
	 * 
	 * @param player - Owner of properties
	 * @return properties - name of properties
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
	 * Returns a string of names of the properties that are not built on, which the player owns.
	 * 
	 * @param player - Owner of property
	 * @return properties - name of properties
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
	 * This includes his balance and value of properties and buildings.
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
	 * If a field has 5 buildings it corresponds to it having 1 Hotel and no Houses. 
	 * 
	 * @param sql - SQL class object, to access methods from the SQL class
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
	 * Returns the abstract fields object at field position i
	 * 
	 * @param i - position a game board
	 * @return logicFields[i]
	 */

	public AbstractFields getLogicField(int i) {
		return this.logicFields[i];
	}

	/**
	 * Returns all abstract fields objects
	 * 
	 * @return logicFields
	 */

	public AbstractFields[] getLogicFields() {
		return this.logicFields;
	}

	/**
	 * Checks if player has all properties of the same colour
	 * 
	 * @param owner - owner of field
	 * @param COLOUR - colour of field
	 * @return True if he owns all - otherwise false
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
	 * Returns an abstract field object by using the name of the field as an identifier
	 * 
	 * @param propertyName - name of the field
	 * @return logicfields[i]
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
	 * Saves the game board to the database.
	 * This includes saving the properties owner, if they are mortgaged, if they have houses/hotels  
	 * It also includes updating the positions of the cards in the database
	 * @param sql - SQL class object, to access methods from the SQL class
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
	 * Delegates the chain of events that occurs to a specific player when the player lands on the field.
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
	 * @param text - text that is shown when card is shown
	 * @param sql - SQL class object, to access methods from the SQL class
	 * @throws SQLException
	 */

	public void createCardDeck(Texts text, SQL sql) throws SQLException {
		deck = new CardStack();
		deck.newDeck(text);
		deck.shuffle();
		deck.createCards(sql);
	}

	/**
	 * Generates a new deck of cards, and sets the position of each card corresponding to the position saved in the database
	 * 
	 * @param text - loadCards text
	 * @param sql - SQL class object, to access methods from the SQL class
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
	 * gets amount of hotels on territory
	 * 
	 * @return hotelCount
	 */

	public int getHotelCount(){
		return hotelCount;
	}

	/**
	 * Adds one hotel on territory
	 */

	public void addHotel(){
		hotelCount++;
	}

	/**
	 * Removes one hotel on territory
	 */

	public void subtractHotel(){
		hotelCount--;
	}

	/**
	 * gets amount of houses on territory
	 * 
	 * @return houseCount
	 */

	public int getHouseCount(){
		return houseCount;
	}

	/**
	 *  Adds one house on territory
	 */

	public void addHouse(){
		houseCount++;
	}

	/**
	 *  Removes one house on territory
	 */

	public void subtractHouse(){
		houseCount--;
	}

	/**
	 * Gets number of houses on specific field
	 * 
	 * @param field_id
	 * @return houseCount on field_id
	 */

	public int getHouseCount(int field_id) {
		return ((Territory)logicFields[field_id]).getHouseCount();
	}

	/**
	 * Checks if the field is of the typed ownable
	 * 
	 * @param field_id
	 * @return true if its an ownable and false if its not
	 */

	public boolean isOwnable(int field_id) {
		return this.logicFields[field_id] instanceof Ownable;
	}

	/**
	 * Returns the owner of a specific field
	 * 
	 * @param field_id
	 * @return owner of field or null if there is no owner
	 */

	public Player getOwner(int field_id) {
		if (isOwnable(field_id)) {
			return ((Ownable)logicFields[field_id]).getOwner();
		}
		return null;
	}

	/**
	 * Sets a Player as an owner of a field. 
	 * It will only set a player as owner of a Territory if the territory has not been build on.
	 * 
	 * @param field_id
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
	 * Returns a dicecup object
	 * @return dicecup
	 */

	public DiceCup getDiceCup(){
		return dicecup;
	}

	/**
	 * draws a card from the deck
	 * 
	 * @param player - player who draws the card
	 * @return deck.draw(player)
	 */

	public String drawCard(Player player){
		return deck.draw(player);

	}

	/**
	 * When players are about to go bankrupt, they have the option to mortgage properties and sell buildings,
	 * to make money to pay of their debt and stay in the game.
	 * houses/hotels
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
	 * When player hits bankruptcy all his buildings are removed. Besides that, the account
	 * from players who hit bankruptcy is transfered to creditor.
	 * 
	 * @param player - player who goes bankrupt
	 * @param creditor - the player who made the first player go bankrupt
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
	 * Returns all player object in an array
	 * @return players
	 */

	public Player[] getPlayers() {
		return con.getPlayers();
	}

	/**
	 * Returns an object of the action controller
	 * @return Auction controller
	 */

	public AuctionController getAuctionController() {
		return con.getAuctionController();
	}

}