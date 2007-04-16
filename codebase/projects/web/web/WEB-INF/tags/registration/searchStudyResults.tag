<%@tag%><%@attribute name="url" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
function toggleImage(id){
	imageStr=document.getElementById(id).src;
//	alert(imageStr);
	if(imageStr.indexOf('plus')==-1)
		document.getElementById(id).src=imageStr.replace('minus','plus');
	else
		document.getElementById(id).src=imageStr.replace('plus','minus');	
//	alert(document.getElementById(id).src)
}
function toggleTableVisibility(id){
	if(document.getElementById(id).style.visibility=='collapse')
		document.getElementById(id).style.visibility='visible';
	else
		document.getElementById(id).style.visibility='collapse';
}
</script>
<!-- STUDY SEARCH RESULTS START HERE -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" id="additionalList1">
				<c:if test="${fn:length(studyResults) > 0}">
					<tr align="center" class="label">
						<td></td>
						<td>Primary Identifier</td>				
						<td>Short Title</td>
						<td>Status</td>
						<td>Sponsor</td>
						<td>Phase</td>			
						<td>Target Accrual</td>
					</tr>
				</c:if>
				<%int i=0; %>
				<c:forEach items="${studyResults}" var="study" varStatus="statusStudy">
					<c:choose>
						<c:when test="${!empty subjectId}">
							<c:set var="documentLocation" value="${url}?participant=${subjectId }&resumeFlow=true&_page=1&_target3=3&studySite=" />
						</c:when>
						<c:otherwise>
							<c:set var="documentLocation" value="${url}?studySiteId=" />	
						</c:otherwise>
					</c:choose>
					<tr align="center" id="row<%= i++ %>" class="results" onMouseOver="navRollOver('row<%= i-1 %>', 'on')"
						onMouseOut="navRollOver('row<%= i-1 %>', 'off')"
						onClick="
							<c:choose>
								<c:when test="${fn:length(study.studySites) > 1}">
									toggleTableVisibility('studySites-table-${statusStudy.index }');Effect.Combo('studySites-${statusStudy.index }');toggleImage('image-open-${statusStudy.index }');
								</c:when>
								<c:otherwise>
									document.location='${documentLocation }${study.studySites[0].id }'
								</c:otherwise>
							</c:choose>
						">
						<td width="2%"><c:if test="${fn:length(study.studySites) > 1}">
							<img id="image-open-${statusStudy.index }" src="<tags:imageUrl name="b-plus.gif"/>" border="0" alt="expand">
							</c:if>
						</td>
						<td>${study.primaryIdentifier}</td>
						<td>${study.trimmedShortTitleText}</td>
						<td>${study.status}</td>
						<td>${study.sponsorCode}</td>
						<td>${study.phaseCode}</td>
						<td>${study.targetAccrualNumber}</td>
						</a>
					</tr>
					<c:if test="${fn:length(study.studySites) > 1}">
						<tr id="studySites-table-${statusStudy.index }" style="visibility:collapse;" class="details">
							<td colspan="2">&nbsp;</td>
							<td colspan="6" height="0" class>
								<div id="studySites-${statusStudy.index }" style="display:none;">
								<table width="50%" height="0" border="0" cellspacing="0" cellpadding="0" id="tableHistory1">
									<tr class="header">
										<td>Healthcare Site</td>
										<td>IRB Approval Date</td>
									</tr>
									<%int j=i*100; %>
									<c:forEach items="${study.studySites}" var="site" varStatus="siteIndex">
										<tr align="center" id="row<%= j++ %>" class="results" onMouseOver="navRollOver('row<%= j-1 %>', 'on')"
										onMouseOut="navRollOver('row<%= j-1 %>', 'off')"
										onClick="document.location='${documentLocation }${site.id }'">
											<td>${site.site.name}</td>
											<td>${site.irbApprovalDateStr==null?'01/01/1970':site.irbApprovalDateStr}</td>
										</tr>
									</c:forEach>
								</table>
								</div>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>
<!-- STUDY SEARCH RESULTS END HERE -->