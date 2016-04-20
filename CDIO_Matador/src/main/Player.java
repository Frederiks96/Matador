package main;

import java.sql.SQLException;
import java.util.ArrayList;

import desktop_resources.GUI;
import entity.SQL;
import fields.AbstractFields;
import fields.Territory;

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
	private ControllerGUI myGUI = new ControllerGUI();
	

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
	
	public void sellFleets() {
		this.numFleetsOwned--;
	}

	public int getBreweries() {
		return this.numBreweriesOwned;
	}
	
	public void setBreweries() {
		this.numFleetsOwned++;
	}
	
	public void sellBreweries() {
		this.numFleetsOwned--;
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

	public String updateBalance(int d) { 
		return account.updateBalance(d);
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
	
	public void imprison() {
		this.jailTime++;
		setPosition(10);
		myGUI.setCar(10, this.name);
	}

	public Account getAccount(){
		return this.account;
	}

	
	public boolean allOwned(AbstractFields field) {
		// Returnerer true, hvis en spiller ejer alle felterne, inden for den specifikke farve
		if(field.colour.equals("BLUE") || colour.equals("PURPLE")){
			int j = 0;
			for(int i = 0; i<40; i++){
				if(gameboard.getLogicField()[i] instanceof Territory){
					if (gameboard.getLogicField().getColour() == colour && gameboard.getLogicField().getOwner() == owner ){
						j++;
					}
				}
			}  
			return 2 == j;
		}
		
		else{
			int j;
			for(int i = 0; i<40; i++){
				if(gameboard.getLogicField()[i] instanceof Territory){
					if (gameboard.getLogicField().getColour() == colour && gameboard.getLogicField().getOwner() == owner ){
						j++;
					}
				}
			}
			return 3 == j;
		}		
	}
	
}
