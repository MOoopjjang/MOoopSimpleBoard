<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
	<head>
		<meta charset="UTF-8" >
		<title>MSimpleBoard</title>
		<link rel='stylesheet' type="text/css" href="/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
		<link rel='stylesheet' type="text/css" href="/resources/css/admin/common.css?ver=1">
		<link rel='stylesheet' type="text/css" href="/resources/css/layer.css">
	</head>
	
	<body>
		<div class="container">
			<tiles:insertAttribute name="header"/>
			<div style="display:flex;height:100%;">
				<tiles:insertAttribute name="left" />
				<div id="bodycontent">
					<tiles:insertAttribute name="content" />
				</div>
			</div>
		</div>
<!-- 
	 		--------------------------------------------------------------
	 				자바스크립트 영역
		    --------------------------------------------------------------
-->
		<script src="/resources/asset/jquery-3.4.1.min.js"></script>
		<script src="/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
		<script src="/resources/asset/jquery-loading/dist/jquery.loading.js"></script>
		<script src="/resources/js/common.js"></script>
		<script src="/resources/js/network.js"></script>
		<script src="/resources/js/mstringutil.js"></script>
		<script src="/resources/js/msbobject.js"></script>
		<script src="/resources/js/uicommon.js"></script>
		<script type="text/javascript">
		</script>
	</body>
</html>