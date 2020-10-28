package com.mooop.board.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mooop.board.entity.MSBUser;

public interface UserRepository extends JpaRepository<MSBUser, Long>{
	
	MSBUser findByUserNick(String nick);
	
}
