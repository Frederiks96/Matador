package fields;

import main.Player;

public class Refuge extends AbstractFields {

	public Refuge(int id) {
		super(id);
	}

	@Override
	public void landOnField(Player player) {
		if (player.getPosition() == 30) {
			player.imprison();
			
			/* 
			 *  Skriv til spilleren, at han er fængslet
			 */
			
		}
		
		else {
			
			/* 
			 *  Skriv noget til spilleren
			 */
			
		}
	}

}
