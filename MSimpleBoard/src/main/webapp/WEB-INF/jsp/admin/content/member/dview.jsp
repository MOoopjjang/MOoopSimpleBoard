<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.UserItemVO" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/registry.css?ver=1">

<%
	AdmViewResponse<UserItemVO> ar = (AdmViewResponse<UserItemVO>)request.getAttribute("xdata");
	UserItemVO userItem = ar.getData();
	ViewResultVO resultVo = ar.getResult();
%>

<c:set var="userItem" value="<%= userItem %>" scope="page" />


<div class="content_wrap" style="width:600px;">

	<div class="container" style="height:100%;">
		<div class="form-popup" style="height:100%;">
			<form class="form-container">
				<h3>USER 상세정보</h3>
				<div style="margin-top:10px;">
					<input type="text" name="name" id="name" placeholder="이름" disabled="true" value="${userItem.getUserName()}" />
				</div>
				<div style="margin-top:10px;">
					<input type="text" name="nick" id="nick" placeholder="닉네임" disabled="true" value="${userItem.getNickName()}" />
				</div>
				<div style="margin-top:10px;">
					<input type="email" name="email" id="email" placeholder="이메일" disabled="true" value="${userItem.getEmail()}" />
				</div>
				<div style="margin-top:10px;">
					<input type="text" name="address" id="address" placeholder="주소" disabled="true" value="${userItem.getAddr()}" />
				</div>
				<div  style="margin-top:20px;width:200px;display:inline-block;">
					<select class="form-control" id="roleCategory">
						<option value="USER" <c:if test="${userItem.getRole()=='USER'}">selected</c:if>>USER</option>
						<option value="ADMIN" <c:if test="${userItem.getRole()=='ADMIN'}">selected</c:if>>ADMIN</option>
						<option value="GUEST" <c:if test="${userItem.getRole()=='GUEST'}">selected</c:if>>GUEST</option>
						<option value="OPERATOR" <c:if test="${userItem.getRole()=='OPERATOR'}">selected</c:if>>OPERATOR</option>
					</select>
				</div>
				
				<div class="form-check" style="margin-top:10px;margin-left:10px;display:inline-block;">
					<input type="checkbox" class="form-check-input" id="disable" <c:if test="${userItem.getEnable()=='N'}"> checked </c:if>>
					<label class="form-check-label" for="disable" >비활성화</label>
				</div>
				
				<div style="margin-top:20px;">
					<label>소개말</label>
					<textarea id="desc" class="form-control col-md-12" rows="6" style="text-align: left;" disabled="true">${userItem.getDesc()}</textarea>
					
				</div>
				
				<div style="margin-top:10px;">
					<button class="btn-xd" type="button" onclick="dviewSave()">저장</button>
				</div>
			</form>
		</div>
	</div>
</div>


<c:remove var="userItem" scope="page"/>

<script type="text/javascript">
	let $email = document.getElementById('email');
	let $nick = document.getElementById('nick');
	let $name = document.getElementById('name');
	let $address = document.getElementById('address');
	let $desc = document.getElementById('desc');
	let $disable = document.getElementById('disable');
	let $roleCategory = document.getElementById('roleCategory');
	

	function dviewSave(){
		
		let selectRole = '';
		for(let i = 0 ;i < $roleCategory.options.length ; i++) {
			if($roleCategory.options[i].selected === true){
				selectRole = $roleCategory.options[i].value;
				break;
			}
		}
		
		
		let updateData = {
				"email":$email.value,
				"password":'',
				"userName":$name.value,
				"nickName":$nick.value,
				"addr":$address.value,
				"desc":$desc.value,
				"enable":$disable.checked === true?'N':'Y',
				"status":"",		
				"role":selectRole
		};
		
		startLoading('업데이트중...');
		$.ajax({
			url : '${pageContext.request.contextPath}/admin/api/user/update',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
			method : "post",
			data : JSON.stringify(updateData),
			contentType : 'application/json',
			success : function(response){
				stopLoading();
				if(response.result === 'OK'){
					alert('사용자 정보가 변경되었습니다.');
					movePage('${pageContext.request.contextPath}/admin/user/list?category=&text=&page=0&size=10&mode=ALL');
				}else{
					alert('오류가 발생하였습니다.!');
				}
			},
			error : function( response ){
				stopLoading();
				alert('falied');
			}
		});
		
	}
	
	
</script>


