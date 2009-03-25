<%@ include file="taglibs.jsp"%>

<html>
<head>
    <title>Organization: ${command.name}:${command.nciInstituteCode}</title>
    <style type="text/css">
        div.content {
            padding: 5px 15px;
        }
    </style>
    <script>
    ValidationManager.submitPostProcess=function(formElement, flag){
    	if(formElement.id=='command'){
    		complete=true;
    		if($('advance').checked)
    			if($('studyServiceURL').value==''){
    				ValidationManager.removeError($('studyServiceURL'));
    				ValidationManager.showError($('studyServiceURL'),'required');
    				complete=false;
    			}else if($('registrationServiceURL').value==''){
    				ValidationManager.removeError($('registrationServiceURL'));
    				ValidationManager.showError($('registrationServiceURL'),'required');
    				complete=false;
    			}
    		return complete;
    	}
    }
		function displayRemoteOrgs(){
			var contentWin = new Window({className:"alphacube", destroyOnClose:true, id:"remoteorg-popup-id", width:550,  height:200, top: 30, left: 300});
			contentWin.setContent( 'display_remote_org' );
	        contentWin.showCenter(true);
	       popupObserver = {
	      			onDestroy: function(eventName, win) {
	      				if (win == contentWin) {
	      					$('display_remote_org').style.display='none';
	      					
	      					contentWin = null;
	      					Windows.removeObserver(this);
	      				}
	      			}
	      		}
	        Windows.addObserver(popupObserver);
		}


		Event.observe(window, "load", function(){
			if(${fn:length(command.externalOrganizations) gt 0}){
				displayRemoteOrgs();
			}
			
		});

	function submitRemoteOrgForSave(){
		document.getElementById('command')._action.value="saveRemoteOrg";
		document.getElementById('command').submit();
	}

	function selectOrg(selectedIndex){
		document.getElementById('command')._selected.value=selectedIndex;
		document.getElementById('save-yes').disabled = false;
	}
		
    </script>
</head>
<body>

<div id="main">

<tags:tabForm tab="${tab}" flow="${flow}" title="Organization" formName="createOrganization">

<jsp:attribute name="singleFields">
<input type="hidden" name="_finish" value="true">
<input type="hidden" name="type1" value="">
<input type="hidden" name="_action" value="">
<input type="hidden" name="_selected" value="">
<tags:instructions code="organization_details" />

<div id="display_remote_org" style="display:none;text-align:left" >
	<chrome:box title="Please select an Organization to be saved in C3PR" id="popupId">
		<div class="eXtremeTable">
          <table width="100%" border="0" cellspacing="0"  class="tableRegion">
            <thead>
              <tr align="center" class="label">
              	<td/>
                <td class="tableHeader">Organization Name</td>
                <td class="tableHeader">NCI Institute Code</td>
              </tr>
            </thead>
            <c:forEach items="${command.externalOrganizations}"  var="remOrg" varStatus="rdStatus">
              <tr>
              	<td><input "type="radio" name="remoteorgradio" value=${rdStatus.index} id="remoteorg-radio" onClick="javascript:selectOrg('${rdStatus.index}');"/></td>
                <td align="left">${remOrg.name}</td>
                <td align="left">${remOrg.nciInstituteCode}</td>
              </tr>
            </c:forEach>
          </table>
		</div>
		<br><br>
   		<table width="100%">	
   			<tr>
   				<td align="left">
   					<input type="submit" disabled value="Ok" id="save-yes" onClick="javascript:window.parent.submitRemoteOrgForSave();"/>
   				</td>
   				<td align="right">
    				<input type="submit" value="Cancel" id="save-no" onClick="javascript:window.parent.Windows.close('remoteorg-popup-id');"/>
   				</td>
   			<tr>	
   		</table>
	</chrome:box>
</div>


<chrome:division id="organization" title="Details">
<div class="leftpanel">

	<div class="row">
          <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.name"/></div>
		<c:choose>
			<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
				<div class="value">${command.name} &nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/> 
					<tags:hoverHint keyProp="organization.name"/>
				</div>
			</c:when>
			<c:otherwise>
				<div class="value">
					<form:input size="30" path="name" cssClass="validate-notEmpty" /><tags:hoverHint keyProp="organization.name"/>
				</div>
			</c:otherwise>
		</c:choose>
        </div>

    <div class="row">
        <div class="label">
            <fmt:message key="c3pr.common.description"/>
        </div>
		<c:choose>
			<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
				<c:choose>
					<c:when test="${!empty command.descriptionText}">
						<div class="value">${command.descriptionText}</div>
					</c:when>
					<c:otherwise>
						<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<div class="value">
					 <form:textarea rows="3" cols="35" path="descriptionText"/>
				  </div>
			</c:otherwise>
		</c:choose>
    </div>

    <div class="row">
        <div class="label"><tags:requiredIndicator />
            <fmt:message key="organization.NCIInstitueCode"/>
        </div>
        <div class="value">
	        <c:if test="${FLOW == 'EDIT_FLOW'}">
				${command.nciInstituteCode}
	            <tags:hoverHint keyProp="organization.nciInstituteCode"/>
	        </c:if>
	        <c:if test="${FLOW == 'SAVE_FLOW'}">
                <form:input path="nciInstituteCode" size="20" cssClass="validate-notEmpty"/>
                <tags:hoverHint keyProp="organization.nciInstituteCode"/>
	        </c:if>
        </div>
    </div>
    </chrome:division>

    <chrome:division id="multisite-config" title="Multisite Configuration" minimize="true" divIdToBeMinimized="multisiteConfig">
    <div id="multisiteConfig" style="display:none">
	    <div class="leftpanel">
	    	<div class="row">
		        <div class="label">
		            <fmt:message key="organization.advancedProperty"/>
		        </div>
		        <div class="value">
			        <input type="checkbox" id="advance" name="setAdvancedProperty">
		        </div>
	    	</div>
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />
	                <fmt:message key="organization.studyServiceURL"/>
	            </div>
	            <div class="value">
	                <input type="text" size="60" id="studyServiceURL" name="studyServiceURL" value="${command.hasEndpointProperty?command.studyEndPointProperty.url:''}" />
	            </div>
	        </div>
	
	        <div class="row">
	            <div class="label"><tags:requiredIndicator />
	                <fmt:message key="organization.registrationServiceURL"/>
	            </div>
	            <div class="value">
	                <input type="text" size="60" id="registrationServiceURL" name="registrationServiceURL" value="${command.hasEndpointProperty?command.registrationEndPointProperty.url:''}" />
	            </div>
	        </div>
	
	        <div class="row">
	            <div class="label">
	                <fmt:message key="organization.authenticationRequired"/>
	            </div>
	            <div class="value">
	                <input type="checkbox" id="authenticationRequired" name="authenticationRequired"/>
	            </div>
	        </div>
	    </div>
    </div>
    </chrome:division>
    <script>
	<c:if test="${command.hasEndpointProperty}">
       $('advance').checked=true;
       <c:if test="${command.endPointAuthenticationRequired}">$('authenticationRequired').checked=true;</c:if>
       Element.show('multisiteConfig');
    </c:if>
    </script>
    <chrome:division id="address" title="Address">
    <div class="leftpanel">

		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.streetAddress"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
						<c:choose>
							<c:when test="${!empty command.address.streetAddress}">
								<div class="value">${command.address.streetAddress}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="40" path="address.streetAddress"  />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.city"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
						<c:choose>
							<c:when test="${!empty command.address.city}">
								<div class="value">${command.address.city}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="20" path="address.city"  />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>
		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.state"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
						<c:choose>
							<c:when test="${!empty command.address.stateCode}">
								<div class="value">${command.address.stateCode}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="20" path="address.stateCode"  />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>

		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.zip"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
						<c:choose>
							<c:when test="${!empty command.address.postalCode}">
								<div class="value">${command.address.postalCode}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="20" path="address.postalCode"  />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>

		<div class="row">
            <div class="label">
                <fmt:message key="c3pr.common.country"/></div>
				<c:choose>
					<c:when test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite'}">
						<c:choose>
							<c:when test="${!empty command.address.countryCode}">
								<div class="value">${command.address.countryCode}</div>
							</c:when>
							<c:otherwise>
								<div class="value"><span class="no-selection"><fmt:message key="c3pr.common.noDataAvailable"/></span></div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<div class="value">
							<form:input size="20" path="address.countryCode"  />
						  </div>
					</c:otherwise>
				</c:choose>
        </div>

    </div>

    </chrome:division>


    </jsp:attribute>
    </tags:tabForm>
    </div>
    </body>
</html>
