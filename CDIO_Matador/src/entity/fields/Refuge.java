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
		if (player.getPosition() == 0) {
			// Spilleren er landet på start - spilleren får penge, allerede i update position, så den skal vi ikke røre ved her
		} else if (player.getPosition() == 10) {
			// På besøg i fængsel
		} else if (player.getPosition() == 20) {
			// Fri parkering
		} else if (player.getPosition() == 30) {
			player.imprison();
			 // Skriv til spilleren, at han er fængslet
		}
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

}
