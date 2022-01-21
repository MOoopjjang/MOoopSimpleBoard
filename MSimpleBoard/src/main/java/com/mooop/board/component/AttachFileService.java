package com.mooop.board.component;

import com.mooop.board.config.property.MUploadProperties;
import com.mooop.board.domain.web.UploadFileInfoVO;
import com.mooop.board.utils.MFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.service.web
 * Author :  MOoop
 * Date : 20/09/2021
 * Desc :
 */
@Slf4j
@Service("attachFileService")
public class AttachFileService {
    private MUploadProperties mUploadProperties;
    public AttachFileService(MUploadProperties mUploadProperties){
        this.mUploadProperties = mUploadProperties;
    }


    /**
     * 첨부된 파일을 storage에 저장한다.
     *
     * @param files
     * @param subDir
     * @return
     */
    public List<UploadFileInfoVO> upload(List<MultipartFile> files , String subDir){
       MFileUtil.makeDirectory(mUploadProperties.getPath() , subDir , false);
       return files.parallelStream().map(mf->{
           String targetName = String.join("_"
               , UUID.randomUUID().toString().replaceAll("-" , "")
           ,mf.getOriginalFilename());
           String oName = mf.getOriginalFilename();
           long fileSize = mf.getSize();
           String targetFullPath = String.join("/" , mUploadProperties.getPath() , subDir , targetName);
           try{
               mf.transferTo(new File(targetFullPath));
           }catch (Exception e){e.printStackTrace();}

           return UploadFileInfoVO.builder()
               .cname(targetName)
               .oname(oName)
               .path(targetFullPath)
               .size(fileSize)
               .build();
       }).collect(Collectors.toList());
    }


    /**
     * 첨부된 파일을 다운로드 한다.
     *
     * @param targetName
     * @return
     */
    public ResponseEntity<InputStreamResource> download(String subDir , String targetName){
        String fullPath = String.join(mUploadProperties.getPath() , subDir , targetName);
        return download(fullPath);
    }


    /**
     *
     * @param downloadPath
     * @return
     */
    public ResponseEntity<InputStreamResource> download(String downloadPath){
        return Optional.of(new File(downloadPath))
            .filter(f->f.exists())
            .filter(f->f.isFile())
            .map(f->{
                String mimeType = URLConnection.guessContentTypeFromName(f.getName());
                if(mimeType == null){
                    mimeType = "application/octet-stream";
                }

                try {
                    Resource rs = new UrlResource(f.toURI());
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setContentType(MediaType.parseMediaType(mimeType));
                    httpHeaders.setContentLength(rs.contentLength());
                    httpHeaders.setCacheControl("no-cache");
                    httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION , "attachment;filename=\""+rs.getFilename()+"\"");
                    InputStreamResource isr = new InputStreamResource(new FileInputStream(f));
                    return ResponseEntity.ok().headers(httpHeaders).body(isr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            })
            .orElseGet(()->null);
    }


}
