package entity.fields;

import entity.Player;
import entity.Texts;

public class Refuge extends AbstractFields {
	private String name;
	
	public Refuge(int id, Texts text) {
		super(id);
		this.name = (String) text.getInfo(id+"_name");
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
	
	public String getName() {
		return this.name;
	}
	

}
