package com.ahhmet.bankingSystem.repositories;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;

import com.ahhmet.bankingSystem.models.UserModel;

@Mapper
public interface UserRepositoryInterface {
	public UserModel loadUserByUsername(String username);
}
