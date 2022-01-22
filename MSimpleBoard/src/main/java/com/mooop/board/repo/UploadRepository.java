package com.mooop.board.repo;

import java.util.List;

import com.mooop.board.enums.UPLOAD_P_TYPE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooop.board.entity.MSBUpload;

public interface UploadRepository extends JpaRepository<MSBUpload, Long>{
	
	
	@Query("select u from MSBUpload u WHERE u.brd_idx=:brd_idx")
	List<MSBUpload> findAllByBrdIdx(@Param("brd_idx") Long brd_idx);

	@Query("select u from MSBUpload u WHERE u.brd_idx=:brd_idx and u.utype=:utype")
	MSBUpload findByBrdIdxAndUtype(@Param("brd_idx") Long brd_idx , @Param("utype") UPLOAD_P_TYPE utype);

//	MSBUpload findByBrd_idxAndUtype(@Param("brd_idx") Long brd_idx , @Param("utype") String utype);


}
