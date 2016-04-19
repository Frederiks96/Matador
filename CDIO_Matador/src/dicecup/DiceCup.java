package dicecup;

public class DiceCup {

	private Dice die1;
	private Dice die2;
	
	public DiceCup() {
		this.die1 = new Dice();
		this.die2 = new Dice();
	}

	public int roll(){
		return die1.roll() + die2.roll();
	}

	public boolean hasPair() {
		return die1.getLastRoll() == die2.getLastRoll();
	}
	
	public int getLastRoll() {
		return die1.getLastRoll() + die2.getLastRoll();
		
	}
}