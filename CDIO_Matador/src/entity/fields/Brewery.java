package entity.fields;

import boundary.GUI_Commands;
import entity.Player;
import entity.Texts;
import entity.dicecup.DiceCup;

public class Brewery extends AbstractFields implements Ownable {
	
	private Player owner;
	private DiceCup dicecup;
	private int price;
	private GUI_Commands myGUI = new GUI_Commands();
	private boolean isMortgaged;
	private String name;
	
	public Brewery(int id, Player owner, Texts text) {
		super(id);
		this.owner = null;
		this.isMortgaged = false;
		this.price = 3000;
		this.name = (String) text.getInfo(id+"_name");
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		owner.addBrewery();
	}

	public int getRent() {
		return owner.getNumBreweriesOwned()*dicecup.getLastRoll()*100;
	}

	public boolean isOwned() {
		return this.owner.equals(null);
	}
	
	public void buyProperty(Player player, Texts text) {
		if(player.getAccount().legalTransaction(-price)){
			player.updateBalance(-price);
			myGUI.setOwner(this.id, player.getName());
			setOwner(player);
		}
		else
			myGUI.showMessage(text.getString("failedTransaction"));
	}

	@Override
	public void landOnField(Player player, Texts text) {
		
		if(owner.equals(null)){
			String s = myGUI.getUserSelection(text.getFormattedString("buy", this.price), 
											  text.getString("Yes"), text.getString("No"));					  						
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player, text);
			}
		}
	
		if(!isMortgaged && !this.owner.equals(player)){
			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}

	}

	public void mortgage(Texts text) {
		this.isMortgaged=true;
		owner.mortgageBrewery();
		owner.updateBalance((int)(this.price*0.5));
	}

	public void unMortgage() {
		this.isMortgaged=false;
		owner.addBrewery();
		owner.updateBalance(-(int)(this.price*0.5*1.1));
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	public boolean isMortgaged() {
		return this.isMortgaged;
	}

	@Override
	public int getID() {
		return id;
	}

}
