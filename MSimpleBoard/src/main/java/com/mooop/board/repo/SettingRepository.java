package com.mooop.board.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mooop.board.entity.MSBSetting;


/**
 * 설정 Repository
 * 
 * @author MOoop
 *
 */
public interface SettingRepository extends JpaRepository<MSBSetting , Long>{
	
//	MSBSetting findByBoardPagingCount();

}
