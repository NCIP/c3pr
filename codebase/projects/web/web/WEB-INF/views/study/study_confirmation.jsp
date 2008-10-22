<%@ include file="taglibs.jsp"%>

<html>
	<head>
        <title><studyTags:htmlTitle study="${command.study}" /></title>   
	</head>
	<body>
	
	<script>
	</script>
	    <chrome:box title="Confirmation" autopad="true">
	            	<div class="row" >
	                    <h2><font color="green">Study successfully created.</font></h2>
	                </div>
	                 <div class="row" >
	                 	<table class="tablecontent" width="60%">
							<tr>
								<td width="35%" class="alt" align="left"><b>Short Title</b></td>
								<td class="alt" align="left">${command.study.trimmedShortTitleText}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>Coordinating Center Study Identifier</b></td>
								<td class="alt" align="left">${command.study.primaryIdentifier}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>Study Status</b></td>
								<td class="alt" align="left">${command.study.coordinatingCenterStudyStatus.displayName}</td>
							</tr>
						</table>
	                 </div>
					<div class="flow-buttons">
						<span class="next"> 
							<input type="button" value="Manage Study" id="manageStudy" onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${command.study.id}' />'" />
						</span>
					</div>
					<br/>
					<br/>
	    </chrome:box>
	    <div <c:if test="${command.study.companionIndicator == 'true'}">style="display:none;"</c:if>>
	    <chrome:box title="Companion Studies" autopad="true">
		    <table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%" scope="col" align="left"><b>Short Title</b></th>
		            <th width="25%" scope="col" align="left"><b>Status</b></th>
		            <th width="25%" scope="col" align="left"><b>Mandatory</b></th>
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
		<div <c:if test="${command.study.companionIndicator=='false' || (command.study.companionIndicator=='true' && command.study.standaloneIndicator=='true')}">style="display:none;"</c:if>>
	    <chrome:box title="Parent Study" autopad="true">
		    <table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%" scope="col" align="left"><b>Short Title</b></th>
		            <th width="25%" scope="col" align="left"><b>Status</b></th>
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
