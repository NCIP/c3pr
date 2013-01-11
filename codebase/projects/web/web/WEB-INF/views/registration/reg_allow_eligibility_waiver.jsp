<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<script>
	function enableWaiverButton(){
		waiverAnswerChecked = false;
		$$(".waiverCheckbox").each(function(checkbox){
									if(checkbox.checked) waiverAnswerChecked=true;
								});
		$('waiverButton').disabled=!waiverAnswerChecked;
	}
	
</script>
<form:form method="post">
<input type="hidden" name="_target${tab.number}" id="_target"/>
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
<input type="hidden" name="allowWaiver" value="true"/>
<chrome:box title="Allow Eligibility Waiver">
<table width="100%" border="0" class="tablecontent">
	<tr>
		<th align="left"><b><fmt:message key="study.criterion"/></b></th>
		<th align="left"><b>Waive</b></th>
	</tr>
	<c:forEach items="${command.studySubject.scheduledEpoch.waivableEligibilityAnswers}" var="answer">
		<tr>
			<td width="85%">
				${answer.eligibilityCriteria.questionText}
			</td>
			<td width="15%" valign="center">
				<form:checkbox path="waiveEligibilityCrieteria" value="${answer.id}" cssClass="waiverCheckbox" onclick="enableWaiverButton();"/>
			</td>
		</tr>
	</c:forEach>
</table>
</chrome:box>
<div id="waiveConfirm" style="display:none;">
	<div id="flash-message" class="error">
			<img src="<tags:imageUrl name="error-red.png" />" alt="" style="vertical-align:middle;" />
			<fmt:message key="c3pr.registration.allowWaiverWarning"/>
	</div>
	<div class="flow-buttons">
		<span class="next">
			<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
			<tags:button type="submit" color="green" icon="continue" value="Confirm"/>
	    </span>
	</div>
</div>
<div id="defaultButtons" class="flow-buttons">
	<span class="next">
		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button id="waiverButton" type="button" color="green" icon="continue" value="Waive Eligibility" disabled="true" onclick="$('defaultButtons').hide();$('waiveConfirm').show()" />
    </span>
</div>  
</form:form>
