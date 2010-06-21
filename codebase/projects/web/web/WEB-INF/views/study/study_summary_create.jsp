<%@ include file="taglibs.jsp" %>
<html>
<head>
 <title><studyTags:htmlTitle study="${command.study}"/></title>
 <style>
    .dialog{
    	overflow:hidden;
    }
    </style>
<script>

function redirectToTab(tabNumber){
	if(tabNumber != ''){
		document.getElementById('flowredirect-target').name='_target'+tabNumber;
		document.getElementById('flowredirect').submit()
	}
}

function activateAndSaveStudy(){
	if (${fn:length(errors)} > 0){
		var d = $('errorsOpenDiv');
		Dialog.alert(d.innerHTML, {className: "alphacube", width:300, okLabel: "Close" });
	} else {
		contentWin = new Window({className :"mac_os_x", title: "Confirm", 
								hideEffect:Element.hide, 
								zIndex:100, width:400, height:180 , minimizable:false, maximizable:false,
								showEffect:Element.show 
								}); 
		//contentWin = new Window({ width:350, height:180 ,className :"alert_lite"})
		if(${command.study.coordinatingCenterStudyStatus=='PENDING'}){
   			contentWin.setContent('openWithDateMessage') ;
   		}else{
   			contentWin.setContent('openMessage') ;
   		}
		contentWin.showCenter(true);
	}
}

function openStudy(){
	if(${command.study.coordinatingCenterStudyStatus=='PENDING'}){
		$('study.studyVersion.versionDate').value=$('studyOpenDate').value;
	}
	document.getElementById("_activate").value="true";
	document.getElementById("_action").value="open";
	document.getElementById("viewDetails").submit();
}

function closePopup() {
	win.close();
}

ValidationManager.submitPostProcess=function(formElement, flag){
	if(formElement.id =='targetAccrualForm' && flag ){
		<tags:tabMethod method="updateTargetAccrual" divElement="'targetAccrualDiv'" formName="'targetAccrualForm'"  viewName="/study/asynchronous/target_accrual_section" onComplete="closePopup"/>
		Element.show('flash-message-targetaccrual');
		return false;
	}
	return flag;
}


function updateTargetAccrual(){
	Element.hide('flash-message-targetaccrual');
		var arr= $$("#targetAccrual");
		win = new Window({className :"mac_os_x", title: "Update Target Accrual",
								hideEffect:Element.hide,
								zIndex:100, width:500, height:150 , minimizable:false, maximizable:false, closable : false,
								showEffect:Element.show
								})
		win.setContent(arr[0]) ;
		win.showCenter(true);
}
</script>
</head>
<body>
<c:forEach items="${flow.tabs}" var="tabLink" varStatus="status">
		<c:if test="${tabLink.shortTitle == 'Epochs & Arms'}">
			<c:set var="epochTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Eligibility'}">
			<c:set var="eligibilityTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Stratification'}">
			<c:set var="stratificationTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Diseases'}">
			<c:set var="diseaseTab" value="${status.index}"/>
		</c:if>
		<c:if test="${tabLink.shortTitle == 'Companion Studies'}">
			<c:set var="companionTab" value="${status.index}"/>
		</c:if>
	</c:forEach>
<div id="controlPanel">
	<tags:controlPanel>
		<c:if test="${!(command.study.companionIndicator && !command.study.standaloneIndicator)}">
			<tags:oneControlPanelItem linkhref="javascript:activateAndSaveStudy();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_openstudy.png" linktext="Open Study" />
		</c:if>
		<tags:oneControlPanelItem linkhref="javascript:javascript:document.location='../study/viewStudy?studyId=${command.study.id}';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_manageThisReg.png" linktext="Manage Study" />
		<csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="UPDATE" authorizationCheckName="studyAuthorizationCheck">
			<tags:oneControlPanelItem linkhref="javascript:updateTargetAccrual();" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_reconsent.png" linktext="Edit Accrual" />
		</csmauthz:accesscontrol>
	</tags:controlPanel>
</div>
<div id="flash-message-targetaccrual" style="display:none;">
		<div id="flash-message" class="info"><img src="<tags:imageUrl name="check.png" />" alt="" style="vertical-align:middle;" />Target accrual has been updated.</div>
	</div>
<form:form id="viewDetails" name="viewDetails">
<form:hidden path="study.studyVersion.versionDate"/>
<div id="summary">
<tags:tabFields tab="${tab}"/>
<tags:instructions code="study_summary_create" />
<div>
    <input type="hidden" name="_finish" value="true"/>
    <input type="hidden" name="_activate" id="_activate" value="false"/>
   	<input type="hidden" name="_action" id="_action"/>
</div>
<tags:errors path="*"/>
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
	<div id="targetAccrualDiv">
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.targetAccrual"/>:</div>
		<div class="value">${command.study.targetAccrualNumber}
		</div>
	</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.phase"/>:</div>
		<div class="value">${command.study.phaseCode}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.status"/>:</div>
		<div class="value">${command.study.coordinatingCenterStudyStatus.code}</div>
	</div>
</div>
<div class="rightpanel">
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.type"/>:</div>
		<div class="value">${command.study.type}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.blinded"/>:</div>
		<div class="value">${command.study.blindedIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.stratified"/>:</div>
		<div class="value">${command.study.stratificationIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.randomized"/>:</div>
		<div class="value">${command.study.randomizedIndicator=="true"?"Yes":"No"}</div>
	</div>
	<div class="row" <c:if test="${!command.study.randomizedIndicator}">style="display:none;"</c:if>>
		<div class="label"><fmt:message key="study.randomizationType"/>:</div>
		<div class="value">${command.study.randomizationType.displayName}</div>
	</div>
</div>
</chrome:division>
<chrome:division id="study-pi" cssClass="big" title="Principal Investigator">
<div class="leftpanel">
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.organization"/>:</div>
		<div class="value">${command.study.principalInvestigatorStudyOrganization.healthcareSite.name} (${command.study.principalInvestigatorStudyOrganization.healthcareSite.primaryIdentifier})</div>
	</div>
</div>
<div class="rightpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.principalInvestigator"/>:</div>
		<div class="value">${command.study.principalInvestigatorFullName}</div>
	</div>
</div>
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
           	<td class="alt" align="left">${command.study.coordinatingCenterAssignedIdentifier.type.displayName}</td>
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
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.type.displayName}</td>
                <td class="alt" align="left">${command.study.fundingSponsorAssignedIdentifier.value}</td>
            </tr>
        </c:if>
    </table>
</chrome:division>
<chrome:division title="Consents" cssClass="big">
    <table class="tablecontent" width="60%">
        <tr>
            <th><b><fmt:message key="study.consents"/></b></th>
            <th><b><fmt:message key="study.consentVersion"/></b></th>
            <th><b><fmt:message key="study.consentMandatoryIndicator"/></b></th>
        </tr>
        <c:forEach items="${command.study.consents}" var="consent">
            <tr>
                <td class="alt">${consent.name}</td>
                <td class="alt">${consent.versionId}</td>
                <td class="alt">${consent.mandatoryIndicator?"Yes":"No"}</td>
			</tr>
        </c:forEach>
    </table>
</chrome:division>

<chrome:division title="Epochs &amp; Arms" cssClass="big" link="javascript:redirectToTab('${epochTab}')" condition="${flowType != 'VIEW_STUDY'}">
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
		                                <th><b><fmt:message key="c3pr.common.targetAccrual"/></b></th>
		                                <th/>
		                            </tr>

		                            <tr>
		                                <c:forEach items="${epoch.arms}" var="arm">
		                                <tr>
		                                    <td>${arm.name}</td>
		                                    <td>${arm.targetAccrualNumber}</td>
		                                </tr>
		                                </c:forEach>
		                            </tr>
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
<chrome:division title="Eligibility Criteria" cssClass="big" link="javascript:redirectToTab('${eligibilityTab}')" condition="${flowType != 'VIEW_STUDY'}">
	<c:forEach items="${command.study.epochs}" var="epoch">
		<c:if test="${fn:length(epoch.eligibilityCriteria)> 0}">
			<chrome:division title="Epoch: ${epoch.name}" cssClass="indented">
				<c:if test="${fn:length(epoch.inclusionEligibilityCriteria)> 0}">
					<table class="tablecontent" width="70%"}">
				        <tr>
				            <th width="70%" scope="col" align="left"><b><fmt:message key="study.inclusionCriterion"/></b></th>
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
					<table class="tablecontent" width="70%"}">
				        <tr>
				            <th width="70%" scope="col" align="left"><b><fmt:message key="study.exclusionCriterion"/></b></th>
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
</chrome:division>
<chrome:division title="Stratum Groups" cssClass="big" link="javascript:redirectToTab('${stratificationTab}')" condition="${flowType != 'VIEW_STUDY'}">
	<c:forEach items="${command.study.epochs}" var="epoch">
		<c:if test="${epoch.stratificationIndicator}">
			<chrome:division title="Epoch: ${epoch.name}" cssClass="indented">
				<c:choose>
					<c:when test="${fn:length(epoch.stratumGroups)> 0}">
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
					</c:when>
					<c:otherwise>
						<div align="left"><span><fmt:message key="study.noStratumGroupGenerated"/></span></div>
					</c:otherwise>
				</c:choose>
			</chrome:division>
		</c:if>
    </c:forEach>
</chrome:division>
<chrome:division title="Diseases" cssClass="big" link="javascript:redirectToTab('${diseaseTab}')" condition="${flowType != 'VIEW_STUDY'}">
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
<div id="companionDiv">
<div id="companionAssociationsDiv" <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>>
    	<chrome:division title="Companion Studies" cssClass="big" link="javascript:redirectToTab('${companionTab}')" condition="${flowType != 'VIEW_STUDY'}">
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
		                    	<tags:button id="editCompanionStudy" type="button" color="blue" value="Edit" onclick="javascript:document.location='editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}&parentStudyFlow=${flowType}'" size="small"/>
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
<div id="errorsCreateDiv" style="display:none">
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
<div id="openWithDateMessage" style="display:none; padding: 15px;">
		<div style="font-size: 10pt; padding-top: 20px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
	        <b><tags:requiredIndicator />Effective date</b>
	        <input type="text" id="studyOpenDate" class="date validate-DATE&&notEmpty"  value="${command.study.studyVersion.versionDateStr }"/>
            <a href="#" id="studyOpenDate-callButton">
           	   	<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           	</a>
	        <script>
	        	Calendar.setup(
		        {
		            inputField  : "studyOpenDate",         // ID of the input field
		            ifFormat    : "%m/%d/%Y",    // the date format
		            button      : "studyOpenDate-callButton"       // ID of the button
		        }
		        );
	        </script>
        	<tags:hoverHint keyProp="study.openDate"/>
		</div>
		<div id="actionButtons">
			<div class="flow-buttons">
		   	<span class="next">
		   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="contentWin.close();" />
				<tags:button type="button" color="green" icon="save" onclick="javascript:openStudy();" value="Open study" />
			</span>
			</div>
		</div>
	</div>
<div id="openMessage" style="display:none">
	<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="STUDY.OPEN.WARNING"/>
	<div id="actionButtons">
		<div class="flow-buttons">
	   	<span class="next">
	   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="confirmWin.close();" />
			<tags:button type="button" color="green" icon="save" onclick="$('command').submit();" value="Open study" />
		</span>
		</div>
	</div>
</div>
<div id="targetAccrualPage" style="display:none;">
<div id="targetAccrual" >
<%@ include file="update_target_accrual.jsp"%>
</div>
</div>
</body>
</html>
