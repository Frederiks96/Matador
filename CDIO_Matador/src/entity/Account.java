package entity;

import boundary.SQL;

public class Account {

	private int balance;
	private int accountID;



	public Account(int player_id) {
		this.balance = 30000;
		this.accountID = player_id*111;
	}

	public int getBalance() {
		return this.balance;
	}

	public Boolean updateBalance(int d) {
		if (legalTransaction(d)){
			this.balance += d;
			return true;
		} else
			return false;
	}

	public boolean legalTransaction(int d) {
		return this.balance+d>=0;
	}

	public int getAccountID() {
		return this.accountID;
	}

}
