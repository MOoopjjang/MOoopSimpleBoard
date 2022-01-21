<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.SearchResponseVO" %>
<%@ page import="com.mooop.board.domain.web.UserItemVO" %>
<%@ page import="com.mooop.board.domain.web.AdmViewInfoVO" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%
	AdmViewResponse<Page<UserItemVO>> ar = (AdmViewResponse<Page<UserItemVO>>)request.getAttribute("xdata");
	Page<UserItemVO> pageInfo = ar.getData();
	SearchResponseVO searchInfo = ar.getSearch();
	ViewResultVO resultVo = ar.getResult();
	AdmViewInfoVO viewInfo = ar.getAdmViewInfo();
	List<UserItemVO> items = pageInfo.getContent();
	
	System.out.println("list ==>"+viewInfo.getCallUrl());
%>

<c:set var="pageInfo" value="<%= pageInfo  %>" scope="page" />
<c:set var="searchInfo" value="<%= searchInfo  %>" scope="page" />
<c:set var="items" value="<%= items  %>" scope="page" />
<c:set var="resultVo" value="<%= resultVo  %>" scope="page" />
<c:set var="viewInfo" value="<%= viewInfo %>" scope="page" />


<div class="content_wrap">
    <div class="container">
		<c:if test="${viewInfo.getMode()=='ALL' }">
		<div>
			<nav class="navbar navbar-expand-sm navbar-dark" style="border-top:1px solid #adb5bd;">
				<form class="form-inline" style="vertical-align: middle;margin-bottom: 0px;">
					<select class="form-control" id="searchCategory">
						<option value="role" <c:if test="${searchInfo.getCategory()!=null && searchInfo.getCategory()=='role'}">selected</c:if>>role</option>
						<option value="name"  <c:if test="${searchInfo.getCategory()!=null && searchInfo.getCategory()=='name'}">selected</c:if>>이름</option>
						<option value="status"  <c:if test="${searchInfo.getCategory()!=null && searchInfo.getCategory()=='status'}">selected</c:if>>상태</option>
						<option value="enable"  <c:if test="${searchInfo.getCategory()!=null && searchInfo.getCategory()=='enable'}">selected</c:if>>활성화</option>
					</select>
					
					<input class="form-control" style="margin-left:4px;" type="text" id="searchText" name="searchText" placeholder="검색" />
<%--					<button class="btn btn-primary" style="margin-left:10px;" type="button" id="btnSearch" onclick="search('${viewInfo.getMode()}')" style="">검색</button>--%>
					<img src="/resources/img/ico_search_s.svg" style="margin-left:10px;cursor:pointer;" onclick="search('${viewInfo.getMode()}')" />
				</form>
			</nav>
		</div>
		</c:if>
		<div>
			<table class="msb-com-table">
				<thead class="thead">
					<th>#</th>
					<th>이름</th>
					<th>Email</th>
					<th>Nick</th>
					<th>Role</th>
					<th>마지막로그인</th>
					<th>상태</th>
					<th>-</th>
				</thead>
				
				<tbody>
					<c:forEach var="item" items="${items}" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td><a href="javascript:void(0);" onclick="moveDViewPage('${item.getEmail()}','${viewInfo.getCallUrl()}')" style="color:black;">${item.getUserName()}</a></td>
						<td>${item.getEmail()}</td>
						<td>${item.getNickName()}</td>
						<td>${item.getRole()}</td>
						<td>${item.getLastLogin()}</td>
						<td>${item.getStatus()}</td>
						<td>
							<c:if test="${item.getEnable()=='N'}">
								<img alt="" src="/resources/img/icon_lock2.png" style="display:inline-block;width:20px;height:20px;">
							</c:if>
						</td>
					</tr>
					</c:forEach>
					
				</tbody>
			</table>
			
			<c:if test="${pageInfo !=null && pageInfo.getNumberOfElements() > 10}">
			<div>
				<nav class="Page navigation" style="display: inline-block;">
					<ul class="pagination">
						<c:if test="${pageInfo.getNumber() != 0}">
						<li class="page-item">
							<a class="page-link" href="/admin/user/list?category=<c:if test="${pageInfo.getCategory()!=null }">${pageInfo.getCategory()}</c:if>
							&text=<c:if test="${pageInfo.getText()!=null }">${pageInfo.getText()}</c:if>&page=${pageInfo.getNumber()-1}&size=10">
							Prev</a>
						</li>
						</c:if>
						
						<c:forEach var="i" begin="0" end="${pageInfo.getTotalPages() - 1}" step="1">
						<li class="page-item <c:if test="${pageInfo.getNumber() == i}">active</c:if>">
							<a class="page-link" href="/admin/user/list?category=<c:if test="${pageInfo.getCategory()!=null }">${pageInfo.getCategory()}</c:if>
							&text=<c:if test="${pageInfo.getText()!=null }">${pageInfo.getText()}</c:if>&page=${i}&size=10">
							${i+1}</a>
						</li>
						</c:forEach>
						
						<c:if test="${pageInfo.getNumber() != (pageInfo.getTotalPages() - 1)}">
						<li class="page-item">
						<a class="page-link" href="/admin/user/list?category=<c:if test="${pageInfo.getCategory()!=null }">${pageInfo.getCategory()}</c:if>
							&text=<c:if test="${pageInfo.getText()!=null }">${pageInfo.getText()}</c:if>&page=${pageInfo.getNumber()+1}&size=10">
						Next</a>
						</li>
						</c:if>
					</ul>
				</nav>
			</div>
			</c:if>
		</div>
	</div>
</div>


<c:remove var="pageInfo" scope="page"/>
<c:remove var="searchInfo" scope="page"/>
<c:remove var="items" scope="page"/>
<c:remove var="resultVo" scope="page"/>
<c:remove var="viewInfo" scope="page"/>

<script type="text/javascript">

	/* 검색 */
	function search(_mode){
		
		startLoading('검색중...');

		let $searchCategory = document.getElementById('searchCategory');
		let $searchText = document.getElementById('searchText');
		let selCategory = null;
		for(let i = 0 ; i < $searchCategory.options.length ; i++){
			if($searchCategory.options[i].selected === true){
				selCategory = $searchCategory.options[i].value;
				break;
			}
		}
		let searchData = {
			'category':selCategory,
			'text':$searchText.value,
			'page':0,
			'size':10,
			'mode':_mode
		};

		let $searchForm = document.createElement('form');
		$searchForm.setAttribute('method' , 'GET');
		$searchForm.setAttribute('action' , '/admin/user/list?')
	 	
	 	for(let k in searchData){
			let hiddenField = document.createElement('input');
	 		hiddenField.setAttribute('type' , 'hidden');
	 		hiddenField.setAttribute('name' ,k );
	 		hiddenField.setAttribute('value' , searchData[k])
	 		$searchForm.appendChild(hiddenField);
	 	}
		
		document.body.appendChild($searchForm);
		$searchForm.submit();
	}
	
	/* 상세보기 이동 */
	function moveDViewPage(_email , _callUrl){
		let ss = _callUrl.split('&');
		let callUri = '/admin/user/list';
		movePage('/admin/user/dview?email='+_email+'&callUri='+callUri+'&'+ss[ss.length - 1]);
	}

</script>
