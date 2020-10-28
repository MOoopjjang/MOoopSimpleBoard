<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ko">
	<head>
		<meta charset="UTF-8" >
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css">
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/layer.css?ver=1">
		<title>MSimpleBoard</title>
		
	</head>
	
	<body>
	<%
		String error = (String)request.getAttribute("error");
		System.out.println("error :: "+error);
	%>
		<div class="container">
			<div class="form-popup" id="loginform">
				<form class="form-container" action="${pageContext.request.contextPath}/loginproc" method="post">
					<h1>로그인</h1>
					
					<label for="username"><b>EMAIL</b></label>
					<input type="email" placeholder="이메일" name="username" id="username" required>
					
					<label for="password"><b>암호</b></label>
					<input type="password" placeholder="암호" name="password" id="password" required>
					
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
					<button type="submit" class="btn">로그인</button>
					<button type="button" class="btn" onclick="moveRegistry()">가입</button>
					<c:if test="${error=='Y'}">
					<div class="form-control">
						<p class="warn-p">이메일 이나 암호가 일치하지 않습니다.</p>
					</div>
					</c:if>
				</form>
			</div>
		</div>
		
		<!-- registry layout -->
	 	<div id="user_info_layer" class="layer_wrap dview_layer dn"></div>
		
		<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
		<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>  
		<script src="${pageContext.request.contextPath}/resources/js/network.js"></script> 
		<script src="${pageContext.request.contextPath}/resources/js/uicommon.js"></script>
		<script type="text/javascript">
			function moveRegistry(){
				 $.get('${pageContext.request.contextPath}/login/registry' , data=>{
					 $('#user_info_layer').removeClass('dn');
					 $('#user_info_layer').empty().append(data);
				 });
			
			}
			
			/* popup layer close */
			function mo_close_layer(){
				$('#user_info_layer').empty().addClass('dn');
			}
		
		</script>
		
	</body>
</html>