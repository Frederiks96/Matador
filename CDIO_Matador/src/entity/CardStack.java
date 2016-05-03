package entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import boundary.SQL;

public class CardStack {
	private ChanceCard card;
	private ArrayList<String> texts;
	private ArrayList<ChanceCard> chanceCardDeck;
	private SQL sql;


	public CardStack(){
		chanceCardDeck = new ArrayList<ChanceCard>();
	}
	
	public void newDeck(Texts text) {
		for(int i = 1; i<34; i++) {
			this.card = new ChanceCard(text.getCardString("k"+i));
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
	
	public void loadCards(Texts text) throws SQLException {
		sql = new SQL();
		for (int i = 0; i < chanceCardDeck.size(); i++) {
			chanceCardDeck.add(new ChanceCard(text.getCardString("k"+sql.getCardId(i))));
		}
	}

}
