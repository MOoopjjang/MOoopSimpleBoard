package com.mooop.board.sec;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	DaoManager daoManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("username : "+username);
		AuthRepository authRepository =  (AuthRepository) daoManager.getRepository(DAO_TYPE.AUTH);
		MSBAuth auth =  authRepository.findByEmail(username);
		
//		UserBuilder builder = Optional.ofNullable(auth).map(data->{
//			UserBuilder ub = User.withUsername(auth.getEmail());
//			ub.password(auth.getPassword());
//			ub.roles(auth.getUserRole().getRole());
//			return ub;
//		}).orElseThrow(()->{
//			throw new UsernameNotFoundException(username+" is not found!!!");
//		});
		
		UserBuilder ub = null;
		if(auth !=null) {
			ub = User.withUsername(auth.getEmail());
			ub.password(auth.getPassword());
			ub.roles(auth.getUserRole().getRole());
		}else {
			throw new UsernameNotFoundException(username+" is not found!!!");
		}
		
		return ub.build();
	}

}
