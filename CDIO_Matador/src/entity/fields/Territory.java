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
	private int[] rent = new int[6];
	private int price;
	private int housePrice;
	private boolean isMortgaged;
	private String name;

	public Territory(int id, Texts text){
		super(id);
		this.owner = null;
		this.houseCount = 0;
		this.colour = (String) text.getInfo(id+"_color");
		this.isMortgaged = false;
		this.hasHotel = false;
		this.name = (String) text.getInfo(id+"_name");

		this.price = (int) text.getInfo(id+"_price");
		this.housePrice = (int) text.getInfo(id+"_house");
		
		for (int i = 0; i < rent.length; i++) {
			this.rent[i] = (int) text.getInfo(id+"_"+i);
		}
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
	
		if (!isMortgaged && this.owner!=player){
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
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount < 4 && !isMortgaged){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHouse(fieldID, houseCount+1);
			this.houseCount++;
		}
	}

	private void buyHotel(){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount == 4 && !isMortgaged){
			myGUI.showMessage(owner.updateBalance(-housePrice));
			myGUI.setHotel(fieldID, true);
			this.hasHotel = true;
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
	public void mortgage(Texts text) { 
		if (houseCount == 0) {
			isMortgaged = true;
			owner.updateBalance((int)(price*0.5));
		}
		else {
			myGUI.showMessage(text.getString("errorHouseOnField"));
		}
	}

	public void unMortgage() {
		isMortgaged = false;
		this.owner.updateBalance(-(int)(this.price*0.5*1.1));
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
	
	public boolean hasHotel() {
		return this.hasHotel;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public boolean isMortgaged() {
		return this.isMortgaged;
	}

}
