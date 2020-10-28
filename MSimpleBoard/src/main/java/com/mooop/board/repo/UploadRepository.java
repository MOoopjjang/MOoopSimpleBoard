package com.mooop.board.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooop.board.entity.MSBUpload;

public interface UploadRepository extends JpaRepository<MSBUpload, Long>{
	
	
	@Query("select u from MSBUpload u WHERE u.brd_idx=:brd_idx")
	List<MSBUpload> findAllByBrdIdx(@Param("brd_idx") Long brd_idx);

}
