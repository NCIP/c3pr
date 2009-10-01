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
	</jsp:attribute>
</tags:tabForm>

</body>
</html>
