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

		

		function takeSubjectOffStudyPopup(){
			var arr= $$("#takeSubjectOffStudy");
			win = new Window({className :"mac_os_x", title: "Take Subject Off Study", 
									hideEffect:Element.hide, 
									zIndex:100, width:600, height:300 , minimizable:false, maximizable:false, destroyOnClose: true, recenterAuto:true,
									showEffect:Element.show 
									})
			win.setContent(arr[0]) ;
			win.showCenter(true);
			inputDateElementLocal1="offStudyDate";
			inputDateElementLink1="offStudyDate-calbutton";
			Calendar.setup(
			{
			    inputField  : inputDateElementLocal1,         // ID of the input field
			    ifFormat    : "%m/%d/%Y",    // the date format
			    button      : inputDateElementLink1       // ID of the button
			}
			);
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

        Event.observe(window, "load", function() {
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
</head>
<body>
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
<div id="summary">
<br/>
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
<chrome:division id="Subject Information" title="Subject">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.firstName"/>:</div>
            <div class="value">${command.participant.firstName}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.lastName"/>:</div>
            <div class="value">${command.participant.lastName}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.gender"/>:</div>
            <div class="value">${command.participant.administrativeGenderCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.medicalRecordNumber"/>:</div>
            <div class="value">${command.participant.MRN.value }</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="participant.birthDate"/>:</div>
            <div class="value">${command.participant.birthDateStr}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.ethnicity"/>:</div>
            <div class="value">${command.participant.ethnicGroupCode}</div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="participant.race"/>:</div>
            	<c:forEach items="${command.participant.raceCodes}" var="raceCode">
		                <div class="value">${raceCode.displayName}</div>
		        </c:forEach>

        </div>
    </div>
</chrome:division>
<chrome:division title="Identifiers">
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
            <div class="label"><fmt:message key="c3pr.common.epoch"/>:</div>
            <div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
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
                <div class="label"><fmt:message key="registration.offStudyReason"/>:</div>
                <div class="value">
   		             <tags:inPlaceEdit value="${command.studySubject.offStudyReasonText}" path="studySubject.offStudyReasonText" id="offStudyReasonText" validations="validate-notEmpty" disable="${!canEditRegistrationRecord}"/>
                </div>
            </div>
            <div class="row">
                <div class="label"><fmt:message key="registration.offStudyDate"/>:</div>
                <div class="value">
                <tags:inPlaceEdit value="${command.studySubject.offStudyDateStr}" path="studySubject.offStudyDate" id="offStudyDate" validations="validate-notEmpty&&DATE" disable="${!canEditRegistrationRecord}"/>
                </div>
            </div>
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
							    <%-- <input type="hidden" name="value" class="editor_field" id="diseaseSite-hidden">
<input type="text" class="autocomplete" id="diseaseSite-input">
<tags:indicator id="diseaseSite-indicator" />
<div id="diseaseSite-choices" class="autocomplete"></div>--%>
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
    </script>
    
</chrome:division>
<chrome:division title="Informed Consents" inPlaceLinkId="editInPlaceForInformedConsent" condition="${canEditRegistrationRecord}">
	<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
		<div class="row">
			<div class="label"><b>${studySubjectConsentVersion.consent.name}</b>:</div>
			<div class="value">
				<c:choose>
					<c:when test="${studySubjectConsentVersion.informedConsentSignedDateStr != null && studySubjectConsentVersion.informedConsentSignedDateStr != ''}"> 
						<tags:inPlaceEdit value="${studySubjectConsentVersion.informedConsentSignedDateStr}" path="studySubject.studySubjectConsentVersions[${status.index}].informedConsentSignedDate" 
								id="informedConsentSignedDate_${status.index}" validations="validate-notEmpty&&DATE" disable="${!canEditRegistrationRecord}"/>
					</c:when>
					<c:otherwise>
						<font color="red"><i>not signed</i></font>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:forEach>
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
    <c:set var="commanSepOptVal" value="${commanSepOptVal}[ 'Yes'],"></c:set>
    <c:set var="commanSepOptVal" value="${commanSepOptVal}[ 'No'],"></c:set>
    <c:set var="commanSepOptVal" value="${commanSepOptVal}[ 'Not Applicable']"></c:set>
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
	                          	<tags:inPlaceSelect value="${eligibilityAnswer.answerText}" path="studySubject.scheduledEpoch.inclusionEligibilityAnswers[${status.index}].answerText" id="inclusionAnswerText_${status.index}"
                                    commanSepOptVal="${commanSepOptVal}" onComplete="updateEligibility" disable="${!canEditRegistrationRecord}"/>
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
<div id="epochSelection" style="display:none;">
<div id="changeEpoch" >
	<%@ include file="reg_change_epoch.jsp"%>
</div>
</div>
<div id="takeSubjectOffStudyPage" style="display:none;">
<div id="takeSubjectOffStudy" >
	<%@ include file="take_subject_offstudy.jsp"%>
</div>
</div>
<div id="invalidateRecordPage" style="display:none;">
<div id="invalidateRecord" >
	<%@ include file="invalidate_registration_record.jsp"%>
</div>
</div>
<div id="printable" style="display:none;">
	<chrome:division id="Subject Information" title="Subject">
			<div class="row">
				<div class="label"><fmt:message key="participant.fullName"/>:</div>
				<div class="value">${command.participant.fullName}</div>
			</div>
			<div class="row">
				<div class="label"><fmt:message key="participant.MRN"/>:</div>
				<div class="value">${command.participant.MRN.value }</div>
			</div>
		</chrome:division>
		<chrome:division id="Parent Registration Information" title="${command.studySubject.studySite.study.shortTitleText} (${command.studySubject.studySite.study.primaryIdentifier})">
			<div class="row">
	            <div class="label"><b><fmt:message key="registration.registrationIdentifier"/></b>:</div>
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
				<div class="label"><b><fmt:message key="study.shortTitle"/></b>:</div>
				<div class="value">${command.studySubject.studySite.study.shortTitleText}</div>
			</div>
			<div class="row">
				<div class="label"><b><fmt:message key="registration.registrationStatus"/></b>:</div>
				<div class="value">${command.studySubject.regWorkflowStatus.code }</div>
			</div>		
			<div class="row">
				<div class="label"><b><fmt:message key="c3pr.common.epoch"/></b>:</div>
				<div class="value">${command.studySubject.scheduledEpoch.epoch.name}</div>
			</div>
			<c:if test="${!empty armAssigned}">
				<div class="row">
					<div class="label"><b>${armAssignedLabel }</b>:</div>
					<div class="value">${armAssigned}</div>
				</div>
			</c:if>
			<div class="row">
				<div class="label"><b><fmt:message key="registration.enrollingSite"/></b>:</div>
				<div class="value">${command.studySubject.studySite.healthcareSite.name}</div>
			</div>
			<div class="row">
				<div class="label"><b><fmt:message key="registration.startDate"/></b>:</div>
				<div class="value">${command.studySubject.startDateStr}</div>
			</div>
			<c:forEach items="${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="studySubjectConsentVersion" varStatus="status">
				<div class="row">
					<div class="label"><b>Informed Consent ${status.index+1}</b>:</div>
					<div class="value">${studySubjectConsentVersion.informedConsentSignedDateStr} (${studySubjectConsentVersion.consent.name})</div>
				</div>
			</c:forEach>
			<div class="row">
				<div class="label"><b><fmt:message key="registration.enrollingPhysician"/></b>:</div>
				<c:choose>
					<c:when test="${!empty command.studySubject.treatingPhysicianFullName}">
						<div class="value">
            				${command.studySubject.treatingPhysicianFullName} 
						</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
					</c:otherwise>
				</c:choose>
			</div>
		</chrome:division>
		<c:forEach items="${command.studySubject.childStudySubjects}" var="childStudySubject" varStatus="status">
			<c:if test="${newRegistration || (!newRegistration && !previous_epoch_enrollment_indicator )}">
			<chrome:division id="companionRegInfo${status.index}" title="${childStudySubject.studySite.study.shortTitleText} (${childStudySubject.studySite.study.primaryIdentifier})">
				<div class="row">
		            <div class="label"><b><fmt:message key="registration.registrationIdentifier"/></b>:</div>
					<c:choose>
						<c:when test="${!empty childStudySubject.coOrdinatingCenterIdentifier.value}">
							<div class="value">${childStudySubject.coOrdinatingCenterIdentifier.value}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notGenerated"/></span></div>
						</c:otherwise>
					</c:choose>
		        </div>
				<div class="row">
					<div class="label"><b><fmt:message key="study.shortTitle"/></b>:</div>
					<div class="value">${childStudySubject.studySite.study.shortTitleText}</div>
				</div>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.registrationStatus"/></b>:</div>
					<div class="value">${childStudySubject.regWorkflowStatus.code }</div>
				</div>		
				<div class="row">
					<div class="label"><b><fmt:message key="c3pr.common.epoch"/></b>:</div>
					<div class="value">${childStudySubject.scheduledEpoch.epoch.name}</div>
				</div>
				<c:if test="${!empty armAssigned}">
					<div class="row">
						<div class="label"><b>${armAssignedLabel }</b>:</div>
						<div class="value">${armAssigned}</div>
					</div>
				</c:if>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.enrollingSite"/></b>:</div>
					<div class="value">${childStudySubject.studySite.healthcareSite.name}</div>
				</div>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.startDate"/></b>:</div>
					<div class="value">${childStudySubject.startDateStr}</div>
				</div>
				<c:forEach items="${childStudySubject.studySubjectStudyVersion.studySubjectConsentVersions}" var="childStudySubjectConsentVersion" varStatus="status">
					<div class="row">
						<div class="label"><b>Informed Consent ${status.index+1}</b>:</div>
						<div class="value">${childStudySubjectConsentVersion.informedConsentSignedDateStr} (${childStudySubjectConsentVersion.consent.name})</div>
					</div>
				</c:forEach>
				<div class="row">
					<div class="label"><b><fmt:message key="registration.enrollingPhysician"/></b>:</div>
					<c:choose>
						<c:when test="${!empty childStudySubject.treatingPhysicianFullName}">
							<div class="value">${childStudySubject.treatingPhysicianFullName}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noSelection"/></span></div>
						</c:otherwise>
					</c:choose>
				</div>
			</chrome:division>
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
				<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
				<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
				</div>
			</c:when>
			<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND'}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<fmt:message key="REGISTRATION.BROADCAST.SENT_NO_RESPONSE"/>
				</div>
				<div align="center" style="padding-top: 20px">
				<tags:button type="button "color="blue" value="Check response" onclick="javascript:getBroadcastStatus();"/>
				<tags:button type="button" color="red" icon="x" value="Later" onclick="window.location.reload();" />
				</div>
			</c:when>
			<c:when test="${command.studySubject.cctsWorkflowStatus=='MESSAGE_SEND_CONFIRMED'}">
				<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
					<strong><font color="green">
						<fmt:message key="REGISTRATION.BROADCAST.SENT_SUCCESSFULLY"/>
					</font><br></strong><br>
				</div>
				<div align="center">
					<c:if test="${isAdmin}">
						<div align="left" style="padding-left: 5px; font-size: 10pt;"><fmt:message key="REGISTRATION.BROADCAST.SENT_SUCCESSFULLY.RESEND"/></div>
						<tags:button type="button "color="blue" value="Yes" onclick="javascript:doSendMessageToESB();"/>
					</c:if>
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
