<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mooop.board.domain.ViewResponse" %>
<%@ page import="com.mooop.board.domain.web.UserItemVO" %>
<%@ page import="com.mooop.board.domain.web.ViewInfoVO" %>
<%@ page import="com.mooop.board.domain.web.AuthenticationResponseVO" %>

<html lang="ko">
	<head>
		<%@ include file="../common/common.jsp" %>
		<link rel='stylesheet' type="text/css" href="/resources/css/registry.css?ver=1">
		<style>
			.flex-div{
				display: flex;
				width: 100%;

				flex-direction: row;
			}

			.l-div{width: 60%;}
			.r-div{width:40%;}
		</style>
	</head>
	<%
		ViewResponse<UserItemVO> cr = (ViewResponse<UserItemVO>)request.getAttribute("xdata");

		ViewInfoVO viewInfo = cr.getViewInfo();
		AuthenticationResponseVO authInfo = cr.getAuthentication();
		UserItemVO metaData = cr.getData();

		String mode = viewInfo.getMode();
		String role = null;
		String email = null;
		if(authInfo != null && authInfo.getRole() != null){
			role = authInfo.getRole();
			email = authInfo.getEmail();
		}
	%>

	<c:set var="mode" value="<%= mode %>" scope="page" />
	<c:set var="metaData" value="<%= metaData %>" scope="page" />
	<c:set var="role" value="<%= role %>" scope="page" />
	<c:set var="email" value="<%= email %>" scope="page"/>
	<body>
	<div class="layer_dim_wrap">
		<form class="form-container layer_shadow" method="post">
			<div class="close_layer">
				<a href="javascript:void(0);" onclick="mo_close_layer()" ><img alt="" src="${pageContext.request.contextPath}/resources/img/btn_layer_close.png"></a>
			</div>

			<h3 id="subtitle">
				<c:choose>
					<c:when test="${mode=='register'}">사용자등록</c:when>
					<c:otherwise>상세보기</c:otherwise>
				</c:choose>
			</h3>

			<div class="flex-div">
				<div class="l-div">
					<div>
						<input type="text" placeholder="이름" name="name" id="name" required="required"
						<c:if test="${mode != 'register'}"> disabled="true"</c:if>
						<c:if test="${metaData!=null &&  metaData.getUserName()!=null}">
							   value=${metaData.getUserName()}
							   </c:if>
						>
					</div>

					<div style="margin-top:10px;">
						<input type="text" placeholder="닉네임" name="nick" id="nick" required="required"
						<c:if test="${mode!='register'}"> disabled="true" </c:if>
						<c:if test="${metaData!=null && metaData.getNickName()!=null}"> value=${metaData.getNickName()}</c:if>
						>

						<c:if test="${mode=='register'}">
							<button type="button" class="btn-sm" onclick="checkNick()">등록조회</button>
						</c:if>
					</div>

					<div style="margin-top:10px;">
						<input type="email" placeholder="이메일" name="email" id="email" required="required"
							   <c:if test="${mode!='register'}">disabled="true"</c:if>
							   <c:if test="${metaData != null && metaData.getEmail() != null}">value=${metaData.getEmail()}</c:if>
						>
						<c:if test="${mode=='register'}">
							<button type="button" class="btn-sm" onclick="checkEmail()">등록조회</button>
						</c:if>
					</div>
				</div>
				<div class="r-div">
					<img src="/ImageView?email=${email}" width="100%" height="188px;" />

					<div class="custom-file" >
						<input type="file" name="files" class="custom-file-input" id="upload_file"
						<c:if test="${mode!='register'}"> disabled="disabled"</c:if>
						>
						<label class="custom-file-label" for="customFile">사진</label>
					</div>
				</div>
			</div>

			<c:if test="${mode=='register'}">
				<div style="margin-top:10px;">
					<input type="password" placeholder="비밀번호" name="fpassword" id="fpassword" required="required" >
					<input type="password" placeholder="확인" name="cpassword" id="cpassword" required="required" >
				</div>
			</c:if>

			<div style="margin-top:10px;">
				<input type="text" placeholder="주소" name="address" id="address" required="required"
				<c:if test="${mode!='register'}"> disabled="true" </c:if>
					   <c:if test="${metaData != null && metaData.getAddr() != null}">value=${metaData.getAddr()}</c:if>
				>
			</div>

			<div style="margin-top:20px;">
				<label for="desc"><b>소개말</b></label>
				<textarea id="desc" class="form-control col-md-12" rows="5" style="text-align:left;"
						<c:if test="${mode!='register'}"> disabled="true" </c:if>><c:if test="${metaData != null && metaData.getDesc() != null}">${metaData.getDesc().trim()}</c:if></textarea>
			</div>

			<div style="margin-top:10px;">
				<button id="btn_action" type="button" onclick="regiFunc(this.innerHTML);" class="btn-md">
					<c:choose>
						<c:when test="${mode=='register'}">등록</c:when>
						<c:otherwise>편집</c:otherwise>
					</c:choose>
				</button>
				<c:if test="${mode=='board' && role!=null && role!='ADMIN'}">
					<button type="button"  class="btn-md" onclick="unregister()">탈퇴</button>
				</c:if>
			</div>
		</form>

	</div>
	<!-- 탈퇴 질의 modal  -->
	<%@ include file="../board/board_password.jsp" %>

		<c:remove var="mode" scope="page"/>
		<c:remove var="metaData" scope="page"/>
		<c:remove var="role" scope="page" />
		<c:remove var="email" scope="page"/>

		<!--
                --------------------------------------------------------------
                        자바스크립트 영역
               --------------------------------------------------------------
         -->
		<script type="text/javascript">

			var $email = document.getElementById('email');
			var $nick = document.getElementById('nick');
			var $name = document.getElementById('name');
			var $fpassword = document.getElementById('fpassword');
			var $cpassword = document.getElementById('cpassword');
			var $address = document.getElementById('address');
			var $desc = document.getElementById('desc');
			var $disable = document.getElementById('disable');
			var $subtitle = document.getElementById('subtitle');
			var $btn_action = document.getElementById('btn_action');
			var $roleCategory = document.getElementById('roleCategory');
			var $upload_file = document.getElementById('upload_file');

			var fullUrl = '';

			$(document).ready(()=>{
				$(".custom-file-input").on("change", function() {
					let fileName = $(this).val().split("\\").pop();
					let ss = fileName.split(".");
					let ext = ss[ss.length-1];
					if( ext !== 'jpg' && ext!== 'png' && ext!=='jpeg' && ext!=='gif'){
						alert('이미지파일만 첨부가능합니다.');
						return;
					}
					$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
				});

			});

			function testFunc(){
				alert($disable.checked);
			}

			/* 필수 입력값 체크 */
			function checkValid(){
				if(chkValidValue(trim($email.value)) === false
						|| chkValidValue(trim($name.value)) === false
						|| chkValidValue(trim($nick.value)) === false
						|| chkValidValue(trim($fpassword.value)) === false
						|| chkValidValue(trim($cpassword.value)) === false
				){
					return false;
				}
				return true;
			}


			/* MODE에 따라 동작을 실행한다. */
			function regiFunc(v){
				let act = trim(v);
				console.log(act);
				if(act === '등록'){
					register();
				}else if(act === '편집'){
					edit();
				}else{
					save();
				}
			}

			/* 등록 */
			function register(){

				if(checkValid() === false){
					alert('누락된 항목이 있습니다.!!');
					return;
				}


				if(trim($fpassword.value) !== trim($cpassword.value)){
					alert('비밀번호가 일치하지 않습니다.!!');
					return;
				}

				let formData = new FormData();
				formData.append("email" , trim($email.value));
				formData.append("password" , trim($fpassword.value));
				formData.append("userName" , trim($name.value));
				formData.append("nickName" , trim($nick.value));
				formData.append("addr" , trim($address.value));
				formData.append("desc" , trim($desc.value));
				formData.append("enable" , $disable === null?'Y':$disable.checked);
				formData.append("status" , "ACCESSION");
				formData.append("role" , "GUEST");
				if($('#upload_file')[0].files[0] !== null && $('#upload_file')[0].files[0] !== undefined && $('#upload_file')[0].files[0] !== 'undefined'){
					formData.append("files" , $('#upload_file')[0].files[0]);
				}


				startLoading('등록중...');
				$.ajax({
					url : '${pageContext.request.contextPath}/login/api/register',
					headers: {
						"X-CSRF-TOKEN": "${_csrf.token}"
					},
					method : "post",
					enctype: "multipart/form-data",
					processData: false,
					data : formData,
					contentType : false,
					cache: false,
					success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('가입되었습니다.!');
							movePage('${pageContext.request.contextPath}/login');
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

			/* 편집 */
			function edit(){
				$address.disabled = false;
				$desc.disabled = false;
				$btn_action.innerHTML = '저장';
				$upload_file.disabled = false;
			}

			/* 편집 --> 저장 */
			function save(){
				let selectCategory = "<%= role %>";
				if($roleCategory !== null){
					for(let i = 0 ; i < $roleCategory.options.length ; i++){
						if($roleCategory.options[i].selected === true){
							selectCategory = $roleCategory.options[i].value;
							break;
						}
					}
				}

				if(selectCategory === null || selectCategory === ''){
					selectCategory = 'USER';
				}

				let formData = new FormData();
				formData.append("email" , trim($email.value));
				formData.append("userName" , trim($name.value));
				formData.append("nickName" , trim($nick.value));
				formData.append("addr" , trim($address.value));
				formData.append("desc" , trim($desc.value));
				formData.append("enable" , $disable === null?'Y':$disable.checked);
				formData.append("status" , "ACCESSION");
				formData.append("role" , selectCategory);
				if($('#upload_file')[0].files[0] !== null && $('#upload_file')[0].files[0] !== undefined && $('#upload_file')[0].files[0] !== 'undefined'){
					formData.append("files" , $('#upload_file')[0].files[0]);
				}

				startLoading('저장중...');
				$.ajax({
					url : '${pageContext.request.contextPath}/login/api/save',
					headers: {
						"X-CSRF-TOKEN": "${_csrf.token}"
					},
					method : "post",
					enctype: "multipart/form-data",
					processData: false,
					data : formData,
					contentType : false,
					success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('사용자 정보가 변경되었습니다.');
							movePage('/board/');
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


			/* 탈퇴 popup */
			function unregister(){
				$("#pwdModal").modal('toggle');
			}


			/* 등록된 email인지 체크 */
			function checkEmail(){
				let $email = document.getElementById('email');
				if( $email.value.length === 0 || trim($email.value).length === 0){
					alert('email이 정확하지 않습니다.\n 다시 입력해 주세요.');
					return;
				}

				startLoading('중복 체크중...');
				$.ajax({
					url : '${pageContext.request.contextPath}/login/api/check/email/'+trim($email.value)+"/",
					method : "get",
					success : function(response){
						stopLoading();
						let msg = '';
						if(response.result === 'OK'){
							msg = response.reason === 'exist'?'이미 등록된 email 입니다.':'등록 가능한 email입니다.';
						}else{
							msg = '오류가 발생하였습니다.!';
						}
						alert(msg);
					},
					error : function( response ){
						stopLoading();
						alert('falied');
					}

				});

			}

			/* 등록된 Nick인지 체크 */
			function checkNick(){
				let $nick = document.getElementById('nick');
				if( $nick.value.length === 0 || trim($nick.value).length === 0){
					alert('nick이 정확하지 않습니다.\n 다시 입력해 주세요.');
					return;
				}

				startLoading('중복 체크중...');
				$.ajax({
					url : '${pageContext.request.contextPath}/login/api/check/nick/'+trim($nick.value),
					method : "get",
					success : function(response){
						stopLoading();
						let msg = '';
						if(response.result === 'OK'){
							msg = response.reason === 'exist'?'이미 등록된 nick 입니다.':'등록 가능한 nick입니다.';
						}else{
							msg = '오류가 발생하였습니다.!';
						}
						alert(msg);
					},
					error : function( response ){
						stopLoading();
						alert('falied');
					}

				});
			}

			/* 탈퇴 진행 */
			function authenticationProc(_email){
				startLoading('탈퇴 진행중...');
				authentication(_email)
						.then(processingUnregister)
						.then(moveLogout)
						.catch(exceptionFunction);

				stopLoading();
			}


			/* 1. 탈퇴 인증 */
			function authentication(_email){
				return new Promise(function(resolve , reject){
					let $password = document.getElementById('password');

					console.log('email : '+_email);
					let authData = {
						"email":_email,
						"password":$password.value
					};


					$.ajax({
						url : '/login/api/check/auth',
						headers: {
							"X-CSRF-TOKEN": "${_csrf.token}"
						},
						method : "post",
						data : JSON.stringify(authData),
						contentType : 'application/json',
						success : function(response){
							if(response.result === 'OK' && response.reason === 'equal'){
								resolve(_email);
							}else{
								reject('암호가 일치하지 않습니다.!');
							}
						},
						error : function( response ){
							reject('에러가 발생하였습니다.');
						}

					});
				})
			}

			/* 2. 탈퇴 진행 */
			function processingUnregister(_email){
				return new Promise(function(resolve , reject){
					$.ajax({
						url:'/login/api/unregister',
						headers: {
							"X-CSRF-TOKEN": "${_csrf.token}"
						},
						method:'post',
						dataType:'json',
						data:_email,
						contentType:'text/plain',
						success:function(response){
							if(response.result === 'OK'){
								alert(response.result);
								resolve('탈퇴하였습니다.');
							}else{
								alert(response.result);
								reject('탈퇴가 실패하였습니다.');
							}

						},
						error:function(error){
							reject('내부에러가 발생하였습니다.');
						}
					});

				})

			}

			/* 3. 화면 이동 */
			function moveLogout(_msg){
				movePage('/logout');
			}

			function exceptionFunction(){
				document.getElementById('password').value = '';
				alert('오류가 발생하였습니다.');
			}


		</script>
	</body>

</html>




	


