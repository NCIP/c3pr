<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title>Configure application</title>
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
        	width: 15em;
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
        		<div class="label"><fmt:message key="configure.app.localSiteNCICode"/></div>
        		<div class="value"><form:input path="conf[localNciInstituteCode].value" id="conf[localNciInstituteCode].value" cssClass="validate-notEmpty"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.app.siteBanner"/></div>
        		<div class="value"><form:input path="conf[siteName].value" id="conf[siteName].value" cssClass="validate-notEmpty"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.app.notificationLinkBack"/></div>
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
        		<div class="label"><fmt:message key="configure.auth.enable"/></div>
        		<div class="value">
        			<form:select path="conf[authorizationSwitch].value" id="conf[authorizationSwitch].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.mode"/><tags:hoverHint keyProp="study.shortTitleText"/></div>
        		<div class="value">
        			<form:select path="conf[authenticationMode].value" id="conf[authenticationMode].value" >
        				<form:option value="local">Local</form:option>
        				<form:option value="cas">CAS</form:option>
        				<form:option value="webSSO">WebSSO</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.c3pr.url"/></div>
        		<div class="value"><form:input path="conf[c3pr.webapp.url].value" id="conf[c3pr.webapp.url].value" cssClass="validate-URL"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.cas.baseurl"/></div>
        		<div class="value"><form:input path="conf[cas.base_url].value" id="conf[cas.base_url].value" cssClass="validate-URL"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.cas.certfile"/></div>
        		<div class="value"><form:input path="conf[cas.cert_file].value" id="conf[cas.cert_file].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.websso.baseurl"/></div>
        		<div class="value"><form:input path="conf[ccts.websso.base_url].value" id="conf[ccts.websso.base_url].value" cssClass="validate-URL"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.auth.websso.certfile"/></div>
        		<div class="value"><form:input path="conf[ccts.websso.cert_file].value" id="conf[ccts.websso.cert_file].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.ccts.host.certfile"/></div>
        		<div class="value"><form:input path="conf[hostCertificate].value" id="conf[hostCertificate].value" cssClass="validate-URL"/></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.ccts.host.key"/></div>
        		<div class="value"><form:input path="conf[hostKey].value" id="conf[hostKey].value" cssClass="validate-URL"/></div>
        	</div>
        </chrome:division>
        <chrome:division title="SMTP Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.server"/></div>
        		<div class="value"><form:input path="conf[outgoingMailServer].value" id="conf[outgoingMailServer].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.port"/></div>
        		<div class="value"><form:input path="conf[outgoingMailServerPort].value" id="conf[outgoingMailServerPort].value" cssClass="validate-NUMERIC" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.username"/></div>
        		<div class="value"><form:input path="conf[outgoingMailUsername].value" id="conf[outgoingMailUsername].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.password"/></div>
        		<div class="value"><form:input path="conf[outgoingMailPassword].value" id="conf[outgoingMailPassword].value" /></div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.protocol"/></div>
        		<div class="value">
        			<form:select path="conf[smtpProtocol].value" id="conf[smtpProtocol].value" >
        				<form:option value="smtps">SMTPS</form:option>
        				<form:option value="smtp">SMTP</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.auth"/></div>
        		<div class="value">
        			<form:select path="conf[outgoingMailAuth].value" id="conf[outgoingMailAuth].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
				</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.sslAuth"/></div>
        		<div class="value">
        			<form:select path="conf[smtpSSLAuth].value" id="conf[smtpSSLAuth].value" >
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div class="row">
        		<div class="label"><fmt:message key="configure.smtp.address"/></div>
        		<div class="value"><form:input path="conf[outgoingMailFromAddress].value" id="conf[outgoingMailFromAddress].value" cssClass="validate-Email" /></div>
        	</div>
        </chrome:division>
		
		<chrome:division title="Multisite Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.multisite.enable"/></div>
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
        		<div class="label"><fmt:message key="configure.ccts.enable.esb"/></div>
        		<div class="value">
        			<form:select path="conf[esbEnable].value" id="conf[esbEnable].value" onchange="manageCCTSConfiguration(this);">
        				<form:option value="true">Yes</form:option>
        				<form:option value="false">No</form:option>
        			</form:select>
        		</div>
        	</div>
        	<div id="cctsConfig">
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.smoketest.serviceurl"/></div>
	        		<div class="value"><form:input path="conf[smokeTestURL].value" id="conf[smokeTestURL].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.c3d.hotlinkurl"/></div>
	        		<div class="value"><form:input path="conf[c3dViewerBaseUrl].value" id="conf[c3dViewerBaseUrl].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.c3d.browser.window"/></div>
	        		<div class="value"><form:input path="conf[c3d_window_name].value" id="conf[c3d_window_name].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caaers.hotlinkurl"/></div>
	        		<div class="value"><form:input path="conf[caaersBaseUrl].value" id="conf[caaersBaseUrl].value"/></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caaers.browser.window"/></div>
	        		<div class="value"><form:input path="conf[caaers_window_name].value" id="conf[caaers_window_name].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.psc.hotlinkurl"/></div>
	        		<div class="value"><form:input path="conf[pscBaseUrl].value" id="conf[pscBaseUrl].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.psc.browser.window"/></div>
	        		<div class="value"><form:input path="conf[psc_window_name].value" id="conf[psc_window_name].value" /></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.esb.url"/></div>
	        		<div class="value"><form:input path="conf[esbUrl].value" id="conf[esbUrl].value"/></div>
	        	</div>
	        	<div class="row">
	        		<div class="label"><fmt:message key="configure.ccts.caxchange.timeout"/></div>
	        		<div class="value"><form:input path="conf[esb.timeout].value" id="conf[esb.timeout].value" /></div>
	        	</div>
        	</div>
        </chrome:division>
		<chrome:division title="COPPA Configuration">
        	<div class="row">
        		<div class="label"><fmt:message key="configure.coppa.enable"/></div>
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
            <tags:button type="submit" color="green" value="Save" icon="save"/>
        </div>
    </form:form>
    
</body>
</html>