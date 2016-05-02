package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class Territory extends AbstractFields implements Ownable {

	private GUI_Commands myGUI = new GUI_Commands();   // spørg Fin hvad forskellen på at lave instanser af objekter og putte objeter ind i parameter listen 

	private Player owner;
	private int houseCount;
	private String colour;
	private int[] rent = new int[6];
	private int price;
	private int housePrice;
	private boolean isMortgaged;
	private String name;
	
	public Territory(int id, Player owner, Texts text){
		super(id);
		this.owner = null;
		this.houseCount = 0;
		this.colour = (String) text.getInfo(id+"_color");
		this.isMortgaged = false;
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
				buyProperty(player, text);
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

	private void buyHouse(Texts text){ 
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount < 4 && !isMortgaged){
			owner.updateBalance(-housePrice);
			myGUI.setHouse(id, houseCount+1);
			this.houseCount++;
		}		
		else if (isMortgaged)
			myGUI.showMessage("failedMortgage");
		
		else if(houseCount == 4)
			myGUI.showMessage(text.getString("houseOverLoad"));
		
		else if(!owner.getAccount().legalTransaction(-housePrice))
			myGUI.showMessage(text.getString("faildTransaction"));
	}
	
	private void buyHotel(Texts text){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount == 4){
			owner.updateBalance(-housePrice);
			myGUI.setHotel(id, true);
			this.houseCount++;
		}
		else if (isMortgaged)
			myGUI.showMessage(text.getString("failedMortgage"));
		
		else if(houseCount < 4)
			myGUI.showMessage(text.getString("needMoreHouse"));
		
		else if(!owner.getAccount().legalTransaction(-housePrice))
			myGUI.showMessage(text.getString("faildTransaction"));
		else if(houseCount == 5) {
			myGUI.showMessage(text.getString("doubleHotel"));
		}
	}

	private void sellHouse(){
		if(houseCount>1){
			owner.updateBalance(housePrice/2);
			myGUI.setHouse(id, houseCount-1);
			this.houseCount--;
		}

	}

	private void sellHotel(){
		if(houseCount == 5){
			owner.updateBalance(housePrice/2);
			myGUI.setHotel(id, false);
			this.houseCount--;
			myGUI.setHouse(id, houseCount);
		}
	}

	@Override
	public void buyProperty(Player player, Texts text){
		if (player.getAccount().legalTransaction(-this.price)){
			player.updateBalance(-this.price);
			this.owner = player;
			myGUI.setOwner(this.id, player.getName());
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
		
	@Override
	public String getName() {
		return this.name;
	}
	
	public boolean isMortgaged() {
		return this.isMortgaged;
	}
	
	public int getID() {
		return this.id;
	}
	
	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}

	@Override
	public int getHouseCount() {
		// TODO Auto-generated method stub
		return houseCount;
	}
	
	public int getPrice() {
		return price;
	}
	
}
