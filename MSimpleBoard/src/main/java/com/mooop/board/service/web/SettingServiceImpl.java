package com.mooop.board.service.web;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.mooop.board.config.property.SettingProperty;
import com.mooop.board.domain.web.AuthorityItemVO;
import com.mooop.board.domain.web.PagingItemVO;
import com.mooop.board.entity.MSBAuthority;
import com.mooop.board.entity.MSBSetting;
import com.mooop.board.repo.AuthorityRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.SettingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("settingService")
public class SettingServiceImpl implements SettingService{
	
	@Autowired
	SettingProperty settingProperty;
	
	@Autowired
	DaoManager daoManager;

	@Override
	public Page<AuthorityItemVO> getAuthorityItemList() {
		AuthorityRepository authorityRepository =  (AuthorityRepository) daoManager.getRepository(DAO_TYPE.AUTHORITY);
		return new PageImpl<AuthorityItemVO>(authorityRepository.findAll().stream().map(authority->{
			AuthorityItemVO aivo = new AuthorityItemVO();
			aivo.setAuthorityName(authority.getAuthorityName());
			aivo.setAuthorityDesc(authority.getAuthorityDesc());
			return aivo;
		}).collect(Collectors.toList()));
	}

	@Override
	public boolean addNewAuthority(AuthorityItemVO aivo) {
		AuthorityRepository authorityRepository =  (AuthorityRepository) daoManager.getRepository(DAO_TYPE.AUTHORITY);
		MSBAuthority authority = new MSBAuthority();
		authority.setAuthorityName(aivo.getAuthorityName());
		authority.setAuthorityDesc(aivo.getAuthorityDesc());
		authorityRepository.saveAndFlush(authority);
		return true;
	}

	@Override
	public boolean isPossibleNewAuthority(AuthorityItemVO aivo) {
		Integer maxCount = settingProperty.getAuthorityMaxCount();
		AuthorityRepository authorityRepository =  (AuthorityRepository) daoManager.getRepository(DAO_TYPE.AUTHORITY);
		return Optional.of(authorityRepository.count())
				.filter(cnt->cnt < maxCount)
				.map(d->{
					if(authorityRepository.findByAuthorityName(aivo.getAuthorityName())!=null) {
						return false;
					}else {
						return true;
					}
				}).orElse(false);
	}

	@Override
	public boolean removeAuthority(AuthorityItemVO aivo) {
		AuthorityRepository authorityRepository =  (AuthorityRepository) daoManager.getRepository(DAO_TYPE.AUTHORITY);
		return Optional.ofNullable(authorityRepository.findByAuthorityName(aivo.getAuthorityName()))
				.map(authority->{
					authorityRepository.delete(authority);
					return true;
				}).orElse(false);
	}

	@Override
	public boolean isPossibleRemoveAuthority() {
		AuthorityRepository authorityRepository =  (AuthorityRepository) daoManager.getRepository(DAO_TYPE.AUTHORITY);
		Integer minCount = settingProperty.getAuthorityMinCount();
		return minCount < authorityRepository.count();
	}

	@Override
	public PagingItemVO getPagingInfo() {
		SettingRepository settingRepository =  (SettingRepository) daoManager.getRepository(DAO_TYPE.SETTING);
		return settingRepository.findAll().stream().limit(1)
				.map(setting->{
					PagingItemVO pivo = new PagingItemVO();
					pivo.setBoardPagingCount(setting.getBoardPagingCount());
					return pivo;
				}).findFirst().get();
	}

	@Override
	public boolean updatePagingInfo(PagingItemVO pivo) {
		SettingRepository settingRepository =  (SettingRepository) daoManager.getRepository(DAO_TYPE.SETTING);
		MSBSetting setting =  settingRepository.findAll().stream().limit(1).findFirst().get();
		setting.setBoardPagingCount(pivo.getBoardPagingCount());
		settingRepository.flush();
		return true;
	}

}
