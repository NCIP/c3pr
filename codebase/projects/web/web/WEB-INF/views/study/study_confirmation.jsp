<%@ include file="taglibs.jsp"%>

<html>
	<head>
        <title><studyTags:htmlTitle study="${command.study}" /></title>   
	</head>
	<body>
	
	<script>
	</script>
	    <chrome:box title="Confirmation" autopad="true">
					<c:choose>
						<c:when test ="${command.study.coordinatingCenterStudyStatus.displayName == 'Open'}">
			            	<div class="row" >
			                    <h2><font color="green">Study successfully opened.</font></h2>
			                </div>
						</c:when>
						<c:otherwise>
			            	<div class="row" >
			                    <h2><font color="green">Study successfully created.</font></h2>
			                </div>
						</c:otherwise>
					</c:choose>
	                 <div class="row" >
	                 	<table class="tablecontent" width="60%">
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="study.shortTitle"/></b></td>
								<td class="alt" align="left">${command.study.trimmedShortTitleText}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="study.coordinatingCenterStudyIdentifier"/></b></td>
								<td class="alt" align="left">${command.study.primaryIdentifier}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b><fmt:message key="study.studyStatus"/></b></td>
								<td class="alt" align="left">${command.study.coordinatingCenterStudyStatus.displayName}</td>
							</tr>
						</table>
	                 </div>
					<div class="flow-buttons">
						<span class="next"> 
							<tags:button id="manageStudy" type="button" color="blue" value="Manage Study" size="small" onclick="javascript:document.location='viewStudy?studyId=${command.study.id}'" />
						</span>
					</div>
					<br/>
					<br/>
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
								<c:if test="${not empty editAuthorizationTask}">  
								<csmauthz:accesscontrol domainObject="${editAuthorizationTask}" authorizationCheckName="taskAuthorizationCheck">
									<input type="button" id="editCompanionStudy" value="Edit" onclick="javascript:document.location='<c:url value='/pages/study/editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
								</csmauthz:accesscontrol>
								</c:if>	                
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
	    <div class="content buttons autoclear">
	</div>
	</body>
</html>
