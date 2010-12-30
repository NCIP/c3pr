<%@ include file="taglibs.jsp"%>
<html>
<head>
<title>
    <c:choose>
        <c:when test="${!empty command.personUser.id && command.personUser.id > 0}">
        	<c:out value="Research Staff: ${command.personUser.firstName} ${command.personUser.lastName} - ${command.personUser.assignedIdentifier}" />
        </c:when>
        <c:when test="${FLOW == 'SETUP_FLOW'}">
        	Setup User
        </c:when>
        <c:otherwise>Create Person/User</c:otherwise>
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
function handleAllSiteAccess(){
	if($('allSiteAccessCheckbox').checked){
		contentWin = new Window({ width:400, height:150 ,className :"alert_lite"}) ;
		contentWin.setContent('confirmation-allsiteaccess-msg') ;
		contentWin.showCenter(true);
	}
}

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

var firstHealthcareSiteAutocompleterProps = {
    basename: "firstHealthcareSite",
    populator: function(autocompleter, text) {
			ResearchStaffAjaxFacade.matchHealthcareSites( text,function(values) {
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
    afterUpdateElement: 
	    function(inputElement, selectedElement, selectedChoice) {
			hiddenField=inputElement.id.split("-")[0]+"-hidden";
			$(hiddenField).value=selectedChoice.id;
		}
}

AutocompleterManager.addAutocompleter(firstHealthcareSiteAutocompleterProps);

var healthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {
			ResearchStaffAjaxFacade.matchHealthcareSites( text,function(values) {
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
    afterUpdateElement: 
	    function(inputElement, selectedElement, selectedChoice) {
			hiddenField=inputElement.id.split("-")[0]+"-hidden";
			$(hiddenField).value=selectedChoice.id;
		}
}
AutocompleterManager.addAutocompleter(healthcareSiteAutocompleterProps);

var healthcareSiteRowInserterProps = {
    add_row_division_id: "associateOrganization", 	        
    skeleton_row_division_id: "dummy-healthcareSite",
    initialIndex: ${fn:length(command.personUser.healthcareSites) + 1},
    softDelete: false,
    path: "healthcareSites",
    postProcessRowInsertion: function(object){
    	statusIndex = object.localIndex;
		currentRow = object.localIndex;
        clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    },
    onLoadRowInitialize: function(object, currentRowIndex){
		clonedRowInserter=Object.clone(healthcareSiteAutocompleterProps);
		clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
		AutocompleterManager.registerAutoCompleter(clonedRowInserter);
    }
};
RowManager.addRowInseter(healthcareSiteRowInserterProps);
RowManager.registerRowInserters();

function manageRoleGrouping(role, id, checkbox){
	if(checkbox.checked){
		if(role == 'STUDY_CREATOR'){
			groupStudySpecificRoles(id);
		}else if(role == 'REGISTRAR'){
			groupRegistrarSpecificRoles(id);
		}
	}
}
	
function groupStudySpecificRoles(id){
	var value = 'hcs-'+id+'-role-'
	$(value+'STUDY_TEAM_ADMINISTRATOR').checked = true;
	$(value+'STUDY_SITE_PARTICIPATION_ADMINISTRATOR').checked = true;
	$(value+'SUPPLEMENTAL_STUDY_INFORMATION_MANAGER').checked = true;
}
	
function groupRegistrarSpecificRoles(id){
	var value = 'hcs-'+id+'-role-'
	$(value+'SUBJECT_MANAGER').checked = true;
}



ValidationManager.submitPostProcess= function(formElement, flag){	
	//ensuring the checkboxes are disabled during submit as the controller relies on these values for processing
	//not clean...change in the future
	createAsUser = $('createAsUser');
	if(createAsUser != null && createAsUser.disabled){
		createAsUser.disabled='';
	}
	createAsStaff = $('createAsStaff');
	if(createAsStaff != null && createAsStaff.disabled){
		createAsStaff.disabled='';
	}
	if((createAsStaff == null || createAsStaff.checked == false)
		 && (createAsUser == null || createAsUser.checked == false)){
		alert("Select Create as Staff or Create as User");
		return false;
	}
	return true;
}


function toggleStaffDisplay(){
	createAsStaff = $('createAsStaff');
	staff_details = $('staff_details')
	if(createAsStaff != null && createAsStaff.checked == true){
		new Effect.BlindDown(staff_details);
	} else {
		new Effect.BlindUp(staff_details);
	}
}

function toggleUserDisplay(){
	var x = document.getElementsByTagName("div")
    createAsUser = $('createAsUser');
	el = $('global_roles')
	ell = $('roles')
	elll = $('username_section')
	ellll = $('allsite_section')
	if(createAsUser != null && createAsUser.checked == true){
		//display user sections
		new Effect.BlindDown(el);
		new Effect.BlindDown(ell);
		new Effect.BlindDown(elll);
		new Effect.BlindDown(ellll);
		for(var i=0; i<x.length; i++){
		    if(x[i].className=="addedRoles"){
		    	//x[i].style.display=="none" ? new Effect.BlindDown(x[i]):new Effect.BlindUp(x[i]);
		    	new Effect.BlindDown(x[i]); 
			}
	    }
	} else {
		//hide user sections
		new Effect.BlindUp(el);
		new Effect.BlindUp(ell);
		new Effect.BlindUp(elll);
		new Effect.BlindUp(ellll);
		for(var i=0; i<x.length; i++){
		    if(x[i].className=="addedRoles"){
		    	//x[i].style.display=="none" ? new Effect.BlindDown(x[i]):new Effect.BlindUp(x[i]); 
		    	new Effect.BlindUp(x[i])
			}
	    }
	}
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
<chrome:box title="${FLOW == 'SETUP_FLOW'?'Create Research Staff as Super User':'Basic Details'}" htmlContent="${imageStr}">
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
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.email" /></div>
				<tags:researchStaffInput id="email" commandClass="${command.personUser.class}" cssClass="required validate-notEmpty&&EMAIL" path="personUser.email" size="30" value="${command.personUser.email}" onkeyup="copyUsername();"></tags:researchStaffInput>
	       	</div>
		</div>
	    <div class="rightpanel">
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.phone" /></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}" cssClass="validate-US_PHONE_NO" path="personUser.phone" size="25" value="${command.personUser.phone}"></tags:researchStaffInput>
	        </div>
	        <c:set var="staffdisplay" value="display:none"/>
	        <c3pr:checkprivilege hasPrivileges="UI_RESEARCHSTAFF_CREATE">
	        	<c:set var="staffdisplay" value="display"/>
	        </c3pr:checkprivilege>
        	 <div class="row" style="${staffdisplay}">
	            <div class="label"><fmt:message key="researchstaff.createAsStaff" /></div>
	            <c:if test="${FLOW == 'SAVE_FLOW'}">
	            	<form:checkbox id="createAsStaff" path="createAsStaff" onchange="toggleStaffDisplay()"/>
	              	<!--  <input type="checkbox" id="createAsStaff" name="createAsStaff"  value="true" checked />  -->
	            </c:if>
	            <c:if test="${FLOW == 'EDIT_FLOW'}">
	            	<input type="checkbox" id="createAsStaff" name="createAsStaff" onchange="toggleStaffDisplay()" value="true"  <c:if test="${command.isStaff == 'true'}">disabled="disabled" checked</c:if> />
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
	            	<!-- <input type="checkbox" id="createAsUser" name="createAsUser"  value="true" checked /> -->
	            </c:if>
	            <c:if test="${FLOW == 'EDIT_FLOW'}">
	            	<input type="checkbox" id="createAsUser" name="createAsUser" onchange="toggleUserDisplay()" value="true"  <c:if test="${command.isUser == 'true'}">disabled="disabled" checked</c:if> />
	            </c:if>
	        </div>
	    </div>
	    <div class="division"></div>
</chrome:box>

<c3pr:checkprivilege hasPrivileges="UI_RESEARCHSTAFF_CREATE">
	<chrome:box title="Research Staff Details" htmlContent="${imageStr}" id="staff_details">
		<chrome:flashMessage />
		<tags:tabFields tab="${tab}" />
	
		<c:set var="noHealthcareSiteAssociated" value="${fn:length(command.personUser.healthcareSites) == 0}"></c:set>
		<tags:instructions code="research_staff_details" />
	    <div class="leftpanel">
			<div class="row">
	            <div class="label"><fmt:message key="c3pr.common.middleName"/></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}"  path="personUser.middleName" size="25" value="${command.personUser.middleName}"></tags:researchStaffInput>
	        </div>
			<div class="row">
	            <div class="label"><fmt:message key="c3pr.common.maidenName"/></div>
	            <tags:researchStaffInput commandClass="${command.personUser.class}"  path="personUser.maidenName" size="25" value="${command.personUser.maidenName}"></tags:researchStaffInput>
			</div>
		</div>
	    <div class="rightpanel">
	    	<div class="row">
	   	        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.person.identifier"/></div>
		        <tags:researchStaffInput commandClass="${command.personUser.class}" path="personUser.assignedIdentifier" size="25" value="${command.personUser.assignedIdentifier}"></tags:researchStaffInput>    
		    </div>
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.fax" /></div>
	             <tags:researchStaffInput commandClass="${command.personUser.class}" cssClass="validate-US_PHONE_NO" path="personUser.fax" size="25" value="${command.personUser.fax}"></tags:researchStaffInput>
	        </div>
	    </div>
	    <div class="division"></div>
	</chrome:box>
</c3pr:checkprivilege>

<chrome:box title="Account Information" >
<tags:instructions code="research_staff_account_information" />
<c:choose>
<c:when test="${FLOW=='SETUP_FLOW'}">
	<div class="row">
		<div class="label"><fmt:message key="c3pr.common.username"/></div>
        <div class="value">
        	<c:choose>
        		<c:when test="${!empty errorPassword}">
        			${command.userName}
        		</c:when>
        		<c:otherwise>
        			<form:input id="loginId" size="20" path="userName" cssClass="MAXLENGTH100"/><tags:hoverHint keyProp="contactMechanism.username"/>
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
</c:when>
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

<c:if test="${FLOW != 'SETUP_FLOW'}">
<c3pr:checkprivilege hasPrivileges="USER_CREATE">
<div class="row" id="allsite_section">
    <div class="label"><fmt:message key="researchStaff.siteAccess"/></div>
    <div class="value">
    	<form:checkbox id="allSiteAccessCheckbox" path="hasAccessToAllSites" onclick="handleAllSiteAccess();"/><tags:hoverHint keyProp="researchStaff.accessToAllSites"></tags:hoverHint>
    </div>
</div>
<br>
</c3pr:checkprivilege>
</c:if>
<chrome:division title="Associated Organizations" cssClass="big">
	<c:if test="${FLOW != 'SETUP_FLOW'}">
	<c3pr:checkprivilege hasPrivileges="USER_CREATE">
	<chrome:division title="Global Roles" cssClass="indented" insertHelp="true" insertHelpKeyProp="researchStaff.globalRoles" id="global_roles">
		<table>
			<tr>
			<c:forEach items="${globalRoles}" var="globalRole" varStatus="roleStatus" >
				<td>
				<c:if test="${roleStatus.index % 3 == 0}">
					</td></tr><tr><td>						
				</c:if>
				<div class="newLabel"> 
					<c:choose>
						<c:when test="${isLoggedInUser}">
				        	<c:choose>
				        		<c:when test="${c3pr:contains(command.healthcareSiteRolesHolderList[0].groups, globalRole)}">
				        			<img src="<tags:imageUrl name='check.png'/>" height="15px" width="15px"/>
				        		</c:when>
				        		<c:otherwise>
				        			<img src="<tags:imageUrl name='checkno.gif'/>"/>
				        		</c:otherwise>
				        	</c:choose>
						</c:when>
						<c:otherwise>
							<form:checkbox id="global-role-${globalRole.name}" path="healthcareSiteRolesHolderList[0].groups" value="${globalRole.name}"/>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="newValue">
					${globalRole.displayName}
				</div>
				</td>
	    	</c:forEach>
		 </tr>
		</table>
	</chrome:division>
	</c3pr:checkprivilege>
	</c:if>
	<table id="associateOrganization" width="100%" border="0">
	<tr></tr>
    <c:forEach items="${command.healthcareSiteRolesHolderList}" var="healthcareSiteRolesHolder"  varStatus="status">
    <tr id="healthcareSite-${status}">
	    <td>
		<chrome:deletableDivision divTitle="genericTitle-${status.index}" id="genericHealthcareSiteBox-${status.index}" cssClass="indented"
	    	title="${command.healthcareSiteRolesHolderList[status.index].healthcareSite.name} (${command.healthcareSiteRolesHolderList[status.index].healthcareSite.primaryIdentifier })" 
	    	minimize="${FLOW != 'EDIT_FLOW'?'false':'true'}" divIdToBeMinimized="hcs-${status.index}" disableDelete="true"
		    onclick="#">
		    <div id="hcs-${status.index}" style="${FLOW != 'EDIT_FLOW'?'display':'display:none'}">
			<c:if test="${fn:length(command.personUser.healthcareSites) == 0 && fn:length(command.healthcareSiteRolesHolderList) == 1}">
				<c3pr:checkprivilege hasPrivileges="UI_PERSONUSER_CREATE">
		    	<div class="row">
 					<div class="orgLabel">
 						<tags:requiredIndicator /><fmt:message key="c3pr.common.organization"></fmt:message>
	 				</div>
	 				<div class="orgValue">
	 					<c:choose>
		 					<c:when test="${c3pr:hasAllSiteAccess('UI_PERSONUSER_CREATE') || c3pr:hasAllSiteAccess('USER_CREATE')}">
								<tags:autocompleter name="healthcareSiteRolesHolderList[0].healthcareSite" size="40" displayValue="${command.healthcareSiteRolesHolderList[0].healthcareSite.name}" value="${command.healthcareSiteRolesHolderList[0].healthcareSite.id}" basename="firstHealthcareSite" cssClass="validate-notEmpty"></tags:autocompleter>							
							</c:when>
							<c:otherwise>
								<select name="healthcareSiteRolesHolderList[0].healthcareSite" class="required validate-notEmpty" style="width: 350px;">
									<tags:userOrgOptions/>
								</select>
							</c:otherwise>
	 					</c:choose>
	 				</div>
 				</div>
 				</c3pr:checkprivilege>
 				<c:if test="${FLOW == 'SETUP_FLOW'}">
	 				<div class="row">
	 					<div class="orgLabel">
	 						<tags:requiredIndicator /><fmt:message key="c3pr.common.organization"></fmt:message>
		 				</div>
		 				<div class="orgValue">
		 					<tags:autocompleter name="healthcareSiteRolesHolderList[0].healthcareSite" size="40" displayValue="${command.healthcareSiteRolesHolderList[0].healthcareSite.name}" value="${command.healthcareSiteRolesHolderList[0].healthcareSite.id}" basename="firstHealthcareSite" cssClass="validate-notEmpty"></tags:autocompleter>
		 				</div>
	 				</div>
 				</c:if>
		    </c:if>
		    <c:choose>
		    	<c:when test="${FLOW=='SETUP_FLOW'}">
				 	<div class="row">
			        	<div class="label">
			                <fmt:message key="c3pr.common.c3prAdmin"></fmt:message>
				        </div>
				        <div class="value">
				        	<img src="<tags:imageUrl name='check.png'/>" height="15px" width="15px"/>
				       		<c:forEach items="${roles}" var="role" varStatus="roleStatus" >
								<input type="hidden" id="hcs-${status.index}-role-${role.name}" name="healthcareSiteRolesHolderList[${status.index}].groups" value="${role.name}"  />
					    	</c:forEach>
					    	<c:forEach items="${globalRoles}" var="globalRole" varStatus="roleStatus" >
								<input type="hidden" id="global-role-${globalRole.name}" name="healthcareSiteRolesHolderList[${status.index}].groups" value="${globalRole.name}" />
							</c:forEach>
				        </div>
			    	</div>
		    	</c:when>
		    	<c:otherwise>
		    		<c3pr:checkprivilege hasPrivileges="USER_CREATE">
		    		<div id="roles">
			    	<table>
					<tr>
					<c:forEach items="${roles}" var="role" varStatus="roleStatus" >
						<td>
						<c:if test="${roleStatus.index % 3 == 0}">
							</td></tr><tr><td>						
						</c:if>
						<div class="newLabel"> 
							<c:choose>
								<c:when test="${isLoggedInUser}">
						        	<c:choose>
						        		<c:when test="${c3pr:contains(healthcareSiteRolesHolder.groups, role)}">
						        			<img src="<tags:imageUrl name='check.png'/>" height="15px" width="15px"/>
						        		</c:when>
						        		<c:otherwise>
						        			<img src="<tags:imageUrl name='checkno.gif'/>"/>
						        		</c:otherwise>
						        	</c:choose>
								</c:when>
								<c:otherwise>
									<form:checkbox id="hcs-${status.index}-role-${role.name}" path="healthcareSiteRolesHolderList[${status.index}].groups" value="${role.name}" onclick="manageRoleGrouping('${role.name}', ${status.index}, this);"/>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="newValue">
							${role.displayName}
						</div>
						</td>
			    	</c:forEach>
			    	</tr>
			    	</table>
			    	</div>
			    	</c3pr:checkprivilege>
		    	</c:otherwise>
		    </c:choose>
			</div>
		</chrome:deletableDivision>
		</td>
		</tr>
	</c:forEach>
	</table>
</chrome:division>
<c3pr:checkprivilege hasPrivileges="UI_PERSONUSER_CREATE">
	<br>
	<hr />
	<div align="right">
		<c:if test="${!isLoggedInUser}">
			<tags:button id="associateOrganizationBtn" size="small" type="button" color="blue" icon="add" value="Add organization" onclick="$('dummy-healthcareSite').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(healthcareSiteRowInserterProps)" />
		</c:if>
	</div>
</c3pr:checkprivilege>
<c:if test="${FLOW == 'SETUP_FLOW'}">
	<br>
	<hr />
	<div align="right">
		<tags:button id="associateOrganizationBtn" size="small" type="button" color="blue" icon="add" value="Associate organization" onclick="$('dummy-healthcareSite').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(healthcareSiteRowInserterProps)" />
	</div>
</c:if>
</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="true"> 
	<jsp:attribute name="submitButton">
		<table>
			<tr>
				<c:if test="${command.personUser.id != null && command.personUser.class.name eq 'edu.duke.cabig.c3pr.domain.LocalResearchStaff' && coppaEnable}">
					<td valign="bottom">
						<tags:button type="submit" value="Sync" color="blue" id="sync-org" onclick="javascript:syncResearchStaff();" />	
					</td>
				</c:if>
				<td>
					<tags:button type="submit" color="green" id="flow-update" value="Save" icon="save" />
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
		<a href='../researchStaff/editResearchStaff?assignedIdentifier=${command.preExistingUsersAssignedId}'>Click here</a>
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

<!-- Dummy Section -->
<div id="dummy-healthcareSite" style="display: none"></div>
<div id="genericHtml" style="display: none">
<table width="100%">
	<tr id="healthcareSite-PAGE.ROW.INDEX">
	    <td>
		<chrome:deletableDivision divTitle="genericTitle-PAGE.ROW.INDEX" id="genericHealthcareSiteBox-PAGE.ROW.INDEX" cssClass="indented"
	    	title="Organization" onclick="RowManager.deleteRow(healthcareSiteRowInserterProps,PAGE.ROW.INDEX,-1)" >
 				<tags:errors path="healthcareSiteRolesHolderList[PAGE.ROW.INDEX]" />
 				<c3pr:checkprivilege hasPrivileges="UI_PERSONUSER_CREATE">
 				<div class="row">
 					<div class="orgLabel"><tags:requiredIndicator />
 						<fmt:message key="c3pr.common.organization"></fmt:message>
	 				</div>
	 				<div class="orgValue">
	 					<c:choose>
		 					<c:when test="${c3pr:hasAllSiteAccess('UI_RESEARCHSTAFF_CREATE') || FLOW =='SETUP_FLOW'}">
		 						<input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden" name="healthcareSiteRolesHolderList[PAGE.ROW.INDEX].healthcareSite" />
		       					<input class="autocomplete validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input" size="40"  
		       																						value="${command.healthcareSiteRolesHolderList[PAGE.ROW.INDEX].healthcareSite.name}"/>
		      		 			<tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
								<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete" style="display:none;"></div>
							</c:when>
							<c:otherwise>
								<select name="healthcareSiteRolesHolderList[PAGE.ROW.INDEX].healthcareSite" class="required validate-notEmpty" style="width: 350px;">
									<tags:userOrgOptions/>
								</select>
							</c:otherwise>
	 					</c:choose>
	 				</div>
 				</div>
 				<br>
 				</c3pr:checkprivilege>
 				<c:choose>
		    	<c:when test="${FLOW=='SETUP_FLOW'}">
				 	<div class="row">
			        	<div class="label">
			                <fmt:message key="c3pr.common.c3prAdmin"></fmt:message>
				        </div>
				        <div class="value">
				        	<img src="<tags:imageUrl name='check.png'/>" height="15px" width="15px"/>
				       		<c:forEach items="${roles}" var="role" varStatus="roleStatus" >
								<input type="hidden" id="hcs-PAGE.ROW.INDEX-role-${role.name}" name="healthcareSiteRolesHolderList[PAGE.ROW.INDEX].groups" value="${role.name}"  />
					    	</c:forEach>
				        </div>
			    	</div>
		    	</c:when>
		    	<c:otherwise>
		    	<c3pr:checkprivilege hasPrivileges="USER_CREATE">
		    	<div id="roles-PAGE.ROW.INDEX" class="addedRoles">
		    		<table width="100%">
		 				<tr>
			 				<c:forEach items="${roles}" var="role" varStatus="roleStatus" >
			 					<td>
								<c:if test="${roleStatus.index % 3 == 0}">
									</td></tr><tr><td>						
								</c:if>
								<div class="newLabel"> 
									<input type="checkbox" id="hcs-PAGE.ROW.INDEX-role-${role.name}" name="healthcareSiteRolesHolderList[PAGE.ROW.INDEX].groups" value="${role.name}"  onclick="manageRoleGrouping('${role.name}', PAGE.ROW.INDEX, this);"/>
								</div>
								<div class="newValue">
									${role.displayName}
								</div>
								</td>
					    	</c:forEach>
				    	</tr>
		    		</table>
		    	</div>
		    	</c3pr:checkprivilege>
		    	</c:otherwise>
		    	</c:choose>
		</chrome:deletableDivision>
		</td>
		</tr>
</table>
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
