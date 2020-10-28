<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mooop.board.domain.web.ViewPopupItemVO" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>


<%
/* 
	ViewPopupItemVO vpiv = ( ViewPopupItemVO )request.getAttribute("xdata");
	System.out.println(vpiv.toString());
	 */
%>
<%-- 
<c:set var="title" value="<%= vpiv.getTitle() %>" scope="page"/>
<c:set var="buttonTexts" value="<%= vpiv.getButtonTexts() %>" scope="page"/>

 --%>
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

    <div class="modal-content">
        <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Modal Header</h4>
        </div>
        <div class="modal-body">
        <p>Some text in the modal.</p>
        </div>
        <div class="modal-footer">
        <%-- 
        	<c:forEach var="item" items="${buttonTexts}">
        		<button type="button" class="btn btn-default" data-dismiss="modal"></button>
        	</c:forEach>
        	 --%>
        
        </div>
    </div>

    </div>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		console.log('####### popup_1.jsp #######')
	});
</script>
