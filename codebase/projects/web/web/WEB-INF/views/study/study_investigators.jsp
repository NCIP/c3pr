<%@ include file="taglibs.jsp"%>

<html>
<title><studyTags:htmlTitle study="${command.study}" /></title>

<head>
<%--<tags:includeScriptaculous/>--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>

<title>${tab.longTitle}</title>

<script type="text/javascript">

function removeStudyInvestigator(selectedStudyInvAssignedId) {
	$('_doNotSave').value = true;
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
    $('_actionx').value = "removeStudyInvestigator";
    $('_selectedInvAssignedId').value=selectedStudyInvAssignedId;
	$('command').submit();
}

function changeStudyOrganization(){
	$('_doNotSave').value = true;
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
	$('_actionx').value = "changeStudyOrganization";
	$('command').submit();
}

function addStudyInvestigators(){
	addStudyInvestigatorsToCart();
	$('_doNotSave').value = true;
	$('_selectedStudyOrganization').value=$('studyOrganizationSelect').value.split("-")[0];
	$('_actionx').value = "addStudyInvestigators";
	$('command').submit();
}

function updateStudyInvGroups() {
	id= $('studyOrganizationSelect').value.split("-")[1];

    StudyAjaxFacade.matchActiveGroupsByOrganizationId(id, function(categories) {
        var sel = $("study-inv-sub-category")
        sel.size = categories.length < 10 ? categories.length + 2 : 10;
        //sel.size= 10
        sel.options.length = 0
        sel.options.add(new Option("All", ""))
        sel.options[0].selected = true;
        categories.each(function(cat) {
            var opt = new Option(cat.name, cat.id)
            sel.options.add(opt)
        })
        showStudyInvestigators()
    })
}

function showStudyInvestigators() {
    var categoryId = $("study-inv-sub-category").value;
    var subCategorySelect = $("study-inv-sub-category")

    // If all is selected
    if (subCategorySelect.value == "") {
        var studyInvestigatorsSelect = $("study-inv")
        studyInvestigatorsSelect.options.length = 0
        studyInvestigatorsSelect.size = 10
        studyInvestigatorsSelect.options.add(new Option("All", ""))
        studyInvestigatorsSelect.options[0].selected = true;
        //call the getAll method for the selected organization instead of calling getInvestigatorsById() for every group.
        var catId = $('studyOrganizationSelect').value.split("-")[1];
        StudyAjaxFacade.getActiveSiteInvestigators(catId, function(studyInvestigators) {
              studyInvestigators.each(function(cat) {
            	var name = cat.investigator.firstName + " " +  cat.investigator.lastName + " (" +  cat.investigator.assignedIdentifier + ")";
                var opt = new Option(name, cat.id)
                studyInvestigatorsSelect.options.add(opt)
            })
        })
    }
    else {
        StudyAjaxFacade.matchActiveInvestigatorsByGroupId(categoryId, function(studyInvestigators) {
            var sel = $("study-inv")
            sel.size = studyInvestigators.length + 2;
            sel.options.length = 0
            sel.options.add(new Option("All", ""))
            sel.options[0].selected = true;
            studyInvestigators.each(function(cat) {
            	var name = cat.investigator.firstName + " " +  cat.investigator.lastName + " (" +  cat.investigator.assignedIdentifier + ")";
                var opt = new Option(name, cat.id)
                sel.options.add(opt)
            })
        })
    }
}

function addStudyInvestigatorsToCart() {
    var studyInvestigatorsSelect = $("study-inv");
    var studyInvestigatorsChosen = $("study-inv-sel");
    var studyInvestigatorsHidden = $("study-inv-sel-hidden");
    if (studyInvestigatorsChosen.options[0].value == "") {
        studyInvestigatorsChosen.options.length = 0
    }
    // If all is selected  in the [Study Inv MultiSelect]
    if (studyInvestigatorsSelect.options[0].selected) {
        for (i = 1; i < studyInvestigatorsSelect.length; i++)
        {
            var opt = new Option(studyInvestigatorsSelect.options[i].text, studyInvestigatorsSelect.options[i].value)
            var opt1 = new Option(studyInvestigatorsSelect.options[i].text, studyInvestigatorsSelect.options[i].value)
            studyInvestigatorsChosen.options.add(opt)
            studyInvestigatorsHidden.options.add(opt1)
        }
    }
    // If anything other than all is selected
    else {
        for (i = 1; i < studyInvestigatorsSelect.length; i++)
        {
            if (studyInvestigatorsSelect.options[i].selected) {
                var opt = new Option(studyInvestigatorsSelect.options[i].text, studyInvestigatorsSelect.options[i].value)
                studyInvestigatorsChosen.options.add(opt)
            }
        }
    }
    // Copy over [Selected Study Inv MultiSelect] to [Hidden Selected Study Inv MultiSelect]
    synchronizeSelects(studyInvestigatorsChosen, studyInvestigatorsHidden);
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
    $('study-inv-sel').style.display = 'none';
    $('study-inv-sel-hidden').style.display = 'none';

    Event.observe("study-inv-sub-category", "change", function() {
        showStudyInvestigators()
    })

    updateStudyInvGroups();
})

function showCreateInvestigatorPopup(){
	win = new Window(
			{title: "Create Investigator", top:35, left:35, width:900, height:400, zIndex:100,
			url: "<c:url value='/pages/personAndOrganization/investigator/createInvestigator?decorator=noheaderDecorator&studyflow=true'/>", showEffectOptions: {duration:1.5}}
			)
	win.showCenter(true);
}

function closePopup() {
	win.close();
}

</script>
</head>

<body>
<form:form>
<%--For actions like adding investigator, removing investigator and changing site selection from dropdown, the hidden parameter values are
changed before submit in javascripts. The parameters need proper default values, for click on save.--%>
<input type="hidden" id="_doNotSave" name="_doNotSave" value="false">
<input type="hidden" id="_actionx" name="_actionx" value="save">
<input type="hidden" id="_selectedInvAssignedId" name="_selectedInvAssignedId" value="">
<input type="hidden" id="_selectedStudyOrganization" name="_selectedStudyOrganization" value="${!empty selectedStudyOrganization?selectedStudyOrganization.id:''}">

<c:choose>
	<c:when test="${fn:length(command.study.studyOrganizations) == 0}">
        <tr>
			<td><fmt:message key="study.investigator.noStudySite" /></td>
		</tr>
    </c:when>
    <c:otherwise>
		<table border="0" id="table1" cellspacing="10" width="100%">
			<tr>
			<td valign="top" width="45%">
			<tags:errors path="study.studySites[0].studyInvestigators"/>
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
			                        <csmauthz:accesscontrol domainObject="${studyOrganization}" hasPrivileges="ACCESS"  
				                                                authorizationCheckName="studySiteAuthorizationCheck">
				                        <c:if test="${empty selectedStudyOrganization}">
				                        	<c:set var="selectedStudyOrganization" value="${studyOrganization}"/>
				                        	<c:set var="selected_site_index" value="${status.index}"/>
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

			            <p id="study-inv-selected" style="display: none"></p>
			            <br><br><b>&nbsp;<fmt:message key="c3pr.common.selectInvGroupToAdd"/></b><br>
			            <select multiple size="1" style="width:400px" id="study-inv-sub-category">
			                <option value="">Please select a Group first</option>
			            </select>

			            <br><br><b>&nbsp;<fmt:message key="c3pr.common.participatingSiteInv"/></b><br>
			            <select multiple size="1" style="width:400px" id="study-inv">
			                <option value="">Please select a Group first</option>
			            </select> <span id="study-inv-selected-name"></span>
			            <select multiple size="10" id="study-inv-sel">
			                <option value="">No Selected Investigators</option>
			            </select>

			            <form:select id="study-inv-sel-hidden" size="1" path="studyInvestigatorIds" />
			        <br/>
	               </div>
			    </chrome:box>
			</td>
			<td valign="middle">
				<tags:button type="button" icon="continue" size="small" color="blue" value="Add" id="add" onclick="addStudyInvestigators();"/>
	        </td>
			<td valign="top" width="45%">
				<chrome:box title="${selectedStudyOrganization.healthcareSite.name}" id="selectedStudyOrgDiv">
			        <br/>
			        <c:choose>
			            <c:when test="${fn:length(selectedStudyOrganization.studyInvestigators) == 0}">
			                <fmt:message key="c3pr.common.noInvestigators" />
			            </c:when>			
			            <c:otherwise>
			                <table border="1" class="tablecontent" >
			                    <tr>
			                        <th scope="col"><fmt:message key="c3pr.common.name"/></th>
			                        <th width="20%"><fmt:message key="c3pr.common.status"/><tags:hoverHint keyProp="study.investigator.status"/></th>
			                        <th width="5%"></th>
			                    </tr>
			                    <c:forEach items="${selectedStudyOrganization.studyInvestigators}" var="studyInvestigator" varStatus="status">
		                        <tr>
			                    	<td>
		                              ${studyInvestigator.healthcareSiteInvestigator.investigator.firstName}&nbsp;${studyInvestigator.healthcareSiteInvestigator.investigator.lastName}
		                            </td>
		                            <td>
		                            <form:select path="study.studyOrganizations[${selected_site_index}].studyInvestigators[${status.index}].statusCode" cssClass="required validate-notEmpty">
				                        <form:options items="${studyInvestigatorStatusRefData}" itemLabel="desc" itemValue="code"/>
				                    </form:select>
		                            </td>
		                            <td class="alt">
			                            <a href="javascript:removeStudyInvestigator('${studyInvestigator.healthcareSiteInvestigator.investigator.assignedIdentifier }');">
			                                <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove">
			                            </a>&nbsp;
			                        </td>
		                        </tr>
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
	<csmauthz:accesscontrol domainObject="${command.study}" hasPrivileges="CREATE"
                            authorizationCheckName="studyAuthorizationCheck">
		<tags:button id="createInvestigator" type="button" size="small" color="blue" value="Create Investigator" onclick="showCreateInvestigatorPopup();"/>
	</csmauthz:accesscontrol>
</div>
<br/>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" isFlow="false"/>
</form:form>
</body>
</html>