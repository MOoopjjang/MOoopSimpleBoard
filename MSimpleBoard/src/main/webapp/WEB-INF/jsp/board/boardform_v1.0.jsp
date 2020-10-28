<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.ViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewInfoVO" %>
<%@ page import="com.mooop.board.domain.web.AuthenticationResponseVO" %>
<%@ page import="com.mooop.board.domain.web.BoardItemVO" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/registry.css?ver=1">
		<title>MSimpleBoard</title>
		
		<style>
			.form-container{
				width: 600px;
				padding: 10px;
				background-color: white;
			}
		</style>
	</head>
	
<%
		ViewResponse<BoardItemVO> cr = (ViewResponse<BoardItemVO>)request.getAttribute("xdata");
		ViewInfoVO viewInfo = cr.getViewInfo();
		AuthenticationResponseVO authInfo = cr.getAuthentication();
		BoardItemVO item = cr.getData();
		String mode = viewInfo.getMode();
		Long idx = (mode.equals("register"))?-1:item.getBoardIdx();
		System.out.println("idx : "+idx);
	%>
	
	<c:set var="mode" value="<%= mode %>" scope="page" />
	<c:set var="authInfo" value="<%= authInfo %>" scope="page" />
	<c:set var="item" value="<%= item %>" scope="page" />
	<c:set var="idx" value="<%= idx %>" scope="page" />

	<body>
		<div class="container">
			<div class="form-popup">
				<form class="form-container" method="post">
					<h3>
						<c:choose>
							<c:when test="${mode=='register'}">새글작성</c:when>
							<c:otherwise>상세보기</c:otherwise>
						</c:choose>
					</h3>
					<div>
						<input type="email" placeholder="이메일" name="email" id="email" disabled="true" 
							<c:if test="${item!=null && item.getEmail()!=null}">value=${item.getEmail().replace(" ", "&nbsp;")}</c:if>
						/>
					</div>
					
					<div style="margin-top:10px;">
						<input type="text" placeholder="닉네임" name="nick" id="nick" disabled="true" 
							<c:if test="${item!=null && item.getNick()!=null}">value=${item.getNick().replace(" ", "&nbsp;")}</c:if>
						/>
					</div>
					
					<div style="margin-top:10px;">
						<input type="text" placeholder="타이틀" name="title" id="title" 
							<c:if test="${mode!='register'}">disabled="true"</c:if>
						 	<c:if test="${item!=null && item.getTitle()!=null}">value=${ item.getTitle().replace(" ", "&nbsp;")}</c:if>
						/>
					</div>
					
					<div class="form-check" style="margin-top:10px;">
						<input type="checkbox" class="from-check-input" id="secYN" <c:if test="${mode!='register'}">disabled="true"</c:if> />
						<label class="form-check-label" for="secYN">비밀글</label>
					</div>
					
					<div style="margin-top:10px;">
						<label for="content"><b>내용</b></label>
						<textarea id="content" class="form-control col-md-12" rows="8" style="text-align: left;"  
						<c:if test="${mode!='register'}">disabled="true"</c:if>
							><c:if test="${item != null && item.getContent() != null}">${item.getContent().replace(" ", "&nbsp;").trim()}</c:if></textarea>
						
					</div>
				
					<div style="margin-top:10px;">
						<c:if test="${authInfo.getRole()=='USER'}">
							<c:choose>
								<c:when test="${mode=='register'}">
									<button id="btn_action" type="button"  class="btn-xd" style="background-color: #4CAF50;" onclick="dviewAction('${idx}')">등록</button>
								</c:when>
								<c:otherwise>
									<c:if test="${authInfo.getEmail() == item.getEmail()}">
										<button id="btn_action" type="button"  class="btn-xd" style="background-color: #4CAF50;" onclick="dviewAction('${idx}')">편집</button>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
						<button type="button" class="btn-xd" onclick="dviewClose()">닫기</button>
					</div>
					
					<c:if test="${mode!='register' && (authInfo.getEmail() == item.getEmail() || authInfo.getRole()=='ADMIN') }">
						<div>
							<button type="button" class="btn-xd" onclick="textDelete('${idx}')">삭제</button>
						</div>
					</c:if>
				
				</form>
			</div>
		</div>
		
		
		<!-- 변수 제거 -->
		<c:remove var="mode" scope="page"/>
		<c:remove var="authInfo" scope="page"/>
		<c:remove var="item" scope="page"/>
		<c:remove var="idx" scope="page"/>
	
<!-- 
	 		--------------------------------------------------------------
	 				자바스크립트 영역
		    --------------------------------------------------------------
-->

		<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/js/mstringutil.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/network.js"></script> 
		<script src="${pageContext.request.contextPath}/resources/js/uicommon.js"></script>
		<script type="text/javascript">
		
			var $email = document.getElementById('email');
			var $nick = document.getElementById('nick');
			var $title = document.getElementById('title');
			var $content = document.getElementById('content');
			var $secYN = document.getElementById('secYN');
			var $btn_action = document.getElementById('btn_action');
			
		
		    /* 액션 ( 등록 | 편집 | 저장 ) */
			function dviewAction(_idx){
				var $btn_action = document.getElementById('btn_action');
				var actionText = trim(btn_action.innerHTML);
				if(actionText === '등록'){
					register();
				}else if(actionText === '편집'){
					edit();
				}else{
					alert(_idx);
					save(_idx);
				}
				
			}
		    
		    /* 액션 ( 등록 ) */
		    function register(){
		    	var secYn = 'N';
		    	if($secYN !== null && $secYN.checked === true){
		    		secYn = 'Y';
		    	}
		    	var registerData = {
		    			'email':$email.value,
		    			'nick':$nick.value,
		    			'title':$title.value,
		    			'content':$content.value,
		    			'secyn':secYn
		    	}
		    	
		    	startLoading('등록중...');
				$.ajax({
			        url : '${pageContext.request.contextPath}/board/api/register',
					headers: {
			        	"X-CSRF-TOKEN": "${_csrf.token}"
			        },
			        method : "post",
			        data : JSON.stringify(registerData),
			        contentType : 'application/json',
			        success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('등록되었습니다..!');
							movePage('${pageContext.request.contextPath}/board/');
						}else{
							alert('오류가 발생하였습니다.!');
						}
					},
			        error : function( response ){
						stopLoading();
						alert('falied');
					}
			      
			    });
		    	
		    }
		    
		    /* 액션 ( 편집 ) */
		    function edit(){
		    	$title.disabled = false;
				$content.disabled = false;
				$secYN.disabled = false;
				
				$btn_action.innerHTML = '저장';
		    }
		    
		    /* 액션 ( 저장 ) */
		    function save(_idx){
		    	var secYn = 'N';
		    	if($secYN !== null && $secYN.checked === true){
		    		secYn = 'Y';
		    	}
		    	
		    	var saveData = {
		    			'idx':_idx,
		    			'title':$title.value,
		    			'content':$content.value,
		    			'secyn':secYn
		    	}
		    	startLoading('저장중...');
		    	$.ajax({
			        url : '${pageContext.request.contextPath}/board/api/save',
					headers: {
			        	"X-CSRF-TOKEN": "${_csrf.token}"
			        },
			        method : "post",
			        data : JSON.stringify(saveData),
			        contentType : 'application/json',
			        success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('등록되었습니다..!');
							movePage('${pageContext.request.contextPath}/board/');
						}else{
							alert('오류가 발생하였습니다.!');
						}
					},
			        error : function( response ){
						stopLoading();
						alert('falied');
					}
			      
			    });
		    }
		
			/* 닫기 */
			function dviewClose(){
				movePage('${pageContext.request.contextPath}/board/');
			}
			
			/* 삭제 */
			function textDelete(_idx){
				var removeData = {
		    			'idx':_idx
		    	}
				startLoading('삭제중...');
		    	$.ajax({
			        url : '${pageContext.request.contextPath}/board/api/remove',
					headers: {
			        	"X-CSRF-TOKEN": "${_csrf.token}"
			        },
			        method : "post",
			        data : JSON.stringify(removeData),
			        contentType : 'application/json',
			        success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('삭제되었습니다..!');
							movePage('${pageContext.request.contextPath}/board/');
						}else{
							alert('오류가 발생하였습니다.!');
						}
					},
			        error : function( response ){
						stopLoading();
						alert('falied');
					}
			      
			    });
			}
		</script>  
	</body>
</html>

