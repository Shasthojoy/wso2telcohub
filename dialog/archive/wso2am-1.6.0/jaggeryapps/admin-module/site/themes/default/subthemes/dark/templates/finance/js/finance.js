function getSelectedSubscriber() {
	var subscriber = $("#subscriber").val();
	// alert(subscriber);
	if (subscriber.trim() != "__ALL__") {
		var action = "getAppsBySubscriber";
		jagg.post("/site/blocks/finance/ajax/finance.jag", {
			action : action,
			subscriber : subscriber
		}, function(result) {
			if (!result.error) {
				if (result.data != null) {
					// alert(result.data);
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
		var option = '<option value="0">All</option>';
		$('#app').append(option);
	}
}

function fillAppsCombo(apps) {
	// alert(apps.length);
	if (apps.length != 0) {
		clearCombo();
		var option = '<option value="0">All</option>';
		var i;
		for (i = 0; i < apps.length; i++) {
			var app = apps[i];
			option += '<option value="' + app.id + '">' + app.name
					+ '</option>';
		}
		$('#app').append(option);
	} else {
		clearCombo();
		/*
		 * var option = '<option value="0">All</option>';
		 * $('#app').append(option);
		 */
	}
};

function clearCombo() {
	$('#app').find('option').remove().end();
}

function generateFinanceReport() {
	// alert("generateFinanceReport");
	var year = $("#year").val();
	var month = $("#month").val();
	var subscriber = $("#subscriber").val();

	var period = year + "-" + month;
	generateFinanceReportTable(period, subscriber);
}

function generateFinanceReportTable(period, subscriber) {
	// alert(period);
	// alert(subscriber);
	var action = "getBillingData";
	jagg.post("/site/blocks/finance/ajax/finance.jag", {
		action : action,
		period : period,
		subscriber : subscriber
	}, function(result) {
		if (!result.error) {
			if (result.usage != null) {
				// alert(result.usage[0].subscriber);
				var usage = result.usage;
				$('#finance_report_table_content').empty();
				createFinanceReportTable(usage);
			} else {
				jagg.showLogin();
			}
		} else {
			jagg.showLogin();
		}
	}, "json");
}

function createFinanceReportTable(usage) {
	if (usage.length > 0) {
		var retMsgTable = $('<table></table>').attr({
			id : "finance_report_table"
		}).attr({
			class : "table table-bordered table-striped"
		});
		var hRow = $('<tr></tr>').appendTo(retMsgTable);
		$('<th></th>').text("Subscriber").appendTo(hRow);
		$('<th></th>').text("Application").appendTo(hRow);
		$('<th></th>').text("API").appendTo(hRow);
		$('<th></th>').text("Version").appendTo(hRow);
		$('<th></th>').text("Charging Plan").appendTo(hRow);
		$('<th></th>').text("Count").appendTo(hRow);
		$('<th></th>').text("Usage Charge").appendTo(hRow);
		$('<th></th>').text("Tax").appendTo(hRow);
		$('<th></th>').text("Grand Total").appendTo(hRow);

		for (var i = 0; i < usage.length; i++) {
			//var bRow = $('<tr></tr>').appendTo(retMsgTable);
			
			var subscriberDetails = usage[i];
			var applicationDetails = subscriberDetails.applications;
			
//			$('<td></td>').text(usage[i].subscriber).appendTo(bRow);
//			$('<td></td>').text(usage[i].applications).appendTo(bRow);
//			$('<td></td>').text(usage[i].messageId).appendTo(bRow);
//			$('<td></td>').text(usage[i].resourceURL).appendTo(bRow);
//			$('<td></td>').text(usage[i].message).appendTo(bRow);
//			$('<td></td>').text(usage[i].dateTime).appendTo(bRow);
		}
		retMsgTable.appendTo("#finance_report_table_content");
	}
}
