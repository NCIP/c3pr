<%@ include file="taglibs.jsp" %>
<html>
<body>
<chrome:division id="study-details" cssClass="big" title="Amendment Details">
<div class="leftpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.versionNameNumber" />:</div>
		<div class="value">${command.study.studyVersion.name }</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.amendmentType" />:</div>
		<div class="value">${command.study.studyVersion.amendmentType.displayName }</div>
	</div>
	<c:if test="${command.study.studyVersion.amendmentType == 'IMMEDIATE_AFTER_GRACE_PERIOD'}">
	<div class="row">
		<div class="label"><fmt:message key="study.gracePeriod" />:</div>
		<div class="value">${command.study.studyVersion.gracePeriod }</div>
	</div>
	</c:if>
</div>
<div class="rightpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.amendmentDate" />:</div>
		<div class="value">${command.study.studyVersion.versionDateStr }</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.comments" />:</div>
		<div class="value">${command.study.studyVersion.comments == ''? 'NA' : command.study.studyVersion.comments}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.amendmentReasons" />:</div>
		<div class="value">
			<c:forEach items="${command.study.studyVersion.amendmentReasons}" var="amendmentReason" varStatus="status">
				${amendmentReason.displayName }<br>
			</c:forEach>
		</div>
	</div>
</div>
</chrome:division>
<chrome:division id="study-details" cssClass="big" title="Version Details">
<div class="leftpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.shortTitle"/>:</div>
		<div class="value">${command.study.shortTitleText}</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="study.longTitle"/>:</div>
		<div class="value">${command.study.longTitleText}</div>
	</div>
</div>
<div class="rightpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.precis"/>:</div>
		<c:choose>
           	<c:when test="${empty command.study.precisText}">
           		<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.notApplicable"/></span></div>
           	</c:when>
           	<c:otherwise>
           		<div class="value">${command.study.precisText}</div>
           	</c:otherwise>
         </c:choose>
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
		<div class="value">${command.study.principalInvestigatorStudyOrganization.healthcareSite.name} (${command.study.principalInvestigatorStudyOrganization.healthcareSite.ctepCode})</div>
	</div>
</div>
<div class="rightpanel">
	<div class="row">
		<div class="label"><fmt:message key="study.principalInvestigator"/>:</div>
		<div class="value">${command.study.principalInvestigatorFullName}</div>
	</div>
</div>
</chrome:division>
<chrome:division title="Consents">
    <table class="tablecontent" width="60%">
        <tr><th width="50%"><b><fmt:message key="study.consents"/></b></th></tr>
        <c:forEach items="${command.study.consents}" var="consent">
            <tr><td class="alt">${consent.name}</td></tr>
        </c:forEach>
    </table>
</chrome:division>
<chrome:division title="Epochs &amp; Arms" cssClass="big" link="javascript:redirectToTab('${epochTab}')" condition="${not empty flowType}">
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
<chrome:division title="Eligibilty Criteria" cssClass="big" link="javascript:redirectToTab('${eligibilityTab}')" condition="${not empty flowType}">
	<c:forEach items="${command.study.epochs}" var="epoch">
		<c:if test="${fn:length(epoch.eligibilityCriteria)> 0}">
			<chrome:division title="Epoch: ${epoch.name}" cssClass="indented">
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
</chrome:division>
<div <c:if test="${!command.hasStratifiedEpoch}">style="display:none;"</c:if>>
<chrome:division title="Stratum Groups"  cssClass="big" link="javascript:redirectToTab('${stratificationTab}')" condition="${not empty flowType}" >
    <c:forEach items="${command.study.epochs}" var="epoch">
		<c:if test="${epoch.stratificationIndicator}">
			<chrome:division title="Epoch: ${epoch.name}" cssClass="indented">
				<c:choose>
					<c:when test="${fn:length(epoch.stratumGroups)> 0}">
						<table class="tablecontent" width="70%">
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
</div>
<c:if test="${fn:length(command.study.studyDiseases) >0}">
<chrome:division title="Diseases" cssClass="big" link="javascript:redirectToTab('${diseaseTab}')" condition="${not empty flowType}">
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
</chrome:division>
</c:if>	
<div id="companionDiv">
<div id="companionAssociationsDiv" <c:if test="${command.study.companionIndicator=='true'}">style="display:none;"</c:if>>
	<c:if test="${fn:length(command.study.companionStudyAssociations)>0}">
    	<chrome:division title="Companion Studies" cssClass="big" link="javascript:redirectToTab('${companionTab}')" condition="${not empty flowType}">
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
    </chrome:division>
    </c:if>
</div>
</div>
<div <c:if test="${command.study.companionIndicator=='false' || (command.study.companionIndicator=='true' && command.study.standaloneIndicator=='true')}">style="display:none;"</c:if>>
	<c:if test="${fn:length(command.study.parentStudyAssociations) > 0}">
    <chrome:division title="Parent Study" cssClass="big">
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
	</chrome:division>
	</c:if>
</div>
<div class="flow-buttons">
	<span class="next">
    	<tags:button type="button" color="blue" value="Close" onclick="parent.closePopup();"/>
	</span>
</div>
</body>
</html>
