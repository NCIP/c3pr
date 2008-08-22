<%@ include file="taglibs.jsp"%>
<chrome:division title="Companion Studies">
    <table class="tablecontent" width="60%">
        <tr>
            <th width="50%" scope="col" align="left"><b>Companion Study Short Title</b></th>
            <th width="25%" scope="col" align="left"><b>Status</b></th>
            <th width="25%" scope="col" align="left"><b>Mandatory</b></th>
        </tr>
        <c:forEach items="${command.companionStudyAssociations}" var="companionStudyAssociation">
            <tr>
                <td class="alt">${companionStudyAssociation.companionStudy.shortTitleText}</td>
                <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.code}</td>
                <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
                <td class="alt">
					<c:choose>
						<c:when test="${(companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'ACTIVE') || (companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.name == 'READY_FOR_ACTIVATION')}">                	
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
</chrome:division>