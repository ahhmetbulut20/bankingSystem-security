package com.ahhmet.bankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahhmet.bankingSystem.JWTTokenSecurity.JwtTokenUtil;
import com.ahhmet.bankingSystem.models.AccountModel;
import com.ahhmet.bankingSystem.requests.LoginRequest;
import com.ahhmet.bankingSystem.service.UserService;

@RestController
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userDetailsService;
	
	
	@PostMapping(path="/auth")
	public ResponseEntity<?>login(@RequestBody LoginRequest request){
		System.out.println("qdafsadfas");
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
			
		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().build();
		} catch (DisabledException e) {
			
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		System.out.println(userDetails);
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok().body(token);
		
		
	}
	
}
