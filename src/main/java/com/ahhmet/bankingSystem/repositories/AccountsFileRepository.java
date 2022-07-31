package com.ahhmet.bankingSystem.repositories;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.kafka.clients.admin.UpdateFeaturesOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ahhmet.bankingSystem.currency.CurrencyInterface;
import com.ahhmet.bankingSystem.models.AccountModel;
import com.ahhmet.bankingSystem.models.LogModel;
import com.fasterxml.jackson.core.JsonProcessingException;

//@Component
public class AccountsFileRepository implements RepositoryInterface {
		
	@Override
	public boolean createAccount(AccountModel account) {
			
		String fileLine =account.getAccountNumber() +","+account.getName()+","+account.getSurname()+","+account.getTc()+","+account.getEmail()+","+account.getType()+","+account.getBalance()+","+account.getLastModified()+","+account.getDate()+",0";
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(new File("accounts.txt"),true));
			wr.write(fileLine);
			wr.newLine();
			wr.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}


	@Override
	public AccountModel findByAccountNumber(int AccountNumber) {
		AccountModel account=new AccountModel();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("accounts.txt"));
			String line = reader.readLine();
			while (line != null) {
				if(line != null) {
						if(line.contains(String.valueOf(AccountNumber))) {
						String [] fileDetail=line.split(",");
						account.setAccountNumber(Integer.parseInt(fileDetail[0]));
						account.setName(fileDetail[1]);;
						account.setSurname(fileDetail[2]);
						account.setTc(fileDetail[3]);
						account.setEmail(fileDetail[4]);
						account.setType(fileDetail[5]);
						account.setBalance(Double.parseDouble(fileDetail[6]));
						account.setLastModified(Long.valueOf(fileDetail[7]));
						return account;
					}
				}
				line = reader.readLine();
			}
				reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int updateBalance(AccountModel account) {
		
		BufferedReader reader;
		String newLine="";
		int counter=0;
		try {
			reader = new BufferedReader(new FileReader("accounts.txt"));
			String line = reader.readLine();
			while (line != null) {
				if(!line.contains(String.valueOf(account.getAccountNumber()))){
					newLine=newLine+line;
				}
				else {
					String fileLine =account.getAccountNumber() +","+account.getName()+","+account.getSurname()+","+account.getTc()+","+account.getEmail()+","+account.getType()+","+account.getBalance()+","+account.getLastModified()+","+account.getDate()+",0";
					newLine=newLine+fileLine;
					counter++;
				}
				
				line=reader.readLine();
				if(line!=null)
					newLine=newLine+"\n";
			}
				reader.close();
				
				if(newLine!="") {
					BufferedWriter wr= new BufferedWriter(new FileWriter(new File("accounts.txt")));
					wr.write(newLine);
					wr.newLine();
					wr.close();
				}
		}		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return counter;
	}


	@Override
	public int deleteAccount(AccountModel account) {
		BufferedReader reader;
		String newLine="";
		int counter=0;
		account=findByAccountNumber(account.getAccountNumber());
		try {
			reader = new BufferedReader(new FileReader("accounts.txt"));
			String line = reader.readLine();
			while (line != null) {
				if(!line.contains(String.valueOf(account.getAccountNumber()))){
					newLine=newLine+line;
				}
				else {
					String fileLine =account.getAccountNumber() +","+account.getName()+","+account.getSurname()+","+account.getTc()+","+account.getEmail()+","+account.getType()+","+account.getBalance()+","+account.getLastModified()+","+account.getDate()+",1";
					newLine=newLine+fileLine;
					counter++;
				}
				
				line=reader.readLine();
				if(line!=null)
					newLine=newLine+"\n";
			}
				reader.close();
				
				if(newLine!="") {
					BufferedWriter wr= new BufferedWriter(new FileWriter(new File("accounts.txt")));
					wr.write(newLine);
					wr.newLine();
					wr.close();
				}
		}		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return counter;
	}


	@Override
	public boolean saveLogs(String message) {
		
		BufferedWriter wr;
		try {
			wr = new BufferedWriter(new FileWriter(new File("logs.txt"),true));
			wr.write(message);
			wr.newLine();
			wr.close();
			return true;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return false;
	}


	@Override
	public List<LogModel> getAllLogs() {

		List<LogModel>logs=new ArrayList<LogModel>();
		BufferedReader reader;
		try {
			reader=new BufferedReader(new FileReader("logs.txt"));
			//ArrayList<String> logsAccount=new ArrayList<String>();
			String line=reader.readLine();
			while(line!=null) {
				if(line!=null) {
					LogModel log=new LogModel();
					log.setLog(line);
					logs.add(log);
				}
				line=reader.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	
	/*
	
	@Override
	public int deleteAccount(int accountNumber) {
		BufferedReader reader;
		String newLine="";
		int counter=0;
		
		try {
			reader = new BufferedReader(new FileReader("accounts.txt"));
			String line = reader.readLine();
			while (line != null) {
				if(!line.contains(String.valueOf(account.getAccountNumber()))){
					newLine=newLine+line;
				}
				else {
					String fileLine =account.getAccountNumber() +","+account.getName()+","+account.getSurname()+","+account.getTc()+","+account.getEmail()+","+account.getType()+","+account.getBalance()+","+account.getLastModified();
					newLine=newLine+fileLine;
					counter++;
				}
				
				line=reader.readLine();
				if(line!=null)
					newLine=newLine+"\n";
			}
				reader.close();
				
				if(newLine!="") {
					BufferedWriter wr= new BufferedWriter(new FileWriter(new File("accounts.txt")));
					wr.write(newLine);
					wr.newLine();
					wr.close();
				}
		}		
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	*/
	
	
}

/*

public Course findById(int id);
public boolean create(int id,String name,int price);
public List<Course> getAll();
public int updateCourseName(String name,int id);
public int updatePrices(int price,int id);*/
