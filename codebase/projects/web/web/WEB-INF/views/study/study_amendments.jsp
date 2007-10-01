<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<tags:stylesheetLink name="tabbedflow" />
<tags:javascriptLink name="tabbedflow" />
<tags:includeScriptaculous />
</head>

<body>
<chrome:box title="Amendments">
<chrome:division id="study-details" title="Previous Amendments">
<br/>	
	<table id="studyAmendments" class="tablecontent">
			<tr>
				<th>Version #</th>
				<th>Amendment Date</th>
				<th><span class="required-indicator">IRB Approval Date</span></th>
				<th>Comments</th>
			</tr>
			<c:forEach items="${command.previousStudyAmendments}"  var="studyAmendments" varStatus="status">
			<tr id="studyAmendments-${status.index}">
				<td>${studyAmendments.amendmentVersion}</td>
				<td>${studyAmendments.amendmentDate}</td>
				<td>${studyAmendments.irbApprovalDate}</td>
				<td>${studyAmendments.comments}</td>
			</tr>
			</c:forEach>
	</table>
	<br />
</chrome:division>
</chrome:box>

<c:choose>
<c:when test="${command.coordinatingCenterStudyStatus == 'AMENDMENT_PENDING'}">
	<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" title="New Amendment" >
	<jsp:attribute name="singleFields">
		<input type="hidden" name="_action" value="_action"/>
		
		<chrome:division id="study-details" title="Basic Details">
    	<div class="row">
            <div class="label"><span class="required-indicator">Version# :</span></div>
            <div class="value">
                <form:input	path="currentStudyAmendment.amendmentVersion" cssClass="validate-notEmpty" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <tags:dateInput path="currentStudyAmendment.amendmentDate"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><span class="required-indicator">IRB Approval Date:</span></div>
            <div class="value">
            	<tags:dateInput path="currentStudyAmendment.irbApprovalDate" cssClass="validate-notEmpty"/>
            </div>
        </div>
        <div class="row">
            <div class="label">Comments :</div>
            <div class="value">
                <form:textarea path="currentStudyAmendment.comments" rows="2" cols="40"/>
            </div>
        </div> 
        </chrome:division>
        <br/>
        
        <chrome:division id="study-details" title="Reasons for Amendment (Minimum One)">
        <table>
        	<tr>
        		<td width="20%"><b>Epoch & Arms :</b></td>
        		<td width="5%"><form:checkbox path="currentStudyAmendment.epochAndArmsChangedIndicator" value="true"/></td>        		
        		<td width="20%"><b>Eligibility :</b></td>
        		<td width="5%"><form:checkbox path="currentStudyAmendment.eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.stratificationChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Consent :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.consentChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.principalInvestigatorChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.randomizationChangedIndicator" value="true"/></td>
        		<td></td>
        		<td></td>
        	</tr>
        </table>        
        </chrome:division>
	</jsp:attribute>
	</tags:tabForm>	
</c:when>

<c:otherwise>
<c:set var="amendmentSize" value="${fn:length(command.studyAmendments)}" scope="request" />

	<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" title="New Amendment" >
	<jsp:attribute name="singleFields">
		<input type="hidden" name="_action" value="_action"/>
		
		<chrome:division id="study-details" title="Basic Details">
    	<div class="row">
            <div class="label"><span class="required-indicator">Version# :</span></div>
            <div class="value">
                <input type="text" name="studyAmendments[${amendmentSize}].amendmentVersion" class="validate-notEmpty" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <input type="text" name="studyAmendments[${amendmentSize}].amendmentDate" id="studyAmendments[${amendmentSize}].amendmentDate" class="date"/>
                <a href="#" id="studyAmendments[${amendmentSize}].amendmentDate-calbutton">
				    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a>
            </div>
        </div>
        <div class="row">
            <div class="label"><span class="required-indicator">IRB Approval Date:</span></div>
            <div class="value">
            	<input type="text" name="studyAmendments[${amendmentSize}].irbApprovalDate" class="date validate-notEmpty" id="studyAmendments[${amendmentSize}].irbApprovalDate"/>
                <a href="#" id="studyAmendments[${amendmentSize}].irbApprovalDate-calbutton">
				    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a>
            </div>
        </div>
        <div class="row">
            <div class="label">Comments :</div>
            <div class="value">
                <textarea name="studyAmendments[${amendmentSize}].comments" rows="2" cols="40"></textarea>
            </div>
        </div> 
        </chrome:division>
        <br/>
        
        <chrome:division id="study-details" title="Reasons for Amendment (Minimum One)">
        <table>
        	<tr>
        		<td width="20%"><b>Epoch & Arms :</b></td>
        		<td width="5%"><input type="checkbox" name="studyAmendments[${amendmentSize}].epochAndArmsChangedIndicator" value="true"/></td>        		
        		<td width="20%"><b>Eligibility :</b></td>
        		<td width="5%"><input type="checkbox" name="studyAmendments[${amendmentSize}].eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].stratificationChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Consent :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].consentChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].principalInvestigatorChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].randomizationChangedIndicator" value="true"/></td>
        		<td></td>
        		<td></td>
        	</tr>
        </table>        
        </chrome:division>
	</jsp:attribute>
	</tags:tabForm>	
</c:otherwise>
</c:choose>


</body>
</html>
