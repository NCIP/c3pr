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
                	<input type="button" id="editCompanionStudy" value="Edit" onclick="javascript:document.location='<c:url value='/pages/study/editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
                </td>
   	        </tr>	           
        </c:forEach>
    </table>
</chrome:division>