package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class Territory extends AbstractFields implements Ownable {

	GUI_Commands myGUI = new GUI_Commands();   // spørg Fin hvad forskellen på at lave instanser af objekter og putte objeter ind i parameter listen 

	private Player owner;
	private int houseCount;
	private boolean hasHotel; 
	private String colour;
	private int[] rent;
	private int price;
	private int housePrice;
	private boolean mortgaged;

	public Territory(int fieldID, String colour, int price, int baseRent, 
			int rent1, int rent2, int rent3, int rent4, int hotelRent, int housePrice){

		super(fieldID);
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
			// der er ingen der ejer feltet
			String s = myGUI.getUserSelection(text.getFormattedString("buy", this.price), text.getString("Yes"), text.getString("No"));
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player);
			}
		}

		if (!mortgaged && this.owner!=player){
			//en anden ejer felet og det er ikke pantsat
			myGUI.showMessage(text.getFormattedString("rent", getRent(), owner));
			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}
	}


	public int getRent() {
		if(owner.hasAll(colour) && houseCount == 0)
			return this.rent[0]*2;
		else
			return this.rent[this.houseCount];	
	}

	public boolean isOwned() {
		return this.owner != null;
	}

	private void buyHouse(){	// housecount betyer hvor mange huse der skal stå på grunden
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount < 4 && !mortgaged){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHouse(fieldID, houseCount+1);
			this.houseCount++;
		}
	}

	private void buyHotel(){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount == 4 && !mortgaged){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHotel(fieldID, true);
			this.houseCount++;
		}
	}

	private void sellHouse(){
		if(houseCount>1){
			myGUI.showMessage(owner.updateBalance(housePrice/2));
			myGUI.setHouse(fieldID, houseCount-1);
			this.houseCount--;
		}

	}

	private void sellHotel(){
		if(houseCount == 5){
			myGUI.showMessage(owner.updateBalance(housePrice/2));
			myGUI.setHotel(fieldID, false);
			this.houseCount--;
			myGUI.setHouse(fieldID, houseCount);
		}
	}

	@Override
	public void buyProperty(Player player){
		if (player.getAccount().legalTransaction(-this.price)){
			myGUI.showMessage(player.updateBalance(-this.price));
			this.owner = player;
			myGUI.setOwner(this.fieldID, player.getName());
		}	
	}

	@Override
	public void mortgage() { 
		if( houseCount == 0)
			mortgaged = true;	
	}

	public void unMortgage() {
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

}