<%@ include file="taglibs.jsp"%>
<%@ taglib prefix="csmauthz" uri="http://csm.ncicb.nci.nih.gov/authz" %>

<html>
<title><studyTags:htmlTitle study="${command.study}" /></title>

<c:set var="selected_site" value="0"/>
<c:if test="${not empty selectedSite}">
	<c:set var="selected_site" value="${selectedSite}"/>
</c:if>
<head>
<%--<tags:includeScriptaculous/>--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>

<title>${tab.longTitle}</title>

<script type="text/javascript">

function fireAction(action, selected) {
        addPersonnelToCart()
         $('_doNotSave').value = true;
        $('_actionx').value = action;
        $('_selectedSite').value = document.getElementById('site').selectedIndex;
        $('_selected').value = selected;
        document.myform.submit();
    }

function closePopup() {
	win.close();
}

function acPostSelect(mode, selectedChoice) {
    showPersonnel();
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


function showPersonnel() {
    var siteSelect = $("site").value;
    // If all is selected
        var diseaseTermSelect = $("disease-term")
        diseaseTermSelect.options.length = 0
        diseaseTermSelect.size = 10
        diseaseTermSelect.options.add(new Option("All", ""))
        diseaseTermSelect.options[0].selected = true;
        //call the getAll method for the selected organization instead of calling getInvestigatorsById() for every group.
            var catId = siteSelect
	        StudyAjaxFacade.getSitePersonnel(catId, function(diseases) {
	              diseases.each(function(cat) {
	               	var name = cat.lastName + " " + cat.firstName+ " (" +  cat.nciIdentifier + ")";
	                var opt = new Option(name, cat.id)
	                diseaseTermSelect.options.add(opt)
	            })
	        })
}

/**
 * Copy personnel from  [Personnel MultiSelect]
 *   to the [Selected Personnel MultiSelect]
 *
 */
function addPersonnelToCart() {
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

function removePersonnelFromCart()
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
var win;
Event.observe(window, "load", function() {
    $('disease-sel').style.display = 'none';
    $('disease-sel-hidden').style.display = 'none';

  	showPersonnel();

    $('createPersonnel').observe('click', function(event) {
    	win = new Window(
				{title: "Create Research Staff", top:35, left:35, width:850, height:400, zIndex:100,
				url: "<c:url value='/pages/personAndOrganization/researchStaff/createResearchStaff?decorator=noheaderDecorator&studyflow=true'/>", showEffectOptions: {duration:1.5}}
				) 
		win.showCenter(true);

});
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

<input type="hidden" id="_doNotSave" name="_doNotSave" value="">
<input type="hidden" id="_actionx" name="_actionx" value="">
<input type="hidden" id="_selected" name="_selected" value="">
<input type="hidden" id="_selectedSite" name="_selectedSite" value="">

<c:choose>
	<c:when test="${fn:length(command.study.studySites) == 0}">
        <tr>
			<td>Choose a study organization before adding personnel</td>
		</tr>
    </c:when>
    <c:otherwise>
		<table border="0" id="table1" cellspacing="10" width="100%">
			<tr>
			<td valign="top" width="45%">
			<tags:errors path="study.studySites[0].studyPersonnel"/> 
				<chrome:box title="${tab.shortTitle}">
					<div>
			            <br/>&nbsp;<b><fmt:message key="c3pr.common.selectAnStudySite"/></b><br>
			            <input:hidden id="disease"/>
			            <select id="site" name="study.site" onchange="fireAction('siteChange','0');" style="width: 400px">   
		                    <c:forEach items="${command.study.studySites}" var="studySite" varStatus="status">
		                        <csmauthz:accesscontrol domainObject="${studySite}" hasPrivileges="ACCESS"  
			                                                authorizationCheckName="studySiteAuthorizationCheck">
			                        <c:if test="${selected_site == status.index }">
			                            <option selected="selected" value=${studySite.healthcareSite.id}>${studySite.healthcareSite.name} (${studySite.healthcareSite.ctepCode})
			                            </option>			                            
			                        </c:if>
			                        <c:if test="${selected_site != status.index }">
			                            <option value=${studySite.healthcareSite.id}>${studySite.healthcareSite.name} 
			                            </option>
			                        </c:if>
		                        </csmauthz:accesscontrol>
		                    </c:forEach>
		                </select>
			            <tags:indicator id="disease-indicator"/>
			            
			            <br><br><b>&nbsp;<fmt:message key="c3pr.common.participatingSitePersonnel"/></b><br>
			            <select multiple size="1" style="width:400px" id="disease-term">
			            </select> <span id="disease-selected-name"></span>
			            <select multiple size="10" id="disease-sel">
			                <option value="">No participating study personnel for this study site.</option>
			            </select>
			            
			            <form:select id="disease-sel-hidden" size="1" path="study.studySites[${selected_site}].studyPersonnelIds" />
			        <br/>
	               </div>	        
			    </chrome:box>
			</td>
			<td valign="middle">
			<tags:button type="button" icon="continue" size="small" color="blue" value="Add" onclick="fireAction('addStudyPerson','0');"/>
	        </td>
			<td valign="top" width="45%">
			    <chrome:box title="${command.study.studySites[selected_site].healthcareSite.name}" id="diseases">
			        <br/>
			        <c:choose>
			            <c:when test="${fn:length(command.study.studySites[selected_site].studyPersonnel) == 0}">
			                <fmt:message key="c3pr.common.noPersonnels" />
			            </c:when>			
			            <c:otherwise>
			                <table border="1" class="tablecontent" >
			                    <tr>
			                        <th scope="col"><fmt:message key="c3pr.common.name"/></th>
									<%-- <th width="20%"><fmt:message key="c3pr.common.role"/><tags:hoverHint keyProp="study.personnel.role"/></th>  --%>
			                        <th width="20%"><fmt:message key="c3pr.common.status"/><tags:hoverHint keyProp="study.personnel.status"/></th>
			                        <th width="5%"></th>
			                    </tr>
			                    <c:forEach items="${command.study.studySites[selected_site].studyPersonnel}" var="studyPersonnel"
			                               varStatus="status">
		                        <tr>
		                            <td>
		                              ${studyPersonnel.researchStaff.lastName}&nbsp;${studyPersonnel.researchStaff.firstName}
		                                <c:if test="${studyPersonnel.researchStaff.class.name=='edu.duke.cabig.c3pr.domain.RemoteResearchStaff' && studyPersonnel.researchStaff.externalId != null}">
						            		<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="NCI data" width="17" height="16" border="0" align="middle"/>
						            	</c:if>
		                            </td>
									<%--  <td>
		                            <form:select path="study.studySites[${selected_site}].studyPersonnel[${status.index}].roleCode" cssClass="required validate-notEmpty">
				                       <form:options items="${studyPersonnel.researchStaff.groups}" itemLabel="displayName" itemValue="code"/>
				                    </form:select>
		                            </td> --%>
		                            <td>
		                            <form:select path="study.studySites[${selected_site}].studyPersonnel[${status.index}].statusCode" cssClass="required validate-notEmpty">
				                        <form:options items="${studyPersonnelStatusRefData}" itemLabel="desc" itemValue="desc"/>
				                    </form:select>
		                            </td>
		                            <td class="alt">
			                            <a href="javascript:fireAction('removeStudyPerson',${status.index});">
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
		<tags:button id="createPersonnel" type="button" size="small" color="blue" value="Create Research Staff"/>
	</csmauthz:accesscontrol>
</div>
<br/>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" isFlow="false"/>
</form:form>

</body>
</html>
