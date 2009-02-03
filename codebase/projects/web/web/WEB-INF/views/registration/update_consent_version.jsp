<%@ include file="taglibs.jsp"%>
<style type="text/css">
		div.row div.label {
			width:15em;
		}
		div.row div.value {
			margin-left:16em;
		}
		#main {
			top:35px;
		}
</style>
<script>
function closePopup(){
	parent.closePopup();
}

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
	$("consentVersionForm").submit();
	parent.refreshEnrollmentSection(id);
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
		<div class="label"><tags:requiredIndicator />Current Consent Version is ${command.studySubject.studySite.study.consentVersion}</div>
		<div class="value">
			<input type="checkbox" name="studySubject.currentVersionIndicator" onclick="setVersion(this);" />
			<tags:hoverHint keyProp="studySubject.informedConsentSignedVersion"/>
		</div>
	</div>
	<br>
	<div class="flow-buttons">
         <span class="next">
			<input type="button" value="Save" onclick="updateConsentVersion(${command.studySubject.id});"/>
			<input type="button" value="Cancel" onclick="closePopup();"/>
		</span>
     </div>
     <br>
</chrome:box>
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