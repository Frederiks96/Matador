package fields;

import dicecup.DiceCup;
import entity.Texts;
import main.ControllerGUI;
import main.Player;

public class Brewery extends AbstractFields implements Ownable {
	
	private Player owner;
	private DiceCup dicecup;
	int Price = 3000;
	private ControllerGUI myGUI = new ControllerGUI();
	private boolean isMortgaged;
	
	public Brewery(int id) {
		super(id);
		this.owner = null;
		this.isMortgaged=false;
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
		this.owner = player;
		player.updateBalance(Price);
		myGUI.showMessage(player.updateBalance(Price));
		
	}

	@Override
	public void landOnField(Player player, Texts text) {
		
		
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
