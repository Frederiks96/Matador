package entity;

import java.util.ArrayList;

import desktop_resources.GUI;

/**
 * Class containing player values, like position number of properties owned and if the player is bankrupt
 * @author Benjamin Jensen
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
	 * 
	 * @return the player_id of the Player object
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
	 * Decrements numBreweriesOwned
	 */
	public void sellBrewery() {
		this.numBreweriesOwned--;
	}
	/**
	 * 
	 * @return numBreweriesOwned
	 */
	public int getNumBreweriesOwned() {
		return this.numBreweriesOwned;
	}
	/**
 	 * Decrements numBreweriesOwned
 	 */
	public void mortgageBrewery(){
		numBreweriesOwned--;
	}
	/**
	 * Decrements numFleetsOwned	
	 */
	public void mortgageFleet(){
		numFleetsOwned--;
	}
	/**
	 * 
	 * @return if player isAlive
	 */
	public boolean isAlive() {
		return this.isAlive;
	}
	/**
	 * 
	 * @return Player's position
	 */
	public int getPosition() {
		return this.position;
	}
	/**
	 * 
	 * @return Player's account
	 */
	public int getAccountID(){
		return account.getAccountID();
	}
	/**
	 * Sets the position of a player	
	 * @param position of the player on the gameboard
	 */
	public void setPosition(int position) {
		if (position>0 && position<40) {
			this.position = position;
		}
	}
	/**
	 * Updates the position of a player and gives the player 4000 if he passes 39
	 * @param lastRoll the player made
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
	 * 
	 * @return Player's Balance 
	 */
	public int getBalance() {
		return account.getBalance();
	}
	/**
	 * 
	 * @param d The amount to be added or subtracted from the player's balance
	 * @return the player's new balance
	 */
	public Boolean updateBalance(int d) { 
		return account.updateBalance(d);
	}
	/**
	 * Gives a player a chance card
	 * @param card from the chance card stack
	 */
	public void giveCard(ChanceCard card) {
		this.cards.add(card);
	}
	/**
	 * 
	 * @return the chance card if the player has one, otherwise returns null
	 */
	public ChanceCard getCard() {
		if (this.cards.size()>0) {
			return this.cards.get(0);
		}
		else 
			return null;
	}
	/**
	 * Player takes a card from the card stack
	 */
	public void takeCard() {
		this.cards.remove(0);
	}
	/**
	 * Shows if the player has a card
	 * @return how many cards the player has
	 */
	public boolean hasCards() {
		return this.cards.size()!=0;
	}
	/**
	 * 
	 * @return the player's name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * puts a player in jail
	 */
	public void imprison() {
		this.jailTime++;
		setPosition(10);
	}
	/**
	 * Gets the players account
	 * @return the players account
	 */
	public Account getAccount() {
		return this.account;
	}
	/**
	 * Shows how many turns the player have been in jail
	 * @return the rounds the player have been in jail
	 */
	public int getJailTime() {
		return this.jailTime;
	}
	/**
	 * shows if it's the player's turn
	 * @return if it's the players turn
	 */
	public boolean isTurn() {
		return this.turn;
	}
	/**
	 * set's turn to a player
	 * @param turn who's turn it is
	 */
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	/**
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
	 * Decrements numTerriroriesOwned
	 */
	public void sellTerritory() {
		numTerritoriesOwned--;
	}
	/**
	 * Gives a color to the player's vehicle
	 * @return the player's vehicle's color
	 */
	public String getVehicleColour() {
		return this.vColor;
	}
	/**
	 * Gives the player's vehicle a type
	 * @return the player's vehicle's type
	 */
	public String getVehicleType() {
		return this.vType;
	}
	/**
	 * Gives the player's vehicle an ID
	 * @return the player's vehicle's ID
	 */
	public int getVehicleID() {
		return this.vID;
	}
	/**
	 * Shows if the player is bankrupt	
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
	 * 	
	 * @param jail_time how many rounds have been spent in jail
	 */
	public void setJailTime(int jail_time) {
		this.jailTime=jail_time;
	}
	/**
	 * resets jailTime by setting jailTime to -1
	 */
	public void resetJailTime() {
		this.jailTime=-1;
	}
	/**
	 * 	Sets Balance to newBalance
	 * @param newBalance is the updated balance
	 */
	public void setBalance(int newBalance) {
		this.account.setBalance(newBalance);
	}
	/**
	 * Checks if the player is still in the game	
	 * @param isAlive checks if the player is bankrupt or still in the game
	 */
	public void setIsAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	
}
