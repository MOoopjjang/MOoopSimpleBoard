package com.mooop.board;

import com.mooop.board.entity.*;
import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.enums.USER_STATUS;
import com.mooop.board.repo.*;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.utils.MSecurityUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * DummyData 생성 class
 */
public class DummyDataManager {
	
	/**
	 * Event 데이타 생성
	 * 
	 * @param daoManager
	 */
	public static void createEventDummyData(DaoManager daoManager) {
		
		List<MSBEvent> l = new ArrayList<>();
		for(int i = 0 ; i < 100 ; i++) {
			Date now = new Date();
			MSBEvent event = new MSBEvent();
			event.setTitle("test-"+i);
			event.setContent("테스트입니다. test :: => "+i);
			event.setDtStart(now);
			event.setDtEnd(now);
			if(i%12 == 0) {
				event.setEnable("N");
			}else {
				event.setEnable("Y");
			}
			
			l.add(event);
		}
		EventRepository eRepository = (EventRepository) daoManager.getRepository(DAO_TYPE.EVENT);
		eRepository.saveAll(l);
	}
	
	/**
	 * 계시판 데이타 생성
	 * 
	 * @param daoManager
	 */
	public static void createBoardDummyData(DaoManager daoManager) {
		AuthRepository authRepo = (AuthRepository)daoManager.getRepository(DAO_TYPE.AUTH);
		
		MSBAuth auth = new MSBAuth();
		auth.setEmail("admin@test.com");
		auth.setPassword(MSecurityUtil.makeGeneratePassowrd("admin@test.com" , "admin"));
		auth.setEnable("Y");
		auth.setStatus(USER_STATUS.ACTIVE);
		auth.setUserRole(USER_ROLES.ADMIN);
		
		MSBHistory his = new MSBHistory();
		his.setAuth(auth);
		auth.setHistory(his);
//		auth.getHistorys().add(his);
		
		MSBUser user = new MSBUser();
		user.setUserName("홍길동");
		user.setUserNick("도둑");
		user.setUserAddr("seoul");
		user.setUserDesc("나는 나다");
		user.setAuth(auth);
		auth.setUser(user);
		
		
		
		MSBBoard b1 = new MSBBoard();
		b1.setTitle("안녕하세요~");
		b1.setContent("오늘은 회사로 출근하는 날이다..별루다.좋다..나쁘다..ㅎㅎㅎㅎ");
		b1.setSecYN("N");
		b1.setHit(0);
		b1.setUser(user);
		user.getBoards().add(b1);

		MSBBoard b2 = new MSBBoard();
		b2.setTitle("안녕하세요2~");
		b2.setContent("두번째 방문입니다.ㅎ..ㅎㅎㅎㅎ");
		b2.setSecYN("N");
		b2.setHit(0);
		b2.setUser(user);
		user.getBoards().add(b2);
		
		
		authRepo.save(auth);
		
		
		auth = new MSBAuth();
		auth.setEmail("guest@test.com");
		auth.setPassword(MSecurityUtil.makeGeneratePassowrd("guest@test.com","guest"));
		auth.setEnable("Y");
		auth.setStatus(USER_STATUS.ACTIVE);
		auth.setUserRole(USER_ROLES.GUEST);
		
		his = new MSBHistory();
		his.setAuth(auth);
		auth.setHistory(his);
		
		user = new MSBUser();
		user.setUserName("고길동");
		user.setUserNick("외통수");
		user.setUserAddr("고려시");
		user.setUserDesc("해피니스~");
		user.setAuth(auth);
		auth.setUser(user);
		authRepo.save(auth);
		
		
		auth = new MSBAuth();
		auth.setEmail("user@test.com");
		auth.setPassword(MSecurityUtil.makeGeneratePassowrd("user@test.com" , "user"));
		auth.setEnable("Y");
		auth.setUserRole(USER_ROLES.USER);
		auth.setStatus(USER_STATUS.ACTIVE);
		
		his = new MSBHistory();
		his.setAuth(auth);
		auth.setHistory(his);
		
		user = new MSBUser();
		user.setUserName("정우성");
		user.setUserNick("비트");
		user.setUserAddr("용산");
		user.setUserDesc("항상 기쁨으로~~");
		user.setAuth(auth);
		auth.setUser(user);
		
		authRepo.save(auth);
		
		createDummyData(daoManager , auth , user);
	}
	
	
	private static void createDummyData(DaoManager daoManager , MSBAuth auth , MSBUser user) {
		BoardRepository brdRepository =  (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		for(int i  = 0 ; i < 45 ; i++) {
			MSBBoard brd = new MSBBoard();
			brd.setTitle("TEST - "+(i+1));
			brd.setContent("테스트 body -- "+(i+1));
			if(i%10 == 0) {
				brd.setSecYN("Y");
			}else {
				brd.setSecYN("N");
			}
			
			brd.setHit(i);
			brd.setUser(user);
			user.getBoards().add(brd);
			
			brdRepository.save(brd);
		}
		
		
		UserRepository userRepository =  (UserRepository) daoManager.getRepository(DAO_TYPE.USER);
		userRepository.save(user);
		
	}
	
	/**
	 * 설정 데이타 생성
	 * 
	 * @param daoManager
	 */
	public static void createSettingDummyData(DaoManager daoManager) {
		SettingRepository settingRepository = (SettingRepository) daoManager.getRepository(DAO_TYPE.SETTING);
		MSBSetting setting = new MSBSetting();
		settingRepository.save(setting);
	}
	
	/**
	 * 권한 데이타 생성
	 * 
	 * @param daoManager
	 */
	public static void createAuthorityDummyData(DaoManager daoManager) {
		AuthorityRepository authorityRepository = (AuthorityRepository) daoManager.getRepository(DAO_TYPE.AUTHORITY);
		authorityRepository.save(new MSBAuthority("ADMIN" , "관리자권한"));
		authorityRepository.save(new MSBAuthority("USER" , "일반가입자.계시판 글쓰기 ,타인글 보기를 할수있다"));
		authorityRepository.save(new MSBAuthority("OPERATOR" , "운영자.???"));
		authorityRepository.save(new MSBAuthority("GUEST" , "가입허가 대기자.계사판 목록만 확인이 가능"));
	}

}
