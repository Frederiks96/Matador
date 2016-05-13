package entity;

/**
 * Class for managing account objects and transactions
 * 
 * @author Frederik
 */

public class Account {

	private int balance;
	private int accountID;



	public Account(int player_id) {
		this.balance = 30000;
		this.accountID = player_id*111;
	}
	
	/**
	 * Returns the balance of an account
	 * 
	 * @return balance
	 */
	public int getBalance() {
		return this.balance;
	}
	
	/**
	 * Updates the balance of an account object
	 * 
	 * @param d the amount to be added or subtracted from the player's balance
	 * @return whether the transaction has been made
	 */
	public Boolean updateBalance(int d) {
		if (legalTransaction(d)){
			this.balance += d;
			return true;
		} else
			return false;
	}
	
	/**
	 * Determines whether a transaction is legal
	 * 
	 * @param d the amount to be added or subtracted from the player's balance
	 * @return whether the transaction is legal
	 */
	public boolean legalTransaction(int d) {
		return this.balance+d>=0;
	}
	
	/**
	 * Returns the id of an account object
	 * 
	 * @return accountID
	 */
	public int getAccountID() {
		return this.accountID;
	}
	
	/**
	 * Sets the balance of an account object to the amount given in the parameter
	 * 
	 * @param newBalance
	 */
	public void setBalance(int newBalance) {
		this.balance = newBalance;
	}

}
