<%@ include file="taglibs.jsp"%>
<html>
<head>
<title>
    <c:choose>
        <c:when test="${!empty command.personUser.id && command.personUser.id > 0}">
        	<c:out value="Personnel: ${command.personUser.firstName} ${command.personUser.lastName}" />
        </c:when>
        <c:when test="${FLOW == 'SETUP_FLOW'}">
        	Setup User
        </c:when>
        <c:otherwise>Create Personnel</c:otherwise>
    </c:choose>
</title>
<style type= "text/css">
	div.newLabel  {
		float:left;
		margin-left:0em;
		text-align:left;
		width:1em;
	}
	
	div.newValue  {
		font-weight:bold;
		margin-left:2em;
	}
	
	.division.big h3 {
		font-size:1.2em;
	}
	
	.division.indented {
		margin-left:4px;
	}
	
	.division.indented h3 {
		font-size:1em;
	}
		
	div.row div.orgLabel {
		float:left;
		font-weight:bold;
		margin-left:0.5em;
		margin-right:0.5em;
		text-align:right;
		width:8em;
	}
	div.row div.orgValue {
		font-weight:normal;
		margin-left:8em;
	}
</style>
<tags:dwrJavascriptLink objects="ResearchStaffAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
var contentWin ;

function displayRemoteResearchStaff(){
	var contentWin = new Window({className:"alphacube", destroyOnClose:true, id:"remoteRS-popup-id", width:550,  height:200, top: 30, left: 300});
	contentWin.setContent( 'display_remote_rs' );
  	contentWin.showCenter(true);
 	popupObserver = {
		onDestroy: function(eventName, win) {
			if (win == contentWin) {
				$('display_remote_rs').style.display='none';
				contentWin = null;
				Windows.removeObserver(this);
			}
		}
	}
 	Windows.addObserver(popupObserver);
}

function displayPreExistingCsmUser(){
	var contentWin = new Window({className:"alphacube", destroyOnClose:true, id:"csmUser-popup-id", width:550,  height:200, top: 30, left: 300});
	contentWin.setContent( 'display_csmUser' );
  	contentWin.showCenter(true);
 	popupObserver = {
		onDestroy: function(eventName, win) {
			if (win == contentWin) {
				$('display_csmUser').style.display='none';
				contentWin = null;
				Windows.removeObserver(this);
			}
		}
	}
 	Windows.addObserver(popupObserver);
}

Event.observe(window, "load", function(){
	if(${fn:length(command.personUser.externalResearchStaff) gt 0}){
		displayRemoteResearchStaff();
	}
	
	if(${!empty command.preExistingUsersAssignedId}){
		displayPreExistingCsmUser();
	}	
});

function submitRemoteRsForSave(){
	var form = document.getElementById('command');
	form._action.value="saveRemoteRStaff";
	form.submit();
}
	
function selectResearchStaff(selectedIndex){
	var form = document.getElementById('command')
	form._selected.value=selectedIndex;
	document.getElementById('save-yes').disabled = false;
}
	
function syncResearchStaff(){
	document.getElementById('command')._action.value="syncResearchStaff";
	document.getElementById('command').submit();
}

function handleUsername(){
	if($('usernameCheckbox').checked){
		if($('email').value==''){
			$('usernameCheckbox').checked=false;
			alert("Please enter a valid email first");
			$('email').focus();
			return;
		}
		$('loginId').value=$('email').value;
		isValid = (validateFields(new Array($('loginId')), false) && trimWhitespace($('loginId').value) != "");
    	ValidationManager.setStateOnLoad($('loginId'), isValid);
    	if(isValid){
    		$('loginId').disabled=true;
    		$('copiedEmailAddress').value=$('loginId').value;
    		$('loginId').name="inValidName";
    		$('copiedEmailAddress').name="userName";
    	}
	}else{
		$('loginId').value='';
		ValidationManager.setInvalidState($('loginId'));
		$('loginId').disabled=false;
		$('loginId').name="userName";
    	$('copiedEmailAddress').name="";
	}
}
	
function copyUsername(){
	if($('usernameCheckbox').checked){
		$('loginId').value=$('email').value;
		isValid = (validateFields(new Array($('loginId')), false) && trimWhitespace($('loginId').value) != "");
    	ValidationManager.setStateOnLoad($('loginId'), isValid);
	}
}

function addForDropDown(index){
	var _fieldHelper = 'healthcareSiteRolesHolderList['+ index + ']';
	sid = $(_fieldHelper + '.selectedSiteForDisplay').selectedIndex;
	if(sid > 0){
		Form.Element.enable($('addSite_btn['+ index +']'));
	} else {
		Form.Element.disable('addSite_btn['+index+']');
	}
}

function addSiteForDropDown(el, index, isSiteScoped, isStudyScoped){
	//get the sitePrimaryIdentifier and display value from the drop down
	ddElement = $(el + '.selectedSiteForDisplay');
	selectedIndex = ddElement.selectedIndex;
	var _selectedChoiceForDisplay = ddElement[selectedIndex].text;
	ddElement.selectedIndex = 0;
	
	Form.Element.disable('addSite_btn['+index+']');
	var _sitePrimaryIdentifier =  _selectedChoiceForDisplay.substring(_selectedChoiceForDisplay.indexOf('(') + 1, _selectedChoiceForDisplay.indexOf(')'));
	var _tableId = el+"-sitesTable";
	var _sitesFldName = el + '.sites';
	var _trId = el + '-site-' + _sitePrimaryIdentifier;
	var _deleteBtn = "<a href=\"javascript:removeSite('" + el + "-site-" + _sitePrimaryIdentifier + "','" +index+ "','"+isSiteScoped+"','"+isStudyScoped+ "');\">"+"<img src=\"<tags:imageUrl name='checkno.gif'/>\"></a>"
	$(_tableId).down('tr').insert({
		after: tableRow.interpolate({selectedChoiceForDisplay:_selectedChoiceForDisplay, identifier : _sitePrimaryIdentifier ,fldName : _sitesFldName, deleteBtn : _deleteBtn, trId : _trId })
		});
	sitesCount[index] = sitesCount[index] + 1;
	
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

var tableRow = "<tr id='#{trId}'><td>&nbsp;&nbsp;&nbsp;#{selectedChoiceForDisplay}<input type='hidden' id='#{fldName}' name='#{fldName}' value='#{identifier}' /></td><td>#{deleteBtn}</td></tr>";
var sitesCount = new Array();
var studiesCount = new Array();

function addSite(el, index, isSiteScoped, isStudyScoped){
	Form.Element.disable('addSite_btn['+index+']');
	var _selectedSiteForDisplay = $("firstHealthcareSite"+index+"-input").value;
	var _sitePrimaryIdentifier = $(el+".primaryIdentifier").value;
	var _tableId = el+"-sitesTable";
	var _sitesFldName = el + '.sites';
	var _trId = el + '-site-' + _sitePrimaryIdentifier;
	var _deleteBtn = "<a href=\"javascript:removeSite('" + el + "-site-" + _sitePrimaryIdentifier + "','" +index+ "','"+isSiteScoped+"','"+isStudyScoped+ "');\">"+"<img src=\"<tags:imageUrl name='checkno.gif'/>\"></a>"
	$(_tableId).down('tr').insert({
		after: tableRow.interpolate({selectedChoiceForDisplay:_selectedSiteForDisplay, identifier : _sitePrimaryIdentifier ,fldName : _sitesFldName, deleteBtn : _deleteBtn, trId : _trId })
		});
	sitesCount[index] = sitesCount[index] + 1;
	
	$("firstHealthcareSite"+index+"-input").value='';
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

function removeSite(el,index, isSiteScoped, isStudyScoped){
	$(el).select('[type="hidden"]')[0].remove();
	$(el).remove();
	sitesCount[index] = sitesCount[index] - 1;
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

function addStudy(el,index, isSiteScoped, isStudyScoped){
	Form.Element.disable('addStudy_btn['+index+']');
	var _selectedStudyForDisplay = $("Study"+index+"-input").value;	
	var _studyId = $(el+".studyId").value;
	var _tableId = el+"-studiesTable";
	var _studiesFldName = el + '.studies';
	var _trId = el + '-study-' +_studyId;
	var _deleteBtn = "<a href=\"javascript:removeStudy('" + el + "-study-" +_studyId + "','" +index+ "','"+isSiteScoped+"','"+isStudyScoped+"');\">"+"<img src=\"<tags:imageUrl name='checkno.gif'/>\"></a>"

	$(_tableId).down('tr').insert({
		after: tableRow.interpolate({selectedChoiceForDisplay : _selectedStudyForDisplay, identifier:_studyId, fldName : _studiesFldName, deleteBtn : _deleteBtn, trId : _trId })
		});
	studiesCount[index] = studiesCount[index] + 1;
	
	$("Study"+index+"-input").value='';
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

function removeStudy(el,index, isSiteScoped, isStudyScoped){
	$(el).select('[type="hidden"]')[0].value='';
	$(el).remove();
	studiesCount[index] = studiesCount[index] - 1;
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

function updateRoleSummary(index, isSiteScoped, isStudyScoped){
	grantRoleChkbox = $('grantRole'+index)
	if(grantRoleChkbox.checked){
		var nSiteSummary = '';
		var nStudySummary = '';
		var nRoleSummary = '';
		var selectedImg = '&nbsp;&nbsp;<img src="<c:url value="/images/check.png" />" />';
		var eRoleSummary = $('summary-'+index).innerHTML;
		
		if(eRoleSummary.trim().blank()){
			//adding this condition for POMgr case.
			if(isSiteScoped == 'true' && $('allSiteAccess'+index).checked){
				nSiteSummary = 'All Sites';
			}
			$('summary-'+index).innerHTML = nSiteSummary+selectedImg;
		}else{
			if(isSiteScoped == 'true'){
				if($('allSiteAccess'+index).checked){
					nSiteSummary = 'All Sites';
				}else{
					nSiteSummary = 'Sites('+sitesCount[index]+')';
				}
			}
			if(isStudyScoped == 'true'){
				if($('allStudyAccess'+index).checked){
					nStudySummary = ' | All Studies';
				}else{
					nStudySummary = ' | Studies('+studiesCount[index]+')';
				}
			}
			nRoleSummary = nSiteSummary+nStudySummary+selectedImg;
			$('summary-'+index).innerHTML = nRoleSummary;						
		}
		$('summary-'+index).show();
	}else{
		$('summary-'+index).hide();
	}
} 

ValidationManager.submitPostProcess= function(formElement, flag){	
	createAsUser = $('createAsUser');
	createAsStaff = $('createAsStaff');
	assgndId = $('assignedIdInput')
	ValidationManager.removeError(createAsUser);
	if(assgndId != null) {
		ValidationManager.removeError(assgndId);
	}
	if(flag){
		//ensuring the checkboxes are disabled during submit as the controller relies on these values for processing
		//not clean...change in the future.
		if(createAsUser != null && createAsUser.disabled){
			createAsUser.disabled='';
		}
		if(createAsStaff != null && createAsStaff.disabled){
			createAsStaff.disabled='';
		}
		if((createAsStaff == null || createAsStaff.checked == false)
			 && (createAsUser == null || createAsUser.checked == false)){
			ValidationManager.showError(createAsUser, "Select at least one:  Create as Research Staff, Create as User")
			return false;
		}
		
		if(createAsStaff != null && createAsStaff.checked == true){
			if(assgndId != null){
				if(assgndId.value == null || assgndId.value == ''){
					ValidationManager.showError(assgndId, "Enter an Assigned Identifier for the Research Staff")
					return false
				}
			}	
		}
		return flag;
	} else {
		return flag;
	}	
}

//display associated staff organizations section if createAsStaff is checked
function toggleStaffDisplay(){
	createAsStaff = $('createAsStaff');
	orgInfo = $('OrganizationInformation')
	assignedIdentifier = $('assignedIdentifier')
	if(createAsStaff != null && createAsStaff.checked == true){
		new Effect.BlindDown(orgInfo);
		new Effect.BlindDown(assignedIdentifier);
	} else {
		new Effect.BlindUp(orgInfo);
		new Effect.BlindUp(assignedIdentifier);
	}
}

//display user section if createAsUser is checked
function toggleUserDisplay(){
    createAsUser = $('createAsUser');
	ell = $('AccountInformation')
	if(createAsUser != null && createAsUser.checked == true){
		//display user sections
		new Effect.BlindDown(ell);
	} else {
		//hide user sections
		new Effect.BlindUp(ell);
	}
}

//display site auto-cmpltr if all-site is unchecked
function toggleUserOrgAutocompleter(index, isSiteScoped, isStudyScoped){
	el = $('userOrgAutocompleter'+index)
	allSiteChkbox = $('allSiteAccess'+index)
	if(allSiteChkbox.checked == true){
		//display user sections
		new Effect.BlindUp(el);
	} else {
		new Effect.BlindDown(el);
	}
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

//display study auto-cmpltr if all-study is unchecked
function toggleUserStudyAutocompleter(index, isSiteScoped, isStudyScoped){
	el = $('userStudyAutocompleter'+index)
	allStudyChkbox = $('allStudyAccess'+index)
	if(allStudyChkbox.checked == true){
		//display user sections
		new Effect.BlindUp(el);
	} else {
		new Effect.BlindDown(el);
	}
	updateRoleSummary(index, isSiteScoped, isStudyScoped);
}

//display org and role content if grant role is checked
function toggleRoleContent(index, siteScoped, studyScoped){
	grantRoleChkbox = $('grantRole'+index)
	orgContent = $('orgDisplay'+index)
	studyContent = $('studyDisplay'+index)
	if(grantRoleChkbox.checked == true){
		if(siteScoped == "true"){
			new Effect.BlindDown(orgContent);
		}
		if(studyScoped == "true"){
			new Effect.BlindDown(studyContent);
		}
	} else {
		if(siteScoped == "true"){
			new Effect.BlindUp(orgContent);
		}
		if(studyScoped == "true"){
			new Effect.BlindUp(studyContent);
		}
	}
	updateRoleSummary(index, siteScoped, studyScoped);
}
</script>

</head>
<body>
<div id="confirmation-allsiteaccess-msg" style="display: none;">
	<div align="left" style="font-size: 10pt; padding-top: 10px; padding-bottom: 20px; padding-left: 5px; padding-right: 5px">
		<fmt:message key="RESEARCH_STAFF.ALL_SITE_ACCESS_CHECKED"/>
	</div>
	<div align="center" style="padding-top: 20px">
		<tags:button type="button "color="blue" value="OK" onclick="javascript:contentWin.close();"/>
	</div>
</div>
<div id="main">
<c:choose>
	<c:when test="${command.personUser.class.name eq 'edu.duke.cabig.c3pr.domain.RemotePersonUser'}">
		<c:set var="imageStr" value="&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='22' height='21' border='0' align='middle'/>"/>
	</c:when>
	<c:otherwise>
		<c:set var="imageStr" value=""/>
	</c:otherwise>
</c:choose>
<form:form name="researchStaffForm">
<chrome:box title="${FLOW == 'SETUP_FLOW'?'Create Super User':'Basic Details'}" htmlContent="${imageStr}">
	<chrome:flashMessage />
	<tags:tabFields tab="${tab}" />

	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">
	<input type="hidden" name="_preExistingUsersAsignedId" value="">

	<c:set var="noHealthcareSiteAssociated" value="${fn:length(command.personUser.healthcareSites) == 0}"></c:set>
	<tags:instructions code="research_staff_details" />
	<tags:errors path="*"/>
	    <div class="leftpanel">
	        <div class="row">
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.firstName"/></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}" cssClass="required validate-notEmpty" path="personUser.firstName" size="30" value="${command.personUser.firstName}"></tags:researchStaffInput>
	        </div>
			<div class="row">
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.lastName"/></div>
            	<tags:researchStaffInput commandClass="${command.personUser.class}" cssClass="required validate-notEmpty" path="personUser.lastName" size="30" value="${command.personUser.lastName}"></tags:researchStaffInput>
	        </div>
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.middleName"/></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}"  path="personUser.middleName" size="30" value="${command.personUser.middleName}"></tags:researchStaffInput>
	        </div>
			<div class="row">
	            <div class="label"><fmt:message key="c3pr.common.maidenName"/></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}"  path="personUser.maidenName" size="30" value="${command.personUser.maidenName}"></tags:researchStaffInput>
			</div>
	        <div class="row">
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.email" /></div>
				<tags:researchStaffInput id="email" commandClass="${command.personUser.class}" cssClass="required validate-notEmpty&&EMAIL" path="personUser.email" size="30" value="${command.personUser.email}" onkeyup="copyUsername();"></tags:researchStaffInput>
	       	</div>
		</div>
	    <div class="rightpanel">
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.phone" /></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}" cssClass="validate-US_PHONE_NO" path="personUser.phone" size="25" value="${command.personUser.phone}"></tags:researchStaffInput>
	        </div>
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.fax" /></div>
	             <tags:researchStaffInput commandClass="${command.personUser.class}" cssClass="validate-US_PHONE_NO" path="personUser.fax" size="25" value="${command.personUser.fax}"></tags:researchStaffInput>
	        </div>

			<c:choose>
				<c:when  test="${FLOW == 'SETUP_FLOW'}">
		        	<div class="row" >
		   	        	<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.person.identifier"/></div>
			        	<tags:researchStaffInput id="assignedIdInput" commandClass="${command.personUser.class}" path="personUser.assignedIdentifier" cssClass="required validate-notEmpty"
			        				size="25" value="${command.personUser.assignedIdentifier}"></tags:researchStaffInput>    
			    	</div>
			    	<div class="row">
		            	<div class="label"><fmt:message key="researchstaff.createAsStaff" /></div>
		            	<form:checkbox id="createAsStaff" path="createAsStaff" disabled="true"/>
		        	</div>
		        	<div class="row" >
		            	<div class="label"><fmt:message key="researchstaff.createAsUser" /></div>
		            	<form:checkbox id="createAsUser" path="createAsUser"  disabled="true"/>
		        	</div>
				</c:when>
				<c:otherwise>
					<c:set var="staffdisplay" value="display:none"/>
			        <c3pr:checkprivilege hasPrivileges="UI_RESEARCHSTAFF_CREATE">
			        	<c:set var="staffdisplay" value="display"/>
			        </c3pr:checkprivilege>
			        <div class="row" style="${staffdisplay}" id="assignedIdentifier">
				   	        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.person.identifier"/></div>
					        <tags:researchStaffInput id="assignedIdInput" commandClass="${command.personUser.class}" path="personUser.assignedIdentifier" size="25" value="${command.personUser.assignedIdentifier}"></tags:researchStaffInput>    
					    </div>
				    
		        	 <div class="row" style="${staffdisplay}">
			            <div class="label"><fmt:message key="researchstaff.createAsStaff" /></div>
			            <c:if test="${FLOW == 'SAVE_FLOW'}">
			            	<form:checkbox id="createAsStaff" path="createAsStaff" onchange="toggleStaffDisplay()"/>
			            </c:if>
			            <c:if test="${FLOW == 'EDIT_FLOW'}">
			            	<input type="checkbox" id="createAsStaff" name="createAsStaff" onchange="toggleStaffDisplay()" value="true"  <c:if test="${command.isStaff == 'true' || command.createAsStaff}">disabled="disabled" checked</c:if> />
			            </c:if>
			        </div>
			        
			        <c:set var="userdisplay" value="display:none"/>
			        <c3pr:checkprivilege hasPrivileges="USER_CREATE">
			        	<c:set var="userdisplay" value="display"/>
			        </c3pr:checkprivilege>
			        <div class="row" style="${userdisplay}">
			            <div class="label"><fmt:message key="researchstaff.createAsUser" /></div>
			            <c:if test="${FLOW == 'SAVE_FLOW'}">
			            	<form:checkbox id="createAsUser" path="createAsUser" onchange="toggleUserDisplay()" />
			            </c:if>
			            <c:if test="${FLOW == 'EDIT_FLOW'}">
			            	<input type="checkbox" id="createAsUser" name="createAsUser" onchange="toggleUserDisplay()" value="true"  <c:if test="${command.isUser == 'true' || command.createAsUser}">disabled="disabled" checked</c:if> />
			            </c:if>
			        </div>
				</c:otherwise>
			</c:choose>
	    </div>
	    <div class="division"></div>
</chrome:box>

<c:set var="allowOrganizationDisplay" value="false" />
<c3pr:checkprivilege hasPrivileges="UI_RESEARCHSTAFF_CREATE">
	<c:set var="allowOrganizationDisplay" value="true" />
</c3pr:checkprivilege>
<c:if test="${FLOW == 'SETUP_FLOW'}">
	<c:set var="allowOrganizationDisplay" value="true" />
</c:if> 

<div id="OrganizationInformation">
	<c:if test="${allowOrganizationDisplay == 'true'}">
		<chrome:box title="Associated Organizations" id="organization_details">
			<chrome:flashMessage />
			<tags:instructions code="research_staff_associated_organizations" />
			<div class="row">
				<div class="orgLabel">
					<tags:requiredIndicator /><fmt:message key="c3pr.common.organization"></fmt:message>
				</div>
				<div class="orgValue">
					<c:choose>
	 					<c:when test="${c3pr:hasAllSiteAccess('UI_PERSONUSER_CREATE') || c3pr:hasAllSiteAccess('UI_RESEARCHSTAFF_CREATE') || FLOW == 'SETUP_FLOW'}">
							<tags:autocompleter name="selectedOrganizationForDisplay" size="40" displayValue="${command.selectedOrganizationForDisplay}" 
									value="${command.selectedOrganizationForDisplay}" basename="organization" ></tags:autocompleter>		
							<script>
	 							var organizationAutocompleterProps = {
	 							    basename: "organization",
	 							    populator: function(autocompleter, text) {
	 										ResearchStaffAjaxFacade.matchHealthcareSites(text,function(values) {
	 							            autocompleter.setChoices(values)
	 							        })
	 							    },
	 							    valueSelector: function(obj) {
	 							    	if(obj.externalId != null){
	 							    		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="NCI" width="17" height="16" border="0" align="middle"/>';
	 							    	} else {
	 							    		image = '';
	 							    	}
	 							    	return (obj.name+" ("+obj.primaryIdentifier+")" + image)
	 							    },
	 							    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
	 							    	var _fieldHelper = 'organization';
	 							    	inputElement.value = " ("+selectedChoice.primaryIdentifier+") "	+ selectedChoice.name ;
	 									$('primaryIdentifier').value = selectedChoice.primaryIdentifier;
	 									Form.Element.enable($('addSite_btn'));
	 									
	 									hiddenField=inputElement.id.split("-")[0]+"-hidden";
	 									$(hiddenField).value=selectedChoice.id;
	 								}
	 							}
	 							AutocompleterManager.addAutocompleter(organizationAutocompleterProps);

	 							var orgTableRow = "<tr id='#{trId}'><td>&nbsp;&nbsp;&nbsp;#{selectedChoiceForDisplay}<input type='hidden' id='#{fldName}' name='#{fldName}' value='#{identifier}' /></td><td>#{deleteBtn}</td></tr>";
	 							function addSiteForStaff(){
	 								Form.Element.disable('addSite_btn');
	 								var _selectedSiteForDisplay = $("organization-input").value;
	 								var _sitePrimaryIdentifier = $("primaryIdentifier").value;
	 								var _tableId = "organization-sitesTable";
	 								var _sitesFldName = 'staffOrganizationPrimaryIdentifiers';
	 								var _trId = 'organization-site-' + _sitePrimaryIdentifier;
	 								var _deleteBtn = "<a href=\"javascript:removeSiteForStaff('" + "organization-site-" + _sitePrimaryIdentifier + "');\">"+"<img src=\"<tags:imageUrl name='checkno.gif'/>\"></a>"
	 								$(_tableId).down('tr').insert({
	 									after: orgTableRow.interpolate({selectedChoiceForDisplay:_selectedSiteForDisplay, identifier : _sitePrimaryIdentifier ,fldName : _sitesFldName, deleteBtn : _deleteBtn, trId : _trId })
	 									});
	 								
	 								$("organization-input").value='';
	 							}
	 							function removeSiteForStaff(el){
	 								$(el).remove();
	 							}
	 						</script>												
						</c:when>
						<c:otherwise><!-- This section is probably redundant as only POMgr can create staff and they have all site access in C3PR -->
							
						</c:otherwise>
					</c:choose>
					<form:hidden path="primaryIdentifier" id="primaryIdentifier"/>
					<tags:button id="addSite_btn" type="button" color="blue" value="Add" icon="add" onclick="addSiteForStaff()" size="small" disabled="true"/>
				</div>
				<div class="orgValue">
				<table id="organization-sitesTable">
					<tbody>
						<tr style="display:none;">
							<td> </td>
							<td> </td>
						</tr>
						<c:forEach var="site" items="${command.personUser.healthcareSites}" varStatus="siteIndex">
							<tr id="organization-site-${siteIndex.index}">
								<td>&nbsp;&nbsp;&nbsp;&nbsp;(${site.primaryIdentifier})&nbsp;${site.name}</td>
								<td><a href="javascript:removeSiteForStaff('organization-site-${siteIndex.index}');"></a></td>
							</tr>	
						</c:forEach>
					</tbody>	
				</table>
			</div>
			</div>
		</chrome:box>
	</c:if>
</div>	

<c:if test="${FLOW=='SETUP_FLOW'}">
	<chrome:box title="Account Information" >
		<tags:instructions code="research_staff_account_information" />
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.username"/></div>
	        <div class="value">
	        	<c:choose>
	        		<c:when test="${!empty errorPassword}">
	        			${command.userName}
	        		</c:when>
	        		<c:otherwise>
	        			<form:input id="loginId" size="20" path="userName" cssClass="required validate-notEmpty&&MAXLENGTH100"/><tags:hoverHint keyProp="contactMechanism.username"/>
	        			<input id="usernameCheckbox" name="copyEmailAdress" type="checkbox" onclick="handleUsername();"/> <i><fmt:message key="researchStaff.copyEmailAddress" /></i>
	        			<input id="copiedEmailAddress" type="hidden"/>
	        		</c:otherwise>	
	        	</c:choose>
	        </div>
	    </div>
	    <div class="row">
		  <div class="label"><tags:requiredIndicator /><spring:message code="changepassword.password"/></div>
		  <div class="value">
		    <input type="password" name="password" class="required validate-notEmpty" autocomplete="off"/>
		    <br> 
		    <font color="red" style="font-style: italic;">
		    <spring:message code="changepassword.password.requirement"/></font>
		  </div>
		</div>
		<div class="row">
		  <div class="label"><tags:requiredIndicator /><spring:message code="changepassword.password.confirm"/></div>
		  <div class="value">
		    <input type="password" name="confirmPassword" class="required validate-notEmpty" autocomplete="off"/>
		  </div>
		</div>
		<c:if test="${!empty errorPassword}">
			<input type="hidden" name="errorPassword" value="true"/>
		</c:if>

	
		<div class="row">
	       	<div class="label">
               <fmt:message key="c3pr.common.c3prAdmin"></fmt:message>
	        </div>
	        <div class="value">
	        	<img src="<tags:imageUrl name='check.png'/>" height="15px" width="15px"/>
	        </div>
	   	</div>
   	</chrome:box>
</c:if>

<c3pr:checkprivilege hasPrivileges="USER_CREATE">
	<div id="AccountInformation">
		<chrome:box title="Account Information" >
			<tags:instructions code="research_staff_account_information" />
			<!-- start of username display -->
			<c:choose>
				<c:when test="${isLoggedInUser}">
					<div class="row">
				        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.username"/></div>
				        <div class="value">${command.userName}</div>
				    </div>
				</c:when>
				<c:otherwise>
					<c3pr:checkprivilege hasPrivileges="USER_CREATE">
					<div class="row" id="username_section">
				        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.username"/></div>
				        <div class="value">
				        	<c:choose>
				        	<c:when test="${empty command.userName || duplicateUser}">
					        		<form:input id="loginId" size="20" path="userName" cssClass="MAXLENGTH100"/><tags:hoverHint keyProp="contactMechanism.username"/>
					        		<input id="usernameCheckbox" name="copyEmailAdress" type="checkbox" onclick="handleUsername();"/> <i><fmt:message key="researchStaff.copyEmailAddress" /></i>
					        		<input id="copiedEmailAddress" type="hidden"/>
					        		<input type="hidden" name="_createUser" value="true">
				        	</c:when>
				        	<c:otherwise>${command.userName}</c:otherwise>
				        	</c:choose>
				        </div>
				    </div>
				    </c3pr:checkprivilege>
				</c:otherwise>
			</c:choose>
			<br/>
			<!-- end of username display -->
			
			<c:forEach items="${command.healthcareSiteRolesHolderList}" var="healthcareSiteRolesHolder"  varStatus="status">
	    		<c:set var="noOfSites" value="${fn:length(healthcareSiteRolesHolder.sites)}" />
				<c:set var="noOfStudies" value="${fn:length(healthcareSiteRolesHolder.studies)}" />
				<c:set var="_sitesSummary" value=""/>
				<c:set var="_studiesSummary" value=""/>
				<c:set var="_roleSummary" value=""/>
				<c:if test="${healthcareSiteRolesHolder.group.isScoped && healthcareSiteRolesHolder.checked}">
					<c:choose>
						<c:when test="${healthcareSiteRolesHolder.hasAllSiteAccess}">
							<c:set var="_sitesSummary" value="All Sites"/>
						</c:when>
						<c:otherwise>
							<c:set var="_sitesSummary" value="Sites(${noOfSites})"/>
						</c:otherwise>
					</c:choose>
					<c:if test="${healthcareSiteRolesHolder.group.isStudyScoped}">
						<c:choose>
							<c:when test="${healthcareSiteRolesHolder.hasAllStudyAccess}">
								<c:set var="_studiesSummary" value=" | All Studies"/>
							</c:when>
							<c:otherwise>
								<c:set var="_studiesSummary" value=" | Studies(${noOfStudies})"/>
							</c:otherwise>
						</c:choose>													
					</c:if>
					<c:set var="_roleSummary" value="${_sitesSummary}${_studiesSummary}"/>
				</c:if>
				
				<c:set var="_roleSummaryII" value="${healthcareSiteRolesHolder.checked?\"&nbsp;&nbsp;&nbsp;&nbsp;<tags:imageUrl name='checkno.gif'/>\":\"\"}" />
				<chrome:deletableDivision divTitle="genericTitle-${status.index}" id="genericHealthcareSiteBox-${status.index}" cssClass="indented"
			    	title="${command.healthcareSiteRolesHolderList[status.index].group.displayName}" 
			    	minimize="true" divIdToBeMinimized="hcs-${status.index}" disableDelete="true" onclick="#" 
			    	additionalInfo="${_roleSummary}${ _roleSummaryII}" additionalInfoId="summary-${status.index}" 
			    	additionalImg="${healthcareSiteRolesHolder.checked?'check.png':''}">
				    
				    <div id="hcs-${status.index}" style="display:none">
			    		${command.healthcareSiteRolesHolderList[status.index].group.roleDescription}
			    		<c:set var="isSiteScoped" value="${command.healthcareSiteRolesHolderList[status.index].group.isSiteScoped}"/>
			    		<c:set var="isStudyScoped" value="${command.healthcareSiteRolesHolderList[status.index].group.isStudyScoped}"/>
			    		<div class="row"">
				            <form:checkbox id="grantRole${status.index}" path="healthcareSiteRolesHolderList[${status.index}].checked" 
				            		onchange="toggleRoleContent('${status.index}', '${isSiteScoped}', '${isStudyScoped}')"/>&nbsp;
	 					            <fmt:message key="researchstaff.grantRole" />
				        </div><br/>
				        
			    		<div id="orgDisplay${status.index}" style="${command.healthcareSiteRolesHolderList[status.index].group.isSiteScoped?'':'display:none'}">
					    	<!-- the site and study auto-completer go here  -->
					    	<c3pr:checkprivilege hasPrivileges="UI_PERSONUSER_CREATE">
				    			<div class="row" style="${c3pr:hasAllSiteAccess('UI_PERSONUSER_CREATE') || c3pr:hasAllSiteAccess('USER_CREATE')?'':'display:none'}">
						    		<c:set var="isDisabled" value="${command.healthcareSiteRolesHolderList[status.index].group.code eq 'person_and_organization_information_manager'}"/>
						            <form:checkbox id="allSiteAccess${status.index}" path="healthcareSiteRolesHolderList[${status.index}].hasAllSiteAccess" disabled="${isDisabled}"
						            				onchange="toggleUserOrgAutocompleter('${status.index}', '${isSiteScoped}','${isStudyScoped}')" />&nbsp;
   					            	<fmt:message key="researchStaff.allSiteAccess"/>
					        	</div>
						        
						    	<div class="row" id="userOrgAutocompleter${status.index}">
				 					<div class="orgLabel">
				 						<fmt:message key="c3pr.common.organization"></fmt:message>
					 				</div>
					 				<div class="orgValue">
					 					<c:choose>
						 					<c:when test="${c3pr:hasAllSiteAccess('UI_PERSONUSER_CREATE') || c3pr:hasAllSiteAccess('USER_CREATE')}">
						 						
												<tags:autocompleter name="healthcareSiteRolesHolderList[${status.index}].selectedSiteForDisplay" size="40" displayValue="${command.healthcareSiteRolesHolderList[status.index].selectedSiteForDisplay}" 
														value="${command.healthcareSiteRolesHolderList[0].selectedSiteForDisplay}" basename="firstHealthcareSite${status.index}" ></tags:autocompleter>		
												<script>
						 							var firstHealthcareSite${status.index}AutocompleterProps = {
						 							    basename: "firstHealthcareSite${status.index}",
						 							    populator: function(autocompleter, text) {
						 										ResearchStaffAjaxFacade.matchHealthcareSites(text,function(values) {
						 							            autocompleter.setChoices(values)
						 							        })
						 							    },
						 							    valueSelector: function(obj) {
						 							    	if(obj.externalId != null){
						 							    		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
						 							    	} else {
						 							    		image = '';
						 							    	}
						 							    	return (obj.name+" ("+obj.primaryIdentifier+")" + image)
						 							    },
						 							    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
						 							    	var _fieldHelper = 'healthcareSiteRolesHolderList[' + inputElement.id.substring(19, inputElement.id.indexOf('-')) + ']';
						 							    	inputElement.value = " ("+selectedChoice.primaryIdentifier+") "	+ selectedChoice.name ;
						 									$(_fieldHelper + '.primaryIdentifier').value = selectedChoice.primaryIdentifier;
						 									Form.Element.enable($('addSite_btn[${status.index}]'));
						 									
						 									hiddenField=inputElement.id.split("-")[0]+"-hidden";
						 									$(hiddenField).value=selectedChoice.id;
						 								}
						 							}
						 							AutocompleterManager.addAutocompleter(firstHealthcareSite${status.index}AutocompleterProps);
						 						</script>
												<tags:button id="addSite_btn[${status.index}]" type="button" color="blue" value="Add" icon="add" onclick="addSite('healthcareSiteRolesHolderList[${status.index}]', '${status.index}', '${isSiteScoped}','${isStudyScoped}')" size="small" disabled="true"/>
											</c:when>
											<c:otherwise>
												<select id="healthcareSiteRolesHolderList[${status.index}].selectedSiteForDisplay" name="healthcareSiteRolesHolderList[${status.index}].selectedSiteForDisplay" 
														onchange="addForDropDown('${status.index}')" style="width: 350px;">
													<tags:userOrgOptions privilege="USER_CREATE" />
												</select>
												<tags:button id="addSite_btn[${status.index}]" type="button" color="blue" value="Add" icon="add" onclick="addSiteForDropDown('healthcareSiteRolesHolderList[${status.index}]', '${status.index}', '${isSiteScoped}','${isStudyScoped}')" size="small" disabled="true"/>
											</c:otherwise>
					 					</c:choose>
										<form:hidden path="healthcareSiteRolesHolderList[${status.index}].primaryIdentifier" id="healthcareSiteRolesHolderList[${status.index}].primaryIdentifier"/>
					 				</div>
					 				<div class="orgValue">
										<script>sitesCount[${status.index}] = ${fn:length(healthcareSiteRolesHolder.sites)};</script>
										<table id="healthcareSiteRolesHolderList[${status.index}]-sitesTable">
											<tbody>
												<tr style="display:none;">
													<td> </td>
													<td> </td>
												</tr>
												<c:forEach var="site" items="${healthcareSiteRolesHolder.sites}" varStatus="siteIndex">
													<c:set var="startIndex" value="${fn:indexOf(site, '(')}" />
													<c:set var="endIndex" value="${fn:indexOf(site, ')')}" />
													<c:set var="sitePrimaryIdentifier" value="${fn:substring(site, startIndex + 1, endIndex)}" />
													<tr id="healthcareSiteRolesHolderList[${status.index}]-site-${siteIndex.index}">
														<td>&nbsp;&nbsp;&nbsp;&nbsp;${site}
															<input type='hidden' id='healthcareSiteRolesHolderList[${status.index}].sites' 
																   name='healthcareSiteRolesHolderList[${status.index}].sites' value='${sitePrimaryIdentifier}' />
														</td>
														<td><a href="javascript:removeSite('healthcareSiteRolesHolderList[${status.index}]-site-${siteIndex.index}','${status.index}', '${isSiteScoped}','${isStudyScoped}');">
															<img src="<tags:imageUrl name="checkno.gif"/>"></a>
														</td>
													</tr>	
												</c:forEach>
											</tbody>	
										</table>
									</div>
				 				</div>
			 				</c3pr:checkprivilege>
				    	</div>
				    	<!-- end of orgDisplay div -->
				    	
				    	<!-- start of studyDisplay -->
		    			<div id="studyDisplay${status.index}" style="${command.healthcareSiteRolesHolderList[status.index].group.isStudyScoped?'':'display:none'}">
					    		<div class="row">
						            <form:checkbox id="allStudyAccess${status.index}" path="healthcareSiteRolesHolderList[${status.index}].hasAllStudyAccess" onchange="toggleUserStudyAutocompleter('${status.index}', '${isSiteScoped}','${isStudyScoped}')" />&nbsp;
   					            	<fmt:message key="researchStaff.allStudyAccess" />
						        </div>
						    	<div class="row" id="userStudyAutocompleter${status.index}">
				 					<div class="orgLabel">
				 						<fmt:message key="c3pr.common.study"></fmt:message>
					 				</div>
					 				<div class="orgValue">
					 					<c:choose>
						 					<c:when test="${c3pr:hasAllStudyAccess('UI_PERSONUSER_CREATE') || c3pr:hasAllStudyAccess('USER_CREATE')}">
						 						
												<tags:autocompleter name="healthcareSiteRolesHolderList[${status.index}].selectedStudyForDisplay" size="40" displayValue="${command.healthcareSiteRolesHolderList[status.index].selectedStudyForDisplay}" 
														value="${command.healthcareSiteRolesHolderList[status.index].selectedStudyForDisplay}" basename="Study${status.index}" ></tags:autocompleter>		
												<script>
						 							var Study${status.index}AutocompleterProps = {
						 							    basename: "Study${status.index}",
						 							    populator: function(autocompleter, text) {
						 							    	var roleSites = new Array();
															$('healthcareSiteRolesHolderList[${status.index}]-sitesTable').select('[type="hidden"]').each( function(e){
																roleSites.push(e.value);
															})
						 										ResearchStaffAjaxFacade.matchStudiesGivenSites(text, roleSites, function(values) {
						 							            autocompleter.setChoices(values);
						 							        })
						 							    },
						 							    valueSelector: function(obj) {
						 							    	if(obj.externalId != null){
						 							    		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="NCI" width="17" height="16" border="0" align="middle"/>';
						 							    	} else {
						 							    		image = '';
						 							    	}
						 							    	return ("("+obj.coordinatingCenterAssignedIdentifier.value+") "+ obj.shortTitleText + image)
						 							    },
						 							    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
						 							    	var _fieldHelper = 'healthcareSiteRolesHolderList[' + inputElement.id.substring(5, inputElement.id.indexOf('-')) + ']';
						 							    	inputElement.value = " ("+selectedChoice.coordinatingCenterAssignedIdentifier.value+") "	+ selectedChoice.shortTitleText;
						 									$(_fieldHelper + '.studyId').value = selectedChoice.coordinatingCenterAssignedIdentifier.value;
						 									Form.Element.enable($('addStudy_btn[${status.index}]'));
						 									
						 									hiddenField=inputElement.id.split("-")[0]+"-hidden";
						 									$(hiddenField).value=selectedChoice.id;
						 								}
						 							}
						 							AutocompleterManager.addAutocompleter(Study${status.index}AutocompleterProps);
						 						</script>												
											</c:when>
											<c:otherwise>
												<select name="healthcareSiteRolesHolderList[${status.index}].selectedStudyForDisplay" class="required validate-notEmpty" style="width: 350px;">
													<tags:userOrgOptions privilege="USER_CREATE"/>
												</select>
											</c:otherwise>
					 					</c:choose>
										<form:hidden path="healthcareSiteRolesHolderList[${status.index}].studyId" id="healthcareSiteRolesHolderList[${status.index}].studyId"/>
										<tags:button id="addStudy_btn[${status.index}]" type="button" color="blue" value="Add" icon="add" onclick="addStudy('healthcareSiteRolesHolderList[${status.index}]', '${status.index}', '${isSiteScoped}','${isStudyScoped}')" size="small" disabled="true"/>
					 				</div>
					 				<div class="orgValue">
										<script>studiesCount[${status.index}] = ${fn:length(healthcareSiteRolesHolder.studies)};</script>
										<table id="healthcareSiteRolesHolderList[${status.index}]-studiesTable">
											<tbody>
												<tr style="display:none;">
													<td> </td>
													<td> </td>
												</tr>
												<c:forEach var="study" items="${healthcareSiteRolesHolder.studies}" varStatus="studyIndex">
													<c:set var="startIndex" value="${fn:indexOf(study, '(')}" />
													<c:set var="endIndex" value="${fn:indexOf(study, ')')}" />
													<c:set var="studyIdentifier" value="${fn:substring(study, startIndex + 1, endIndex)}" />
													<tr id="healthcareSiteRolesHolderList[${status.index}]-study-${studyIndex.index}">
														<td>&nbsp;&nbsp;&nbsp;&nbsp;${study}
															<input type='hidden' id='healthcareSiteRolesHolderList[${status.index}].studies' 
																   name='healthcareSiteRolesHolderList[${status.index}].studies' value='${studyIdentifier}' />
														</td>
														<td><a href="javascript:removeStudy('healthcareSiteRolesHolderList[${status.index}]-study-${studyIndex.index}','${status.index}', '${isSiteScoped}','${isStudyScoped}');">
															<img src="<tags:imageUrl name="checkno.gif"/>"></a>
														</td>
													</tr>	
												</c:forEach>
											</tbody>	
										</table>
									</div>
				 				</div>
				    	</div>
				    	<!-- end of studyDisplay div -->
				    	<script>
							toggleUserOrgAutocompleter('${status.index}','${command.healthcareSiteRolesHolderList[status.index].group.isSiteScoped}', '${command.healthcareSiteRolesHolderList[status.index].group.isStudyScoped}');
							toggleUserStudyAutocompleter('${status.index}','${command.healthcareSiteRolesHolderList[status.index].group.isSiteScoped}', '${command.healthcareSiteRolesHolderList[status.index].group.isStudyScoped}');
							toggleRoleContent('${status.index}','${command.healthcareSiteRolesHolderList[status.index].group.isSiteScoped}', '${command.healthcareSiteRolesHolderList[status.index].group.isStudyScoped}');
						</script>
			    	</div>
				</chrome:deletableDivision>
			</c:forEach>
		</chrome:box>
	</div>
</c3pr:checkprivilege>

<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="true"> 
	<jsp:attribute name="submitButton">
		<table>
			<tr>
				<c:if test="${command.personUser.id != null && command.personUser.class.name eq 'edu.duke.cabig.c3pr.domain.LocalPersonUser' && coppaEnable}">
					<td valign="bottom">
						<!--<tags:button type="submit" value="Sync" color="blue" id="sync-org" onclick="javascript:syncResearchStaff();" />	-->
					</td>
				</c:if>
				<td><tags:button type="submit" color="green" id="flow-update" value="Save" icon="save" />
				</td>
			</tr>
		</table>
	</jsp:attribute>
</tags:tabControls>
</form:form>
</div>

<div id="display_remote_rs" style="display:none;text-align:left" >
	<chrome:box title="Please select a Research Staff Person to be saved in C3PR" id="popupId">
		<div class="eXtremeTable">
          <table width="100%" border="0" cellspacing="0"  class="tableRegion">
            <thead>
              <tr align="center" class="label">
              	<td/>
                <td class="tableHeader">First Name</td>
                <td class="tableHeader">Last Name</td>
                <td class="tableHeader">Email Address</td>
              </tr>
            </thead>
            <c:forEach items="${command.personUser.externalResearchStaff}"  var="remRs" varStatus="rdStatus">
              <tr>
              	<td><input type="radio" name="remotersradio" value=${rdStatus.index} id="remoters-radio" onClick="javascript:selectResearchStaff('${rdStatus.index}');"/></td>
                <td align="left">${remRs.firstName}</td>
                <td align="left">${remRs.lastName}</td>
                <td align="left">${remRs.email}</td>
              </tr>
            </c:forEach>
          </table>
		</div>
		<br><br>
   		<table width="100%">	
   			<tr>
   				<td align="left">
   					<tags:button type="submit" id="save-no" color="red" value="Cancel" onclick="javascript:window.parent.Windows.close('remoteRS-popup-id');"/>
   				</td>
   				<td align="right">
   					<tags:button type="submit" color="blue" value="Ok" id="save-yes" onclick="javascript:window.parent.submitRemoteRsForSave();" disabled="disabled"/>
   				</td>
   			</tr>	
   		</table>
	</chrome:box>
</div>

<div id="display_csmUser" style="display:none;text-align:left" >
	<chrome:box title="Please select the user to be saved in C3PR" id="csm-popup-id">
		This username already exists in the system. 
		<a href='../personOrUser/editPersonOrUser?assignedIdentifier=${command.preExistingUsersAssignedId}'>Click here</a>
		to edit the pre-existing user or click cancel and select a different username.
		<br><br>
   		<table width="100%">	
   			<tr>
   				<td align="center">
   					<tags:button type="submit" value="Cancel" id="save-no" color="red" onclick="javascript:window.parent.Windows.close('csmUser-popup-id');"/>
   				</td>
   			</tr>	
   		</table>
	</chrome:box>
</div>

<script>
	new FormQueryStringUtils($('command')).stripQueryString('assignedIdentifier');
	function toggle(){
		toggleStaffDisplay();
		toggleUserDisplay();
	}	
	window.onload=toggle;
</script>
</body>
</html>
