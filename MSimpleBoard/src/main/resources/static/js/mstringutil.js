

/**
 * 
 * @param _data
 * @returns
 */
function chkValidValue(_data){
	if( _data === null || (typeof _data) === 'undefined' || _data.length === 0){
		return false;
	}
	return true;
}


/**
 * 
 * @param _string
 * @returns
 */
function trim( _string ){
	return _string.trim();
}