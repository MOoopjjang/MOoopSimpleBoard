package com.mooop.board.service.web;

import com.mooop.board.domain.web.AdmUserItemVO;
import com.mooop.board.domain.web.BoardItemVO;
import org.springframework.data.domain.Page;

public interface BoardManagerService {
	
	/**
	 * 최다 조회수 글 목록
	 * 
	 * @return
	 */
	public Page<BoardItemVO> getMostHitsBoardItemList();
	
	
	/**
	 * 최다 업로더 목록
	 * 
	 * @return
	 */
	public Page<AdmUserItemVO> getMostUploaderList();
	
	
	/**
	 * 계시글 상세내용
	 * 
	 * @param idx
	 * @return
	 */
	public BoardItemVO getBoardDetailInfo(Long idx);
	
	
	/**
	 * 계시글 삭제
	 * 
	 * @param idx
	 * @return
	 */
	public boolean removeBoardItemInfo(Long idx);

}
