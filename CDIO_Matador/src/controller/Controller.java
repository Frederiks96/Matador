package controller;

import java.io.IOException;
import java.sql.SQLException;

import boundary.GUI_Commands;
import boundary.SQL;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.dicecup.DiceCup;

public class Controller  {

	private PropertiesController propertiescon = new PropertiesController();
	private AuctionController auctioneer = new AuctionController();
	private TradeController broker;

	private GUI_Commands gui = new GUI_Commands();
	private GameBoard board = new GameBoard(this);
	private SQL sql;

	private Texts text;
	private Player[] players;
	private String gameName;

	public Controller() throws SQLException {
		this.sql = new SQL();
		sql.getConnection();
	}

	/**
	 * Runs the game and give each player their turn. If only one player is alive, the player will be declared as winner, 
	 * and the game will end.
	 * 
	 * @throws SQLException
	 */
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
					saveGame();
				}
			}
		} 

		gui.showMessage(text.getFormattedString("winner", getWinner()));
		sql.dropDataBase();
		sql.closeConnection();
		gui.closeGUI();
	}

	/**
	 * Lets the user choose whether a new game should be started, or if they wish to continue a previous game.
	 * 
	 * @throws SQLException
	 */
	private void chooseGame() throws SQLException {
		String game = gui.getUserButtonPressed(text.getString("loadGameQuestion"),text.getString("loadGame"),text.getString("newGame"));
		if (game.equals(text.getString("newGame"))) {
			startNewGame();
		} else {
			loadGame(text, gui.getUserSelection(text.getString("chooseGame"), sql.getActiveGames()));
		}
	}

	/**
	 * Lets the users choose a name for their game.
	 * Starts a new game by setting up the gameboard and adding players to the game.
	 * Creates a new deck of ChanceCards.
	 * 
	 * @throws SQLException
	 */
	public void startNewGame() throws SQLException {
		do {
			gameName = gui.getUserString(text.getString("nameGame"));
		} while (dbNameUsed(gameName.trim()) || isNotValidDBName(gameName));

		try {
			sql.createNewDB(gameName);
		} catch (IOException e) {
			gui.showMessage(text.getString("fileNotFound"));
			gui.closeGUI();
		}

		board.setupBoard(text);
		sql.createProperties();
		addPlayers();
		players[0].setTurn(true);
		board.createCardDeck(text, sql);
	}

	/**
	 * Loads a game stored in the database and sets up the board from the last saved state.
	 * 
	 * @param text – The text object that will determine which texts will be loaded from the properties file, dependent on the language.
	 * @param gameName – The name of the game that will be loaded from the database
	 * @throws SQLException
	 */
	public void loadGame(Texts text, String gameName) throws SQLException {
		sql.useDB(gameName);
		board.setupBoard(text);
		while (true) {
			try {
				loadPlayers();
				board.countBuildings(sql);
				board.loadBoard(players,gui,sql);
				board.loadCardDeck(text,sql);
				break;
			} catch (SQLException s) {
				sql.updateUser(gui.getUserString(text.getString("getUser")), gui.getUserString("getPass"));
			}
		}
	}

	/**
	 * Controls the chain of events in a player's turn. 
	 * 
	 * @param player – The player that has his turn
	 * @throws SQLException
	 */
	public void playerTurn(Player player) throws SQLException {
		String options;
		int numPairs = 0;

		if (player.getJailTime()>-1 && player.getJailTime()<3){
			// PLAYER IS IN JAIL


			boolean roll = gui.getUserLeftButtonPressed(text.getString("prisonQuestion"), text.getString("roll"), text.getString("payBail"));
			if (roll){
				for(int i = 1; i < 3; i++){
					board.getDiceCup().roll();
					gui.setDice(board.getDiceCup().getDieOne(), board.getDiceCup().getDieTwo());	
				}


				if(board.getDiceCup().hasPair()){
					gui.removeAllCars(player.getName());		
					player.updatePosition((board.getDiceCup().getLastRoll()));		
					gui.setCar(player.getPosition(), player.getName());	
					player.resetJailTime();

					board.landOnField(player, text, gui);
					if (board.isOwnable(player.getPosition())) {
						if (board.getOwner(player.getPosition()) == null) {
							auctioneer.auction(players, board.getLogicField(player.getPosition()), gui,text);
						}
					}

					gui.setBalance(player.getName(), player.getAccount().getBalance());
					saveGame();
				} else { 
					player.increaseJailTime();
				}
			} else {
				player.updateBalance(-1000);
				gui.setBalance(player.getName(),player.getBalance());
				player.resetJailTime();
				gui.showMessage(text.getString("paidBail"));
				playerTurn(player); // Man får lov til at slå
			}

		} else if (player.getJailTime()>2) {
			gui.showMessage(text.getString("payBailForced"));
			player.updateBalance(-1000);
			gui.setBalance(player.getName(),player.getBalance());
			player.resetJailTime();
			playerTurn(player); // Man får lov til at slå
		}

		else do {
			// PLAYER IS NOT IN JAIL
			
			if (board.getDiceCup().hasPair()) {
				gui.showMessage(text.getFormattedString("turnAgain", player.getName()));
			}
			options = gui.getUserButtonPressed(text.getFormattedString("turn", player.getName()), 
					text.getStrings("roll","trade","manageProperties"));

			if (options.equals(text.getString("roll"))) {
				//ROLL
				gui.removeAllCars(player.getName());		
				board.getDiceCup().roll();
				player.updatePosition((board.getDiceCup().getLastRoll()));		
				gui.setDice(board.getDiceCup().getDieOne(), board.getDiceCup().getDieTwo());	
				gui.setCar(player.getPosition(), player.getName());		
				
				board.landOnField(player, text, gui);
				if (board.isOwnable(player.getPosition())) {
					if (board.getOwner(player.getPosition()) == null) {
						auctioneer.auction(players, board.getLogicField(player.getPosition()), gui,text);
					}
				}

				gui.setBalance(player.getName(), player.getAccount().getBalance());		
				if(board.getDiceCup().hasPair()) {
					numPairs++;
				}
				if(numPairs == 3) { 
					gui.showMessage(text.getString("threePairs"));
					player.imprison();
					gui.removeAllCars(player.getName());
					gui.setCar(player.getPosition(), player.getName());
					break;
				}
				for (int i = 0; i < players.length; i++) {
					gui.setBalance(players[i].getName(), players[i].getBalance());
				}

			} else if (options.equals(text.getString("trade"))) {
				//TRADE
				String offereeName = gui.getUserButtonPressed(text.getString("offereeName"), 
						getOpponents(player));
				broker = new TradeController();
				broker.suggestDeal(player, getPlayer(offereeName), text, board, gui);
				for (int i = 0; i < players.length; i++) {
					gui.setBalance(players[i].getName(), players[i].getBalance());
				}

			} else { //MANAGE PROPERTIES
				propertiescon.manage(gui, player, text, board);
				for (int i = 0; i < players.length; i++) {
					gui.setBalance(players[i].getName(), players[i].getBalance());
				}
			}
			saveGame();
		} while (!options.equals(text.getString("roll")) || board.getDiceCup().hasPair());
		saveGame();
	}

	/**
	 * Prompts the users of which language they wish to use in the game 
	 */
	private void getLanguage() {
		String lang = gui.getUserButtonPressed("Choose your preferred language", "Dansk", "English");
		if (lang.equals("Dansk")) {
			this.text = new Texts(language.Dansk);
		} else {
			this.text = new Texts(language.English);	
		}
	}

	/**
	 * Evaluates whether a given name is currently in use.
	 * 
	 * @param name – The name to be evaluated
	 * @return true if the name is eligible for use, otherwise false
	 */
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

	/**
	 * Counts the number of players currently alive in the game
	 * 
	 * @return num
	 */
	public int numPlayersAlive() {
		int num = 0;
		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive()) {
				num++;
			}
		}
		return num;
	}

	/**
	 * Asks the users how many players will be participating in the game and adds this amount of players to the game logic and the GUI.
	 * The number of players in a game cannot exceed 6 and has to be minimum 2.
	 * 
	 * @throws SQLException
	 */
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

	/**
	 * Loads players and their last saved state from the database
	 * 
	 * @throws SQLException
	 */
	private void loadPlayers() throws SQLException {
		players = new Player[sql.countPlayers()-1];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(sql.getPlayerName(i+1),sql.getVehicleColour(i+1),sql.getVehicleType(i+1));
			players[i].setBalance(sql.getBalance(players[i].getPlayerID()));
			players[i].setTurn(sql.getTurn(players[i].getPlayerID()));
			players[i].setPosition(sql.getPosition(players[i].getPlayerID()));
			players[i].setJailTime(sql.getJailTime(players[i].getPlayerID()));
			players[i].setIsAlive(sql.getIsAlive(players[i].getPlayerID()));
			gui.addPlayer(players[i].getName(), players[i].getBalance());
			gui.setCar(players[i].getPosition(), players[i].getName());
			//			players[i].giveCard(card); TODO 
		}
	}

	//	private void loadCards(Texts text) throws SQLException {
	//		deck.loadCards(text);
	// }

	/**
	 * Evaluates whether the name of the game is fit for use, or if it conflicts with a already existing game. 
	 * 
	 * @param dbName – The name of the game
	 * @return true if the name is already in use in the database, otherwise false
	 * @throws SQLException
	 */
	private boolean dbNameUsed(String dbName) throws SQLException {
		String[] s = sql.getActiveGames();
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(dbName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Evaluates whether the name contains any special characters, or only contains whitespace.
	 * 
	 * @param gameName – The name to be evaluated
	 * @return true if the name contains any special characters or is null, otherwise false
	 */
	private boolean isNotValidDBName(String gameName) {
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

	/**
	 * Returns the player with a given name. Returns null, if the player doesn't exists.
	 * 
	 * @param playerName
	 * @return Player, if the player exists, otherwise null
	 */
	private Player getPlayer(String playerName) {
		for (int i = 0; i < players.length; i++) {
			if (players[i].getName().equals(playerName)) {
				return players[i];
			}
		}
		return null;
	}

	/**
	 * Returns the rest of the players of the game
	 * 
	 * @param player – The player of which's perspective the opponents are classified
	 * @return opponents
	 */
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

	/**
	 * Returns the winner of the game
	 * 
	 * @return the name of the winner
	 */
	private String getWinner() {
		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive()) {
				return players[i].getName();
			}
		}
		return "";
	}

	/**
	 * Stores the game's state in the database 
	 * 
	 * @throws SQLException
	 */
	private void saveGame() throws SQLException {
		// TODO
		for (int i = 0; i< players.length; i++){
			sql.setBalance(players[i]);
			sql.setPosition(players[i]);
			sql.setJailTime(players[i]);
			sql.setTurn(players[i]);
			sql.setIsAlive(players[i]);
		}
		board.saveBoard(sql);
		for (int i = 0; i < players.length; i++) {
			
		}
	}
	
	/**
	 * Returns an array containing the players in the game.
	 * 
	 * @return players
	 */
	public Player[] getPlayers() {
		return this.players;
	}
	
	/**
	 * Returns the AuctionController object
	 * @return auctioneer
	 */
	public AuctionController getAuctionController() {
		return this.auctioneer;
	}

}