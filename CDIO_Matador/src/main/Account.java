package main;

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
			return null;}
		else
			return "not enough founds";   //text class !!!!
	}
	
	private boolean legalTransaction(int d) {
		return this.balance+d>=0;
	}

}
