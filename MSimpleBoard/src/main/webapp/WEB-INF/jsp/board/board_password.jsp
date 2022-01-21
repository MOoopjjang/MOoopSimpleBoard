
<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" %>

<div id="pwdModal" class="modal fade">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <p class="modal-title">암호를 입력하세요</p>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <input class="form-control" type="password" placeholder="암호" name="password" id="password">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="authenticationProc('<%= authInfo.getEmail() %>')">확인</button>
            </div>

        </div>

    </div>

</div>
