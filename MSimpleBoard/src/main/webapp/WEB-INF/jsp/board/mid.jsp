<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.ViewResponse" %>
<%@ page import="org.springframework.data.domain.Page" %>
<%@ page import="com.mooop.board.domain.web.BoardItemVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<style type="text/css">
	.total-div{
		display:flex;
		
		align-items: center;
		
		margin-left:auto;
		margin-right:20px;
	}
	
	.total-div p{
		display:inline-block;
		color:white;
		
		margin:0;
		padding:0;
		
		font-famliy : arial, helvetica, sans-serif;
	 	font-style : italic; 
		font-size : 14px;
		line-height : 1.6;
		font-weight : bold;
		
	}
	
	.table-body-tr:hover{
		background-color: #CCE7E7;
	}
	
</style>

<%
	ViewResponse<Page<BoardItemVO>> cr = (ViewResponse<Page<BoardItemVO>>)request.getAttribute("xdata");
	Page<BoardItemVO> pageInfo = (Page<BoardItemVO>)cr.getData();
	
	Integer nStart = (pageInfo.getNumber()/10)*10;
%>

	<c:set var="sdf" value="<%=sdf %>" scope = "page"/>
	<c:set var="startindex" value="<%= nStart %>" scope="page" />

<div class="container">
	<div>
		
		<nav class="navbar navbar-expand-sm navbar-dark" style="background-color: #084B8A;">
			<form class="form-inline" style="vertical-align: middle;margin-left:10px;margin-bottom:00px;">
				<select class="form-control" id="searchCategory" name="searchCategory" style="width:100px;margin-right:10px;">
					<option value="nick" <c:if test="${searchInfo != null && searchInfo.getCategory() == 'nick' }"> selected </c:if>>닉네임</option>
					<option value="title" <c:if test="${searchInfo!=null && searchInfo.getCategory() == 'title' }"> selected </c:if> >Title</option>
					
				</select>
				<input class="form-control mr-sm-2" id="searchText" name="searchText" type="text" placeholder="Search" <c:if test="${searchInfo!=null && searchInfo.getText()!=null }">value=${searchInfo.getText() } </c:if> >
				<button class="btn btn-primary" type="button" onclick="searchFunc()" >검색</button>
			</form>
			
			<div class="total-div" style="display:inline-block;margin-left:auto;margin-right:20px;">
				<p>Total : </p>
				<p>${pageInfo.getTotalElements()}</p>
			</div>
		</nav>
	</div>
	
	<div>
		<table class="table table-striped" style="border:1px solid #ddd;">
			<thead class="thead">
				<tr>
					<th style="width:10%;">#</th>
					<th style="width:40%;">Title</th>
					<th style="width:20%;">닉네임</th>
					<th style="width:20%;">등록일</th>
					<th style="width:10%;">조회수</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="item" items="${boardItems}" varStatus="status">
				<tr class="table-body-tr">
					<td><a href="javascript:void(0);" onclick="moveDetailView('${item.getBoardIdx()}','${ item.getSecYn()}','${item.getEmail()}','${role}');" style="color: black;text-decoration:none;">${status.count+(pageInfo.getNumber()*10)}</a></td>
					<td><a href="javascript:void(0);" onclick="moveDetailView('${item.getBoardIdx()}','${ item.getSecYn()}','${item.getEmail()}','${role}');" style="color: black;text-decoration:none;">${item.getTitle()} 
							<c:if test="${item.getSecYn() == 'Y'}">
								 <img src="${pageContext.request.contextPath}/resources/img/item_locked.png" style="width:20px;height:20px;float:right;margin-right:10px" /> 
							 </c:if>
						</a>
					</td>
					<td><a href="javascript:void(0);" onclick="moveDetailView('${item.getBoardIdx()}','${ item.getSecYn()}','${item.getEmail()}','${role}');" style="color: black;text-decoration:none;">${item.getNick()}</a></td>
					<td><a href="javascript:void(0);" onclick="moveDetailView('${item.getBoardIdx()}','${ item.getSecYn()}','${item.getEmail()}','${role}');" style="color: black;text-decoration:none;">${sdf.format(item.getDtCreate())}</a></td>
					<td><a href="javascript:void(0);" onclick="moveDetailView('${item.getBoardIdx()}','${ item.getSecYn()}','${item.getEmail()}','${role}');" style="color: black;text-decoration:none;">${item.getHit()}</a></td>
				
				</tr>
				</c:forEach>
				
			</tbody>
		</table>
		
		
		<c:if test="${pageInfo!=null && pageInfo.getNumberOfElements() > 0}">
		<div>
			<nav aria-label="Page navigation" style="display:inline-block;width:70%;">
				<ul class="pagination">
				<c:if test="${startindex >= 10 }">
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
							&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${startindex - 10}&size=10">
							<img alt="" src="${pageContext.request.contextPath}/resources/img/icon_left_arrow.png" style="width:20px;height:20px;">
						</a>
					</li>
				</c:if>
				<c:if test="${pageInfo.getNumber() != 0}">
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
						&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${pageInfo.getNumber()-1}&size=10">
						이전</a>
					</li>
				</c:if>
				
				<c:set var="ending" 
						value="<%= (pageInfo.getTotalPages() - ((pageInfo.getNumber()/10)*10)) > 10?
								(pageInfo.getNumber()/10)*10+9:
									((pageInfo.getNumber()/10)*10) +((pageInfo.getTotalPages() - ((pageInfo.getNumber()/10)*10))-1) %>" />
				<c:forEach var="i" begin="${startindex}" end="${ending}" step="1">
					<li class="page-item <c:if test="${pageInfo.getNumber() == i}"> active</c:if>">
						<a class="page-link" href="${pageContext.request.contextPath}/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
						&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${i}&size=10" >
						${i+1}</a>
					</li>
				</c:forEach>
				
				<c:if test="${pageInfo.getNumber() != pageInfo.getTotalPages()-1}">
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
							&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${pageInfo.getNumber()+1}&size=10">
						다음</a>
					</li>
				</c:if>
				
				<c:if test="${(pageInfo.getTotalPages() - startindex) > 10 }">
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
							&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${startindex + 10}&size=10">
							<img alt="" src="${pageContext.request.contextPath}/resources/img/icon_right_arrow.png" style="width:20px;height:20px;">
						</a>
					</li>
				</c:if>
				</ul>
			</nav>
			
			<c:if test="${role != 'GUEST' }">
			<div style="float:right;">
				<a class="btn btn-primary" role="button" href="javascript:void(0);" onclick="writeBoard()">
					<i class="fa fa-save fa-2">&nbsp;</i>글쓰기
				</a>
			</div>
			</c:if>
			
		</div>
		</c:if>
	
	</div>
	
	<!-- 암호입력 modal  -->
	<div class="modal fade" id="pwdModal">
		<div class="modal-dialog">
			<div class="modal-content">
			
				<!-- header -->
				<div class="modal-header">
					<h4 class="modal-title">암호를 입력하세요</h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				
				<!-- body -->
				<div class="modal-body">
					<input class="form-control" type="password" placeholder="암호" name="password" id="password">
				</div>
				
				<!-- footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="authentication()">확인</button>
				</div>
				
			</div>
		</div>
	
	</div>
	
	<div id="user_info_layer" class="layer_wrap dn"></div>
	
</div>

<c:remove var="sdf" scope="page"/>
<c:remove var="startindex" scope="page" />


<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/mstringutil.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/network.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/uicommon.js"></script>
<script type="text/javascript">

    var email = '';
    var idx = '';

    /* 상세화면 이동 */
	function moveDetailView(_idx , _secYN , _email , _role ){
    	//접근 권한 체크
    	if(_role === 'GUEST'){
    		alert('글을 볼수있는 권한이 없습니다.');
    		return;
    	}
    	
		if(_secYN === 'Y'){
			email = _email;
			idx = _idx;
			var $pwdModal = document.getElementById('pwdModal');
			$("#pwdModal").modal('toggle');
		}else{
			email = '';
			$.get('${pageContext.request.contextPath}/board/dview?idx='+_idx , data=>{
				$('#user_info_layer').removeClass('dn');
				$('#user_info_layer').empty().append(data);
			});
		}
	}
	
	
	/* 검색 기능 */
	function searchFunc(){
		var $searchCategory = document.getElementById('searchCategory');
		var $searchText = document.getElementById('searchText');
		
		var selCatetory = '';
		for(var i = 0 ; i < $searchCategory.options.length ; i++){
			if($searchCategory.options[i].selected === true){
				selCatetory = $searchCategory.options[i].value;
				break;
			}
		}
		var searchText = $searchText.value;
		var searchData = {
				'category':selCatetory,
				'text':searchText,
				'page':0,
				'size':10
				
		};
		
		startLoading('검색중...');
		
		var $form = document.createElement('form');
		$form.setAttribute('method','GET');
		$form.setAttribute('action' ,'${pageContext.request.contextPath}/board/main?' )
		
		for(var key in searchData){
			var hiddenField = document.createElement('input');
			hiddenField.setAttribute('type' , 'hidden');
			hiddenField.setAttribute('name',key);
			hiddenField.setAttribute('value',searchData[key])
			$form.appendChild(hiddenField)
		}
		
		document.body.appendChild($form);
		$form.submit();
	}
	
	
	/* 비밀글 인증 */
	function authentication(){
		var $password = document.getElementById('password');
		
		
		var authData = {
			"email":email,
			"password":$password.value
		};
		
		startLoading('인증중...');
		$.ajax({
	        url : '${pageContext.request.contextPath}/login/api/check/auth',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
	        method : "post",
	        data : JSON.stringify(authData),
	        contentType : 'application/json',
	        success : function(response){
				stopLoading();
				if(response.result === 'OK' && response.reason === 'equal'){
					alert('인증되었습니다.!');
					$.get('${pageContext.request.contextPath}/board/dview?idx='+idx , data=>{
						$('#user_info_layer').removeClass('dn');
						 $('#user_info_layer').empty().append(data);
					});
				}else{
					alert('암호가 일치하지 않습니다.!');
				}
			},
	        error : function( response ){
				stopLoading();
				alert('에러가 발생하였습니다.');
			}
	      
	    });
		
	}
	
	
	function writeBoard(){
	 $.get('${pageContext.request.contextPath}/board/nview' , data=>{
		 $('#user_info_layer').removeClass('dn');
		 $('#user_info_layer').empty().append(data);
	 });
	}
	
	/* popup layer close */
	function mo_close_layer(){
		$('#user_info_layer').empty().addClass('dn');
	}
	

</script>