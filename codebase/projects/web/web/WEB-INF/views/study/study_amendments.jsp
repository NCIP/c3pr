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
SPRING TAGS
	<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" title="New Amendment" formName="studyAmendmentsForm">
	<jsp:attribute name="singleFields">
		<input type="hidden" name="_action" value="_action"/>
		
		<chrome:division id="study-details" title="Basic Details">
    	<div class="row">
            <div class="label"><span class="required-indicator">Version# :</span></div>
            <div class="value">
                <form:input	path="studyAmendments[0].amendmentVersion" cssClass="validate-notEmpty&&NUMERIC" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <tags:dateInput path="studyAmendments[0].amendmentDate"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><span class="required-indicator">IRB Approval Date:</span></div>
            <div class="value">
            	<tags:dateInput path="studyAmendments[0].irbApprovalDate" cssClass="validate-notEmpty"/>
            </div>
        </div>
        <div class="row">
            <div class="label">Comments :</div>
            <div class="value">
                <form:textarea path="studyAmendments[0].comments" rows="2" cols="40"/>
            </div>
        </div> 
        </chrome:division>
        <br/>
        
        <chrome:division id="study-details" title="Reasons for Amendment (Minimum One):">
        <table>
        	<tr>
        		<td width="20%"><b>Epoch & Arms :</b></td>
        		<td width="5%"><form:checkbox path="studyAmendments[0].epochAndArmsChangedIndicator" value="true"/></td>        		
        		<td width="20%"><b>Eligibility :</b></td>
        		<td width="5%"><form:checkbox path="studyAmendments[0].eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><form:checkbox path="studyAmendments[0].stratificationChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><form:checkbox path="studyAmendments[0].diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Consent :</b></td>
        		<td><form:checkbox path="studyAmendments[0].consentChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><form:checkbox path="studyAmendments[0].principalInvestigatorChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><form:checkbox path="studyAmendments[0].randomizationChangedIndicator" value="true"/></td>
        		<td></td>
        		<td></td>
        	</tr>
        </table>        
        </chrome:division>
	</jsp:attribute>
	</tags:tabForm>	
</c:when>

<c:otherwise>

HTML TAGS
	<tags:tabForm tab="${tab}" flow="${flow}" willSave="${willSave}" title="New Amendment" formName="studyAmendmentsForm">
	<jsp:attribute name="singleFields">
		<input type="hidden" name="_action" value="_action"/>
		
		<chrome:division id="study-details" title="Basic Details">
    	<div class="row">
            <div class="label"><span class="required-indicator">Version# :</span></div>
            <div class="value">
                <input type="text" name="studyAmendments[0].amendmentVersion" cssClass="validate-notEmpty&&NUMERIC" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <input type="text" name="studyAmendments[0].amendmentDate"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><span class="required-indicator">IRB Approval Date:</span></div>
            <div class="value">
            	<input type="text" name="studyAmendments[0].irbApprovalDate" cssClass="validate-notEmpty"/>
            </div>
        </div>
        <div class="row">
            <div class="label">Comments :</div>
            <div class="value">
                <textarea name="studyAmendments[0].comments" rows="2" cols="40"></textarea>
            </div>
        </div> 
        </chrome:division>
        <br/>
        
        <chrome:division id="study-details" title="Reasons for Amendment (Minimum One):">
        <table>
        	<tr>
        		<td width="20%"><b>Epoch & Arms :</b></td>
        		<td width="5%"><input type="checkbox" name="studyAmendments[0].epochAndArmsChangedIndicator" value="true"/></td>        		
        		<td width="20%"><b>Eligibility :</b></td>
        		<td width="5%"><input type="checkbox" name="studyAmendments[0].eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><input type="checkbox" name="studyAmendments[0].stratificationChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><input type="checkbox" name="studyAmendments[0].diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Consent :</b></td>
        		<td><input type="checkbox" name="studyAmendments[0].consentChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><input type="checkbox" name="studyAmendments[0].principalInvestigatorChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><input type="checkbox" name="studyAmendments[0].randomizationChangedIndicator" value="true"/></td>
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
