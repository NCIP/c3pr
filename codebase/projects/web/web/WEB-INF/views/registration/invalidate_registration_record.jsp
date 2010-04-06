<%@ include file="taglibs.jsp"%>
<style type="text/css">
.invalidateRecordClass div.row div.label {
	width:17em;
}
.invalidateRecordClass div.row div.value {
	margin-left:18em;
}
</style>
<script>
function invalidateRecord(){
	<tags:tabMethod method="invalidateRegistration" divElement="'dummy-div'" onComplete="redirectToDashboard"/>
	Element.hide('invalidateRecordDiv');
	Element.show('invalidateMessageDiv');
}

function redirectToDashboard(){
	setTimeout("javascript:document.location='../dashboard';", 30)	
}
</script>
<div id="dummy-div" style="display:none;"></div>
<div id="invalidateMessageDiv" style="display:none;">
<div id="invalidateMessage">
	<fmt:message key="c3pr.registration.invalidation.redirection"/>
</div>
</div>

<div id="invalidateRecordDiv">
<form:form id="invalidateRecordForm">
<chrome:box title="Invalidate Registration Record" id="invalidateRecordClass">
<div class="info red">
	<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> 
	<fmt:message key="c3pr.registration.invalidationWarning"/>
</div>	
<div class="row">
	<div class="label"><fmt:message key="c3pr.registration.reasonText"/></div>
	<div class="value">
		<textarea rows="5" cols="32" name="studySubject.invalidationReasonText" id="reasonText" ></textarea>
		<tags:hoverHint keyProp="studySubject.reasonText"/>
	</div>
</div>
</chrome:box>
<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button type="button" color="green" icon="save" value="Invalidate Record" onclick="invalidateRecord();" />
	</span>
</div>
</form:form>
</div>