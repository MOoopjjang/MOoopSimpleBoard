package com.mooop.board.repo;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooop.board.entity.MSBEvent;

public interface EventRepository extends JpaRepository<MSBEvent, Long>{
	
	public MSBEvent findByIdx(Long idx);
	
	public Page<MSBEvent> findByTitleLike(@Param("title") String title , Pageable pageable);
	
	@Query("select e from MSBEvent e where e.dtStart=:dtstart")
	public Page<MSBEvent> findByDtStart(@Param("dtstart") Date dtstart , Pageable pageable);
	
	@Query("select e from MSBEvent e where e.dtEnd=:dtend")
	public Page<MSBEvent> findByDtEnd(@Param("dtend") Date dtend , Pageable pageable);
	
	@Query("select e from MSBEvent e where e.enable=:enable")
	public Page<MSBEvent> findByEnable(@Param("enable") String enable , Pageable pageable);
}
