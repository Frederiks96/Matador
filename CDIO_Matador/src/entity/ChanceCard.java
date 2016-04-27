package entity;

public class ChanceCard {

	private String text;
	private boolean isOwnable;
	private Player owner;
	private String cardID;

	public ChanceCard(String text){
		this.text = text;
		if (text.charAt(0)=='I' || text.charAt(0)=='O') { // Skal lige rettes til, når teksterne er oversat
			isOwnable = true;
		}
		else {
			isOwnable = false;
		}
		this.cardID = text;
	}

	public String toString() {
		return this.text;
	}

	public boolean isOwnable() {
		return this.isOwnable;
	}

	public Player getOwner() {
		if (this.isOwnable()) {
			return this.owner;
		} else {
			return null;
		}
	}

	public void setOwner(Player player) {
		this.owner=player;
	}

}
