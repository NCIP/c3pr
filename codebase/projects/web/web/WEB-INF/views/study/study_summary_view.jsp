<%@ include file="taglibs.jsp" %>

<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}"/></title>
    <script language="JavaScript" type="text/JavaScript">

        function doExportAction() {
            document.viewDetails._action.value = "export";
            document.viewDetails.submit();
            document.viewDetails._action.value = "";
        }
        function getBroadcastStatus() {

            $('viewDetails').disable('broadcastBtn');
            $('viewDetails').disable('broadcastStatusBtn');

        <tags:tabMethod method="getMessageBroadcastStatus" onComplete="onBroadcastComplete"
            viewName="/ajax/broadcast_res" divElement="'broadcastResponse'"
            formName="'viewDetails'"/>
        }

        function doSendMessageToESB() {
            $('broadcastResponse').innerHTML = 'Sending Message...';


            $('viewDetails').disable('broadcastBtn');
            $('viewDetails').disable('broadcastStatusBtn');

        <tags:tabMethod method="sendMessageToESB" onComplete="onBroadcastComplete"
            viewName="/ajax/broadcast_res" divElement="'broadcastResponse'"
            formName="'viewDetails'"/>
        }

        function onBroadcastComplete() {
            $('viewDetails').enable('broadcastBtn');
            $('viewDetails').enable('broadcastStatusBtn');
        }


        function statusChangeCallback(statusCode) {
            elmt = document.getElementById('amendButtonDisplayDiv');
            if (statusCode == 'Active' || statusCode == 'Amendment Pending') {
                elmt.style.display = "";
            } else {
                elmt.style.display = "none";
            }
        }

        function reloadCompanion() {
        <tags:tabMethod method="reloadCompanion" divElement="'companionDiv'" formName="'tabMethodForm'"  viewName="/study/companionSection"/>
        }

		function closeStudy(closeStatus){
			$('closeStatus').value=closeStatus;
			changeStudyStatus('close');
		}
		
        function changeStudyStatus(status) {

        	if (${fn:length(errors)} > 0){
                if(status=='open'){
        			var d = $('errorsOpenDiv');
                }else  if(status=='readyToOpen'){
                	var d = $('errorsReadyToOpenDiv');
                }
        		Dialog.alert(d.innerHTML, 
        		{width:500, height:200, okLabel: "close", ok:function(win) {debug("validate alert panel"); return true;}});
        	} else if(status=='close') {
				Dialog.confirm("Are you sure you want to close the study?", 
				               {width:300, height:85, okLabel: "Ok",
				               ok:function(win) {$('statusChange').value = 'close';  $('command').submit();}
				              });
        	}else {
	            $('statusChange').value = status;
	            $('command').submit();
        	}

        }
    </script>
	<style>
		#main {
			top:33px;
		}
	</style>
</head>

<body>
<div id="controlPanel">
	<tags:controlPanel>
		<c:forEach items="${command.study.possibleStatusTransitions}" var="coCenterStatus">
            <c:if test="${coCenterStatus=='READY_TO_OPEN'}">
                <c:set var="readyToOpen" value="Create"></c:set>
            </c:if>
            <c:if test="${coCenterStatus=='OPEN' && !(command.study.companionIndicator && !command.study.standaloneIndicator)}">
                <c:set var="open" value="Open"></c:set>
            </c:if>
            <c:if test="${coCenterStatus=='CLOSED_TO_ACCRUAL'}">
                <c:set var="closed" value="Close"></c:set>
            </c:if>
            <c:if test="${coCenterStatus=='TEMPORARILY_CLOSED_TO_ACCRUAL'}">
                <c:set var="closed" value="Temporarily_Close"></c:set>
            </c:if>
        </c:forEach>
		<c:if test="${not empty editAuthorizationTask}">
			<csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
                <c:if test="${!empty open}">
                	<tags:oneControlPanelItem linkhref="javascript:changeStudyStatus('open');" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_openstudy.png" linktext="Open Study" />
                </c:if>
                <c:if test="${!empty readyToOpen}">
            		<tags:oneControlPanelItem linkhref="javascript:changeStudyStatus('readyToOpen')" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_readytoOpen.png" linktext="Ready to open" />
	            </c:if>
            </csmauthz:accesscontrol>
			<c:if test="${not empty flowType}">
				<tags:oneControlPanelItem linkhref="javascript:document.location='../study/viewStudy?studyId=${command.study.id}'" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_createStudy.png" linktext="Manage Study" />
			</c:if>
			<c:if test="${empty flowType}">
				<csmauthz:accesscontrol domainObject="${editAuthorizationTask}" authorizationCheckName="taskAuthorizationCheck">
	                	<c:choose>
		                    <c:when test="${command.study.companionIndicator=='true'}">
		                    	<tags:oneControlPanelItem linkhref="javascript:document.location='../study/editCompanionStudy?studyId=${command.study.id}'" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit Study" />
		                    </c:when>
		                    <c:otherwise>
		                    	<tags:oneControlPanelItem linkhref="javascript:document.location='../study/editStudy?studyId=${command.study.id}'" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_pencil.png" linktext="Edit Study" />
		                    </c:otherwise>
		                </c:choose>
	                    <c:if test="${command.study.standaloneIndicator && command.study.coordinatingCenterStudyStatus != 'PENDING'}">
	                    	<c:choose>
			                    <c:when test="${command.study.companionIndicator=='true'}">
			                    	<tags:oneControlPanelItem linkhref="javascript:document.location='../study/amendCompanionStudy?studyId=${command.study.id}'" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_createStudy.png" linktext="Amend Study" />
			                    </c:when>
			                    <c:otherwise>
			                    	<tags:oneControlPanelItem linkhref="javascript:document.location='../study/amendStudy?studyId=${command.study.id}'" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_createStudy.png" linktext="Amend Study" />
			                    </c:otherwise>
			                </c:choose>
	                     </c:if>
	                </csmauthz:accesscontrol>
				<tags:oneControlPanelItem linkhref="javascript:doExportAction();;" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_xml.png" linktext="Export XML" />
				<tags:oneControlPanelItem linkhref="javascript:launchPrint();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_printer.png" linktext="Print" />
			</c:if>
		</c:if>
	</tags:controlPanel>
</div>
<form:form>
    <input type="hidden" name="_target${tab.number}" id="_target"/>
    <input type="hidden" name="_page" value="${tab.number}" id="_page"/>
    <input type="hidden" name="statusChange" id="statusChange"/>
    <input type="hidden" name="closeStatus" id="closeStatus"/>
</form:form>
<form:form id="viewDetails" name="viewDetails">
<tags:tabFields tab="${tab}"/>
<c:if test="${not empty studyMessage}">
<font color='<fmt:message key="${ studyMessage}.COLOR"/>'><strong><fmt:message key="${studyMessage}"/></strong></font>
</c:if>
<div>
    <input type="hidden" name="_finish" value="true"/> 
    <input type="hidden" name="_action" value="">
</div>
<div id="summary">
<div id="printable">
<chrome:division id="study-details" cssClass="big" title="Study Details">
<div class="leftpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.shortTitle"/>:</div>
		<div class="value">${command.study.shortTitleText}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.primaryIdentifier"/>:</div>
		<div class="value">${command.study.primaryIdentifier}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.targetAccrual"/>:</div>
		<div class="value">
			<tags:inPlaceEdit value="${command.study.targetAccrualNumber}" path="study.changedTargetAccrualNumber" id="changedTargetAccrualNumber" validations="validate-notEmpty"/>
        	<csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE" authorizationCheckName="domainObjectAuthorizationCheck">
            	&nbsp; 
            	<tags:button type="button" color="blue" value="Edit" onclick="editor_changedTargetAccrualNumber.enterEditMode('click')" size="small"/>
        	</csmauthz:accesscontrol>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.phase"/>:</div>
		<div class="value">${command.study.phaseCode}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.dataEntryStatus"/>:</div>
		<div class="value">${command.study.dataEntryStatus.code}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.status"/>:</div>
		<div class="value">${command.study.coordinatingCenterStudyStatus.code}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.type"/>:</div>
		<div class="value">${command.study.type}</div>
	</div>
</div>
<div class="rightpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.multiInstitution"/>:</div>
		<div class="value">${command.study.multiInstitutionIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.blinded"/>:</div>
		<div class="value">${command.study.blindedIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.consentVersionDate"/>:</div>
		<div class="value">${command.study.consentVersion}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.stratified"/>:</div>
		<div class="value">${command.study.stratificationIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.randomized"/>:</div>
		<div class="value">${command.study.randomizedIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.randomizationType"/>:</div>
		<div class="value">${command.study.randomizationType.displayName}</div>
	</div>
</div>
</chrome:division>
<chrome:division title="Epochs &amp; Arms" cssClass="big" link="javascript:redirectToTab('${companionTab}')" condition="${not empty flowType}">
	<c:choose>
		<c:when test="${fn:length(command.study.epochs) >0}">
			<table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%"><b><fmt:message key="study.epochs"/></b></th>
		            <th><b><fmt:message key="study.epoch.arms"/></b>
		            </th>
		        </tr>
		        <c:forEach items="${command.study.epochs}" var="epoch">
		            <tr>
		                <td class="alt">${epoch.name}</td>
		                <c:if test="${not empty epoch.arms}">
		                    <td>
		                        <table border="0" cellspacing="0" cellpadding="0" class="tablecontent">
		
		                            <tr>
		                                <th><b><fmt:message key="c3pr.common.name"/></b></th>
		                                <th><b><fmt:message key="c3pr.common.targetAccrual"/></b>
		                                <th/>
		                            </tr>
		
		                            <tr>
		                                <c:forEach items="${epoch.arms}" var="arm">
		                                <tr>
		                                    <td>${arm.name}</td>
		                                    <td>${arm.targetAccrualNumber}</td>
		                                </tr>
		                                </c:forEach>
		                        </table>
		                    </td>
		                </c:if>
		            </tr>
		        </c:forEach>
		    </table>
		</c:when>
		<c:otherwise>
			<div align="left"><span><fmt:message key="study.noEpochsAvailable"/></span></div>
		</c:otherwise>
	</c:choose>
    
</chrome:division>
<chrome:division title="Diseases" cssClass="big" link="javascript:redirectToTab('${companionTab}')" condition="${not empty flowType}">
	<c:choose>
		<c:when test="${fn:length(command.study.studyDiseases) >0}">
		    <table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%" scope="col" align="left"><b><fmt:message key="study.diseaseTerm"/></b></th>
		            <th scope="col" align="left"><b><fmt:message key="c3pr.common.primary"/></b></th>
		        </tr>
		        <c:forEach items="${command.study.studyDiseases}" var="studyDisease" varStatus="status">
		            <tr class="results">
		                <td class="alt">${studyDisease.diseaseTerm.ctepTerm}</td>
		                <td class="alt">${studyDisease.leadDisease=="true"?"Yes":"No"}</td>
		            </tr>
		        </c:forEach>
		    </table>
	    </c:when>
	    <c:otherwise>
	    	<div align="left"><span><fmt:message key="study.noDiseasesAvailable"/></span></div>
	    </c:otherwise>
    </c:choose>
</chrome:division>
<chrome:division title="Identifiers" cssClass="big">
    <table class="tablecontent" width="70%">
        <tr>
            <th width="45%" scope="col" align="left"><fmt:message key="c3pr.common.assigningAuthority"/></th>
            <th width="35%" scope="col" align="left"><fmt:message key="c3pr.common.identifierType"/></th>
            <th scope="col" align="left" ><fmt:message key="c3pr.common.identifier"/></th>
        </tr>
        <c:if test="${command.study.coordinatingCenterAssignedIdentifier != null}">
        <tr class="results">
			<c:choose>
				<c:when test="${command.study.coordinatingCenterAssignedIdentifier.healthcareSite.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
            		<td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.healthcareSite.name} &nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/></td>
               </c:when>
			  <c:otherwise>
					<td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.healthcareSite.name} </td>
			  </c:otherwise>
			</c:choose>
           	<td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.type}</td>
            <td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.value}</td>
           </tr>
         </c:if>
         <c:if test="${command.study.fundingSponsorAssignedIdentifier != null}">
            <tr class="results">
				<c:choose>
				   <c:when test="${command.study.fundingSponsorAssignedIdentifier.healthcareSite.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
	            		<td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.healthcareSite.name} &nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/></td>
	               </c:when>
				   <c:otherwise>
						<td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.healthcareSite.name} </td>
				   </c:otherwise>
				</c:choose>
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.type}</td>
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.value}</td>
            </tr>
        </c:if>
         
    </table>
</chrome:division>
<div id="companionDiv">
<div id="companionAssociationsDiv" <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>>
    	<chrome:division title="Companion Studies" cssClass="big">
        <c:choose>
	        <c:when test="${fn:length(command.study.companionStudyAssociations)>0}">
	        	<table class="tablecontent" width="60%">
		            <tr>
		                <th width="45%" scope="col" align="left"><b><fmt:message key="study.companionStudyShortTitle"/></b></th>
		                <th width="30%" scope="col" align="left"><b><fmt:message key="c3pr.common.dataEntryStatus"/></b></th>
		                <th width="15%" scope="col" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
		                <th width="10%" scope="col" align="left"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
		            </tr>
		            <c:forEach items="${command.study.companionStudyAssociations}" var="companionStudyAssociation">
		                <tr>
		                    <td class="alt">${companionStudyAssociation.companionStudy.shortTitleText}</td>
		                    <td class="alt">${companionStudyAssociation.companionStudy.dataEntryStatus.code}</td>
		                    <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.code}</td>
		                    <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
		                    <td class="alt">
		                        <c:choose>
		                            <c:when test="${(companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'OPEN') || (companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'READY_TO_OPEN')}">
		                                <tags:button id="manageCompanionStudy" type="button" color="blue" value="View" 
											onclick="javascript:document.location='viewStudy?studyId=${companionStudyAssociation.companionStudy.id}'" size="small"/>
		                            </c:when>
		                            <c:otherwise>
		                                <c:if test="${not empty editAuthorizationTask}">
		                                    <csmauthz:accesscontrol domainObject="${editAuthorizationTask}"
		                                                            authorizationCheckName="taskAuthorizationCheck">
		                                        <tags:button id="editCompanionStudy" type="button" color="blue" value="Edit" 
													onclick="javascript:document.location='editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}'" size="small"/>
		                                    </csmauthz:accesscontrol>
		                                </c:if>
		                            </c:otherwise>
		                        </c:choose>
		                    </td>
		                </tr>
		            </c:forEach>
		        </table>
	        </c:when>
	        <c:otherwise>
	        	<div align="left"><span><fmt:message key="study.noCompanionsAvailable"/></span></div>
	        </c:otherwise>  
        </c:choose>
    </chrome:division>
</div>
</div>
<div <c:if test="${command.study.companionIndicator=='false' || (command.study.companionIndicator=='true' && command.study.standaloneIndicator=='true')}">style="display:none;"</c:if>>
    <chrome:division title="Parent Study" cssClass="big">
        <c:choose>
        	<c:when test="${fn:length(command.study.parentStudyAssociations) > 0}">
        		<table class="tablecontent" width="60%">
		            <tr>
		                <th width="50%" scope="col" align="left"><b><fmt:message key="study.shortTitle"/></b></th>
		                <th width="25%" scope="col" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
		            </tr>
		            <c:forEach items="${command.study.parentStudyAssociations}" var="parentStudyAssociation">
		                <tr>
		                    <td class="alt">${parentStudyAssociation.parentStudy.shortTitleText}</td>
		                    <td class="alt">${parentStudyAssociation.parentStudy.coordinatingCenterStudyStatus.code}</td>
		                    <td class="alt">
		                    	<tags:button id="manageParentStudy" type="button" color="blue" value="View" 
									onclick="javascript:document.location='viewStudy?studyId=${parentStudyAssociation.parentStudy.id}'" size="small"/>
		                    </td>
		
		                </tr>
		            </c:forEach>
		        </table>
        	</c:when>
        	<c:otherwise>
        		<div align="left"><span><fmt:message key="study.noParentStudyAvailable"/></span></div>
        	</c:otherwise>
        </c:choose>
            </chrome:division>
</div>
<c:if test="${fn:length(command.study.studyAmendments) > 0}">
<chrome:division title="Amendments" cssClass="big">
	<table class="tablecontent" width="60%">
	       <tr>
	           <th width="15%" scope="col" align="left"><fmt:message key="study.amendmentVersion"/></th>
	           <th width="30%" scope="col" align="left"><fmt:message key="study.amendmentDate"/></th>
	           <th width="55%" scope="col" align="left"><fmt:message key="c3pr.common.comments"/></th>
	       </tr>
	       <c:forEach items="${command.study.studyAmendments}" var="amendment">
	           <tr class="results">
	               <td class="alt" align="left">${amendment.amendmentVersion}</td>
	               <td class="alt" align="left">${amendment.amendmentDateStr}</td>
	               <td class="alt" align="left">${amendment.comments}</td>
	           </tr>
	       </c:forEach>
    </table>
</chrome:division>
</c:if>

<chrome:division title="Eligibilty Criteria" cssClass="big" />
	<c:forEach items="${command.study.epochs}" var="epoch">
		<c:if test="${fn:length(epoch.eligibilityCriteria)> 0}">
			<chrome:division title="${epoch.name}" cssClass="indented">
				<c:if test="${fn:length(epoch.inclusionEligibilityCriteria)> 0}">
					<h4>Inclusion Eligibility Criteria</h4>
					<br>
					<table class="tablecontent" width="70%"}">
				        <tr>
				            <th width="70%" scope="col" align="left"><b><fmt:message key="study.criterion"/></b></th>
				        </tr>
				        <c:forEach items="${epoch.inclusionEligibilityCriteria}" var="inclusionCriteria">	
					        <tr>
					        	<td class="alt">${inclusionCriteria.questionText}</td>
							</tr>
						</c:forEach>
			   	 	</table>
			   	 	<br>
				</c:if>
				<c:if test="${fn:length(epoch.exclusionEligibilityCriteria)> 0}">
					<h4>Exclusion Eligibility Criteria</h4>
					<br>
					<table class="tablecontent" width="70%"}">
				        <tr>
				            <th width="70%" scope="col" align="left"><b><fmt:message key="study.criterion"/></b></th>
				        </tr>
				        <c:forEach items="${epoch.exclusionEligibilityCriteria}" var="exclusionCriteria">
					        <tr>
					        	<td class="alt">${exclusionCriteria.questionText}</td>
							</tr>
						</c:forEach>
				    </table>
				    <br>
				</c:if>
			</chrome:division>
		</c:if>
    </c:forEach>
<chrome:division title="Stratum Groups" cssClass="big"/>
	<c:forEach items="${command.study.epochs}" var="epoch">
		<c:if test="${fn:length(epoch.stratumGroups)> 0}">
			<chrome:division title="${epoch.name}" cssClass="indented">
				<c:if test="${fn:length(epoch.inclusionEligibilityCriteria)> 0}">
					<table class="tablecontent" width="70%"}">
				        <tr>
				            <th width="50%" scope="col" align="left"><b><fmt:message key="registration.stratumGroupNumber"/></b></th>
            				<th scope="col" align="left"><b><fmt:message key="study.answerCombination"/></b></th>
				        </tr>
				        <c:forEach items="${epoch.stratumGroups}" var="stratGrp">	
					        <tr>
					        	<td class="alt">${stratGrp.stratumGroupNumber}</td>
                    			<td class="alt">${stratGrp.answerCombinations}</td>
							</tr>
						</c:forEach>
			   	 	</table>
			   	 	<br>
				</c:if>
			</chrome:division>
		</c:if>
    </c:forEach>
</div>
<c:if test="${command.study.coordinatingCenterStudyStatus == 'OPEN' && isCCTSEnv}">
    <chrome:division title="CCTS Workflow" cssClass="big"/>
        <div class="content">
            <div class="row">
                <table width="60%">
                    <tr>
                        <td width="15%" align="right">
                            <b>Broadcast Status:</b>
                        </td>
                        <td width="85%" align="left">
                            <div id="broadcastResponse">
                                    ${!empty command.study.cctsWorkflowStatus.displayName?command.study.cctsWorkflowStatus.displayName:'The study has not yet been broadcasted. Click on the broadcast button to create study in other CTMS Application'}
                                <c:if test="${command.study.cctsWorkflowStatus=='MESSAGE_SEND_FAILED'}">
                                    <a href="javascript:C3PR.showCCTSError();">Click here to see the error message</a>

                                    <div id="cctsErrorMessage"
                                         style="display: none;">${command.study.cctsErrorString}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                        	<tags:button id="broadcastStatusBtn" type="button" color="blue" value="Refresh" 
							onclick="getBroadcastStatus();" size="small"/>
							<tags:button id="broadcastBtn" type="button" color="blue" value="Broadcast" 
							onclick="doSendMessageToESB();" size="small"/>
                            
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="built-cctsErrorMessage" style="display: none;"/>
</c:if>
<div class="content buttons autoclear">
        <div class="flow-buttons">
        <span class="next">
<c:if test="${not empty editAuthorizationTask}">
        	<csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE"
                                    authorizationCheckName="domainObjectAuthorizationCheck">
	            <c:if test="${!empty closed}">
	            	<tags:button type="button" color="blue" value="Close Study" id="closeStudy"
							onclick="Effect.SlideDown('close-choices')" size="small"/>
					<div id="close-choices" class="autocomplete" style="display: none">
						<ul>
							<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="closeStudy('Closed_To_Accrual_And_Treatment')">Closed To Accrual And Treatment</li>
							<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="closeStudy('Closed_To_Accrual')">Closed To Accrual</li>
							<c:if test="${closed == 'Temporarily_Close'}">
							<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="closeStudy('Temporarily_Closed_To_Accrual_And_Treatment')">Temporarily Closed To Accrual And Treatment</li>
							<li onmouseover="this.className='selected'" onmouseout="this.className=''" onclick="closeStudy('Temporarily_Closed_To_Accrual')">Temporarily Closed To Accrual</li>
							</c:if>
						</ul>
						<div align="right"><tags:button type="button" color="red" value="Cancel" icon="x"
							onclick="Effect.SlideUp('close-choices')" size="small"/></div>
					</div>
	            </c:if>
            </csmauthz:accesscontrol>
</c:if>
<br/>
</span></div></div>

<div id="errorsOpenDiv" style="display:none">
	<div class="value" align="left">
		<font size="2" face="Verdana" color="red">
			Cannot Open Study. Please review the data.
		</font>
	</div>
	
	<br>
	
	<c:forEach items="${errors}" var="error" >
		<div class="value" align="left">
			<font size="1" face="Verdana" color="black">
				${error.errorMessage}
			</font>
		</div>
	</c:forEach>
</div>

<div id="errorsReadyToOpenDiv" style="display:none">
	<div class="value" align="left">
		<font size="2" face="Verdana" color="red">
			Cannot Create Study. Please review the data.
		</font>
	</div>
	
	<br>
	
	<c:forEach items="${errors}" var="error" >
		<div class="value" align="left">
			<font size="1" face="Verdana" color="black">
				${error.errorMessage}
			</font>
		</div>
	</c:forEach>
</div>
</div>
</form:form>
</body>
</html>
