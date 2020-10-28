package com.mooop.board.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooop.board.entity.MSBAuthority;


/**
 * 
 * @author MOoop
 *
 */

public interface AuthorityRepository extends JpaRepository<MSBAuthority, Long>{
	
	MSBAuthority findByAuthorityName(String authorityName);

}
