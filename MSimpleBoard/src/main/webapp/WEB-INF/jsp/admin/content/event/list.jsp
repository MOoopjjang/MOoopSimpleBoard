<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.SearchResponseVO" %>
<%@ page import="com.mooop.board.domain.web.EventItemVO" %>
<%@ page import="org.springframework.data.domain.Page" %>


<%
	AdmViewResponse<Page<EventItemVO>> av = (AdmViewResponse<Page<EventItemVO>>)request.getAttribute("xdata");
	SearchResponseVO searchInfo = av.getSearch();
	Page<EventItemVO> pageInfo = av.getData();
	List<EventItemVO> items = pageInfo.getContent();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<c:set var="searchInfo" value="<%=searchInfo %>" scope="page" />
<c:set var="pageInfo" value="<%=pageInfo %>" scope="page" />
<c:set var="items" value="<%=items %>" scope="page" />
<c:set var="sdf" value="<%=sdf %>" scope="page" />


<div class="content_wrap">
	<div class="container">
		<div> 
			<nav class="navbar navbar-expand-sm navbar-dark" style="background-color: #084B8A;">
				<form class="form-inline" style="vertical-align: middle;margin-bottom: 0;">
					<select id="searchCategory" class="form-control">
						<option value="title" <c:if test="${searchInfo.getCategory() !=null && searchInfo.getCategory()=='title'}">selected</c:if>>타이틀</option>
						<option value="dtstart" <c:if test="${searchInfo.getCategory() !=null && searchInfo.getCategory()=='dtstart'}">selected</c:if>>시작일</option>
						<option value="dtend" <c:if test="${searchInfo.getCategory() !=null && searchInfo.getCategory()=='dtend'}">selected</c:if>>종료일</option>
						<option value="enable" <c:if test="${searchInfo.getCategory() !=null && searchInfo.getCategory()=='enable'}">selected</c:if>>상태</option>
					</select>
					<input  type="text" class="form-control" style="margin-left:10px;" name="searchText" id="searchText" placeholder="Search" />
					<button type="button" class="btn btn-primary" onclick="searchFunc()"  style="margin-left:4px;">검색</button>
				</form>
			</nav>
		</div> 
		
		<div>
			<table class="table table-striped" style="border: 1px solid #ddd;">
				<thead>
					<th style="width:10%;">#</th>
					<th style="width:40%;">제목</th>
					<th style="width:20%;">시작일</th>
					<th style="width:20%;">종료일</th>
					<th style="width:10%;">-</th>
				</thead>
				
				<tbody>
					<c:forEach var="item"  	items="${items}" varStatus="status">
						<tr>
							<td style="width:10%;"><a href="javascript:void(0);" onclick="moveDViewPage('${item.getIdx()}')" style="color:black;">${status.count}</a></td>
							<td style="width:40%;"><a href="javascript:void(0);" onclick="moveDViewPage('${item.getIdx()}')" style="color:black;">${item.getTitle()}</a></td>
							<td style="width:20%;"><a href="javascript:void(0);" onclick="moveDViewPage('${item.getIdx()}')" style="color:black;">${sdf.format(item.getDtStart())}</a></td>
							<td style="width:20%;"><a href="javascript:void(0);" onclick="moveDViewPage('${item.getIdx()}')" style="color:black;">${sdf.format(item.getDtEnd())}</a></td>
							<td style="width:10%;"><a href="#" style="color:black;">
								<c:if test="${item.getEnable()=='N'}">
									<img alt="" src="${pageContext.request.contextPath}/resources/img/icon_lock2.png" style="display: inline-block;width:20px;height:20px;">
								</c:if>
							
							</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<c:if test="${pageInfo.getTotalElements()!=0}">
		<div>
			<nav class="Page navigation">
				<ul class="pagination">
					<c:if test="${pageInfo.getNumber() != 0 }">
					<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/admin/event/list?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
					&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${pageInfo.getNumber()-1}&size=10" >prev</a></li>
					</c:if>
					
					<c:forEach var="i" begin="0" end="${pageInfo.getTotalPages()-1}" step="1">
					<li class="page-item <c:if test="${pageInfo.getNumber() == i}">active</c:if>">
						<a class="page-link"
						href="${pageContext.request.contextPath}/admin/event/list?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
						&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${i}&size=10">${i+1 }
						</a>
					</li>
					</c:forEach>
					
					<c:if test="${pageInfo.getNumber() != ( pageInfo.getTotalPages()-1) }">
					<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/admin/event/list?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
					&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${pageInfo.getNumber()+1}&size=10">next</a></li>
					</c:if>
				</ul>
			</nav>
		</div>
		</c:if>
		
	</div>
</div>

<c:remove var="searchInfo" scope="page" />
<c:remove var="pageInfo" scope="page" />
<c:remove var="items" scope="page" />
<c:remove var="sdf" scope="page" />

<script type="text/javascript">
	var $searchCategory = document.getElementById('searchCategory');
	var $searchText = document.getElementById('searchText');
	
	
	/* 검색 */
	function searchFunc(){
		startLoading('검색중...');
		
		var searchOptions = null;
		for(var i = 0 ; i < $searchCategory.options.length ; i++){
			if($searchCategory.options[i].selected === true){
				searchOptions = $searchCategory.options[i].value;
				break;
			}
		}
		
		var searchData = {
			'category':searchOptions,
			'text':$searchText.value,
			'page':0,
			'size':10
		}
		
		var $searchForm = document.createElement('form');
		$searchForm.setAttribute('action' , '${pageContext.request.contextPath}/admin/event/list');
		$searchForm.setAttribute('method' , 'GET');
		
		for(var k in searchData){
			var hiddenField = document.createElement('input');
			hiddenField.setAttribute('type' , 'hidden');
			hiddenField.setAttribute('name' , k);
			hiddenField.setAttribute('value' , searchData[k]);
			$searchForm.appendChild(hiddenField);
		}
		
		document.body.appendChild($searchForm);
		$searchForm.submit();
	}
	
	/* 상세페이지이동 */
	function moveDViewPage(_idx){
		movePage('${pageContext.request.contextPath}/admin/event/dview?idx='+_idx);
	}
</script>

