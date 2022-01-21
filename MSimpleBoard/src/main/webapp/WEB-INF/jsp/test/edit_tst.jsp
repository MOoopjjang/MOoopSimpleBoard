<%--
  Created by IntelliJ IDEA.
  User: gimcheol-u
  Date: 03/02/2021
  Time: 12:04 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel='stylesheet' type="text/css" href="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resources/asset/popper/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/asset/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/asset/bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>

    <link href="${pageContext.request.contextPath}/resources/asset/summernote-0.8.18-dist/summernote.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/asset/summernote-0.8.18-dist/summernote.js"></script>
    <title>summernote test</title>
</head>
<body>

<%--    <div style="display: inline-block;width:700px;height:600px;border: 1px solid darkgray;">--%>
<%--        <div id="summernote"></div>--%>
<%--    </div>--%>
<%--    --%>

    <div>
        <textarea  class="col-12 form-control" name="inputContent" id="inputContent"></textarea>
        <button class="btn btn-primary" onclick="registry()">등록</button>
    </div>

<%--    --%>
<%--    <form method="post">--%>
<%--        <textarea id="summernote" name="editdata"></textarea>--%>
<%--    </form>--%>

    <script>
        $(document).ready(function(){
            $('#inputContent').summernote({
                placeholder:'자유롭게 작성해주세요!..불쾌값을 주는글은 삼가해주세요',
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
        })

        function registry(){
            let inputContent = document.getElementById('inputContent');
            alert(inputContent.value);
        }


    </script>
</body>
</html>
