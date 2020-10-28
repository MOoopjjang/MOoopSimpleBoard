package com.mooop.board.service.web;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mooop.board.domain.web.EventItemVO;
import com.mooop.board.entity.MSBEvent;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.EventRepository;
import com.mooop.board.utils.MStringUtil;

@Service("eventManagerService")
public class EventManagerServiceImpl implements EventManagerService{
	
	@Autowired
	DaoManager daoManager;
	
	/**
	 *  Convert MSBEvent to EventItemVO
	 */
	Function<MSBEvent , EventItemVO> convertFunc = (e)->{
		return EventItemVO.builder().idx(e.getIdx())
				.title(e.getTitle())
				.content(e.getContent())
				.dtStart(e.getDtStart())
				.dtEnd(e.getDtEnd())
				.enable(e.getEnable())
				.build();
	};

	@Override
	public Page<EventItemVO> getEventItemList(String category, String text, Integer page, Integer size) {
		EventRepository eventRepository = (EventRepository) daoManager.getRepository(DAO_TYPE.EVENT);
		Page<EventItemVO> result = null;
		Sort sort = new Sort(Direction.DESC , "dtStart");
		if(MStringUtil.validCheck(category) && MStringUtil.validCheck(text)) {
			if(category.equals("title")) {
				result = eventRepository.findByTitleLike("%"+text+"%", PageRequest.of(page, size, sort)).map(convertFunc);
			}else if(category.equals("dtstart")) {
//				result = eventRepository.findByDtStart(dtstart , PageRequest.of(page, size, sort)).map(convertFunc);
			}else if(category.equals("dtend")) {
//				result = eventRepository.findByDtEnd( , PageRequest.of(page, size, sort)).map(convertFunc);
			}else {
				result = eventRepository.findByEnable(text , PageRequest.of(page, size, sort)).map(convertFunc);
			}
		}else {
			result = eventRepository.findAll(PageRequest.of(page, size, sort)).map(convertFunc);
		}
		
		return result;
	}

	@Override
	public EventItemVO getEventItemInfo(Long idx) {
		EventRepository eventRepository = (EventRepository) daoManager.getRepository(DAO_TYPE.EVENT);
		return eventRepository.findById(idx).map(convertFunc).orElse(new EventItemVO());
	}

	@Override
	public boolean registerEventItem(EventItemVO eivo) {
		EventRepository eventRepository = (EventRepository) daoManager.getRepository(DAO_TYPE.EVENT);
		MSBEvent event = new MSBEvent();
		event.setTitle(eivo.getTitle());
		event.setContent(eivo.getContent());
		event.setDtStart(eivo.getDtStart());
		event.setDtEnd(eivo.getDtEnd());
		event.setEnable(eivo.getEnable());
		eventRepository.save(event);
		return true;
	}

	@Override
	public boolean updateEventItem(EventItemVO eivo) {
		EventRepository eventRepository = (EventRepository) daoManager.getRepository(DAO_TYPE.EVENT);
		return eventRepository.findById(eivo.getIdx()).map(e->{
			e.setTitle(eivo.getTitle());
			e.setContent(eivo.getContent());
			e.setDtStart(eivo.getDtStart());
			e.setDtEnd(eivo.getDtEnd());
			e.setEnable(eivo.getEnable());
			
			eventRepository.flush();
			return true;
		}).orElse(false);
	}

	@Override
	public boolean deleteEventItem(EventItemVO eivo) {
		EventRepository eventRepository = (EventRepository) daoManager.getRepository(DAO_TYPE.EVENT);
		return eventRepository.findById(eivo.getIdx()).map(e->{
			eventRepository.delete(e);
			return true;
		}).orElse(false);
	}

}
