package main;

public class Account {
	
	private int balance;
	
	
	public Account() {
		this.balance = 30000;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public void updateBalance(int d) {
		if (legalTransaction(d))
			this.balance += d;
	}
	
	public boolean legalTransaction(int d) {
		return this.balance+d>=0;
	}

}
