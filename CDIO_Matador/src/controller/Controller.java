package controller;

import java.sql.SQLException;

import boundary.GUI_Commands;
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

public class Controller {

	private GUI_Commands c = new GUI_Commands(); 
	private GameBoard gameBoard;
	private AbstractFields[] fields;
	private String[] properties;
	private Texts text;
	private CardStack deck;
	private Player[] players;
	private String name;

	public Controller() {
		gameBoard = new GameBoard();
	}

	public void startNewGame() throws SQLException {
		gameBoard.setupBoard(text);
		fields = gameBoard.getFields();
		CardStack deck = new CardStack(text);
		deck.shuffle();
		int numOfPlayers = 0;
		do {
			numOfPlayers = c.getUserInteger(text.getString("numOfPlayers"));
		} while (numOfPlayers < 2 && numOfPlayers > 6);
		players = new Player[numOfPlayers];
		
		for (int i = 1; i < players.length+1; i++) {
			name = c.getUserString(text.getFormattedString("yourName", i));
			if (isValidName(name)) {
				players[i-1] = new Player(name, "","");
			} else {
				c.showMessage(text.getString("nameTaken"));
				i--;
			}
		}

	}

	public void loadGame() {

	}

	public void run() throws SQLException {
		getLanguage();
		if (newGame()) {
			startNewGame();
		} else {
			loadGame();
		}

		for (int i = 0; i < players.length; i++) {
			if (players[i].isAlive() && numPlayersAlive()>2) {
				playerTurn(players[i]);
			}
		}

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

	private void playerStateMachine(){
		// TODO
	}
	private void arrested(){
		// TODO
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

	private boolean newGame() {
		return c.getUserLeftButtonPressed("", "", "");
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

}
