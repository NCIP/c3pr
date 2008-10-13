<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
<jwr:script src="/js/tabbedflow.js" />

<script>
	Calendar.setup({inputField:"study.consentVersionx", ifFormat:"%", button:"consentVersionx-calbutton"});
    Calendar.setup({inputField:"stud.consentVersiony", ifFormat:"%", button:"consentVersiony-calbutton"});
	
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
    			Effect.OpenUp('study.consentVersion1');
    		}else {
    			Effect.OpenUp('study.consentVersion2');
    		}            
        }else {
        	if(num == 1){
    			Effect.CloseDown('study.consentVersion1');
    		}else {
    			Effect.CloseDown('study.consentVersion2');
    		}             
        }
    }    
    
    ValidationManager.submitPostProcess= function(formElement, continueSubmission){
       
       if(formElement.id="command.study"){
             box1=document.getElementById('study.currentStudyAmendment.amendmentVersion');
             box2=document.getElementById('study.currentStudyAmendment.amendmentDate');
             box3=document.getElementById('study.amendmentVersion');
             box4=document.getElementById('study.amendmentDate');
           
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
<br/>	
<chrome:division id="study-details" title="Previous Amendments">
	<table id="study.studyAmendments" class="tablecontent">
			<tr>
				<th>Version #</th>
				<th>Amendment Date</th>
				<th>Comments</th>
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
		<br/>
		<chrome:box title="New Amendment">
		<chrome:division id="study-details" title="Basic Details">
		<div class="row">
			<div id="errorMsg1" style="display:none">
				<span id='sid1' style='color:#EE3324'>Please enter the Version# or Amendment Date.</span><br/> 	
			</div>
		</div>
		
    	<div class="row">
            <div class="label">Version #:</div>
            <div class="value">
                <form:input	path="study.currentStudyAmendment.amendmentVersion" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date :</div>
            <div class="value">
                <tags:dateInput path="study.currentStudyAmendment.amendmentDate"/>
            </div>
        </div>
        
        <div class="row">
            <div class="label">Comments :</div>
            <div class="value">
                <form:textarea path="study.currentStudyAmendment.comments" rows="2" cols="44"/>
            </div>
        </div> 
        </chrome:division>
        <br/>
        
        <chrome:division id="study-details" title="Reasons for Amendment (Atleast One)">
        <table>
        	<tr>
        		<td width="25%"><b>Epoch & Arms :</b></td>
        		<td width="25%"><form:checkbox path="study.currentStudyAmendment.eaChangedIndicator" value="true"/></td>        		
        		<td width="25%"><b>Eligibility :</b></td>
        		<td width="25%"><form:checkbox path="study.currentStudyAmendment.eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.stratChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.randomizationChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.piChangedIndicator" value="true"/></td>
        	</tr>
        	
        	<tr>
        		<td><b>Consent :</b></td>
        		<td><form:checkbox path="study.currentStudyAmendment.consentChangedIndicator" value="true" onclick="manageConsentVersionCheckBox(this, 1);"/></td>
				<td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><b>Companion Study :</b></td>
        		<td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><form:checkbox path="study.currentStudyAmendment.companionChangedIndicator" value="true"/></td>
        	</tr>        	
        </table>
        
        <div id="study.consentVersion1"
                <c:if test="${ empty command.study.currentStudyAmendment.consentChangedIndicator || 
                command.study.currentStudyAmendment.consentChangedIndicator == '' || 
                !command.study.currentStudyAmendment.consentChangedIndicator}">style="display:none;"</c:if>>
         <b>&nbsp;Consent Version/Date:</b>
         <form:input id="study.consentVersionx" path="study.consentVersion" size="9" cssClass="date"/>
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
		
		<chrome:division id="study-details" title="Basic Details">
		<div class="row">
			<div id="errorMsg2" style="display:none">
				<span id='sid2' style='color:#EE3324'>Please enter the Version# or Amendment Date.</span><br/> 	
			</div>
		</div>
		
    	<div class="row">
            <div class="label">Version #:</div>
            <div class="value">
                <input type="text" name="study.studyAmendments[${amendmentSize}].amendmentVersion" id="study.amendmentVersion" size="5" /> 
            </div>
        </div>
        <div class="row">
            <div class="label">Amendment Date:</div>
            <div class="value">
            	<tags:dateInput path="study.studyAmendments[${amendmentSize}].amendmentDate"/>
            </div>
        </div>

        <div class="row">
            <div class="label">Comments:</div>
            <div class="value">
                <textarea name="study.studyAmendments[${amendmentSize}].comments" rows="2" cols="44"></textarea>
            </div>
        </div>        
       
        </chrome:division>
        <br/>
        
        <chrome:division id="study-details" title="Reasons for Amendment (Atleast One)">
        <table>
        	<tr>
        		<td width="25%"><b>Epoch & Arms :</b></td>
        		<td width="25%"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].eaChangedIndicator" value="true"/></td>        		
        		<td width="25%"><b>Eligibility :</b></td>
        		<td width="25%"><input type="checkbox" name="study.studyAmendments[${amendmentSize}].eligibilityChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Stratification :</b></td>
        		<td><input type="checkbox" name="study.studyAmendments[${amendmentSize}].stratChangedIndicator" value="true"/></td>
        		<td><b>Diseases :</b></td>
        		<td><input type="checkbox" name="study.studyAmendments[${amendmentSize}].diseasesChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Randomization :</b></td>
        		<td><input type="checkbox" name="study.studyAmendments[${amendmentSize}].randomizationChangedIndicator" value="true"/></td>
        		<td><b>Principal Investigator :</b></td>
        		<td><input type="checkbox" name="study.studyAmendments[${amendmentSize}].piChangedIndicator" value="true"/></td>
        	</tr>
        	<tr>
        		<td><b>Consent :</b></td>
        		<td><input type="checkbox" name="study.studyAmendments[${amendmentSize}].consentChangedIndicator" value="true" onclick="manageConsentVersionCheckBox(this, 2);"/></td>
				<td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><b>Companion Study :</b></td>
				<td <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>><input type="checkbox" name="study.studyAmendments[${amendmentSize}].companionChangedIndicator" value="true"/></td>        	
			</tr>
        </table> 

        <div id="study.consentVersion2" style="display:none;">
         <b>&nbsp;Consent Version/Date:</b>
         <input type="text" name="study.consentVersion" id="study.consentVersiony" size="9" value="${command.study.consentVersion}" class="date"/>
         <a href="#" id="consentVersiony-calbutton">
		    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
		</a>
        </div>       
        </chrome:division>
	</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}"/>  
</form:form>
</c:otherwise>
</c:choose>


</body>
</html>
