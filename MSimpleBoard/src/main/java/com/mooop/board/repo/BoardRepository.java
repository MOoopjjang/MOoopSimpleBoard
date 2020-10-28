package com.mooop.board.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mooop.board.entity.MSBBoard;

public interface BoardRepository extends JpaRepository<MSBBoard, Long>{
	
	/* title column에서 title 문구가 포함된 모든 MSBBoard 객체 반환 */
	Page<MSBBoard> findByTitleLike(@Param("title") String title , Pageable pageable);
	
	/* 글작성자의 nick에 해당되는 모든 MSBBoard 객체 반환 */
	@Query("select brd from MSBBoard brd where brd.user.userNick=:nick")
	Page<MSBBoard> findByNick(@Param("nick") String nick , Pageable pageable);
}
