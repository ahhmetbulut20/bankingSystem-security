package com.ahhmet.bankingSystem.requests;

public class TransferredBalanceRequest {
	
	private int transferredAccountNumber;
	private double amount;
	
	public int getTransferredAccountNumber() {
		return transferredAccountNumber;
	}
	public void setTransferredAccountNumber(int transferredAccountNumber) {
		this.transferredAccountNumber = transferredAccountNumber;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
