package com.mooop.board;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBAuthority;
import com.mooop.board.entity.MSBBoard;
import com.mooop.board.entity.MSBEvent;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.entity.MSBSetting;
import com.mooop.board.entity.MSBUser;
import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.enums.USER_STATUS;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.AuthorityRepository;
import com.mooop.board.repo.BoardRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.utils.MSecurityUtil;
import com.mooop.board.repo.EventRepository;
import com.mooop.board.repo.SettingRepository;
import com.mooop.board.repo.UserRepository;

public class DummyDataManager {
	
	/**
	 * Event 데이타 생성
	 * 
	 * @param eRepository
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
		auth.setEmail("cwkim@zinnaworks.com");
		auth.setPassword(MSecurityUtil.makeGeneratePassowrd("cwkim@zinnaworks.com" , "1111"));
		auth.setEnable("Y");
		auth.setStatus(USER_STATUS.ACCESSION);
		auth.setUserRole(USER_ROLES.ADMIN);
		
		MSBHistory his = new MSBHistory();
		his.setAuth(auth);
		auth.getHistorys().add(his);
		
		MSBUser user = new MSBUser();
		user.setUserName("김철우");
		user.setUserNick("길손");
		user.setUserAddr("incheon");
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
		auth.setEmail("hjkim@myhome.com");
		auth.setPassword(MSecurityUtil.makeGeneratePassowrd("hjkim@myhome.com","4444"));
		auth.setEnable("Y");
		auth.setStatus(USER_STATUS.ACCESSION);
		auth.setUserRole(USER_ROLES.GUEST);
		
		his = new MSBHistory();
		his.setAuth(auth);
		auth.getHistorys().add(his);
		
		user = new MSBUser();
		user.setUserName("김효중");
		user.setUserNick("미남이");
		user.setUserAddr("당진시");
		user.setUserDesc("해피니스~");
		user.setAuth(auth);
		auth.setUser(user);
		authRepo.save(auth);
		
		
		auth = new MSBAuth();
		auth.setEmail("ejkim@myhome.com");
		auth.setPassword(MSecurityUtil.makeGeneratePassowrd("ejkim@myhome.com" , "2222"));
		auth.setEnable("Y");
		auth.setUserRole(USER_ROLES.USER);
		auth.setStatus(USER_STATUS.ACCESSION);
		
		his = new MSBHistory();
		his.setAuth(auth);
		auth.getHistorys().add(his);
		
		user = new MSBUser();
		user.setUserName("김은중");
		user.setUserNick("은철이");
		user.setUserAddr("구월동");
		user.setUserDesc("항상 기쁨으로~~");
		user.setAuth(auth);
		auth.setUser(user);
		
		
		
//		b1 = new MSBBoard();
//		b1.setTitle("배가 아퍼요~");
//		b1.setContent("밥을 잘못 먹어서 배가 아퍼요~~ㅜ");
//		b1.setSecYN("N");
//		b1.setHit(0);
//		b1.setUser(user);
//		user.getBoards().add(b1);
//		
//		b1 = new MSBBoard();
//		b1.setTitle("회복중~");
//		b1.setContent("하루종일 약먹구 , 배를 따뜻하게 하구 ...며칠간 고생해야 될것 같아요!!!");
//		b1.setSecYN("N");
//		b1.setHit(0);
//		b1.setUser(user);
//		user.getBoards().add(b1);
		
		
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
