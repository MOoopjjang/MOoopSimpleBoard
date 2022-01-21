<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.AdmViewInfoVO" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	AdmViewResponse av = (AdmViewResponse)request.getAttribute("xdata");
	AdmViewInfoVO viewInfo = av.getAdmViewInfo();
	
	System.out.println("left ::"+viewInfo.getCallUrl());
%>

<c:set var="callUrl" value="<%= viewInfo.getCallUrl().trim() %>" scope="page" />

<link rel='stylesheet' type="text/css" href="/resources/css/admin/left.css?ver=3">
<div class="sidenav">
	<div class="top_div">
		<div>
			<img alt="" src="/resources/img/icon_admin.png" style="width:44px;height:44px;">
		</div>
	</div>
	 
	 <div active class="dropdown-btn" style="margin-top:20px;">
	 	<img alt="" src="/resources/img/icon_member.png">
	 	<p>회원관리</p>
	 	<img alt="" src="/resources/img/icon_down_arrow.png" >
	 </div>
	 <div class="dropdown-container" 
	 	<c:choose>
	 		<c:when test="${callUrl=='/admin/user/list?category=&text=&page=0&size=10&mode=ALL' || callUrl=='/admin/user/list?category=role&text=GUEST&page=0&size=10&mode=GUEST' || callUrl=='/admin/user/list?category=enable&text=N&page=0&size=10&mode=BLOCK'}">style="display:block;"</c:when>
	 		<c:otherwise>style="display:none;"</c:otherwise>
	 	</c:choose>
	 >
	 	<a href="/admin/user/list?category=&text=&page=0&size=10&mode=ALL" <c:if test="${callUrl=='/admin/user/list?category=&text=&page=0&size=10&mode=ALL'}">style="color:darkgray;"</c:if>>회원목록보기</a>
	 	<a href="/admin/user/list?category=role&text=GUEST&page=0&size=10&mode=GUEST" <c:if test="${callUrl=='/admin/user/list?category=role&text=GUEST&page=0&size=10&mode=GUEST'}">style="color:darkgray;"</c:if>>GUEST목록보기</a>
	 	<a href="/admin/user/list?category=enable&text=N&page=0&size=10&mode=BLOCK" <c:if test="${callUrl=='/admin/user/list?category=enable&text=N&page=0&size=10&mode=BLOCK'}">style="color:darkgray;"</c:if>>Block회원보기</a>
	 </div>
	 
	  <div class="dropdown-btn">
	 	<img alt="" src="/resources/img/icon_board.png">
	 	<p>계시판관리</p>
	 	<img alt="" src="/resources/img/icon_down_arrow.png" >
	 </div>
	 <div class="dropdown-container"
	 	<c:choose>
	 		<c:when test="${callUrl=='/admin/board/most/hit'||callUrl=='/admin/board/most/uploader'}">style="display:block;"</c:when>
	 		<c:otherwise>style="display:none;"</c:otherwise>
	 	</c:choose>
	 >
 		<a href="/admin/board/most/hit" <c:if test="${callUrl=='/admin/board/most/hit'}">style="color:darkgray;"</c:if>>최다hit글 목록</a>
	 	<a href="/admin/board/most/uploader" <c:if test="${callUrl=='/admin/board/most/uploader'}">style="color:darkgray;"</c:if>>최다글 uploader 목록</a>
	 </div>
	 
	 <div class="dropdown-btn">
	 	<img alt="" src="/resources/img/icon_event.png">
	 	<p>공지사항관리</p>
	 	<img alt="" src="/resources/img/icon_down_arrow.png" >
	 </div>
	 <div class="dropdown-container"
	 	<c:choose>
	 		<c:when test="${callUrl=='/admin/event/list'||callUrl=='/admin/event/register'}">style="display:block;"</c:when>
	 		<c:otherwise>style="display:none;"</c:otherwise>
	 	</c:choose>
	 >
	 	<a href="/admin/event/list?category=&text=&page=0&size=10" <c:if test="${callUrl=='/admin/event/list'}">style="color:darkgray;"</c:if>>목록보기</a>
	 	<a href="/admin/event/register"  <c:if test="${callUrl=='/admin/event/register'}">style="color:darkgray;"</c:if>>공지사항등록</a>
	 </div>
	 
	 
	 <div class="dropdown-btn">
	 	<img alt="" src="/resources/img/icon_setting.png">
	 	<p>기타</p>
	 	<img alt="" src="/resources/img/icon_down_arrow.png" >
	 </div>
	 <div class="dropdown-container"
		  <c:choose>
	 		<c:when test="${callUrl=='/admin/setting/authority'||callUrl=='/admin/setting/paging'}">style="display:block;"</c:when>
	 		<c:otherwise>style="display:none;"</c:otherwise>
	 	</c:choose>
	 >
	 	<a href="/admin/setting/authority" <c:if test="${callUrl=='/admin/setting/authority'}">style="color:darkgray;"</c:if>>권한관리</a>
	 	<a href="/admin/setting/paging" <c:if test="${callUrl=='/admin/setting/paging'}">style="color:darkgray;"</c:if>>페이징설정</a>
	 </div>
	 
</div>

<c:remove var="viewInfo" scope="page" />


<script type="text/javascript">

	(function init(){
		let dropdownmenu = document.getElementsByClassName('dropdown-btn');
		for(let i = 0 ; i < dropdownmenu.length ; i++){
			dropdownmenu[i].addEventListener('click' , function(){
				this.classList.toggle('active');
				let dropdownContent = this.nextElementSibling;
				if(dropdownContent.style.display === 'block'){
					dropdownContent.style.display = 'none';
				}else{
					dropdownContent.style.display = 'block';
				}
			});
		}
		
	})();

</script>

