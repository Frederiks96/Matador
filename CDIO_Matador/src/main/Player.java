package main;

public class Player {
	
	private String name;
	private int player_id; 							//--> Jeg forestiller mig, at vi i databasen laver 2 nuller foran,
	private int position;
	private static int numOfPlayers = 0;			//    så det kommer til at hedde 001, 002, osv..
	private int numFleetsOwned;
	private int numBreweriesOwned;
	private boolean isAlive;
	private Account account;
	
	public Player(String name) {
		this.name=name;
		this.numFleetsOwned = 0;
		this.numBreweriesOwned = 0;
		this.position = 0;
//		createPlayer();
		numOfPlayers++;
		this.player_id = numOfPlayers;
		this.isAlive=true;
		this.account = new Account();
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
	

}
