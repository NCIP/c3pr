<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@ taglib prefix="registrationTags" tagdir="/WEB-INF/tags/registration"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
<tags:stylesheetLink name="tabbedflow" />
<tags:javascriptLink name="tabbedflow" />
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="registrationDetails" />

<script>
/// AJAX
function getCriteriaNumber(){
	if(document.getElementById("select").value=='Subject')
		return subjectCriteriaNumber();
	else if(document.getElementById("select").value=='Study')
		return studyCriteriaNumber();
	else if(document.getElementById("select").value=='Id')
		return 5;
	
}
function subjectCriteriaNumber(){
	if(document.getElementById("SubjectOption").value=='N')
		return 0
	else if(document.getElementById("SubjectOption").value=='Identifier')
		return 1
	}
	
function studyCriteriaNumber(){
	if(document.getElementById("StudyOption").value=='shortTitle')
		return 2
	else if(document.getElementById("StudyOption").value=='longTitle')
		return 3
	else if(document.getElementById("StudyOption").value=='status')
	{return 4}
	else return 2
	}
	



var registrationAutocompleterProps = {
    basename: "registration",
    populator: function(autocompleter, text) {
	registrationDetails.matchRegistrations(text,getCriteriaNumber(),function(values) {
	    autocompleter.setChoices(values)
	})
    },
    valueSelector: function(obj) {
    if(document.getElementById("select").value=='Subject')
    { 
    	return obj.participant.lastName;
    
    }else if (document.getElementById("select").value=='Study')
    {
    	if (document.getElementById("StudyOption").value=='shortTitle')
    	return obj.studySite.study.shortTitleText
    	else if (document.getElementById("StudyOption").value=='longTitle')
    	return obj.studySite.study.longTitleText
    	else if (document.getElementById("StudyOption").value=='status')
    	return obj.studySite.study.status
    	else return obj.studySite.study.shortTitleText
    	
    }else if (document.getElementById("select").value=='Id') 
    {
     return obj.studySite.study.status
    }
    
    }
}

function acPostSelect(mode, selectedChoice) {
    Element.update(mode.basename + "-selected-name", mode.valueSelector(selectedChoice))
    $(mode.basename).value = selectedChoice.id;
    $(mode.basename + '-selected').show()
    new Effect.Highlight(mode.basename + "-selected")
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

Event.observe(window, "load", function() {
    acCreate(registrationAutocompleterProps)
    updateSelectedDisplay(registrationAutocompleterProps)
   // Element.update("flow-next", "Continue &raquo;")
})
function updateAction(action){
		document.getElementById("_updateaction").value=action;
		document.getElementById("form1").submit();
}
function manageSelectBox(box){
	if(box.value=='Subject'){
		document.getElementById('StudySearch').style.display='none';
		Effect.OpenUp('SubjectSearch');
		manageSearchTypeMessage('Subject');
	}else if(box.value=='Study'){
		Effect.OpenUp('StudySearch');
		document.getElementById('SubjectSearch').style.display='none';	
		manageSearchTypeMessage('Study');	
	}else{
		document.getElementById('StudySearch').style.display='none';
		document.getElementById('SubjectSearch').style.display='none';	
		manageSearchTypeMessage('Identifier');	
	}
}
function manageSearchTypeMessage(message){
	if(message=='Subject'){
		document.getElementById('StudySearchMessage').style.display='none';
		document.getElementById('IdentifierSearchMessage').style.display='none';
		Effect.OpenUp('SubjectSearchMessage');
	}else if(message=='Study'){
		document.getElementById('SubjectSearchMessage').style.display='none';
		document.getElementById('IdentifierSearchMessage').style.display='none';
		Effect.OpenUp('StudySearchMessage');
	}else if(message=='Identifier'){
		document.getElementById('SubjectSearchMessage').style.display='none';
		document.getElementById('StudySearchMessage').style.display='none';	
		Effect.OpenUp('IdentifierSearchMessage');
	}
}


</script>
</head>
<body>
<tabs:body title="">
	<form:form id="searchForm" name="searchForm" method="post">
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
			<tr>
				<td valign="top"><tabs:division id="registration-search">
					<table border="0" id="table1" cellspacing="0" cellpadding="5"
						width="100%">
						<td width="15% valign="top"><b>Search for Registration by:</b></td>
						<td>

						<table border="0">
							<tr>
								<td align="right" width="15%"><select name="select" id="select"
									onChange="manageSelectBox(this);">
									<option selected>--Please Select--</option>
									<option value="Subject">Subject</option>
									<option value="Study">Study</option>
									<option value="Id">Registration Identifier</option>
								</select></td>

								<td align="left">
								<div name="SubjectSearch" id="SubjectSearch" style="display:none;"><select
									id="SubjectOption">
									<option selected>--Please Select--</option>
									<c:forEach items="${searchTypeRefDataPrt}" var="option">
										<option value="${option.code }">${option.desc }</option>
									</c:forEach>
								</select></div>
								<div name="StudySearch" id="StudySearch" style="display:none;"><select
									id="StudyOption">
									<option selected>--Please Select--</option>
									<c:forEach items="${searchTypeRefDataStudy}" var="option">
										<option value="${option.code }">${option.desc }</option>
									</c:forEach>
								</select></div>
								</td>
							</tr>

							<tr>
								<td colspan="2"><input type="hidden" id="registration" /> <form:input
									id="registration-input" size="52" path="searchText" /> <tags:indicator
									id="registration-indicator" />
								<div id="registration-choices" class="autocomplete"></div>
								<p id="registration-selected" style="display: none">You've
								selected <span id="registration-selected-name"></span>.</p>
								</td>
							</tr>
							<tr>
								<td><input type="submit" value="Search" /> <input type="button"
									id="registration-clear" value="Clear" /></td>
							</tr>
							<tr>
								<td>
								<div id="SubjectSearchMessage" style="display:none;">
								<p id="instructions">Please search a Subject</p>
								</div>
								<div id="StudySearchMessage" style="display:none;">
								<p id="instructions">Please search a Study</p>
								</div>
								<div id="IdentifierSearchMessage" style="display:none;">
								<p id="instructions">Please search a Registration</p>
								</div>
								</td>
							</tr>

						</table>
						</td>
					</table></td>
			</tr>
		</table>
		<br>
		<br>
		<br>
		<registrationTags:searchResults />

		<!-- MAIN BODY ENDS HERE -->
		</tabs:division>
	</form:form>
</tabs:body>
</body>
</html>
