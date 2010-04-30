<%@ include file="taglibs.jsp"%>

<html>
<head>
<script>
function handleSaveSubjectDetailsAndReturnToRegistration(){
	$('participantId').value='${command.id}';
	$('goToRegistration').value="true";
	$('participantAddressForm').submit();
}
</script>
    <title>Address of <participanttags:htmlTitle subject="${command}" /></title>
</head>
<body>
<form:form id="participantAddressForm" name="participantAddressForm">   
    <input type="hidden" name="goToRegistration" id="goToRegistration" value="false"/>
	<input type="hidden" name="participantId" id="participantId" value=""/>
	<chrome:box title="Address">
	<input type="hidden" id="currentPage" name="_page${tab.number}"/>
	<input type="hidden" id="_finish" name="_finish"/> 
    <tags:instructions code="participant_address" />
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<chrome:division title="Address">
		<div class="row">
			<div class="label"><em></em> <b><fmt:message key="c3pr.common.streetAddress"/></b>&nbsp;</div>
			<div class="value"><form:input size="58"
				path="address.streetAddress" /></div>
		</div>
		<div class="row">
			<div class="label"><em></em> <b><fmt:message key="c3pr.common.city"/></b>&nbsp;</div>
			<div class="value"><form:input path="address.city" /></div>
		</div>
		<div class="row">
			<div class="label"><span class="data"><em></em> <b><fmt:message key="c3pr.common.state"/></b>&nbsp;</span></div>
			<div class="value"><form:input path="address.stateCode" /></div>
		</div>
		<div class="row">
			<div class="label"><em></em> <b><fmt:message key="c3pr.common.zip"/></b>&nbsp;</div>
			<div class="value"><form:input path="address.postalCode" id="postalCode" cssClass="validate-ZIPCODE" /></div>
		</div>
        
        <div class="row">
			<div class="label"><em></em><em></em> <b><fmt:message key="c3pr.common.country"/></b>&nbsp;</div>
			<div class="value"><form:input path="address.countryCode" /></div>
		</div>
	</chrome:division>
	<chrome:division title="Contact Information">
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.email" />&nbsp;</div>
			<div class="value"><form:input path="email"  cssClass="validate-EMAIL" size="30" /><tags:hoverHint keyProp="contactMechanism.email"/></div>
		</div>
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.phone" />&nbsp;</div>
			<div class="value"><form:input path="phone"  cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.phone"/></div>
		</div>
		<div class="row">
			<div class="label"><fmt:message key="c3pr.common.fax" />&nbsp;</div>
			<div class="value"><form:input path="fax" cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.fax"/></div>
		</div>
	</chrome:division>
	<div class="content buttons autoclear"><div class="flow-buttons"><span class="next">
		<tags:button type="submit" color="green" id="flow-next"  value="Save & Done" icon="save"/>
	</span></div></div>
	</chrome:box>
	
<div align="right">
		<c:if test="${!empty fromCreateRegistration}">
			<tags:button type="button" color="blue" icon="back" value="Return to Registration" onclick="handleSaveSubjectDetailsAndReturnToRegistration()" />
		</c:if>
</div>
</form:form>

</body>
</html>
