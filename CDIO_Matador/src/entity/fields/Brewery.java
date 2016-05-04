package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;
import entity.dicecup.DiceCup;

public class Brewery extends AbstractFields implements Ownable {
	
	private Player owner;
	private DiceCup dicecup;
	private int price;
	private boolean isMortgaged;
	private String name;
	
	public Brewery(int id, Player owner, Texts text) {
		super(id);
		this.owner = null;
		this.isMortgaged = false;
		this.price = 3000;
		this.name = (String) text.getInfo(id+"_name");
	}

	@Override
	public void landOnField(Player player, boolean buy, Texts text, GUI_Commands gui) {
		
		if (buy){ // The Brewery is owned and the Player wishes to buy it
			buyProperty(player, text, gui);
		}
	
		else if(!isMortgaged && owner!=player && isOwned()){
			gui.showMessage(text.getFormattedString("rent", getRent(), owner));
			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}
	}
	
	public void buyProperty(Player player, Texts text, GUI_Commands gui) {
		if(player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			gui.setOwner(this.id, player.getName());
			setOwner(player);
		}
		else
			gui.showMessage(text.getString("failedTransaction"));
	}
	
	@Override
	public void sellProperty(Player player) {
		player.sellBrewery();
	}
	
	public void mortgage(Texts text, GUI_Commands gui) {
		this.isMortgaged=true;
		owner.mortgageBrewery();
		owner.updateBalance((int)(this.price*0.5));
	}

	public void unMortgage() {
		this.isMortgaged=false;
		owner.addBrewery();
		owner.updateBalance(-(int)(this.price*0.5*1.1));
	}
	
	public boolean isMortgaged() {
		return this.isMortgaged;
	}

	public boolean isOwned() {
		return this.owner != null;
	}
			
	public void setOwner(Player owner) {
		this.owner = owner;
		owner.addBrewery();
	}
	
	public Player getOwner() {
		return this.owner;
	}

	public int getRent() {
		return owner.getNumBreweriesOwned()*dicecup.getLastRoll()*100;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public int getID() {
		return id;
	}

	@Override
	public int getPrice() {
		return price;
	}



}
