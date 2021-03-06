<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>

<html>
<title><studyTags:htmlTitle study="${command.study}" /></title>

<head>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>

<title>${tab.longTitle}</title>

<script type="text/javascript">

function removeStudyPersonnel(selectedStudyPersonnelAssignedId) {
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
    $('_actionx').value = "removeStudyPersonnel";
    $('_selectedPersonnelAssignedId').value=selectedStudyPersonnelAssignedId;
	$('command').submit();
}

function changeStudyOrganization(){
	$('_doNotSave').value = true;
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
	$('_actionx').value = "changeStudyOrganization";
	$('command').submit();
}

function addStudyPersonnel(){
	addPersonnelToCart();
	$('_doNotSave').value = true;
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
	$('_actionx').value = "addStudyPersonnel";
	$('command').submit();
}

function saveStudyPersonnel(){
	addPersonnelToCart();
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
	$('_actionx').value = "saveStudyPersonnel";
	$('command').submit();
}

function showPersonnel() {
    <%--If all is selected--%>
    $('sitePersonnelIndicator').show();
    var studyPersonnelSelect = $("study-personnel")
    studyPersonnelSelect.options.length = 0
    studyPersonnelSelect.size = 10
    studyPersonnelSelect.options.add(new Option("All", ""))
    studyPersonnelSelect.options[0].selected = true;
    <%--call the getAll method for the selected organization instead of calling getInvestigatorsById() for every group.--%>
    var catId = $('studyOrganizationSelect').value.substring($('studyOrganizationSelect').value.indexOf('-')+1);
    var studyId = $('_studyId').value;
    if(catId!=null){
    	StudyAjaxFacade.getSitePersonnel(catId, studyId, function(diseases) {
            diseases.each(function(cat) {
           	  var assignedIdentifier = cat.personUser.assignedIdentifier == null ? "":cat.personUser.assignedIdentifier;
              var name = cat.personUser.firstName + " " + cat.personUser.lastName+ " (" +  cat.personUser.assignedIdentifier+ ") ("+ cat.roleName +")";
              var opt = new Option(name, cat.personUser.id + "(" + cat.roleName + ")");
              studyPersonnelSelect.options.add(opt);
          })
          $('sitePersonnelIndicator').hide();
      })   
    } 
}

/**
 * Copy personnel from  [Personnel MultiSelect]
 *   to the [Selected Personnel MultiSelect]
 *
 */
function addPersonnelToCart() {
    var studyPersonnel = $("study-personnel");
    var studyPersonnelSelected = $("study-personnel-sel");
    var studyPersonnelSelectedHidden = $("study-personnel-sel-hidden");
    if (studyPersonnelSelected.options[0].value == "") {
        studyPersonnelSelected.options.length = 0
    }
    // If all is selected  in the [Diseases MultiSelect]
    if (studyPersonnel.options[0].selected) {
        for (i = 1; i < studyPersonnel.length; i++)
        {
            var opt = new Option(studyPersonnel.options[i].text, studyPersonnel.options[i].value)
            var opt1 = new Option(studyPersonnel.options[i].text, studyPersonnel.options[i].value)
            studyPersonnelSelected.options.add(opt)
            studyPersonnelSelectedHidden.options.add(opt1)
        }
    }
    // If anything other than all is selected
    else {
        for (i = 1; i < studyPersonnel.length; i++)
        {
            if (studyPersonnel.options[i].selected) {
                var opt = new Option(studyPersonnel.options[i].text, studyPersonnel.options[i].value)
                studyPersonnelSelected.options.add(opt)
            }
        }
    }
    // Copy over [Selected Diseases MultiSelect] to [Hidden Selected Diseases MultiSelect]
    synchronizeSelects(studyPersonnelSelected, studyPersonnelSelectedHidden);
}

function synchronizeSelects(selectFrom, selectTo)
{
    // Delete everything from the target
    selectTo.options.length = 0;
    // iterate over the source and add to target
    for (i = 0; i < selectFrom.length; i++) {
        var opt = new Option(selectFrom.options[i].text, selectFrom.options[i].value)
        selectTo.options.add(opt)
        selectTo.options[i].selected = true;
    }
}
var win;
Event.observe(window, "load", function() {
    $('study-personnel-sel').style.display = 'none';
    $('study-personnel-sel-hidden').style.display = 'none';

  	showPersonnel();
})

function showCreateResearchStaffPopup(){
	win = new Window(
			{title: "Create Research Staff", top:35, left:35, width:900, height:400, zIndex:100,
			url: "<c:url value='/pages/personAndOrganization/personOrUser/createPersonOrUser?decorator=noheaderDecorator&studyflow=true'/>", showEffectOptions: {duration:1.5}}
			) 
	win.showCenter(true);
}
function closePopup() {
	win.close();
}

</script>
</head>

<body>
<form:form cssClass="standard">
<%--For actions like adding personnel, removing personnel and changing site selection from dropdown, the hidden parameter values are
changed before submit in javascripts. The parameters need proper default values, for click on save.--%>
<input type="hidden" id="_doNotSave" name="_doNotSave" value="false">
<input type="hidden" id="_actionx" name="_actionx" value="save">
<input type="hidden" id="_selectedPersonnelAssignedId" name="_selectedPersonnelAssignedId" value="">
<input type="hidden" id="_selectedStudyOrganization" name="_selectedStudyOrganization" value="${!empty selectedStudyOrganization?selectedStudyOrganization.id:''}">
<input type="hidden" id="_studyId" name="_studyId" value="${command.study.id}">



<c:choose>
	<c:when test="${fn:length(command.study.studyOrganizations) == 0}">
        <tr>
			<td>Choose a study organization before adding personnel</td>
		</tr>
    </c:when>
    <c:otherwise>
		<table border="0" id="table1" cellspacing="10" width="100%">
			<tr>
			<td valign="top" width="45%">
				<tags:errors path="*"/> 
				<chrome:box title="${tab.shortTitle}">
					<div>
			            <br/>&nbsp;<b><fmt:message key="c3pr.common.selectAnStudySite"/></b><br>
			            <select id="studyOrganizationSelect" onchange="changeStudyOrganization();" style="width: 400px">   
		                    <c:forEach items="${command.study.studyOrganizations}" var="studyOrganization" varStatus="status">
		                    	<c:set var="canDisplay" value="false"/>
		                    	<c:choose>
		                    		<c:when test="${studyOrganization.class.name == 'edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' && !command.hasCoordinatingCenterAsStudySite}">
		                    			<c:set var="canDisplay" value="true"/>
		                    			<c:set var="orgType" value="Coordinating Center"/>
		                    		</c:when>
		                    		<c:when test="${studyOrganization.class.name == 'edu.duke.cabig.c3pr.domain.StudyFundingSponsor' && !command.hasFundingSponsorAsStudySite}">
		                    			<c:set var="canDisplay" value="true"/>				
		                    			<c:set var="orgType" value="Funding Sponsor"/>																										
		                    		</c:when>
		                    		<c:when test="${studyOrganization.class.name == 'edu.duke.cabig.c3pr.domain.StudySite'}">
		                    			<c:set var="canDisplay" value="true"/>
		                    			<c:set var="orgType" value="Study Site"/>
		                    			<c:if test="${studyOrganization.healthcareSite.primaryIdentifier == command.study.studyCoordinatingCenter.healthcareSite.primaryIdentifier}">
		                    				<c:set var="orgType" value="${orgType}, Coordinating Center"/>
		                    			</c:if>
		                    			<c:if test="${fn:length(command.study.studyFundingSponsors)>0 && studyOrganization.healthcareSite.primaryIdentifier == command.study.studyFundingSponsors[0].healthcareSite.primaryIdentifier}">
		                    				<c:set var="orgType" value="${orgType}, Funding Sponsor"/>
		                    			</c:if>
		                    		</c:when>
		                    	</c:choose>
		                    	<c:if test="${canDisplay}">
			                        <csmauthz:accesscontrol domainObject="${studyOrganization}" hasPrivileges="STUDYPERSONNEL_CREATE"  
				                                                authorizationCheckName="studySiteAuthorizationCheck">
				                        <c:if test="${empty selectedStudyOrganization}">
				                        	<c:set var="selectedStudyOrganization" value="${studyOrganization}"/>
				                        	<c:set var="selected_site_index" value="${status.index}"/>
				                        	<script>
				                        		$('_selectedStudyOrganization').value = "${selectedStudyOrganization.id}";
				                        	</script>
				                        </c:if>
				                        <c:choose>
				                        <c:when test="${selectedStudyOrganization.healthcareSite.primaryIdentifier == studyOrganization.healthcareSite.primaryIdentifier }">
				                            <option selected value="${studyOrganization.id}-${studyOrganization.healthcareSite.id}">${studyOrganization.healthcareSite.name} (${studyOrganization.healthcareSite.primaryIdentifier}) -${orgType }</option>
				                        </c:when>
				                        <c:otherwise>
				                        	<option value="${studyOrganization.id}-${studyOrganization.healthcareSite.id}">${studyOrganization.healthcareSite.name} (${studyOrganization.healthcareSite.primaryIdentifier}) -${orgType }</option>
				                        </c:otherwise>
				                        </c:choose>
			                        </csmauthz:accesscontrol>
		                        </c:if>
		                    </c:forEach>
		                </select>
			            
			            <br><br><b>&nbsp;<fmt:message key="c3pr.common.participatingSitePersonnel"/></b>
   						<img id="sitePersonnelIndicator" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none"/>
			            <br>
			            <select multiple size="1" style="width:400px" id="study-personnel">
			            </select> <span id="study-personnel-selected-name"></span>
			            <select multiple size="10" id="study-personnel-sel">
			                <option value="">No participating study personnel for this study site.</option>
			            </select>
			            
			            <form:select id="study-personnel-sel-hidden" size="1" path="studyPersonnelIds" />
			        <br/>
	               </div>	        
			    </chrome:box>
			</td>
			<td valign="middle">
			<tags:button type="button" icon="continue" size="small" color="blue" value="Add" onclick="addStudyPersonnel();"/>
	        </td>
			<td valign="top" width="45%">
			    <chrome:box title="${selectedStudyOrganization.healthcareSite.name}" id="selectedStudyOrgDiv">
			        &nbsp;<b><fmt:message key="c3pr.common.selectedSitePersonnel" /></b><br>
			        <c:choose>
			            <c:when test="${fn:length(selectedStudyOrganization.studyPersonnel) == 0}">
			                <fmt:message key="c3pr.common.noPersonnels" />
			            </c:when>			
			            <c:otherwise>
			                <table border="1" class="tablecontent" >
			                    <tr>
			                        <th scope="col"><fmt:message key="c3pr.common.name"/></th>
			                        <th width="20%"><fmt:message key="c3pr.common.status"/><tags:hoverHint keyProp="study.personnel.status"/></th>
			                        <th width="5%"></th>
			                    </tr>
			                    <c:forEach items="${selectedStudyOrganization.studyPersonnel}" var="studyPersonnel" varStatus="status">
			                    	<c:forEach items="${studyPersonnel.studyPersonnelRoles}" var="spRole" varStatus="spRoleStatus">
				                        <tr>
				                            <td>
				                             ${studyPersonnel.personUser.firstName} ${studyPersonnel.personUser.lastName} (<i>${studyPersonnel.personUser.assignedIdentifier}</i>) (<i>${spRole.role}</i>)
				                                <c:if test="${studyPersonnel.personUser.class.name=='edu.duke.cabig.c3pr.domain.RemotePersonUser' && studyPersonnel.personUser.externalId != null}">
								            		<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="NCI data" width="17" height="16" border="0" align="middle"/>
								            	</c:if>
				                            </td>
				                            <td>
				                            <form:select path="study.studyOrganizations[${selected_site_index}].studyPersonnel[${status.index}].statusCode" cssClass="required validate-notEmpty">
						                        <form:options items="${studyPersonnelStatusRefData}" itemLabel="desc" itemValue="desc"/>
						                    </form:select>
				                            </td>
				                            <td class="alt">
					                            <a href="javascript:removeStudyPersonnel('${studyPersonnel.personUser.id}');">
					                                <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove">
					                            </a>&nbsp;
					                        </td>
				                        </tr>
				                    </c:forEach>
			                    </c:forEach>
			                </table>
			            </c:otherwise>
			        </c:choose>
			    </chrome:box>  
			</td>
			</tr>			
		</table>

  </c:otherwise>
</c:choose>
<div align="right">
	<c3pr:checkprivilege hasPrivileges="UI_RESEARCHSTAFF_CREATE">
		<tags:button id="createPersonnel" type="button" size="small" color="blue" value="Create Research Staff" onclick="showCreateResearchStaffPopup();"/>
	</c3pr:checkprivilege>
</div>
<br/>
<div class="flow-buttons">
	<span class="next">
		<tags:button type="button" color="blue" value="Save" icon="save" onclick="saveStudyPersonnel();"/>
 	</span>
 </div>

</form:form>
</body>
</html>
