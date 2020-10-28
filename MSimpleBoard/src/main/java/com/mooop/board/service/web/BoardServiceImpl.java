package com.mooop.board.service.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mooop.board.config.property.MUploadProperties;
import com.mooop.board.domain.web.BoardItemVO;
import com.mooop.board.domain.web.UploadFileInfoVO;
import com.mooop.board.entity.MSBBoard;
import com.mooop.board.entity.MSBUpload;
import com.mooop.board.repo.BoardRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.DaoManager.DAO_TYPE;
import com.mooop.board.repo.UploadRepository;
import com.mooop.board.repo.UserRepository;
import com.mooop.board.utils.MFileUtil;
import com.mooop.board.utils.MStringUtil;

@Service("boardService")
public class BoardServiceImpl implements BoardService{
	
	private static Logger logger = LoggerFactory.getLogger("BoardService");
	
	@Autowired
	private DaoManager daoManager;
	
	@Autowired
	private MUploadProperties mUploadProperties;
	
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
				.create(brd.getDtCreate())
				.hit(brd.getHit())
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
		Sort sort = new Sort(Direction.DESC , "dtUpdate");
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
			
			// get : 첨부 파일정보
			UploadRepository urRepository = (UploadRepository)daoManager.getRepository(DAO_TYPE.UPLOAD);
			List<UploadFileInfoVO> uploadList = Optional.ofNullable(urRepository.findAllByBrdIdx(brd.getId()))
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
							
			
			return BoardItemVO.builder()
					.idx(brd.getId())
					.email(brd.getUser().getAuth().getEmail())
					.name(brd.getUser().getUserName())
					.nick(brd.getUser().getUserNick())
					.title(brd.getTitle())
					.content(brd.getContent())
					.sec(brd.getSecYN())
					.create(brd.getDtCreate())
					.hit(brd.getHit())
					.uploadFile(uploadList)
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
			brd.setDtUpdate((new Date()));
			
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
		return uploadRepository.findById(idx).map(msbUpload->{
			try {
				File f = new File(msbUpload.getPath());
				if(f.exists()) {
					String mimeType = MStringUtil.defaultIfEmptyString(URLConnection.guessContentTypeFromName(f.getName()), "application/octet-stream");
					Resource rs = new UrlResource(f.toURI());
					InputStreamResource isr = new InputStreamResource(new FileInputStream(f));
					
					/* AS-IS
					return ResponseEntity.ok()
											.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+f.getName()+"\"")
											.contentLength(f.length())
											.contentType(MediaType.parseMediaType(mimeType))
											.body(isr);
											*/
					
					HttpHeaders httpHeaders = new HttpHeaders();
					httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\""+rs.getFilename()+"\"");
					httpHeaders.setCacheControl("no-cache");
					httpHeaders.setContentType(MediaType.parseMediaType(mimeType));
					httpHeaders.setContentLength(rs.contentLength());
					
					return ResponseEntity.ok()
							.headers(httpHeaders)
							.body(isr);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		
			return null;
		}).orElse(null);
	}
	
	
	
//=================================================  PRIVATE =================================================	
	
	private void uploadFileSave(MSBBoard boardInfo , MultipartHttpServletRequest mpsr) {
		
		logger.info("########## uploadFileSave Start ##########");
		CompletableFuture.supplyAsync(()->{
			// 첨부파일 disk에 저장
			Iterator<String> iter = mpsr.getFileNames();
			MultipartFile mf = null;
			List<UploadFileInfoVO> rList = new ArrayList<>();
			
			//계정별로 첨부파일 디렉토리생성 
			String subDirName = MFileUtil.makeDirectory(mUploadProperties.getPath(), boardInfo.getUser().getAuth().getEmail(), false)?boardInfo.getUser().getAuth().getEmail():"";
			
			while(iter.hasNext()) {
				try {
					mf = mpsr.getFile(iter.next());
					String cname = MStringUtil.randomStringGenerator(16);
					File f = new File(mUploadProperties.getPath()+"/"+subDirName+"/"+cname);
					mf.transferTo(f);
					
					rList.add( UploadFileInfoVO.builder().cname(cname)
							.oname(mf.getOriginalFilename())
							.path(f.getAbsolutePath())
							.size(f.length())
							.build());
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			return rList;
		}).thenApply((list)->{
			//첨부파일 정보 DB에 저장
			UploadRepository uploadRepository = (UploadRepository) daoManager.getRepository(DAO_TYPE.UPLOAD);
			list.stream().forEach(ufiv->{
				MSBUpload upload = new MSBUpload();
				upload.setBrd_idx(boardInfo.getId());
				upload.setCname(ufiv.getCname());
				upload.setOname(ufiv.getOname());
				upload.setPath(ufiv.getPath());
				upload.setSize(ufiv.getSize());
				
				uploadRepository.save(upload);
			});
			return true;
		}).thenAccept((v)->logger.info("##### Upload File Complete : "+v+" #####"));
		
		logger.info("########## uploadFileSave End ##########");
	}



	
}
