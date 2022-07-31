package com.ahhmet.bankingSystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ahhmet.bankingSystem.JWTTokenSecurity.MyUser;
import com.ahhmet.bankingSystem.models.UserModel;
import com.ahhmet.bankingSystem.repositories.UserRepositoryInterface;

@Component
public class UserService implements UserDetailsService{

	@Autowired
	UserRepositoryInterface userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserModel userDetails = userRepository.loadUserByUsername(username);
		String password=userDetails.getPassword();
		String authorities= userDetails.getAuthorities();
		String[] parsed = authorities.split(",");
		List<GrantedAuthority> grantedAuthority=new ArrayList<GrantedAuthority>(); 
		for(int i=0;i<parsed.length;i++) {
			grantedAuthority.add(new SimpleGrantedAuthority(parsed[i]));
		}
		MyUser user=new MyUser(userDetails.getId(), userDetails.getUsername(), userDetails.getPassword(), grantedAuthority);
		return user;
	}
	
	
}
