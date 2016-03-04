$(document).ready(function() {
	setDateFields();
});

function setDateFields() {
	// Create old date
	var date = new Date();
	var now = new Date();
	now.setDate(now.getDate() - 7);
	var oldDay = now.getDate();
	var oldMonth = now.getMonth() + 1;
	var oldYear = now.getFullYear();
	if (("0" + oldMonth).length == 2)
		oldMonth = "0" + oldMonth;
	if (("0" + oldDay).length == 2)
		oldDay = "0" + oldDay;
	var oldDate = oldYear + "-" + oldMonth + "-" + oldDay;

	// Create today date
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	if (("0" + month).length == 2)
		month = "0" + month;
	if (("0" + day).length == 2)
		day = "0" + day;
	var today = year + "-" + month + "-" + day;

	// Create date controllers
	$('#from_date').datepicker({
		dateFormat : "yy-mm-dd",
		onClose : function(selectedDate) {
			$("#to_date").datepicker("option", "minDate", selectedDate);
		}
	});

	$("#to_date").datepicker({
		dateFormat : "yy-mm-dd",
		onClose : function(selectedDate) {
			$("#from_date").datepicker("option", "maxDate", selectedDate);
		}
	});

	// Set created dates to date controllers
	$("#from_date").attr("value", oldDate);
	$("#to_date").attr("value", today);
}

function getSelectedSubscriber() {
	var subscriber = $("#subscriberSelect").val();
	// alert(subscriber);
	if (subscriber.trim() != "__ALL__") {
		var action = "getAppsBySubscriber";
		jagg.post("/site/blocks/customer-care/ajax/customer-care.jag", {
			action : action,
			subscriber : subscriber
		}, function(result) {
			if (!result.error) {
				if (result.data != null) {
					// alert(result.data);
					$('#appSelect').prop("disabled", false);
					var apps = result.data;
					fillAppsCombo(apps);
				} else {
					jagg.showLogin();
				}
			} else {
				jagg.showLogin();
			}
		}, "json");
	} else {
		clearCombo();
		var option = '<option value="__All__">All</option>';
		$('#appSelect').append(option);
		$('#appSelect').prop("disabled", true);
	}
}

function fillAppsCombo(apps) {
	// alert(apps.length);
	if (apps.length != 0) {
		clearCombo();
		var option = '<option value="__ALL__">All</option>';
		var i;
		for (i = 0; i < apps.length; i++) {
			var app = apps[i];
			option += '<option value="' + app.id + '">' + app.name
					+ '</option>';
		}
		$('#appSelect').append(option);
	} else {
		clearCombo();
		/*
		 * var option = '<option value="0">All</option>';
		 * $('#app').append(option);
		 */
	}
};

function clearCombo() {
	$('#appSelect').find('option').remove().end();
}

function generateCustomerCareData(fromdate, todate, number, subscriber, operator, app, api) {
	var action = "getCustomerCareData";
	jagg.post("/site/blocks/customer-care/ajax/customer-care.jag", {
		action : action,
		fromDate : fromdate,
		toDate : todate,
		msisdn : number,
		subscriber : subscriber,
		operator : operator,
		application : app,
		api : api
	}, function(result) {
		if (!result.error) {
			if(result.usage != null){
				if(result.usage == ""){

					$("#customer_care_tbl_div").empty();
					$(".pagination").empty();
					//alert("ok");
					$("#customer_care_tbl_div").append("There is no API Usage");
				} else {
					var object = result.usage;
					generateDataTable(object);
				}
			}
		} else {
			jagg.showLogin();
		}
	}, "json");
}

function generateCustomerCareReport() {
	
	var fromDate = $("#from_date").val();
	var toDate = $("#to_date").val();
	var msisdn = $("#msisdnText").val();
	var subscriber = $("#subscriberSelect").val();
	var operator = $("#operatorSelect").val();
	var app = $("#appSelect").val();
	var api = '__ALL__';
	//var api = 'payment';
	
	//generateDataTable();
	
	generateCustomerCareData(fromDate, toDate, msisdn, subscriber, operator, app, api);
}

function generateDataTable(object){
	$("#customer_care_tbl_div").empty();
	$(".pagination").empty();
	
	var tableobj = '<table class="table table-bordered"><thead><tr><th style="width: 140px;">Date</th><th>Json Body</th><th style="width: 140px;">API</th></tr></thead><tbody id="dataTableBody"></tbody></table>';
	$("#customer_care_tbl_div").append(tableobj);
	
	$.each(object, function(key, value){
		//alert(value);
		var rowHTML = '<tr>';
		var object_in = value;
		$.each(object_in, function(keyN, valueN){
			//alert(keyN +","+ valueN);
			//var rowVal = [keyN , valueN];
			//dataArray.push(rowVal);
			rowHTML = rowHTML + '<td>'+valueN+'</td>';
		});
		
		rowHTML = rowHTML + '</tr>';
		$("#dataTableBody").append(rowHTML);
		setTablePagination(0);
		
	});
	
}


function setTablePagination(pageNumber) {
	paginator(pageNumber);
}


function paginator(pageNumber) {
	var rows = $("#dataTableBody tr").length;
	var rowsPerPage = 10;
	if (rows > rowsPerPage) {
		var numberOfPages = Math.ceil(rows / rowsPerPage);
		var currentPageStart = pageNumber * rowsPerPage;
		var currentPageEnd = (pageNumber * rowsPerPage) + rowsPerPage;
		for (var i = 0; i < rows; i++) {
			if ((currentPageStart <= i) & (i < currentPageEnd)) {
				$("#dataTableBody tr").eq(i).show();
				// alert(i);
			} else {
				$("#dataTableBody tr").eq(i).hide();
			}
		}
		// alert("PAGENUMBER: "+pageNumber+"\nRows: "+rows+"\nRowsPP:
		// "+rowsPerPage+"\nPAGES: "+numberOfPages+"\nSTART:
		// "+currentPageStart+"\nEND: "+currentPageEnd);
		loadPaginatorView(numberOfPages, pageNumber);
	} else {
		$(".pagination").html('');
	}
}

function loadPaginatorView(numberOfPages, currentPage) {
	$(".pagination").html('<ul></ul>');
	var previousAppender = '<li><a href="javascript:paginator(0)"><<</a></li>';
	if (currentPage == 0) {
		previousAppender = '<li class="disabled"><a><<</a></li>';
	}
	$(".pagination ul").append(previousAppender);
	for (var i = 0; i < numberOfPages; i++) {
		var currentRow;
		var rowSticker = i + 1;
		if (i == currentPage) {
			currentRow = '<li class="active"><a>' + rowSticker + '</a></li>';
		} else {
			currentRow = '<li><a href="javascript:paginator(' + i + ')">'
					+ rowSticker + '</a></li>';
		}
		$(".pagination ul").append(currentRow);
	}
	var lastPage = numberOfPages - 1;// alert(lastPage);
	var postAppender = '<li><a href="javascript:paginator(' + lastPage
			+ ')">>></a></li>';
	if (currentPage == lastPage) {
		postAppender = '<li class="disabled"><a>>></a></li>';
	}
	$(".pagination ul").append(postAppender);
}

