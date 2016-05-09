package entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import boundary.SQL;

public class CardStack {
	private ChanceCard card;
	private ArrayList<String> texts;
	private ArrayList<ChanceCard> chanceCardDeck;


	public CardStack(){
		chanceCardDeck = new ArrayList<ChanceCard>();
	}
	
	public void newDeck(Texts text) {
		for(int i = 1; i<34; i++) {
			this.card = new ChanceCard(text.getCardString("k"+i),i);
			chanceCardDeck.add(card);
		}
	}

	public void shuffle() {
		Collections.shuffle(chanceCardDeck);
	}

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

	public ArrayList<String> drawAll() {
		texts = new ArrayList<String>();
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			texts.add(chanceCardDeck.get(i).toString());
		} 
		return texts;
	}
	
	public void loadCards(Texts text, SQL sql) throws SQLException {
		for (int i = 0; i < 33; i++) {
			chanceCardDeck.add(sql.getCardPosition(i+1), new ChanceCard(text.getCardString("k"+i+1),sql.getCardPosition(i+1)));
		}
	}
	
	public void createCards(SQL sql) throws SQLException {
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			sql.createChanceCard(chanceCardDeck.get(i), i);
		}
	}
	
	public void updateCards(SQL sql) throws SQLException {
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			sql.updateCard(chanceCardDeck.get(i), i);
		}
	}

}
