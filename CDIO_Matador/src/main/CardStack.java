package main;

import java.util.ArrayList;
import java.util.Collections;

import entity.Texts;

public class CardStack {
	private ChanceCard card;
	private ArrayList<String> texts;
	private ArrayList<ChanceCard> chanceCardDeck;
//	private Texts text = new Texts();


	public CardStack(){
		chanceCardDeck = new ArrayList<ChanceCard>();

		for(int i = 0; i<40; i++) {
//			this.card = new ChanceCard(text.getString(i));
			chanceCardDeck.add(card);
		}
	}

	public void shuffle() {
		Collections.shuffle(chanceCardDeck);
	}

	public String draw(Player player) {
		ChanceCard temp = chanceCardDeck.get(0);

		if (temp.isOwnable()==true) {
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

}
