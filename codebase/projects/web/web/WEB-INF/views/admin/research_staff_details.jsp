<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>
        <c:choose>
            <c:when test="${command.id > 0}"><c:out value="Research Staff: ${command.firstName} ${command.lastName} - ${command.nciIdentifier}@${command.healthcareSite.name}" /></c:when>
            <c:otherwise>Create Research Staff</c:otherwise>
        </c:choose>
    </title>

<tags:dwrJavascriptLink objects="ResearchStaffAjaxFacade" />
<script language="JavaScript" type="text/JavaScript">
	ValidationManager.submitPostProcess= function(formElement, continueSubmission){
		var error = document.getElementById("errorMsg1");
		
		var groups_0 = document.getElementById("groups_0");
		var groups_1 = document.getElementById("groups_1");
		var groups_2 = document.getElementById("groups_2");
		var groups_3 = document.getElementById("groups_3");
		
		if(continueSubmission == true){
			if(groups_0.checked == true || groups_1.checked == true || groups_2.checked == true || groups_3.checked == true){
				return true;
			} else {
				error.style.display = "";
				return false;
			}
		}else {
			if(groups_0.checked == true || groups_1.checked == true || groups_2.checked == true || groups_3.checked == true){
				error.style.display = "none";
			}else{
				error.style.display = "";
			} 
			return false;
		}
	}


  var sponsorSiteAutocompleterProps = {
            basename: "healthcareSite",
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

            	return (obj.name+" ("+obj.nciInstituteCode+")" + image)
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
        }
         AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);

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
		if(${fn:length(command.externalResearchStaff) gt 0}){
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

	function submitForm(){
		document.getElementById('command').submit();
	}
	
</script>
</head>
<body>
<div id="main">
<c:choose>
	<c:when test="${command.class.name eq 'edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
		<c:set var="imageStr" value="&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='22' height='21' border='0' align='middle'/>"/>
	</c:when>
	<c:otherwise>
		<c:set var="imageStr" value=""/>
	</c:otherwise>
</c:choose>

<form:form name="researchStaffForm">
	<chrome:box title="Research Staff" htmlContent="${imageStr }">
		<chrome:flashMessage />
		<tags:tabFields tab="${tab}" />

<input type="hidden" name="_action" value="">
<input type="hidden" name="_selected" value="">
<input type="hidden" name="_finish" value="true">
<tags:instructions code="research_staff_details" />
<tags:errors path="*"/>
<chrome:division id="site" title="Organization">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
               <fmt:message key="c3pr.common.organization"/>
            </div>
            <div class="value">
            
             <c:if test="${FLOW == 'EDIT_FLOW'}">
				<c:choose>
				<c:when test="${command.healthcareSite.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
					<div>	${command.healthcareSite.name} &nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/> 
							<tags:hoverHint keyProp="researchStaff.organization"/>	</div>
				</c:when>
				<c:otherwise>
					<div>	${command.healthcareSite.name}<tags:hoverHint keyProp="researchStaff.organization"/> </div>
				</c:otherwise>
				</c:choose>
             </c:if>
             <c:if test="${FLOW == 'SAVE_FLOW'}">
               		<input type="hidden" id="healthcareSite-hidden"
						name="healthcareSite"
						value="${command.healthcareSite.id }" />
						<input id="healthcareSite-input" size="33" type="text"
						value="${command.healthcareSite.name}" class="autocomplete validate-notEmpty" />
						<tags:hoverHint keyProp="researchStaff.organization"/>
					<tags:indicator id="healthcareSite-indicator" />
					<div id="healthcareSite-choices" class="autocomplete" style="display: none;"></div>
              </c:if>
            </div>
        </div>
</chrome:division>

<chrome:division id="staff-details" title="Basic Details">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.firstName"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
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
							<form:input size="25" path="firstName" cssClass="validate-notEmpty" />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.lastName"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
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
							<form:input size="25" path="lastName" cssClass="validate-notEmpty" />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.middleName"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
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
                <fmt:message key="c3pr.common.maidenName"/>
             </div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
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
            <div class="label"><tags:requiredIndicator />
                <fmt:message key="c3pr.common.NCIIdentifier"/></div>
            
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
						<div class="value">${command.nciIdentifier}</div>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25" path="nciIdentifier" cssClass="validate-notEmpty" />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.email" /> (Username)
            </div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
						<c:choose>
							<c:when test="${!empty command.emailAsString}">
								<div class="value">${command.emailAsString}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="30" path="contactMechanisms[0].value" cssClass="validate-notEmpty&&EMAIL" /><tags:hoverHint keyProp="contactMechanism.email"/>
						  </div>
					</c:otherwise>
				</c:choose>
       	 </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.phone" /> 
            </div>
			<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
						<c:choose>
							<c:when test="${!empty command.phoneAsString}">
								<div class="value">${command.phoneAsString}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="25"
                            path="contactMechanisms[1].value" cssClass="validate-US_PHONE_NO" /><tags:hoverHint keyProp="contactMechanism.phone"/>
						  </div>
					</c:otherwise>
			</c:choose>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.fax" /> 
            </div>
			<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteResearchStaff'}">
						<c:choose>
							<c:when test="${!empty command.faxAsString}">
								<div class="value">${command.faxAsString}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							 <form:input size="25"
                            path="contactMechanisms[2].value" cssClass="validate-US_PHONE_NO" /><tags:hoverHint keyProp="contactMechanism.fax"/>
						  </div>
					</c:otherwise>
			</c:choose>
        </div>
    </div>
</chrome:division>
<chrome:division id="staff-details" title="* User Role (At least one required- Check all that apply)">
	<div id="errorMsg1" style="display:none">
		<span id='sid1' style='color:#EE3324'>Please select atleast one role.</span><br/> 	
	</div>
	   
        <c:forEach items="${groups}" var="group" varStatus="status">
            <div class="row">
                <div class="label">
                        ${group.displayName}
                </div>
                <div class="value">
                    <form:checkbox id="groups_${status.index}" path="groups" value="${group}" disabled="${fn:contains(group.displayName,'admin') && !isAdmin}" />
                </div>
            </div>
        </c:forEach>
</chrome:division>

</chrome:box>
</form:form> <tags:tabControls tab="${tab}" flow="${flow}"
	localButtons="${localButtons}" willSave="true">
	<jsp:attribute name="submitButton">
		<table>
				<tr>
					<c:if test="${command.id != null && command.class.name eq 'edu.duke.cabig.c3pr.domain.LocalResearchStaff'}">
						<td valign="bottom">
									<tags:button type="submit" value="Sync" color="blue"
									id="sync-org" onclick="javascript:syncResearchStaff();" />	
						</td>
					</c:if>
						<td>
							    	<tags:button type="submit" color="green" id="flow-update"
									value="Save" icon="save" onclick="javascript:submitForm();" />
						</td>
				</tr>
		</table>
	</jsp:attribute>
</tags:tabControls></div>



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
            <c:forEach items="${command.externalResearchStaff}"  var="remRs" varStatus="rdStatus">
              <tr>
              	<td><input type="radio" name="remotersradio" value=${rdStatus.index} id="remoters-radio" onClick="javascript:selectResearchStaff('${rdStatus.index}');"/></td>
                <td align="left">${remRs.firstName}</td>
                <td align="left">${remRs.lastName}</td>
                <td align="left">${remRs.emailAsString}</td>
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
   			<tr>	
   		</table>
	</chrome:box>
</div>



</div>
</body>
</html>
