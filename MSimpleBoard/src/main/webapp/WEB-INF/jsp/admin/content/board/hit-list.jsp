<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.BoardItemVO" %>
<%@ page import="org.springframework.data.domain.Page" %>


<%
	AdmViewResponse<Page<BoardItemVO>> admViewResponse = (AdmViewResponse<Page<BoardItemVO>>)request.getAttribute("xdata");
	Page<BoardItemVO> pageInfo = admViewResponse.getData();
	List<BoardItemVO> items = pageInfo.getContent();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<c:set var="pageInfo" value="<%= pageInfo %>" scope="page" />
<c:set var="items" value="<%= items %>" scope="page" />
<c:set var="sdf" value="<%=sdf %>" scope="page"/>

<div class="content_wrap">
	<div class="container">
		<nav class="navbar navbar-expand-sm navbar-dark" >
			<h2>최다 조회글</h2>
		</nav>
		<div style="margin-top:10px;">
			<table class="table table-striped" style="border: 1px solid #ddd;">
				<thead>
					<th style="width:10%;">#</th>
					<th style="width:40%;">Title</th>
					<th style="width:20%;">닉네임</th>
					<th style="width:20%;">등록일</th>
					<th style="width:10%;">조회수</th>
				</thead>
				
				<tbody>
					<c:forEach var="item" items="${items}" varStatus="status">
						<tr>
							<td><a href="javascript:void(0);" onclick="moveDViewPage('${item.getBoardIdx()}')" style="color:black;">${status.count}</a></td>
							<td><a href="javascript:void(0);" onclick="moveDViewPage('${item.getBoardIdx()}')" style="color:black;">${item.getTitle()}</a></td>
							<td><a href="javascript:void(0);" onclick="moveDViewPage('${item.getBoardIdx()}')" style="color:black;">${item.getNick()}</a></td>
							<td><a href="javascript:void(0);" onclick="moveDViewPage('${item.getBoardIdx()}')" style="color:black;">${sdf.format(item.getDtCreate())}</a></td>
							<td><a href="javascript:void(0);" onclick="moveDViewPage('${item.getBoardIdx()}')" style="color:black;">${item.getHit()}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div>
		</div>
	</div>
</div>

<c:remove var="pageInfo" scope="page"/>
<c:remove var="items" scope="page"/>
<c:remove var="sdf" scope="page"/>

<script type="text/javascript">

	/* 상세페이지 이동 */
	function moveDViewPage(_idx){
		movePage('${pageContext.request.contextPath}/admin/board/dview?idx='+_idx);
	}

</script>