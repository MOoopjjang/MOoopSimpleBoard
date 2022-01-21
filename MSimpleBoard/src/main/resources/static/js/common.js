

/**
 * 
 * @returns
 */
function getContextPath(){
   var offset=window.location.href.indexOf(window.location.host)+window.location.host.length;
   var ctxPath=window.location.href.substring(offset,window.location.href.indexOf('/',offset+1));
   return ctxPath;
}

