$(function () {
    $('.register').click(function () {/*
        var str = "/dashboard/self_registration.jag";

        $.ajax({
            type: "GET",
            url: str

        })
            .done(function (data) {
                json = $.parseJSON(data);
                $('#myModal').modal('show');
                drawPage(json);

            })
            .fail(function () {
                console.log('error');

            })
            .always(function () {
                console.log('completed');
            });
    */});


    $('.login').click(function () {
        window.location = ('index.jag');
    });
});

function drawPage(json) {
    var output = "";
    var body = "";
    var head = "" +
        "            <div class=\"modal-body\" >" +
        "                <form method=\"post\" class=\"form-horizontal\" id=\"selfReg\" name=\"selfReg\" action=\"controllers/user-registration/add.jag?\" >\n" +
        "";
    for (var i in json.pwdRegexps.return) {
        body = body + "                    <input type=\"hidden\" name=\"regExp_" + json.pwdRegexps.return[i].domainName + "\" value=\"" + json.pwdRegexps.return[i].regEx + "\" />\n";
    }
    body = body + "                    <div class=\"control-group\">" +
        //              "                        <label class=\"control-label\" for=\"domain\">Domain Name<span class=\"required\">*</span></label>\n" +
        "                        <div class=\"controls\" style=\"display:none;\" type=\"hidden\">\n" +
        "                            <select class=\"col-lg-3\" name=\"domain\">\n";
    for (var i in json.pwdRegexps.return) {
        body = body + "                                <option value=\"" + json.pwdRegexps.return[i].domainName + "\">" + json.pwdRegexps.return[i].domainName + "</option>\n";
    }
    body = body + "                            </select>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                            <input class=\"col-lg-3\" type=\"hidden\" value=\"\" id=\"user_name\" name=\"userName\"  />\n" +
        "                            <input class=\"col-lg-3\" type=\"hidden\" value=\"\" id=\"password\" name=\"pwd\"  />\n" +
        "                            <input class=\"col-lg-3\" type=\"hidden\" value=\"\" id=\"retype_pwd\" name=\"retypePwd\"  />\n";


    for (var i in json.fieldValues.return) {
        if (json.fieldValues.return[i].required == "true") {
            body = body + "                    <div class=\"control-group\">\n" +
                "                        <label class=\"control-label\" for=\"" + json.fieldValues.return[i].fieldName + "\">" + json.fieldValues.return[i].fieldName;
            if (json.fieldValues.return[i].required == "true") {
                body = body + " <span class=\"required\">*</span>";
                if (json.fieldValues.return[i].regEx != "") {
                    body = body + "      <input type=\"hidden\" name=\"mailRegEx\" value=\"" + json.fieldValues.return[i].regEx + "\" />\n" +
                        "                    <input type=\"hidden\" name=\"mailInput\" value=\"" + json.fieldValues.return[i].claimUri + "\" />\n";
                }

            }

            body = body + "</label>\n" +
                "                        <div class=\"controls\">\n" +
                "                            <input class=\"col-lg-3\" type=\"text\" value=\"\" id=\"" + json.fieldValues.return[i].fieldName + "\" name=\"" + json.fieldValues.return[i].claimUri + "\"  />\n" +
                "                        </div>\n" +
                "                    </div>\n";
        }
    }
    var end = "";

    end = end + "                    <div class=\"control-group\"><div class=\"controls\">" +
        "	               <input type=\"checkbox\" name=\"tc\" value=\"tc\"> By selecting,you agree to our <a href=\"termsConditions.html\" target=\"_blank\" >Terms&Conditions</a> <br>" +

        "<table border=0>"+
                        "<tr>"+
                        "<td>"+
                        "</br>"+
                        "<div>"+
                        "<b>Dialog Mobile Connect Privacy Notice</b>"+
                               "<ul type=\"circle\">"+
                               "       <li>Dialog Mobile Connect Privacy Notice"+
                                       "<li>Mobile Connect service is provided to you by Dialog Axiata PLC. We"+
                                               "care about your privacy and we've kept it Simple<br />"+
                                       "<li>You can login-in privately with Mobile connect and we: <br />"+
                                               "<ul type=\"disc\">"+
                                               "       <li>Never share your phone number with anyone else.<br />"+
                                               "       <li>Never disclose your personal information unless you choose to"+
                                                               "share and give your consent.<br />"+
                                               "</ul>"+
                                       "<li>Click here"+
                                       "       <link to Dialog Mobile connect Privacy Policy> find out how mobile"+
                                      "       connect works and your choices.<br />"+
                                       "<li>We don't want you to worry. You can contact us with any queries or"+
                                       "       concerns about Mobile Connect by sending an email to <contact email>"+
                                       "       <br />"+
                               "</ul>"+
                        "</div>"+
                        "</br>"+
                        "</br>"+
                        "</br>"+
                        "<center>"+
                        "</br>"+
                        "</br>"+
                        "</center>"+
                        "</td>"+
                        "</tr>"+
                        "</table>"+

        "                    </div></div>" +
        "                    </div>" +
        "                   <div class=\"modal-footer\">" +
        "                            <input type=\"button\" onclick=\"validate();\" class=\"btn btn-primary\" value=\"Register\"/>\n" +
        "                            <input type=\"button\" onclick=\"cancelProcessToLogin();\" class=\"btn\" value=\"Cancel\" data-dismiss=\"modal\" />\n" +
        "                   </div>" +
        "                </form>\n" +
        "    </div>   ";
    output = head + body + end;
    $("#modalData").empty();
    $("#modalData").append(output);

}

function cancelProcessToLogin() {
    document.getElementById('light').style.display = 'none';
    document.getElementById('fade').style.display = 'none';

}

var msisdnval='';

function validate() {
	var selectQ1 = document.getElementsByName('challengeQuestion1')[0];
	var challengeQ1 = selectQ1.options[selectQ1.selectedIndex].value;
	var selectQ2 = document.getElementsByName('challengeQuestion2')[0];
	var challengeQ2 = selectQ2.options[selectQ2.selectedIndex].value;
	
	
	var challengeA1 = document.getElementsByName('challengeAns1')[0].value;
	var challengeA2 = document.getElementsByName('challengeAns2')[0].value;
	
	document.getElementsByName('http://wso2.org/claims/challengeQuestion1')[0].value = challengeQ1 + "!" + challengeA1;
	document.getElementsByName('http://wso2.org/claims/challengeQuestion2')[0].value = challengeQ2 + "!" + challengeA2;

	
    var element = "<div class=\"modal fade\" id=\"messageModal\">\n" +
        "  <div class=\"modal-dialog\">\n" +
        "    <div class=\"modal-content\">\n" +
        "      <div class=\"modal-header\">\n" +
        "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n" +
        "        <h3 class=\"modal-title\">Modal title</h4>\n" +
        "      </div>\n" +
        "      <div class=\"modal-body\">\n" +
        "        <p>One fine body&hellip;</p>\n" +
        "      </div>\n" +
        "      <div class=\"modal-footer\">\n" +
        "      </div>\n" +
        "    </div>\n" +
        "  </div>\n" +
        "</div>";
    $("#message").empty();
    $("#message").append(element);

    var msisdn = document.getElementsByName("http://wso2.org/claims/mobile")[0];
    msisdnval=msisdn.value;
    var fld = document.getElementsByName("userName")[0];
    var value = fld.value;

    //fld.value = "MobileConnect" + Math.floor((Math.random() * 10000) + 1);;
    //var value1 = fld.value;

    fld.value = msisdn.value;

    var fldPwd = document.getElementsByName("pwd")[0];
    var value = fldPwd.value;

    fldPwd.value = "cY4L3dBf";
    var value2 = fldPwd.value;

    var fldRe = document.getElementsByName("retypePwd")[0];
    var value = fldRe.value;

    fldRe.value = "cY4L3dBf";
    var value3 = fldRe.value;


    var value4 = msisdn.value;

    if (!(value4.charAt(0) == '9' && value4.charAt(1) == '4' && value4.charAt(2) == '7')) {
        message({content: 'Invalid Mobile Number.Should be in 9477....... format', type: 'error', cbk: function () {
        } });
        return false;
    }



	
	
	
//var strBackend = "http://10.62.96.187:9764/mavenproject1-1.0-SNAPSHOT/webresources/endpoint/ussd/pin?username=" + fld.value + "&" + "msisdn=" + value4;


    //$.ajax({
//	  type:"GET",
//	  url:strBackend

    // })

	var checkIfExistUser = "/dashboard/user_service.jag?username=" + msisdn.value;
    $.ajax({
        type: "GET",
        url: checkIfExistUser,
        async:false,
    }).done(function (data) {
        json = $.parseJSON(data);
        //$('#myModal').modal('show');
        //drawPage(json);
        if (json.return=='true'){
        	var msg = "User Name is already exist";
            message({content: msg, type: 'error', cbk: function () {
            } });
            return true;
        }else{
	
	
    var strBack = "/dashboard/backend_service.jag?msisdn=" + msisdn.value;

    $.ajax({
        type: "GET",
        url: strBack
    })
        	
	
	
    if (validateEmpty("userName").length > 0) {
        var msg = "User Name is required";
        message({content: msg, type: 'error', cbk: function () {
        } });
        return false;
    }

    if (validateEmpty("pwd").length > 0) {
        var msg = "Password is required";
        ;
        message({content: msg, type: 'error', cbk: function () {
        } });
        return false;
    }

    if (validateEmpty("retypePwd").length > 0) {
        var msg = "Password verification is required";
        ;
        message({content: '', type: 'error', cbk: function () {
        } });
        return false;
    }

    var pwd = $("input[name='pwd']").val();
    var retypePwd = $("input[name='retypePwd']").val();

    if (pwd != retypePwd) {
        var msg = "Password does not match";
        message({content: msg, type: 'error', cbk: function () {
        } });
        return false;
    }

    var domain = $("select[name='domain']").val();
    var pwdRegex = $("input[name='regExp_" + domain + "']").val();

    var reg = new RegExp(pwdRegex);
    var valid = reg.test(pwd);
    if (pwd != '' && !valid) {
        message({content: 'Password does not match with password policy', type: 'error', cbk: function () {
        } });
        return false;
    }


    var unsafeCharPattern = /[<>`\"]/;
    var elements = document.getElementsByTagName("input");
    for (i = 0; i < elements.length; i++) {
        if ((elements[i].type === 'text' || elements[i].type === 'password') &&
            elements[i].value != null && elements[i].value.match(unsafeCharPattern) != null) {
            message({content: 'Unsafe input found', type: 'error', cbk: function () {
            } });
            return false;
        }
    }

    if (!document.getElementsByName("tc")[0].checked) {
        message({content: 'Please accept terms & conditions to complete registration', type: 'error', cbk: function () {
        } });
        return false;
    }

    for (i = 0; i < elements.length; i++) {
        if ((elements[i].type === 'text' || elements[i].type === 'password') &&
            (elements[i].value == null || elements[i].value == "" )) {
            message({content: 'Input value should not be empty', type: 'error', cbk: function () {
            } });
            return false;
        }
    }

    var mailRegex = $("input[name='mailRegEx']").val();
    var mailInputName = $("input[name='mailInput']").val();
    var mailValue = $("input[name='" + mailInputName + "']").val();
    var regMail = new RegExp(mailRegex);
    var validMail = regMail.test(mailValue);
    if (mailValue != '' && !validMail) {
        message({content: 'Email is not valid ', type: 'error', cbk: function () {
        } });
        return false;
    }
    // var question1 = document.getElementsByName("http://wso2.org/claims/challengeQuestion1")[0];

    // var question1Answer = question1.value;
//  var question1Value = question1.id;


    //question1.value = question1Value + "!" +question1Answer;


    // var question2 = document.getElementsByName("http://wso2.org/claims/challengeQuestion2")[0];

    // var question2Answer = question2.value;
    // var question2Value = question2.id;

    // question2.value = question2Value + "!" + question2Answer;
    
    
    

    
    	checkUserRegistered();
    	
        	
        }
        
    })
    .fail(function () {
        console.log('error');
    })
    .always(function () {
        console.log('completed');
	});   
    
    
}


function checkUserRegistered(){
	document.selfReg.submit();
	window.open("waiting.jsp?username="+ msisdnval);
}

