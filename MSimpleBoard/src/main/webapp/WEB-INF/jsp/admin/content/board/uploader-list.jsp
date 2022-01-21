<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.UserItemVO" %>
<%@ page import="com.mooop.board.domain.web.AdmUserItemVO" %>
<%@ page import="org.springframework.data.domain.Page" %>


<%
	AdmViewResponse<Page<AdmUserItemVO>> admViewResponse = (AdmViewResponse<Page<AdmUserItemVO>>)request.getAttribute("xdata");
	Page<AdmUserItemVO> pageInfo = admViewResponse.getData();
	List<AdmUserItemVO> items = pageInfo.getContent();
%>

<c:set var="pageInfo" value="<%= pageInfo %>" scope="page" />
<c:set var="items" value="<%= items %>" scope="page" />

<div class="content_wrap">
	<div class="container">
		<nav class="navbar navbar-expand-sm navbar-dark" >
			<h2>최다 UPLOADER</h2>
		</nav>
		<div style="margin-top:10px;">
			<table class="msb-com-table">
				<thead>
					<th>No</th>
					<th>이름</th>
					<th>Email</th>
					<th>Nick</th>
					<th>Role</th>
					<th>마지막로그인</th>
					<th>count</th>
					<th>상태</th>
				</thead>
				
				<tbody>
					<c:forEach var="item" items="${items}" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td><a style="color:black;" href="javascript:void(0);" onclick="moveDViewPage('${item.getEmail()}')">${item.getUserName()}</a></td>
							<td>${item.getEmail()}</td>
							<td>${item.getNickName()}</td>
							<td>${item.getRole()}</td>
							<td>${item.getLastLogin()}</td>
							<td>${item.getUploadCount()}</td>
							<td>${item.getStatus()}</td>
						</tr>
					</c:forEach>
					
				</tbody>
			</table>
		</div>
	</div>
</div>


<c:remove var="pageInfo" scope="page"/>
<c:remove var="items" scope="page"/>

<script type="text/javascript">
	/* 상세페이지 이동 */
	function moveDViewPage(_email){
		let callUri='/admin/board/most/uploader';
		movePage('${pageContext.request.contextPath}/admin/user/dview?email='+_email+"&callUri="+callUri+"&mode=");
	}
</script>