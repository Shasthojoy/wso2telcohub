var timeout = 60000;
var pollingInterval = 2000;
var timeRemaining = timeout;
var hasResponse = false;
var isTimeout = false;
var status;

var pollingVar = setInterval(pollForStatus, pollingInterval);

/* 
 * Check for USSD response status if timeout not is reached 
 * or user approval status(USSD) is not specified (YES/NO).
 */
function pollForStatus() {

	// If timeout has not reached.
	if(timeRemaining > 0) {
		// If user has not specified a response(YES/NO).
		if(!hasResponse) {
			checkUSSDResponseStatus();
			timeRemaining = timeRemaining - pollingInterval;
			
		} else {
			handleTermination();
		}
		
	} else {
		isTimeout = true;
		handleTermination();
	}
}

/*
 * Handle polling termination and form submit.
 */
function handleTermination() {
	//window.clearInterval(pollingVar);
	//window.open("./landing.html",'_self',"User Registration");
	
	window.clearInterval(pollingVar);
	var STATUS_PENDING = "PENDING";
	if(status.toUpperCase()==STATUS_PENDING){
		deleteUser(sessionId);
	}
	
    $('#waiting_screen_success').show();
    $('#waiting_screen').hide();
    setTimeout(function(){window.open("./landing.html","_self")}, 5000);
	
}

/*
 * Invoke the endpoint to retrieve USSD status.
 */


function qs(key) {
	
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars[key];
}


function checkUSSDResponseStatus() {
	
	//var sessionId = document.getElementById('username').value;
	var sessionId=qs('username');
	
	//var url = "../MediationTest/tnspoints/endpoint/ussd/status?sessionID=" + sessionId;
	var url = "../mavenproject1-1.0-SNAPSHOT/webresources/endpoint/ussd/status?username=" + sessionId;
	var STATUS_PENDING = "PENDING";
	
	$.ajax({
		type: "GET",
		url:url,
		async: false,
		success:function(result){
			if(result != null) {
				var responseStatus = result.status; 
				
				if(responseStatus != null && responseStatus.toUpperCase() != STATUS_PENDING) {
					hasResponse = true;
					status = result.status;
				}
			}
	}});
}
