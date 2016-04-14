package fields;

import dicecup.DiceCup;
import main.Player;

public class Brewery extends AbstractFields implements Ownable {
	
	private Player owner;
	private DiceCup dicecup;
	
	public Brewery(int id) {
		super(id);
		this.owner = null;
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

	@Override
	public void landOnField(Player player) {
		// TODO Auto-generated method stub
		
	}

}
