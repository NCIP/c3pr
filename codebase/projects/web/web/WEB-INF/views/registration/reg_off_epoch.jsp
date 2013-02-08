<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
<title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<script>
	function addReasons(){
		$('rename').name='_target0';
		$('action').value='addReasons';
		$('command').submit();
	}
	
	function submitForm(){
		if(${operationType == 'changeEpochAssignment'}){
			$('rename').name='_target1';
		}else{
			$('rename').name='_target2';
			$('action').value='saveOffEpochReasons';
		}
		$('command').submit();
	}
</script>
</head>
<body>
<c:set var="studySubject" value="${command}" scope="request"/>
<div id="summary">
    <registrationTags:registrationSummary/>
</div>
<div id="offEpochSection"/>
<c:choose>
	<c:when test="${operationType == 'takeSubjectOffStudy'}">
		<c:set var="chromeBoxTitle" value="Off Study Details"/>
		<c:set var="offEpochLabel" value="Off study date"/>
		<c:set var="offEpochDivisionTitle" value="Off Study Reason(s)"/>
		<c:set var="submitButtonLabel" value="Save"/>
	</c:when>
	<c:when test="${operationType == 'failScreening'}">
		<c:set var="chromeBoxTitle" value="Failed Screening Details"/>
		<c:set var="offEpochLabel" value="Screening failed date"/>
		<c:set var="offEpochDivisionTitle" value="Off Screening Reason(s)"/>
		<c:set var="submitButtonLabel" value="Save"/>
	</c:when>
	<c:when test="${operationType == 'changeEpochAssignment'}">
		<c:set var="chromeBoxTitle" value="Off ${command.studySubject.scheduledEpoch.epoch.name} Details"/>
		<c:set var="offEpochLabel" value="Off epoch date"/>
		<c:choose>
			<c:when test="${command.studySubject.scheduledEpoch.epoch.type == 'SCREENING'}">
				<c:set var="offEpochDivisionTitle" value="Off Screening Reason(s)"/>
			</c:when>
			<c:when test="${command.studySubject.scheduledEpoch.epoch.type == 'TREATMENT'}">
				<c:set var="offEpochDivisionTitle" value="Off Treatment Reason(s)"/>
			</c:when>
			<c:when test="${command.studySubject.scheduledEpoch.epoch.type == 'FOLLOWUP'}">
				<c:set var="offEpochDivisionTitle" value="Off Follow-up Reason(s)"/>
			</c:when>
			<c:when test="${command.studySubject.scheduledEpoch.epoch.type == 'RESERVING'}">
				<c:set var="offEpochDivisionTitle" value="Off Reserving Reason(s)"/>
			</c:when>
		</c:choose>
		<c:set var="submitButtonLabel" value="Continue"/>
	</c:when>
</c:choose>
<form:form>
<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
<input type="hidden" id="rename"/>
<input type="hidden" id="action" name="action"/>
<input type="hidden" name="dontSave"/>
<chrome:box title="${chromeBoxTitle}">
<div class="row">
  	<div class="label"><tags:requiredIndicator />${offEpochLabel }</div>
  	<div class="value">
  		<tags:dateInput path="offEpochDate" validateDate="true" cssClass='required validate-notEmpty$$DATE'/>
  	</div>
 </div>
<chrome:division title="${offEpochDivisionTitle}">
<tags:errors path="*"/> 

<table border="0" id="table1" cellspacing="10" width="100%">
	<tr>
	<td valign="top" width="45%">
		Select reason(s)
		<form:select path="reasons" multiple="multiple" size="20"  cssStyle="width:400px">
			<form:options items="${offEpochReasons}" itemLabel="description" itemValue="id"/>
		</form:select>
	</td>
	<td valign="middle">
		<tags:button type="button" icon="continue" size="small" color="blue" value="Add" id="add" onclick="addReasons();"/>
    </td>
    <td width="45%" valign="top">
    	Selected reason(s)
   		<table border="1" class="tablecontent" width="100%">
            <tr>
                <th width="30%">Reason</th>
                <th width="65%">Description</th>
            </tr>
            <c:choose>
            <c:when test="${fn:length(command.reasons) == 0 }">
            	<tr><td colspan="3">No reasons selected</td></tr>
            </c:when>
            <c:otherwise>
            	<c:forEach items="${command.reasons}" var="reason" varStatus="status">
		            <tr>
		            	<td>${reason.description }</td>
		                <td>
		                	<input type="hidden" name="offEpochReasons[${status.index}].reason" value="${reason.id }"/>
		                	<textarea name="offEpochReasons[${status.index}].description" rows="3" cols="30"></textarea>
		                </td>
		            </tr>
	            </c:forEach>
	        </c:otherwise>
        	</c:choose>
        </table>
    </td>
    </tr>
</table>
</chrome:division>
</chrome:box>
<div class="flow-buttons">
	<span class="next">
		<tags:button type="button" color="green" icon="continue" value="${submitButtonLabel}" onclick="submitForm();"/>
    </span>
</div>
</div>
</form:form>
</body>
</html>
