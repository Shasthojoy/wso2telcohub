$(document).ready(function(){
	
    $("#param-add-button").click(function (){
        var paystatus = $("#paystatus").val();
        var maxamt = $("#maxamt").val();       
        var maxtrn = $("#maxtrn").val();
        var btn = $(this);
        btn.attr("disabled","disabled");
        //var iteration=btn.attr("iteration");
        jagg.post("/site/blocks/payment/ajax/payment.jag", { action:"saveparam",user:"username",paystatus:paystatus,maxtrn:maxtrn,maxamt:maxamt },
            function (response) {
                if (!response.error) {
                    //btn.next().show();
                    //$('#js_completeBtn'+iteration).show();
                    //btn.hide();
                    //$('#status'+iteration).text("IN_PROGRESS");
                    $('#appAddMessage').show();
                    window.location.reload();
                    
                } else {
                    jagg.showLogin();
                }
            }, "json");

    }).removeAttr("disabled","disabled");
    
    $("#payment-add-button").click(function (){
        var paystatus = $("#paystatus").val();
        var endUserId = $("#endUsertp").val();
        var transactionOperationStatus = $("#transactionOperationStatus").val();
        var referenceCode = $("#referenceCode").val();
        var description = $("#description").val();
        var amount = $("#amount").val();
        var clientCorrelator = $("#clientCorrelator").val();
        var callbackURL = $("#callbackURL").val();
        var onBehalfOf = $("#onBehalfOf").val();
        var purchaseCategoryCode = $("#purchaseCategoryCode").val();
        var channel = $("#channel").val();
        var taxAmount = $("#taxAmount").val();
                      
        var btn = $(this);
        //btn.attr("disabled","disabled");
        //var iteration=btn.attr("iteration");
        jagg.post("/site/blocks/payment/ajax/payment.jag", { action:"addpayment",endUserId:endUserId,
            transactionOperationStatus:transactionOperationStatus,referenceCode:referenceCode,
            description:description,currency:"USD",amount:amount,clientCorrelator:clientCorrelator,
            callbackURL:callbackURL,onBehalfOf:onBehalfOf,purchaseCategoryCode:purchaseCategoryCode,
            channel:channel,taxAmount:taxAmount
        },
            function (response) {
                if (!response.error) {
                    //btn.next().show();
                    //$('#js_completeBtn'+iteration).show();
                    //btn.hide();
                    //$('#status'+iteration).text("IN_PROGRESS");                    
                
                    $('#response').val(response.tasks);
                    $('#request').val(response.req);
                    
                    window.location.reload();
                    
                } else {
                    jagg.showLogin();
                }
            }, "json");

    }).removeAttr("disabled","disabled");
    
    $("#maxtrn").keydown(function(event) {
        if ( event.keyCode == 46 || event.keyCode == 8 ) {
        }
        else if (event.keyCode == 37 || event.keyCode == 39) {
        }
        else {
            if (event.keyCode < 95) {
                if (event.keyCode < 48 || event.keyCode > 57 ) {
                    event.preventDefault();
                }
            }
            else {
                if (event.keyCode < 96 || event.keyCode > 105 ) {
                    event.preventDefault();
                }
            }
            if($("#maxtrn").val().length >= 10) {
                event.preventDefault();
            }
        }
    });
    
    $("#maxamt").keyup(function(event) {
        
    	var inputVal = $("#maxamt").val();
        var numericReg = /^[0-9]{0,10}(\.[0-9]{0,2})?$/;
        if(!numericReg.test(inputVal)) {
        	event.preventDefault();
        	$("#maxamt").val(inputVal.substr(0,inputVal.length-1));
        	return false;
        }
    });
    
   
    $("#maxamt").keydown(function(event) {
       
    	if ( event.keyCode == 46 || event.keyCode == 8 ) {
        }
        else if (event.keyCode == 37 || event.keyCode == 39) {
        } else if (event.keyCode == 190) {        	
        }
        else {
            if (event.keyCode < 95) {
                if (event.keyCode < 48 || event.keyCode > 57 ) {
                    event.preventDefault();
                }
            }
            else {
                if (event.keyCode < 96 || event.keyCode > 105 ) {
                    event.preventDefault();
                }                
            }
            if($("#maxamt").val().length >= 10) {
                event.preventDefault();
            }
            var inputVal = $("#maxamt").val();
            var numericReg = /^[0-9]{0,10}(\.[0-9]{0,2})?$/;
            if(!numericReg.test(inputVal)) {
            	event.preventDefault();
            	return false;
            }
        }
    	
    	
    	
    });
    

    $('.js_completeBtn').click(function(){
        var btn = $(this);
        var taskId=btn.attr("data");
        var iteration=btn.attr("iteration");
        var description=$('#desc'+iteration).text();
        var status=$('.js_stateDropDown').val();
        btn.attr("disabled","disabled");
        jagg.post("/site/blocks/task-manager/ajax/task.jag", { action:"completeTask",status:status,taskId:taskId,taskType:"application",description:description },
            function (json) {
                if (!json.error) {
                    btn.next().show();
                    btn.next().next().html(json.msg);
                    btn.hide();
                    window.location.reload();
                } else {
                    jagg.showLogin();
                }
            }, "json");

    }).removeAttr("disabled","disabled");

    $('.js_assignBtn').click(function(){
        var btn = $(this);
        var taskId=btn.attr("data");
        var iteration=btn.attr("iteration");
        btn.attr("disabled","disabled");
        jagg.post("/site/blocks/task-manager/ajax/task.jag", { action:"assignTask",taskId:taskId,taskType:"application" },
            function (json) {
                if (!json.error) {
                    btn.next().show();
                    $('#js_startBtn'+iteration).show();
                    btn.hide();
                    $('#status'+iteration).text("RESERVED");
                } else {
                    jagg.showLogin();
                }
            }, "json");
    }).removeAttr("disabled","disabled");

});
