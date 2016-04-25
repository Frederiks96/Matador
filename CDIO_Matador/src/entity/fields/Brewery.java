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
	
	public Brewery(int fieldID, Texts text) {
		super(fieldID);
		this.owner = null;
		this.isMortgaged = false;
		this.price = 3000;
		this.name = (String) text.getRent(fieldID+"_name");
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
		owner.addBrewery();
	}

	public int getRent() {
		return owner.getBreweriesNum()*dicecup.getLastRoll()*100;
	}

	public boolean isOwned() {
		return this.owner.equals(null);
	}
	
	public void buyProperty(Player player) {
		myGUI.showMessage(player.updateBalance(-this.price));
		if(player.getAccount().legalTransaction(-this.price)){
			myGUI.setOwner(this.fieldID, player.getName());
			setOwner(player);
		}
	}

	@Override
	public void landOnField(Player player, Texts text) {
		
		if(owner.equals(null)){
			String s = myGUI.getUserSelection(text.getFormattedString("buy", this.price), 
											  text.getString("Yes"), text.getString("No"));					  						
			if (s.equals(text.getString("Yes"))) {
				buyProperty(player);
			}
		}
	
		if(!isMortgaged && !this.owner.equals(player)){
			player.updateBalance(-getRent());
			owner.updateBalance(getRent());
		}

	}

	@Override
	public void mortgage() {
		this.isMortgaged=true;
	}

	@Override
	public void unMortgage() {
		this.isMortgaged=false;
	}
	
	public String getName() {
		return this.name;
	}

}
