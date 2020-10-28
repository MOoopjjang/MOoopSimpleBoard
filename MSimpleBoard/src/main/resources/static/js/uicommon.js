

/**
 * loading 화면 start
 * 
 * @param _msg
 * @returns
 */
function startLoading(_msg){
	$("body").loading({
        stoppable: false,
        message: _msg,
        theme: "dark"
      });
}


/**
 * loading 화면 중지
 * 
 * @returns
 */
function stopLoading(){
	$("body").loading("stop");
}

/**
 * 화면이동
 * 
 * @param _url
 * @returns
 */
function movePage(_url){
	startLoading('로딩중...')
	window.location.href=_url;
	stopLoading();
}


