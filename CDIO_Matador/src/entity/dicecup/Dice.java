package entity.dicecup;
/**
 * This is a class about the dice we use in the game
 * @author Benjamin Jensen
 *
 */
public class Dice {

	private int lastRoll;
	
	/**
	 * Rolls a random value between 1 and 6
	 */
	public void roll() {
		lastRoll = (int) (Math.random()*6+1);
	}
	
	/**
	 * Returns the faceValue of the last roll
	 * 
	 * @return The last roll
	 */
	public int getLastRoll() {
		return lastRoll;
	}

}
