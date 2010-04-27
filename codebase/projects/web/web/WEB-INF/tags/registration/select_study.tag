<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">

document.observe("dom:loaded", function() {
		<c:if test="${!empty from_reg_confirmation && from_reg_confirmation==true}">
			fromConfirmRegistration(true,'${create_studySiteStudyVersionId}','${create_study_name}','${create_study_identifier}','${create_studySiteName}');
			function fromConfirmRegistration(isActive, studySiteStudyVersionId, studyName,studyPrimaryIdentifier,siteName){
					var url = "../registration/searchEpoch?studySiteStudyVersionId="+studySiteStudyVersionId;
					document.getElementById("studySiteStudyVersionId").value = studySiteStudyVersionId;
					new Ajax.Updater('epochResults',url, {onSuccess:callbackEpoch, onFailure:callbackEpochFail});
					var message = "Selected Study: " +studyName+ " (" +studyPrimaryIdentifier+ ") "  + " at " +siteName;
					minimizeStudyBox(message);
				}
		</c:if>
			
		<c:if test="${!empty fromStudyRegistrations && fromStudyRegistrations==true}">
				searchStudyFromStudyRegistrations('${createRegistration_studyId}');
				function searchStudyFromStudyRegistrations(studyId) {
			        new Element.show('searchStudyInd');
			        new Ajax.Updater('studySearchResults','../registration/searchStudy?fromStudyRegistrations=true&decorator=nullDecorator&studyId='+studyId,
			        {
			            method:'post',
			            postBody:Form.serialize('searchstudyFromRegistrationForm'), 
			            onSuccess:callbackStudy,
			            onFailure:callbackStudyFail
			        });
			    }
		</c:if>
			
		<c:if test="${!empty fromUpdateParticipant}">
				<c:choose>
					<c:when test="${!empty studySiteStudyVersionIdFromUpdateParticipant}">
						searchStudySiteVersionfromUpdateParticipant(true,'${studySiteStudyVersionIdFromUpdateParticipant}','${studyName}','${studyPrimaryIdentifier}','${siteName}');
						function searchStudySiteVersionfromUpdateParticipant(isActive, studySiteStudyVersionId,studyName,studyPrimaryIdentifier,siteName){
							var url = "../registration/searchEpoch?studySiteStudyVersionId="+studySiteStudyVersionId;
							document.getElementById("studySiteStudyVersionId").value = studySiteStudyVersionId;
							new Ajax.Updater('epochResults',url, {onSuccess:callbackEpoch, onFailure:callbackEpochFail});
							var message = "Selected Study: " +studyName+ " (" +studyPrimaryIdentifier+ ") "  + " at " +siteName;
							minimizeStudyBox(message);
						}
						postProcessSubjectSelection(${participantId}, '${participantName}','${participantIdentifier}');
					</c:when>
					<c:otherwise>
						   document.searchstudyForm.searchText.value='${studySearchText}';
						   document.searchstudyForm.searchType.value='${studySearchType}';
						   searchStudy();
						postProcessSubjectSelection(${participantId}, '${participantName}','${participantIdentifier}');
					</c:otherwise>
				</c:choose>
			</c:if>
	});
		
	
	  
	
	function minimizeStudyBox(msg){
		PanelCombo('Studybox');
		displayStudyMessage(msg,true);
	}
	
	function displayStudyMessage(message,pulsateFlag){
		localClassString="#Study .header h2";
		element=$$(localClassString)[0];
		new Element.update(element,message);
		pulsateFlag?(!is_ie?new Effect.Pulsate(element):null):null;
	}
	
    function navRollOver(obj, state) {
        document.getElementById(obj).className = (state == 'on') ? 'resultsOver' : 'results';
    }		
    
    function callbackStudyFail(t ){
		alert("Oops! The search failed. Please refresh and try again.");
	}

	function expandMultiSiteStudy(){
		new Element.toggle('studySites-table-0'); 
		toggleImage('image-open-0');
	}
	
	function callbackStudy(t){
		new Element.hide('searchStudyInd');
		var resultDiv = document.getElementById("studySearchResults");
		resultDiv.innerHTML = t.responseText;
		new Effect.SlideDown(resultDiv);
		<c:if test="${!empty fromStudyRegistrations && fromStudyRegistrations==true}">
			setTimeout("expandMultiSiteStudy()",1000);
		</c:if>
	}
	
	function postProcessStudySelection(isActive, studySiteStudyVersionId,studySiteId, siteName, studyName, identifier){
		document.getElementById("studySiteStudyVersionId").value = studySiteStudyVersionId;
		var url = "../registration/searchEpoch?studySiteStudyVersionId="+studySiteStudyVersionId;
		new Ajax.Updater('epochResults',url, {onSuccess:callbackEpoch, onFailure:callbackEpochFail});
		var message = "Selected Study: " +studyName+ " (" +identifier+ ") "  + " at " +siteName;
		minimizeStudyBox(message);

	}
	
	 function callbackEpochFail(t ){
	//	alert("epoch Search Failed");
	}
	
	function callbackEpoch(t){
		document.getElementById("Epoch").style.display="";
		var resultDiv = document.getElementById("epochResults");
		resultDiv.innerHTML = t.responseText;
		new Effect.SlideDown(resultDiv);		
	}

    function searchStudy() {
    	$('searchText').value = document.searchstudyForm.searchText.value ;
    	$('searchType').value = document.searchstudyForm.searchType.value ;
    	$('searchedStudy').value = "true";
        new Element.show('searchStudyInd');
        new Ajax.Updater('studySearchResults','../registration/searchStudy?fromRegistration=true&decorator=nullDecorator',
        {
            method:'post',
            postBody:Form.serialize('searchstudyForm'), 
            onSuccess:callbackStudy,
            onFailure:callbackStudyFail
        });
    }
    
</script>

<!--tags:minimizablePanelBox title="${epoch.name} : ${epoch.descriptionText }"	boxId="${epoch.name}"-->
<tags:minimizablePanelBox	title="Select Study" boxId="Studybox">
<tags:instructions code="select_study" />
		<form id="searchstudyFromRegistrationForm" name="searchstudyFromRegistrationForm" action="" method="post">
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value=""> 
            <input type="hidden" name="async" id="async" value="async"> 
            <input type="hidden" id="nonPending" name="nonPending" value="true"/>
            <input type="hidden" id="standaloneOnly" name="standaloneOnly" value="true"/>
            <input type="hidden" id="searchType" name="searchType" type="text" value="" size="25"/>
            <input type="hidden" id="searchText" name="searchText" type="text" value="" size="25"/>
            <input type="hidden" name="searchedStudy" id="searchedStudy" value="false">
		</form>

        <form id="searchstudyForm" name="searchstudyForm" action="" method="post">
            <input type="hidden" name="_selected" id="_selected" value="">
            <input type="hidden" name="_action" id="_action" value=""> 
            <input type="hidden" name="async" id="async" value="async"> 
             
        <div class="content">
            <div class="row">
                <div class="label">
                    <fmt:message key="registration.searchStudiesBy"/>
                </div>
                <div class="value">
                	<select id="searchType" name="searchType">
						<c:forEach items="${searchTypeRefDataStudy}" var="searchTypeRefDataStudy">
						<c:if test="${!empty searchTypeRefDataStudy.desc}">
							<option value="${searchTypeRefDataStudy.code}">${searchTypeRefDataStudy.desc}</option>
						</c:if>									
						</c:forEach>
					</select>
                </div>		
            </div>
            <div class="row">
                <div class="label">
                    <fmt:message key="c3pr.common.searchCriteria"/>
                </div>
                <div class="value">
                	<input type="hidden" id="nonPending" name="nonPending" value="true"/>
                	<input type="hidden" id="standaloneOnly" name="standaloneOnly" value="true"/>
                	<input id="searchText" name="searchText" type="text" value="" size="25"/>
                	<tags:button type="button" icon="search" size="small" color="blue" value="Search" onclick="searchStudy();"/>
                	<img id="searchStudyInd" src="<tags:imageUrl name="indicator.white.gif"/>"
								alt="Indicator" align="absmiddle">
					<script>new Element.hide('searchStudyInd');</script>
                </div>		
            </div>
        </div>
		</form>
    	
    	<div id="studySearchResults" style="display:none;">
		<!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
    		add the mapping to the excludes section of decorators.xml -->
    	</div>
</tags:minimizablePanelBox>



