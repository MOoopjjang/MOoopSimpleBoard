package com.mooop.board.service.web;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mooop.board.domain.web.AdmUserItemVO;
import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.entity.MSBHistory;
import com.mooop.board.entity.MSBUser;
import com.mooop.board.repo.BoardRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.UserRepository;
import com.mooop.board.utils.MDateUtil;

@Service("boardManagerService")
public class BoardManagerServiceImpl implements BoardManagerService{

	private final DaoManager daoManager;
	public BoardManagerServiceImpl(DaoManager daoManager) {
		this.daoManager = daoManager;
	}


	@Override
	public Page<BoardItemVO> getMostHitsBoardItemList() {
		BoardRepository boardRepository =  (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		
		return new PageImpl<BoardItemVO>(boardRepository.findAll(new Sort(Direction.DESC , "hit")).stream().limit(10).map(brd->{
			return BoardItemVO.builder().idx(brd.getId())
					.create(brd.getCreateDt())
					.title(brd.getTitle())
					.sec(brd.getSecYN())
					.nick(brd.getUser().getUserNick())
					.name(brd.getUser().getUserName())
					.hit(brd.getHit())
					.email(brd.getUser().getAuth().getEmail())
					.content(brd.getContent())
					.build();
			}).collect(Collectors.toList()));
	}

	@Override
	public Page<AdmUserItemVO> getMostUploaderList() {
		UserRepository userRepository =  (UserRepository) daoManager.getRepository(DAO_TYPE.USER);
		List<MSBUser> l = userRepository.findAll().stream().sorted((s1 , s2)->{
			return Integer.compare(s2.getBoards().size(), s1.getBoards().size());
		}).collect(Collectors.toList());
		return new PageImpl<AdmUserItemVO>(l.stream().limit(10).map(u->{
					MSBHistory his = u.getAuth().getHistory();
					AdmUserItemVO auivo = new AdmUserItemVO();
					auivo.setUserName(u.getUserName());
					auivo.setStatus(u.getAuth().getStatus().getStatus());
					auivo.setRole(u.getAuth().getUserRole().getRole());
					auivo.setPassword("");
					auivo.setNickName(u.getUserNick());
					auivo.setLastLogin(MDateUtil.convertDateTimeString(his.getCreateDt()));
					auivo.setEnable(u.getAuth().getEnable());
					auivo.setEmail(u.getAuth().getEmail());
					auivo.setDesc(u.getUserDesc());
					auivo.setAddr(u.getUserAddr());
					auivo.setUploadCount(u.getBoards().size());
					return auivo;
				}).collect(Collectors.toList()));
	}

	@Override
	public BoardItemVO getBoardDetailInfo(Long idx) {
		BoardRepository boardRepository = (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		return boardRepository.findById(idx).map(brd->{
			return BoardItemVO.builder().title(brd.getTitle())
										.content(brd.getContent())
										.email(brd.getUser().getAuth().getEmail())
										.sec(brd.getSecYN())
										.nick(brd.getUser().getUserNick())
										.name(brd.getUser().getUserName())
										.hit(brd.getHit())
										.idx(brd.getId())
										.build();
		}).orElse(new BoardItemVO());
	}

	@Override
	public boolean removeBoardItemInfo(Long idx) {
		BoardRepository boardRepository = (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		return boardRepository.findById(idx).map(brd->{
			boardRepository.delete(brd);
			return true;
		}).orElse(false);
	}

}
