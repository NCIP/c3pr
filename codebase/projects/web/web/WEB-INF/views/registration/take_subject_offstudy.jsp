<%@ include file="taglibs.jsp"%>
<style type="text/css">
		div.row div.label {
			width:5em;
		}
		div.row div.value {
			margin-left:6em;
		}
		#main {
			top:35px;
		}
</style>

<script>
function closePopup(){
	parent.closePopup();
}

function takeSubjectOffStudy(id){
	$("offStudyStatusForm").submit();
	parent.refreshEnrollmentSection(id);
	closePopup();
}

ValidationManager.submitPostProcess= function(formElement, continueSubmission){
	var id = ${command.studySubject.id}
    if(formElement.id=="offStudyStatusForm" && continueSubmission){
    	parent.refreshEnrollmentSection(id);
  	};
	return continueSubmission;
} 
</script>
<div id="OffStudyStatus">
<form:form id="offStudyStatusForm">
<chrome:box title="Take subject off study">
     <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
     <input type="hidden" name="studySubject.regWorkflowStatus" value="OFF_STUDY" id="regWorkflowStatus"/>
     <div class=row>
     	<div class="label"><tags:requiredIndicator />Reason</div>
     	<div class="value">
     		<textarea rows="2" cols="30" class="validate-notEmpty" name="studySubject.offStudyReasonText"></textarea>
     	</div>
     </div>
     <div class=row>
     	<div class="label"><tags:requiredIndicator />Date</div>
     	<div class="value">
     		<input type="text" name="studySubject.offStudyDate" id="offStudyDate" class="date validate-DATE" />
            <a href="#" id="offStudyDate-calbutton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
     	</div>
     </div>
     <br>
     <div class="flow-buttons">
         <span class="next">
     	<input type="button" value="Save" onclick="takeSubjectOffStudy(${command.studySubject.id})"/>
        <input type="button" value="Cancel" onClick="closePopup();"/>
	        </span>
    	</div>  
    	<br>  
</chrome:box>     
</form:form>
</div>
<script>
inputDateElementLocal1="offStudyDate";
inputDateElementLink1="offStudyDate-calbutton";
Calendar.setup(
{
    inputField  : inputDateElementLocal1,         // ID of the input field
    ifFormat    : "%m/%d/%Y",    // the date format
    button      : inputDateElementLink1       // ID of the button
}
);
</script>