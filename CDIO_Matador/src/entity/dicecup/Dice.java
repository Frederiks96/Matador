package entity.dicecup;

public class Dice {

	private int lastRoll;

	public int roll() {
		lastRoll = (int) Math.floor(Math.random()) * 6;
		return lastRoll;
	}

	public int getLastRoll() {
		return lastRoll;
	}

}
