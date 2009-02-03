<%@ include file="taglibs.jsp"%>
<style type="text/css">
		div.row div.label {
			width:8em;
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

function takeSubjectOffStudy(){
	$("offStudyStatusForm").submit();
	//parent.refreshEnrollmentSection();
	closePopup();
}
</script>
<br>
<div id="OffStudyStatus">
<form:form id="offStudyStatusForm">
     <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
     <input type="hidden" name="studySubject.regWorkflowStatus" value="OFF_STUDY" id="regWorkflowStatus"/>
     <br>
     <div class=row>
     	<div class="label">Reason</div>
     	<div class="value">
     		<textarea rows="2" cols="30" class="validate-notEmpty" name="studySubject.offStudyReasonText"></textarea>
     	</div>
     </div>
     <div class=row>
     	<div class="label">Date</div>
     	<div class="value">
     		<input type="text" name="studySubject.offStudyDate" id="offStudyDate" class="date validate-DATE" />
            <a href="#" id="offStudyDate-calbutton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
     	</div>
     </div>
     <br><br>
     <c:if test="${command.studySubject.regWorkflowStatus!='OFF_STUDY'}">
     	<input type="button" value="Save" onclick="takeSubjectOffStudy()"/>
        <input type="button" value="Cancel" onClick="closePopup();"/>
     </c:if>
     
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