package com.mooop.board;

import javax.annotation.PostConstruct;

import com.mooop.board.config.property.ConfirmProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

import com.mooop.board.config.property.MUploadProperties;
import com.mooop.board.config.property.SettingProperty;
import com.mooop.board.constants.ErrorDefines;
import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBBoard;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.entity.MSBUser;
import com.mooop.board.enums.USER_ROLES;
import com.mooop.board.enums.USER_STATUS;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.BoardRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.HistoryRespository;
import com.mooop.board.repo.UserRepository;
import com.mooop.board.utils.MSecurityUtil;

import lombok.extern.slf4j.Slf4j;



/**
 * 
 * MSimpleBoard Service
 * 
 * @author MOoop
 *
 */

@Slf4j
@EnableConfigurationProperties({SettingProperty.class , MUploadProperties.class , ConfirmProperties.class})
@EnableAspectJAutoProxy
@ServletComponentScan
@SpringBootApplication
public class MSimpleBoardApplication {
	
	@Value("${msp.static.mode}")
	String staticMode;
	
	@Autowired
	Environment env;
	
	
	@PostConstruct
	public void initApplication() {
		
		/**
		 * 에러메세지를 생성한다
		 */
		ErrorDefines.makeErrorMessage();
	}

	public static void main(String[] args) {
		SpringApplication.run(MSimpleBoardApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner runner(DaoManager daoManager) throws Exception{
		return(args)->{
			
			/**
			 *  H2 profile  일경우에만 test용 데이타 insert
			 */
			if(staticMode.equals("Y")) {
				DummyDataManager.createBoardDummyData(daoManager);
//				DummyDataManager.createEventDummyData(daoManager);
				DummyDataManager.createSettingDummyData(daoManager);
				DummyDataManager.createAuthorityDummyData(daoManager);
			}
		
			
		};
		
	}
	
	
	

}
