<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.mooop.board.domain.AdmViewResponse" %>
<%@ page import="com.mooop.board.domain.web.ViewResultVO" %>
<%@ page import="com.mooop.board.domain.web.EventItemVO" %>
<%@ page import="com.mooop.board.domain.web.AdmViewInfoVO" %>

<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/summernote-0.8.18-dist/summernote.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/css/registry.css">

<%
	AdmViewResponse<EventItemVO> av = (AdmViewResponse<EventItemVO>)request.getAttribute("xdata");
	ViewResultVO  viewResult = av.getResult();
	EventItemVO item = av.getData();
	AdmViewInfoVO admViewInfo = av.getAdmViewInfo();
	
	Long idx = -1L;
	if(item !=null && item.getIdx() != null){
		idx = item.getIdx();
	}
	
%>

<c:set var="viewInfo" value="<%= admViewInfo %>" scope="page" />
<c:set var="item" value="<%= item %>" scope="page" />
<c:set var="idx" value="<%= idx %>" scope="page" />
<c:set var="mode" value="<%= admViewInfo.getMode() %>" scope="session" />

<div class="content_wrap" style="width:900px;" >
	<div class="container" style="height:100%;">
		<div class="form-popup" style="height:100%;">
			<form class="form-container">
				<h3>
					<c:choose>
						<c:when test="${viewInfo.getMode()=='register'}">공지사항등록</c:when>
						<c:otherwise>상세보기</c:otherwise>
					</c:choose>
				</h3>
				  <div class='col-sm-10' style="margin-top:20px;">
				    <label style="font-weight: bold;">시작일</label>
				    
		            <div class="form-group">
		                <div class='input-group date' id='datetimepicker1'>
		                    <input id="eventStart" type='text' class="form-control" data-format="YYYY-MM-dd hh:mm" 
		                     <c:if test="${viewInfo.getMode()=='dview'}">
		                     disabled = 'true'
		                     </c:if>
		                     <c:if test="${item!=null}"> 
		                    <fmt:formatDate var="fmtDate" pattern = "YYYY-MM-dd hh:mm" value = "${item.getDtStart()}" />
		                    value="${fmtDate}"
		                    </c:if> />
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                </div>
		            </div>
		         </div>
		         
				  <div class='col-sm-10'>
				    <label style="font-weight: bold;">종료일</label>
		            <div class="form-group">
		                <div class='input-group date' id='datetimepicker2'>
		                    <input id="eventEnd" type='text' class="form-control" data-format="YYYY-MM-dd hh:mm" 
		                    <c:if test="${viewInfo.getMode()=='dview'}">
		                     disabled = 'true'
		                     </c:if>
		                    <c:if test="${item!=null}">
		                    <fmt:formatDate var="fmtDate2" pattern="YYYY-MM-dd hh:mm" value="${item.getDtEnd()}" />
		                    value="${fmtDate2}"
		                    </c:if> />
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                </div>
		            </div>
		         </div>
		         
		         <div class="form-check" style="padding-left:40px;" >
		         	<input type="checkbox" class="form-check-input" id="disable" 
		         	<c:if test="${viewInfo.getMode()=='dview'}">
	                    disabled = 'true'
                     </c:if>
		         	 />
		         	<label class="form-check-label"> 비활성화</label>
		         </div>
		         
		         <div class='col-sm-12' style="margin-top:10px;">
		         	<input type="text" name="title" id="title" placeholder="이벤트제목"
		         	<c:if test="${viewInfo.getMode()=='dview'}">
                     disabled = 'true'
                     </c:if>
		         	<c:if test="${item!=null && item.getTitle()!=null }">
		         		value=${item.getTitle().replaceAll(" ", "&nbsp;")}
		         	</c:if>
		         	 />
		         </div>
		         
		         <div class='col-sm-12' style="margin-top:10px;">
		         	<textarea id="content" name="content">
		         	<c:if test="${item!=null && item.getContent()!=null }">${item.getContent()}</c:if>
					</textarea>
		         </div>
		         
		         <div class="col-sm-12" style="margin-top:4px;">
		         	<button id="btnAction" type="button" class="btn-xd" onclick="actionFunc(this.innerHTML,'${idx}')">
		         	<c:choose>
		         		<c:when test="${viewInfo.getMode()=='register'}">등록</c:when>
		         		<c:otherwise>편집</c:otherwise>
		         	</c:choose>
		         	</button>
		         </div>
		         
		         <c:if test="${viewInfo.getMode()=='dview'}">
		         <div class="col-sm-12" style="margin-top:4px;">
		         	<button type="button" class="btn-xd" onclick="itemDelete('${idx}')">삭제</button>
		         </div>
		         </c:if>
		         
			</form>
		</div>
	</div>
</div>

<c:remove var="viewInfo" scope="page" />
<c:remove var="item" scope="page" />
<c:remove var="idx" scope="page" />

<script src="${pageContext.request.contextPath}/resources/asset/popper/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.ko.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/moment.min.js"></script><%-- 날짜 ,시간 관련 library --%>
<script src="${pageContext.request.contextPath}/resources/js/mstringutil.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/summernote-0.8.18-dist/summernote.js"></script>
<script type="text/javascript">

	let $eventStart = document.getElementById('eventStart');
	let $eventEnd = document.getElementById('eventEnd');
	let $disable = document.getElementById('disable');
	let $title = document.getElementById('title');
	let $content = document.getElementById('content');
	let $btnAction = document.getElementById('btnAction');



	function actionFunc(_btnText , _idx){
		let trimText = trim(_btnText);
		if(trimText === '등록'){
			register();
		}else if(trimText === '편집'){
			edit();
		}else{
			update(_idx);
		}
	};
	
	
	/* 등록 */
	function register(){
		let fmt = 'YYYY-MM-DD HH:mm';
		let strEnabled = ($disable.checked === true)?'N':'Y';
		let registerData = {
			'idx':0,
			'title':trim($title.value),
			'content':trim($content.value),
			'start':moment($eventStart.value ,fmt),
			'end':moment($eventEnd.value , fmt),
			'enable':strEnabled
		}
		
		startLoading('등록중...');
		$.ajax({
			url : '${pageContext.request.contextPath}/admin/api/event/register',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
			method : "post",
			data : JSON.stringify(registerData),
			contentType : 'application/json',
			success : function(response){
				stopLoading();
				if(response.result === 'OK'){
					alert('공지사항이 등록되었습니다.');
					movePage('${pageContext.request.contextPath}/admin/event/list?category=&text=&page=0&size=10');
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
		$eventStart.disabled = false;
		$eventEnd.disabled = false;
		$disable.disabled = false;
		$title.disabled = false;
		$content.disabled = false;
		
		
		$btnAction.innerHTML = '저장';
	}
	
	
	function update(_idx){
		let fmt = 'YYYY-MM-DD HH:mm';
		let strEnabled = ($disable.checked === true)?'N':'Y';
		let updateData = {
			'idx':_idx,
			'title':trim($title.value),
			'content':trim($content.value),
			'start':moment($eventStart.value ,fmt),
			'end':moment($eventEnd.value , fmt),
			'enable':strEnabled
		}
		
		startLoading('업데이트중...');
		$.ajax({
			url : '${pageContext.request.contextPath}/admin/api/event/update',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
			method : "post",
			data : JSON.stringify(updateData),
			contentType : 'application/json',
			success : function(response){
				stopLoading();
				if(response.result === 'OK'){
					alert('공지사항이 변경되었습니다.');
					movePage('${pageContext.request.contextPath}/admin/event/list?category=&text=&page=0&size=10');
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
	
	
	function itemDelete(_idx){
		let deleteData = {
				'idx':_idx
			}
			
		startLoading('삭제 진행중...');
		$.ajax({
			url : '${pageContext.request.contextPath}/admin/api/event/delete',
			headers: {
	        	"X-CSRF-TOKEN": "${_csrf.token}"
	        },
			method : "post",
			data : JSON.stringify(deleteData),
			contentType : 'application/json',
			success : function(response){
				stopLoading();
				if(response.result === 'OK'){
					alert('공지사항이 삭제되었습니다.');
					movePage('${pageContext.request.contextPath}/admin/event/list?category=&text=&page=0&size=10');
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
	

	(function init(){
	 	$("#datetimepicker1").datetimepicker({
	        language:  'ko',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });


	 	$("#datetimepicker2").datetimepicker({
	        language:  'ko',
	        weekStart: 1,
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0
	    });

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
	})();


</script>