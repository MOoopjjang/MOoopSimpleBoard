package com.mooop.board.service.web;

import org.springframework.data.domain.Page;

import com.mooop.board.domain.web.AuthorityItemVO;
import com.mooop.board.domain.web.PagingItemVO;

public interface SettingService {
	
	/**
	 * 전체권한 목록을 가져온다
	 * 
	 * @return
	 */
	public Page<AuthorityItemVO> getAuthorityItemList();
	
	
	/**
	 * 신규 권한을 추가한다
	 * 
	 * @param aivo
	 * @return
	 */
	public boolean addNewAuthority(AuthorityItemVO aivo);
	
	
	/**
	 * 신규 권한추가가 가능한지 체크
	 * 
	 * @return
	 */
	public boolean isPossibleNewAuthority(AuthorityItemVO aivo);
	
	
	/**
	 * 권한을 삭제한다
	 * 
	 * @param aivo
	 * @return
	 */
	public boolean removeAuthority(AuthorityItemVO aivo);
	
	
	/**
	 * 권한 삭제가능한지 체크
	 * 
	 * @return
	 */
	public boolean isPossibleRemoveAuthority();
	
	
	/**
	 * 계시판 페이징관련 정보를 가져온다.
	 * @return
	 */
	public PagingItemVO getPagingInfo();
	
	
	/**
	 * 페이지정보 갱신
	 * 
	 * @param pivo
	 * @return
	 */
	public boolean updatePagingInfo(PagingItemVO pivo);

}
