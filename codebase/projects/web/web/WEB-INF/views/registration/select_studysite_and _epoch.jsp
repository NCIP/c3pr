<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<script>

function manageStudySiteSelection(element){
	$$('.studySiteSelection').each(function(e){
				e.checked=false;
			}
		);
	element.checked=true;
	$('studySite').value = element.value ;
}

function manageEpochSelection(element){
	$$('.epochSelection').each(function(e){
				e.checked=false;
			}
	);
	element.checked=true;
	$('create_epoch').value = element.value ;
}

</script>
<style>
	#mybox div.hr{
		display : none ;
	}
</style>

</head>
<body>
	<br>
	<tags:panelBox boxId="mybox" title="Selected Subject: ${command.participant.fullName}"></tags:panelBox>
	<tags:panelBox boxId="mybox" title="Selected Study: ${companionStudy.shortTitleText} (${companionStudy.coordinatingCenterAssignedIdentifier.value})"></tags:panelBox>
	<tags:formPanelBox tab="${tab}" flow="${flow}" >
	<chrome:division title="Select Study Site">
		<table id="studySites" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
               <tr>
               		<th></th>
                   <th><b><fmt:message key="c3pr.common.organization"/></b>&nbsp;<tags:hoverHint keyProp="study.healthcareSite.name"/></th>
                   <th><b><fmt:message key="c3pr.common.CTEPIdentifier"/></b>&nbsp;<tags:hoverHint keyProp="organization.ctepCode"/></th>
               </tr>
               <c:choose>
	               <c:when test="${fn:length(studySites) > 0}">
		               <c:forEach items="${studySites}" var="studySite" varStatus="status">
		               	 <tr>
							<td>
								<input class="studySiteSelection" type="radio" value="${studySite.id}" onclick="manageStudySiteSelection(this);" <c:if test="${status.count ==1}">checked </c:if>  />
							</td>
		               		<td>
		               			${studySite.healthcareSite.name} 
		               		</td>
		               		<td>
		               			${studySite.healthcareSite.primaryIdentifier}
		               		</td>
		               	 </tr>
		               </c:forEach>
	               </c:when>  
	               <c:otherwise>
	               		<div><span class="red"><fmt:message key="registartion.companion.noStudySites"/></span></div>
	               </c:otherwise>
               </c:choose>
            </table>
	</chrome:division>
	<chrome:division title="Select Epoch">
		<table id="epochs" class="tablecontent" border="0" cellspacing="0" cellpadding="0"	>
              <tr>
              		<th></th>
                  <th><b><fmt:message key="c3pr.common.name"/></b><tags:hoverHint keyProp="study.treatmentEpoch.name" /></th>
                  <th><b><fmt:message key="c3pr.common.description"/></b><tags:hoverHint keyProp="study.treatmentEpoch.description" /></th>
                  <th><b><fmt:message key="c3pr.common.enrolling"/></b><tags:hoverHint keyProp="study.nonTreatmentEpoch.enrollmentIndicator" /></th>
              </tr>
              <c:choose>
	              <c:when test="${fn:length(epochs) > 0}">
		              <c:forEach items="${epochs}" var="epoch" varStatus="epochStatus">
		              	 <tr>
							<td>
								<input class="epochSelection" type="radio" value="${epoch.id}" onclick="manageEpochSelection(this);" <c:if test="${epochStatus.count ==1}">checked </c:if>  />
							</td>
		             		<td>
		             			${epoch.name}
		             		</td>
		             		<td>
		             			${epoch.descriptionText}
		             		</td>
		             		<td>
		             			${(epoch.enrollmentIndicator)?'Yes':'No'}
		             		</td>
		             	 </tr>
		              </c:forEach>
	               </c:when>  
	               <c:otherwise>
	               		<div><span class="red"><fmt:message key="registartion.companion.noEnrollingEpoch"/></span></div>
	               </c:otherwise>
               </c:choose>
           </table>
	</chrome:division>
	<div style="display:none">
        <input type="text" name="studySite" id="studySite" class="required validate-notEmpty"/> 
        <input type="text" name="epoch" id="create_epoch" class="required validate-notEmpty"> 
    </div>
</tags:formPanelBox>
<script>
	$$('.studySiteSelection').each(function(e){
			if(e.checked){
				$('studySite').value = e.value ;
			}
		}
	);

	$$('.epochSelection').each(function(e){
			if(e.checked){
				$('create_epoch').value = e.value ;
			}
		}
	);
</script>
</body>
</html>
