<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See  http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>

<html>
<head>
<title>
	<c:choose>
        <c:when test="${command.id > 0}"><c:out value="Organization: ${command.name}:${command.primaryIdentifier}" /></c:when>
        <c:otherwise>Create Organization</c:otherwise>
    </c:choose>
</title>
<style type="text/css">
div.content {
	padding: 5px 15px;
}
div.row div.label {
	width: 14em;
}
</style>
<script>
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
	function syncOrganization(){
		document.getElementById('command')._action.value="syncOrganization";
		document.getElementById('command').submit();
	}
	function submitForm(){
		document.getElementById('command').submit();
	}
	
		
    </script>
</head>
<body>
<c:choose>
<c:when test="${FLOW == 'SAVE_FLOW' && !c3pr:hasAllSiteAccess('UI_HEALTHCARE_SITE_CREATE')}">
	<tags:notAuthorized/>
</c:when>
<c:otherwise>
<c:choose>
<c:when test="${FLOW == 'EDIT_FLOW'}">
	<c:set var="hasEditPrivilege" value="false"/>
	<csmauthz:accesscontrol domainObject="${command}" hasPrivileges="UI_HEALTHCARE_SITE_UPDATE" authorizationCheckName="siteAuthorizationCheck">
		<c:set var="hasEditPrivilege" value="true"/>
	</csmauthz:accesscontrol>
</c:when>
<c:otherwise>
	<c:set var="hasEditPrivilege" value="true"/>
</c:otherwise>
</c:choose>
<div id="main"><c:choose>
	<c:when
		test="${command.class.name eq 'edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
		<c:set var="imageStr"
			value="&nbsp;<img src='/c3pr/images/chrome/nci_icon.png' alt='Calendar' width='22' height='21' border='0' align='middle'/>" />
	</c:when>
	<c:otherwise>
		<c:set var="imageStr" value="" />
	</c:otherwise>
</c:choose> 
<form:form name="createOrganization">
	<chrome:box title="Organization" htmlContent="${imageStr }">
		<chrome:flashMessage />

		<input type="hidden" name="_finish" value="true">
		<input type="hidden" name="type1" value="">
		<input type="hidden" name="_action" value="">
		<input type="hidden" name="_selected" value="">
		<tags:instructions code="organization_details" />
		<tags:errors path="*"/>
		<chrome:division id="organization" title="Details">

			<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message
				key="c3pr.common.name" /></div>
			<c:choose>
				<c:when
					test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
					<div class="value">&nbsp;${command.name} &nbsp;<img
						src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar"
						width="17" height="16" border="0" align="middle" /> <tags:hoverHint
						keyProp="organization.name" /></div>
				</c:when>
				<c:otherwise>
					<div class="value"><form:input size="40" path="name"
						cssClass="required validate-notEmpty$$maxlength200" /><tags:hoverHint
						keyProp="organization.name" /></div>
				</c:otherwise>
			</c:choose>
			</div>

			<div class = "row">
			<div class="label"><fmt:message key="c3pr.common.description" />
			</div>
			<c:choose>
				<c:when
					test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
					<c:choose>
						<c:when test="${!empty command.descriptionText}">
							<div class="value">&nbsp;${command.descriptionText}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection">&nbsp;<fmt:message
								key="c3pr.common.noDataAvailable" /></span></div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="value"><form:textarea rows="3" cols="37"
						path="descriptionText" /></div>
				</c:otherwise>
			</c:choose></div>

			<%--Note that for now CTEP is treated as Primary --%>
		    <div class="row">
        		<div class="label"><tags:requiredIndicator />
            		<fmt:message key="c3pr.common.CTEPIdentifier"/>&nbsp;(Primary)
        		</div>
        		<div class="value">
	        		<c:if test="${FLOW == 'EDIT_FLOW'}">
					&nbsp;${command.ctepCode}
	            	<tags:hoverHint keyProp="organization.primaryIdentifier"/>
	        		</c:if>
	        		<c:if test="${FLOW == 'SAVE_FLOW'}">
                		<form:input path="ctepCode" size="20" cssClass="required validate-notEmpty"/>
                		<tags:hoverHint keyProp="organization.ctepCode"/>
	        		</c:if>
        		</div>
    		</div>
    		
    		<c:if test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' && FLOW == 'EDIT_FLOW'}">
				<div class="row">
	        		<div class="label">
	            		<fmt:message key="organization.NCIInstituteCode"/>
	        		</div>
	        		<div class="value">
						&nbsp;${command.externalId}
	            		<tags:hoverHint keyProp="organization.nciCode"/>
	        		</div>
	    		</div>
			</c:if>
		</chrome:division>
		<br/>
		<chrome:division id="address" title="Address">
				<div class="row">
					<div class="label"><fmt:message key="c3pr.common.streetAddress" /></div>
						<c:choose>
							<c:when
								test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
								<c:choose>
									<c:when test="${!empty command.address.streetAddress}">
										<div class="value">&nbsp;${command.address.streetAddress}</div>
									</c:when>
									<c:otherwise>
										<div class="value"><span class="no-selection"><fmt:message
											key="c3pr.common.noDataAvailable" /></span></div>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<div class="value"><form:input size="40"
									path="address.streetAddress" /></div>
							</c:otherwise>
						</c:choose></div>
						<div class="row">
						<div class="label"><fmt:message key="c3pr.common.city" /></div>
						<c:choose>
							<c:when
								test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
								<c:choose>
									<c:when test="${!empty command.address.city}">
										<div class="value">&nbsp;${command.address.city}</div>
									</c:when>
									<c:otherwise>
										<div class="value"><span class="no-selection"><fmt:message
											key="c3pr.common.noDataAvailable" /></span></div>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<div class="value"><form:input size="20" path="address.city" cssClass="validate-ALPHABETIC"/>
								</div>
							</c:otherwise>
						</c:choose></div>
			<div class="row">
			<div class="label"><fmt:message key="c3pr.common.state" /></div>
			<c:choose>
				<c:when
					test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
					<c:choose>
						<c:when test="${!empty command.address.stateCode}">
							<div class="value">&nbsp;${command.address.stateCode}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection">&nbsp;<fmt:message
								key="c3pr.common.noDataAvailable" /></span></div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="value"><form:input size="20"
						path="address.stateCode" cssClass="validate-ALPHABETIC" /></div>
				</c:otherwise>
			</c:choose></div>

			<div class="row">
			<div class="label"><fmt:message key="c3pr.common.zip" /></div>
			<c:choose>
				<c:when
					test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
					<c:choose>
						<c:when test="${!empty command.address.postalCode}">
							<div class="value">&nbsp;${command.address.postalCode}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection">&nbsp;<fmt:message
								key="c3pr.common.noDataAvailable" /></span></div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="value"><form:input size="20"
						path="address.postalCode" /></div>
				</c:otherwise>
			</c:choose></div>

			<div class="row">
			<div class="label"><fmt:message key="c3pr.common.country" /></div>
			<c:choose>
				<c:when
					test="${command.class eq 'class edu.duke.cabig.c3pr.domain.RemoteHealthcareSite' || !hasEditPrivilege}">
					<c:choose>
						<c:when test="${!empty command.address.countryCode}">
							<div class="value">&nbsp;${command.address.countryCode}</div>
						</c:when>
						<c:otherwise>
							<div class="value"><span class="no-selection"><fmt:message
								key="c3pr.common.noDataAvailable" /></span></div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<div class="value"><form:input size="20"
						path="address.countryCode" cssClass="validate-ALPHABETIC" /></div>
				</c:otherwise>
			</c:choose>
</div>
			<script>
				<c:if test="${command.hasEndpointProperty}">
			       $('advance').checked=true;
			       <c:if test="${command.endPointAuthenticationRequired}">$('authenticationRequired').checked=true;</c:if>
			       Element.show('multisiteConfig');
			    </c:if>
    		</script>

		</chrome:division>

	</chrome:box>
</form:form> 
<div class="content buttons autoclear">
	<div class="flow-buttons">
        <span class="next">
	    	<table>
				<tr>
					<c:if test="${command.id != null && command.class.name eq 'edu.duke.cabig.c3pr.domain.LocalHealthcareSite' && coppaEnable &&  hasEditPrivilege}">
						<td valign="bottom">
									<tags:button type="submit" value="Sync" color="blue"
									id="sync-org" onclick="javascript:syncOrganization();" />	
						</td>
					</c:if>
					<td>
						    	<tags:button type="button" color="green" value="Save" icon="save" onclick="submitForm();"/>
					</td>
				</tr>
		</table>
        </span>
	</div>
</div>
<div id="display_remote_org" style="display: none; text-align: left">
<chrome:box title="Please select an Organization to be saved in C3PR"
	id="popupId">
	<div class="eXtremeTable">
	<table width="100%" border="0" cellspacing="0" class="tableRegion">
		<thead>
			<tr align="center" class="label">
				<td />
				<td class="tableHeader">Organization Name</td>
				<td class="tableHeader">NCI Institute Code</td>
			</tr>
		</thead>
		<c:forEach items="${command.externalOrganizations}" var="remOrg"
			varStatus="rdStatus">
			<tr>
				<td><input type="radio" name="remoteorgradio"
					id="remoteorg-radio" onClick="javascript:selectOrg('${rdStatus.index}');" /></td>
				<td align="left">${remOrg.name}</td>
				<td align="left">${remOrg.ctepCode}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<br>
	<br>
	<table width="100%">
		<tr>
			<td align="left"><input type="submit" value="Cancel"
				id="save-no"
				onClick="javascript:window.parent.Windows.close('remoteorg-popup-id');" />
			</td>
			<td align="right"><input type="submit" disabled value="Ok"
				id="save-yes"
				onClick="javascript:window.parent.submitRemoteOrgForSave();" /></td>
		<tr>
	</table>
</chrome:box></div>
</c:otherwise>
</c:choose>
</body>
</html>
