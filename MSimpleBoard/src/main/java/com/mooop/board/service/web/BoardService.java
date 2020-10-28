package com.mooop.board.service.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.entity.MSBBoard;

public interface BoardService {
	
	/* board item 들을 가져온다 */
	public Page<BoardItemVO> getBoardItemList(String category , String text , Integer page , Integer size) throws Exception;
	
	/* idx에 해당되는 글정보를 가져온다. */
	public BoardItemVO getBoardItem(Long idx) throws Exception;
	
	/* 선택한 item을 삭제한다 */
	public boolean deleteBoardItem(BoardItemVO item) throws Exception;
	
	/* 새로운 item을 저장한다 */
	public boolean insertBoardItem(BoardItemVO item , MultipartHttpServletRequest mpsr) throws Exception;
	
	/* 변경된 item 정보를 갱신한다 */
	public boolean updateBoardItem(BoardItemVO item , MultipartHttpServletRequest mpsr) throws Exception;
	
	/* 첨부된 파일을 삭제한다  */
	public boolean deleteUploadFile(Long idx) throws Exception;
	
	/* 파일을 다운로드 한다. */
	public ResponseEntity<InputStreamResource> downloadFile(Long idx , HttpServletResponse response) throws Exception;

}
