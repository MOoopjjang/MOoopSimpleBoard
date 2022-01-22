
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
%>

<style type="text/css">
	.total-div{
		display:flex;
		
		align-items: center;
		
		margin-left:auto;
		margin-right:20px;
	}
	
	.table-body-tr:hover{
		background-color: #CCE7E7;
	}
	
</style>

<%
	Integer nStart = (pageInfo.getNumber()/10)*10;
%>

<%--	<c:set var="sdf" value="<%=sdf %>" scope = "page"/>--%>
	<c:set var="startindex" value="<%= nStart %>" scope="page" />

<div class="container">
	<div>
		<nav class="navbar navbar-expand-sm navbar-dark" style="border-top:1px solid #adb5bd;">
			<form class="form-inline" style="vertical-align: middle;margin-left:10px;margin-bottom:00px;">
				<select class="form-control" id="searchCategory" name="searchCategory" style="width:100px;margin-right:10px;">
					<option value="nick" <c:if test="${searchInfo != null && searchInfo.getCategory() == 'nick' }"> selected </c:if>>닉네임</option>
					<option value="title" <c:if test="${searchInfo!=null && searchInfo.getCategory() == 'title' }"> selected </c:if> >Title</option>
				</select>
				<input class="form-control mr-sm-2" id="searchText" name="searchText" type="text" placeholder="Search" <c:if test="${searchInfo!=null && searchInfo.getText()!=null }">value=${searchInfo.getText() } </c:if> >
				<img src="${pageContext.request.contextPath}/resources/img/ico_search_s.svg" style="margin-left:10px;cursor:pointer;" onclick="searchFunc()" />
			</form>

			<div class="total-div" style="display:inline-block;margin-left:auto;margin-right:20px;">
				<span class="m-font-16 m-font-bold">Total : ${pageInfo.getTotalElements()}</span>
			</div>
		</nav>
	</div>
	
	<div>
		<table class="msb-com-table">
			<thead>
				<tr>
					<th style="width:10%;">No</th>
					<th style="width:40%;">제목</th>
					<th style="width:20%;">닉네임</th>
					<th style="width:20%;">등록일</th>
					<th style="width:10%;">조회수</th>
				</tr>
			</thead>
			
			<tbody>
				<c:choose>
					<c:when test="${boardItems.size() > 0}">
						<c:forEach var="item" items="${boardItems}" varStatus="status">
						<tr class="table-body-tr">
							<td>${status.count+(pageInfo.getNumber()*10)}</td>
							<td><a href="javascript:void(0);" onclick="moveDetailView('${item.getBoardIdx()}','${ item.getSecYn()}','${item.getEmail()}','${role}');" style="color: black;text-decoration:none;">${item.getTitle()}
								</a>
								<c:if test="${item.uploadFileInfos.size() > 0}">
									<c:choose>
										<c:when test='${item.uploadFileInfos[0].oname.endsWith(".png") == true}'>
											<img src="/resources/img/png_s.svg" />
										</c:when>
										<c:when test='${item.uploadFileInfos[0].oname.endsWith(".jpg") == true || item.uploadFileInfos[0].oname.endsWith(".jpeg") == true}'>
											<img src="/resources/img/jpg_s.svg" />
										</c:when>
										<c:when test='${item.uploadFileInfos[0].oname.endsWith(".pdf") == true}'>
											<img src="/resources/img/pdf_s.svg" />
										</c:when>
										<c:when test='${item.uploadFileInfos[0].oname.endsWith(".doc") == true || item.uploadFileInfos[0].oname.endsWith(".docx") == true}'>
											<img src="/resources/img/doc_s.svg" />
										</c:when>
										<c:when test='${item.uploadFileInfos[0].oname.endsWith(".ppt") == true || item.uploadFileInfos[0].oname.endsWith(".pptx") == true}'>
											<img src="/resources/img/ppt_s.svg" />
										</c:when>
										<c:when test='${item.uploadFileInfos[0].oname.endsWith(".xls") == true || item.uploadFileInfos[0].oname.endsWith(".xlsx") == true}'>
											<img src="/resources/img/xls_s.svg" />
										</c:when>
										<c:otherwise>
											<img src="/resources/img/unknown_s.svg" />
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${item.getSecYn() == 'Y'}">
									<img src="/resources/img/item_locked.png" style="width:20px;height:20px;" />
								</c:if>
							</td>
							<td>${item.getNick()}</td>
							<td>${item.dtCreate}</td>
							<td>${item.getHit()}</td>
						</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td style="width: 100%;" colspan="4">
								<%@include file="board_empty.jsp" %>
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		
		
		<c:if test="${pageInfo!=null && pageInfo.getNumberOfElements() > 0}">
		<div style="display:inline-block;width:70%;">
			<nav aria-label="Page navigation" style="display:inline-block;width:70%;">
				<ul class="pagination">
				<c:if test="${startindex >= 10 }">
					<li class="page-item">
						<a class="page-link" href="/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
							&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${startindex - 10}&size=10">
							<img alt="" src="/resources/img/icon_left_arrow.png" style="width:20px;height:20px;">
						</a>
					</li>
				</c:if>
				<c:if test="${pageInfo.getNumber() != 0}">
					<li class="page-item">
						<a class="page-link" href="/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
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
						<a class="page-link" href="/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
						&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${i}&size=10" >
						${i+1}</a>
					</li>
				</c:forEach>
				
				<c:if test="${pageInfo.getNumber() != pageInfo.getTotalPages()-1}">
					<li class="page-item">
						<a class="page-link" href="/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
							&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${pageInfo.getNumber()+1}&size=10">
						다음</a>
					</li>
				</c:if>
				
				<c:if test="${(pageInfo.getTotalPages() - startindex) > 10 }">
					<li class="page-item">
						<a class="page-link" href="/board/main?category=<c:if test="${searchInfo!=null && searchInfo.getCategory()!=null }">${searchInfo.getCategory()}</c:if>
							&text=<c:if test="${searchInfo!=null && searchInfo.getText()!=null }">${searchInfo.getText()}</c:if>&page=${startindex + 10}&size=10">
							<img alt="" src="/resources/img/icon_right_arrow.png" style="width:20px;height:20px;">
						</a>
					</li>
				</c:if>
				</ul>
			</nav>
		</div>
		</c:if>
		<c:if test="${role != 'GUEST' }">
			<div style="float:right;">
				<a class="btn btn-primary" role="button" href="javascript:void(0);" onclick="writeBoard()">
					<i class="fa fa-save fa-2">&nbsp;</i>글쓰기
				</a>
			</div>
		</c:if>
	</div>
	<%@ include file="board_password.jsp" %>
	<div id="user_info_layer" class="layer_wrap dn"></div>
	
</div>

<%--<c:remove var="sdf" scope="page"/>--%>
<c:remove var="startindex" scope="page" />

<script type="text/javascript">
    let email = '';
	let idx = '';
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
			$("#pwdModal").modal('toggle');
		}else{
			email = '';
			$.get('/board/dview?idx='+_idx , data=>{
				$('#user_info_layer').removeClass('dn');
				$('#user_info_layer').empty().append(data);
			});
		}
	}
	
	
	/* 검색 기능 */
	function searchFunc(){
		let $searchCategory = document.getElementById('searchCategory');
		let $searchText = document.getElementById('searchText');

		let selCatetory = '';
		for(let i = 0 ; i < $searchCategory.options.length ; i++){
			if($searchCategory.options[i].selected === true){
				selCatetory = $searchCategory.options[i].value;
				break;
			}
		}
		let searchText = $searchText.value;
		let searchData = {
				'category':selCatetory,
				'text':searchText,
				'page':0,
				'size':10
				
		};
		
		startLoading('검색중...');

		let $form = document.createElement('form');
		$form.setAttribute('method','GET');
		$form.setAttribute('action' ,'/board/main?' )
		
		for(let key in searchData){
			let hiddenField = document.createElement('input');
			hiddenField.setAttribute('type' , 'hidden');
			hiddenField.setAttribute('name',key);
			hiddenField.setAttribute('value',searchData[key])
			$form.appendChild(hiddenField)
		}
		
		document.body.appendChild($form);
		$form.submit();
	}
	
	
	/* 비밀글 인증 */
	function authenticationProc(_email){
		let $password = document.getElementById('password');
		let authData = {
			"email":_email,
			"password":$password.value
		};
		
		startLoading('인증중...');
		$.ajax({
			url : '/login/api/check/auth',
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
					$.get('/board/dview?idx='+idx , data=>{
						$('#user_info_layer').removeClass('dn');
						 $('#user_info_layer').empty().append(data);
					});
				}else{
					alert('암호가 일치하지 않습니다.!');
				}
				document.getElementById('password').value = '';
			},
			 error : function( response ){
				stopLoading();
				alert('에러가 발생하였습니다.');
				document.getElementById('password').value = '';
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