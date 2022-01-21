<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.AuthorityItemVO" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<style>
/* 
	td p{
		margin:0;
		padding:0;
		
	  font-famliy : arial, helvetica, sans-serif;
	  font-size : 14px; 
	  line-height : 1.6;
	  font-weight : bold;
	}
	 */
	p{
		margin:0;
		padding:0;
		
	  font-famliy : arial, helvetica, sans-serif;
	  font-size : 14px; 
	  line-height : 1.6;
	  font-weight : bold;
	}
	
</style>

<%
	AdmViewResponse<Page<AuthorityItemVO>> admViewResponse = (AdmViewResponse<Page<AuthorityItemVO>>)request.getAttribute("xdata");
	Page<AuthorityItemVO> pageInfo = admViewResponse.getData();
	List<AuthorityItemVO> items = pageInfo.getContent();
%>

<c:set var="pageInfo" value="<%= pageInfo %>" scope="page" />
<c:set var="items" value="<%= items %>" scope="page" />

<div class="content_wrap">
	<div class="container">
		<nav class="navbar navbar-expand-sm navbar-dark" style="vertical-align: middle;">
			<h5 style="margin:0;padding:0;">권한목록</h5>
			<p style="display: inline-block;margin-left:20px;font-size:12px;color:red;"> 최소:1 ~ 최대 :8 </p>
		</nav>
		
		<div>
			<table class="table" style="border:2px solid #ddd">
				<thead>
					<th>권한</th>
					<th>설명</th>
					<th>-</th>
				</thead>
				
				<tbody>
					<c:forEach var="item" items="${items}">
						<tr>
							<td style="vertical-align: middle;"><p>${item.getAuthorityName()}</p></td>
							<td style="vertical-align: middle;"><p style="font-size:12px;color:#08088A;">${item.getAuthorityDesc()}</p></td>
							<td style="vertical-align: middle;"><button type="button" class="btn btn-primary" onclick="authorityDel('${item.getAuthorityName()}')">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div>
			<nav class="navbar navbar-expand-sm navbar-dark">
				<h5>권한추가</h5>
			</nav>
			<div>
				<table class="msb-com-table">
					<thead>
						<th>권한</th>
						<th>설명</th>
						<th>-</th>
					</thead>
					
					<tbody>
						<tr>
							<td style="vertical-align: middle;"><input type="text" class="form-control" id="authority" name="authority" placeholder="권한" /></td>
							<td style="vertical-align: middle;"><input type="text" class="form-control"id="desc" name="desc" placeholder="설명" /></td>
							<td style="vertical-align: middle;"><button type="button" class="btn btn-primary" onclick="authorityAdd()">추가</button></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<!--
			권한삭제시 암호확인을 받는다.
		  -->
		<div class="modal fade" id="pwdModal">
			<div class="modal-dialog">
				<div class="modal-content">
					<!-- header -->
					<div class="modal-header">
						<h4 class="modal-title">암호를 입력하세요</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					
					<!-- body -->
					<div class="modal-body">
						<input class="form-control" type="password" id="password" name="password" placeholder="암호" />
					</div>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-primary">확인</button>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>
	
	
</div>

<c:remove var="pageInfo" scope="page"/>
<c:remove var="items" scope="page"/>


<script type="text/javascript">

	let $authority = document.getElementById('authority');
	let $desc = document.getElementById('desc');
	
	
	/* 권한을 추가한다 */
	function authorityAdd(){
		
		if(trim($authority.value).length === 0 || trim($desc.value).length === 0){
			alert('빈값이 있습니다. 다시입력해 주세요!')
			return;
		}

		let nAuthority = {
			'authorityName':$authority.value,
			'authorityDesc':$desc.value
		}
		
		startLoading('권한 추가중...');
		$.ajax({
			url : '${pageContext.request.contextPath}/admin/api/setting/authority/add',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
			method : "post",
			data : JSON.stringify(nAuthority),
			contentType : 'application/json',
			success : function(response){
				stopLoading();
				if(response.result === 'OK'){
					alert('새로운 권한이 등록되었습니다.');
					movePage('${pageContext.request.contextPath}/admin/setting/authority');
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
	
	/* 권한 삭제 */
	function authorityDel(_authorityName){
		result = confirm(_authorityName+' 권한을 삭제하시겠습니까?');
		if(result === true){
			let nAuthority = {
					'authorityName':_authorityName,
					'authorityDesc':''
				}
			
			startLoading('삭제중...');
			$.ajax({
				url : '${pageContext.request.contextPath}/admin/api/setting/authority/del',
				headers: {
		        	"X-CSRF-TOKEN": "${_csrf.token}"
		        },
				method : "post",
				data : JSON.stringify(nAuthority),
				contentType : 'application/json',
				success : function(response){
					stopLoading();
					if(response.result === 'OK'){
						alert('권한이 삭제되었습니다.');
						movePage('${pageContext.request.contextPath}/admin/setting/authority');
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
	}
</script>

