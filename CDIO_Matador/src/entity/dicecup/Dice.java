package entity.dicecup;

public class Dice {

	private int lastRoll;

	public void roll() {
		lastRoll = (int) Math.floor(Math.random()) * 6;
	}

	public int getLastRoll() {
		return lastRoll;
	}

}
