package entity.dicecup;
/**
 * This class is for the Dice cup which holds two dies, it also checks if the dies is a pair
 * @author Benjamin Jensen
 *
 */
public class DiceCup {

	private Dice die1;
	private Dice die2;
	
	public DiceCup() {
		this.die1 = new Dice();
		this.die2 = new Dice();
		die1.roll();
		die2.roll();
	}
	
	/**
	 * Rolls the dice
	 */
	public void roll(){
		die1.roll();
		die2.roll();
	}
	
	/**
	 * Checks if a pair has been rolled
	 * 
	 * @return True if a pair has been rolled, otherwise false
	 */
	public boolean hasPair() {
		return die1.getLastRoll() == die2.getLastRoll();
	}
	
	/**
	 * Returns the sum of the last roll of the dice
	 * 
	 * @return The sum of the dice
	 */
	public int getLastRoll() {
		return die1.getLastRoll() + die2.getLastRoll();
		
	}
	
	/**
	 * Returns the facevalue of die 1
	 * 
	 * @return The last roll of die1
	 */
	public int getDieOne() {
		return this.die1.getLastRoll();
	}
	
	/**
	 * Returns the facevalue of die 2
	 * 
	 * @return The last roll of die2
	 */
	public int getDieTwo() {
		return this.die2.getLastRoll();
	}
}	