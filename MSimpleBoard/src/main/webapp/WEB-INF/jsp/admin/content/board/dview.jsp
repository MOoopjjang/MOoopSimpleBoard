<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.BoardItemVO" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel='stylesheet' type="text/css" href="/resources/css/registry.css?ver=1">

<%
	AdmViewResponse<BoardItemVO> ar = (AdmViewResponse<BoardItemVO>)request.getAttribute("xdata");
	BoardItemVO item = ar.getData();
%>

<c:set var="item" value="<%= item %>" scope="page" />

<div class="content_wrap" style="width:600px;">
	<div class="container" style="height:100%;">
		<div class="form-popup" style="height:100%;">
			<form class="form-container">
				<div>
					<h3>상세보기</h3>
				</div>
				
				<div style="margin-top:20px;">
					<input type="text" name="email" id="email" placeholder="이메일" disabled="true" value="${item.getEmail()}" />
				</div>
				<div style="margin-top:10px;">
					<input type="text" name="nick" id="nick" placeholder="닉네임" disabled="true" value="${item.getNick()}" />
				</div>
				<div style="margin-top:10px;">
					<input type="text" name="title" id="title" placeholder="타이틀" disabled="true" value="${item.getTitle()}" /> 
				</div>
				
				<div class="form-check" style="margin-top:10px;">
					<input class="form-check-input" type="checkbox" />
					<label class="form-check-label" >비활성화</label>
				</div>
				
				<div style="margin-top:10px;">
					<label >본문</label>
					<textarea class="form-control col-md-12" rows="8" style="text-align: left;" disabled="true">${item.getContent()}</textarea>
				</div>
				
				<div style="margin-top:20px;">
					<button type="button" class="btn-xd" onclick="deleteFunc('${item.getBoardIdx()}')">삭제</button>
				</div>
				
			</form>
		</div>
	</div>
</div>

<c:remove var="item" scope="page" />

<script type="text/javascript">
	function deleteFunc(_idx){
		let removeData = {
   			'idx':_idx
    	}
    	
		startLoading('삭제 진행중...');
    	$.ajax({
			url : '/admin/api/board/remove',
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
				 	movePage('/admin/board/most/hit');
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