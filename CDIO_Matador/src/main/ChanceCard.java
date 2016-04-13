package main;

public class ChanceCard {

	private String text;
	private boolean isOwnable;
	private Player owner;
	
	public ChanceCard(String text){
		this.text = text;
		if (text.equals("KingsBirthday")) {
			isOwnable = true;
		}
		else {
			isOwnable = false;
		}
	}
	
	public String toString() {
		return this.text;
	}
	
	public boolean isOwnable() {
		return this.isOwnable;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public void setOwner(Player player) {
		this.owner=player;
	}


	
	// lav kø
}
