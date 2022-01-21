package com.mooop.board.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * 
 */
public class EmailUtil {

    private static final String BODY_CONTENT1="<html  lang='ko'>"+
            "<head><meta charset='UTF-8'></head>"+
            "<body><p>다음주소로 이동하세요 : </p><h1>%s</h1></body>"+
            "</html>";

    private static final String BODY_CONTENT2="<html  lang='ko'>"+
            "<head><meta charset='UTF-8'></head>"+
            "<body><p>해당기기에서 로그인을 하였습니다. : </p><h1>%s</h1></body>"+
            "</html>";



    public static void sendConfirmUrl(String id , String password , String to , String confirUrl){
        String content = String.format(BODY_CONTENT1 , confirUrl);
        sendEmail(id , password , to , content);
    }

    public static void sendNotiOtherDevice(String id , String password , String to , String device){
        String content = String.format(BODY_CONTENT2 , device);
        sendEmail(id , password , to , content);
    }



    private static void sendEmail(String id , String password , String to , String content){

        CompletableFuture.runAsync(()->{
            Properties properties = new Properties();
            try{

                properties.setProperty("mail.transport.protocol","smtp");
                //메일호스트주소를설정합니다
                properties.setProperty("mail.host","smtp.gmail.com");
                //id/password를설정한다.
                properties.put("mail.smtp.auth","true");
                //port설정
                properties.put("mail.smtp.port","465");

                //ssl를사용할경우설정합니다.
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.socketFactory.fallback","false");
                properties.setProperty("mail.smtp.quitwait","false");

                //id와password를설정하고session을생성합니다.
                Session session= Session.getInstance(properties,new Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(id,password);
                    }
                });

                //디버그모드입니다.
                session.setDebug(true);
                //메일메시지를만들기위한클래스를생성합니다.
                MimeMessage message=new MimeMessage(session);
                //송신자설정
                message.setFrom(new InternetAddress(id));
                //수신자설정
                message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
                //메일제목을설정합니다.
                message.setSubject("test email");
                //메일내용을설정을위한클래스를설정합니다.
                message.setContent(new MimeMultipart());

                Multipart mp=(Multipart)message.getContent();
                mp.addBodyPart(getContent(content));


                Transport.send(message);
            }catch(Exception e){
                e.printStackTrace();
            }

        });
    }

    private static BodyPart getContent(String html) throws MessagingException{
        BodyPart bp=new MimeBodyPart();
        bp.setContent(html,"text/html;charset=utf-8");
        return bp;
    }

}
