<%@ page import="com.mooop.board.entity.MSBUser" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="UTF-8" >
		<link rel='stylesheet' type="text/css" href="/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
		<title>MSimpleBoard</title>
		<style type="text/css">
			.main-wrap{
			}

			.main-wrap p{
				font-size: 60px;
				font-weight:bold;
				text-align: center;
			}

			.main-wrap p:nth-of-type(1){
				font-size: 60px;
				color: #0B4C5F;
			}

			.main-wrap p:nth-of-type(2){
				font-size: 40px;
				color: red;
			}

			.main-wrap p:nth-of-type(3){
				font-size: 20px;
				color: #0B4C5F;
			}

		</style>
	</head>

	<%
		String result = (String) request.getAttribute("result");
		MSBUser userInfo = (MSBUser) request.getAttribute("xdata");
	%>

	<body>
		<div style="display:flex;align-items:center;justify-content: center;background-color:lightgray; width:100%;height:800px;">
			<div class="main-wrap">
				<div>
					<p>MSimpleBoard</p>
					<c:choose>
						<c:when test='<%= result.equals("OK") %>'>
							<p><%= userInfo.getUserNick() %> 님의 계정이 활성화 되었습니다.</p>
						</c:when>
						<c:otherwise>
							<p><%= userInfo.getUserNick() %> 님의 계정이 활성화 실패하였습니다..</p>
						</c:otherwise>
					</c:choose>
					<div style="display: flex;align-items: center;justify-content: center;">
						<button class="btn btn-warning" style="width:50%;margin-top:40px;background-color: white;border: 1px solid blue;" onclick="goHome()" >
						Home
						</button>
					</div>
				</div>
			</div>
		</div>


		 <script src="/resources/asset/jquery-3.4.1.min.js"></script>
		<script src="/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
		<script src="/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
		<script src="/resources/js/common.js"></script>
		<script src="/resources/js/mstringutil.js"></script>
		<script src="/resources/js/network.js"></script>
		<script src="/resources/js/uicommon.js"></script>
		<script type="text/javascript">

			function goHome(){
				movePage('/board/main?category=&text=&page=0&size=10');
			}

		</script>
	</body>


</html>