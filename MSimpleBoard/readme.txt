

##################################################################
#			Template Project 구성내용
##################################################################
1. DB 연동 ( MySQL + Common DBCP2.x + JPA )
2. Controller ExceptionHandler 정의 
3. Handler Interceptor + AOP ( log , filter ) 적용
4. Spring Cache적용 ( EHCache )
5. Thread Pool 적용으로 Working Thread 관리
6. log back 
7. 외부 I/F제공 ( Dynamic & Static )


##################################################################
#			Template Project 포팅 방법
##################################################################

project를 import한다.


1. Project Name 변경 ( STS 기준 )
   *  Package Explorer > 프로젝트에 cursor위치 > 우클릭 > Refactor > Rename
   * settings.gradle > rootProject.name 값 변경
   * project 삭제 ( disk에서 삭제는 체크하지 않는다.)
   * project import

2. package 이름 변경
   * Package Explorer > package 이름에 cursor위치 > 우클릭 > Refactor > Rename 
	( 단 , 모든 check box를 체크 : 하위 package 및 txt , xml파일에도 적용된다.) 

