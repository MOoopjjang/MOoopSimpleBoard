package com.mooop.board.repo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


/**
 * 
 * Repository 접근을 관리하는 component
 *  - Repository생성시 이곳에 추가후 DaoManager를 통해 호출되어야 한다
 *     ( 직접호출은 X )
 * 
 * @author MOoop
 *
 */
@Component("daoManager")
public class DaoManager {
	
	public enum DAO_TYPE{
		AUTH("AuthRepository"),
		BRD("BoardRepository"),
		HISTORY("HistoryRespository"),
		USER("UserRepository"),
		EVENT("EventRepository"),
		SETTING("SettingRepository"),
		AUTHORITY("AuthorityRepository"),
		UPLOAD("UploadRepository")
		;
		
		String repositoryName;
		
		DAO_TYPE(String repositoryName){
			this.repositoryName = repositoryName;
		}
	}

	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private HistoryRespository historyRespository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private SettingRepository settingRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private UploadRepository uploadRepository;
	
	
	private Map<DAO_TYPE , JpaRepository> repositoryMap = new HashMap<>();
	
	@PostConstruct
	public void initData() {
		/**
		 *  Repository들을 등록한다
		 */
		repositoryMap.put(DAO_TYPE.AUTH, authRepository);
		repositoryMap.put(DAO_TYPE.BRD, boardRepository);
		repositoryMap.put(DAO_TYPE.HISTORY, historyRespository);
		repositoryMap.put(DAO_TYPE.USER, userRepository);
		repositoryMap.put(DAO_TYPE.EVENT, eventRepository);
		repositoryMap.put(DAO_TYPE.SETTING, settingRepository);
		repositoryMap.put(DAO_TYPE.AUTHORITY, authorityRepository);
		repositoryMap.put(DAO_TYPE.UPLOAD, uploadRepository);
	}
	
	
	/**
	 * Matching Type의 Repository를 반환
	 * 
	 * @param daoType
	 * @return Repository
	 */
	public JpaRepository<?,?> getRepository(DAO_TYPE daoType){
		return repositoryMap.get(daoType);
	}
	
	
}
