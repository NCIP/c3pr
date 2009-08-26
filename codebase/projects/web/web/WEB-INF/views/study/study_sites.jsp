<%@ include file="taglibs.jsp"%>
<link href="themes/mac_os_x.css" rel="stylesheet" type="text/css"/>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
	<script type="text/javascript">
	var nciCode ;
	function updateStudy() {
		$('openSections').value = getOpenSectionsStr();
	    document.getElementById("studySitesForm").submit();
	}

	function getOpenSectionsStr(){
		var strHiddenDiv = '' ;
		$$('.hiddenDiv').each(function(element){
			if(element.style.display != 'none'){
				strHiddenDiv = strHiddenDiv + '|' +element.id.substring(element.id.indexOf("-") + 1, element.id.length) ;
			}
		});
		return strHiddenDiv;
	}

	function deleteStudySite(nciCode){
		<tags:tabMethod method="deleteStudySite" divElement="'studySites'" formName="'tabMethodForm'"  viewName="/study/asynchronous/delete_study_site_section" javaScriptParam="'nciCode='+nciCode+'&openSections='+getOpenSectionsStr()" /> ;
	}

	function addStudySite(){
		nciCode = $('studysite-hidden').value ;
		var alreadyExist = false ;
		$$('.divisonClass').each(function(element){
			if(element.id.indexOf(nciCode) !=-1){
				alreadyExist = true ;
			}
		});
		if(alreadyExist){
			Dialog.alert("Study site already exist", {className: "alphacube", width:240, okLabel: "Done" });
			return;
		}
		// check if this already exists
		<tags:tabMethod method="addStudySite" divElement="'newStudySite'" formName="'tabMethodForm'"  viewName="/study/asynchronous/add_study_site_section" javaScriptParam="'nciCode='+nciCode" onComplete="refreshStudySiteSection" /> ;
	}

	function refreshStudySiteSection(){
		if($('startDate-'+nciCode) != null){
			Element.insert($('studySites'), { bottom: $('newStudySite').innerHTML })
			$('studysite-hidden').value = '' ;
			$('studysite-input').value = '' ;
			$('addStudySite').disabled=true ;
			inputDateElementLocal1="startDate-"+nciCode;
			inputDateElementLink1="startDate-"+nciCode+"-calbutton";
			Calendar.setup(
			{
			    inputField  : inputDateElementLocal1,         // ID of the input field
			    ifFormat    : "%m/%d/%Y",    // the date format
			    button      : inputDateElementLink1       // ID of the button
			}
			);
			inputDateElementLocal="irbApprovalDate-"+nciCode;
	       	inputDateElementLink="irbApprovalDate-"+nciCode+"-calbutton";
	       	Calendar.setup(
	       	{
	       	    inputField  : inputDateElementLocal,         // ID of the input field
	       	    ifFormat    : "%m/%d/%Y",    // the date format
	       	    button      : inputDateElementLink       // ID of the button
	       	}
	       	);
	       	$('siteIndicator').hide();
	       	$('divison-'+nciCode).scrollIntoView();

		}
	}

	var multisiteStudySiteAutocompleterProps = {
	    basename: "studysite",
	    populator: function(autocompleter, text) {
	        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
	            autocompleter.setChoices(values)
	        })
	    },
	    valueSelector: function(obj) {
	    	if(obj.externalId != null){
	    		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
	    	} else {
	    		image = '';
	    	}
	    	 return (obj.name+" ("+obj.ctepCode+")" + image)
	    },
	    afterUpdateElement:
		    function(inputElement, selectedElement, selectedChoice) {
	    			hiddenField=inputElement.id.split("-")[0]+"-hidden";
		    		$(hiddenField).value=selectedChoice.ctepCode;
		    		$('addStudySite').disabled=false ;
		}
	}

	function takeAction(nciCode, action){
		submitStr='action=' + action+'&nciCode='+nciCode+'&DO_NOT_SAVE=true';
		if($("irbApprovalDate-"+nciCode)){
			submitStr+='&'+$("irbApprovalDate-"+nciCode).name+'='+$("irbApprovalDate-"+nciCode).value;
		}
		if($("startDate-"+nciCode)){
			submitStr+='&'+$("startDate-"+nciCode).name+'='+$("startDate-"+nciCode).value;
		}
		if($("targetAccrual-"+nciCode)){
			submitStr+='&'+$("targetAccrual-"+nciCode).name+'='+$("targetAccrual-"+nciCode).value;
		}
		<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" onFailure='failedStatusChange' viewName="/study/asynchronous/updatedStudySiteSection" divElement="'siteSection_'+nciCode" javaScriptParam="submitStr"/>
		Element.show('sendingMessage-'+nciCode);
	}

	function changeCompanionStudySiteStatus(nciCode){
		action=$("companionSiteAction-"+nciCode).value;
		<tags:tabMethod method="changeStatus" formName="'tabMethodForm'" onFailure='failedStatusChange' viewName="/study/asynchronous/companionSites_row" divElement="'dummy-div'" javaScriptParam="'action=' + action+ '&nciCode='+nciCode+ '&studySiteType=companionSite&DO_NOT_SAVE=true'" />
		Element.show('companionSendingMessage-'+nciCode);
	}

	failedStatusChange= function (responseXML){
										Dialog.alert(responseXML.responseText,
							             {width:600, height:600, okLabel: "Close",
							              ok:function(win) {$('reload').submit(); return true;}});
									}

	AutocompleterManager.addAutocompleter(multisiteStudySiteAutocompleterProps);

	var win;
	function selectStudySites(studyId, parentAssociationId, parentIndex){
		win = new Window({title: "Select Study Sites",
				scrollbar: false, zIndex:100, width:900, height:325 ,
				recenterAuto:true, className :"mac_os_x",
				url: "<c:url value='/pages/study/selectStudySites?decorator=noheaderDecorator&parentAssociationId='/>" + parentAssociationId  +"&parentIndex=" + parentIndex  +"&studyId=" + studyId ,
				showEffectOptions: {duration:1.5}
					}
				)
		win.showCenter(true)
	}

	function closePopup() {
		win.close();
	}

	function reloadParentStudySites(studyId , studyAssociationId , nciCodes , parentIndex, irbApprovalSites){
		$('nciCodes').value=nciCodes;
		$('irbApprovalSites').value=irbApprovalSites;
		$('studyAssociationId').value=studyAssociationId;
		<tags:tabMethod method="associateParentStudySites" divElement="'parentStudySiteDiv-'+parentIndex" formName="'parentStudySiteForm'"  viewName="/study/parentStudySiteSection" javaScriptParam="'parentIndex='+parentIndex"/>
	}

	function deleteCompanionStudySiteAssociation(studySiteId, parentIndex){
		$("_doNotSave").name="xyz";
		<tags:tabMethod method="removeCompanionStudyAssociation" divElement="'parentStudySiteDiv-'+parentIndex" formName="'parentStudySiteForm'"  viewName="/study/parentStudySiteSection" javaScriptParam="'studySiteId='+studySiteId+'&parentIndex='+parentIndex"/>
		$("_doNotSave").name="_doNotSave";
	}
	</script>
</head>
<body>
	<form:form id="reload">
		<input type="hidden" name="_target${tab.number}" id="_target"/>
		<input type="hidden" name="_page" value="${tab.number}" id="_page"/>
	</form:form>
	<div id="dummy-div" style="display: none;" ></div>
	<tags:panelBox title="Sites">
		<div class="row">
			<div class="name">
				<tags:autocompleter name="axxxxyyy" displayValue="" value="" basename="studysite" size="90"></tags:autocompleter>
				<tags:button color="blue" value="Add study site" icon="add" type="button" size="small" id="addStudySite" onclick="$('siteIndicator').show();addStudySite();" disabled="true"></tags:button>
				<img id="siteIndicator" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none">
			</div>
		</div>
		<br>
		<form:form id="studySitesForm">
			<input type="hidden" name="submitted" value="true"/>
			<input type="hidden" name="openSections" id="openSections"/>
			<tags:errors path="study.studySites" />
			<div id="studySites">
				<studyTags:studySitesSection commandObj="${command}"></studyTags:studySitesSection>
			</div>
			<!--  This section belongs to study site for companion study associations -->
			<div id="companionStudyAssocition-studySite" <c:if test="${!command.study.companionIndicator}">style="display:none;"</c:if>>
			 	<c:forEach items="${command.study.parentStudyAssociations}" var="parentStudyAssociation" varStatus="parentStudySiteStaus">
			 		<div id="parentStudySiteDiv-${parentStudySiteStaus.index}">
						<chrome:division title="${parentStudyAssociation.parentStudy.shortTitleText}">
							<c:forEach items="${parentStudyAssociation.parentStudy.studySites}" varStatus="status" var="site">
								<chrome:division title="${site.healthcareSite.name} (${site.healthcareSite.ctepCode})" minimize="true" divIdToBeMinimized="site-${status.index}" >
									<div id="companionsite-${status.index}" style="display:none;">
										<div class="row">
											<div class="leftpanel">
												<div class="row">
													<div class="label"><fmt:message key="site.IRBApprovalDate" /></div>
													<div class="value" id="companionSiteIRB-${site.healthcareSite.ctepCode }">
														<tags:dateInput path="study.studySites[${status.index}].irbApprovalDate"/>
													</div>
												</div>
												<div class="row">
													<div class="label"><fmt:message key="c3pr.common.targetAccrual" /></div>
													<div class="value">
														<form:input path="study.studySites[${status.index}].targetAccrualNumber" maxlength="6" cssClass="validate-NUMERIC" size="6"/>
													</div>
												</div>
												<div class="row" id="companionActions-${status.index}">
													<div class="label"><fmt:message key="site.actions" /></div>
													<div class="value" id="companionActions-${site.healthcareSite.ctepCode }">
					           							<c:set var="noAction" value="true"/>
					           							<c:if test="${fn:length(site.possibleTransitions)>0 && (site.hostedMode || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode || localNCICode==site.healthcareSite.ctepCode)}">
				           									<c:forEach items="${site.possibleTransitions}" var="possibleAction">
				           										<c:choose>
				  														<c:when test="${possibleAction=='ACTIVATE_STUDY_SITE'}">
				  															<%--<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.ctepCode && (site.siteStudyStatus=='APPROVED_FOR_ACTIVTION' || localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode))}">--%>
				  															<c:if test="${site.hostedMode || (localNCICode==site.healthcareSite.ctepCode && localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode)}">
				  																<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="takeAction('${site.healthcareSite.ctepCode}', '${possibleAction}', '${status.index}');" size="small"/>
				  																<c:set var="noAction" value="false"/>
				  															</c:if>
				  														</c:when>
				  														<%--<c:when test="${possibleAction=='APPROVE_STUDY_SITE_FOR_ACTIVATION'}">
				  															<c:if test="${localNCICode==site.study.studyCoordinatingCenters[0].healthcareSite.ctepCode}">
				  																<option value="${possibleAction}">${possibleAction.displayName }</option>
				  																<c:set var="noAction" value="false"/>
				  															</c:if>
				  														</c:when>--%>
									   								<c:otherwise>
											   							<tags:button type="button" color="blue" value="${possibleAction.displayName }" id="${possibleAction}" onclick="takeAction('${site.healthcareSite.ctepCode}', '${possibleAction}', '${status.index}');" size="small"/>
											   							<c:set var="noAction" value="false"/>
											   						</c:otherwise>
																</c:choose>
				           									</c:forEach>
														</c:if>
														<div id="companionSendingMessage-${site.healthcareSite.ctepCode }" class="working" style="display: none">
															Working...<img src="<tags:imageUrl name='indicator.white.gif'/>" border="0" alt="sending.."/>
														</div>
													</div>
													<c:if test="${noAction}">
														<script>
															Element.hide('actions-'+${status.index});
														</script>
													</c:if>
												</div>
												<div class="row" id="companionMessage-${status.index}">
													<div class="label"><fmt:message key="site.messages" /></div>
													<div class="value" id="companionMessages-${site.healthcareSite.ctepCode }">
														<c:choose>
															<c:when test="${!site.hostedMode && !site.isCoordinatingCenter && fn:length(siteEndpoint.endpoints)>0}">
																<c:choose>
																	<c:when test="${siteEndpoint.lastAttemptedEndpoint.status=='MESSAGE_SEND_FAILED'}">
																		<font color="red">${siteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
																		Click <a href="javascript:showEndpointError('${siteEndpoint.healthcareSite.ctepCode }','${site.healthcareSite.ctepCode }');">here</a> to see the error messages
																	</c:when>
																	<c:otherwise>
																		<font color="green">${siteEndpoint.lastAttemptedEndpoint.status.code}</font><br>
																		Click <a href="javascript:showEndpointError('${siteEndpoint.healthcareSite.ctepCode }','${site.healthcareSite.ctepCode }');">here</a> to see the messages
																	</c:otherwise>
																</c:choose>
															</c:when>
															<c:otherwise>
																None
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</div>
											<div class="rightpanel">
												<div class="row">
													<div class="label"><fmt:message key="site.activationDate" /></div>
													<div class="value">
														<tags:dateInput path="study.studySites[${status.index}].startDate"/>
													</div>
												</div>
												<div class="row">
													<c:if test="${multisiteEnv}">
														<div class="label"><fmt:message key="site.hostedMode" /></div>
														<div class="value">
									            			<form:checkbox path="study.studySites[${status.index}].hostedMode"/>
					           								<input type="hidden" name="${command.study.studySites[status.index].healthcareSite.ctepCode}-wasHosted" value="${command.study.studySites[status.index].hostedMode}"/>
														</div>
					   			        			</c:if>
												</div>
												<div class="row">
													<div class="label"><fmt:message key="c3pr.common.status" /></div>
													<div class="value" id="companionSiteStatus-${site.healthcareSite.ctepCode }">${site.siteStudyStatus.code}</div>
												</div>
											</div>
										</div>
									</div>
								</chrome:division>
								<div class="division"></div>
							</c:forEach>
						</chrome:division>
						<br>
					</div>
					<br>
					<div class="flow-buttons">
			            <span class="next">
			            	<tags:button type="button" color="blue" value="Select From Parent" onclick="selectStudySites(${command.study.id},${parentStudyAssociation.id}, ${parentStudySiteStaus.index})" size="small"/>
			 			</span>
		        	</div>
					<br>
				</c:forEach>
			</div>
			</form:form>
			<div id="newStudySite" style="display: none" ></div>
		</tags:panelBox>
		<div class="flow-buttons">
		    <span class="next">
				<tags:button color="green" value="Save" icon="save" id="save" type="button" onclick="updateStudy();"></tags:button>
			</span>
		</div>
</body>
</html>