<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>

<%@ page import="java.util.Calendar" %>
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
			<td class="tableHeader">Primary Identifier</td>				
			<td class="tableHeader">Short Title</td>
			<td class="tableHeader">Sponsor</td>
			<td class="tableHeader">Phase</td>			
			<td class="tableHeader">Target Accrual</td>
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
			<% String currClass=i%2==0? "odd":"even"; %>
			<tr align="center" id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
				onMouseOut="this.className='<%= currClass %>'" style="cursor:pointer"
				onClick="
					<c:choose>
						<c:when test="${fn:length(study.studySites) > 1}">
							new Element.toggle('studySites-table-${statusStudy.index }');toggleImage('image-open-${statusStudy.index }');
						</c:when>
						<c:otherwise>
						    <c:set var="singleQuote" value="'" />
						    <c:set var="singleQuoteAlias" value="\\&#39" />
						    <c:set var="siteName" value="${fn:replace(study.studySites[0].healthcareSite.name, singleQuote, singleQuoteAlias)}" />
							postProcessStudySelection(${study.studySites[0].siteStudyStatus.code=='Active'},'${study.studySites[0].id}', '${siteName}','${study.shortTitleText}','${study.identifiers[0].type}'+' - '+ '${study.identifiers[0].value}');
						</c:otherwise>
					</c:choose>
				">
				<td width="2%"><c:if test="${fn:length(study.studySites) > 1}">
					<img id="image-open-${statusStudy.index }" src="<tags:imageUrl name="b-plus.gif"/>" border="0" alt="expand">
					</c:if>
				</td>
				<td align="left">${study.primaryIdentifier}</td>
				<td align="left">${study.trimmedShortTitleText}</td>
				<td align="left">${study.identifiers[0].value}</td>
				<td align="left">${study.phaseCode}</td>
				<td align="left">${study.targetAccrualNumber}</td>
			</tr>
			<c:if test="${fn:length(study.studySites) > 1}">
				<tr id="studySites-table-${statusStudy.index }" style="display:none;">
					<td colspan="2">&nbsp;</td>
					<td colspan="6" height="0" class>
						<div id="studySites-${statusStudy.index }">
						<table width="50%" height="0" border="0" cellspacing="0" cellpadding="0" class="tableRegion">
							<thead>
							<tr>
								<td class="tableHeader">Site</td>
								<td class="tableHeader">IRB Approval Date</td>
							</tr>
							</thead>
							<%int j=i*100; %>
							<c:forEach items="${study.studySites}" var="site" varStatus="siteIndex">
								<c:if test='${site.siteStudyStatus.code=="Active"}'>
								<%
									Calendar yearOld=Calendar.getInstance();
									yearOld.add(Calendar.YEAR, -1);
									pageContext.setAttribute("yearOld",yearOld);
								%>

                                <c:set var="singleQuote" value="'" />
                                <c:set var="singleQuoteAlias" value="\\&#39" />
                                <c:set var="siteName" value="${fn:replace(site.healthcareSite.name, singleQuote, singleQuoteAlias)}" />

                                <c:set var="expiredIrb" value="${site.irbApprovalDate.time le yearOld.timeInMillis}"></c:set>
								<c:set var="javLink" value="postProcessStudySelection(${site.siteStudyStatus.code=='Active'},'${site.id}','${siteName}','${study.shortTitleText}','${study.identifiers[0].type}'+' - '+ '${study.identifiers[0].value}')"/>
								<c:if test="${expiredIrb}">
									<c:set var="javLink" value="alert('The IRB approval date for this site has expired. Its more than an year old');"/>
								</c:if>
								<csmauthz:accesscontrol domainObject="${site.healthcareSite}"
		                                                  hasPrivileges="ACCESS"  authorizationCheckName="siteAuthorizationCheck">
		                            <% System.out.println("11----"); %>
									<% String currClassJ=j%2==0? "odd":"even"; %>
									<tr align="center" id="row<%= j++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'"
												onMouseOut="this.className='<%= currClass %>'" 
										onClick="${javLink }">
										<td>${site.healthcareSite.name}</td>
										<td>${site.irbApprovalDateStr}<c:if test="${expiredIrb}"><font color='Red'><i>(expired)</i></font></c:if></td>
									</tr>
								</csmauthz:accesscontrol>
								</c:if>
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