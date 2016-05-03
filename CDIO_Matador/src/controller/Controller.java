package controller;

import java.io.IOException;
import java.sql.SQLException;
import boundary.GUI_Commands;
import boundary.SQL;
import entity.CardStack;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.dicecup.DiceCup;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.ChanceField;
import entity.fields.Fleet;
import entity.fields.Territory;

public class Controller  {

	private GUI_Commands gui = new GUI_Commands(); 
	private GameBoard gameboard;
	private AbstractFields[] fields;
	private String[] properties;
	private Texts text;
	private CardStack deck;
	private Player[] players;
	private SQL sql;
	private String gameName;
	private DiceCup dicecup = new DiceCup();
	private TradeController broker;

	public Controller() throws SQLException {
		this.sql = new SQL();
	}

	public void run() throws SQLException {
		getLanguage();
		gameboard = new GameBoard();
		dicecup = new DiceCup();
		chooseGame();

		countTotalWorth(players[0]);

		while (numPlayersAlive()>1) {	
			for (int i=0; i < players.length; i++) {
				if (players[i].isAlive() && players[i].isTurn()) {
					playerTurn(players[i]);
					players[i].setTurn(false);
					if(players.length == i+1 )players[0].setTurn(true);
					else players[i+1].setTurn(true);
				
				}
			}
		} 

		gui.showMessage(text.getFormattedString("winner", getWinner()));
		gui.closeGUI();
		sql.dropDataBase();
	}

	private void chooseGame() throws SQLException {
		String game = gui.getUserButtonPressed(text.getString("loadGameQuestion"),text.getString("loadGame"),text.getString("newGame"));
		if (game.equals(text.getString("newGame"))) {
			startNewGame();
		} else {
			loadGame(text, gui.getUserSelection(text.getString("chooseGame"), sql.getActiveGames()));
		}
	}

	public void startNewGame() throws SQLException {
		do {
			gameName = gui.getUserString(text.getString("nameGame"));
		} while (gameName.equals(null) || gameName.trim().equals("") || dbNameUsed(gameName.trim()));

		try {
			sql.createNewDB(gameName);
		} catch (IOException e) {
			gui.showMessage(text.getString("fileNotFound"));
			gui.closeGUI();
		}

		gameboard.setupBoard(text);
		fields = gameboard.getLogicFields();
		this.deck = new CardStack();
		deck.newDeck(text);
		deck.shuffle();
		addPlayers();
		players[0].setTurn(true);

	}

	public void loadGame(Texts text, String gameName) throws SQLException {
		sql.useDB(gameName);
//		while (true) {
//			try {
				loadPlayers(sql);
//				loadCards(text);
//				break;
//			} catch (SQLException s) {
//				sql.updateUser(gui.getUserString(text.getString("getUser")), gui.getUserString("getPass"));
//			}
//		}
		gameboard.setupBoard(text,gameName,players,sql);
	}

	public void playerTurn(Player player) throws SQLException {
		String options;
		String choice;

		if (player.getJailTime()>1){

		}

		else do {
			options = gui.getUserButtonPressed(text.getFormattedString("turn", player.getName()), 
					text.getStrings("roll","trade","manageProperties"));

			if (options.equals(text.getString("roll"))) {
				gui.removeCar(player.getPosition(), player.getName());		
				dicecup.roll();
				player.updatePosition((int)(dicecup.getLastRoll()));		
				gui.setDice(dicecup.getDieOne(), dicecup.getDieTwo());	
				gui.setCar(player.getPosition(), player.getName());		
				gameboard.getLogicField(player.getPosition()).landOnField(player, text, gui);;
				gui.setBalance(player.getName(), player.getAccount().getBalance());
				if (fields[player.getPosition()] instanceof ChanceField) 
//					deck.draw(player);				
				saveGame();

			} else if (options.equals(text.getString("trade"))) {
				String offereeName = gui.getUserButtonPressed(text.getString("offereeName"), 
						getOpponents(player));
				broker = new TradeController();
				broker.suggestDeal(player, getPlayer(offereeName), text, gameboard, gui);
				saveGame();

			} else {	//BUILD   //manage properties
				
				choice = gui.getUserButtonPressed("", text.getStrings("build","mortgage","unbuild"));
				build(player);
				saveGame();
			}

		} while (!options.equals(text.getString("roll")) || dicecup.hasPair());
	}

	private String[] getTerritoriesOwned(Player player) { //	virker ikke
		String[] ownedTerritories = new String[player.getNumTerritoryOwned()];
		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Territory && ((Territory)(fields[i])).getOwner().equals(player)){
				ownedTerritories[i] = fields[i].getName();
			}
		}
		return ownedTerritories;
	}

	public boolean hasAll(Player owner, String COLOUR) {
		fields = gameboard.getLogicFields();
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Territory) {
				if (((Territory) (fields[i])).getColour().equals(COLOUR)) {
					if (((Territory) (fields[i])).getOwner().equals(owner)) {
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

	public String[] getOwnedProperties(Player player) {
		fields = gameboard.getLogicFields();
		properties = new String[player.getNumBreweriesOwned()+player.getNumFleetsOwned()+player.getNumTerritoryOwned()];
		int j = 0;
		for (int i = 0; i < fields.length; i++) {
			if (fields[i] instanceof Brewery) {
				if (((Brewery) (fields[i])).getOwner().equals(player)) {
					properties[j] = fields[i].getName();
					j++;
				}
			}

			if (fields[i] instanceof Fleet) {
				if (((Fleet) (fields[i])).getOwner().equals(player)) {
					if (fields[i] instanceof Fleet) {
						properties[j] = fields[i].getName();
						j++;
					}
				}
			}

			if (fields[i] instanceof Territory) {
				if (((Territory) (fields[i])).getOwner().equals(player)) {

					if (fields[i] instanceof Territory) {
						properties[j] = fields[i].getName();
						j++;
					}
				}
			}
		}

		return properties;
	}

	public AbstractFields[] getFields() {
		return this.fields;
	}

	private void getLanguage() {
		String lang = gui.getUserButtonPressed("Choose your preferred language", "Dansk", "English");
		if (lang.equals("Dansk")) {
			text = new Texts(language.Dansk);
		} else {
			text = new Texts(language.English);	
		}
	}

	public boolean isValidName(String name) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if (players[i].getName().toLowerCase().trim().equals(name.toLowerCase().trim())) {
					return false;
				}
			}
		}
		return true;
	}

	public int numPlayersAlive() {
		int num = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive()) {
				num++;
			}
		}
		return num;
	}

	private void addPlayers() throws SQLException {
		int numOfPlayers = 0;
		do {
			numOfPlayers = gui.getUserInteger(text.getString("numOfPlayers"));
		} while (numOfPlayers < 2 || numOfPlayers > 6);

		players = new Player[numOfPlayers];
		int i = 0;
		do {
			String name = gui.getUserString(text.getFormattedString("yourName", i+1));
			if (isValidName(name)) {
				players[i] = new Player(name,""/*Bilens farve*/,""/*Bilens type*/, sql);
				gui.addPlayer(name, players[i].getBalance());
				gui.setCar(players[i].getPosition(), players[i].getName());
				if(i==0) players[i].setTurn(true);
				i++;
			} else {
				gui.showMessage(text.getString("nameTaken"));
			}
		} while(i<players.length);
	}

	private void loadPlayers(SQL sql) throws SQLException {
		for (int i = 0; i < sql.countPlayers(); i++) {
			players[i] = new Player(sql.getPlayerName(i+1),sql.getVehicleColour(i+1),sql.getVehicleType(i+1), sql);
			sql.setBalance(players[i]);
		}
	}

	private void loadCards(Texts text) throws SQLException {
		deck.loadCards(text);
	}

	private void loadGameBoard(){
		// TODO
		// sætter alle ejerne på brættet ud fra databasen
		// sætter hvor mange 
	}

	private void build(Player player){ // mangler build even
		if(player.getNumTerritoryOwned()>1){
			String property = gui.getUserSelection(text.getString("choosePropertyBuild"),
					getTerritoriesOwned(player));
			String building;
			do{
				building = gui.getUserButtonPressed(text.getString("chooseBuild"),
						text.getString("house"),text.getString("hotel"), text.getString("back"));

				// Builds a house
				if (building == text.getString("house") && gameboard.getHouseCount() < 32){
					for (int j = 0; j < fields.length; j++) {
						if (fields[j].getName().equals(property));
						((Territory) (fields[j])).buyHouse(text, gui);
					}	
				}else gui.showMessage(text.getString("noMoreHouses"));

				// Builds a hotel
				if(building == text.getString("hotel") && gameboard.getHotelCount() < 12){
					for (int j = 0; j < fields.length; j++) {
						if (fields[j].getName().equals(property));
						((Territory) (fields[j])).buyHotel(text, gui);
					}	
				}else gui.showMessage(text.getString("noMoreHotels"));

			}while (building != text.getString("back"));
		}else gui.showMessage(text.getString("notEnoughTerritory"));
	}
	
	private boolean dbNameUsed(String dbName) throws SQLException {
		String[] s = sql.getActiveGames();
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(dbName)) {
				return true;
			}
		}
		return false;
	}

	private Player getPlayer(String playerName) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName().equals(playerName)) {
				return players[i];
			}
		}
		return null;
	}

	private String[] getOpponents(Player player) {
		String[] opponents = new String[players.length-1];
		for (int i = 0; i < players.length; i++) {
			if (!players[i].equals(player)) {
				opponents[i] = players[i].getName();
			}
		}
		return opponents;
	}

	private String getWinner() {
		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive()) {
				return players[i].getName();
			}
		}
		return "";
	}

	private void saveGame() throws SQLException{
		for (int i = 0; i< players.length; i++){
			sql.setBalance(players[i]);
			sql.setPosition(players[i]);
			sql.setJailTime(players[i]);
			sql.setTurn(players[i]);
			sql.setIsAlive(players[i]);
		}
		//		for(int i=0; i < deck.length; i++){
		//			sql.setCardPosition(deck[i].getPosition, deck[i].getID());
		//		}

		for(int i = 0; i < fields.length; i++){
			if (fields[i] instanceof Territory ){
				sql.setMortgage( fields[i].getID(), ((Territory)(fields[i])).isMortgaged()); 
				sql.setHouseCount(fields[i].getID(), ((Territory)(fields[i])).getHouseCount());
			}
			if (fields[i] instanceof Brewery){
				sql.setMortgage( fields[i].getID(), ((Brewery)(fields[i])).isMortgaged()); 
			}
			if (fields[i] instanceof Fleet){
				sql.setMortgage( fields[i].getID(), ((Fleet)(fields[i])).isMortgaged()); 
			}

		}
	}

	private int countTotalWorth(Player player){
		// Counts player balance + value of properties + values of buildings

		int totalworth=0;

		totalworth += player.getBalance();

		for (int i = 0; i < fields.length; i++) {
			if(fields[i] instanceof Territory){
				if(((Territory)(fields[i])).isMortgaged())
					totalworth += (((Territory)(fields[i])).getPrice()*0.5*0.9);
				else{
					totalworth += ((Territory)(fields[i])).getPrice();
					totalworth += (((Territory)(fields[i])).getHouseCount()
							*((Territory)(fields[i])).getHousePrice()*0.5);
				}
			}

			if(fields[i] instanceof Fleet){
				if(((Fleet)(fields[i])).isMortgaged())
					totalworth += ((Fleet)(fields[i])).getPrice()*0.5*0.9;
				else totalworth += ((Fleet)(fields[i])).getPrice();
			}

			if(fields[i] instanceof Brewery){
				if(((Brewery)(fields[i])).isMortgaged())
					totalworth += ((Brewery)(fields[i])).getPrice()*0.5*0.9;
				else totalworth += ((Brewery)(fields[i])).getPrice();
			}
		}
		return totalworth;

	}

}
