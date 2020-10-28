<%@ page contentType="text/html; charset=UTF8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ page import="com.mooop.board.domain.ViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewInfoVO" %>
<%@ page import="com.mooop.board.domain.web.AuthenticationResponseVO" %>
<%@ page import="com.mooop.board.domain.web.SearchResponseVO" %>
<%@ page import="com.mooop.board.domain.web.BoardItemVO" %>

<html lang="ko">
	<head>
		<meta charset="UTF-8" >
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>MSimpleBoard</title>
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/top.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/layer.css?ver=1">
	</head>
	
<%
	ViewResponse<Page<BoardItemVO>> cr = (ViewResponse<Page<BoardItemVO>>)request.getAttribute("xdata");
	AuthenticationResponseVO authInfo = cr.getAuthentication();
	SearchResponseVO searchInfo = cr.getSearch();
	ViewInfoVO viewInfo = cr.getViewInfo();
	Page<BoardItemVO> pageInfo = (Page<BoardItemVO>)cr.getData();
	List<BoardItemVO> items = pageInfo.getContent();
%>

	

	<body>
		<c:set var="authInfo" value="<%= authInfo %>" scope="session" />
		<c:set var="searchInfo" value="<%= searchInfo %>" scope="session" />
		<c:set var="viewInfo" value="<%= viewInfo %>" scope="session"/>
		<c:set var="pageInfo" value="<%= pageInfo %>" scope="session"/>
		<c:set var="boardItems" value="<%= pageInfo.getContent() %>" scope="session"/>
		<c:set var="nick" value="<%= authInfo.getNick() %>" scope="session" />
		<c:set var="dtaccess" value="<%= viewInfo.getCurrentTime() %>" scope="session"/>
		<c:set var="role" value="<%= authInfo.getRole() %>" scope="session" />
	
		<!-- 로그인정보  , 기타.. -->
		<jsp:include page="top.jsp" />
		
		<!-- 계시판 목록  , 새글쓰기  -->
		<jsp:include page="mid.jsp" />
		
		<!-- 미정 -->
	<%-- 	<jsp:include page="bottom.jsp" /> --%>
		
		
		<c:remove var="authInfo" scope="session"/>
		<c:remove var="searchInfo" scope="session"/>
		<c:remove var="viewInfo" scope="session"/>
		<c:remove var="pageInfo" scope="session"/>
		<c:remove var="boardItems" scope="session"/>
		<c:remove var="nick" scope="session"/>
		<c:remove var="dtaccess" scope="session"/>
		<c:remove var="role" scope="session"/>
<!-- 
	 		--------------------------------------------------------------
	 				자바스크립트 영역
		    --------------------------------------------------------------
-->
<%-- 
		<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/js/network.js"></script> 
		<script src="${pageContext.request.contextPath}/resources/js/uicommon.js"></script>
 --%>
		<script type="text/javascript">
		</script>
	</body>
</html>