package com.mooop.board.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooop.board.entity.MSBAuth;
import com.mooop.board.enums.USER_ROLES;

public interface AuthRepository extends JpaRepository<MSBAuth, Long>{
	
	MSBAuth findByEmail(String email);
	
	@Query("select a.userRole from MSBAuth a where a.email=:email")
	String getRole(@Param("email") String email);
	
	@Query("select a from MSBAuth a where a.userRole=:role")
	Page<MSBAuth> findAllByRole(@Param("role") USER_ROLES role ,  Pageable pageable);
	
	@Query("select a from MSBAuth a where a.status=:status")
	Page<MSBAuth> findAllByStatus(@Param("status") String status ,  Pageable pageable);
	
	@Query("select a from MSBAuth a where a.enable=:enable")
	Page<MSBAuth> findAllByEnable(@Param("enable") String enable ,  Pageable pageable);
	
	@Query("select a from MSBAuth a inner join a.user u on u.userName LIKE :name")
	Page<MSBAuth> findByNameLike(@Param("name") String name ,  Pageable pageable);
}
