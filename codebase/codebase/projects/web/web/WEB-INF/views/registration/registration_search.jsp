<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Search Registration</title>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>

<jwr:script src="/js/tabbedflow.js" />
<tags:dwrJavascriptLink objects="registrationDetails" />

<script>
/// AJAX
function getCriteriaNumber(){
	if(document.getElementById("select").value=='Subject')
		return subjectCriteriaNumber();
	else if(document.getElementById("select").value=='Study')
		{
		return studyCriteriaNumber();
		}
	else if(document.getElementById("select").value=='Id')
		return 5;
	
}
function subjectCriteriaNumber(){
	if(document.getElementById("subjectOption").value=='N'){
			return 0
		}else if(document.getElementById("subjectOption").value=='Identifier'){
					return 1 
				}
		 else if(document.getElementById("subjectOption").value=='F'){
					return 6
				}
	}
	
function studyCriteriaNumber(){

	if(document.getElementById("studyOption").value=='shortTitle')
		return 2
	else if(document.getElementById("studyOption").value=='longTitle')
		return 3
	else if(document.getElementById("studyOption").value=='status')
	{return 5}
	else if(document.getElementById("studyOption").value=='id')
	{
	return 4
	}
	}
	
var registrationAutocompleterProps = {
    basename: "registration",
    isFreeTextAllowed: true,
    populator: function(autocompleter, text) {if (document.getElementById("select").value=="Study"){if(document.getElementById("studyOption").value=='id') {
	(registrationDetails.matchStudyIdentifiers(text,getCriteriaNumber(),function(values) {
	    autocompleter.setChoices(values)
	}))
    } else {
	(registrationDetails.matchStudies(text,getCriteriaNumber(),function(values) {
	    autocompleter.setChoices(values)
	}))
    }}else if (document.getElementById("select").value=="Subject"){if(document.getElementById("subjectOption").value=='Identifier') {
	(registrationDetails.matchParticipantIdentifiers(text,getCriteriaNumber(),function(values) {
	    autocompleter.setChoices(values)
	}))
    } else {
	(registrationDetails.matchParticipants(text,getCriteriaNumber(),function(values) {
	    autocompleter.setChoices(values)
	}))
    }}else {
	(registrationDetails.matchRegistrationIdentifiers(text,getCriteriaNumber(),function(values) {
	    autocompleter.setChoices(values)
	}))
    }},
    valueSelector: function(obj) {
    if(document.getElementById("select").value=='Subject')
    { 
    	if(document.getElementById("subjectOption").value=='N'|| document.getElementById("subjectOption").value=='F')
    	
		return (obj.lastName +", "+ ((obj.middleName==null||obj.middleName=='')?'':(obj.middleName+", "))+obj.firstName)
		
		else if(document.getElementById("subjectOption").value=='Identifier'){
		return obj.value}
   }else if (document.getElementById("select").value=='Study')
    {
    	if (document.getElementById("studyOption").value=='shortTitle')
    	return obj.shortTitleText
    	else if (document.getElementById("studyOption").value=='longTitle')
    	return obj.longTitleText
    	else if (document.getElementById("studyOption").value=='status')
    	return obj.status
    	else return obj.value
    	
    }else if (document.getElementById("select").value=='Id') 
    {
     return obj.value
    }
    
    }
}

function acPostSelect(mode, selectedChoice) {
    Element.update(mode.basename + "-selected-name", mode.valueSelector(selectedChoice))
    $(mode.basename + "-hidden").value = selectedChoice.id;
    $(mode.basename + '-selected').show()
    new Effect.Highlight(mode.basename + "-selected")
}

function updateSelectedDisplay(mode) {
    if ($(mode.basename + "-hidden").value) {
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
	$(mode.basename + "-input").value = ""
	$(mode.basename + "-hidden").value = ""
	
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
		Effect.SlideDown('SubjectSearch');
		manageSearchTypeMessage('Subject');
	}else if(box.value=='Study'){
		Effect.SlideDown('StudySearch');
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
		Effect.SlideDown('SubjectSearchMessage');
	}else if(message=='Study'){
		document.getElementById('SubjectSearchMessage').style.display='none';
		document.getElementById('IdentifierSearchMessage').style.display='none';
		Effect.SlideDown('StudySearchMessage');
	}else if(message=='Identifier'){
		document.getElementById('SubjectSearchMessage').style.display='none';
		document.getElementById('StudySearchMessage').style.display='none';	
		Effect.SlideDown('IdentifierSearchMessage');
	}
}

function submitPage(){
	document.getElementById("selected-id").value =  document.getElementById("registration-hidden").value;
	document.getElementById("searchForm").submit();
}

// after the POST, comboboxes lose their visibility states which is set on client side only,
// that's why this method should be called everytime after the page loads. 
Event.observe(window, "load", function() {
    manageSelectBox($('select'));    
})

</script>
</head>
<body>
<chrome:search title="Search">
	<form:form id="searchForm" name="searchForm" method="post">
	
	<div>
		<input type="hidden" name="selected-id" id="selected-id" value=""/>
	</div>

		<table border="0" id="table1" cellspacing="0" cellpadding="0"
			width="100%">
			<td>
			<table border="0">
				<tr>
					<td width="20%" valign="top" align="right"><b>Search By:</b></td>
					<td align="right" width="15%">
                        <form:select id="select" path="select" onchange="manageSelectBox(this);">
                            <form:option value="Subject" label="Subject" />
                            <form:option value="Study" label="Study" />
                            <form:option value="Id" label="Registration Identifier" />
                        </form:select>
                       </td>

					<td align="left">
                        <div name="SubjectSearch" id="SubjectSearch">
                            <form:select path="subjectOption" id="subjectOption">
                            <c:forEach items="${searchTypeRefDataPrt}" var="option">
                                <form:option value="${option.code}" label="${option.desc}" />
                            </c:forEach>
                            </form:select>
                        </div>

                        <div name="StudySearch" id="StudySearch" style="display:none;">
                            <form:select path="studyOption" id="studyOption">
                            <c:forEach items="${searchTypeRefDataStudy}" var="option">
                                <form:option value="${option.code }" label="${option.desc}" />
                            </c:forEach>
                            </form:select>
                        </div>
					</td>
				</tr>

				<tr>
					<td width="20%" valign="top" align="right"><span class="label"><b>Search Criteria:</b></span>&nbsp;</td>
					<td colspan="2"><input type="hidden" id="registration-hidden" />
                        <form:input id="registration-input" path="searchText" cssClass="autocomplete" size="52"/>
                        <tags:indicator id="registration-indicator" />
					<div id="registration-choices" class="autocomplete"></div>
					<p id="registration-selected" style="display: none">You've selected
					<span id="registration-selected-name"></span>.</p>
					</td>
				</tr>
				<tr>
					<td width="20%"></td>
					<td><input type="button" value="Search" onClick="submitPage();return false;"> <input type="button"
						id="registration-clear" value="Clear" /></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<td>
					<div id="SubjectSearchMessage">
					<p id="instructions">Search for Subjects</p>
					</div>
					<div id="StudySearchMessage" style="display:none;">
					<p id="instructions">Search for Studies</p>
					</div>
					<div id="IdentifierSearchMessage" style="display:none;">
					<p id="instructions">Search for Registrations</p>
					</div>
					</td>
				</tr>

			</table>
			<tr>
				<td align="left"><c:if
					test="${registrations!=null && fn:length(registrations)==0}">
											Sorry, no matches were found				
											</c:if></td>
			</tr>
		</table>
	</form:form>
	<registrationTags:searchResults registrations="${registrations }" />

	<!-- MAIN BODY ENDS HERE -->
</chrome:search>
</body>
</html>
