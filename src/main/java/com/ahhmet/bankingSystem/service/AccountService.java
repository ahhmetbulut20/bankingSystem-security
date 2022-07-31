package com.ahhmet.bankingSystem.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ahhmet.bankingSystem.currency.CurrencyInterface;
import com.ahhmet.bankingSystem.models.*;
import com.ahhmet.bankingSystem.repositories.AccountsFileRepository;
import com.ahhmet.bankingSystem.repositories.RepositoryInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ahhmet.bankingSystem.JWTTokenSecurity.MyUser;

@Component
public class AccountService implements IAccountService {

	@Autowired
	private RepositoryInterface repository;
	
	@Autowired
	private CurrencyInterface currency;
	
	@Override
	public AccountModel create(String name, String surname, String email, String tc, String type, int userId) throws IOException {
		if(type.equals("TL") || type.equals("Dolar") || type.equals("Altın")) {
			Random rnd = new Random();
		    int accountNumber = rnd.nextInt(999999999)+1000000000;
		    Date date=new Date();
		    AccountModel account=new AccountModel();
		    account.setAccountNumber(accountNumber);
		    account.setName(name);
		    account.setSurname(surname);
		    account.setEmail(email);
		    account.setTc(tc);
		    account.setType(type);
		    account.setLastModified(System.currentTimeMillis());
		    account.setDate(date);
		    account.setUserId(userId);
		    
		    if(repository.createAccount(account))
		    	return account;
		}
		return null;
	}
	
	@Override
	public AccountModel findByAccountNumber(int accountNumber) {
		return repository.findByAccountNumber(accountNumber);
	}

	
	@Override
	public AccountModel increaseBalance(double amount, int accountNumber) {
		AccountModel account=repository.findByAccountNumber(accountNumber);
		account.setBalance(account.getBalance()+amount);
		account.setLastModified(System.currentTimeMillis());
		Date date=new Date();
		account.setDate(date);
		int updatedAccountNumber=repository.updateBalance(account);
		if(updatedAccountNumber>0)
			return account;
		else
			return null;
	}

	@Override
	public boolean transferBalance(double amount, int ownerAccountNumber, int transferredAccountNumber) {
		AccountModel ownerAccount=findByAccountNumber(ownerAccountNumber);
		AccountModel transferredAccount=findByAccountNumber(transferredAccountNumber);
		
		if(ownerAccount.getBalance()>=amount) {
			ownerAccount.setBalance(ownerAccount.getBalance()-amount);
			ownerAccount.setLastModified(System.currentTimeMillis());
			Date date=new Date();
			ownerAccount.setDate(date);
			repository.updateBalance(ownerAccount);
			if(!ownerAccount.getType().equals(transferredAccount.getType())) {
				try {
					double transferredAmount=currency.exchange(amount, ownerAccount.getType(), transferredAccount.getType());
					transferredAccount.setBalance(transferredAccount.getBalance()+transferredAmount);
					transferredAccount.setLastModified(System.currentTimeMillis());
					date=new Date();
					transferredAccount.setDate(date);
					repository.updateBalance(transferredAccount);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				transferredAccount.setBalance(transferredAccount.getBalance()+amount);
				transferredAccount.setLastModified(System.currentTimeMillis());
				date=new Date();
				transferredAccount.setDate(date);
				repository.updateBalance(transferredAccount);
			}
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean delete(int accountNumber) {
		AccountModel account=new AccountModel();
		Date date=new Date();
		account.setDate(date);
		account.setLastModified(System.currentTimeMillis());
		account.setAccountNumber(accountNumber);
		int deletedAccounts=repository.deleteAccount(account);
		if(deletedAccounts>0)
			return true;
		return false;
	}
	
	@Override
	public LogModel saveLogs(String message) {
		LogModel log=new LogModel();
		log.setLog(message);
		boolean result=repository.saveLogs(message);
		if(result)
			return log;
		
		return null;
	}

	@Override
	public List<LogModel> accountLogs(int accountNumber) {
		List<LogModel>allLogs=new ArrayList<LogModel>();
		allLogs=repository.getAllLogs();
		List<LogModel>logs_temp=new ArrayList<LogModel>();
		for(int i=0;i<allLogs.size();i++) {
			if(allLogs.get(i).getLog().contains(String.valueOf(accountNumber))) {
				logs_temp.add(allLogs.get(i));
			}
		}
		
		
		List<LogModel>logs=new ArrayList<LogModel>();
		String type=repository.findByAccountNumber(accountNumber).getType();
		
		for(int i=0;i<logs_temp.size();i++) {
			if(logs_temp.get(i).getLog().contains("transferred")) {
				String [] temp=logs_temp.get(i).getLog().split(" ");
				String message=temp[0]+" hesaptan "+temp[7]+" hesaba "+temp[3]+" "+temp[4]+" transfer edilmiştir.";
				LogModel log=new LogModel();
				log.setLog(message);
				logs.add(log);
			}
			
			else {
				String [] temp=logs_temp.get(i).getLog().split(" ");
				String message=temp[0]+" no'lu hesaba "+temp[3]+" "+type+" yatırılmıştır.";
				LogModel log=new LogModel();
				log.setLog(message);
				logs.add(log);
			}
		}
		
		return logs;
	}
	
	
	
}
