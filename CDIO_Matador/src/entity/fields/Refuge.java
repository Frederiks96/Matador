package entity.fields;

import entity.Player;
import entity.Texts;

public class Refuge extends AbstractFields {

	public Refuge(int id, Texts text) {
		super(id);
	}

	@Override
	public void landOnField(Player player, Texts text) {
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
