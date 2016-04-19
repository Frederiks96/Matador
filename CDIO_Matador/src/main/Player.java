package main;

import java.sql.SQLException;
import java.util.ArrayList;

import entity.SQL;

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
	private String vColor;
	private String vType;
	private int accountID;
	private int vID;
	private int jailTime;
	private SQL sql = new SQL();
	

	public Player(String name, String vColor, String vType) throws SQLException {
		numOfPlayers++;
		this.name = name;
		this.numFleetsOwned = 0;
		this.numBreweriesOwned = 0;
		this.position = 0;
		this.player_id = numOfPlayers;
		this.vID = numOfPlayers*11;
		this.accountID = numOfPlayers*111;
		this.jailTime = -1;
		this.vColor = vColor;
		this.vType = vType;
		this.isAlive=true;
		this.account = new Account();
		this.cards = new ArrayList<ChanceCard>();
		
		sql.createPlayer(player_id, name, position, jailTime, isAlive,accountID,this.getBalance(),vID,vColor,vType);
	}

	public int getPlayerID() {
		return this.player_id;
	}

	public int getFleets() {
		return this.numFleetsOwned;
	}

	public void setFleets() {
		this.numFleetsOwned++;
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
		return this.position;
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
	
	public boolean hasCards() {
		return this.cards.size()!=0;
	}
	
	public String getName() {
		return this.name;
	}


}
