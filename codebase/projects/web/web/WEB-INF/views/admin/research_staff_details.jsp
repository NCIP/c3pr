<%@ include file="taglibs.jsp"%>
<html>
<head>
<title>
    <c:choose>
        <c:when test="${!empty command.researchStaff.id && command.researchStaff.id > 0}">
        	<c:out value="Research Staff: ${command.researchStaff.firstName} ${command.researchStaff.lastName} - ${command.researchStaff.assignedIdentifier}" />
        </c:when>
        <c:when test="${FLOW == 'SETUP_FLOW'}">
        	Setup User
        </c:when>
        <c:otherwise>Create Research Staff</c:otherwise>
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
		font-size:1em;
	}
	.division.indented {
		margin-left:2px;
	}
	
	div.row div.orgLabel {
		float:left;
		font-weight:bold;
		margin-left:0.5em;
		text-align:right;
		width:5em;
	}
	div.row div.orgValue {
		font-weight:normal;
		margin-left:8em;
	}


</style>
<tags:dwrJavascriptLink objects="ResearchStaffAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
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

Event.observe(window, "load", function(){
	if(${fn:length(command.researchStaff.externalResearchStaff) gt 0}){
		displayRemoteResearchStaff();
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
    		$('copiedEmailAddress').name="loginId";
    	}
	}else{
		$('loginId').value='';
		ValidationManager.setInvalidState($('loginId'));
		$('loginId').disabled=false;
		$('loginId').name="loginId";
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
    initialIndex: ${fn:length(command.researchStaff.healthcareSites)},
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

</script>

</head>
<body>
<div id="main">
<c:choose>
	<c:when test="${command.researchStaff.class.name eq 'edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
		<c:set var="imageStr" value="&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='22' height='21' border='0' align='middle'/>"/>
	</c:when>
	<c:otherwise>
		<c:set var="imageStr" value=""/>
	</c:otherwise>
</c:choose>
<form:form name="researchStaffForm">
<chrome:box title="${FLOW == 'SETUP_FLOW'?'Create Research Staff as Administrator':'Research Staff'}" htmlContent="${imageStr }">
	<chrome:flashMessage />
	<tags:tabFields tab="${tab}" />

	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">
	<input type="hidden" name="_username" value="">
	<c:set var="noHealthcareSiteAssociated" value="${fn:length(command.researchStaff.healthcareSites) == 0}"></c:set>
	<tags:instructions code="research_staff_details" />
	<tags:errors path="*"/>
	<chrome:division id="staff-details" title="Basic Details">
	    <div class="leftpanel">
	        <div class="row">
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.firstName"/></div>
	            <tags:researchStaffInput commandClass="${command.researchStaff.class}" cssClass="required validate-notEmpty" path="researchStaff.firstName" size="25" value="${command.researchStaff.firstName}"></tags:researchStaffInput>
	        </div>
			<div class="row">
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.lastName"/></div>
            	<tags:researchStaffInput commandClass="${command.researchStaff.class}" cssClass="required validate-notEmpty" path="researchStaff.lastName" size="25" value="${command.researchStaff.lastName}"></tags:researchStaffInput>
	        </div>
			<div class="row">
	            <div class="label"><fmt:message key="c3pr.common.middleName"/></div>
	            <tags:researchStaffInput commandClass="${command.researchStaff.class}"  path="researchStaff.middleName" size="25" value="${command.researchStaff.middleName}"></tags:researchStaffInput>
	        </div>
			<div class="row">
	            <div class="label"><fmt:message key="c3pr.common.maidenName"/></div>
	            <tags:researchStaffInput commandClass="${command.researchStaff.class}"  path="researchStaff.maidenName" size="25" value="${command.researchStaff.maidenName}"></tags:researchStaffInput>
			</div>
		</div>
	    <div class="rightpanel">
		    <div class="row">
	   	        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.person.identifier"/></div>
		        <tags:researchStaffInput commandClass="${command.researchStaff.class}" cssClass="required validate-notEmpty" path="researchStaff.assignedIdentifier" size="25" value="${command.researchStaff.assignedIdentifier}"></tags:researchStaffInput>    
		    </div>
			<div class="row">
	            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.email" /></div>
	            <div class="value">
					<form:input size="30" path="researchStaff.email" cssClass="required validate-notEmpty&&EMAIL" onkeyup="copyUsername();"/><tags:hoverHint keyProp="contactMechanism.email"/>
				</div>
	       	</div>
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.phone" /></div>
	            <tags:researchStaffInput commandClass="${command.researchStaff.class}" cssClass="validate-US_PHONE_NO" path="researchStaff.phone" size="25" value="${command.researchStaff.phone}"></tags:researchStaffInput>
	        </div>
	        <div class="row">
	            <div class="label"><fmt:message key="c3pr.common.fax" /></div>
	             <tags:researchStaffInput commandClass="${command.researchStaff.class}" cssClass="validate-US_PHONE_NO" path="researchStaff.fax" size="25" value="${command.researchStaff.fax}"></tags:researchStaffInput>
	        </div>
	    </div>
	    <div class="division"></div>
	</chrome:division>
</chrome:box>
<chrome:box title="Account Information">
<tags:instructions code="research_staff_account_information" />
<c:choose>
<c:when test="${FLOW=='SETUP_FLOW'}">
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.username"/></div>
        <div class="value">
        	<c:choose>
        		<c:when test="${!empty errorPassword}">
        			${command.userName}
        		</c:when>
        		<c:otherwise>
        			<form:input size="20" path="userName" cssClass="required validate-notEmpty&&MAXLENGTH100"/><tags:hoverHint keyProp="contactMechanism.username"/>
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
    <div class="row">
        <div class="label">
                <fmt:message key="c3pr.common.c3prAdmin"></fmt:message>
        </div>
        <div class="value">
        	<img src="<tags:imageUrl name='check.png'/>" height="15px" width="15px"/>
       		<input type="hidden" name="healthcareSiteRolesHolderList[0].groups" value="C3PR_ADMIN" />
       		<input type="hidden" name="siteAccess" value="ALL" />
       		<input type="hidden" name="_groups" value="on" />
        </div>
    </div>
	<c:if test="${!empty errorPassword}">
		<input type="hidden" name="errorPassword" value="true"/>
		<input type="hidden" name="username" value="${username }"/>
	</c:if>
</c:when>
<c:when test="${isLoggedInUser}">
	<div class="row">
        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.username"/></div>
        <div class="value">${command.userName}</div>
    </div>
</c:when>
<c:otherwise>
	<div class="row">
        <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.username"/></div>
        <div class="value">
        	<c:choose>
        	<c:when test="${empty command.userName}">
        		<form:input size="20" path="userName" cssClass="required validate-notEmpty&&MAXLENGTH100"/><tags:hoverHint keyProp="contactMechanism.username"/>
        		<input id="usernameCheckbox" name="copyEmailAdress" type="checkbox" onclick="handleUsername();"/> <i><fmt:message key="researchStaff.copyEmailAddress" /></i>
        		<input id="copiedEmailAddress" type="hidden"/>
        		<input type="hidden" name="_createUser" value="true">
        	</c:when>
        	<c:otherwise>${command.userName}</c:otherwise>
        	</c:choose>
        </div>
    </div>
</c:otherwise>
</c:choose>
<div class="row">
        <div class="label"><fmt:message key="researchStaff.siteAccess"/></div>
        <div class="value">
       		<input id="allSiteAccessCheckbox" name="hasAccessToAllSites" type="checkbox" /><tags:hoverHint keyProp="researchStaff.accessToAllSites"/>
        </div>
    </div>
<br>
<chrome:division title="Associated Organizations" cssClass="big">
	<table id="associateOrganization" width="100%" border="0">
	<tr></tr>
    <c:forEach items="${command.healthcareSiteRolesHolderList}" var="healthcareSiteRolesHolder"  varStatus="status">
    <tr id="healthcareSite-${status}">
	    <td>
		<chrome:deletableDivision divTitle="genericTitle-${status.index}" id="genericHealthcareSiteBox-${status.index}" cssClass="small"
	    	title="Organization: ${command.healthcareSiteRolesHolderList[status.index].healthcareSite.name} (${command.healthcareSiteRolesHolderList[status.index].healthcareSite.primaryIdentifier })" minimize="true" divIdToBeMinimized="hcs-${status.index}" disableDelete="true"
		    onclick="#">
			<div id="hcs-${status.index}" style="display: none">
				<table>
				<tr>
				<c:forEach items="${groups}" var="group" varStatus="groupStatus" >
					<td>
					<c:if test="${groupStatus.index % 3 == 0}">
						</td></tr><tr><td>						
					</c:if>
					<div class="newLabel"> 
						<input type="checkbox" id="hcs-${status.index}-group-${groupStatus.index}" name="healthcareSiteRolesHolderList[${status.index}].groups" value="${group.name}" <c:if test="${c3pr:contains(healthcareSiteRolesHolder.groups, group)}"> checked </c:if> />
					</div>
					<div class="newValue">
						${group.displayName}
					</div>
					</td>
		    	</c:forEach>
		    	</tr>
		    	</table>
			</div>
		</chrome:deletableDivision>
		</td>
		</tr>
	</c:forEach>
	</table>
</chrome:division>
	<br>
	<hr />
	<div align="right">
		<tags:button id="associateOrganizationBtn" size="small" type="button" color="blue" icon="add" value="Associate organization" onclick="$('dummy-healthcareSite').innerHTML=$('genericHtml').innerHTML;RowManager.addRow(healthcareSiteRowInserterProps)" />
	</div>
</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="true"> 
	<jsp:attribute name="submitButton">
		<table>
			<tr>
				<c:if test="${command.researchStaff.id != null && command.researchStaff.class.name eq 'edu.duke.cabig.c3pr.domain.LocalResearchStaff' && coppaEnable}">
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
            <c:forEach items="${command.researchStaff.externalResearchStaff}"  var="remRs" varStatus="rdStatus">
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
   					<input type="submit" value="Cancel" id="save-no" onClick="javascript:window.parent.Windows.close('remoteRS-popup-id');"/>
   				</td>
   				<td align="right">
    				<input type="submit" disabled value="Ok" id="save-yes" onClick="javascript:window.parent.submitRemoteRsForSave();"/>
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
 				<div class="row">
 					<div class="orgLabel">
 						<fmt:message key="c3pr.common.organization"></fmt:message>
	 				</div>
	 				<div class="orgValue">
	 					<input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden" name="healthcareSiteRolesHolderList[PAGE.ROW.INDEX].healthcareSite" />
       					<input class="autocomplete validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input" size="40"  
       																						value="${command.healthcareSiteRolesHolderList[PAGE.ROW.INDEX].healthcareSite.name}"/>
      		 			<tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
						<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete" style="display:none;"></div>
	 				</div>
 				</div>
 				<br>
 				<table width="100%">
 				<tr>
 				<c:forEach items="${groups}" var="group" varStatus="groupStatus" >
 					<td>
					<c:if test="${groupStatus.index % 3 == 0}">
						</td></tr><tr><td>						
					</c:if>
					<div class="newLabel"> 
						<input type="checkbox" id="hcs-PAGE.ROW.INDEX-group-${groupStatus.index}" name="healthcareSiteRolesHolderList[PAGE.ROW.INDEX].groups" value="${group.name}"  />
					</div>
					<div class="newValue">
						${group.displayName}
					</div>
					</td>
		    	</c:forEach>
		    	</tr>
		    	</table>
		</chrome:deletableDivision>
		</td>
		</tr>
</table>
</div>
<script>
	new FormQueryStringUtils($('command')).stripQueryString('assignedIdentifier');
	//$('associateOrganizationBtn').click()
</script>
</body>
</html>
