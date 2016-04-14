package fields;

import main.Player;

public class Territory extends AbstractFields implements Ownable {
	
	private Player owner;
	private int houseCount;
	
	public Territory(int id) {
		super(id);
		this.owner = null;
		this.houseCount = 0;
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
		// Undersøger om alle grunde er ejet i en farvekombination
		// Undersøger herefter om der er hus på den pågældende grund
		// Beregner/henter herefter lejen fra en fil?
		return 0;
	}

	@Override
	public boolean isOwned() {
		return this.owner == null;
	}

	@Override
	public void landOnField(Player player) {
		if (this.owner==null) {
			// Ingen ejer
		}
		else if (this.owner!=player) {
			// En anden spiller ejer feltet
		}
		else {
			// Spilleren ejer selv feltet
		}
	}
	
	public int getHouseCount() {
		return this.houseCount;
	}
	
	public boolean hasAll() {
		// Returnerer true, hvis en spiller ejer alle felterne, inden for den specifikke farve
		return false;
	}

}
