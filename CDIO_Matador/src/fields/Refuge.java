package fields;

import main.Player;

public class Refuge extends AbstractFields {

	public Refuge(int id) {
		super(id);
	}

	@Override
	public void landOnField(Player player) {
		if (super(id) == 30) {
			player.imprison();
		}
		// Skriv noget til spilleren
	}

}
