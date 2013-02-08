<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
	<head>
        <title><studyTags:htmlTitle study="${command.study}" /></title>   
	</head>
	<body>
	<div id="controlPanel">
		<tags:controlPanel>
			<tags:oneControlPanelItem linkhref="javascript:javascript:document.location='../study/viewStudy?studyId=${command.study.id}';" imgsrc="/c3pr/templates/mocha/images/controlPanel/controlPanel_manageThisReg.png" linktext="Manage Study" />
		</tags:controlPanel>
	</div>
	<div id="flash-message" class="info">
		<img src="<tags:imageUrl name='check.png'/>" alt="" style="vertical-align:top;" /> 
		<font color="green">
		<c:choose>
				<c:when test ="${command.study.coordinatingCenterStudyStatus.displayName == 'Open'}">
	                    Study successfully opened.
				</c:when>
				<c:otherwise>
	                    Study successfully created.
				</c:otherwise>
			</c:choose>
		</font>
	</div>

	    <chrome:box title="Confirmation" autopad="true">
                 <div class="row" >
         			<div class="label"><fmt:message key="study.shortTitle"></fmt:message></div>
         			<div class="value">${command.study.trimmedShortTitleText}</div>
         		</div>
         		<div class="row" >
         			<div class="label"><fmt:message key="c3pr.common.primaryIdentifier"/></div>
         			<div class="value">${command.study.primaryIdentifier } </div>
         		</div>
         		<div class="row" >
         			<div class="label"><fmt:message key="study.studyStatus"/> </div>
         			<div class="value">${command.study.coordinatingCenterStudyStatus.displayName} </div>
         		</div>
         		<div class="row" >
         			<div class="label"><fmt:message key="study.openDate"/> </div>
         			<div class="value">${command.study.versionDateStr} </div>
         		</div>
	    	</chrome:box>
	    	<div <c:if test="${command.study.companionIndicator == 'true' || fn:length(command.study.companionStudyAssociations) == 0}">style="display:none;"</c:if>>
	    <chrome:box title="Companion Studies" autopad="true">
		    <table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%" scope="col" align="left"><b><fmt:message key="study.shortTitle"/></b></th>
		            <th width="25%" scope="col" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
		            <th width="25%" scope="col" align="left"><b><fmt:message key="c3pr.common.mandatory"/></b></th>
		        </tr>
		        <c:forEach items="${command.study.companionStudyAssociations}" var="companionStudyAssociation">
		            <tr>
		                <td class="alt">${companionStudyAssociation.companionStudy.shortTitleText}</td>
		                <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.displayName}</td>
		                <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
		                <td class="alt">
							<c:choose>
								<c:when test="${(companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.displayName == 'Open') || (companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.displayName == 'Ready For Activation')}">                	
									<input type="button" id="manageCompanionStudy" value="Manage" onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
								</c:when>
								<c:otherwise>                	
								<csmauthz:accesscontrol domainObject="${companionStudyAssociation.companionStudy.studyCoordinatingCenter.healthcareSite}" hasPrivileges="UI_STUDY_UPDATE" authorizationCheckName="siteAuthorizationCheck">
									<input type="button" id="editCompanionStudy" value="Edit" onclick="javascript:document.location='<c:url value='/pages/study/editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
								</csmauthz:accesscontrol>
								</c:otherwise>
							</c:choose>
		                </td>

		   	        </tr>	           
		        </c:forEach>
		    </table>
	    </chrome:box>
	    </div>
		<div <c:if test="${command.study.companionIndicator=='false' || (command.study.companionIndicator=='true' && (command.study.standaloneIndicator=='true' || fn:length(command.study.companionStudyAssociations) == 0))}">style="display:none;"</c:if>>
	    <chrome:box title="Parent Study" autopad="true">
		    <table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%" scope="col" align="left"><b><fmt:message key="study.shortTitle"/></b></th>
		            <th width="25%" scope="col" align="left"><b><fmt:message key="c3pr.common.status"/></b></th>
		        </tr>
		        <c:forEach items="${command.study.parentStudyAssociations}" var="parentStudyAssociation">
		            <tr>
		                <td class="alt">${parentStudyAssociation.parentStudy.shortTitleText}</td>
		                <td class="alt">${parentStudyAssociation.parentStudy.coordinatingCenterStudyStatus.displayName}</td>
		                <td class="alt">
							<input type="button" id="manageParentStudy" value="Manage" onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${parentStudyAssociation.parentStudy.id}' />'"/>
                		</td>
		   	        </tr>	           
		        </c:forEach>
		    </table>
	    </chrome:box>
	    </div>
	</body>
</html>
