var selectedAPI = null;


$(document).ready(function() {
	
	$("#add-manual").click(function() {
		addManualToWhitelist();
	});
	
	$("#add-range").click(function() {
		addRangeToWhitelist();
	});
	
	$("#add-new").click(function() {
		addNewToWhitelist();
	});
	
	$("#subsSelect").change(function() {
		loadApplicationsComboBox($("#subsSelect").val());
	});
	
	$("#appSelect").change(function() {
		loadApisComboBox($("#appSelect").val());
	});
	
	loadSubscribersComboBox();
});

function radioButton1(){
	if ($("#isManual").checked) {
		$('#manualRow1').show();$('#manualRow2').show();$('#manualRow3').show();$('#manualRow4').show();
	} else {
	    $('#rangeRow1').hide();$('#rangeRow2').hide();$('#rangeRow3').hide();$('#rangeRow4').hide();$('#rangeRow5').hide();
	    $('#listRow1').hide();$('#listRow2').hide();
	    
	}
}	
function radioButton2(){
	if ($("#isRange").checked) {
		$('#rangeRow1').show();$('#rangeRow2').show();$('#rangeRow3').show();$('#rangeRow4').show();$('#rangeRow5').show();
	} else {
		$('#manualRow1').hide();$('#manualRow2').hide();$('#manualRow3').hide();$('#manualRow4').hide();
	    $('#listRow1').hide();$('#listRow2').hide();
	}
}
function radioButton3(){
	if ($("#isList").checked) {
		$('#listRow1').show();$('#listRow2').show();	
	} else {
		$('#manualRow1').hide();$('#manualRow2').hide();$('#manualRow3').hide();$('#manualRow4').hide();
		$('#rangeRow1').hide();$('#rangeRow2').hide();$('#rangeRow3').hide();$('#rangeRow4').hide();$('#rangeRow5').hide();
	}
}


function addManualToWhitelist() {
	if(!validate()) {
		return;
	}
	var subscriberList='';
	subscriberList=$("#manualNumber").val();
	jagg.post("/site/blocks/apiwise-whitelist/ajax/apiwise-whitelist.jag", {
		action : "addNewToWhitelist",
		appId : $("#appSelect").val(),
		apiId : $("#apiSelect").val(),
		subscriberList : subscriberList
		
	}, function(result) {
		var resultJS = JSON.parse(result);
		if (resultJS.Success) {
			jagg.message({
				content : resultJS.Success.text,
				type : "info"
			});
		} else {
			jagg.message({
				content : resultJS.Failed.text,
				type : "error"
			});
		}
	}, "json");
};

function addNewToWhitelist() {
	if(!validate()) {
		return;
	}
	var subscriberList = prompt("Please enter whitelist numbers Separated with comma");
	
	jagg.post("/site/blocks/apiwise-whitelist/ajax/apiwise-whitelist.jag", {
		action : "addNewToWhitelist",
		appId : $("#appSelect").val(),
		apiId : $("#apiSelect").val(),
		/*subscriptionId : subscriptionId,*/
		subscriberList : subscriberList
		
	}, function(result) {
		var resultJS = JSON.parse(result);
		if (resultJS.Success) {
			jagg.message({
				content : resultJS.Success.text,
				type : "info"
			});
		} else {
			jagg.message({
				content : resultJS.Failed.text,
				type : "error"
			});
		}
		/*if (!result.error && result.code == 1) {
			alert('Done' + result);
		} else {
			jagg.message({
				content : result.message + "fff",
				type : "error"
			});
		}*/
	}, "json");
};

function addRangeToWhitelist() {
	if(!validate()) {
		return;
	}
	var subscriberList='';
	var minValue=$("#minnumber").val();
	var maxValue=$("#maxnumber").val();

	
	for (var minValue; minValue <= maxValue; minValue++) {
		subscriberList=subscriberList+minValue+',';
	}
	
	jagg.post("/site/blocks/apiwise-whitelist/ajax/apiwise-whitelist.jag", {
		action : "addNewToWhitelist",
		appId : $("#appSelect").val(),
		apiId : $("#apiSelect").val(),
		/*subscriptionId : subscriptionId,*/
		subscriberList : subscriberList
		
	}, function(result) {
		var resultJS = JSON.parse(result);
		if (resultJS.Success) {
			jagg.message({
				content : resultJS.Success.text,
				type : "info"
			});
		} else {
			jagg.message({
				content : resultJS.Failed.text,
				type : "error"
			});
		}
		/*if (!result.error && result.code == 1) {
			alert('Done');
		} else {
			jagg.message({
				content : result.message,
				type : "error"
			});
		}*/
	}, "json");
};

function validate() {
	if(!$(subsSelect).val()) {
		jagg.message({
			content : 'A subscriber should be selected.',
			type : "error"
		});
		return false;
	}
	if(!$(appSelect).val()) {
		jagg.message({
			content : 'An application should be selected.',
			type : "error"
		});
		return false;
	}
	if(!$(apiSelect).val()) {
		jagg.message({
			content : 'An API should be selected.',
			type : "error"
		});
		return false;
	}
	if(!$(apiSelect).val()) {
		jagg.message({
			content : 'An API should be selected.',
			type : "error"
		});
		return false;
	}
	return true;
}

function loadSubscribersComboBox() {
	jagg.post("/site/blocks/apiwise-whitelist/ajax/apiwise-whitelist.jag", {
		action : "getSubscribers"
	}, function(result) {
		var subsSelect = $("#subsSelect");
		result = JSON.parse(result);
		if(result.error == 'true') {
			jagg.message({
				content : 'Error loading subscribers. Try reloading the page.',
				type : "error"
			});
			return;
		}
		
	    for (var prop in result) {
	    	subsSelect.append($("<option></option>")
	    	         .attr("value",prop)
	    	         .text(result[prop])); 
	    }
	    //alert("auto-selectedSubs>" + subsSelect.val());
	    loadApplicationsComboBox(subsSelect.val());
	}, "json");
}
function loadApplicationsComboBox(subscriberId) {
	jagg.post("/site/blocks/apiwise-whitelist/ajax/apiwise-whitelist.jag", {
		action : "getApps",
		subscriberId : subscriberId
	}, function(result) {
		var appSelect = $("#appSelect");
		appSelect.html("");
		result = JSON.parse(result);
		if(result.error == 'true') {
			jagg.message({
				content : 'Error finding applications of the subscriber.',
				type : "error"
			});
			return;
		}
	    for (var prop in result) {
	    	appSelect.append($("<option></option>")
	    	         .attr("value",prop)
	    	         .text(result[prop])); 
	    }
//	    alert("auto-selectedApp>" + appSelect.val());
	    loadApisComboBox(appSelect.val());
	}, "json");
}
function loadApisComboBox(appId) {
	//alert("appId>" + appId);
	jagg.post("/site/blocks/apiwise-whitelist/ajax/apiwise-whitelist.jag", {
		action : "getApis",
		appId : appId
	}, function(result) {
		var apiSelect = $("#apiSelect");
		apiSelect.html("");
		result = JSON.parse(result);
		if(result.error == 'true') {
			jagg.message({
				content : 'Error finding APIs of the application.',
				type : "error"
			});
			return;
		}
	    for (var prop in result) {
	    	apiSelect.append($("<option></option>")
	    	         .attr("value",prop)
	    	         .text(result[prop])); 
	    }
	    //alert("selectedAPI>" + apiSelect.val());
	}, "json");
}