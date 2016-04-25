package entity;

public class Account {
	
	private int balance;
	
	
	public Account() {
		this.balance = 30000;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public String updateBalance(int d) {
		if (legalTransaction(d)){
			this.balance += d;
			return "transaction complete";}  //text class !!!
		else
			return "not enough founds";   //text class !!!!
	}
	
	public boolean legalTransaction(int d) {
		return this.balance+d>=0;
	}

}
