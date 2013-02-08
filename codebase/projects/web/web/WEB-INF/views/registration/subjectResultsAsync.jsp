<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    	
<!-- SUBJECT SEARCH RESULTS START HERE -->
<div class="eXtremeTable" >
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>
		<table class="tableRegion" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
			<c:if test="${participants!=null}">
				<tr>
					<td class="tableHeader"></td>
					<td class="tableHeader"><fmt:message key="c3pr.common.lastName"/>, <fmt:message key="c3pr.common.firstName"/></td>
					<td class="tableHeader"><fmt:message key="participant.medicalRecordNumber"/></td>
					<td class="tableHeader"><fmt:message key="c3pr.common.assigningAuthority"/></td>
					<td class="tableHeader"><fmt:message key="participant.gender"/></td>
					<td class="tableHeader"><fmt:message key="participant.race"/></td>
					<td class="tableHeader"><fmt:message key="participant.birthDate"/></td>
    			</tr>
			</c:if>
			</thead>
			<tbody class="tableBody">
			<c:if test="${participants!=null && fn:length(participants)==0}">
				<tr>
					Sorry, no matches were found
				</tr>
			</c:if>
			<%int i=0; %>
			<c:forEach items="${participants}" var="participant" varStatus="participantResultsStatus">
				  <% String currClass=i%2==0? "odd":"even"; %>

            <tr id="row<%= i++ %>" class="<%= currClass %>" onMouseOver="this.className='highlight'" style="cursor:pointer"
				onMouseOut="this.className='<%= currClass %>'" 
				onclick='toggleCriteria("participants-table-${participantResultsStatus.index }", "minmax_participants-table-${participantResultsStatus.index }")'>	
					<td><img id="image-open-${statusStudy.index }" src="<tags:imageUrl name="b-plus.gif"/>" border="0" alt="expand"></td>				
					<td>${participant.lastName},${participant.firstName}</td>
					<td>${participant.MRN.value}</td>
					<td><c:if test="${! empty participant.MRN}">${participant.MRN.healthcareSite.name}</c:if></td>
					<td>${participant.administrativeGenderCode}</td>
					<td>
						<c:forEach items="${participant.raceCodeAssociations}" var="raceCode">
				            <div class="row">
				                <div class="left">${raceCode.raceCode.displayName}</div>
				            </div>
			        	</c:forEach>
		        	</td>
					<td>${participant.birthDateStr}</td>
					<td>
									 <script>
									    function toggleCriteria(divToBeMinimized }, id){
									        var el = document.getElementById(divToBeMinimized);
											var elimg =document.getElementById(id);
									        if (el == null) {
									            alert("division.tag - Could not find div Element to minimize.");
									        }
									        if (el.style.display != 'none') {
									            new Effect.BlindUp(el);
									            elimg.src = '<chrome:imageUrl name="../../templates/mocha/images/maximize.png" />';
												elimg.alt = 'Maximize';
									            
									        }
									        else {
									            new Effect.BlindDown(el);
									            elimg.src = '<chrome:imageUrl name="../../templates/mocha/images/minimize.png" />';
												elimg.alt = 'Minimize';
									            
									        }
									    }
									</script>
										<c:set var="minimize" value="true" />
										<c:choose>
											<c:when test="${minimize}">
												<c:set var="imageVar" value="maximize"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="imageVar" value="minimize"></c:set>				
											</c:otherwise>
										</c:choose>
					</td>
				</tr>
				<tr id="participants-table-${participantResultsStatus.index }" style="display:none;">
				<td colspan="8" style="padding:30px;">
					<table border="1" cellpadding="4" width="100%">
        				<tr> <td width="100%">
							<chrome:division id="participant-details" title="Basic Details">
								<div class="leftpanel">
									<div class="row">
										<div class="label"><fmt:message key="c3pr.common.firstName"/></div>
										<div class="value">${participant.firstName }</div>
									</div>
									<div class="row">
										<div class="label"><fmt:message key="c3pr.common.lastName"/></div>
										<div class="value">${participant.lastName }</div>
									</div>
									<div class="row">
										<div class="label"><fmt:message key="participant.gender"/></div>
										<div class="value">${participant.administrativeGenderCode }
										</div> 
									</div>
									<div class="row">
										<div class="label"><fmt:message key="participant.birthDate"/></div>
										<div class="value">${participant.birthDateStr }</div>
									</div>
								</div>
								<div class="rightpanel">
									<div class="row">
										<div class="label"><fmt:message key="participant.ethnicity"/></div>
										<div class="value">${participant.ethnicGroupCode }</div>
									</div>
									<div class="row">
										<div class="label"><fmt:message key="participant.race"/></div>
										<div class="value">
										<c:forEach items="${participant.raceCodeAssociations}" var="raceCode">
								            <div class="row">
								                <div class="left">${raceCode.raceCode.displayName}</div>
								            </div>
							        	</c:forEach>
										</div>
									</div>
								</div>
							</chrome:division>
							<chrome:division title="Identifiers">
							    <table width="90%" border="0" cellspacing="0" cellpadding="0" class="tablecontent">
							        <tr>
							            <th><fmt:message key="c3pr.common.assigningAuthority"/></th>
							            <th><fmt:message key="c3pr.common.identifierType"/></th>
							            <th><fmt:message key="c3pr.common.identifier"/></th>
							        </tr>
							        <c:forEach var="orgIdentifier" items="${participant.organizationAssignedIdentifiers}"
							                  varStatus="organizationStatus">
							            <tr>
							                <td>${orgIdentifier.healthcareSite.name}</td>
							                <td>${orgIdentifier.type}</td>
							                <td>${orgIdentifier.value}</td>
							            </tr>
							        </c:forEach>
							        <c:forEach items="${participant.systemAssignedIdentifiers}" varStatus="status" var="sysIdentifier">
							            <tr>
							                <td>${sysIdentifier.systemName}</td>
							                <td>${sysIdentifier.type}</td>
							                <td>${sysIdentifier.value}</td>
							            </tr>
							        </c:forEach>
						    	</table>
							</chrome:division>
							<chrome:division title="Address">
								<c:choose>
								<c:when test="${participant.address.addressString != ''}">
									<div class="leftpanel">
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.streetAddress"/>:</div>
											<tags:value value="${participant.address.streetAddress}" ></tags:value>
										</div>
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.city"/>:</div>
											<tags:value value="${participant.address.city}" ></tags:value>
										</div>
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.state"/>:</div>
											<tags:value value="${participant.address.stateCode}" ></tags:value>
										</div>
									</div>
									<div class="rightpanel">
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.country"/>:</div>
											<tags:value value="${participant.address.countryCode}" ></tags:value>
										</div>
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.zip"/>:</div>
											<tags:value value="${participant.address.postalCode}" ></tags:value>
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.addressNotSpecified"/></span></div>
								</c:otherwise>
								</c:choose>
							</chrome:division>
							<chrome:division title="Contact Information">
								<c:choose>
								<c:when test="${empty participant.email && empty participant.phone && empty participant.fax}">
									<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.contactInfoNotProvided"/></span></div>				
								</c:when>
								<c:otherwise>
									<div class="leftpanel">
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.email"/>:</div>
											<tags:value value="${participant.email}" ></tags:value>
										</div>
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.phone"/>:</div>
											<tags:value value="${participant.phone}" ></tags:value>
										</div>
									</div>
									<div class="rightpanel">
										<div class="row">
											<div class="label"><fmt:message key="c3pr.common.fax"/>:</div>
											<tags:value value="${participant.fax}" ></tags:value>
										</div>
									</div>
								</c:otherwise>
								</c:choose>
							</chrome:division>
							</td>
						 </tr>
						 <tr>
							 <td align="right">
							 	<c:set var="assignedBy" value="organization"/>
							 	<c:set var="sourceValue" value="${participant.organizationAssignedIdentifiers[0].healthcareSite.primaryIdentifier}"/>
							 	<c:set var="identifierType" value="${participant.organizationAssignedIdentifiers[0].type}"/>
							 	<c:set var="idValue" value="${participant.organizationAssignedIdentifiers[0].value}" scope="request"/>
							 	<c:set var="encodedIdValue" value='<%=java.net.URLEncoder.encode((java.lang.String)request.getAttribute("idValue"))%>' />
							 	<table>
							 		<tr>
							 			<c3pr:checkprivilege hasPrivileges="UI_SUBJECT_UPDATE">
										 	<td>
										 		<tags:button id="updateSubject" type="button" size="small" color="blue" value="Edit" onclick="javascript:confirmationPopup('assignedBy=organization&organizationNciId=${sourceValue }&identifierType=${identifierType}&identifier=${encodedIdValue}')" />
										 	</td>
										 </c3pr:checkprivilege>
									 	<td>
									 		<tags:button type="button" size="small" color="blue" icon="Select" value="Select" 
										onclick="postProcessSubjectSelection('${participant.id}','${participant.lastName} ${participant.firstName}','${participant.identifiers[0].type.code}'+' - '+ '${participant.identifiers[0].value}')"/>
									 	</td>
							 		</tr>
							 	</table>
							 	
							 	
							 	
							</td>
						</tr>
						</table>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</td>
	</tr>
</table>
</div>
<!-- SUBJECT SEARCH RESULTS END HERE -->
