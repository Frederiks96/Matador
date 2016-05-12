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
	}
	/**
	 * Roll the two dice
	 */
	public void roll(){
		die1.roll();
		die2.roll();
	}
	/**
	 * Checks if the dice are alike
	 * @return die1.lastRoll = die2.lastRoll - The value of the dice
	 */
	public boolean hasPair() {
		return die1.getLastRoll() == die2.getLastRoll();
	}
	/**
	 * 
	 * @return die1.lastRoll + die2.lastRoll - The last roll of the dice
	 */
	public int getLastRoll() {
		return die1.getLastRoll() + die2.getLastRoll();
		
	}
	/**
	 * 
	 * @return die1.lastRoll - The last roll of die1
	 */
	public int getDieOne() {
		return this.die1.getLastRoll();
	}
	/**
	 * 
	 * @return die2.lastRoll - The last roll of die2
	 */
	public int getDieTwo() {
		return this.die2.getLastRoll();
	}
}	