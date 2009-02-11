<%@ include file="taglibs.jsp"%>
<script>

function takeSubjectOffStudy(){
	<tags:tabMethod method="refreshEnrollmentSection" divElement="'enrollmentSection'" formName="'offStudyStatusForm'"  viewName="/registration/enrollmentSection" />
	<tags:tabMethod method="refreshEnrollmentSection" divElement="'controlPanel'" formName="'command'"  viewName="/registration/control_panel_section" />
	//$('offStudyStatusForm').submit();
	Element.show('flash-message-offstudy');
	Element.hide('flash-message-reconsent');
	Element.hide('flash-message-edit');
	closePopup();
}

function confirmTakeSubjectOffStudy(){
	var subjectOffStudySection = $('OffStudyStatus');
	var confirmationSection = $('confirmTakeSubjectOffStudy');
	subjectOffStudySection.style.display = "none";
	confirmationSection.style.display = "";
}

function cancelTakeSubjectOffStudy(){
	var subjectOffStudySection = $('OffStudyStatus');
	var confirmationSection = $('confirmTakeSubjectOffStudy');
	subjectOffStudySection.style.display = "";
	confirmationSection.style.display = "none";
	closePopup();
}

</script>
<div id="OffStudyStatus">
<form:form id="offStudyStatusForm">
<chrome:box title="Take subject off study">
     <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
     <input type="hidden" name="studySubject.regWorkflowStatus" value="OFF_STUDY" id="regWorkflowStatus"/>
     <div class="info red"><img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.SUBJECTOFFSTUDY.WARNING"/></div>
     <br>
     <div class=row>
     	<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.reason"/></div>
     	<div class="value">
     		<textarea rows="2" cols="30" class="validate-notEmpty" name="studySubject.offStudyReasonText"></textarea>
     	</div>
     </div>
     <div class=row>
     	<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.date"/></div>
     	<div class="value">
     		<input type="text" name="studySubject.offStudyDate" id="offStudyDate" class="date validate-DATE" />
            <a href="#" id="offStudyDate-calbutton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
     	</div>
     </div>
</chrome:box>
<!--    
<div class="flow-buttons">
	<span class="next">
		<tags:button markupWithTag="button" color="green" value="Save" onclick="confirmTakeSubjectOffStudy();" icon="save" type="button"/>
		<tags:button markupWithTag="button" color="red" value="Cancel" onclick="closePopup();" icon="x" type="button"/>
    </span>
</div>
-->
<div class="flow-buttons">
	<span class="next">
	 	<input type="image" src="/c3pr/images/flow-buttons/save_btn.png" onclick="confirmTakeSubjectOffStudy()"/>
        <input type="image" src="/c3pr/images/flow-buttons/cancel_btn.png" onclick="closePopup();"/>
        <input type="button" value="Save" onclick="confirmTakeSubjectOffStudy();"/>
    </span>
</div>    
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
<div id="confirmTakeSubjectOffStudy" style="display:none;">
<chrome:box>
	<div class="info">You are about to take subject off study. This step is irreversible. Please click OK to confirm.</div>
	<br>
	</chrome:box>
	<!-- <div class="flow-buttons">
	<span class="next">
		<tags:button markupWithTag="button" color="green" value="OK" onclick="takeSubjectOffStudy();" type="button"/>
		<tags:button markupWithTag="button" color="red" value="Cancel" onclick="cancelTakeSubjectOffStudy();" type="button"/>
	</span>
	</div>-->
	<div class="flow-buttons">
	<span class="next">
	<input type="button" value="OK" onclick="takeSubjectOffStudy();"/>
	<input type="button" value="Cancel" onclick="cancelTakeSubjectOffStudy();"/>
	</span>
	</div>
</div>