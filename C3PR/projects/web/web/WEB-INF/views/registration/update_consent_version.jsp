<%@ include file="taglibs.jsp"%>
<style type="text/css">
.updateConsentVersionClass div.row div.label {
	width:17em;
}
.updateConsentVersionClass div.row div.value {
	margin-left:18em;
}
</style>
<script>


function updateConsentVersion(id){
	$('consentVersionForm').submit();
}
</script>
<form:form id="consentVersionForm">
<chrome:box title="Reconsent" id="updateConsentVersionClass">
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentSignedDate"/></div>
		<div class="value">
			<input type="text" name="studySubject.informedConsentSignedDate" id="consentSignedDate" class="date validate-DATE&&notEmpty" />
            <a href="#" id="consentSignedDate-calbutton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
				<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/>
		</div>
	</div>
</chrome:box>
<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button type="button" color="green" icon="save" value="Save" onclick="updateConsentVersion();" />
	</span>
</div>
</form:form>
<script>
inputDateElementLocal1="consentSignedDate";
inputDateElementLink1="consentSignedDate-calbutton";
Calendar.setup(
{
    inputField  : inputDateElementLocal1,         // ID of the input field
    ifFormat    : "%m/%d/%Y",    // the date format
    button      : inputDateElementLink1       // ID of the button
}
);
</script>