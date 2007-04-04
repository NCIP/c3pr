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

function fireAction(action, selected){
	document.getElementsByName('_target4')[0].name='_target3';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form.submit();

}

function chooseSites(){
	document.getElementsByName('_target4')[0].name='_target3';
	document.form._action.value="siteChange";
	document.form._selected.value=document.getElementById('site').value;
	document.form.submit();
}

function chooseSitesfromSummary(selected){
	document.getElementsByName('_target4')[0].name='_target3';
	document.form._action.value="siteChange";
	document.form._selected.value=selected;
	document.form.submit();
}

function fireAction1(action, selected, studysiteindex){
	document.getElementsByName('_target4')[0].name='_target3';
	document.form._action.value=action;
	document.form._selected.value=selected;
	document.form._studysiteindex.value=studysiteindex;
	document.form.submit();
	fireListeners(selected);

}

function dothis()
{
	alert(document.getElementById('investigator0').value);
	alert(document.getElementById('investigator1').value);
	alert(document.getElementById('investigator2').value);
}

/// AJAX

var investigatorAutocompleterProps = {
	basename: "investigator",
    populator: function(autocompleter, text) {
    createStudy.matchSiteInvestigators(text,document.getElementById('site').value, function(values) {
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
	//Element.update("flow-next", "Continue &raquo;")
})

</script>
</head>

<!-- MAIN BODY STARTS HERE -->
<body>
<form:form method="post" name="form">
<table border="0" id="table1" cellspacing="10" width="100%">
	<tr>
	<td valign="top" width="70%" >
	<tabs:division id="study-details" title="Study Investigators">
	<tabs:tabFields tab="${tab}"/>
	<div>
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selected" value="">
		<input type="hidden" name="_studysiteindex" value="">
	</div>
	<p id="instructions">
		Please choose a study site and link investigators to that study site
	</p>

		<c:set var="selectedSite" value="0"/>
		<c:if test="${not empty site_id}">
			<c:set var="selectedSite" value="${site_id}"/>
		</c:if>

		<table border="0" id="table1" cellspacing="10" width="30%">

		   <tr>
				<td align="left"> <b> <span class="red">*</span><em></em>Site:</b> </td>
				<td align="left">
					<select id="site" name="site" onchange="javascript:chooseSites();">
						<c:forEach  items="${command.studySites}" var="studySite" varStatus="status">
							<c:if test="${selectedSite == status.index }">
								<option selected="true" value=${status.index}>${studySite.site.name}</option>
							</c:if>
							<c:if test="${selectedSite != status.index }">
								<option value=${status.index}>${studySite.site.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
		   </tr>
		</table>

		<c:set var="index" value="0"/>
		<c:if test="${!empty site_id}">
			<c:set var="index" value="${site_id}"/>
		</c:if>

		<table border="0" id="table1" cellspacing="10" width="100%">
			<tr>
				<td align="left"> <b> <span class="red">*</span><em></em>Investigator:</b> </td>
				<td align="left"> <b> <span class="red">*</span><em></em>Role:</b> </td>
				<td align="left"> <b> <span class="red">*</span><em></em>Status:</b> </td>
				<td align="left">
					<b><a href="javascript:fireAction1('addInv','0', ${index});"><img
						src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a></b>
				</td>
			</tr>

			<c:forEach varStatus="status" items="${command.studySites[index].studyInvestigators}">
				<tr>
					<td align="left" width="50%">
						<form:hidden id="investigator${status.index}" path="studySites[${index}].studyInvestigators[${status.index}].healthcareSiteInvestigator"/>
						<input type="text" id="investigator${status.index}-input" size="30" value="${command.studySites[index].studyInvestigators[status.index].healthcareSiteInvestigator.investigator.fullName}"/>
						<input type="button" id="investigator${status.index}-clear" value="Clear"/>
						<tags:indicator id="investigator${status.index}-indicator"/>
						<div id="investigator${status.index}-choices" class="autocomplete"></div>
					</td>
					<td width="20%">
						<form:select path="studySites[${index}].studyInvestigators[${status.index}].roleCode">
							<option value="">--Please Select--
							<form:options items="${studyInvestigatorRoleRefData}" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td align="center" width="23%">
						<form:select path="studySites[${index}].studyInvestigators[${status.index}].statusCode">
							<option value="">--Please Select--
							<form:options items="${studyInvestigatorStatusRefData}" itemLabel="desc" itemValue="desc" />
						</form:select>
					</td>

					<td align="center" width="20%">
						<a href="javascript:fireAction1('removeInv',${status.index}, ${index});"><img
							src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="delete"></a>
					</td>
				</tr>
			</c:forEach>
			<tr>
			<td>
			<p id="investigator-selected" style="display: none">
				You've selected the participant <span id="investigator-selected-name"></span>.
			</p>
			</td>
			</tr>
		</table>
	  </tabs:division>
	  </td>
	   <td valign="top" width="30%">
	  	<tabs:division id="Summary" title="Investigators Summary">
	  	<font size="2"><b> Study Sites </b> </font>
	  	<br><br>
	  	<table border="0" id="table1" cellspacing="0" cellpadding="0" width="100%">
	  	<c:forEach var="studySite" varStatus="status" items="${command.studySites}">
	  		<tr>
	  			<td>
	  				<a onclick="javascript:chooseSitesfromSummary(${status.index});" title="click here to edit investigator assigned to study"> <font size="2"> <b> ${studySite.site.name} </b> </font> </a>
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