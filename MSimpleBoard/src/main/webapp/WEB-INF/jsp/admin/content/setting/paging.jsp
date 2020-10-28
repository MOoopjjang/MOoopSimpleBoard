<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.PagingItemVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	AdmViewResponse<PagingItemVO> admViewResponse = (AdmViewResponse<PagingItemVO>)request.getAttribute("xdata");
	PagingItemVO pivo = admViewResponse.getData();
%>

<c:set var="pivo" value="<%= pivo %>" scope="page" />

<div class="content_wrap">
	<div class="container">
		<nav  class="navbar navbar-expand-sm navbar-dark">
			<h4>페이징 설정</h4>
		</nav>
		
		<div>
			<table class="table"  style="border:2px solid #ddd">
				<thead>
					<th>현재</th>
					<th>변경</th>
					<th>-</th>
				</thead>
				
				<tbody>
					<tr>
						<td><input class="form-control" type="text" id="before" name="before" value="${pivo.getBoardPagingCount()}" readonly="true" /></td>
						<td><input class="form-control" type="text" id="after" name="after" placeholder="목록 count" /></td>
						<td><button class="btn btn-primary" type="button" onclick="pagingUpdate()">적용</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>


<c:remove var="pivo" scope="page"/>

<script type="text/javascript">
	var $after = document.getElementById('after');
	
	function pagingUpdate(){
		if($after.value === null || $after.value <= 0){
			alert('입력값을 확인하세요.');
			return;
		}
		
		var updateData = {
			'boardPagingCount':$after.value	
		}
		
		$.ajax({
	        url : '${pageContext.request.contextPath}/admin/api/setting/paging/update',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
	        method : "post",
	        data : JSON.stringify(updateData),
	        contentType : 'application/json',
	        success : function(response){
				stopLoading();
				if(response.result === 'OK'){
					alert('paging 정보가 갱신되었습니다.');
					window.location.href = '${pageContext.request.contextPath}/admin/setting/paging'
				}else{
					alert('오류가 발생하였습니다.!\n'+response.reason);
				}
			},
	        error : function( response ){
				stopLoading();
				alert('오류가 발생하였습니다.!\n'+response.reason);
			}
	      
	    });
	}
	
</script>

