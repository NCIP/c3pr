<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

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
</script>
<!-- STUDY SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableRegion">
	<thead>
		<c:if test="${studyResults!=null}">
		<tr align="center" class="label">
			<td class="tableHeader"></td>
			<td class="tableHeader"><fmt:message key="c3pr.common.primaryIdentifier"/></td>				
			<td class="tableHeader"><fmt:message key="study.shortTitle"/></td>
			<td class="tableHeader"><fmt:message key="study.sponsor"/></td>
			<td class="tableHeader"><fmt:message key="study.phase"/></td>			
			<td class="tableHeader"><fmt:message key="c3pr.common.targetAccrual"/></td>
		</tr>
		</c:if>
	</thead>	
	<tbody class="tableBody">
	<c:if test="${studyResults!=null && fn:length(studyResults)==0}">
		<tr>
			Sorry, no matches were found
		</tr>
	</c:if>
	<%int i=0; %>
	<c:forEach items="${studyResults}" var="study" varStatus="statusStudy">
		<c:choose>
			<c:when test="${study.class.name eq 'edu.duke.cabig.c3pr.domain.RemoteStudy'}">
				<c:set var="imageStr" value="&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='22' height='21' border='0' align='middle'/>"/>
			</c:when>
			<c:otherwise>
				<c:set var="imageStr" value=""/>
			</c:otherwise>
		</c:choose>
			<% String currClass=i%2==0? "odd":"even"; %>
			<tr align="center" id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'" style="cursor:pointer"
				onClick="
					<c:choose>
						<c:when test="${fn:length(study.accruingStudySites) > 1}">
							new Element.toggle('studySites-table-${statusStudy.index }'); toggleImage('image-open-${statusStudy.index }');
						</c:when>
						<c:when test="${fn:length(study.accruingStudySites) == 1}">
							<c:set var="singleQuote" value="'" />
						    <c:set var="singleQuoteAlias" value="\\&#39" />
						    <c:set var="siteName" value="${fn:replace(study.accruingStudySites[0].healthcareSite.name, singleQuote, singleQuoteAlias)}" />
						     <c:set var="studyShortTitle" value="${fn:replace(study.shortTitleText, singleQuote, singleQuoteAlias)}" />
						     <c:set var="studyIdentifier" value="${fn:replace(study.identifiers[0].value, singleQuote, singleQuoteAlias)}" />
								postProcessStudySelection(${study.accruingStudySites[0].siteStudyStatus.code=='Active'},'${study.accruingStudySites[0].latestAccruingStudySiteStudyVersion.id}','${study.accruingStudySites[0].id}', '${siteName}','${studyShortTitle}','${studyIdentifier}');
						</c:when>
						<c:otherwise>
						    alert('Study has no active study sites.');
						</c:otherwise>
					</c:choose>
				">
				<td width="2%"><c:if test="${fn:length(study.accruingStudySites) > 1}">
					<img id="image-open-${statusStudy.index }" src="<tags:imageUrl name="b-plus.gif"/>" border="0" alt="expand">
					</c:if>
				</td>
				<td align="left">${study.primaryIdentifier}${imageStr}</td>
				<td align="left">${study.trimmedShortTitleText}</td>
				<td align="left">
					<c:if test="${fn:length(study.studyFundingSponsors) > 0}">
						${study.studyFundingSponsors[0].healthcareSite.name}
					</c:if>
				</td>
				<td align="left">${study.phaseCode}</td>
				<td align="left">${study.targetAccrualNumber}</td>
			</tr>
			<c:if test="${fn:length(study.accruingStudySites) > 1}">
				<tr id="studySites-table-${statusStudy.index }" style="display:none;">
					<td colspan="2">&nbsp;</td>
					<td colspan="6" height="0" class>
						<div id="studySites-${statusStudy.index }">
						<table width="50%" height="0" border="0" cellspacing="0" cellpadding="0" class="tableRegion">
							<thead>
							<tr>
								<td class="tableHeader"><fmt:message key="study.site"/></td>
								<td class="tableHeader"><fmt:message key="site.IRBApprovalDate"/></td>
							</tr>
							</thead>
							<%int j=i*100; %>
							<c:forEach items="${study.accruingStudySites}" var="site" varStatus="siteIndex">
							<c:set var="singleQuote" value="'" />
                                <c:set var="singleQuoteAlias" value="\\&#39" />
                                <c:set var="siteName" value="${fn:replace(site.healthcareSite.name, singleQuote, singleQuoteAlias)}" />
                                <c:set var="studyShortTitle" value="${fn:replace(study.shortTitleText, singleQuote, singleQuoteAlias)}" />
                                <c:set var="studyIdentifier" value="${fn:replace(study.identifiers[0].value, singleQuote, singleQuoteAlias)}" />
								<c:set var="javLink" value="postProcessStudySelection(${site.siteStudyStatus.code=='Active'},'${site.latestStudySiteStudyVersion.id}','${site.id}','${siteName}','${studyShortTitle}','${studyIdentifier}')"/>
										<%
											Calendar yearOld=Calendar.getInstance();
											yearOld.add(Calendar.YEAR, -1);
											pageContext.setAttribute("yearOld",yearOld);
										%>
										<c:set var="expiredIrb" value="${site.studySiteStudyVersion.irbApprovalDate.time le yearOld.timeInMillis}"></c:set>
								<%--<c:if test='${site.siteStudyStatus.code=="Active"}'>--%>
								<csmauthz:accesscontrol domainObject="${site}"
		                                                  hasPrivileges="STUDYSUBJECT_CREATE"  authorizationCheckName="studySiteAuthorizationCheck">
									<% String currClassJ=j%2==0? "odd":"even"; %>
									<tr align="center" id="row<%= j++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
												style="cursor:pointer;" onMouseOut="this.className='<%= currClass %>'" 
										onClick="${javLink }">
										<td>${site.healthcareSite.name}</td>
										<td>
											<c:choose>
												<c:when test="${empty site.studySiteStudyVersion.irbApprovalDateStr}">
													<font color='Red'><i>not available</i></font>
												</c:when>
												<c:otherwise>
													${site.studySiteStudyVersion.irbApprovalDateStr}
												</c:otherwise>
											</c:choose>
											<c:if test="${expiredIrb}"><font color='Red'><i>(IRB expired)</i></font></c:if>
										</td>
									</tr>
								</csmauthz:accesscontrol>
								<%--</c:if>--%>
							</c:forEach>
						</table>
						</div>
					</td>
				</tr>
			</c:if>		
	</c:forEach>
</table>
</div>
<!-- STUDY SEARCH RESULTS END HERE -->
