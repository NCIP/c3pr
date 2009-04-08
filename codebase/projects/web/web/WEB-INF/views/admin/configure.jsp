<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>Configure application</title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
    <style type="text/css">
    	.updated {
            border: #494 solid;
            border-width: 1px 0;
            background-color: #8C8;
            padding: 1em 2em;
            text-align: center;
            margin: 1em 30%;
            color: #fff;
            font-weight: bold;
            font-size: 1.1em;
        }
        p.description {
            margin: 0.25em 0 0 1em;
        }
        div.submit {
            text-align: right;
        }
        .value input[type=text] {
            width: 50%;	
        }
        
        div.row div.label{
        	margin-right: 0.5em;
        	width: 18em;
        }
    </style>
    <script type="text/javascript">
    function manageCCTSConfiguration(box) {
        if (box.value == 'true') {
            Effect.OpenUp('cctsConfig');
        	document.getElementById("conf[smokeTestURL].value").className="validate-notEmpty&&URL";
            document.getElementById("conf[c3dViewerBaseUrl].value").className="validate-notEmpty&&URL";
            document.getElementById("conf[caaersBaseUrl].value").className="validate-notEmpty&&URL";
            document.getElementById("conf[pscBaseUrl].value").className="validate-notEmpty&&URL";
            document.getElementById("conf[esbUrl].value").className="validate-notEmpty&&URL";
            document.getElementById("conf[c3d_window_name].value").className="validate-notEmpty";
            document.getElementById("caaers_window_name").className="validate-notEmpty";
            document.getElementById("psc_window_name").className="validate-notEmpty";
            document.getElementById("conf[esb.timeout].value").className="validate-notEmpty&&NUMERIC";		
        }
        if (box.value == 'false') {
            Effect.CloseDown('cctsConfig');
            document.getElementById("conf[smokeTestURL].value").className="";
            document.getElementById("conf[c3dViewerBaseUrl].value").className="";
            document.getElementById("conf[caaersBaseUrl].value").className="";
            document.getElementById("conf[pscBaseUrl].value").className="";
            document.getElementById("conf[esbUrl].value").className="";
            document.getElementById("conf[c3d_window_name].value").className="";
            document.getElementById("caaers_window_name").className="";
            document.getElementById("psc_window_name").className="";
            document.getElementById("conf[esb.timeout].value").className="";
        }
    }
	
    function manageAuthenticationSwitch(box){
    	 if (box.value == 'true') {
            Effect.OpenUp('authenticationConfig');
    	 }else if (box.value == 'false') {
    		Effect.CloseDown('authenticationConfig');
    		document.getElementById("conf[authenticationMode].value").value="local";
       	}
    }

    function manageAuthenticationMode(box){
    }

    var localNCIInstituteCodeAutocompleterProps = {
            basename: "localNCIInstituteCode",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	return obj.name + "("+(obj.nciInstituteCode)+")" ;
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=localNCIInstituteCodeAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.nciInstituteCode;
			}
		}
    AutocompleterManager.addAutocompleter(localNCIInstituteCodeAutocompleterProps);

    function testConnectivity(id, type){
    	var url ;
    	$('testIndicator['+id+']').style.display = "";
		if(id != 'testEmail'){
			var value = $(id).value ;
			if(value == ''){
				alert("Enter Data");
				Element.hide('testIndicator['+id+']');
				return;
			}
			url = "../admin/validateConnectivity?type="+type+"&value="+escape(value)+"&id="+escape(id);
			new Ajax.Updater('connectionTestResult['+id+']',url, {evalScripts: true});
		}else{
			var server = $('outgoingMailServer').value ;
			var port = $('outgoingMailServerPort').value ;
			var username = $('outgoingMailUsername').value  ;
			var passwd = $('outgoingMailPassword').value  ;
			var auth = $('outgoingMailAuth').value  ;
			var protocol = $('smtpProtocol').value  ;
			var sslAuth = $('smtpSSLAuth').value  ;
			var fromAddress = $('outgoingMailFromAddress').value  ;

			if(server == '' || port == '' || auth == '' || protocol =='' || sslAuth == '' || fromAddress == ''){
				alert("Enter Data");
				Element.hide('testIndicator['+id+']');
				return;	
			}
			url = "../admin/validateConnectivity?type="+type+"&id="+escape(id)+"&server="+escape(server)+"&port="+port+"&username="+escape(username)+"&passwd="+escape(passwd)+"&protocol="+protocol+"&auth="+auth+"&sslAuth="+sslAuth+"&fromAddress="+escape(fromAddress);
			new Ajax.Updater('connectionTestResult['+id+']',url, {evalScripts: true, method: 'post'});	
		}
	}
	
	function showErrorTrace(errorTrace) {
		var win = new Window( {
			className :"mac_os_x",
			width :250,
			height :200,
			zIndex :100,
			resizable :true,
			title :"Error Trace",
			draggable :true,
			wiredDrag :true
		});
		win.getContent().innerHTML = errorTrace ;
		win.showCenter(true);
	}
	
    </script>
</head>
<body>
    <form:form action="${action}" cssClass="standard">
    <chrome:box title="Configure C3PR" autopad="true">
    <c:url value="/pages/admin/configure" var="action"/>
    <tags:errors path="*"/>
    <tags:instructions code="configure" />
    	<chrome:division title="Application Configuration">
    		<div class="row">
        		<div class="label"><fmt:message key="configure.app.localSiteNCICode"/><tags:hoverHint keyProp="configure.localNciInstituteCode" /></div>
        		<div class="value">
        			<tags:autocompleter name="conf[localNciInstituteCode].value" displayValue="${command.conf['localNciInstituteCode'].value}" value="${command.conf['localNciInstituteCode'].value}" basename="localNCIInstituteCode" cssClass="validate-notEmpty" ></tags:autocompleter>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.app.siteBanner"/><tags:hoverHint keyProp="configure.siteName" /></div>
        		<div class="value"><form:input path="conf[siteName].value" id="siteName" cssClass="validate-notEmpty"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.app.notificationLinkBack"/><tags:hoverHint keyProp="notification.linkBack" /></div>
        		<div class="value">
        			<form:select path="conf[notification.link_back].value" id="notification.link_back" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
    	</chrome:division>
    	<chrome:division title="Authentication Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.enable"/><tags:hoverHint keyProp="configure.authorizationSwitch"/></div>
        		<div class="value">
        			<form:select path="conf[authorizationSwitch].value" id="authorizationSwitch" onchange="manageAuthenticationSwitch(this);">
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div id="authenticationConfig">
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.auth.mode"/><tags:hoverHint keyProp="configure.authenticationMode"/></div>
	        		<div class="value">
	        			<form:select path="conf[authenticationMode].value" id="authenticationMode" onchange="manageAuthenticationMode(this);">
	        				<form:option value="local">Local</form:option>
	        				<form:option value="cas">CAS</form:option>
	        				<form:option value="webSSO">WebSSO</form:option>
	        			</form:select>
	        		</div>
	        	</div>
	        	<div id="c3prurl">
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.c3pr.url"/><tags:hoverHint keyProp="configure.c3pr.webapp.url" /></div>
		        		<div class="value">
		        			<form:input path="conf[c3pr.webapp.url].value" id="c3pr.webapp.url" cssClass="validate-URL"/>
		        			<tags:button type="button" onclick="testConnectivity('c3pr.webapp.url', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[c3pr.webapp.url]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[c3pr.webapp.url]" ></span>
		        		</div>
		        	</div>
	        	</div>
	        	<div id="casAuthConfig">
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.cas.baseurl"/><tags:hoverHint keyProp="configure.cas.base_url" /></div>
		        		<div class="value">
		        			<form:input path="conf[cas.base_url].value" id="cas.base_url" cssClass="validate-URL"/>
		        			<tags:button type="button" onclick="testConnectivity('cas.base_url', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[cas.base_url]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[cas.base_url]" ></span>
		        		</div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.cas.certfile"/><tags:hoverHint keyProp="configure.cas.cert_file" /></div>
		        		<div class="value">
		        			<form:input path="conf[cas.cert_file].value" id="cas.cert_file" />
		        			<tags:button type="button" onclick="testConnectivity('cas.cert_file', 'testFileLocation');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[cas.cert_file]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[cas.cert_file]" ></span>
		        		</div>
		        	</div>
	        	</div>
	        	<div id="webssoAuthConfig">
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.websso.baseurl"/><tags:hoverHint keyProp="configure.ccts.websso.base_url" /></div>
		        		<div class="value">
		        			<form:input path="conf[ccts.websso.base_url].value" id="ccts.websso.base_url" cssClass="validate-URL"/>
		        			<tags:button type="button" onclick="testConnectivity('ccts.websso.base_url', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[ccts.websso.base_url]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[ccts.websso.base_url]" ></span>
		        		</div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.websso.certfile"/><tags:hoverHint keyProp="configure.ccts.websso.cert_file" /></div>
		        		<div class="value">
		        			<form:input path="conf[ccts.websso.cert_file].value" id="ccts.websso.cert_file" />
		        			<tags:button type="button" onclick="testConnectivity('ccts.websso.cert_file', 'testFileLocation');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[ccts.websso.cert_file]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[ccts.websso.cert_file]" ></span>
		        		</div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.ccts.host.certfile"/><tags:hoverHint keyProp="configure.hostCertificate" /></div>
		        		<div class="value">
		        			<form:input path="conf[hostCertificate].value" id="hostCertificate"/>
		        			<tags:button type="button" onclick="testConnectivity('hostCertificate', 'testFileLocation');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[hostCertificate]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[hostCertificate]" ></span>
		        		</div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.ccts.host.key"/><tags:hoverHint keyProp="configure.hostKey" /></div>
		        		<div class="value">
		        			<form:input path="conf[hostKey].value" id="hostKey" />
		        			<tags:button type="button" onclick="testConnectivity('hostKey', 'testFileLocation');" color="blue" value="Test" icon="check" size="small"/>
		        			<img id="testIndicator[hostKey]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
		        			<span id="connectionTestResult[hostKey]" ></span>
		        		</div>
		        	</div>
	        	</div>
        	</div>
        </chrome:division>
        <chrome:division title="SMTP Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.server"/><tags:hoverHint keyProp="configure.outgoingMailServer" /></div>
        		<div class="value"><form:input path="conf[outgoingMailServer].value" id="outgoingMailServer" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.port"/><tags:hoverHint keyProp="configure.outgoingMailServerPort" /></div>
        		<div class="value"><form:input path="conf[outgoingMailServerPort].value" id="outgoingMailServerPort" cssClass="validate-NUMERIC" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.username"/><tags:hoverHint keyProp="configure.outgoingMailUsername" /></div>
        		<div class="value"><form:input path="conf[outgoingMailUsername].value" id="outgoingMailUsername" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.password"/><tags:hoverHint keyProp="configure.outgoingMailPassword" /></div>
        		<div class="value"><form:input path="conf[outgoingMailPassword].value" id="outgoingMailPassword" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.protocol"/><tags:hoverHint keyProp="configure.smtpProtocol" /></div>
        		<div class="value">
        			<form:select path="conf[smtpProtocol].value" id="smtpProtocol" >
        				<form:option value="smtps">SMTPS</form:option>
        				<form:option value="smtp">SMTP</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.auth"/><tags:hoverHint keyProp="configure.outgoingMailAuth" /></div>
        		<div class="value">
        			<form:select path="conf[outgoingMailAuth].value" id="outgoingMailAuth" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
				</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.sslAuth"/><tags:hoverHint keyProp="configure.smtpSSLAuth" /></div>
        		<div class="value">
        			<form:select path="conf[smtpSSLAuth].value" id="smtpSSLAuth" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.address"/><tags:hoverHint keyProp="configure.outgoingMailFromAddress" /></div>
        		<div class="value"><form:input path="conf[outgoingMailFromAddress].value" id="outgoingMailFromAddress" cssClass="validate-Email" /></div>
        	</div>
        	<br>
        	<div class="row" id="testEmail">
        		<div class="value"><tags:button type="button" onclick="testConnectivity('testEmail', 'testEmailServer');" color="blue" value="Send test email" icon="mail" size="small"/>
        		<img id="testIndicator[testEmail]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
        		<span id="connectionTestResult[testEmail]" ></span>
        		</div>
        	</div>
        </chrome:division>
		<chrome:division title="Multisite Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.enable"/><tags:hoverHint keyProp="configure.multisiteEnable" /></div>
        		<div class="value">
        			<form:select path="conf[multisiteEnable].value" id="multisiteEnable" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.ifs_url"/></div>
        		<div class="value"><form:input path="conf[ifs.url].value" id="ifs.url" cssClass="validate-URL"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.idp_url"/></div>
        		<div class="value"><form:input path="conf[idp.url].value" id="idp.url" cssClass="validate-URL"/></div>
        	</div>
        </chrome:division>
        
        <chrome:division title="CCTS Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.ccts.enable.esb"/><tags:hoverHint keyProp="configure.esbEnable" /></div>
        		<div class="value">
        			<form:select path="conf[esbEnable].value" id="esbEnable" onchange="manageCCTSConfiguration(this);">
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div id="cctsConfig">
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.smoketest.serviceurl"/><tags:hoverHint keyProp="configure.smokeTestURL" /></div>
	        		<div class="value">
	        			<form:input path="conf[smokeTestURL].value" id="smokeTestURL" />
	        			<tags:button type="button" onclick="testConnectivity('smokeTestURL', 'testSmokeTestURL');" color="blue" value="Test" icon="check" size="small"/>
	        			<img id="testIndicator[smokeTestURL]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
	        			<span id="connectionTestResult[smokeTestURL]" ></span>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.c3d.hotlinkurl"/><tags:hoverHint keyProp="configure.c3dViewerBaseUrl"/></div>
	        		<div class="value">
	        			<form:input path="conf[c3dViewerBaseUrl].value" id="c3dViewerBaseUrl" />
	        			<tags:button type="button" onclick="testConnectivity('c3dViewerBaseUrl', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
	        			<img id="testIndicator[c3dViewerBaseUrl]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
	        			<span id="connectionTestResult[c3dViewerBaseUrl]" ></span>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.c3d.browser.window"/><tags:hoverHint keyProp="configure.c3d_window_name"/></div>
	        		<div class="value"><form:input path="conf[c3d_window_name].value" id="c3d_window_name" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caaers.hotlinkurl"/><tags:hoverHint keyProp="configure.caaersBaseUrl" /></div>
	        		<div class="value"><form:input path="conf[caaersBaseUrl].value" id="caaersBaseUrl"/>
	        			<tags:button type="button" onclick="testConnectivity('caaersBaseUrl', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
	        			<img id="testIndicator[caaersBaseUrl]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
	        			<span id="connectionTestResult[caaersBaseUrl]" ></span>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caaers.browser.window"/><tags:hoverHint keyProp="configure.caaers_window_name" /></div>
	        		<div class="value"><form:input path="conf[caaers_window_name].value" id="caaers_window_name" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.psc.hotlinkurl"/><tags:hoverHint keyProp="configure.pscBaseUrl" /></div>
	        		<div class="value"><form:input path="conf[pscBaseUrl].value" id="pscBaseUrl" />
	        			<tags:button type="button" onclick="testConnectivity('pscBaseUrl', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
	        			<img id="testIndicator[pscViewerBaseUrl]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
	        			<span id="connectionTestResult[pscBaseUrl]" ></span>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.psc.browser.window"/><tags:hoverHint keyProp="configure.psc_window_name" /></div>
	        		<div class="value"><form:input path="conf[psc_window_name].value" id="psc_window_name" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.esb.url"/><tags:hoverHint keyProp="configure.esbUrl" /></div>
	        		<div class="value">
	        			<form:input path="conf[esbUrl].value" id="esbUrl"/>
	        			<tags:button type="button" onclick="testConnectivity('esbUrl', 'testURL');" color="blue" value="Test" icon="check" size="small"/>
	        			<img id="testIndicator[esbUrl]" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
	        			<span id="connectionTestResult[esbUrl]" ></span>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caxchange.timeout"/><tags:hoverHint keyProp="esb.timeout" /></div>
	        		<div class="value"><form:input path="conf[esb.timeout].value" id="esb.timeout" /></div>
	        	</div>
        	</div>
        </chrome:division>
		<chrome:division title="COPPA Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.coppa.enable"/><tags:hoverHint keyProp="configure.coppaEnable" /></div>
        		<div class="value">
        			<form:select path="conf[coppaEnable].value" id="coppaEnable" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        </chrome:division>
        </chrome:box>
         <div class="row submit">
            <tags:button type="submit" color="green" value="Save" icon="save"/>
        </div>
    </form:form>
    
</body>
</html>