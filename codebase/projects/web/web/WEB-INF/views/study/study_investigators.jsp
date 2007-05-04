<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study"%>


<html>
<head>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="createStudy"/>

<title>${tab.longTitle}</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<script language="JavaScript" type="text/JavaScript">

function fireAction(action, selectedSite, selectedInvestigator){
	document.getElementById('command').targetPage.name='_noname';
	document.form._action.value=action;
	document.form._selectedSite.value=selectedSite;
	document.form._selectedInvestigator.value=selectedInvestigator;
	// need to disable validations while submitting
//	role = 'studySites['+selectedSite+'].studyInvestigators['+selectedInvestigator+'].roleCode';
//	$(role).className='none';
//	status = 'studySites['+selectedSite+'].studyInvestigators['+selectedInvestigator+'].statusCode';
//	$(status).className='none';
	document.form.submit();
	fireListeners(selected);
}

function chooseSites(){
	document.getElementById('command').targetPage.name='_noname';	
	document.form._action.value="siteChange";
	document.form._selectedSite.value=document.getElementById('site').value;
	document.form.submit();
}

function chooseSitesFromSummary(selected){
	document.getElementById('command').targetPage.name='_noname';	
	document.form._action.value="siteChange";
	document.form._selectedSite.value=selected;
	document.form.submit();
}

/// AJAX
var investigatorAutocompleterProps = {
	basename: "investigator",
    populator: function(autocompleter, text) {
    
    createStudy.matchSiteInvestigators(text, document.getElementById('site').value, function(values) {
	    autocompleter.setChoices(values)
	})
    },
    valueSelector: function(obj) {
	return obj.investigator.fullName
    }
}

function acPostSelect(mode, selectedChoice) {
	$(mode.basename).value = selectedChoice.id;
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
	//$(mode.basename + "-selected").hide()
	$(mode.basename).value = ""
	$(mode.basename + "-input").value = ""
    })
}

function fireListeners(count)
{
	index = 0;
	autoCompleteId = 'investigator' + index ;
	for (i=0;i<count;i++)
	{
	 // change the basename property to agent0 ,agent1 ...
	 investigatorAutocompleterProps.basename=autoCompleteId
	 acCreate(investigatorAutocompleterProps)
	 index++
	 autoCompleteId= 'investigator' + index  ;
	}

	investigatorAutocompleterProps.basename='investigator' + count ;
	acCreate(investigatorAutocompleterProps);
}

Event.observe(window, "load", function() {
	index = 0;
	autoCompleteId = 'investigator' + index ;
	while( $(autoCompleteId)  )
	{
	 // change the basename property to investigator0 ,investigator1 ...
	 investigatorAutocompleterProps.basename=autoCompleteId
	 acCreate(investigatorAutocompleterProps)
	 index++
	 autoCompleteId= 'investigator' + index  ;
	}
})

</script>
</head>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form method="post" name="form">
<table border="0" id="table1" cellspacing="10" width="100%">
	<tr>
	<td valign="top" >
	<tabs:division id="study-details" title="Study Investigators">
	<tabs:tabFields tab="${tab}"/>
	<div>
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selectedSite" value="">
		<input type="hidden" name="_selectedInvestigator" value="">
	</div>
	<p id="instructions">
		Choose a study site first
	</p>

	<c:set var="selected_site" value="0"/>
	<c:if test="${not empty selectedSite}">
		<c:set var="selected_site" value="${selectedSite}"/>
	</c:if>

	<table border="0" id="table1" cellspacing="0">
	   <tr>
			<td align="left"> <b> <span class="red">*</span><em></em>Site:</b> </td>
			<td align="left">
				<select id="site" name="site" onchange="javascript:chooseSites();">
					<c:forEach  items="${command.studySites}" var="studySite" varStatus="status">
						<c:if test="${selected_site == status.index }">
							<option selected="true" value=${status.index}>${studySite.site.name}</option>
						</c:if>
						<c:if test="${selected_site != status.index }">
							<option value=${status.index}>${studySite.site.name}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
	   </tr>
	</table>
	<br>
	<hr>
	<p id="instructions">
		Add investigators <a href="javascript:fireAction('addInv',${selected_site}, '0');"><img
			src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add Investigators"></a>
	</p>
	<table border="0" id="mytable" cellspacing="0">
		<tr>
			<th scope="col" align="left"><b> <span class="red">*</span><em></em>Investigator:</b></th>
			<th scope="col" align="left"><b> <span class="red">*</span><em></em>Role:</b> </th>
			<th scope="col" align="left"><b> <span class="red">*</span><em></em>Status:</b> </th>
			<th scope="col" class="specalt" align="left"><b></b></th>
		</tr>

		<c:forEach varStatus="status" items="${command.studySites[selected_site].studyInvestigators}">
			<tr>
				<td class="alt">
					<form:hidden id="investigator${status.index}" path="studySites[${selected_site}].studyInvestigators[${status.index}].healthcareSiteInvestigator"/>
					<input class="validate-notEmpty" type="text" id="investigator${status.index}-input" size="30" value="${command.studySites[selected_site].studyInvestigators[status.index].healthcareSiteInvestigator.investigator.fullName}"/>
					<input type="button" id="investigator${status.index}-clear" value="Clear"/>
					<tags:indicator id="investigator${status.index}-indicator"/>
					<div id="investigator${status.index}-choices" class="autocomplete"></div></td>
				<td class="alt">
					<form:select path="studySites[${selected_site}].studyInvestigators[${status.index}].roleCode" cssClass="validate-notEmpty">
						<option value="">--Please Select--</option>
						<form:options items="${studyInvestigatorRoleRefData}" itemLabel="desc" itemValue="desc"/>
					</form:select></td>
				<td class="alt">
					<form:select path="studySites[${selected_site}].studyInvestigators[${status.index}].statusCode" cssClass="validate-notEmpty">
						<option value="">--Please Select--</option>
						<form:options items="${studyInvestigatorStatusRefData}" itemLabel="desc" itemValue="desc" />
					</form:select></td>
				<td class="alt">
					<a href="javascript:fireAction('removeInv', ${selected_site}, ${status.index});"><img
						src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a></td>
			</tr>
		</c:forEach>
		
	</table>
  	</tabs:division>
	</td>
    <td valign="top">
  	<tabs:division id="Summary" title="Investigators Summary">
	  	<font size="2"><b> Study Sites </b> </font>
	  	<br><br>
	  	<table border="0" id="table1" cellspacing="0" cellpadding="0" width="100%">
	  	<c:forEach var="studySite" varStatus="status" items="${command.studySites}">
	  		<tr>
	  			<td>
	  				<a onclick="javascript:chooseSitesFromSummary(${status.index});" title="click here to edit investigator assigned to study"> <font size="2"> <b> ${studySite.site.name} </b> </font> </a>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				Investigators Assigned: <b> ${fn:length(studySite.studyInvestigators)} </b>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td>
	  				<br>
	  			</td>
	  		</tr>
	  	</c:forEach>
	  	<c:forEach begin="1" end="12">
	  	<tr>
	  		<td>
	  			<br>
	  		</td>
	  	</tr>
	  	</c:forEach>
	  	</table>
	  	</tabs:division>
	</td>
  </tr>
</table>
</form:form>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>