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
import entity.fields.ChanceField;
import entity.fields.Ownable;
import entity.fields.Territory;

public class Controller  {

	private PropertiesController propertiescon = new PropertiesController();
	private AuctionController auctioneer = new AuctionController();
	private TradeController broker;

	private GUI_Commands gui = new GUI_Commands();
	private GameBoard board = new GameBoard();
	private SQL sql;

	private Texts text;
	private CardStack deck;
	private Player[] players;
	private String gameName;
	
	public Controller() throws SQLException {
		this.sql = new SQL();
	}

	public void run() throws SQLException {
		getLanguage();
		chooseGame();

		while (numPlayersAlive()>1) {	
			for (int i=0; i < players.length; i++) {
				if (players[i].isAlive() && players[i].isTurn()) {
					playerTurn(players[i]);
					players[i].setTurn(false);
					if(players.length == i+1 ) { 
						players[0].setTurn(true);
					}
					else { 
						players[i+1].setTurn(true);
					}

				}
			}
		} 

		gui.showMessage(text.getFormattedString("winner", getWinner()));
		sql.dropDataBase();
		sql.closeConnection();
		gui.closeGUI();
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
		} while (dbNameUsed(gameName.trim()) || isValidDBName(gameName));

		try {
			sql.createNewDB(gameName);
		} catch (IOException e) {
			gui.showMessage(text.getString("fileNotFound"));
			gui.closeGUI();
		}

		board.setupBoard(text);
		this.deck = new CardStack();
		deck.newDeck(text);
		deck.shuffle();
		addPlayers();
		players[0].setTurn(true);

	}

	public void loadGame(Texts text, String gameName) throws SQLException {
		sql.useDB(gameName);
		while (true) {
			try {
				loadPlayers();
				board.countBuildings(sql);
				//				loadCards(text);
				break;
			} catch (SQLException s) {
				sql.updateUser(gui.getUserString(text.getString("getUser")), gui.getUserString("getPass"));
			}
		}
		board.setupBoard(text,gameName,players,gui,sql);
	}

	public void playerTurn(Player player) throws SQLException {
		String options;
		int numPairs = 0;

		if (player.getJailTime()>-1){
			// PLAYER IS IN JAIL
			boolean roll = gui.getUserLeftButtonPressed(text.getString("prisonQuestion"), text.getString("roll"), text.getString("payBaiul"));
			if (roll){
				board.getDiceCup().roll();
				gui.setDice(board.getDiceCup().getDieOne(), board.getDiceCup().getDieTwo());	



				if(board.getDiceCup().hasPair()){
					gui.removeCar(player.getPosition(), player.getName());		
					player.updatePosition((board.getDiceCup().getLastRoll()));		
					gui.setCar(player.getPosition(), player.getName());		

					board.landOnField(player, text, gui);
					if (board.isOwnable(player.getPosition())) {
						if (board.getOwner(player.getPosition()) == null) {
							auctioneer.auction(players, board.getLogicField(player.getPosition()), this, gui,text);
						}
					}

					gui.setBalance(player.getName(), player.getAccount().getBalance());
					if (board.getLogicField(player.getPosition()) instanceof ChanceField) 
						deck.draw(player);				
					saveGame();
				}else player.increaseJailTime();
			}else {
				player.updateBalance(-1000);
				gui.setBalance(player.getName(),player.getBalance());
				player.resetJailTime();
			}
		}

		else do {
			options = gui.getUserButtonPressed(text.getFormattedString("turn", player.getName()), 
					text.getStrings("roll","trade","manageProperties"));

			if (options.equals(text.getString("roll"))) {
				//ROLL
				gui.removeCar(player.getPosition(), player.getName());		
				board.getDiceCup().roll();
				player.updatePosition((board.getDiceCup().getLastRoll()));		
				gui.setDice(board.getDiceCup().getDieOne(), board.getDiceCup().getDieTwo());	
				gui.setCar(player.getPosition(), player.getName());		

				board.landOnField(player, text, gui);
				if (board.isOwnable(player.getPosition())) {
					if (board.getOwner(player.getPosition()) == null) {
						auctioneer.auction(players, board.getLogicField(player.getPosition()), this, gui,text);
					}
				}

				gui.setBalance(player.getName(), player.getAccount().getBalance());
				if (board.getLogicField(player.getPosition()) instanceof ChanceField) 
					deck.draw(player);				
				if(board.getDiceCup().hasPair()) numPairs++;
				if(numPairs == 3) player.imprison();
				for (int i = 0; i < players.length; i++) {
					gui.setBalance(players[i].getName(), players[i].getBalance());
				}
				;
				saveGame();

			} else if (options.equals(text.getString("trade"))) {
				//TRADE
				String offereeName = gui.getUserButtonPressed(text.getString("offereeName"), 
						getOpponents(player));
				broker = new TradeController();
				broker.suggestDeal(player, getPlayer(offereeName), text, board, gui);
				for (int i = 0; i < players.length; i++) {
					gui.setBalance(players[i].getName(), players[i].getBalance());
				}
				saveGame();

			} else { //MANAGE PROPERTIES
				propertiescon.manage(gui, player, text, board);
				for (int i = 0; i < players.length; i++) {
					gui.setBalance(players[i].getName(), players[i].getBalance());
				}
				saveGame();
			}

		} while (!options.equals(text.getString("roll")) || board.getDiceCup().hasPair());
	}

	private void getLanguage() {
		String lang = gui.getUserButtonPressed("Choose your preferred language", "Dansk", "English");
		if (lang.equals("Dansk")) {
			this.text = new Texts(language.Dansk);
		} else {
			this.text = new Texts(language.English);	
		}
	}

	public boolean isValidName(String name) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] != null) {
				if ((players[i].getName().toLowerCase().trim()).equals(name.toLowerCase().trim())) {
					return false;
				}
			}else if(name.trim().equals(""))
				return false;
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
				players[i] = new Player(name,""/*Bilens farve*/,""/*Bilens type*/);
				sql.createPlayer(players[i]);
				gui.addPlayer(name, players[i].getBalance());
				gui.setCar(players[i].getPosition(), players[i].getName());
				if(i==0) players[i].setTurn(true);
				i++;
			} else {
				gui.showMessage(text.getString("invalidName"));
			}
		} while(i<players.length);
	}

	private void loadPropertires() throws SQLException{
		//TODO
		for(int i = 0; i < 40; i++){
			if (board.getLogicField(i) instanceof Ownable){
				// load mortgage
				((Ownable)board.getLogicField(i)).setMortgage(sql.isMortgaged(board.getLogicField(i)));

				if (board.getLogicField(i) instanceof Territory){
					//load HouseCount and builds hotels and houses on board
					((Territory)board.getLogicField(i)).setHouseCount(sql.getFieldHouseCount((Territory)board.getLogicField(i)));
					if(sql.getFieldHouseCount((Territory)board.getLogicField(i))==5){
						gui.setHotel(i, true);
					}else gui.setHouse(i, ((Territory)board.getLogicField(i)).getHouseCount());

				}
				if(sql.getOwnerID(i)!= null)
					((Ownable)board.getLogicField(i)).setOwner(players[sql.getOwnerID(i)], gui);

			}
		}
	}

	private void loadPlayers() throws SQLException {
		players = new Player[sql.countPlayers()];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(sql.getPlayerName(i+1),sql.getVehicleColour(i+1),sql.getVehicleType(i+1));
			sql.setBalance(players[i]);
		}
	}
	
//	private void loadCards(Texts text) throws SQLException {
//		deck.loadCards(text);
// }

	private boolean dbNameUsed(String dbName) throws SQLException {
		String[] s = sql.getActiveGames();
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(dbName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidDBName(String gameName) {
		return gameName.equals(null) || gameName.trim().equals("") || 
				gameName.contains("/") || gameName.contains(";") || 
				gameName.contains("'") || gameName.contains("?") ||
				gameName.contains("¨") || gameName.contains("´") ||
				gameName.contains("`") || gameName.contains("^") ||
				gameName.contains("!") || gameName.contains("#") ||
				gameName.contains("€") || gameName.contains("%") ||
				gameName.contains("(") || gameName.contains(")") ||
				gameName.contains("=") || gameName.contains("<") ||
				gameName.contains(">") || gameName.contains(",") ||
				gameName.contains(".") || gameName.contains("-") ||
				gameName.contains("@");
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
		int j = 0;
		for (int i = 0; i < players.length; i++) {
			if (!players[i].equals(player)) {
				opponents[j] = players[i].getName();
				j++;
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

	private void saveGame() throws SQLException {
		// TODO
		for (int i = 0; i< players.length; i++){
			sql.setBalance(players[i]);
			sql.setPosition(players[i]);
			sql.setJailTime(players[i]);
			sql.setTurn(players[i]);
			sql.setIsAlive(players[i]);
		}
		//				for(int i=0; i < deck.size(); i++){
		//					sql.setCardPosition(deck[i].getPosition, deck[i].getID());
		//				}

		board.saveBoard(sql);
	}

	private void removeAllBuildings(Player player){
		for (int i = 0; i < 40; i++){
			if(board.getLogicField(i) instanceof Territory){
				if (((Territory)board.getLogicField(i)).getOwner() == player){

					while(((Territory)board.getLogicField(i)).getHouseCount() > 0){
						if(((Territory)board.getLogicField(i)).getHouseCount()==5)
							((Territory)board.getLogicField(i)).sellHotel(gui);
						((Territory)board.getLogicField(i)).sellHouse(gui);
					}
				}
			}
		}
	}	
	public void bankrupt(Player player, Player creditor) {
		player.bankrupt();
		if (creditor!=null) {
			removeAllBuildings(player);
			creditor.updateBalance(player.getBalance());
			player.updateBalance(-player.getBalance());

			for (int i = 0; i < 40; i++) {
				if(board.getLogicField(i) instanceof Territory){
					if(((Territory)board.getLogicField(i)).getOwner().equals(player)){
						gui.removeOwner(i);
						((Territory)board.getLogicField(i)).setOwner(creditor, gui);
					}
				}	
			}


		} else {
			removeAllBuildings(player);
			player.updateBalance(-player.getBalance());
			String[] properties = board.getOwnedProperties(player);
			for (int i = 0; i < properties.length; i++) {
				auctioneer.auction(players, board.getProperty(properties[i]), this, gui, text);
			}
		}
	}

}