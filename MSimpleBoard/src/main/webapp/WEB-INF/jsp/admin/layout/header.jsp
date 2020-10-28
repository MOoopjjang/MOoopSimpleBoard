<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.AuthenticationResponseVO" %>
<%@ page import="com.mooop.board.domain.web.AdmViewInfoVO" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<style>
	.nav-wrap{
		display:flex;
		align-items:center;
		
		width:98%;
	/* 	height:80px; */
	}
	
	.nav-wrap div{
		display: inline-block;
	}
	
	.nav-wrap p{
		display: inline-block;
		
		margin:0;
		
		font-famliy : arial, helvetica, sans-serif;
		  font-style : italic;
		  font-size : 14px;
		  line-height : 1.6;
		  font-weight : bold;
	}
	
	.nav-wrap button{
		background-color: white;
		border: 1px solid #084B8A;
		
		color:#084B8A;
		text-align: center;
		
		font-famliy : arial, helvetica, sans-serif;
		font-size : 14px;
		line-height : 1.6;
		font-weight : bold;
	}
	
</style>

<%
	AdmViewResponse ar = (AdmViewResponse)request.getAttribute("xdata");
	AuthenticationResponseVO authInfo = ar.getAuthenticationInfo();
	AdmViewInfoVO viewInfo = ar.getAdmViewInfo();
	
	/* 
	authInfo.getNick();
	authInfo.getRole()
	 */
	System.out.println(authInfo.getNick());
%>

<c:set var="authInfo" value="<%=authInfo %>" scope="page" />
<c:set var="viewInfo" value="<%=viewInfo %>" scope="page" />

<nav class="navbar navbar-expand-sm ">
	<div class="nav-wrap">
		<img alt="" src="${pageContext.request.contextPath}/resources/img/icon_user.png" style="wdith:40px;height:40px;">
		<p>${authInfo.getNick()} ( ${authInfo.getRole()} )</p>
		<p style="margin-left:20px;">: ${viewInfo.getCurrentTime()}</p>
		<div style="margin-left:auto; ">
			<button type="button" class="btn btn-primary" onclick="moveBoardPage()">계시판이동</button>
			<button type="button" class="btn btn-primary" onclick="logoutFunc()">로그아웃</button>
		</div>
	</div>
</nav>

<script type="text/javascript">

	/* 계시판으로 이동 */
	function moveBoardPage(){
		window.location.href = '${pageContext.request.contextPath}/board/main?category=&text=&page=0&size=10';
	}
	
	/* 로그아웃 */
	function logoutFunc(){
		window.location.href='${pageContext.request.contextPath}/logout';
	}

</script>