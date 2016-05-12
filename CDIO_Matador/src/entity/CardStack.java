package entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import boundary.SQL;

public class CardStack {
	private ChanceCard card;
	private ArrayList<String> texts;
	private ArrayList<ChanceCard> chanceCardDeck;

	/**
	 * The constructor of the card stack. Initializes the local ArrayList<ChanceCard> chanceCardDeck.
	 */
	public CardStack(){
		chanceCardDeck = new ArrayList<ChanceCard>();
	}
	
	/**
	 * Creates a new deck of ChanceCards with texts from the properties file specified by the text object
	 * 
	 * @param text the object initialized with an enum 'language', to get the texts for the cards
	 */
	public void newDeck(Texts text) {
		for(int i = 1; i<34; i++) {
			this.card = new ChanceCard(text.getCardString("k"+i),i);
			chanceCardDeck.add(card);
		}
	}

	/**
	 * Shuffles the deck using the shuffle method in the Collections class
	 */
	public void shuffle() {
		Collections.shuffle(chanceCardDeck);
	}

	/**
	 * Draws a card and returns the text of the card drawn. If the card is ownable, 
	 * the card will be given to the player 
	 * 
	 * @param player the player who draws the card from the deck
	 * @return the text from the card
	 */
	public String draw(Player player) {
		ChanceCard temp = chanceCardDeck.get(0);

		if (temp.isOwnable()) {
			temp.setOwner(player);
			player.giveCard(temp);
		}

		else {
			chanceCardDeck.add(temp);
		}
		chanceCardDeck.remove(0);
		return temp.toString();
	}

	/**
	 * Draws all cards from the deck, but leaves the cards in the deck.
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> drawAll() {
		texts = new ArrayList<String>();
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			texts.add(chanceCardDeck.get(i).toString());
		} 
		return texts;
	}
	
	/**
	 * Loads the ChanceCards from the database. Ensures that chanceCardDeck holds at least 33 null values to be replaced. 
	 * Finally it removes any null values, if such haven't been replaced.
	 * 
	 * @param text The text object which fills the ChanceCards with the corresponding text
	 * @param sql The object that connects to the database and loads the cards
	 * @throws SQLException
	 */
	public void loadCards(Texts text, SQL sql) throws SQLException {
		ensureSize(chanceCardDeck,33);
		for (int i = 1; i < 34; i++) {
			chanceCardDeck.set(sql.getCardPosition(i), new ChanceCard(text.getCardString("k"+i),sql.getCardPosition(i)));
		}
		while (chanceCardDeck.contains(null)) {
			chanceCardDeck.remove(null);
		}
	}
	
	/**
	 * Stores the cards and their position into the database.
	 * 
	 * @param sql The object that connects with the database and store the cards
	 * @throws SQLException
	 */
	public void createCards(SQL sql) throws SQLException {
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			sql.createChanceCard(chanceCardDeck.get(i), i);
		}
	}
	
	/**
	 * Updates the cards position in the deck.
	 * 
	 * @param sql The object that connects with the database and updates the position
	 * @throws SQLException
	 */
	public void updateCards(SQL sql) throws SQLException {
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			sql.updateCard(chanceCardDeck.get(i), i);
		}
	}
	
	/**
	 * Ensures the size of an ArrayList by adding null values into it, until it reaches the specified size.
	 * 
	 * @param list The ArrayList that will be given a specific size
	 * @param size The size that the ArrayList should be
	 */
	private void ensureSize(ArrayList<?> list, int size) {
		list.ensureCapacity(size);
		while (list.size()<size) {
			list.add(null);
		}
	}

}
