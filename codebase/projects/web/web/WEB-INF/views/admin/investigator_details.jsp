<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>
    	<c:choose>
            <c:when test="${command.id > 0}"><c:out value="Investigator: ${command.firstName} ${command.lastName} - ${command.assignedIdentifier}" /></c:when>
            <c:otherwise>Create Investigator</c:otherwise>
        </c:choose>
    </title>
<tags:dwrJavascriptLink objects="InvestigatorAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
var healthcareSiteAutocompleterProps = {
    basename: "healthcareSite",
    populator: function(autocompleter, text) {
        InvestigatorAjaxFacade.matchHealthcareSites( text,function(values) {
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
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
									}
}
var investigatorAutocompleterProps = {
            add_row_division_id: "invesitgatorTable", 	        /* this id belongs to element where the row would be appended to */
            skeleton_row_division_id: "dummy-row",
            initialIndex:  ${fn:length(command.healthcareSiteInvestigators)},                            /* this is the initial count of the rows when the page is loaded  */
            path: "healthcareSiteInvestigators",                               /* this is the path of the collection that holds the rows  */
            postProcessRowInsertion: function(object){
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
        
 RowManager.addRowInseter(investigatorAutocompleterProps);

function fireAction(action, selected){	
			document.getElementById("command")._finish.name='xyz';		    
			document.getElementById("command")._action.value=action;
			document.getElementById("command")._selected.value=selected;		
			document.getElementById("command").submit();
		
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

Event.observe(window, "load", function(){
	if(${fn:length(command.externalInvestigators) gt 0}){
		displayRemoteResearchStaff();
	}
	
});

function submitRemoteRsForSave(){
	var form = document.getElementById('command');
	form._action.value="saveRemoteInvestigator";
	form.submit();
}

function selectResearchStaff(selectedIndex){
	var form = document.getElementById('command')
	form._selected.value=selectedIndex;
	document.getElementById('save-yes').disabled = false;
}

function syncInvestigator(){
	var form = document.getElementById('command');
	form._action.value="syncInvestigator";
	form.submit();
}

function submitForm(){
	document.getElementById('command').submit();
}
</script>

</head>
<body>

<div id="main">
<c:choose>
	<c:when test="${command.class.name eq 'edu.duke.cabig.c3pr.domain.RemoteInvestigator'}">
		<c:set var="imageStr" value="&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='22' height='21' border='0' align='middle'/>"/>
	</c:when>
	<c:otherwise>
		<c:set var="imageStr" value=""/>
	</c:otherwise>
</c:choose>
<form:form name="investigatorForm">
		<chrome:flashMessage />
		<tags:tabFields tab="${tab}" />
<c:choose>
<c:when test="${FLOW == 'EDIT_FLOW'}">
	<c:set var="hasEditPrivilege" value="false"/>
	<c:forEach items="${command.healthcareSiteInvestigators}" var="siteInv">
		<csmauthz:accesscontrol domainObject="${siteInv.healthcareSite}" hasPrivileges="UI_INVESTIGATOR_UPDATE" authorizationCheckName="siteAuthorizationCheck">
			<c:set var="hasEditPrivilege" value="true"/>
		</csmauthz:accesscontrol>
	</c:forEach>
</c:when>
<c:otherwise>
	<c:set var="hasEditPrivilege" value="true"/>
</c:otherwise>
</c:choose>

<chrome:box title="Investigator" htmlContent="${imageStr }">
<tags:instructions code="investigator_details" />
	<input type="hidden" name="_action" value="">
	<input type="hidden" name="_selected" value="">
	<input type="hidden" name="_finish" value="true">
<tags:errors path="*"/>
<chrome:division id="investigator-details" title="Basic Details">
	<div class="leftpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.firstName"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.firstName}">
								<div class="value">${command.firstName}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25" path="firstName" cssClass="required validate-notEmpty" />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.lastName"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.lastName}">
								<div class="value">${command.lastName}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25" path="lastName" cssClass="required validate-notEmpty" />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.middleName"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.middleName}">
								<div class="value">${command.middleName}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25" path="middleName" />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.maidenName"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.maidenName}">
								<div class="value">${command.maidenName}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25" path="maidenName" />
						  </div>
					</c:otherwise>
				</c:choose>
        	</div>
		</div>

	<div class="rightpanel">
    	<div class="row">
            <div class="label">
                <tags:requiredIndicator /><fmt:message key="c3pr.person.identifier"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<div class="value">${command.assignedIdentifier}</div>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25" path="assignedIdentifier" cssClass="required validate-notEmpty"/>
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.email" />
            </div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.email}">
								<div class="value">${command.email}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="30" path="email" cssClass="required validate-notEmpty&&EMAIL" /><tags:hoverHint keyProp="contactMechanism.email"/>
						  </div>
					</c:otherwise>
				</c:choose>
       	 </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.phone" /> 
            </div>
			<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.phone}">
								<div class="value">${command.phone}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25"
                            path="phone" cssClass="validate-US_PHONE_NO" /><tags:hoverHint keyProp="contactMechanism.phone"/>
						  </div>
					</c:otherwise>
			</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.fax" /> 
            </div>
			<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteInvestigator' || !hasEditPrivilege}">
						<c:choose>
							<c:when test="${!empty command.fax}">
								<div class="value">${command.fax}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							 <form:input size="25"
                            path="fax" cssClass="validate-US_PHONE_NO" /><tags:hoverHint keyProp="contactMechanism.fax"/>
						  </div>
					</c:otherwise>
			</c:choose>
        </div>
    </div>
	 <div class="clear"></div>
</chrome:division>
<chrome:division id="site" title="Organization">
	<table class="tablecontent" width="60%" border="0" cellspacing="0" id="invesitgatorTable" cellpadding="0">
		<tr>
			<th class="label" scope="col" align="left"><tags:requiredIndicator /><b><fmt:message key="c3pr.common.organization"/></b><tags:hoverHint keyProp="healthcareSiteInvestigator.organization"/></th>
			<th class="label" scope="col" align="left"><tags:requiredIndicator /><b><fmt:message key="investigator.investigatorStatus"/></b><tags:hoverHint keyProp="healthcareSiteInvestigator.statusCode"/></th>
			<th>&nbsp;</th>
		</tr>

        <c:forEach items="${command.healthcareSiteInvestigators}" var="hcsInv" varStatus="status">
			<tr id="invesitgatorTable-${status.index}">
				<td class="alt">
					<c:choose>
						<c:when test="${command.healthcareSiteInvestigators[status.index].healthcareSite.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
							${command.healthcareSiteInvestigators[status.index].healthcareSite.name} (${command.healthcareSiteInvestigators[status.index].healthcareSite.primaryIdentifier}) &nbsp;
							 <c:if test="${command.healthcareSiteInvestigators[status.index].healthcareSite.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
							 	<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>
							 </c:if>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${c3pr:hasAllSiteAccess('UI_INVESTIGATOR_CREATE')}">
									<input type="hidden"
										id="healthcareSite${status.index}-hidden"
										name="healthcareSiteInvestigators[${status.index}].healthcareSite"
										value="${command.healthcareSiteInvestigators[status.index].healthcareSite.id}" />
									<c:set var="siteDisplayText" value=""/>
									<c:if test="${!empty command.healthcareSiteInvestigators[status.index].id}">
										<c:set var="siteDisplayText" value="${command.healthcareSiteInvestigators[status.index].healthcareSite.name} (${command.healthcareSiteInvestigators[status.index].healthcareSite.primaryIdentifier})"/>
									</c:if>
									<input class="autocomplete required validate-notEmpty" type="text" id="healthcareSite${status.index}-input" size="50"
										value="${siteDisplayText }" />
								</c:when>
								<c:otherwise>
									<form:select path="healthcareSiteInvestigators[${status.index}].healthcareSite" cssClass="required validate-notEmpty" cssStyle="width: 350px;">
										<tags:userOrgOptions preSelectedSiteId="${command.healthcareSiteInvestigators[status.index].healthcareSite.id}" privilege="UI_INVESTIGATOR_CREATE"/>
									</form:select>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<tags:indicator
						id="healthcareSite${status.index}-indicator" />
					<div id="healthcareSite${status.index}-choices"
						class="autocomplete" style="display: none;"></div>
				</td>
                <td class="alt">
                    <form:select path="healthcareSiteInvestigators[${status.index}].statusCode" cssClass="required validate-notEmpty">
                    <option value="">Please Select</option>
                    <form:options items="${studySiteStatusRefData}" itemLabel="desc" itemValue="code" />
                    </form:select>
                </td>

                <c:choose>
					<c:when test="${(status.index == 0) || (not empty hcsInv.id)}"><td>&nbsp;</td></c:when>
					<c:otherwise>
						<td class="alt"><a href="javascript:RowManager.deleteRow(investigatorAutocompleterProps,${status.index},'${hcsInv.id==null?'HC#':'ID#'}${hcsInv.id==null?hcsInv.hashCode:hcsInv.id}');"><img src="<tags:imageUrl name="checkno.gif"/>"></a></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
    </table>

    <c:if test="${hasEditPrivilege && (empty createPI || createPI == false)}">
    	<tags:button type="button" color="blue" value="Add Organization" icon="add" onclick="javascript:RowManager.addRow(investigatorAutocompleterProps);" size="small"/>
    </c:if>
	
</chrome:division>


</chrome:box>
<c:if test="${hasEditPrivilege}">
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="true"> 
	<jsp:attribute name="submitButton">
		<table>
			<tr>
				<c:if test="${command.id != null && command.class.name eq 'edu.duke.cabig.c3pr.domain.LocalInvestigator' && coppaEnable &&  hasEditPrivilege}">
					<td valign="bottom">
						<!-- <tags:button type="submit" value="Sync" color="blue" id="sync-org" onclick="javascript:syncInvestigator();" />	-->
					</td>
				</c:if>
					<td>
				    	<tags:button type="submit" color="green" id="flow-update" value="Save" icon="save" />
					</td>
			</tr>
		</table>
	</jsp:attribute>
</tags:tabControls>
</c:if>
</form:form> 
</div>


<div id="dummy-row" style="display: none;">
<table>
	<tr>
		<td class="alt">
			<c:choose>
				<c:when test="${c3pr:hasAllSiteAccess('UI_INVESTIGATOR_CREATE')}">
					<input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden" name="healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite" />
		            <input class="autocomplete validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input" size="50" value="${command.healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite.name}" />
		            <tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator" />
		            <div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
				</c:when>
				<c:otherwise>
					<select name="healthcareSiteInvestigators[PAGE.ROW.INDEX].healthcareSite" class="required validate-notEmpty" style="width: 350px;">
						<tags:userOrgOptions privilege="UI_INVESTIGATOR_CREATE"/>
					</select>
				</c:otherwise>
			</c:choose>
    	</td>
	
		<td class="alt"><select id="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode" name="healthcareSiteInvestigators[PAGE.ROW.INDEX].statusCode" class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${studySiteStatusRefData}" var="siteRef">
				<option value="${siteRef.code}">${siteRef.desc }</option>
			</c:forEach>
		    </select>
        </td>
		<td class="tdalt"><a href="javascript:RowManager.deleteRow(investigatorAutocompleterProps,PAGE.ROW.INDEX,-1);">
            <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
        </td>
	</tr>
</table>
</div>


<div id="display_remote_rs" style="display:none;text-align:left" >
	<chrome:box title="Please select an Investigator to be saved in C3PR" id="popupId">
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
            <c:forEach items="${command.externalInvestigators}"  var="remRs" varStatus="rdStatus">
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
    				<input type="submit" disabled="disabled" value="Ok" id="save-yes" onClick="javascript:window.parent.submitRemoteRsForSave();"/>
   				</td>
   			<tr>	
   		</table>
	</chrome:box>
</div>
</div>
<script>
	new FormQueryStringUtils($('command')).stripQueryString('assignedIdentifier');
</script>
</body>
</html>