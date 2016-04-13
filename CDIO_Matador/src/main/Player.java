package main;

import java.util.ArrayList;

public class Player {

	private String name;
	private int player_id; 							//--> Jeg forestiller mig, at vi i databasen laver 2 nuller foran,
	private int position;
	private static int numOfPlayers = 0;			//    så det kommer til at hedde 001, 002, osv..
	private int numFleetsOwned;
	private int numBreweriesOwned;
	private boolean isAlive;
	private Account account;
	private ArrayList<ChanceCard> cards;

	public Player(String name) {
		this.name = name;
		this.numFleetsOwned = 0;
		this.numBreweriesOwned = 0;
		this.position = 0;
		//		createPlayer();
		numOfPlayers++;
		this.player_id = numOfPlayers;
		this.isAlive=true;
		this.account = new Account();
		this.cards = new ArrayList<ChanceCard>();
	}

	public int getPlayerID() {
		return this.player_id;
	}

	public int getFleets() {
		return numFleetsOwned;
	}

	public void setFleets() {
		this.numFleetsOwned++;
	}

	private void createPlayer(Player player) {
		// Opretter spilleren i databasen. Laves private, da det er en hjælpemetode,
		// eller skal den ligge implicit i konstruktøren?
	}

	public int getBreweries() {
		return this.numBreweriesOwned;
	}

	public void turn() {
		// Spillerens tur
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getBalance() {
		return account.getBalance();
	}

	public void updateBalance(int d) { 
		account.updateBalance(d);
	}

	public void giveCard(ChanceCard card) {
		this.cards.add(card);
		System.out.println("John har nu et benådningskort");
		System.out.println(cards.get(0).toString());
	}

	public ChanceCard getCard() {
		if (this.cards.size()>0) {
			return this.cards.get(0);
		}
		else 
			return null;
	}

	public void takeCard() {
		this.cards.remove(0);
	}


}
