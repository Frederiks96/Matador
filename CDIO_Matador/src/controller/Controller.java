package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import boundary.GUI_Commands;
import boundary.SQL;
import entity.CardStack;
import entity.GameBoard;
import entity.Player;
import entity.Texts;
import entity.Texts.language;
import entity.fields.AbstractFields;
import entity.fields.Brewery;
import entity.fields.CardField;
import entity.fields.Fleet;
import entity.fields.Territory;

public class Controller  {

	private GUI_Commands c = new GUI_Commands(); 
	private GameBoard gameBoard;
	private AbstractFields[] fields;
	private String[] properties;
	private Texts text;
	private CardStack deck;
	private Player[] players;
	private SQL sql;
	private String gameName;

	public Controller() throws SQLException {
		this.sql = new SQL();
	}

	public void run() throws SQLException {
		getLanguage();
		gameBoard = new GameBoard();
		String game = newGame();
		if (game.equals(text.getString("newGame"))) {
			startNewGame();
		} else {
			loadGame(text, c.getUserSelection(text.getString("chooseGame"), sql.getActiveGames()));
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive() && numPlayersAlive()>1) {
				playerTurn(players[i]);
			} else {
				c.showMessage(text.getFormattedString("winner", players[i].getName()));
			}
		}
	}

	public void startNewGame() throws SQLException {
		do {
			gameName = c.getUserString(text.getString("nameGame"));
		} while (gameName.equals(null) || gameName.trim().equals("") || dbNameUsed(gameName.trim()));

		try {
			sql.createNewDB(gameName);
		} catch (IOException e) {
			c.showMessage(text.getString("fileNotFound"));
			c.closeGUI();
		}

		gameBoard.setupBoard(text);
		fields = gameBoard.getFields();
		CardStack deck = new CardStack();
		deck.newDeck(text);
		deck.shuffle();
		addPlayers();
	}

	public void loadGame(Texts text, String gameName) throws SQLException {
		sql.useDB(gameName);
		while (true) {
			try {
				loadPlayers();
				loadCards(text);
				break;
			} catch (SQLException s) {
				sql.updateUser(c.getUserString(text.getString("getUser")), c.getUserString("getPass"));
			}
		}
		gameBoard.setupBoard(text,gameName,players);
	}

	public void playerTurn(Player player) {
		// Opdatere spillerens position før landOnField kaldes
		if (fields[player.getPosition()] instanceof CardField) {
			deck.draw(player);
		}
		c.closeGUI();
	}

	public boolean hasAll(Player owner, String COLOUR) {
		fields = gameBoard.getFields();
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
		fields = gameBoard.getFields();
		properties = new String[28];
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
		String lang = c.getUserSelection("Choose your preferred language", "Dansk", "English");
		if (lang.equals("Dansk")) {
			text = new Texts(language.Dansk);
		} else {
			text = new Texts(language.English);	
		}
	}

	private String newGame() {
		return c.getUserSelection(text.getString("loadGameQuestion"),text.getString("loadGame"),text.getString("newGame"));
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
			numOfPlayers = c.getUserInteger(text.getString("numOfPlayers"));
		} while (numOfPlayers < 2 && numOfPlayers > 6);
		players = new Player[numOfPlayers];
		int i = 0;
		do {
			String name = c.getUserString(text.getFormattedString("yourName", i+1));
			if (isValidName(name)) {
				players[i] = new Player(name,"","",sql);
				i++;
			} else {
				c.showMessage(text.getString("nameTaken"));
			}
		} while(i<players.length);
	}

	private void loadPlayers() throws SQLException {
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(sql.getPlayerName(i+1),sql.getVehicleColour(i+1),sql.getVehicleType(i+1),sql);
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

	private boolean dbNameUsed(String dbName) throws SQLException {
		String[] s = sql.getActiveGames();
		for (int i = 0; i < s.length; i++) {
			if (s[i].equals(dbName)) {
				return true;
			}
		}
		return false;
	}

}
