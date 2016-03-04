$(document).ready(function() {
	setTablePagination(0);
});

function sendlocationRequest() {
	var address = $("#address").val();
	var requestedAccuracy = $("#requestedAccuracy").val();
	// alert(requestedAccuracy);
	saveLocationRequest(address, requestedAccuracy);
};

function saveLocationRequest(address, requestedAccuracy) {
	var action = "saveLocationRequest";
	jagg.post("/site/blocks/location/ajax/location-track.jag", {
		action : action,
		address : address,
		requestedAccuracy : requestedAccuracy
	}, function(result) {
		if (!result.error) {
			if (result.data != "null") {
				$('#json-response').val(result.data);
				location.reload();
			} else {
				$('#errorMessage').show();
				$('#errorMessage').delay(4000).hide('fast');
			}
			setTablePagination(0);
		} else {
			$('#errorMessage').show();
			$('#errorMessage').delay(4000).hide('fast');
		}
	}, "json");
};

function performSettingsAction() {
	var action = "performSettingsAction";
	var alt = $("#altitude").val();
	var lat = $("#latitude").val();
	var longi = $("#longitude").val();
	var state = $("#retState").val();

	jagg.post("/site/blocks/location/ajax/location-track.jag", {
		action : action,
		altitude : alt,
		latitude : lat,
		longitude : longi,
		lbsStatus : state
	}, function(result) {
		if (!result.error) {
			if (result.data != "null") {
				$('#lbsAddMessage').show();
				$('#lbsAddMessage').delay(4000).hide('fast');
				location.reload();
			} else {
				$('#lbsErrorMessage').show();
				$('#lbsErrorMessage').delay(4000).hide('fast');
			}
			setTablePagination(0);
		} else {
			$('#lbsErrorMessage').show();
			$('#lbsErrorMessage').delay(4000).hide('fast');
		}
	}, "json");
};

function setTablePagination(pageNumber) {
	paginator(pageNumber);
}

function paginator(pageNumber) {
	var rows = $("#loc_request_table #lbs_table_body tr").length;
	var rowsPerPage = 10;
	if (rows > rowsPerPage) {
		var numberOfPages = Math.ceil(rows / rowsPerPage);
		var currentPageStart = pageNumber * rowsPerPage;
		var currentPageEnd = (pageNumber * rowsPerPage) + rowsPerPage;
		for (var i = 0; i < rows; i++) {
			if ((currentPageStart <= i) & (i < currentPageEnd)) {
				$("#loc_request_table #lbs_table_body tr").eq(i).show();
				// alert(i);
			} else {
				$("#loc_request_table #lbs_table_body tr").eq(i).hide();
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