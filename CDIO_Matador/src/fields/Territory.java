package fields;

import main.Player;

public class Territory extends AbstractFields implements Ownable {
	
	private Player owner;
	
	public Territory(int id) {
		super(id);
		this.owner = null;
	}

	@Override
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public void setOwner(Player owner) {
		this.owner = owner;
	}

	@Override
	public int getRent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isOwned() {
		return this.owner == null;
	}

}
