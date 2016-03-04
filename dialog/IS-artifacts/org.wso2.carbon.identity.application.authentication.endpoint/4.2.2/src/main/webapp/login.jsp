<!DOCTYPE html>
<!--
~ Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
~
~ WSO2 Inc. licenses this file to you under the Apache License,
~ Version 2.0 (the "License"); you may not use this file except
~ in compliance with the License.
~ You may obtain a copy of the License at
~
~    http://www.apache.org/licenses/LICENSE-2.0
~
~ Unless required by applicable law or agreed to in writing,
~ software distributed under the License is distributed on an
~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
~ KIND, either express or implied.  See the License for the
~ specific language governing permissions and limitations
~ under the License.
-->
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.IOException"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.CharacterEncoder"%>
<%@ page import="org.wso2.carbon.identity.application.common.util.IdentityApplicationConstants"%>

<fmt:bundle basename="org.wso2.carbon.identity.application.authentication.endpoint.i18n.Resources">

    <html lang="en">
    <head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
        <title>Login with WSO2 Identity Server</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="/authenticationendpoint/assets/css/bootstrap.min.css" rel="stylesheet">
        <link href="/authenticationendpoint/css/localstyles.css" rel="stylesheet">
        <!--[if lt IE 8]>
        <link href="css/localstyles-ie7.css" rel="stylesheet">
        <![endif]-->
	<link href="/authenticationendpoint/css/styles-axiata.css" rel="stylesheet">

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
        <script src="assets/js/html5.js"></script>
        <![endif]-->
        <script src="/authenticationendpoint/assets/js/jquery-1.7.1.min.js"></script>
        <script src="/authenticationendpoint/js/scripts.js"></script>
	<style>
	div.different-login-container a.truncate {
	  width: 148px;
	  white-space: nowrap;
	  overflow: hidden;
	  text-overflow: ellipsis;
	}
	</style>

    </head>

    <body>
<div id="wrap">
    <a class="brand"
       href="/dashboard/landing.html?"
       onclick="jagg.sessionAwareJS({redirect:'/dashboard/landing.html?',e:event})">
        <img
                alt="API Store"
                src="/authenticationendpoint/images/logo.png"
                >
    </a>

        <div class="navbar main-menu">
            <div class="navbar-inner main-menu-navbar">

                <ul class="nav orderFix">
                    <li>
                        <a class="link-home" href="../dashboard/landing.html" title="Mobile Connect">
                            Home
                        </a>
                    </li>
                    <li class="active">
                        <a href="#" >Login</a>
                    </li>
                    <li>
                        <a href="../dashboard/register.jag" onclick="return saveLink();">Sign-up</a>
                    </li>


                </ul>


            </div>
        </div>
            <div class="container-fluid">
                <div class="row-fluid">


                    <div id="middle" class="span12">



                        <div class="container-fluid login-container">
                            <div class="row-fluid">
                                <div class="span12 login-content">

                                    <div class="clear"></div>
                                        <div class="content-data">
                                            <div class="alert alert-error" id="loginError" style="display:none">
                                                <span id="loginErrorSpan"></span>
                                            </div>

                                            <%--START new content--%>

                                            <div class="overlay" style="display:none"></div>


                                            <div class="header-text">

                                            </div>
                                            <!-- container -->
                                            <%@ page import="java.util.Map" %>
                                            <%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.CharacterEncoder" %>
                                            <%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.Constants" %>
                                            <%@ page import="org.wso2.carbon.identity.application.authentication.endpoint.util.TenantDataManager" %>
                                            <%
                                               request.getSession().invalidate();

                                                String queryString = request.getQueryString();
                                                Map<String, String> idpAuthenticatorMapping = null;
                                                if (request.getAttribute("idpAuthenticatorMap") != null) {
                                                    idpAuthenticatorMapping = (Map<String, String>)request.getAttribute("idpAuthenticatorMap");
                                                }
                                                String errorMessage = "You are not registered with Mobile connect! Please Register and Retry";
                                                String loginFailed = "false";

                                                if (CharacterEncoder.getSafeText(request.getParameter(Constants.AUTH_FAILURE)) != null &&
                                                        "true".equals(CharacterEncoder.getSafeText(request.getParameter(Constants.AUTH_FAILURE)))) {
                                                    loginFailed = "true";
                                                if( request.getParameter("isexisting").equalsIgnoreCase("false")){

                                                if(CharacterEncoder.getSafeText(request.getParameter(Constants.AUTH_FAILURE_MSG)) != null){
                                                        errorMessage = (String) CharacterEncoder.getSafeText(request.getParameter(Constants.AUTH_FAILURE_MSG));

                                                        if (errorMessage.equalsIgnoreCase("login.fail.message")) {
                                                         if(request.getParameter("acr_values")!=null){
                                                         String clientId=request.getParameter("client_id");
                                                         String redirectUri=URLEncoder.encode(request.getParameter("redirect_uri"),"UTF-8");
                                                         String scope= URLEncoder.encode(request.getParameter("scope"), "UTF-8");
                                                         String arc=request.getParameter("acr_values");
                                                         String responseType=request.getParameter("response_type");
                                                         String authenticator=request.getParameter("authenticators");
                                                         String token=null;
                                                         String requestURL=request.getRequestURL().toString();
                                                         String requestURI=request.getRequestURI();
                                                         String baseURL=requestURL.substring(0, requestURL.indexOf(requestURI));
                                                         URL url=new URL(baseURL+"/UserRegistration-1.0-SNAPSHOT/webresources/endpoint/user/authenticate/add?scope="+scope+"&redirecturi="+redirectUri+"&clientid="+clientId+"&acrvalue="+arc+"&responsetype="+responseType);
                                                         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                                            conn.setRequestMethod("GET");
                                                         	conn.setRequestProperty("Accept", "application/json");
                                                         	if (conn.getResponseCode() != 200) {
                                                         			throw new RuntimeException("Failed : HTTP error code : "
                                                         					+ conn.getResponseCode());
                                                         		}
                                                         	else{
                                                         		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                                                                token = br.readLine();
                                                                conn.disconnect();
                                                                String site = new String("/dashboard/register.jag?token="+token);
                                                                response.setStatus(response.SC_MOVED_TEMPORARILY);
                                                                response.setHeader("Location", site);
                                                                }
                                                              }
                                                              else{
                                                           errorMessage = "You are not a registered user. Please register and try again";
                                                           }
                                                        }
                                                      }
                                                   }else {
                                                       errorMessage = "Authentication failed.Please try again";
                                                        }

                                                }
                                            %>

                                            <script type="text/javascript">

                                                function doLogin() {
                                                    var loginForm = document.getElementById('loginForm');
                                                    loginForm.submit();
                                                }

                                            </script>

                                            <%

                                                boolean hasLocalLoginOptions = false;
                                                List<String> localAuthenticatorNames = new ArrayList<String>();

                                                if (idpAuthenticatorMapping.get(IdentityApplicationConstants.RESIDENT_IDP_RESERVED_NAME) != null){
                                                    String authList = idpAuthenticatorMapping.get(IdentityApplicationConstants.RESIDENT_IDP_RESERVED_NAME);
                                                    if (authList!=null){
                                                        localAuthenticatorNames = Arrays.asList(authList.split(","));
                                                    }
                                                }


                                            %>

                                            <%if(localAuthenticatorNames.contains("BasicAuthenticator")){ %>
                                            <div id="local_auth_div">
                                                <%} %>

                                                <% if ("true".equals(loginFailed)) { %>
                                                <div class="alert alert-error">
                                                    <%=errorMessage%>
                                                </div>
                                                <% } %>

                                                <form action="../../commonauth" method="post" id="loginForm" class="form-horizontal" onsubmit="return setSession()" >
                                                   <div class="container">
                                                        <h2>Mobile Connect</h2>
                                                        <h3>The Simple and Secure Login Solution with Strong Privacy Protection</h3>
                                                        <p>Authenticate User to your digital service via any device, on any channel, using proven, trusted and secure mobile technology</p>


                                                            <%
                                                                if(localAuthenticatorNames.size()>0) {

                                                                    if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("OpenIDAuthenticator")){
                                                                        hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="openid.jsp" %>

                                                                </div>
                                                            </div>

                                                            <%
                                                            } else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("BasicAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>


                                                            <%
                                                                  if(TenantDataManager.isTenantListEnabled() && "true".equals(CharacterEncoder
                                                                  .getSafeText(request.getParameter("isSaaSApp")))){
                                                            %>
                                                            <div class="row">
                                                                <div class="span12">

                                                                        <%@ include file="tenantauth.jsp" %>

                                                                </div>
                                                            </div>

                                                            <script>
                                                                //set the selected tenant domain in dropdown from the cookie value
                                                                window.onload=selectTenantFromCookie;
                                                            </script>

                                                            <%
                                                                } else{
                                                            %>
                                                            <div class="row">
                                                                <div class="span12">
                                                                    <%@ include file="basicauth.jsp" %>

                                                                </div>
                                                            </div>

                                                            <%
                                                                }
                                                            %>

                                                            <%
                                                            } else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("PinAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="pin.jsp" %>

                                                                </div>
                                                            </div>

                                                            <%
                                                            }  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("MSISDNAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="msisdn.jsp" %>

                                                                </div>
                                                            </div>
                                                            <%
                                                            } else if (localAuthenticatorNames.size() > 0 && localAuthenticatorNames.contains("WhiteListMSISDNAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="whitelistmsisdn.jsp" %>

                                                                </div>
                                                            </div>
                                                              <%
                                                                }  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("MSSAuthenticator")) {
                                                                      hasLocalLoginOptions = true;
                                                                %>

                                                                 <div class="row">
                                                                  <div class="span12">
                                                                    <%@ include file="waiting.jsp" %>

                                                                   </div>
                                                                 </div>

                                                            <%

                                                                }  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("MSSPinAuthenticator")) {
                                                                      hasLocalLoginOptions = true;
                                                                %>

                                                                 <div class="row">
                                                                  <div class="span12">
                                                                    <%@ include file="waiting.jsp" %>

                                                                   </div>
                                                                 </div>

                                                            <%


                                                            }  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("GSMAMSISDNAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="gsmamsisdn.jsp" %>

                                                                </div>
                                                            </div>

                                                            <%
                                                            }  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("USSDAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="waiting.jsp" %>

                                                                </div>
                                                            </div>
															                             <%
             												} else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("USSDPinAuthenticator")) {
                 												hasLocalLoginOptions = true;
                 											%>

                     										<div class="row">
                         										<div class="span12">

                             										<%@ include file="waiting.jsp" %>

                         										</div>
                     										</div>

															<%
             												} else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("MePinAuthenticatorPIN")) {
                 												hasLocalLoginOptions = true;
                 											%>

                     										<div class="row">
                         										<div class="span12">

                             										<%@ include file="waiting.jsp" %>

                         										</div>
                     										</div>


                             								<%
             												}  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("HeaderEnrichmentAuthenticator")) {
                 												hasLocalLoginOptions = true;
                 											%>

                     										<div class="row">
                         										<div class="span6">

                             										<%@ include file="headerauth.jsp" %>

                         										</div>
                     										</div>


                                                            <%
                                                            }  else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("SMSAuthenticator")) {
                                                                hasLocalLoginOptions = true;
                                                            %>

                                                            <div class="row">
                                                                <div class="span12">

                                                                    <%@ include file="waiting.jsp" %>

                                                                </div>
                                                            </div>

                                                            <%
                                                                    }
                                                                }

                                                            if ((hasLocalLoginOptions && localAuthenticatorNames.size() > 1) || (!hasLocalLoginOptions)
                                                                    || (hasLocalLoginOptions && idpAuthenticatorMapping.size() > 1)) {
                                                        %>
                                                        <div class="row">
                                                            <div class="span12">
                                                                <% if(hasLocalLoginOptions) { %>
                                                                <h2>Other login options:</h2>
                                                                <%} else { %>
                                                                <script type="text/javascript">
                                                                    document.getElementById('local_auth_div').style.display = 'block';
                                                                </script>
                                                                <%} %>
                                                            </div>
                                                        </div>

                                                            <div class="row">

                                                                <%
                                                                    for (Map.Entry<String, String> idpEntry : idpAuthenticatorMapping.entrySet())  {
                                                                        if(!idpEntry.getKey().equals(IdentityApplicationConstants.RESIDENT_IDP_RESERVED_NAME)) {
                                                                            String idpName = idpEntry.getKey();
                                                                            boolean isHubIdp = false;
                                                                            if (idpName.endsWith(".hub")){
                                                                                isHubIdp = true;
                                                                                idpName = idpName.substring(0, idpName.length()-4);
                                                                            }
                                                                %>
                                                                <div class="span6">
                                                                    <% if (isHubIdp) { %>
                                                                    <a href="#"  class="main-link"><%=idpName%></a>
                                                                    <div class="slidePopper" style="display:none">
                                                                        <input type="text" id="domainName" name="domainName"/>
                                                                        <input type="button" class="btn btn-primary go-btn" onClick="javascript: myFunction('<%=idpName%>','<%=idpEntry.getValue()%>','domainName')" value="Go" />
                                                                    </div>
                                                                    <%}else{ %>
                                                                    <a onclick="javascript: handleNoDomain('<%=idpName%>','<%=idpEntry.getValue()%>')"  class="main-link truncate" style="cursor:pointer" title="<%=idpName%>"><%=idpName%></a>
                                                                    <%} %>
                                                                </div>
                                                                <%}else if(localAuthenticatorNames.size()>0 && localAuthenticatorNames.contains("IWAAuthenticator")) {
                                                                %>
                                                                <div class="span6">
                                                                    <a onclick="javascript: handleNoDomain('<%=idpEntry.getKey()%>','IWAAuthenticator')"  class="main-link" style="cursor:pointer">IWA</a>
                                                                </div>
                                                                <%
                                                                        }

                                                                    }%>



                                                            </div>
                                                            <% } %>
                                                            </div>
                                                </form>
                                            </div>



                                            <%--END new content--%>



                                        </div>

                                </div>

                            </div>
                        </div>


                    </div>


                </div>
            </div>
        <div id="push"></div>
    </div>


<!--
    <div id="footer" class="footer-main">
        <div class="container">
        <div class="row-fluid">
            <div class="span12">
                <ul class="help-links footer-left-most">
                    <li><h5><a href="" target="_blank">About Us</a></h5></li>
                    <li><h5><a href="" target="_blank">Our APIs</a></h5></li>
                </ul>
                <ul class="help-links">
                    <li><h5><a href="" target="_blank">Privacy Policy</a></h5></li>
                    <li><h5><a href="http://www.ideamart.lk/content/terms-conditions" target="_blank">Terms and Conditions</a></h5></li>
                    <li><h5><a href="" target="_blank">Useful Guidelines</a></h5></li>
                </ul>
                <ul class="help-links">
                    <li><h5>Social Networking</h5></li>
                    <li>
                        <h5>
                            <table class="share-pane">
                                <tr>
                                    <td><a class="facebook" target="_blank" rel="external nofollow" href="https://www.facebook.com/groups/ideamartlk/" title="Share with your friends on Facebook"></a></td>
                                    <td><a class="twitter" target="_blank" rel="external nofollow" href="" title="Twitter"></a></td>
                                    <td><a class="linkedin" target="_blank" rel="external nofollow" href="" title="Share this post on linkedin"></a></td>
                                </tr>
                            </table>
                        </h5>
                    </li>
                    <li><h5><a href="http://www.ideamart.lk/content/solutions" target="_blank">Applications for Use</a></h5></li>
                </ul>
                <ul class="help-links">
                    <li><h5><a href="http://www.ideamart.lk/content/contactus" target="_blank">Contact Us</a></h5></li>
                    <li><h5><a href="" target="_blank">Support</a></h5></li>
                </ul>
                <div style="clear:both"></div>
            </div>
        </div>
        </div>
    </div>
-->
<div id="footer" class="footer-main">
         <div class="row-fluid">
                   <div class="span12">
                           <div class="container-fluid">
		                      <div class="row-fluid">
				                   <div class="span3 footer-left-most">
				                          <ul class="help-links">
				                                <li><h5><a href="http://wso2telco.com" target="_blank">About Us</a></h5></li>
				                          </ul>
				                     </div>
				                     <div class="span9 help-and-links">

						                <div class="span3">
						                  <ul class="help-links">
						                        <li><h5><a href="http://wso2.com/privacy-policy" target="_blank">Privacy Policy</a></h5></li>
						                        <li><h5><a href="http://wso2.com/terms-of-use" target="_blank">Terms and Conditions</a></h5></li>

						                  </ul>
						                </div>
						                <div class="span3">
						                  <ul class="help-links">
						                        <li><h5>Social Networking</h5></li>
						                        <li>
						                        <h5>
						                        <table class="share-pane">
						                      <tr>
						                          <td><a class="facebook" target="_blank" rel="external nofollow" href="https://www.facebook.com/wso2telco" title="Share with your friends on Facebook"></a></td>
						                          <td><a class="twitter" target="_blank" rel="external nofollow" href="http://twitter.com/wso2telco" title="Twitter"></a></td>
						                          <td><a class="linkedin" target="_blank" rel="external nofollow" href="https://www.linkedin.com/groups/WSO2-Telco-8263390?gid=8263390&mostPopular=&trk=tyah&trkInfo=idx%3A1-1-1%2CtarId%3A1425100882984%2Ctas%3Awso2.telco" title="Share this post on linkedin" title="Share this post on linkedin"></a></td>
						                      </tr>
						                    </table>
						                   </li>
						                   </ul>
						                 </div>

						                 <div class="span3">
						                  <ul class="help-links">
						                        <li><h5><a href="http://wso2telco.com" target="_blank">Contact Us</a></h5></li>
						                        <li><h5><a href="http://wso2.com/support" target="_blank">Support</a></h5></li>
						                  </ul>
						                </div>
						    		<div  style="clear:both">
								</div>   <hr />  <a class="powered-by-logo pull-right" href="http://wso2telco.com" target="_blank"></a>

							</div>
		                            </div>
                                   </div>
                      </div>
          </div>
</div>

<script>
 	$(document).ready(function(){

  $('.main-link').click(function(){
			$('.main-link').next().hide();
			$(this).next().toggle('fast');
			var w = $(document).width();
			var h = $(document).height();
			$('.overlay').css("width",w+"px").css("height",h+"px").show();
		});
		$('.overlay').click(function(){$(this).hide();$('.main-link').next().hide();});

	});
        function myFunction(key, value, name)
        {
	    var object = document.getElementById(name);
	    var domain = object.value;


            if (domain != "")
            {
                document.location = "../../commonauth?idp=" + key + "&authenticator=" + value + "&sessionDataKey=<%=CharacterEncoder.getSafeText(request.getParameter("sessionDataKey"))%>&domain=" + domain;
            } else {
                document.location = "../../commonauth?idp=" + key + "&authenticator=" + value + "&sessionDataKey=<%=CharacterEncoder.getSafeText(request.getParameter("sessionDataKey"))%>";
            }
        }

        function handleNoDomain(key, value)
        {


          document.location = "../../commonauth?idp=" + key + "&authenticator=" + value + "&sessionDataKey=<%=CharacterEncoder.getSafeText(request.getParameter("sessionDataKey"))%>";

        }

        function saveLink(){
            var url = window.location.href;
            var date = new Date();
            var expiryMins = 5;
            date.setTime(date.getTime() + (expiryMins * 60 * 1000));
            var expires = "expires=" + date.toUTCString();
            document.cookie = "loginRequestURL=" + encodeURIComponent(url) + "; path=/; " + expires;
            return true;
        }


    </script>

    </body>
    </html>

</fmt:bundle>
