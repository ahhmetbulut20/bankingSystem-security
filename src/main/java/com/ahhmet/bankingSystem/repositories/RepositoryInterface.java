package com.ahhmet.bankingSystem.repositories;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ahhmet.bankingSystem.models.AccountModel;
import com.ahhmet.bankingSystem.models.LogModel;

@Mapper
public interface RepositoryInterface {
	public boolean createAccount(AccountModel account);
	public AccountModel findByAccountNumber(int AccountNumber);
	public int updateBalance(AccountModel account);
	public int deleteAccount(AccountModel account);
	public boolean saveLogs(String message);
	public List<LogModel> getAllLogs();
}
