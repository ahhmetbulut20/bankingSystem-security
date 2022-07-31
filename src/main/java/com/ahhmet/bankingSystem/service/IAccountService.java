package com.ahhmet.bankingSystem.service;

import java.io.IOException;
import java.util.List;

import com.ahhmet.bankingSystem.models.AccountModel;
import com.ahhmet.bankingSystem.models.LogModel;

public interface IAccountService {
	public AccountModel create(String name, String surname, String email, String tc, String type, int userId) throws IOException;
	public AccountModel findByAccountNumber(int accountNumber);
	public AccountModel increaseBalance(double amount, int accountNumber);
	public boolean transferBalance(double amount, int ownerAccountNumber, int transferredAccountNumber);
	public boolean delete(int accountNumber);
	public LogModel saveLogs(String message);
	public List<LogModel> accountLogs(int accountNumber); 
}
