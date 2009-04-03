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
	function sendTestEmail(){
		
	}

    
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
    </script>
</head>
<body>
    <form:form action="${action}" cssClass="standard">
    <c:if test="${param.updated}">
        <p class="updated">Settings saved</p>
    </c:if>
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
        		<div class="value"><form:input path="conf[siteName].value" id="conf[siteName].value" cssClass="validate-notEmpty"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.app.notificationLinkBack"/><tags:hoverHint keyProp="notification.linkBack" /></div>
        		<div class="value">
        			<form:select path="conf[notification.link_back].value" id="conf[notification.link_back].value" >
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
        			<form:select path="conf[authorizationSwitch].value" id="conf[authorizationSwitch].value" onchange="manageAuthenticationSwitch(this);">
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div id="authenticationConfig">
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.auth.mode"/><tags:hoverHint keyProp="configure.authenticationMode"/></div>
	        		<div class="value">
	        			<form:select path="conf[authenticationMode].value" id="conf[authenticationMode].value" onchange="manageAuthenticationMode(this);">
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
		        			<form:input path="conf[c3pr.webapp.url].value" id="conf[c3pr.webapp.url].value" cssClass="validate-URL"/>
		        				<tags:button onclick="testConnection('conf[c3pr.webapp.url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
		        		</div>
		        	</div>
	        	</div>
	        	<div id="casAuthConfig">
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.cas.baseurl"/><tags:hoverHint keyProp="configure.cas.base_url" /></div>
		        		<div class="value"><form:input path="conf[cas.base_url].value" id="conf[cas.base_url].value" cssClass="validate-URL"/>
		        			<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
		        		</div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.cas.certfile"/><tags:hoverHint keyProp="configure.cas.cert_file" /></div>
		        		<div class="value"><form:input path="conf[cas.cert_file].value" id="conf[cas.cert_file].value" /></div>
		        	</div>
	        	</div>
	        	<div id="webssoAuthConfig">
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.websso.baseurl"/><tags:hoverHint keyProp="configure.ccts.websso.base_url" /></div>
		        		<div class="value"><form:input path="conf[ccts.websso.base_url].value" id="conf[ccts.websso.base_url].value" cssClass="validate-URL"/>
		        			<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
		        		</div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.auth.websso.certfile"/><tags:hoverHint keyProp="configure.ccts.websso.cert_file" /></div>
		        		<div class="value"><form:input path="conf[ccts.websso.cert_file].value" id="conf[ccts.websso.cert_file].value" /></div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.ccts.host.certfile"/><tags:hoverHint keyProp="configure.hostCertificate" /></div>
		        		<div class="value"><form:input path="conf[hostCertificate].value" id="conf[hostCertificate].value" cssClass="validate-URL"/></div>
		        	</div>
		        	<div class="row">
		        		<div class="label"><fmt:message key="configure.ccts.host.key"/><tags:hoverHint keyProp="configure.hostKey" /></div>
		        		<div class="value"><form:input path="conf[hostKey].value" id="conf[hostKey].value" cssClass="validate-URL"/></div>
		        	</div>
	        	</div>
        	</div>
        </chrome:division>
        <chrome:division title="SMTP Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.server"/><tags:hoverHint keyProp="configure.outgoingMailServer" /></div>
        		<div class="value"><form:input path="conf[outgoingMailServer].value" id="conf[outgoingMailServer].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.port"/><tags:hoverHint keyProp="configure.outgoingMailServerPort" /></div>
        		<div class="value"><form:input path="conf[outgoingMailServerPort].value" id="conf[outgoingMailServerPort].value" cssClass="validate-NUMERIC" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.username"/><tags:hoverHint keyProp="configure.outgoingMailUsername" /></div>
        		<div class="value"><form:input path="conf[outgoingMailUsername].value" id="conf[outgoingMailUsername].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.password"/><tags:hoverHint keyProp="configure.outgoingMailPassword" /></div>
        		<div class="value"><form:input path="conf[outgoingMailPassword].value" id="conf[outgoingMailPassword].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.protocol"/><tags:hoverHint keyProp="configure.smtpProtocol" /></div>
        		<div class="value">
        			<form:select path="conf[smtpProtocol].value" id="conf[smtpProtocol].value" >
        				<form:option value="smtps">SMTPS</form:option>
        				<form:option value="smtp">SMTP</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.auth"/><tags:hoverHint keyProp="configure.outgoingMailAuth" /></div>
        		<div class="value">
        			<form:select path="conf[outgoingMailAuth].value" id="conf[outgoingMailAuth].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
				</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.sslAuth"/><tags:hoverHint keyProp="configure.smtpSSLAuth" /></div>
        		<div class="value">
        			<form:select path="conf[smtpSSLAuth].value" id="conf[smtpSSLAuth].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.address"/><tags:hoverHint keyProp="configure.outgoingMailFromAddress" /></div>
        		<div class="value"><form:input path="conf[outgoingMailFromAddress].value" id="conf[outgoingMailFromAddress].value" cssClass="validate-Email" /></div>
        	</div>
        	<br>
        	<div class="row">
        		<div class="value"><tags:button onclick="sendTestEmail();" color="blue" value="Send test email" icon="mail" size="small"/></div>
        	</div>
        </chrome:division>
		
		<chrome:division title="Multisite Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.enable"/><tags:hoverHint keyProp="configure.multisiteEnable" /></div>
        		<div class="value">
        			<form:select path="conf[multisiteEnable].value" id="conf[multisiteEnable].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.ifs_url"/></div>
        		<div class="value"><form:input path="conf[ifs.url].value" id="conf[ifs.url].value" cssClass="validate-URL"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.idp_url"/></div>
        		<div class="value"><form:input path="conf[idp.url].value" id="conf[idp.url].value" cssClass="validate-URL"/></div>
        	</div>
        </chrome:division>
        
        <chrome:division title="CCTS Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.ccts.enable.esb"/><tags:hoverHint keyProp="configure.esbEnable" /></div>
        		<div class="value">
        			<form:select path="conf[esbEnable].value" id="conf[esbEnable].value" onchange="manageCCTSConfiguration(this);">
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div id="cctsConfig">
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.smoketest.serviceurl"/><tags:hoverHint keyProp="configure.smokeTestURL" /></div>
	        		<div class="value"><form:input path="conf[smokeTestURL].value" id="conf[smokeTestURL].value" />
	        		<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.c3d.hotlinkurl"/><tags:hoverHint keyProp="configure.c3dViewerBaseUrl"/></div>
	        		<div class="value"><form:input path="conf[c3dViewerBaseUrl].value" id="conf[c3dViewerBaseUrl].value" />
	        			<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.c3d.browser.window"/><tags:hoverHint keyProp="configure.c3d_window_name"/></div>
	        		<div class="value"><form:input path="conf[c3d_window_name].value" id="conf[c3d_window_name].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caaers.hotlinkurl"/><tags:hoverHint keyProp="configure.caaersBaseUrl" /></div>
	        		<div class="value"><form:input path="conf[caaersBaseUrl].value" id="conf[caaersBaseUrl].value"/>
	        			<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caaers.browser.window"/><tags:hoverHint keyProp="configure.caaers_window_name" /></div>
	        		<div class="value"><form:input path="conf[caaers_window_name].value" id="conf[caaers_window_name].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.psc.hotlinkurl"/><tags:hoverHint keyProp="configure.pscBaseUrl" /></div>
	        		<div class="value"><form:input path="conf[pscBaseUrl].value" id="conf[pscBaseUrl].value" />
	        			<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.psc.browser.window"/><tags:hoverHint keyProp="configure.psc_window_name" /></div>
	        		<div class="value"><form:input path="conf[psc_window_name].value" id="conf[psc_window_name].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.esb.url"/><tags:hoverHint keyProp="configure.esbUrl" /></div>
	        		<div class="value"><form:input path="conf[esbUrl].value" id="conf[esbUrl].value"/>
	        		<tags:button onclick="testConnection('conf[cas.base_url].value');" color="blue" value="Test connection" icon="connectivity" size="small"/>
	        		</div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caxchange.timeout"/><tags:hoverHint keyProp="esb.timeout" /></div>
	        		<div class="value"><form:input path="conf[esb.timeout].value" id="conf[esb.timeout].value" /></div>
	        	</div>
        	</div>
        </chrome:division>
		<chrome:division title="COPPA Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.coppa.enable"/><tags:hoverHint keyProp="configure.coppaEnable" /></div>
        		<div class="value">
        			<form:select path="conf[coppaEnable].value" id="conf[coppaEnable].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        </chrome:division>
        </chrome:box>
         <div class="row submit">
            <tags:button type="submit" color="green" value="Save" icon="connectivity"/>
        </div>
    </form:form>
    
</body>
</html>