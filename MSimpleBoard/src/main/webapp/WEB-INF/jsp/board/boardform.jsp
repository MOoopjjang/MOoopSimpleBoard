<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.mooop.board.domain.ViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewInfoVO" %>
<%@ page import="com.mooop.board.domain.web.AuthenticationResponseVO" %>
<%@ page import="com.mooop.board.domain.web.BoardItemVO" %>
<%@ page import="com.mooop.board.domain.web.UploadFileInfoVO" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel='stylesheet' type="text/css" href="/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
<link href="/resources/asset/summernote-0.8.18-dist/summernote.css" rel="stylesheet">
<link rel='stylesheet' type="text/css" href="/resources/css/registry.css?ver=1">

<style>
	p{
		margin:0;
		padding:0;
	}
	
	.form-container{
		width: 800px;
		padding: 10px;
		background-color: white;
	}

	.modal {
		background: rgba(0, 0, 0, 0.5);
	}
	.modal-backdrop {
		display: none;
	}
	
</style>

	
<%
		ViewResponse<BoardItemVO> cr = (ViewResponse<BoardItemVO>)request.getAttribute("xdata");
		ViewInfoVO viewInfo = cr.getViewInfo();
		AuthenticationResponseVO authInfo = cr.getAuthentication();
		BoardItemVO item = cr.getData();
		String mode = viewInfo.getMode();
		Long idx = (mode.equals("register"))?-1:item.getBoardIdx();
		List<UploadFileInfoVO> uploadFiles = item.getUploadFileInfos();
	%>
	
	<c:set var="mode" value="<%= mode %>" scope="page" />
	<c:set var="authInfo" value="<%= authInfo %>" scope="page" />
	<c:set var="item" value="<%= item %>" scope="page" />
	<c:set var="idx" value="<%= idx %>" scope="page" />
	<c:set var="uploadFiles" value="<%= uploadFiles %>" scope="page" />

	<div class="layer_dim_wrap">
		<form id="boardForm" class="form-container layer_shadow" method="post" >
			<div class="close_layer">
				<a href="javascript:void(0);" onclick="mo_close_layer()" ><img alt="" src="${pageContext.request.contextPath}/resources/img/btn_layer_close.png"></a>
			</div>
			<h3>
				<c:choose>
					<c:when test="${mode=='register'}">새글작성</c:when>
					<c:otherwise>상세보기</c:otherwise>
				</c:choose>
			</h3>
			<div>
				<input type="email" placeholder="이메일" name="email" id="email" disabled="true" 
					<c:if test="${item!=null && item.getEmail()!=null}">value=${item.getEmail().replace(" ", "&nbsp;")}</c:if>
				/>
			</div>
			
			<div style="margin-top:10px;">
				<input type="text" placeholder="닉네임" name="nick" id="nick" disabled="true" 
					<c:if test="${item!=null && item.getNick()!=null}">value=${item.getNick().replace(" ", "&nbsp;")}</c:if>
				/>
			</div>
			
			<div style="margin-top:10px;">
				<input type="text" placeholder="타이틀" name="title" id="title" 
					<c:if test="${mode!='register'}">disabled="true"</c:if>
				 	<c:if test="${item!=null && item.getTitle()!=null}">value=${ item.getTitle().replace(" ", "&nbsp;")}</c:if>
				/>
			</div>
			
			<div class="form-check" style="margin-top:10px;">
				<input type="checkbox" class="from-check-input" name="secyn" id="secYN" <c:if test="${mode!='register'}">disabled="true"</c:if> />
				<label class="form-check-label" for="secYN">비밀글</label>
			</div>
			
			<div style="margin-top:10px;">
				<label for="content"><b>내용</b></label>
				<textarea id="content" name="content">
					<c:if test="${item != null && item.getContent() != null}">${item.getContent()}</c:if>
				</textarea>
			</div>
			
			<div style="margin-top:10px;color:#bf5eab"><span> * 파일첨부는 1개만 가능합니다.</span></div>
			<!-- 첨부파일 -->
			<div style="margin-top:10px;border:1px solid lightgray;;border-radius: 4px;">
				<c:choose>
					<c:when test="${mode == 'register'}">
						<div class="custom-file">
						    <input type="file" name="file1" class="custom-file-input" id="upload_file">
						    <label class="custom-file-label" for="customFile">파일 첨부</label>
						  </div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${uploadFiles.size() > 0 }">
								<c:forEach var="uf" items="${uploadFiles}">
									<div style="display:flex;align-items: center;">
									    <a style="width:80%;padding:10px;" href="${pageContext.request.contextPath}/board/api/download/${uf.getIdx()}"><span>${uf.getOname()}</span></a>
									    <button type="button" class="btn btn-warning" style="width:20%;margin-left:10px;" onclick="fileDelete('${uf.getIdx()}');"> 삭제</button>
										<input type="hidden" name="file_idx" value="${uf.getIdx()}" >
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="custom-file" >
								    <input type="file" name="file1" class="custom-file-input" id="upload_file" disabled="disabled">
								    <label class="custom-file-label" for="customFile">파일 첨부</label>
								 </div>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			</div>
			
		
			<div style="margin-top:10px;">
				<c:if test="${authInfo.getRole()=='USER'}">
					<c:choose>
						<c:when test="${mode=='register'}">
							<button id="btn_action" type="button"  class="btn-xd" style="background-color: #4CAF50;" onclick="dviewAction('${idx}')">등록</button>
						</c:when>
						<c:otherwise>
							<c:if test="${authInfo.getEmail() == item.getEmail()}">
								<button id="btn_action" type="button"  class="btn-xd" style="background-color: #4CAF50;" onclick="dviewAction('${idx}')">편집</button>
							</c:if>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
			
			<c:if test="${mode!='register' && (authInfo.getEmail() == item.getEmail() || authInfo.getRole()=='ADMIN') }">
				<div>
					<button type="button" class="btn-xd" onclick="textDelete('${idx}')">삭제</button>
				</div>
			</c:if>
		
		</form>
	</div>
		
		
		<!-- 변수 제거 -->
		<c:remove var="authInfo" scope="page"/>
		<c:remove var="item" scope="page"/>
		<c:remove var="idx" scope="page"/>
		<<c:remove var="uploadFiles" scope="page"/>
	
<!-- 
	 		--------------------------------------------------------------
	 				자바스크립트 영역
		    --------------------------------------------------------------
-->

		<script src="/resources/asset/popper/popper.min.js"></script>
		<script src="/resources/asset/jquery-3.4.1.min.js"></script>
		<script src="/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
		<script src="/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
		<script src="/resources/js/common.js"></script>
		<script src="/resources/js/mstringutil.js"></script>
		<script src="/resources/js/network.js"></script>
		<script src="/resources/js/uicommon.js"></script>
		<script src="/resources/asset/summernote-0.8.18-dist/summernote.js"></script>
		<script type="text/javascript">
			var $email = document.getElementById('email');
			var $nick = document.getElementById('nick');
			var $title = document.getElementById('title');
			var $content = document.getElementById('content');
			var $secYN = document.getElementById('secYN');
			var $btn_action = document.getElementById('btn_action');

			$(document).ready(function(){

				$('#content').summernote({
					placeholder:'자유롭게 작성해주세요!..불쾌감을 주는글은 삼가해주세요',
					tabsize: 2,
					height:300,
					minWidth:null,
					minHeight:null,
					focus: true,
					lang:'ko-KR',
					toolbar: [
						['style', ['style']],
						['font', ['bold', 'underline', 'clear']],
						['color', ['color']],
						['para', ['ul', 'ol', 'paragraph']],
						['table', ['table']],
						['insert', ['link', 'picture', 'video']],
						['view', ['fullscreen', 'codeview', 'help']]
					]
				});

				/* 등록모드가 아니면 편집기능 disable */
				if('${mode}' !== 'register'){
					$('#content').summernote('disable');
				}

				$(".custom-file-input").on("change", function() {
					let fileName = $(this).val().split("\\").pop();
					$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
				});
			})


		    /* 액션 ( 등록 | 편집 | 저장 ) */
			function dviewAction(_idx){
				let $btn_action = document.getElementById('btn_action');
				let actionText = trim(btn_action.innerHTML);
				if(actionText === '등록'){
					register();
				}else if(actionText === '편집'){
					edit();
				}else{
					save(_idx);
				}
				
			}
		    
		    /* 액션 ( 등록 ) */
		    function register(){
				let secYn = 'N';
		    	if($secYN !== null && $secYN.checked === true){
		    		secYn = 'Y';
		    	}
		    	
		    	const registryData = new FormData();
		    	registryData.append('email' ,$email.value );
		    	registryData.append('nick' ,$nick.value );
		    	registryData.append('title' ,$title.value );
		    	registryData.append('content' ,$content.value );
		    	registryData.append('secyn' ,secYn );
		    	registryData.append('file1' ,$('#upload_file')[0].files[0] );
		    	
		    	startLoading('등록중...');
		    	$.ajax({
			        url : '/board/api/register',
					headers: {
			        	"X-CSRF-TOKEN": "${_csrf.token}"
			        },
					method : "post",
					enctype: 'multipart/form-data',
					data : registryData,
					processData: false,
					contentType : false,
					cache: false,
					timeout: 600000,
					success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('등록되었습니다..!');
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
		    
		    /* 액션 ( 편집 ) */
		    function edit(){
		    	$title.disabled = false;
				$secYN.disabled = false;

				$('#content').summernote('enable');
				$('input[name="file1"]').prop('disabled' , false);
				
				$btn_action.innerHTML = '저장';
		    }
		    
		    /* 액션 ( 저장 ) */
		    function save(_idx){
		    	let secYn = 'N';
		    	if($secYN !== null && $secYN.checked === true){
		    		secYn = 'Y';
		    	}
		    	
		    	const saveData = new FormData();
		    	saveData.append('idx' , _idx);
		    	saveData.append('email' ,$email.value );
		    	saveData.append('nick' ,$nick.value );
		    	saveData.append('title' ,$title.value );
		    	saveData.append('content' ,$content.value );
		    	saveData.append('secyn' ,secYn );
		    	saveData.append('file1' ,$('#upload_file')[0].files[0] );
		    	
		    	
		    	startLoading('등록중...');
		    	$.ajax({
					url : '/board/api/save',
					headers: {
			        	"X-CSRF-TOKEN": "${_csrf.token}"
			        },
					method : "post",
					enctype: 'multipart/form-data',
					data : saveData,
					processData: false,
					contentType : false,
					cache: false,
					timeout: 600000,
					success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('등록되었습니다..!');
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
		
			
			/* 삭제 */
			function textDelete(_idx){
				
				// set : 첨부파일 정보
				var uploadFileInfos = [];
				if($('input[name="file_idx"]').val() !== undefined){
					var uploadFile = {"idx" : $('input[name="file_idx"]').val()}
					uploadFileInfos.push(uploadFile);
				}
				
				var removeData = {
		    			'idx':_idx,
		    			'uploadFileInfos' : uploadFileInfos
		    			
		    	}
				startLoading('삭제중...');
		    	$.ajax({
					url : '/board/api/remove',
					headers: {
			        	"X-CSRF-TOKEN": "${_csrf.token}"
			        },
					method : "post",
					data : JSON.stringify(removeData),
					contentType : 'application/json',
					success : function(response){
						stopLoading();
						if(response.result === 'OK'){
							alert('삭제되었습니다..!');
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
			
			/* 첨부파일 삭제 */
			function fileDelete(_idx){
				
				if(!confirm('첨부된파일을 삭제하시겠습니까?')){
					return;
				}
				
				startLoading('삭제중...');
				$.ajax({
					url : '/board/api/upload/remove/'+_idx,
					headers: {
						"X-CSRF-TOKEN": "${_csrf.token}"
					},
					method:"get",
					success:(response)=>{
						stopLoading();
						
						alert('삭제되었습니다.');
						window.location.reload(true);
					},
					error:(response)=>{
						stopLoading();
					}
				});
				
			}
			
		</script>

