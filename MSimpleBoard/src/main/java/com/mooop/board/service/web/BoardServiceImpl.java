package com.mooop.board.service.web;

import com.mooop.board.component.AttachFileService;
import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.domain.web.UploadFileInfoVO;
import com.mooop.board.entity.MSBBoard;
import com.mooop.board.entity.MSBUpload;
import com.mooop.board.enums.UPLOAD_P_TYPE;
import com.mooop.board.repo.BoardRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.UploadRepository;
import com.mooop.board.repo.UserRepository;
import com.mooop.board.utils.MFileUtil;
import com.mooop.board.utils.MStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	private static Logger logger = LoggerFactory.getLogger("BoardService");

	private AttachFileService attachFileService;
	private DaoManager daoManager;

	public BoardServiceImpl(AttachFileService attachFileService
	,DaoManager daoManager){
		this.attachFileService = attachFileService;
		this.daoManager = daoManager;
	}
	

	/* convert MSBBoard Object to BoardItemVO Object */
	Function<MSBBoard, BoardItemVO> convertFunc = (brd)->{
		return makeBoardItem(brd);
	};
	
	private BoardItemVO makeBoardItem(MSBBoard brd) {
		return BoardItemVO.builder()
				.idx(brd.getId())
				.email(brd.getUser().getAuth().getEmail())
				.name(brd.getUser().getUserName())
				.nick(brd.getUser().getUserNick())
				.title(brd.getTitle())
				.content(brd.getContent())
				.sec(brd.getSecYN())
				.create(brd.getCreateDt())
				.hit(brd.getHit())
				.uploadFile(getBrdUploadFileInfo(brd.getId()))
				.build();
	}

	
	
	/**
	 * 
	 */
	@Override
	public Page<BoardItemVO> getBoardItemList(String category , String text , Integer page , Integer size) throws Exception {
		BoardRepository repository =  (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		Integer nPage = Optional.ofNullable(page).orElse(0);
		Integer nSize = Optional.ofNullable(size).orElse(10);
		
		Page<BoardItemVO> pageInfo = null;
		//갱신날짜로 sorting...
		Sort sort = new Sort(Direction.DESC , "updateDt");
		if(MStringUtil.validCheck(category) && MStringUtil.validCheck(text)) {
			if(category.equals("nick")) {
				pageInfo = repository.findByNick(text, PageRequest.of(nPage, nSize , sort)).map(convertFunc);
			}else {
				pageInfo = repository.findByTitleLike("%"+text+"%", PageRequest.of(nPage, nSize , sort)).map(convertFunc);
			}
		}else {
			pageInfo = repository.findAll(PageRequest.of(nPage, nSize , sort)).map(convertFunc);
		}
		return pageInfo;
	}
	
	
	/**
	 * 
	 */
	@Override
	public BoardItemVO getBoardItem(Long idx) throws Exception {
		BoardRepository repository =  (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		return repository.findById(idx).map(brd->{
			//set : hit count
			brd.setHit(brd.getHit().intValue() + 1);
			repository.flush();
			return BoardItemVO.builder()
					.idx(brd.getId())
					.email(brd.getUser().getAuth().getEmail())
					.name(brd.getUser().getUserName())
					.nick(brd.getUser().getUserNick())
					.title(brd.getTitle())
					.content(brd.getContent())
					.sec(brd.getSecYN())
//					.create(brd.getDtCreate())
					.hit(brd.getHit())
					.uploadFile(getBrdUploadFileInfo(brd.getId()))
					.build();
			}).orElseThrow(NullPointerException::new);
	}

	@Override
	public boolean deleteBoardItem(BoardItemVO item) throws Exception {
		BoardRepository repository =  (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		return repository.findById(item.getBoardIdx()).map(brd->{
			brd.getUser().getBoards().remove(brd);
			repository.delete(brd);
			
			// 첨부파일 삭제
			CompletableFuture.runAsync(()->{
				item.getUploadFileInfos().stream().forEach(ufiv->{
					try {
						this.deleteUploadFile(ufiv.getIdx());
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			});
			return true;
		}).orElse(false);
	}

	@Override
	public boolean insertBoardItem(BoardItemVO item , MultipartHttpServletRequest mpsr) throws Exception {
		UserRepository repository = (UserRepository) daoManager.getRepository(DAO_TYPE.USER);
		return Optional.ofNullable(repository.findByUserNick(item.getNick())).map(user->{
			MSBBoard nItem = new MSBBoard();
			nItem.setTitle(item.getTitle());
			nItem.setContent(item.getContent());
			nItem.setHit(0);
			nItem.setSecYN(item.getSecYn());
			nItem.setUser(user);
			
			BoardRepository brdRepository = (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
			MSBBoard sData = brdRepository.saveAndFlush(nItem);
			
			uploadFileSave( sData , mpsr);
			return true;
		}).orElse(false);
	}

	@Override
	public boolean updateBoardItem(BoardItemVO item , MultipartHttpServletRequest mpsr) throws Exception {
		BoardRepository brdRepository = (BoardRepository) daoManager.getRepository(DAO_TYPE.BRD);
		return Optional.ofNullable(brdRepository.findById(item.getBoardIdx())).get().map(brd->{
			brd.setTitle(item.getTitle());
			brd.setSecYN(item.getSecYn());
			brd.setContent(item.getContent());
//			brd.setDtUpdate((new Date()));
			
			brdRepository.flush();
			
			uploadFileSave(brd , mpsr);
			return true;
		}).orElse(false);
	}



	@Override
	public boolean deleteUploadFile(Long idx) throws Exception {
		UploadRepository uploadRepository = (UploadRepository) daoManager.getRepository(DAO_TYPE.UPLOAD);
		// 파일삭제
		uploadRepository.findById(idx).ifPresent(msbUpload->MFileUtil.removeFile(msbUpload.getPath()));
		// DB정보 삭제
		uploadRepository.deleteById(idx);
		return true;
	}
	
	
	@Override
	public ResponseEntity<InputStreamResource> downloadFile(Long idx , HttpServletResponse response) throws Exception {
		UploadRepository uploadRepository = (UploadRepository) daoManager.getRepository(DAO_TYPE.UPLOAD);
		return uploadRepository.findById(idx)
			.map(msbUpload->attachFileService.download(msbUpload.getPath()))
			.orElse(null);
	}
	
	
	
//=================================================  PRIVATE =================================================	
	
	private void uploadFileSave(MSBBoard boardInfo , MultipartHttpServletRequest mpsr) {
		logger.info("########## uploadFileSave Start ##########");

		UploadRepository uploadRepository = (UploadRepository) daoManager.getRepository(DAO_TYPE.UPLOAD);
		Iterator<String> iter = mpsr.getFileNames();
		List<MultipartFile> files = new ArrayList<>();
		while(iter.hasNext()) {
			files.add(mpsr.getFile(iter.next()));
		}
		List<UploadFileInfoVO> list = attachFileService.upload(files , boardInfo.getUser().getAuth().getEmail());
		for(UploadFileInfoVO ufiv : list){
			MSBUpload upload = new MSBUpload();
			upload.setBrd_idx(boardInfo.getId());
			upload.setCname(ufiv.getCname());
			upload.setOname(ufiv.getOname());
			upload.setUtype(UPLOAD_P_TYPE.BOARD);
			upload.setPath(ufiv.getPath());
			upload.setSize(ufiv.getSize());

			uploadRepository.save(upload);
		}
		logger.info("########## uploadFileSave End ##########");
	}



	private List<UploadFileInfoVO> getBrdUploadFileInfo(Long brdId){
		UploadRepository urRepository = (UploadRepository)daoManager.getRepository(DAO_TYPE.UPLOAD);
		return Optional.ofNullable(urRepository.findAllByBrdIdx(brdId))
				.filter(l->l.size() > 0)
				.map(l->{
					return l.stream()
							.map(mu->UploadFileInfoVO.builder().idx(mu.getIdx())
									.brd(mu.getBrd_idx())
									.path(mu.getPath())
									.cname(mu.getCname())
									.oname(mu.getOname())
									.size(mu.getSize()).build()
							)
							.collect(Collectors.toList());
				})
				.orElse(new ArrayList<>());
	}

	
}
