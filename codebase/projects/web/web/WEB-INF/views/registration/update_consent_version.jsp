<%@ include file="taglibs.jsp"%>
<style type="text/css">
		div.row div.label {
			width:15em;
		}
		div.row div.value {
			margin-left:1em;
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
	cv = document.getElementById('consentVersion');
	icv = document.getElementById('studySubject.informedConsentVersion');
	if (box.checked) {
		icv.value=cv.value;       
    }else {
    	icv.value="";             
    }       
}

function updateConsentVersion(){
	document.getElementById("consentVersionForm").submit();
	closePopup();	
}


</script>
<form:form id="consentVersionForm">
<input type="hidden" name="studySubject.consentVersion" id="consentVersion" value="${studySubject.studySite.study.consentVersion}"/>
<form:hidden path="studySubject.informedConsentVersion"/>
<chrome:box title="Update Consent Version">
	<div class="row">
		<div class="label"><fmt:message key="registration.consentSignedDate"/></div>
		<div class="value">
			<input type="text" name="command.studySubject.informedConsentSignedDate" id="consentSignedDate" class="date validate-DATE" value="${command.studySubject.informedConsentSignedDateStr}" />
              	<a href="#" id="consentSignedDate-calbutton">
             	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
             	</a>
				<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/>
			</div>
	</div>
	<div class="row">
		<div class="label"><tags:requiredIndicator />Current Consent Version is ${command.studySubject.studySite.study.consentVersion}</div>
		<div class="value">
			<input align="left"  type="checkbox" name="command.studySubject.currentVersionIndicator" value="true" onclick="setVersion(this);" 
				<c:if test="${!empty command.studySubject.informedConsentVersion}"> checked</c:if>/>
			<tags:hoverHint keyProp="studySubject.informedConsentSignedVersion"/>
		</div>
	</div>
	<br>
		
	<div class="flow-buttons">
         <span class="next">
			<input type="button" value="Update" onclick="updateConsentVersion();"/>
			<input type="button" value="Close" onclick="closePopup();"/>
		</span>
     </div>
     <br>
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