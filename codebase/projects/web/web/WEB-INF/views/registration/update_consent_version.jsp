<%@ include file="taglibs.jsp"%>
<script>

function setVersion(box){
	cv = $('consentVersion');
	icv = document.getElementById('studySubject.informedConsentVersion');
	if (box.checked) {
		icv.value=cv.value;       
    }else {
    	icv.value="";             
    }       
}

function updateConsentVersion(id){
	<tags:tabMethod method="refreshEnrollmentSection" divElement="'enrollmentSection'" formName="'consentVersionForm'"  viewName="/registration/enrollmentSection" />
	<tags:tabMethod method="refreshEnrollmentSection" divElement="'controlPanel'" formName="'command'"  viewName="/registration/control_panel_section" />
	
	//$('consentVersionForm').submit();
	Element.hide('flash-message-offstudy');
	Element.show('flash-message-reconsent');
	Element.hide('flash-message-edit');
	
	closePopup();
}
</script>
<form:form id="consentVersionForm">
<input type="hidden" id="consentVersion" value="${command.studySubject.studySite.study.consentVersion}"/>
<input type="hidden" name="studySubject.informedConsentVersion" id="studySubject.informedConsentVersion" value="${studySubject.informedConsentVersion}"/>
<chrome:box title="Reconsent">
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentSignedDate"/></div>
		<div class="value">
			<input type="text" name="studySubject.informedConsentSignedDate" id="consentSignedDate" class="date validate-DATE" />
            <a href="#" id="consentSignedDate-calbutton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
				<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/>
			</div>
	</div>
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.currentConsentVersionIs"/> ${command.studySubject.studySite.study.consentVersion}</div>
		<div class="value">
			<input type="checkbox" name="studySubject.currentVersionIndicator" onclick="setVersion(this);" />
			<tags:hoverHint keyProp="studySubject.informedConsentSignedVersion"/>
		</div>
	</div>
</chrome:box>
<!-- 
<div class="flow-buttons">
   	<span class="next">
		<tags:button markupWithTag="button" color="green" value="Save" onclick="updateConsentVersion();" icon="save" type="button"/>
		<tags:button markupWithTag="button" color="red" value="Cancel" onclick="closePopup();" icon="x" type="button"/>
	</span>
</div>
-->
<div class="flow-buttons">
   	<span class="next">
		<input type="image" src="/c3pr/images/flow-buttons/save_btn.png" onclick="updateConsentVersion();"/>
		<input type="image" src="/c3pr/images/flow-buttons/cancel_btn.png"  onclick="closePopup();"/>
		<input type="button" value="Save" onclick="updateConsentVersion();"/>
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