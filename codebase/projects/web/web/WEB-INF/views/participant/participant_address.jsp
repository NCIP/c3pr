<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Address of <participanttags:htmlTitle subject="${command}" /></title>
<style>
#workflow-tabs {
top:83px;
}
</style>
</head>
<body>
<tags:tabForm tab="${tab}" flow="${flow}"
	formName="participantAddressForm">    
	<jsp:attribute name="singleFields">
    <tags:instructions code="participant_address" />
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<p id="address"><h2><fmt:message key="c3pr.common.address" /></h2><br></p>
	<div>
		<div class="row">
			<div class="label"><em></em> <b><fmt:message key="c3pr.common.streetAddress"/></b>&nbsp;</div>
			<div class="value"><form:input size="58"
				path="address.streetAddress" />&nbsp;&nbsp;&nbsp;</div>
		</div>
		<div class="row">
			<div class="label"><em></em> <b><fmt:message key="c3pr.common.city"/></b>&nbsp;</div>
			<div class="value"><form:input path="address.city" />&nbsp;&nbsp;&nbsp;</div>
		</div>
		<div class="row">
			<div class="label"><span class="data"><em></em> <b><fmt:message key="c3pr.common.state"/></b>&nbsp;</span></div>
			<div class="value"><form:input path="address.stateCode" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<b><fmt:message key="c3pr.common.zip"/></b>&nbsp;<form:input path="address.postalCode" id="postalCode" cssClass="validate-ZIPCODE" /></div>
		</div>
        
        <div class="row">
			<div class="label"><em></em><em></em> <b><fmt:message key="c3pr.common.country"/></b>&nbsp;</div>
			<div class="value"><form:input path="address.countryCode" />&nbsp;&nbsp;&nbsp;</div>
		</div>
	</div>
	<hr align="left" width="95%">
	<div>
		<div class="row">
			<p id="contactInfo"><h2><fmt:message key="c3pr.common.contactInformation" /></h2><br>
			</p>
		<div class="row">
			<div class="label"><em></em><em></em> <b>${command.contactMechanisms[0].type.displayName }</b>&nbsp;</div>
			<div class="value"><form:input path="contactMechanisms[0].value"  cssClass="validate-EMAIL" size="30" /><tags:hoverHint keyProp="contactMechanism.email"/></div>
		</div>
		<div class="row">
			<div class="label"><em></em><em></em> <b>${command.contactMechanisms[1].type.displayName }</b>&nbsp;</div>
			<div class="value"><form:input path="contactMechanisms[1].value"  cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.phone"/> e.g. 7035600296 or 703-560-0296</div>
		</div>
		<div class="row">
			<div class="label"><em></em><em></em> <b>${command.contactMechanisms[2].type.displayName }</b>&nbsp;</div>
			<div class="value"><form:input path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO"/><tags:hoverHint keyProp="contactMechanism.fax"/> e.g. 7035600296 or 703-560-0296</div>
		</div>
	
	</div>
	</jsp:attribute>
</tags:tabForm>

</body>
</html>
