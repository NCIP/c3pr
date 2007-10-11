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
<script>	
	Effect.OpenUp = function(element) {
        element = $(element);
        new Effect.BlindDown(element, arguments[1] || {});
    }

    Effect.CloseDown = function(element) {
        element = $(element);
        new Effect.BlindUp(element, arguments[1] || {});
    }
        
    function manageConsentVersionCheckBox(box, num){
    	if (box.checked) {
    		if(num == 1){
    			Effect.OpenUp('consentVersion1');
    		}else {
    			Effect.OpenUp('consentVersion2');
    		}            
        }else {
        	if(num == 1){
    			Effect.CloseDown('consentVersion1');
    		}else {
    			Effect.CloseDown('consentVersion2');
    		}             
        }
    }    
    
    ValidationManager.submitPostProcess= function(formElement, continueSubmission){
       
       if(formElement.id="command"){
             box1=document.getElementById('currentStudyAmendment.amendmentVersion');
             box2=document.getElementById('currentStudyAmendment.amendmentDate');
             box3=document.getElementById('amendmentVersion');
             box4=document.getElementById('amendmentDate');
           
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
             
             elmt1 = document.getElementById('errorMsg1');
             if(elmt1 != null){
             	elmt1.style.display='';
             } else {
             	document.getElementById('errorMsg2').style.display='';
             }
         	return false;            
         }         
     } 
</script>
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
			<div id="errorMsg1" style="display:none">
				<span id='sid1' style='color:#EE3324'>Please enter the Version# or Amendment Date.</span><br/> 	
			</div>
		</div>
		
    	<div class="row">
            <div class="label">Version# :</div>
            <div class="value">
                <form:input	path="currentStudyAmendment.amendmentVersion" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <tags:dateInput path="currentStudyAmendment.amendmentDate"/>
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
        		<td width="5%"><form:checkbox path="currentStudyAmendment.eaChangedIndicator" value="true"/></td>        		
        		<td width="20%"><b>Eligibility :</b></td>
        		<td width="5%"><form:checkbox path="currentStudyAmendment.eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.stratChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.randomizationChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.piChangedIndicator" value="true"/></td>
        	</tr>
        	
        	<tr>
        		<td><b>Update Consent :</b></td>
        		<td><form:checkbox path="currentStudyAmendment.consentChangedIndicator" value="true" onclick="manageConsentVersionCheckBox(this, 1);"/></td>
        		<td></td>
        		<td></td>
        	</tr>        	
        </table>
        
        <div id="consentVersion1"
                <c:if test="${ empty command.currentStudyAmendment.consentChangedIndicator || 
                command.currentStudyAmendment.consentChangedIndicator == '' || 
                !command.currentStudyAmendment.consentChangedIndicator}">style="display:none;"</c:if>>
         <b>&nbsp;Consent Version :</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <form:input path="consentVersion" size="20" />
        </div>               
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
			<div id="errorMsg2" style="display:none">
				<span id='sid2' style='color:#EE3324'>Please enter the Version# or Amendment Date.</span><br/> 	
			</div>
		</div>
		
    	<div class="row">
            <div class="label">Version# :</div>
            <div class="value">
                <input type="text" name="studyAmendments[${amendmentSize}].amendmentVersion" id="amendmentVersion" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <input type="text" name="studyAmendments[${amendmentSize}].amendmentDate" id="amendmentDate" class="date"/>
                <a href="#" id="studyAmendments[${amendmentSize}].amendmentDate-calbutton">
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
        		<td width="5%"><input type="checkbox" name="studyAmendments[${amendmentSize}].eaChangedIndicator" value="true"/></td>        		
        		<td width="20%"><b>Eligibility :</b></td>
        		<td width="5%"><input type="checkbox" name="studyAmendments[${amendmentSize}].eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].stratChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].randomizationChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].piChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Update Consent :</b></td>
        		<td><input type="checkbox" name="studyAmendments[${amendmentSize}].consentChangedIndicator" value="true" onclick="manageConsentVersionCheckBox(this, 2);"/></td>
        		<td></td>
        		<td></td>
        	</tr>     	
        </table> 

        <div id="consentVersion2" style="display:none;">
         <b>&nbsp;Consent Version :</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="text" name="consentVersion" id="consentVersion" size="20"  />
        </div>       
        </chrome:division>
	</jsp:attribute>
	</tags:tabForm>	
</c:otherwise>
</c:choose>


</body>
</html>
