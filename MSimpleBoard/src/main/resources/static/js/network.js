



/**
 * 
 * @param _url
 * @param _type
 * @param _datatype
 * @param _data
 * @param _success
 * @param _error
 * @param _complete
 * @returns
 */
function requestJSONData(_url, _type, _datatype, _data, _success, _error, _complete){
	$.ajax({
	        url : _url,
//	
//	 		beforeSend: function(xhr) {
//	 			xhr.setRequestHeader('X-CSRF-Token', $('meta[name="csrf-token"]').attr('content'))
//	 		},
//	 		
	        type : _type,
	        dataType : _datatype,
	        data : JSON.stringify(_data),
	        contentType : 'application/json',
	        success : _success,
	        error : _error,
	        complete : _complete
	    });
}


/**
 * 
 * @param _url
 * @param _method
 * @param _datatype
 * @param _data
 * @param _contentType
 * @param _success
 * @param _error
 * @returns
 */
function requestAjax(_url , _method , _datatype , _data , _contentType , _success , _error){
	$.ajax({
		url:_url,
		method:_method,
		dataType:_datatype,
		data:_data,
		contentType:_contentType,
		success:_success,
		error:_error
	});
}