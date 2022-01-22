package com.mooop.board.component;

import com.mooop.board.entity.MSBAuth;
import com.mooop.board.entity.MSBUpload;
import com.mooop.board.enums.UPLOAD_P_TYPE;
import com.mooop.board.repo.AuthRepository;
import com.mooop.board.repo.DaoManager;
import com.mooop.board.repo.UploadRepository;
import com.mooop.board.utils.MStringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Project : MSimpleBoard
 * Package :com.mooop.board.component
 * Author :  MOoop
 * Date : 18/09/2021
 * Desc : 첨부파일중 image를 web page에 출력해주는 공통 Servlet
 */
@Slf4j
@WebServlet(name = "ImageViewServlet" , urlPatterns = {"/ImageView"})
public class ImageViewServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = MStringUtil.defaultIfEmptyString(req.getParameter("email") , "");
        ClassPathResource rs = new ClassPathResource("static/img/icon_photo.jpeg");

        //Default Image
        String imagePath = rs.getFile().getPath();
        if(!"".equals(email)){
            ApplicationContext wax = (ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            DaoManager daoManager = (DaoManager) wax.getBean("daoManager");

            AuthRepository authRepository = (AuthRepository) daoManager.getRepository(DaoManager.DAO_TYPE.AUTH);
            MSBAuth auth = authRepository.findByEmail(email);
            if(auth != null){
                UploadRepository uploadRepository = (UploadRepository) daoManager.getRepository(DaoManager.DAO_TYPE.UPLOAD);
                MSBUpload info =  uploadRepository.findByBrdIdxAndUtype(auth.getId() , UPLOAD_P_TYPE.REG);
                if(info != null){
                    imagePath = info.getPath();
                }
            }
        }

        byte[] buffer = new byte[1024];
        int cnt;
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imagePath))){
            while((cnt = bis.read(buffer)) > 0){
                resp.getOutputStream().write(buffer , 0 , cnt);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req , resp);
    }
}
