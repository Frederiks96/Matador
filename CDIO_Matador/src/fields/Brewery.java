package fields;

import dicecup.DiceCup;
import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Brewery extends AbstractFields implements Ownable {
	
	private Player owner;
	private DiceCup dicecup;
	private int price;
	private ControllerGUI myGUI = new ControllerGUI();
	private boolean isMortgaged;
	
	public Brewery(int id) {
		super(id);
		this.owner = null;
		this.isMortgaged=false;
		this.price = 3000;
	}

	public Player getOwner() {
		return this.owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getRent() {
		return owner.getBreweries()*dicecup.getLastRoll()*100;
	}

	public boolean isOwned() {
		return this.owner == null;
	}
	
	public void buyProperty(Player player) {
		if(player.getAccount().legalTransaction(this.price)){
			this.owner = player;
			player.updateBalance(price);
			myGUI.showMessage(player.updateBalance(price));
			player.setBreweries();
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
	public void mortage() {
		this.isMortgaged=true;
	}

	@Override
	public void unMortage() {
		this.isMortgaged=false;
	}

}
