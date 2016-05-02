package entity.fields;

import boundary.GUI_Commands;
import entity.GameBoard;
import entity.Player;
import entity.Texts;

public class Territory extends AbstractFields implements Ownable {

	
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
	public void landOnField(Player player, Texts text, GUI_Commands gui) {
		if (this.owner==null) {
			// der er ingen der ejer feltet
			String s = gui.getUserSelection(text.getFormattedString("buy", this.price), text.getString("Yes"), text.getString("No"));
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player, text, gui);
			}
		}

		if (!isMortgaged && this.owner!=player){
			//en anden ejer felet og det er ikke pantsat
			gui.showMessage(text.getFormattedString("rent", getRent(), owner));
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

	public void buyHouse(Texts text, GUI_Commands gui){ 
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount < 4 && !isMortgaged){
			owner.updateBalance(-housePrice);
			gui.setHouse(id, houseCount+1);
			this.houseCount++;
		}		
		else if (isMortgaged)
			gui.showMessage("failedMortgage");

		else if(houseCount == 4)
			gui.showMessage(text.getString("houseOverLoad"));

		else if(!owner.getAccount().legalTransaction(-housePrice))
			gui.showMessage(text.getString("faildTransaction"));
	}

	public void buyHotel(Texts text,GUI_Commands gui){	// has hotel betyder om der skal sættes eller fjernes hotel
		if(owner.getAccount().legalTransaction(-housePrice) && houseCount == 4){
			owner.updateBalance(-housePrice);
			gui.setHotel(id, true);
			this.houseCount++;
		}
		else if (isMortgaged)
			gui.showMessage(text.getString("failedMortgage"));

		else if(houseCount < 4)
			gui.showMessage(text.getString("needMoreHouse"));

		else if(!owner.getAccount().legalTransaction(-housePrice))
			gui.showMessage(text.getString("faildTransaction"));
		else if(houseCount == 5) {
			gui.showMessage(text.getString("doubleHotel"));
		}
	}

	private void sellHouse(GUI_Commands gui){
		if(houseCount>1){
			owner.updateBalance(housePrice/2);
			gui.setHouse(id, houseCount-1);
			this.houseCount--;
		}

	}

	private void sellHotel(GUI_Commands gui){
		if(houseCount == 5){
			owner.updateBalance(housePrice/2);
			gui.setHotel(id, false);
			this.houseCount--;
			gui.setHouse(id, houseCount);
		}
	}

	@Override
	public void buyProperty(Player player, Texts text, GUI_Commands gui){
		if (player.getAccount().legalTransaction(-this.price)){
			player.updateBalance(-this.price);
			this.owner = player;
			gui.setOwner(this.id, player.getName());
			player.updateNumTerritoryOwned(1);
		}	
	}
	@Override
	public void sellPproperty(Player player){
		
		
		player.updateNumTerritoryOwned(-1);
	}

	@Override
	public void mortgage(Texts text, GUI_Commands gui) { 
		if (houseCount == 0) {
			isMortgaged = true;
			owner.updateBalance((int)(price*0.5));
		}
		else {
			gui.showMessage(text.getString("errorHouseOnField"));
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
		return houseCount;
	}

	public int getPrice() {
		return price;
	}
	
}
