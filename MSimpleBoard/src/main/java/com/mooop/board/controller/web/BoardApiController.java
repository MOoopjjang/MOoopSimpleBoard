package com.mooop.board.controller.web;

import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.logging.log4j2.Log4j2AbstractLoggerImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mooop.board.domain.ResponseVO;
import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.exception.GlobalException;

import antlr.debug.ParserListener;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(value="/board/api")
public class BoardApiController extends MSBBaseController{
	
	
	/**
	 * 글 등록
	 * 
	 * @param bio
	 * @param request
	 * @return
	 */
	@PostMapping("/register")
	public ResponseVO<String> register(@RequestParam HashMap<String , Object> param , MultipartHttpServletRequest filelist) {
		try {
			BoardItemVO bivo = BoardItemVO.builder().email((String)param.get("email"))
					.title((String)param.get("title"))
					.nick((String)param.get("nick"))
					.content((String)param.get("content"))
					.sec((String)param.get("secyn"))
					.build();
			
			return (boardService.insertBoardItem(bivo , filelist))?
					ResponseVO.builder().result("OK").reason("").build()
					:ResponseVO.builder().result("FAILED").reason("").build();
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseVO.builder().result("FAILED").reason(e.toString()).build();
		}
	}
	
	
	
	/**
	 * 저장
	 * @param param
	 * @param filelist
	 * @return
	 */
	@PostMapping("/save")
	public ResponseVO<String> save(@RequestParam HashMap<String , Object> param , MultipartHttpServletRequest filelist){
		ResponseVO<String> result = null;
		try {
			BoardItemVO bivo = BoardItemVO.builder().email((String)param.get("email"))
					.idx(Long.parseLong((String)param.get("idx")))
					.title((String)param.get("title"))
					.nick((String)param.get("nick"))
					.content((String)param.get("content"))
					.sec((String)param.get("secyn"))
					.build();
			
			if(boardService.updateBoardItem(bivo , filelist)) {
				result = ResponseVO.builder().result("OK").reason("").build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").build();
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).build();
		}
		
		return result;
	}
	
	
	/**
	 * 글 삭제
	 * 
	 * @param bio
	 * @param request
	 * @return
	 */
	@PostMapping("/remove")
	public ResponseVO<String> remove(@RequestBody BoardItemVO bio , HttpServletRequest request){
		ResponseVO<String> result = null;
		try {
			if(boardService.deleteBoardItem(bio)) {
				result = ResponseVO.builder().result("OK").reason("").build();
			}else {
				result = ResponseVO.builder().result("FAILED").reason("").build();
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
			result = ResponseVO.builder().result("FAILED").reason(e.toString()).build();
		}
		
		return result;
	}
	
	
	/**
	 * 첨부파일 삭제
	 * 
	 * @param idx
	 * @return
	 */
	@GetMapping("/upload/remove/{idx}")
	public ResponseVO<String> fileRemove(@PathVariable("idx") Long idx , HttpServletRequest request){
		String result = "OK";
		try {
			boardService.deleteUploadFile(idx);
		}catch(Exception e) {
			e.printStackTrace();
			result = "FAILED";
		}
		return ResponseVO.builder().result(result).reason("").build();
	}
	
	/**
	 * 첨부파일 다운로드
	 * 
	 * @param idx
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/download/{idx}")
	public ResponseEntity<InputStreamResource> fileDownload(@PathVariable("idx") Long idx , HttpServletRequest request , HttpServletResponse response) 
			throws Exception{
		return boardService.downloadFile(idx , response );
	}

}
