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
	 * Returns the text of the card
	 * 
	 * @return The text of the card
	 */
	public String toString() {
		return this.text;
	}
	
	/**
	 * Returns wether a card is ownable or not
	 * 
	 * @return True if the card is ownable, otherwise false
	 */
	public boolean isOwnable() {
		return this.isOwnable;
	}
	
	/**
	 * Returns the owner of a specific card, if the card is ownable. Otherwise returns null
	 * 
	 * @return The owner of the card
	 */
	public Player getOwner() {
		if (this.isOwnable()) {
			return this.owner;
		} else {
			return null;
		}
	}
	
	/**
	 * Sets a player to the owner of a card
	 * 
	 * @param player – The player that is to be the owner of the card
	 */
	public void setOwner(Player player) {
		this.owner=player;
	}
	
	/**
	 * Returns the ID of a specific card
	 * 	
	 * @return The ID of the card
	 */
	public int getCardID() {
		return this.cardID;
	}

}
