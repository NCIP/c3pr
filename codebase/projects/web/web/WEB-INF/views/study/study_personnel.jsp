<%@ include file="taglibs.jsp"%>

<html>
<title><studyTags:htmlTitle study="${command}" /></title>

<c:set var="selected_site" value="0"/>
<c:if test="${not empty selectedSite}">
	<c:set var="selected_site" value="${selectedSite}"/>
</c:if>
<head>
<%--<tags:includeScriptaculous/>--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>

<title>${tab.longTitle}</title>

<script type="text/javascript">
function validatePage() {
    return true;
}

function fireAction(action, selected) {
    if (validatePage()) {
        
        addDiseasesToCart()
        document.getElementById('command')._target.name = '_noname';
        $('_actionx').value = action;
        $('_selectedSite').value = document.getElementById('site').selectedIndex;
        $('_selected').value = selected;
        document.myform.submit();
    }
}

function clearField(field) {
    field.value = "";
}

function hover(index)
{
}

function acPostSelect(mode, selectedChoice) {
    showDiseases();
}

function updateSelectedDisplay(mode) {
    if ($(mode.basename).value) {
        Element.update(mode.basename + "-selected-name", $(mode.basename + "-input").value)
        $(mode.basename + '-selected').show()
    }
}

function acCreate(mode) {
    new Autocompleter.DWR(mode.basename + "-input", mode.basename + "-choices",
            mode.populator, {
        valueSelector: mode.valueSelector,
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
            acPostSelect(mode, selectedChoice)
        },
        indicator: mode.basename + "-indicator"
    })
    Event.observe(mode.basename + "-clear", "click", function() {
        $(mode.basename + "-selected").hide()
        $(mode.basename).value = ""
        $(mode.basename + "-input").value = ""
    })
}


function showDiseases() {
  //  var categoryId = $("disease-sub-category").value    
  //  var subCategorySelect = $("disease-sub-category")
    var siteSelect = $("site").value;
    
    // If all is selected
        var diseaseTermSelect = $("disease-term")
        diseaseTermSelect.options.length = 0
        diseaseTermSelect.size = 10
        diseaseTermSelect.options.add(new Option("All", ""))
        diseaseTermSelect.options[0].selected = true;
        //call the getAll method for the selected organization instead of calling getInvestigatorsById() for every group.
        //for (i = 0; i < siteSelect.length; i++) {
            var catId = siteSelect
	        StudyAjaxFacade.getSitePersonnel(catId, function(diseases) {
	              diseases.each(function(cat) {
	               	var name = cat.lastName + " (" +  cat.nciIdentifier + ")";
	                var opt = new Option(name, cat.id)
	                diseaseTermSelect.options.add(opt)
	            })
	        })
	    //}
}

/**
 * Copy Diseases from  [Diseases MultiSelect]
 *   to the [Selected Diseases MultiSelect]
 *
 */
function addDiseasesToCart() {
    var diseaseTerm = $("disease-term");
    var diseaseSelected = $("disease-sel");
    var diseaseSelectedHidden = $("disease-sel-hidden");
    if (diseaseSelected.options[0].value == "") {
        diseaseSelected.options.length = 0
    }
    // If all is selected  in the [Diseases MultiSelect]
    if (diseaseTerm.options[0].selected) {
        for (i = 1; i < diseaseTerm.length; i++)
        {
            var opt = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
            var opt1 = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
            diseaseSelected.options.add(opt)
            diseaseSelectedHidden.options.add(opt1)
        }
    }
    // If anything other than all is selected
    else {
        for (i = 1; i < diseaseTerm.length; i++)
        {
            if (diseaseTerm.options[i].selected) {
                var opt = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
                diseaseSelected.options.add(opt)
            }
        }
    }
    // Copy over [Selected Diseases MultiSelect] to [Hidden Selected Diseases MultiSelect]
    //selectAll(diseaseSelectedHidden)
    synchronizeSelects(diseaseSelected, diseaseSelectedHidden);
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

function removeDiseasesFromCart()
{
    var diseaseSelected = $("disease-sel");
    var diseaseSelectedHidden = $("disease-sel-hidden");

    for (i = 0; i < diseaseSelected.length; i++)
    {
        if (diseaseSelected.options[i].selected) {
            diseaseSelected.options[i] = null
        }
    }
    synchronizeSelects(diseaseSelected, diseaseSelectedHidden)
}

Event.observe(window, "load", function() {
    $('disease-sel').style.display = 'none';
    $('disease-sel-hidden').style.display = 'none';

    //acCreate(diseaseAutocompleterProps)
    //updateSelectedDisplay(diseaseAutocompleterProps)

  //  Event.observe("disease-sub-category", "change", function() {
    //    showDiseases()
  //  })
  	showDiseases();
    populateSelectsOnLoad();
})

</script>
</head>

<body>
<!-- selected_site is a c:set var which is request attrubute. 
it has the value that is set in the _selectedSite by the controller.
and the controller gets the selected index via the hidden variable _selectedSite.
(Look at the fireAction() method in the js on top.) -->
<c:set var="selected_site" value="0"/>
<c:if test="${not empty _selectedSite}">
	<c:set var="selected_site" value="${_selectedSite}"/>
</c:if>

<form:form method="post" name="myform" cssClass="standard">
<tags:tabFields tab="${tab}"/>

<input type="hidden" id="_actionx" name="_actionx" value="">
<input type="hidden" id="_selected" name="_selected" value="">
<input type="hidden" id="_selectedSite" name="_selectedSite" value="">

<c:choose>
	<c:when test="${fn:length(command.studyOrganizations) == 0}">
        <tr>
			<td>Choose a study organization before adding investigators</td>
		</tr>
    </c:when>
    <c:otherwise>

		<table border="0" id="table1" cellspacing="10" width="100%">
			<tr>
			<td valign="top" width="45%">
			<tags:errors path="studyOrganizations[0].studyPersonnel"/> 
				<chrome:box title="${tab.shortTitle}">
					<div>
			            <br/>&nbsp;<b>Select an Organization</b><br>
			            <input:hidden id="disease"/>
			            <select id="site" name="site" onchange="fireAction('siteChange','0');">   
			                    <c:forEach items="${command.studyOrganizations}" var="studySite" varStatus="status">
			                        <csmauthz:accesscontrol domainObject="${studySite.healthcareSite}"
			                                                hasPrivileges="ACCESS"  authorizationCheckName="siteAuthorizationCheck">
			                        <c:if test="${selected_site == status.index }">
			                            <option selected="selected" value=${studySite.healthcareSite.id}>${studySite.healthcareSite.name} (${studySite.healthcareSite.nciInstituteCode})
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' }"> (Coordinating Center) </c:if>
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyFundingSponsor' }"> (Funding Sponsor) </c:if> 
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudySite' }"> (Site) </c:if>			                            
			                            </option>			                            
			                        </c:if>
			                        <c:if test="${selected_site != status.index }">
			                            <option value=${studySite.healthcareSite.id}>${studySite.healthcareSite.name} 
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter' }"> (Coordinating Center) </c:if>
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudyFundingSponsor' }"> (Funding Sponsor) </c:if> 
			                            <c:if test="${studySite.class eq 'class edu.duke.cabig.c3pr.domain.StudySite' }"> (Site) </c:if>
			                            </option>
			                        </c:if>
			                        </csmauthz:accesscontrol>
			                    </c:forEach>
			                </select>
			            <tags:indicator id="disease-indicator"/>
			            
			            <br><br><b>&nbsp;Research Staff</b><br>
			            <select multiple size="1" style="width:400px" id="disease-term">
			            </select> <span id="disease-selected-name"></span>
			            <select multiple size="10" id="disease-sel">
			                <option value="">No Selected Research Staff</option>
			            </select>
			            
			            <form:select id="disease-sel-hidden" size="1" path="studyOrganizations[${selected_site}].studyPersonnelIds" />
			        <br/>
	               </div>	        
			    </chrome:box>
			</td>
			<td valign="middle">
				<input type="button" value="Add Research Staff >>" onclick="fireAction('addStudyDisease','0');" alt="Add Research Staff"/>
	        </td>
			<td valign="top" width="45%">
			    <chrome:box title="${command.studyOrganizations[selected_site].healthcareSite.name}" id="diseases">
			        <br/>
			        <c:choose>
			            <c:when test="${fn:length(command.studyOrganizations[selected_site].studyPersonnel) == 0}">
			                No Research Staff Selected
			            </c:when>			
			            <c:otherwise>
			                <table border="1" class="tablecontent" >
			                    <tr>
			                        <th scope="col">Name</th>
									<th width="20%">Role<tags:hoverHint keyProp="study.personnel.role"/></th>
			                        <th width="20%">Status<tags:hoverHint keyProp="study.personnel.status"/></th>
			                        <th width="5%"></th>
			                    </tr>
			                    <c:forEach items="${command.studyOrganizations[selected_site].studyPersonnel}" var="studyPersonnel"
			                               varStatus="status">
		                        <tr>
		                            <td>
		                              ${studyPersonnel.researchStaff.lastName}&nbsp;${studyPersonnel.researchStaff.firstName}
		                            </td>
									<td>
		                            <form:select path="studyOrganizations[${selected_site}].studyPersonnel[${status.index}].roleCode" cssClass="validate-notEmpty">
				                        <option value="">Please Select</option>
				                       <form:options items="${studyPersonnelRoleRefData}" itemLabel="desc" itemValue="desc"/>
				                    </form:select>
		                            </td>
		                            <td>
		                            <form:select path="studyOrganizations[${selected_site}].studyPersonnel[${status.index}].statusCode" cssClass="validate-notEmpty">
				                        <option value="">Please Select</option>
				                        <form:options items="${studyPersonnelStatusRefData}" itemLabel="desc" itemValue="desc"/>
				                    </form:select>
		                            </td>
		                            <td class="alt">
			                            <a href="javascript:fireAction('removeStudyDisease',${status.index});">
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
</c:choose><br/>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}"/>  
</form:form>

</body>
</html>
