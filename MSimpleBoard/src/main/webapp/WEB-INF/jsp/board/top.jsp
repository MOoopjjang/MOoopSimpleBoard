<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>

	.top-label-nick{
		font-size: 14px;
		margin-top:6px;
	}
	
	.top-label-date{
		width:140px;
		font-size: 14px;
	 	margin-left:2%; 
		margin-top:6px;
	}
	
	.top-btn-logout{
		position:relative;
		display:inline-block;
		width:80px;
		height:24px;
		margin-left:10px;
	/* 	background-color: #0066FF; */
		opacity:0.8;
		border: 1px solid #0066FF;
		border-radius: 10px;
	}
	
	.top-btn-logout:hover{
		opacity:1;
	}
	
	.top-p-logout{
		color:black;
		text-align: center;
		font-size: 12px;
		margin-top:2px;
	}
	
	
	.top-img-type-btn{
		position:relative;
		display:inline-block;
		width:32px;
		height:32px;
		margin-right:10px;
		float:right;
		opacity: 0.7;
	}
	
	.top-img-type-1{
		position:relative;
		display:inline-block;
		width:32px;
		height:32px;
		margin-left:2%;
	}
	
	.top-img-div:hover{
		opacity: 1;
	}
	
	.top-user-div{
		position:relative;
		display:inline-block;
		width:20%;
		height:32px;
		margin-left: auto;
		margin-right:10px;
	}
	
	
	
	
</style>

<div class="container" >
	<nav class="navbar navbar-expand-sm">
		<div class="top-img-type-1" >
			<img alt="유저" src="${pageContext.request.contextPath}/resources/img/icon_user.png" width="30px" height="30px">
		</div>
		<label class="top-label-nick"><i><u><c:out value="${nick} ( ${role} )" /> : </u></i></label> 
		<label class="top-label-date"><i><u>${dtaccess}</u></i></label>
		<div class="top-btn-logout"  style="cursor:pointer;" onclick="logoutFunc()" >
			<p class="top-p-logout">로그아웃</p>
		</div>
		
		<!-- 사용자 상세화면 -->
		<div class="top-user-div">
			<div class="top-img-type-btn" onclick="moveDetail()">
					<img alt="유저" src="${pageContext.request.contextPath}/resources/img/btn_user.png" width="30px" height="30px" style="cursor:pointer;">
			</div>
			
			<!-- 관리자 페이지 이동 -->
			<div class="top-img-type-btn" <c:if test="${ role ne 'ADMIN' }">style="display:none;"</c:if> onclick="moveAdmin()">
				<img alt="관리자" src="${pageContext.request.contextPath}/resources/img/btn_admin.png" width="30px" height="30px" style="cursor:pointer;">
			</div>
		</div>
		
	</nav>
</div>

<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/network.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/uicommon.js"></script>
<script type="text/javascript">
	/* 로그아웃 */
	function logoutFunc(){
		movePage('${pageContext.request.contextPath}/logout');
	}
	
	
	function moveDetail(){
		 $.get('${pageContext.request.contextPath}/login/dview' , data=>{
			 $('#user_info_layer').removeClass('dn');
			 $('#user_info_layer').empty().append(data);
		 });
		 
	}
	
	
	function moveAdmin(){
		movePage('${pageContext.request.contextPath}/admin/user/list?category=&text=&page=0&size=10&mode=ALL');
	}
</script>