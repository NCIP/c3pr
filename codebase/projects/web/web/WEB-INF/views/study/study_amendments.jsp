<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />
<style>
#main {
	top:33px;
}
</style>
<script>
	   ValidationManager.submitPostProcess= function(formElement, continueSubmission){
       
       if(formElement.id="command.study"){
             box1=$('study.currentStudyAmendment.amendmentVersion');
             box2=$('study.currentStudyAmendment.amendmentDate');
             box3=$('study.amendmentVersion');
             box4=$('study.amendmentDate');
           
             if(box1 != null){
             	if(box1.value != ''){
             		return true;
             	}
             }
             if(box2 != null){
             	if(box2.value != ''){
             		return true;
             	}
             }
             if(box3 != null){
             	if(box3.value != ''){
             		return true;
             	}
             }
             if(box4 != null){
             	if(box4.value != ''){
             		return true;
             	}
             }
             
             elmt1 = $('errorMsg1');
             if(elmt1 != null){
             	elmt1.style.display='';
             } else {
             	$('errorMsg2').style.display='';
             }
         	return false;            
         }         
     } 
</script>
</head>
<body>
<chrome:box title="Amendments">
<chrome:division id="study-amendments" title="Previous Amendments">
	<table id="study.studyAmendments" class="tablecontent">
			<tr>
				<th><fmt:message key="study.amendmentVersion"/></th>
				<th><fmt:message key="study.amendmentDate"/></th>
				<th><fmt:message key="c3pr.common.comments"/></th>
			</tr>
			<c:forEach items="${command.study.previousStudyAmendments}"  var="studyAmendments" varStatus="status">
				<tr id="study.studyAmendments-${status.index}">
					<td>${studyAmendments.amendmentVersion}</td>
					<td>${studyAmendments.amendmentDateStr}</td>
					<td>${studyAmendments.comments}</td>
				</tr>
			</c:forEach>
	</table>
	<br />
</chrome:division>
</chrome:box>
<c:choose>
<c:when test="${command.study.coordinatingCenterStudyStatus == 'AMENDMENT_PENDING'}">
	<form:form name="myform" cssClass="standard">
	<tags:tabFields tab="${tab}"/>
		<input type="hidden" name="_actionx" value="_actionx"/>
		<input type="hidden" id="_selected" name="_selected" value=""/>
		<input type="hidden" id="_selectedSite" name="_selectedSite" value=""/>
		<chrome:box title="New Amendment">
		<chrome:division id="study-amendments" title="Basic Details">
		<div class="row">
			<div id="errorMsg1" style="display:none">
				<span id='sid1' style='color:#EE3324'>Please enter the Version# or Amendment Date.</span><br/> 	
			</div>
		</div>
    	<div class="row">
            <div class="label"><fmt:message key="study.amendmentVersion"/></div>
            <div class="value">
                <form:input	path="study.currentStudyAmendment.amendmentVersion" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.amendmentDate"/></div>
            <div class="value">
                <tags:dateInput path="study.currentStudyAmendment.amendmentDate"/>
            </div>
        </div>
        
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.comments"/></div>
            <div class="value">
                <form:textarea path="study.currentStudyAmendment.comments" rows="2" cols="44"/>
            </div>
        </div> 
        </chrome:division>
        <chrome:division id="study-amendments" title="Reasons for Amendment (Atleast One)">
        <table>
        	<tr>
        		<td width="25%"><b><fmt:message key="study.epoch&Arms"/></b></td>
        		<td width="25%"><form:checkbox path="study.currentStudyAmendment.eaChangedIndicator" value="true"/></td>        		
        		<td width="25%"><b><fmt:message key="study.eligibility"/></b></td>
        		<td width="25%"><form:checkbox path="study.currentStudyAmendment.eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b><fmt:message key="study.stratification"/></b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.stratChangedIndicator" value="true"/></td>
        		<td><b><fmt:message key="study.diseases"/></b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	
        	<tr>
        		<td><b><fmt:message key="study.randomization"/></b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.randomizationChangedIndicator" value="true"/></td>
        		<td><b><fmt:message key="study.principalInvestigator"/></b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.piChangedIndicator" value="true"/></td>
        	</tr>
        	
        	<tr>
        		<td><b><fmt:message key="study.consent"/></b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.consentChangedIndicator" value="true" onclick="manageConsentVersionCheckBox(this, 1);"/></td>
				<td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><b><fmt:message key="study.companionStudy"/></b></td>
        		<td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><form:checkbox path="study.currentStudyAmendment.companionChangedIndicator" value="true"/></td>
        	</tr>        	
        </table>
        
        <div id="study.consentVersion1"
                <c:if test="${ empty command.study.currentStudyAmendment.consentChangedIndicator || 
                command.study.currentStudyAmendment.consentChangedIndicator == '' || 
                !command.study.currentStudyAmendment.consentChangedIndicator}">style="display:none;"</c:if>>
         <b>&nbsp;Consent Version/Date:</b>
         <form:input id="study.consentVersionx" path="study.currentStudyAmendment.consentVersion" size="9" cssClass="date"/>
         <a href="#" id="consentVersionx-calbutton">
		    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
		</a>
        </div>               
        </chrome:division>
        </chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}"/>  
</form:form>
</c:when>

<c:otherwise>
<c:set var="amendmentSize" value="${fn:length(command.study.studyAmendments)}" scope="request" />

	<form:form name="myform" cssClass="standard">
	<tags:tabFields tab="${tab}"/>

		<input type="hidden" name="_actionx" value="_actionx"/>
		<input type="hidden" id="_selected" name="_selected" value=""/>
		<input type="hidden" id="_selectedSite" name="_selectedSite" value=""/>
		<br/>
		<chrome:box title="New Amendment">
		
		<chrome:division id="study-amendments" title="Basic Details">
		<div class="row">
			<div id="errorMsg2" style="display:none">
				<span id='sid2' style='color:#EE3324'>Please enter the Version# or Amendment Date.</span><br/> 	
			</div>
		</div>
		
    	<div class="row">
            <div class="label"><fmt:message key="study.amendmentVersion"/></div>
            <div class="value">
                <input type="text" name="study.studyAmendments[${amendmentSize}].amendmentVersion" id="study.amendmentVersion" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.amendmentDate"/></div>
            <div class="value">
            	<tags:dateInput path="study.studyAmendments[${amendmentSize}].amendmentDate"/>
            </div>
        </div>

        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.comments"/></div>
            <div class="value">
                <textarea name="study.studyAmendments[${amendmentSize}].comments" rows="2" cols="44"></textarea>
            </div>
        </div>        
       
        </chrome:division>
        <br/>
        
        <chrome:division id="study-amendments" title="Reasons for Amendment (Atleast One)">
        <div>
        <div class="leftpanel">
        	<div class="row">
        		<div class="label"><fmt:message key="study.epoch&Arms"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].eaChangedIndicator" value="true"/></div>
	       	</div>
	       	<div class="row">
        		<div class="label"><fmt:message key="study.stratification"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].stratChangedIndicator" value="true"/></div>
	       	</div>
	       	<div class="row">
        		<div class="label"><fmt:message key="study.randomization"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].randomizationChangedIndicator" value="true"/> </div>
	       	</div>
	       	<div class="row">
        		<div class="label"><fmt:message key="study.consent"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].consentChangedIndicator" value="true"/></div>
	       	</div>
        
       	</div>
       	<div class="rightpanel">
        	<div class="row">
        		<div class="label"><fmt:message key="study.eligibility"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].eligibilityChangedIndicator" value="true"/></div>
	       	</div>
	       	<div class="row">
        		<div class="label"><fmt:message key="study.diseases"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].diseasesChangedIndicator" value="true"/></div>
	       	</div>
	       	<div class="row">
        		<div class="label"><fmt:message key="study.principalInvestigator"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].piChangedIndicator" value="true"/></div>
	       	</div>
	       	<div class="row"  <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>>
        		<div class="label"><fmt:message key="study.companionStudy"/></div>
		       	<div class="value"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].companionChangedIndicator" value="true"/></div>
	       	</div>
       	</div>
        </div> 
		<div class="division"></div>
        </chrome:division>
	</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}"/>  
</form:form>
</c:otherwise>
</c:choose>


</body>
</html>
