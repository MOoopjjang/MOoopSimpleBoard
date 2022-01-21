package com.mooop.board.sec;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.mooop.board.entity.MSBAuth;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;

/**
 * 
 * @author MOoop
 *
 */
@Component
public class AuthenticationUserDetailService implements UserDetailsService{
	
	private static Logger logger = LoggerFactory.getLogger("AuthenticationUserDetailService");

	private final DaoManager daoManager;
	public AuthenticationUserDetailService(DaoManager daoManager) {
		this.daoManager = daoManager;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("username : "+username);
		AuthRepository authRepository =  (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		MSBAuth auth =  authRepository.findByEmail(username);
		
		return Optional.ofNullable(auth).map(data->{
			UserBuilder ub = User.withUsername(auth.getEmail());
			ub.password(auth.getPassword());
			ub.roles(auth.getUserRole().getRole());
			return ub.build();
		}).orElseThrow(()->new UsernameNotFoundException(username+" is not found!!!"));
	}

}
