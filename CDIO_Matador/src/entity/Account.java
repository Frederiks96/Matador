package entity;

public class Account {
	
	private int balance;
	
	
	public Account() {
		this.balance = 30000;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Boolean updateBalance(int d) {
		if (legalTransaction(d)){
			this.balance += d;
			return true;}  //text class !!!
		else
			return true;   //text class !!!!
	}
	
	public boolean legalTransaction(int d) {
		return this.balance+d>=0;
	}

}
