<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="ko">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
		<meta http-equiv="X-UA-Compatible" content="IE=edge" >
		
		<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
	   <script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.js"></script>
	</head>
	
	<body>
		<button type="button" class="btn btn-primary" onclick="modal_popup_1()">modal_popup_1</button>
		
		<div id="m_content_1">
		</div>
		
		<script type="text/javascript">
			
			function modal_popup_1(){
				
				let title = 'simple popup';
				let bodyHTML = 'simple1';
				let buttons = "ok,cancel";
				
				
				$("#m_content_1").load("popup_template" 
						,{"title":title,"bodyHTML":bodyHTML,"buttonTexts":buttons}
						, function(){
							$("#myModal").modal('toggle');
				});
				 
				 
				 
			}
		</script>
	</body>
</html>