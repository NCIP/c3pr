<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Manage Registration</title>
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
    populator: function(autocompleter, text) {
    if (document.getElementById("select").value=="Study"){if(document.getElementById("studyOption").value=='id') {
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
    submitPage();
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
})
function updateAction(action){
		document.getElementById("_updateaction").value=action;
		document.getElementById("form1").submit();
}
function manageSelectBox(box){
	if(box.value=='Subject'){
		document.getElementById('StudySearch').style.display='none';
		document.getElementById('SubjectSearch').style.display='';
	}else if(box.value=='Study'){
		document.getElementById('StudySearch').style.display='';
		document.getElementById('SubjectSearch').style.display='none';	
	}else{
		document.getElementById('StudySearch').style.display='none';
		document.getElementById('SubjectSearch').style.display='none';	
	}
}

function clearSearchCriteria(){
	$("registration-selected").hide()
	$("registration-input").value = ""
	$("registration-hidden").value = ""
}

function submitPage(){
	//actvate the processing gif
	document.getElementById("formSubmit-indicator").style.display="";
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
<tags:instructions code="registration_search" />
	<form:form id="searchForm" name="searchForm" method="post">
	
	<div>
		<input type="hidden" name="selected-id" id="selected-id" value=""/>
	</div>

		<table border="0" id="table1" cellspacing="0" cellpadding="0"
			width="100%">
			<td>
			<table border="0">
				<tr>
					<td width="20%" valign="top" align="right">Search By:</td>
					<td align="right" width="15%">
                        <form:select id="select" path="select" onchange="clearSearchCriteria();manageSelectBox(this);">
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
					<td width="20%" valign="top" align="right"><span class="label">Search Criteria:</span>&nbsp;
					</td>
					<td colspan="2"><input type="hidden" id="registration-hidden" />
                        <form:input id="registration-input" path="searchText" cssClass="autocomplete" size="52"/>
                        <img id="formSubmit-indicator" src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator" style="display:none"/>
                        <tags:indicator id="registration-indicator" />
					<div id="registration-choices" class="autocomplete" style="display: none;"></div>
					<p id="registration-selected" style="display: none">You've selected<span id="registration-selected-name"></span>.</p>
					</td>
				</tr>
				<tr>
					<td width="20%"></td>
					<td></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<td>
					<div id="SubjectSearchMessage" style="display:none;">
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
