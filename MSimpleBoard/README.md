# MSimpleBoard ( 계시판 )  
---
> 구성  
> > * SpringBoot 2.1  
> > * MySQL 8.x ( docker )  
> > * JSP Template  
> > * JDK 1.8  
---
> 세부기술  
> > * JPA 
> > * Spring Security  
> > * View Template : JSP  
---  
> 제공서비스  
> > * 인증기능  
> >> - 사용자 가입 / 정보변경 / 탈퇴 기능  
> >> - 로그인 기능  
> >> - 패스워드 5회 불일치시 계정잠금 기능  
> >> - 로그인 device 불일치시 알림 email 발송기능  
> >> - 로그인 요청 IP 불일치시 계정 일시정지 기능 및 인증 페이지 이메일로 발송기능
> > * 계시판 기능  
> >> - 글작성 / 편집 / 삭제 기능  
> >> - 암호 설정기능  
> >> - 검색기능  
> >> - 첨부파일 업로드 / 다운로드 기능  
> > * 관리자 기능  
> >> - 회원관리 기능  
> >> - 계시판 관리기능  
> >> - 공지사항 관리기능  
> >> - 기타 기능  
---  
> 기타  
> > * dummy 데이타 활성화  
> >> - msp.static.mode=[Y|N]  
> >> - 테스트 계정 msp.static.mode=Y 상태일경우 guest@test.com/guest (GUEST) , user@test.com/test (USER)  , admin@test.com/admin (ADMIN )