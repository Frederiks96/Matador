package entity;

import java.util.ArrayList;

import desktop_resources.GUI;

/**
 * Class that manages the Player objects
 * @author Frederik
 *
 */
public class Player {

	private String name;
	private int player_id; 							//--> Jeg forestiller mig, at vi i databasen laver 2 nuller foran,
	private int position;
	private static int numOfPlayers = 0;			//    så det kommer til at hedde 001, 002, osv..
	private int numFleetsOwned;
	private int numBreweriesOwned;
	private int numTerritoriesOwned;
	private boolean isAlive;
	private boolean turn;
	private Account account;
	private ArrayList<ChanceCard> cards;
	private String vColor;
	private String vType;
	private int vID;
	private int jailTime;


	public Player(String name, String vColor, String vType) {
		numOfPlayers++;
		this.name 				= name;
		this.numFleetsOwned 	= 0;
		this.numBreweriesOwned 	= 0;
		this.position 			= 0;
		this.player_id 			= numOfPlayers;
		this.vID 				= numOfPlayers*11;
		this.jailTime 			= -1;
		this.vColor 			= vColor;
		this.vType 				= vType;
		this.isAlive			= true;
		this.account 			= new Account(player_id);
		this.cards 				= new ArrayList<ChanceCard>();
		this.turn 				= false;

	}
	
	/**
	 * Returns the ID of the player
	 * 
	 * @return player_id – The ID of the player
	 */
	public int getPlayerID() {
		return this.player_id;
	}
	
	/**
	 * Increments numFleetsOwned
	 */
	public void addFleet() {
		this.numFleetsOwned++;
	}
	
	/**
	 * Decrement numFleetsOwned
	 */
	public void sellFleet() {
		this.numFleetsOwned--;
	}
	
	/**
	 * Returns the number of Fleets owned by the player
	 * 
	 * @return numFleetsOwned
	 */
	public int getNumFleetsOwned() {
		return this.numFleetsOwned;
	}
	
	/**
	 * Increments numBreweriesOwned
	 */
	public void addBrewery() {
		this.numBreweriesOwned++;
	}
	
	/**
	 * Decrements numBreweriesOwned in the event of a sale
	 */
	public void sellBrewery() {
		this.numBreweriesOwned--;
	}
	
	/**
	 * Returns the number of Breweries owned
	 * 
	 * @return numBreweriesOwned
	 */
	public int getNumBreweriesOwned() {
		return this.numBreweriesOwned;
	}
	
	/**
 	 * Decrements numBreweriesOwned in the event of mortgage
 	 */
	public void mortgageBrewery(){
		numBreweriesOwned--;
	}
	
	/**
	 * Decrements numFleetsOwned in the event of mortgage
	 */
	public void mortgageFleet(){
		numFleetsOwned--;
	}
	
	/**
	 * Returns the isAlive attribute of the player
	 * 
	 * @return True if the player is alive
	 */
	public boolean isAlive() {
		return this.isAlive;
	}
	
	/**
	 * Returns the position af a player
	 * 
	 * @return position
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * Returns the accountID of the player's account
	 * 
	 * @return accountID
	 */
	public int getAccountID(){
		return account.getAccountID();
	}
	
	/**
	 * Sets the position of a player
	 * 	
	 * @param position
	 */
	public void setPosition(int position) {
		if (position>0 && position<40) {
			this.position = position;
		}
	}
	
	/**
	 * Updates the position of a player and updates the player's balance with 4000 if he passes START
	 * 
	 * @param lastRoll – The player's last roll
	 */
	public void updatePosition(int lastRoll) {
			if ((position+lastRoll)>39) {
				updateBalance(4000);
				GUI.setBalance(name, account.getBalance());
				position += lastRoll-40;
			} else {
				position += lastRoll;
			}
	}
	
	/**
	 * Returns the balance of a player
	 * 
	 * @return balance
	 */
	public int getBalance() {
		return account.getBalance();
	}
	
	/**
	 * Returns whether an update has been made
	 * 
	 * @param d The amount to be added or subtracted from the player's balance
	 * @return True if the transaction has been made, otherwise false
	 */
	public boolean updateBalance(int d) { 
		return account.updateBalance(d);
	}
	
	/**
	 * Gives a player a card
	 * @param card – The card to be given to the player
	 */
	public void giveCard(ChanceCard card) {
		this.cards.add(card);
	}
	
	/**
	 * Returns the card of a player, if he holds one. Otherwise returns null
	 * 
	 * @return Chancecard
	 */
	public ChanceCard getCard() {
		if (this.cards.size()>0) {
			return this.cards.get(0);
		}
		else 
			return null;
	}
	
	/**
	 * Removes a card from the player, if he holds one
	 */
	public void takeCard() {
		this.cards.remove(0);
	}
	
	/**
	 * Returns whether a player holds a card
	 * 
	 * @return how many cards the player has
	 */
	public boolean hasCards() {
		return this.cards.size()!=0;
	}
	
	/**
	 * Returns the name of the player
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Increments the player's jailTime and sets his/hers position to 10
	 */
	public void imprison() {
		this.jailTime++;
		setPosition(10);
	}
	
	/**
	 * Returns the account of a player
	 * 
	 * @return account
	 */
	public Account getAccount() {
		return this.account;
	}
	
	/**
	 * Returns the jailTime of a player
	 * 
	 * @return jailTime
	 */
	public int getJailTime() {
		return this.jailTime;
	}
	
	/**
	 * Return the turn attribute of a player
	 * 
	 * @return True if it is the player's turn, otherwise false
	 */
	public boolean isTurn() {
		return this.turn;
	}
	
	/**
	 * Sets a players turn
	 * 
	 * @param turn – Whether it is a player's turn or not
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	/**
	 * Returns the number of Territories owned by a player
	 * 
	 * @return numTerritoriesOwned
	 */
	public int getNumTerritoriesOwned() {
		return numTerritoriesOwned;
	}
	
	/**
	 * Increments numTerriotriesOWned
	 */
	public void addTerritory() {
		numTerritoriesOwned++;
	}
	
	/**
	 * Decrements numTerriroriesOwned in the event of a sale
	 */
	public void sellTerritory() {
		numTerritoriesOwned--;
	}
	
	/**
	 * Returns the colour of a player's vehicle
	 * 
	 * @return color
	 */
	public String getVehicleColour() {
		return this.vColor;
	}
	
	/**
	 * Returns the type of the player's vehicle
	 * 
	 * @return type
	 */
	public String getVehicleType() {
		return this.vType;
	}
	
	/**
	 * Returns the ID of the player's vehicle
	 * 
	 * @return the ID of the player's vehicle
	 */
	public int getVehicleID() {
		return this.vID;
	}
	
	/**
	 * Declares the player as bankrupt	
	 */
	public void bankrupt() {
		this.isAlive 				= false;
		this.numBreweriesOwned 		= 0;
		this.numFleetsOwned 		= 0;
		this.numTerritoriesOwned 	= 0;
	}
	
	/**
	 * Increments jailTime
	 */
	public void increaseJailTime() {
		this.jailTime++;
	}
	
	/**
	 * Sets a player's jailTime to a specific value
	 * 	
	 * @param jail_time
	 */
	public void setJailTime(int jail_time) {
		this.jailTime=jail_time;
	}
	
	/**
	 * Resets a player's jailTime
	 */
	public void resetJailTime() {
		this.jailTime=-1;
	}
	
	/**
	 * 	Sets a player's balance to newBalance
	 * @param newBalance
	 */
	public void setBalance(int newBalance) {
		this.account.setBalance(newBalance);
	}
	
	/**
	 * Sets a player's isAlive attribute to isAlive
	 * 	
	 * @param isAlive
	 */
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
}
