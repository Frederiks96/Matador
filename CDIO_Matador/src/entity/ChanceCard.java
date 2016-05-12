package entity;

/**
 * This class is for the stack of chance cards
 * @author Benjamin Jensen
 *
 */
public class ChanceCard {

	private String text;
	private boolean isOwnable;
	private Player owner;
	private int cardID;

	public ChanceCard(String text, int cardID){
		this.text = text;
		if (text.charAt(0)=='I' || text.charAt(0)=='O') { // Skal lige rettes til, når teksterne er oversat
			isOwnable = true;
		}
		else {
			isOwnable = false;
		}
		this.cardID = cardID;
	}
/**
 * @return the string on the card
 */
	public String toString() {
		return this.text;
	}
/**
 * 
 * @return if the card is ownable
 */
	public boolean isOwnable() {
		return this.isOwnable;
	}
/**
 * 
 * @return the owner of the card if the card is ownable otherwise returns null
 */
	public Player getOwner() {
		if (this.isOwnable()) {
			return this.owner;
		} else {
			return null;
		}
	}
/**
 * sets a player to be an owner of a chance card
 * @param player 
 */
	public void setOwner(Player player) {
		this.owner=player;
	}
/**
 * 	
 * @return the chance cards ID
 */
	public int getCardID() {
		return this.cardID;
	}

}
