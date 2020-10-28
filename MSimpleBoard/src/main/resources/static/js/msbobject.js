
/**
 * Application 전역객체 
 */
var MSB = MSB || {}



function setLeftInfo(dep1 , dep2){
	if(MSB['left'] === undefined){
		MSB['left'] = {};
		MSB['left']['deps1'] = '';
		MSB['left']['deps2'] = '';
	}
	
	MSBLeft = MSB['left'];
	MSBLeft['dep1'] = dep1;
	MSBLeft['dep2'] = dep2;
}


function getLeftInfo(){
	if(MSB['left'] === null){
		return {};
	}
	
	return MSB['left'];
}
