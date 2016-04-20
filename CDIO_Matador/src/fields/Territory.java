package fields;

import main.ControllerGUI;
import main.GameBoard;
import main.Player;
import entity.Texts;

public class Territory extends AbstractFields implements Ownable {
	
	ControllerGUI myGUI = new ControllerGUI();
	GameBoard gameboard = new GameBoard(null);
	private Player owner;
	private int houseCount;
	private boolean hasHotel; 
	private String colour;
	private int[] rent;
	private int price;
	private int housePrice;
	private boolean mortgaged;

	public Territory(int id, String colour, int price, int baseRent, 
			int rent1, int rent2, int rent3, int rent4, int hotelRent, int housePrice){
		
		super(id);
		this.owner = null;
		this.houseCount = 0;
		this.colour = colour;
		this.mortgaged = false;
		
		this.price = price;
		this.rent[0] = baseRent;
		this.rent[1] = rent1;
		this.rent[2] = rent2;
		this.rent[3] = rent3;
		this.rent[4] = rent4;
		this.rent[5] = hotelRent;
		this.housePrice = housePrice;
	}

	@Override
	public void landOnField(Player player, Texts text) {
		if (this.owner==null) {
			String s = myGUI.getUserSelection(text.getFormattedString("buy", this.price), text.getString("Yes"), text.getString("No"));
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player);
			}
		}
		
		if (!mortgaged && this.owner!=player){
			
		}
		
		else if (this.owner!=player) {
			// En anden spiller ejer feltet
			myGUI.showMessage(text.getFormattedString("rent", rent[houseCount], owner));
		}
		else {
			// Spilleren ejer selv feltet
		}
	}
	

	public int getRent(Player player) {
//		if(player.allOwned() && houseCount == 0)
//			return this.rent[0]*2;
//		else
			return this.rent[this.houseCount];	
	}

	public boolean isOwned() {
		return this.owner != null;
	}

	
	private void buyHouse(int i){	// housecount betyer hvor mange huse der skal stå på grunden
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount+i <= 4){
			myGUI.showMessage(owner.updateBalance(-i*housePrice));
			myGUI.setHouse(fieldID, houseCount+i);
			this.houseCount+=i;
		}
	}
		
	private void buyHotel(){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount == 4){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHotel(fieldID, true);
			this.houseCount++;
		}
	}
	
	private void sellHouse(int i){
		if(houseCount>=i){
			myGUI.showMessage(owner.updateBalance(i*housePrice/2));
			myGUI.setHouse(fieldID, houseCount-i);
			this.houseCount-=i;
 		}
			
	}
	
	private void sellHotel(){
		if(houseCount == 5){
			myGUI.showMessage(owner.updateBalance(housePrice/2));
			myGUI.setHotel(fieldID, false);
			this.houseCount--;
		}
	}
	
	
	public int getHouseCount() {	//unødvendig???
		return this.houseCount;
	}
	
	

	@Override
	public void buyProperty(Player player){
		
		if (player.getAccount().legalTransaction(-this.price)){
			myGUI.showMessage(player.updateBalance(-this.price));
			this.owner = player;
			myGUI.setOwner(this.fieldID, player.getName());
			player.setFleets();
		}	
	}

	@Override
	public void mortage() {   // stavefejl
		mortgaged = true;	
	}

	@Override
	public void unMortage() {  // stavefejl
		mortgaged = false;		
	}
	
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}

	public String getColour(){
		return colour;
	}

	@Override
	public int getRent() {
		// Unødvendig
		return 0;
	}

	
}
