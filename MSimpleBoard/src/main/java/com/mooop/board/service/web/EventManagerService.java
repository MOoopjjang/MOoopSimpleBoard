package com.mooop.board.service.web;

import org.springframework.data.domain.Page;

import com.mooop.board.domain.web.EventItemVO;

public interface EventManagerService {
	
	/**
	 * 공지사항목록을 가져온다
	 * 
	 * @param category
	 * @param text
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<EventItemVO> getEventItemList(String category , String text , Integer page , Integer size);
	
	/**
	 * 공지사항을 가져온다
	 * 
	 * @param idx
	 * @return
	 */
	public EventItemVO getEventItemInfo(Long idx);

	/**
	 * 새로운 공지사항을 저장한다
	 * 
	 * @param eivo
	 * @return
	 */
	public boolean registerEventItem(EventItemVO eivo);
	
	
	/**
	 * 변경된 공지사항을 저장한다
	 * 
	 * @param eivo
	 * @return
	 */
	public boolean updateEventItem(EventItemVO eivo);
	
	
	/**
	 * 공지사항을 삭제한다
	 * 
	 * @param eivo
	 * @return
	 */
	public boolean deleteEventItem(EventItemVO eivo);
}
