<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <tags:dwrJavascriptLink objects="anatomicDiseaseSite" />
    <script>
    	C3PR.disableCheckRequiredFieldOnLoad=true;
    	function confirmBroadcastRegistration(){
			contentWin = new Window({ width:400, height:200 ,className :"alert_lite"}) 
			contentWin.setContent('confirmation-msg') ;
			contentWin.showCenter(true);
        }
    	
        function getBroadcastStatus() {
			new Element.update('broadcastResponse','');
			new Element.hide('broadcastAction');
        	new Element.show('broadcastResponseCheckWait');
        	new Element.show('broadcastResponse');

        <tags:tabMethod method="getMessageBroadcastStatus"
            viewName="/registration/asynchronous/broadcast_res" divElement="'broadcastResponse'"
            formName="'broadcastForm'"/>
        }

      paramString="<tags:identifierParameterString identifier='${command.studySubject.systemAssignedIdentifiers[0] }'/>";
      
        doSendMessageToESB = function() {
        	new Element.update('broadcastResponse','');
        	new Element.hide('broadcastAction');
        	new Element.show('broadcastWait');
        	new Element.show('broadcastResponse');

	        <tags:tabMethod method="sendMessageToESB"
	            viewName="/registration/asynchronous/broadcast_res" divElement="'broadcastResponse'"
	            formName="'broadcastForm'"/>
        }
        
		function accessApp(url,targetWindow){
			C3PR.disableAjaxLoadingIndicator=true;
			$('hotlinksForm').target=targetWindow;
			$('hotlinksForm').action=url;
			$('hotlinksForm').submit();
			C3PR.disableAjaxLoadingIndicator=false;
		}
		function createReg(studySite, participant, parentRegistrationId){
			$('create_studySite').value=studySite;
			$('create_participant').value=participant;
			$('create_parent_id').value=parentRegistrationId;
			$('create').submit();
		}

		function closePopup() {
			win.close();
		}
		
		ValidationManager.submitPostProcess=function(formElement, flag){
			if(formElement.id =='offStudyStatusForm' && flag ){
				Element.show('confirmTakeSubjectOffStudy');
				Element.hide('OffStudyStatus');
				return false;
			}
			if(formElement.id =='consentVersionForm' && flag ){
				<tags:tabMethod method="refreshEnrollmentSection" divElement="'enrollmentSection'" formName="'consentVersionForm'"  viewName="/registration/enrollmentSection" />
				<tags:tabMethod method="refreshEnrollmentSection" divElement="'controlPanel'" formName="'command'"  viewName="/registration/control_panel_section" />
				Element.hide('flash-message-offstudy');
				closePopup();
				return false;
			}
			return flag;
		}

		function manageCompanionRegistration(url){
			if(url != ''){
				document.location='../registration/manageRegistration?'+url;
			}
		}

		function manageParentRegistration(){
			document.location="../registration/manageRegistration?<tags:identifierParameterString identifier='${command.studySubject.parentStudySubject.systemAssignedIdentifiers[0] }'/>" 
		}

		function changeEpochPopup(){
			var arr= $$("#changeEpoch");
			win = new Window({ width:950, height:600 ,className :"mac_os_x" , 
				title: "Change Epoch" , minimizable:false, maximizable:false ,
				zIndex:100 , hideEffect:Element.hide, showEffect:Element.show}) 
			win.setContent(arr[0]) ;
			win.showCenter(true);
	 	}

		function allowEligibilityWaiverPopup(){
			var arr= $$("#allowWaiverDiv");
			win = new Window({ width:600, height:300 ,className :"mac_os_x" , 
				title: "Waive Eligibility" , minimizable:false, maximizable:false ,
				zIndex:100 , hideEffect:Element.hide, showEffect:Element.show}) 
			win.setContent(arr[0]) ;
			win.showCenter(true);
	 	}

		function invalidateRegistrationRecord(){
			var arr= $$("#invalidateRecord");
			win = new Window({className :"mac_os_x", title: "Invalidate Registration Record", 
									hideEffect:Element.hide, 
									zIndex:100, width:550, height:300 , minimizable:false, maximizable:false,
									showEffect:Element.show 
									}) 
			win.setContent(arr[0]) ;
			win.showCenter(true);
		}

		function activateInPlaceEditingForArm(localEditEvent) {
			showWarning(localEditEvent, armArray);
        }

        function activateInPlaceEditingForEnrollmentDetail(localEditEvent) {
			showWarning(localEditEvent, enrollmentDetailArray);
        }

        function activateInPlaceEditingForInformedConsent(localEditEvent) {
        	showWarning(localEditEvent, informedConsentArray);
        }

        function activateInPlaceEditingForEligibilityCriteria(localEditEvent) {
        	showWarning(localEditEvent, eligibilityCriteriaArray);
        }

        function activateInPlaceEditingForStratificationCriteria(localEditEvent) {
        	showWarning(localEditEvent, stratificationCriteriaArray);
        }

        function activateInPlaceEditingForSubject(localEditEvent) {
        	showWarning(localEditEvent, subjectArray);
        }

        Event.observe(window, "load", function() {
        	Event.observe("editInPlaceForSubject", "click", activateInPlaceEditingForSubject);
    		Event.observe("editInPlaceForArm", "click", activateInPlaceEditingForArm);
    		Event.observe("editInPlaceForEnrollmentDetail", "click", activateInPlaceEditingForEnrollmentDetail);
    		Event.observe("editInPlaceForInformedConsent", "click", activateInPlaceEditingForInformedConsent);
    		Event.observe("editInPlaceForEligibilityCriteria", "click", activateInPlaceEditingForEligibilityCriteria);
    		Event.observe("editInPlaceForStratificationCriteria", "click", activateInPlaceEditingForStratificationCriteria);
    	})
    	
    	
        function activateInPlaceEditing(localEditEvent, array) {
            for (var aE = 0; aE < array.length; aE++) {
            	array[aE].enterEditMode(localEditEvent);
            }
        }

		function showWarning(localEditEvent, array){
			Dialog.confirm("This step might corrupt registration record. Are you sure you want to continue?", 
                	{width:400,height:125, okLabel: "Continue", buttonClass: "button", id: "myDialogId", 
            		cancel:function(win) {
        			}, 
            		ok:function(win) {
        				activateInPlaceEditing(localEditEvent, array); 
                		return true;
                		} 
            		}); 
        }

        updateEligibility = function(){
        	<tags:tabMethod method="updateEligibility" divElement="'eligibility'"  viewName="/registration/asynchronous/eligibilityIndicator"/>
        }

        updateStratification = function(){
            <tags:tabMethod method="updateStratumGroupNumber" divElement="'stratumGroupNumber'" />
        }
        
        var diseaseSiteAutocompleterProps = {
        		basename: "diseaseSite",
        	    populator: function(autocompleter, text) {
 				        anatomicDiseaseSite.matchDiseaseSites(text, function(values) {
							autocompleter.setChoices(values)
 						})
				},
        	    valueSelector: function(obj) {
        			return obj.name + " (" + obj.code + ")"
        		},
        	    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
        										$("diseaseSite-hidden").value=selectedChoice.id;
        									}
        	}
		
    </script>
    <style type="text/css">
	    button.omnipotent-button td.m, a.omnipotent-button td.m {
			padding-top:2px;
			padding-left:8px;
			padding-right:8px;
		}
    </style>
</head>
<body>
<c:set var="canEditRegistrationRecord" value="false"/>
<c3pr:checkprivilege hasPrivileges="STUDYSUBJECT_OVERRIDE">
	<c:if test="${command.studySubject.scheduledEpoch.scEpochWorkflowStatus == 'REGISTERED'}">
		<c:set var="canEditRegistrationRecord" value="true"/>
	</c:if>
</c3pr:checkprivilege>
	<div id="controlPanel">
		<registrationTags:registrationControlPanel command="${command}"></registrationTags:registrationControlPanel>
	</div>
<form action="../registration/createRegistration" method="post" id="create">
	<input type="hidden" name="_page" id="_page0" value="0"/>
	<input type="hidden" name="_target1" id="_target1" value="1"/>
	<input type="hidden" name="studySite" id="create_studySite" value=""/>
	<input type="hidden" name="participant" id="create_participant" value=""/>
	<input type="hidden" name="parentRegistrationId" id="create_parent_id" value=""/>
	<input type="hidden" name="create_companion" value=""/>
	<!-- <input type="hidden" name="scheduledEpoch" id="create_scheduledEpoch" value=""/>-->
</form>
<form id="broadcastForm" action="/c3pr/pages/registration/manageRegistration">
	<tags:tabFields tab="${tab}"/>
</form>
<form id="hotlinksForm" action="" method="get">
<input type="hidden" name="assignment" value="${command.studySubject.gridId }"/>
</form>
<form:form method="post" action="manageRegistration">
    <tags:tabFields tab="${tab}"/>
</form:form>
<div id="flash-message-offstudy" style="display:none;">
	<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /><fmt:message key="c3pr.registration.subjectOffStudy"/>Subject has been taken off study.</div>
</div>
<c:if test="${displayAllowWaiverSuccessMessage}">
	<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" /><fmt:message key="c3pr.registration.allowWaiverSucess"/></div>
</c:if>
<div id="summary">
<br/>
<chrome:division id="Subject Information" title="Subject" inPlaceLinkId="editInPlaceForSubject" condition="${canEditRegistrationRecord}">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
            <div class="value">
            	<tags:inPlaceEdit value="${command.participant.firstName}" path="studySubject.studySubjectDemographics.firstName" id="firstName" validations="validate-notEmpty" disable="${!canEditRegistrationRecord}"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
            <div class="value">
            	<tags:inPlaceEdit value="${command.participant.lastName}" path="studySubject.studySubjectDemographics.lastName" id="lastName" validations="validate-notEmpty" disable="${!canEditRegistrationRecord}"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.gender"/>:</div>
            <div class="value">
	            <c:set var="commanSepOptValGender" value="["></c:set>
				<c:forEach items="${administrativeGenderCode}"  var="genderCode" varStatus="status">
			 				<c:set var="commanSepOptValGender" value="${commanSepOptValGender}['${genderCode.code}','${genderCode.desc}']"></c:set>
			 				<c:if test="${status.index != (fn:length(administrativeGenderCode) -1)}">
			     				<c:set var="commanSepOptValGender" value="${commanSepOptValGender},"></c:set>
			 				</c:if>
				</c:forEach>
				<c:set var="commanSepOptValGender" value="${commanSepOptValGender}]"></c:set>
            	<tags:inPlaceSelect value="${command.participant.administrativeGenderCode}" path="studySubject.studySubjectDemographics.administrativeGenderCode" id="administrativeGenderCode" validations="validate-notEmpty"
                    commanSepOptVal="${commanSepOptValGender}"  pathToGet="studySubject.studySubjectDemographics.administrativeGenderCode" disable="${!canEditRegistrationRecord}"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.medicalRecordNumber"/>:</div>
            <div class="value">
            	<tags:inPlaceEdit value="${command.participant.MRN.value}" path="studySubject.studySubjectDemographics.MRN.value" id="medicalRecordNumber" validations="validate-notEmpty" disable="${!canEditRegistrationRecord}"/>
            </div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="participant.birthDate"/>:</div>
            <div class="value">
            	<tags:inPlaceEdit value="${command.participant.birthDateStr}" path="studySubject.studySubjectDemographics.birthDate" id="birthDate" validations="validate-notEmpty" disable="${!canEditRegistrationRecord}"/>
           	</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.ethnicity"/>:</div>
            <div class="value">
            	<c:set var="commanSepOptValEthnicity" value="["></c:set>
				<c:forEach items="${ethnicGroupCodes}"  var="ethnicCode" varStatus="status">
			 				<c:set var="commanSepOptValEthnicity" value="${commanSepOptValEthnicity}['${ethnicCode.code}','${ethnicCode.desc}']"></c:set>
			 				<c:if test="${status.index != (fn:length(ethnicGroupCodes) -1)}">
			     				<c:set var="commanSepOptValEthnicity" value="${commanSepOptValEthnicity},"></c:set>
			 				</c:if>
				</c:forEach>
				<c:set var="commanSepOptValEthnicity" value="${commanSepOptValEthnicity}]"></c:set>
            	<tags:inPlaceSelect value="${command.participant.ethnicGroupCode}" path="studySubject.studySubjectDemographics.ethnicGroupCode" id="ethnicGroupCode" validations="validate-notEmpty"
                    commanSepOptVal="${commanSepOptValEthnicity}"  pathToGet="studySubject.studySubjectDemographics.ethnicGroupCode" disable="${!canEditRegistrationRecord}"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.race"/>:</div>
            	<c:forEach items="${command.participant.raceCodes}" var="raceCode">
		                <div class="value">${raceCode.displayName}</div>
		        </c:forEach>

        </div>
    </div>
     <script>
    var subjectArray = new Array();
    subjectArray.push(editor_firstName);
    subjectArray.push(editor_lastName);
    subjectArray.push(editor_administrativeGenderCode);
    subjectArray.push(editor_medicalRecordNumber);
    subjectArray.push(editor_birthDate);
    subjectArray.push(editor_ethnicGroupCode);
   // subjectArray.push(editor_raceCodes);
    </script>
</chrome:division>
<chrome:division id="Study Information" title="Study">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="study.shortTitle"/>:</div>
            <div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.status"/>:</div>
            <div class="value">${command.studySubject.studySite.study.coordinatingCenterStudyStatus.displayName}</div>
        </div>
         <div class="row">
            <div class="label"><fmt:message key="study.version.name"/>:</div>
            <div class="value">${command.studySubject.studySiteVersion.studyVersion.name}</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"> <fmt:message key="study.phase"/>:</div>
            <div class="value">${command.studySubject.studySite.study.phaseCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.randomized"/>:</div>
            <div class="value">${command.studySubject.studySite.study.randomizedIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
            <div class="value">${command.studySubject.studySite.study.primaryIdentifier}</div>
        </div>
   </div>
</chrome:division>
<chrome:division id="Study Site Information:" title="Study Site">
   	<div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.name"/>:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="site.address"/>:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.address.addressString}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.status"/>:</div>
            <div class="value">${command.studySubject.studySite.siteStudyStatus.code}</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="site.NCIInstitutionCode"/>:</div>
            <div class="value">${command.studySubject.studySite.healthcareSite.primaryIdentifier}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="site.IRBApprovalDate"/>:</div>
            <div class="value">${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.irbApprovalDateStr}</div>
        </div>
  </div>
</chrome:division>
<chrome:division title="Registration Identifiers">
    <table class="tablecontent" width="100%">
        <tr>
        	<th width="10%"><fmt:message key="c3pr.common.class"/></th>
            <th width="30%" scope="col" align="left"><fmt:message key="c3pr.common.assigningAuthority"/></th>
            <th width="30%" scope="col" align="left"><fmt:message key="c3pr.common.identifierType"/></th>
            <th scope="col" align="left"><fmt:message key="c3pr.common.identifier"/></th>
        </tr>
        <c:forEach var="orgIdentifier" items="${command.studySubject.organizationAssignedIdentifiers}" varStatus="organizationStatus">
			<tr>
				<td><fmt:message key="c3pr.common.organization" /></td>
				<c:choose>
				<c:when test="${orgIdentifier.healthcareSite.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
            		<td class="alt" align="left">${orgIdentifier.healthcareSite.name} &nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/></td>
                </c:when>
   			    <c:otherwise>
  					<td class="alt" align="left">${orgIdentifier.healthcareSite.name} </td>
			    </c:otherwise>
			    </c:choose>
				<td>${orgIdentifier.type.displayName}</td>
				<td>${orgIdentifier.value}</td>
			</tr>
		</c:forEach>
		<c:forEach items="${command.studySubject.systemAssignedIdentifiers}"	varStatus="status" var="sysIdentifier">
			<tr>
				<td><fmt:message key="c3pr.common.system" /></td>
				<td>${sysIdentifier.systemName}</td>
				<td>${sysIdentifier.type}</td>
				<td>${sysIdentifier.value}</td>
			</tr>
		</c:forEach>
  	</table>
</chrome:division>
<chrome:division id="Current Epoch Information" title="Epoch & Arm" inPlaceLinkId="editInPlaceForArm" condition="${canEditRegistrationRecord}">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.epoch.name"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.epoch.type"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.type.code}</div>
        </div>
       	<div class="row">
       		<c:choose>
            	<c:when test="${empty armAssignedLabel}">
            		<div class="label"><fmt:message key="study.epoch.arm"/>:</div>
            	</c:when>
            	<c:otherwise>
            		<div class="label">${armAssignedLabel}:</div>
            	</c:otherwise>
            </c:choose>
            <c:choose>
            	<c:when test="${empty armAssignedLabel}">
            		<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notApplicable"/></span></div>
            	</c:when>
            	<c:otherwise>
            		<div class="value">
            		<c:if test="${armAssignedLabel == 'Kit'}">
            			<tags:inPlaceEdit value="${command.studySubject.scheduledEpoch.scheduledArm.kitNumber}" path="studySubject.scheduledEpoch.scheduledArm.kitNumber" id="armAssigned" validations="validate-notEmpty" disable="${!canEditRegistrationRecord}"/>
            		</c:if>
            		<c:if test="${armAssignedLabel == 'Arm'}">
            			<c:set var="commanSepOptValArm" value="["></c:set>
           				<c:forEach items="${command.studySubject.scheduledEpoch.epoch.arms}" var="arm" varStatus="temp">
               				<c:set var="commanSepOptValArm" value="${commanSepOptValArm}[${arm.id},'${arm.name}']"></c:set>
               				<c:if test="${!temp.last}">
                   				<c:set var="commanSepOptValArm" value="${commanSepOptValArm},"></c:set>
               				</c:if>
           				</c:forEach>
           				<c:set var="commanSepOptValArm" value="${commanSepOptValArm}]"></c:set>
               			<tags:inPlaceSelect value="${command.studySubject.scheduledEpoch.scheduledArm.arm.name}" path="studySubject.scheduledEpoch.scheduledArm.arm" id="armAssigned" disable="${!canEditRegistrationRecord}"
                                   commanSepOptVal="${commanSepOptValArm}" pathToGet="studySubject.scheduledEpoch.scheduledArm.arm.name"/>
            		</c:if>
            		</div>
            	</c:otherwise>
            </c:choose>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.enrolling"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator?'Yes':'No'}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.epochStatus"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.scEpochWorkflowStatus.code}</div>
        </div>
    </div>
    
     <script>
    var armArray = new Array();
    armArray.push(editor_armAssigned);
    </script>
</chrome:division>
<div id="enrollmentSection">
<chrome:division id="enrollment" title="Enrollment Details" inPlaceLinkId="editInPlaceForEnrollmentDetail" condition="${canEditRegistrationRecord}">
<div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.startDate"/>:</div>
            <div class="value">
           		<tags:inPlaceEdit value="${command.studySubject.startDateStr}" path="studySubject.startDate" id="startDate" validations="validate-notEmpty&&DATE" disable="${!canEditRegistrationRecord}"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationStatus"/>:</div>
            <div class="value">${command.studySubject.regWorkflowStatus.code}
            </div>
        </div>
        <c:if test="${command.studySubject.regWorkflowStatus=='OFF_STUDY'}">
        	<div class="row">
                <div class="label"><fmt:message key="registration.offStudyDate"/>:</div>
                <div class="value">
                	<tags:inPlaceEdit value="${command.studySubject.offStudyDateStr}" path="studySubject.offStudyDate" id="offStudyDate" validations="validate-notEmpty&&DATE" disable="${!canEditRegistrationRecord}"/>
                </div>
            </div>
            <table class="tablecontent">
		        <tr>
		        	<th align="left">Off study reason(s)</th>
		            <th align="left">Description</th>
		        </tr>
		        <c:forEach var="offStudyReason" items="${command.studySubject.offStudyReasons}">
					<tr>
						<td>${offStudyReason.reason.description}</td>
						<td>${offStudyReason.description}</td>
					</tr>
				</c:forEach>
		  	</table>
        </c:if>
        <div class="row">
            <div class="label"><fmt:message key="registration.enrollingPhysician"/>:</div>
            	<c:choose>
					<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
						<div class="value">
							<c:set var="commanSepOptValPI" value="["></c:set>
            				<c:forEach items="${command.studySubject.studySite.activeStudyInvestigators}" var="physician" varStatus="temp">
                				<c:set var="commanSepOptValPI" value="${commanSepOptValPI}[${physician.id},'${physician.healthcareSiteInvestigator.investigator.fullName}']"></c:set>
                				<c:if test="${!temp.last}">
                    				<c:set var="commanSepOptValPI" value="${commanSepOptValPI},"></c:set>
                				</c:if>
            				</c:forEach>
            				<c:set var="commanSepOptValPI" value="${commanSepOptValPI}]"></c:set>
                			<tags:inPlaceSelect value="${command.studySubject.treatingPhysicianFullName}" path="studySubject.treatingPhysician" id="treatingPhysician"
                                    commanSepOptVal="${commanSepOptValPI}" pathToGet="treatingPhysicianFullName" disable="${!canEditRegistrationRecord}"/>
						</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
					</c:otherwise>
				</c:choose>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="registration.registrationIdentifier"/>:</div>
            	<c:choose>
					<c:when test="${!empty command.studySubject.coOrdinatingCenterIdentifier.value}">
						<div class="value">${command.studySubject.coOrdinatingCenterIdentifier.value}</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notGenerated"/></span></div>
					</c:otherwise>
				</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.primaryDisease"/>:</div>
            	<c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseStr}">
							<div class="value">
								<c:set var="commanSepOptValDisease" value="["></c:set>
	            				<c:forEach items="${command.studySubject.studySite.study.studyDiseases}" var="disease" varStatus="temp">
	                				<c:set var="commanSepOptValDisease" value="${commanSepOptValDisease}[${disease.id},'${disease.diseaseTerm.term}']"></c:set>
	                				<c:if test="${!temp.last}">
	                    				<c:set var="commanSepOptValDisease" value="${commanSepOptValDisease},"></c:set>
	                				</c:if>
	            				</c:forEach>
	            				<c:set var="commanSepOptValDisease" value="${commanSepOptValDisease}]"></c:set>
	                			<tags:inPlaceSelect value="${command.studySubject.diseaseHistory.primaryDiseaseStr}" path="studySubject.diseaseHistory.studyDisease" id="primaryDisease"
	                                    commanSepOptVal="${commanSepOptValDisease}" pathToGet="studySubject.diseaseHistory.studyDisease.diseaseTerm.term" disable="${!canEditRegistrationRecord}"/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="registration.primaryDiseaseSite"/>:</div>
            <c:choose>
						<c:when test="${!empty command.studySubject.diseaseHistory.primaryDiseaseSiteStr }">
							<div class="value">
							    <tags:inPlaceEdit value="${command.studySubject.diseaseHistory.primaryDiseaseSiteStr}" path="studySubject.diseaseHistory.icd9DiseaseSite" id="icd9DiseaseSite" validations="validate-notEmpty" autocompleterJSVar="diseaseSiteAutocompleterProps"
							    pathToGet="studySubject.diseaseHistory.icd9DiseaseSite.name" disable="${!canEditRegistrationRecord}"
							    />
							</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
        <div class="row">
        <div class="label"><fmt:message key="registration.paymentMethod"/>:</div>
        	  <c:set var="options" value=""></c:set>
		      <c:set var="values" value=""></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="["></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Private Insurance', 'Private Insurance'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Medicare', 'Medicare'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Medicare And Private Insurance', 'Medicare And Private Insurance'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Medicaid', 'Medicaid'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Medicaid And Medicare', 'Medicaid And Medicare'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Self Pay (No Insurance)', 'Self Pay (No Insurance)'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'No Means Of Payment (No Insurance)', 'No Means Of Payment (No Insurance)'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Military Sponsored', 'Military Sponsored'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Veterans Sponsored', 'Veterans Sponsored'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Military Or Veterans Sponsored Nos', 'Military Or Veterans Sponsored Nos'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Other', 'Other'],"></c:set>
		      <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}[ 'Unknown', 'Unknown']"></c:set>
			  <c:set var="commanSepOptValPaymentMethod" value="${commanSepOptValPaymentMethod}]"></c:set>
              <c:choose>
						<c:when test="${!empty command.studySubject.paymentMethod}">
							<div class="value">
								<tags:inPlaceSelect value="${command.studySubject.paymentMethod}" path="studySubject.paymentMethod" id="paymentMethod"
                                    commanSepOptVal="${commanSepOptValPaymentMethod}" disable="${!canEditRegistrationRecord}"/>
							</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
        </div>
</div>
    <script>
    var enrollmentDetailArray = new Array();
    enrollmentDetailArray.push(editor_startDate);
    enrollmentDetailArray.push(editor_paymentMethod);
    enrollmentDetailArray.push(editor_primaryDisease);
    enrollmentDetailArray.push(editor_offStudyDate);
    enrollmentDetailArray.push(editor_offStudyReasonText);
    enrollmentDetailArray.push(editor_treatingPhysician);
    enrollmentDetailArray.push(editor_icd9DiseaseSite);
    </script>
    
</chrome:division>
<chrome:division title="Informed Consents" inPlaceLinkId="editInPlaceForInformedConsent" condition="${canEditRegistrationRecord}">
		<table class="tablecontent">
					<tr>
						<th><fmt:message key="study.consentName"/></th>
						<th><fmt:message key="registration.consentSignedDate"/></th>
						<th><fmt:message key="registration.consentDeliveredDate"/></th>
						<th><fmt:message key="registration.consentMethod"/></th>
						<th><fmt:message key="registration.consentPresenter"/></th>
					</tr>
					<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
						<tr>
							<td>${studySubjectConsentVersion.consent.name}</td>
								
								<c:choose>
									<c:when test="${studySubjectConsentVersion.informedConsentSignedDateStr != null && studySubjectConsentVersion.informedConsentSignedDateStr != ''}"> 
										<td>
											<tags:inPlaceEdit value="${studySubjectConsentVersion.informedConsentSignedDateStr}" path="studySubject.studySubjectConsentVersions[${status.index}].informedConsentSignedDate" 
												id="informedConsentSignedDate_${status.index}" validations="validate-notEmpty&&DATE" disable="${!canEditRegistrationRecord}"/>
										</td>
										<td><tags:noDataAvailable value="${studySubjectConsentVersion.consentDeliveryDateStr}"/></td>
										<td><tags:noDataAvailable value="${studySubjectConsentVersion.consentingMethod.displayName}"/></td>
										<td><tags:noDataAvailable value="${studySubjectConsentVersion.consentPresenter}"/></td>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${studySubjectConsentVersion.consent.mandatoryIndicator == true}"> 
												<td colspan="4"><span class="red"><fmt:message key="registartion.consentRequired"/></span></td>
											</c:when>
											<c:otherwise>
												<td colspan="4"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></td>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
						</tr>
					</c:forEach>
				</table>
	 <script>
 		var informedConsentArray = new Array();
	    <c:forEach var="informedConsent" items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" varStatus="informedConsentStatus">
	    	informedConsentArray.push(editor_informedConsentSignedDate_${informedConsentStatus.index});	
	    </c:forEach>
 	</script>
</chrome:division>
</div>
<chrome:division id="Eligibility" title="Eligibility" inPlaceLinkId="editInPlaceForEligibilityCriteria" condition="${canEditRegistrationRecord}">
    <c:set var="options" value=""></c:set>
    <c:set var="values" value=""></c:set>
    <c:set var="commanSepOptVal" value="["></c:set>
    <c:set var="commanSepOptVal" value="${commanSepOptVal}[ 'Yes', 'Yes'],"></c:set>
    <c:set var="commanSepOptVal" value="${commanSepOptVal}[ 'No', 'No'],"></c:set>
    <c:set var="commanSepOptVal" value="${commanSepOptVal}[ 'NA', 'Not Applicable']"></c:set>
	<c:set var="commanSepOptVal" value="${commanSepOptVal}]"></c:set>
	<div class="leftpanel">
		<div class="row">
    		<div class="label" ><fmt:message key="registration.eligible"/>:</div>
    		<div id="eligibility">
        		<registrationTags:eligibilityIndicator command="${command}"></registrationTags:eligibilityIndicator>
			</div>
         </div>
      </div>
      <div class="rightpanel">&nbsp;</div> 
      <br>  
      <div class="row">
         <c:if test="${fn:length(command.studySubject.scheduledEpoch.inclusionEligibilityAnswers) > 0}">
				<table border="0" cellspacing="0" cellpadding="0" class="tablecontent" width="80%">
	                  <tr>
	                      <th width="35%" scope="col" align="left"><fmt:message key="study.inclusionCriterion"/></th>
	                      <th scope="col" align="left"><b><fmt:message key="study.answers"/></b></th>
	                  </tr>
	                  <c:forEach items="${command.studySubject.scheduledEpoch.inclusionEligibilityAnswers}" var="eligibilityAnswer" varStatus="status">
	                      <tr class="results">
	                          <td class="alt" align="left">${eligibilityAnswer.eligibilityCriteria.questionText}</td>
	                          <td class="alt" align="left">
	                          	<c:choose>
	                          	<c:when test="${!eligibilityAnswer.allowWaiver}">
	                          		<tags:inPlaceSelect value="${eligibilityAnswer.answerText}" path="studySubject.scheduledEpoch.inclusionEligibilityAnswers[${status.index}].answerText" id="inclusionAnswerText_${status.index}"
                                    commanSepOptVal="${commanSepOptVal}" onComplete="updateEligibility" disable="${!canEditRegistrationRecord}" />
	                          	</c:when>
	                          	<c:otherwise>Waived <img src='<tags:imageUrl name="eligibility_waived.png"/>'/>
									<table>
										<tr>
											<td style="border:0px;"><b>Waiver id:</b> ${!empty eligibilityAnswer.waiverId?eligibilityAnswer.waiverId:'Not yet specified' }</td>
										</tr>
										<tr>
											<td style="border:0px;"><b>Waiver allowed by:</b> ${eligibilityAnswer.waivedBy.fullName}</td>
										</tr>
										<tr>
											<td style="border:0px;"><b>Waiver reason:</b>${!empty eligibilityAnswer.waiverReason?eligibilityAnswer.waiverReason:'Not yet specified' }</td>
										</tr>
									</table>
	                          	</c:otherwise>
	                          	</c:choose>
	                          </td>
	                      </tr>
	                  </c:forEach>
	              </table>
              </c:if>
              <br>
              <c:if test="${fn:length(command.studySubject.scheduledEpoch.exclusionEligibilityAnswers) > 0}">
				<table border="0" cellspacing="0" cellpadding="0" class="tablecontent" width="80%">
	                  <tr>
	                      <th width="35%" scope="col" align="left"><fmt:message key="study.exclusionCriterion"/></th>
	                      <th scope="col" align="left"><b><fmt:message key="study.answers"/></b></th>
	                  </tr>
	                  <c:forEach items="${command.studySubject.scheduledEpoch.exclusionEligibilityAnswers}" var="eligibilityAnswer" varStatus="exclusionStatus">
	                      <tr class="results">
	                          <td class="alt" align="left">${eligibilityAnswer.eligibilityCriteria.questionText}</td>
	                          <td class="alt" align="left">
	                          	<tags:inPlaceSelect value="${eligibilityAnswer.answerText}" path="studySubject.scheduledEpoch.exclusionEligibilityAnswers[${exclusionStatus.index}].answerText" id="exclusionAnswerText_${exclusionStatus.index}"
                                    commanSepOptVal="${commanSepOptVal}" disable="${!canEditRegistrationRecord}"/>
	                          </td>
	                      </tr>
	                  </c:forEach>
	              </table>
              </c:if>
         </div>
    <script>
    var eligibilityCriteriaArray = new Array();
    <c:forEach var="inclusionEligibility" items="${command.studySubject.scheduledEpoch.inclusionEligibilityAnswers}" varStatus="inclusionEligibilityStatus">
	    eligibilityCriteriaArray.push(editor_inclusionAnswerText_${inclusionEligibilityStatus.index});	
    </c:forEach>
    <c:forEach var="exclusionEligibility" items="${command.studySubject.scheduledEpoch.exclusionEligibilityAnswers}" varStatus="exclusionEligibilityStatus">
    	eligibilityCriteriaArray.push(editor_exclusionAnswerText_${exclusionEligibilityStatus.index});	
	</c:forEach>
    </script>
    </chrome:division>
    <chrome:division id="stratification" title="Stratification" inPlaceLinkId="editInPlaceForStratificationCriteria" condition="${canEditRegistrationRecord}">
        <c:choose>
            <c:when test="${fn:length(command.studySubject.scheduledEpoch.subjectStratificationAnswers) == 0}">
                <div align="left"><span class="no-selection"><fmt:message key="registartion.stratificationNotAvailable"/></span></div>
            </c:when>
            <c:otherwise>
	          	<c:if test="${command.studySubject.scheduledEpoch.epoch.stratificationIndicator}">
	          		<div class="leftpanel">
					<div class="row">
						<div class="label"><fmt:message key="registration.stratumGroupNumber"/>:</div>
						<div class="value" id="stratumGroupNumber"> ${command.studySubject.scheduledEpoch.stratumGroupNumber}</div>
					</div>
					</div>
				</c:if>
				<div class="rightpanel">&nbsp;</div>
                <div class="row">
                <table border="0" cellspacing="0" cellpadding="0" class="tablecontent" width="80%">
                    <tr>
                        <th width="35%" scope="col" align="left"><fmt:message key="study.criterion"/></th>
                        <th scope="col" align="left"><b><fmt:message key="study.answers"/></b></th>
                    </tr>	
                    <c:forEach items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}" var="criteria" varStatus="status">
                        <tr class="results">
                            <td class="alt" align="left">${criteria.stratificationCriterion.questionText}</td>
                            <td class="alt" align="left">
                            	<c:set var="commanSepOptValStratification" value="["></c:set>
			       				<c:forEach items="${criteria.stratificationCriterion.permissibleAnswers}" var="answer" varStatus="temp">
			           				<c:set var="commanSepOptValStratification" value="${commanSepOptValStratification}[${answer.id},'${answer.permissibleAnswer}']"></c:set>
			           				<c:if test="${!temp.last}">
			               				<c:set var="commanSepOptValStratification" value="${commanSepOptValStratification},"></c:set>
			           				</c:if>
			       				</c:forEach>
			       				<c:set var="commanSepOptValStratification" value="${commanSepOptValStratification}]"></c:set>			
                            	<tags:inPlaceSelect value="${criteria.stratificationCriterionAnswer.permissibleAnswer}" path="studySubject.scheduledEpoch.subjectStratificationAnswers[${status.index}].stratificationCriterionAnswer" id="permissibleAnswer_${status.index}"
                                    commanSepOptVal="${commanSepOptValStratification}"  pathToGet="studySubject.scheduledEpoch.subjectStratificationAnswers[${status.index}].stratificationCriterionAnswer.permissibleAnswer" onComplete="updateStratification" disable="${!canEditRegistrationRecord}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                </div>
            </c:otherwise>
        </c:choose>
         <script>
		    var stratificationCriteriaArray = new Array();
		    <c:forEach var="stratificationAnswer" items="${command.studySubject.scheduledEpoch.subjectStratificationAnswers}" varStatus="stratificationStatus">
		    stratificationCriteriaArray.push(editor_permissibleAnswer_${stratificationStatus.index});	
		    </c:forEach>
    </script>
    </chrome:division>
    
<div <c:if test="${empty command.studySubject.parentStudySubject}">style="display:none;"</c:if>>
<chrome:division title="Parent Registration">
    <table class="tablecontent" width="90%">
        <tr>
            <th width="35%" scope="col" align="left"><b><fmt:message key="study.shortTitle"/></b></th>
			<th width="25%" scope="col" align="left"><b><fmt:message key="c3pr.common.primaryIdentifier"/></b></th>
        </tr>
            <tr>
                <td class="alt"><a href="javascript:manageParentRegistration();">${command.studySubject.parentStudySubject.studySite.study.shortTitleText}&nbsp;(${command.studySubject.parentStudySubject.studySite.study.primaryIdentifier}) </a></td>
				<td class="alt">${command.studySubject.parentStudySubject.studySite.study.primaryIdentifier}</td>
   	        </tr>	           
    </table>
</chrome:division>
</div>
<div id="companionAssociationsDiv" <c:if test="${fn:length(companions) == 0 || !command.studySubject.scheduledEpoch.epoch.enrollmentIndicator || not empty command.studySubject.parentStudySubject}">style="display:none;"</c:if>>
	<chrome:division id="companionRegistration" title="Companion Registration">
			<table border="0" cellspacing="0" cellpadding="0" class="tablecontent"  width="80%">
				<tr>
					<th width="40%" scope="col" align="left"><b><fmt:message key="c3pr.common.study"/></b></th>
					<th scope="22%" align="left"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
					<th scope="20%" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
				</tr>
				<c:forEach items="${companions}" var="companion" varStatus="status">
					<tr class="results" <c:if test="${companion.registrationStatus == 'Enrolled'}">onclick="manageCompanionRegistration('${companion.companionRegistrationUrl}');"</c:if>>
						<td class="alt"><c:if test="${companion.mandatoryIndicator}"><tags:requiredIndicator /></c:if>
							<c:choose>
								<c:when test="${companion.registrationStatus == 'Enrolled'}">
									<a href="javascript:manageCompanionRegistration('${companion.companionRegistrationUrl}');">
										${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})
									</a>
								</c:when>
								<c:otherwise>
									${companion.companionStudyShortTitle}(${companion.companionStudyPrimaryIdentifier})
								</c:otherwise>
							</c:choose>
						</td>
						<td class="alt">${companion.mandatoryIndicator=="true"?"Yes":"No"}</td>
						<td class="alt">
						<c:choose>
								<c:when test="${companion.mandatoryIndicator}">
									<c:choose>
										<c:when test="${empty companion.registrationStatus}">
											<font color="Red"><i><fmt:message key="c3pr.common.notStarted" /></i></font>
										</c:when>
										<c:otherwise>
											${companion.registrationStatus}
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									${empty companion.registrationStatus? 'Not Started': companion.registrationStatus}
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
	</chrome:division>
</div>
<c:if test="${canBroadcast}">
    <chrome:division title="CCTS Applications">
    	<ul>
	    	<c:if test="${!empty caaersBaseUrl}">
		    <li><a href="javascript:accessApp('${caaersBaseUrl }','${caaers_window }')"><b>Adverse Event Reporting System</b></a></li>
		    </c:if>
			<c:if test="${!empty pscBaseUrl}">
		    <li><a href="javascript:accessApp('${pscBaseUrl }','${psc_window }')">Patient Study Calendar</a></li>
		    </c:if>
			<c:if test="${!empty c3dBaseUrl}">
		    <li><a href="javascript:accessApp('${c3dBaseUrl }','${c3d_window }')">Cancer Central Clinical Database</a></li>
		    </c:if>
	  	</ul>
    </chrome:division>
</c:if>

</div>
<div id="invalidateRecordPage" style="display:none;">
<div id="invalidateRecord" >
	<%@ include file="invalidate_registration_record.jsp"%>
</div>
<div id="allowWaiverDiv" >
	<%@ include file="reg_allow_eligibility_waiver.jsp"%>
</div>
</div>

<div id="printable" style="display:none;">
	<h3><b>Subject</b></h3>
		<hr></hr>
		<table>
			<tr>
				<td align="right"><b><fmt:message key="participant.fullName"/>:</b></td>
				<td align="left">${command.participant.fullName}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="participant.MRN"/>:</b></td>
				<td align="left">${command.participant.MRN.value }</td>
			</tr>
		</table> 
		
	<h3><b>${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.primaryIdentifier})</b></h3>
		<hr></hr>
		<table>
			<tr>
				<td align="right"><b><fmt:message key="registration.registrationIdentifier"/>:</b></td>
				<td align="left">${command.studySubject.coOrdinatingCenterIdentifier.value}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="study.shortTitle"/>:</b></td>
				<td align="left">${command.studySubject.studySite.study.shortTitleText }</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="registration.registrationStatus"/>:</b></td>
				<td align="left">${command.studySubject.regWorkflowStatus.code}</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="c3pr.common.epoch"/>:</b></td>
				<td align="left">${command.studySubject.scheduledEpoch.epoch.name }</td>
			</tr>
			<c:if test="${!empty armAssigned}">
				<tr>
					<td align="right"><b><fmt:message key="armAssignedLabel"/>:</b></td>
					<td align="left">${armAssigned}</td>
				</tr>
			</c:if>
			<tr>
				<td align="right"><b><fmt:message key="registration.enrollingSite"/>:</b></td>
				<td align="left">${command.studySubject.studySite.healthcareSite.name }</td>
			</tr>
			<tr>
				<td align="right"><b><fmt:message key="registration.startDate"/>:</b></td>
				<td align="left">${command.studySubject.startDateStr }</td>
			</tr>
			
			<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
				<tr>
				<td align="right"><b>Informed Consent ${status.index+1}:</b></td>
				<td align="left">${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})</td>
			</tr>
			</c:forEach>
			
			<tr>
				<td align="right"><b><fmt:message key="registration.enrollingPhysician"/>:</b></td>
				<c:choose>
					<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
						<td align="left">${command.studySubject.treatingPhysicianFullName }</td>
					</c:when>
					<c:otherwise>
						<td align="left"><i><fmt:message key="c3pr.common.noSelection"/></i></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table> 
		
		<c:forEach items="${command.studySubject.childStudySubjects}" var="childStudySubject" varStatus="status">
			<c:if test="${newRegistration || (!newRegistration && !previous_epoch_enrollment_indicator )}">
				<h3><b>${childStudySubject.studySite.study.shortTitleText} (${childStudySubject.studySite.study.primaryIdentifier})</b></h3>
				<hr></hr>
				<table>
					<tr>
						<td align="right"><b><fmt:message key="registration.registrationIdentifier"/>:</b></td>
						<td align="left">${childStudySubject.coOrdinatingCenterIdentifier.value}</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="study.shortTitle"/>:</b></td>
						<td align="left">${childStudySubject.studySite.study.shortTitleText }</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="registration.registrationStatus"/>:</b></td>
						<td align="left">${childStudySubject.regWorkflowStatus.code}</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="c3pr.common.epoch"/>:</b></td>
						<td align="left">${childStudySubject.scheduledEpoch.epoch.name }</td>
					</tr>
					<c:if test="${!empty childStudySubject.scheduledEpoch.scheduledArm}">
						<tr>
							<td align="right"><b><fmt:message key="armAssignedLabel"/>:</b></td>
							<td align="left">${childStudySubject.scheduledEpoch.scheduledArm.arm.name}</td>
						</tr>
					</c:if>
					<tr>
						<td align="right"><b><fmt:message key="registration.enrollingSite"/>:</b></td>
						<td align="left">${childStudySubject.studySite.healthcareSite.name }</td>
					</tr>
					<tr>
						<td align="right"><b><fmt:message key="registration.startDate"/>:</b></td>
						<td align="left">${childStudySubject.startDateStr }</td>
					</tr>
					
					<c:forEach items="${childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="childStudySubjectConsentVersion" varStatus="status">
						<tr>
						<td align="right"><b>Informed Consent ${status.index+1}:</b></td>
						<td align="left">${childStudySubjectConsentVersion.informedConsentSignedDateStr} (${childStudySubjectConsentVersion.consent.name})</td>
					</tr>
					</c:forEach>
					
					<tr>
						<td align="right"><b><fmt:message key="registration.enrollingPhysician"/>:</b></td>
						<c:choose>
							<c:when test="${!empty childStudySubject.treatingPhysicianFullName}">
								<td align="left">${childStudySubject.treatingPhysicianFullName }</td>
							</c:when>
							<c:otherwise>
								<td align="left"><i><fmt:message key="c3pr.common.noSelection"/></i></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</table> 
			</c:if>
		</c:forEach>
	</div>

<form:form id="exportForm" method="post">
    <tags:tabFields tab="${tab}"/>
    <input type="hidden" name="_action" value="export"/>
</form:form>
<div id="confirmation-msg" style="display: none;">
	<div id="broadcastAction">
		<c:choose>
			<c:when test="${empty command.studySubject.cctsWorkflowStatus}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<fmt:message key="REGISTRATION.BROADCAST.NOT_YET_SENT"/>
				</div>
				<div align="center" style="padding-top: 20px">
					<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();" icon="check" id="broadcastButton" size="big"/>
					<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" id="cancelButton" size="big"/>
				</div>
			</c:when>
			<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND'}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<fmt:message key="REGISTRATION.BROADCAST.SENT_NO_RESPONSE"/>
				</div>
				<tags:button type="button "color="blue" value="Check response" onclick="javascript:getBroadcastStatus();"/>
				<tags:button type="button" color="red" icon="x" value="Later" onclick="window.location.reload();" />
			</c:when>
			<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND_CONFIRMED'}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<strong><font color="green">
						<fmt:message key="REGISTRATION.BROADCAST.SENT_SUCCESSFULLY"/>
					</font><br></strong><br>
				</div>
				<div align="center">
					<c3pr:checkprivilege hasPrivileges="STUDYSUBJECT_UPDATE,STUDYSUBJECT_CREATE">
						<div align="left" style="padding-left: 5px; font-size: 10pt;"><fmt:message key="REGISTRATION.BROADCAST.SENT_SUCCESSFULLY.RESEND"/></div>
						<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
					</c3pr:checkprivilege>
					<tags:button type="button" color="red" icon="x" value="Close" onclick="contentWin.close();" />
				</div>
			</c:when>
			<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<div style="float: left; padding: 5px;">
						<img src="<tags:imageUrl name='error.png'/>" alt="Calendar" border="0" align="middle"/>
					</div>
					<c:choose>
						<c:when test="${empty command.studySubject.cctsErrorString}"><fmt:message key="REGISTRATION.BROADCAST.SEND_ERROR"/></c:when>
						<c:otherwise><fmt:message key="REGISTRATION.BROADCAST.SEND_FAILED"/><div id="ccts-error-message"></div><script>$("ccts-error-message").update(C3PR.buildCCTSErrorHtml("${command.studySubject.cctsErrorString}"))</script></c:otherwise>
					</c:choose>
					<fmt:message key="BROADCAST.RESEND"/>
				</div>
				<div align="center" style="padding-top: 20px">
				<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
				<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
				</div>
			</c:when>
			<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_ACK_FAILED'}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<div style="float: left; padding: 5px;">
						<img src="<tags:imageUrl name='error.png'/>" alt="Calendar" border="0" align="middle"/>
					</div>
					<fmt:message key="REGISTRATION.BROADCAST.SENT_NO_ACK"/>
					<fmt:message key="BROADCAST.RESEND"/>
				</div>
				<div align="center" style="padding-top: 20px">
				<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
				<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
				</div>
			</c:when>
		</c:choose>
	</div>
	<div id="broadcastWait" align="center" style="display: none;">
		<div style="padding-top: 5px"><img src="/c3pr/images/broadcast_animation.gif"><div style="font-size: 15pt; padding-top: 5px">Please Wait... Sending</div></div>
	</div>
	<div id="broadcastResponseCheckWait" align="center" style="display: none;">
		<div style="padding-top: 5px"><img src="/c3pr/images/broadcast_animation.gif"><div style="font-size: 10pt; padding-top: 5px">Checking response. This may take few minutes...</div></div>
	</div>
	<div id="broadcastResponse">
	</div>	
</div>
</body>
</html>
