 <%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <c:set var="sponIndex" value="${command.study.fundingSponsorIdentifierIndex==-1?fn:length(command.study.organizationAssignedIdentifiers):command.study.fundingSponsorIdentifierIndex}"></c:set>
    <jwr:script src="/js/tabbedflow.js" />
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
    <style>
		div.row div.label {
			margin-left:-1.3em;
			
		}
		div.row div.value {
			margin-left:12em;
		}
	</style>
    <script type="text/javascript">
    
    function blindedRandomization(){
    	if($('study.blindedIndicator').value == 'true'){
    		$('study.randomizedIndicator').value = 'true';
    		$('study.randomizedIndicator').disabled = true;
    		$('randomizationTypeDiv').style.display = "";
    		$('randomizationType').value="PHONE_CALL";
    		$('randomizationType').disabled = true;
    	} else {
    		$('study.randomizedIndicator').value = 'false';
    		$('study.randomizedIndicator').disabled = false;
    		$('randomizationTypeDiv').style.display = "none";
    		$('randomizationType').value="";
    		$('randomizationType').disabled = false;
    	}
    }
    ValidationManager.submitPostProcess= function(formElement, continueSubmission){
             if(formElement.id=="command" && continueSubmission){
             	box1=$('healthcareSite-hidden');
           	    if($('study.randomizedIndicator').value =='false'){
           	    	new Element.update('randomizationTypeDiv','');
           		}
	             if(box1 == null || box1.value == '') {
	            	 $('healthcareSite-input').name="abcd";
		              $('healthcareSite-hidden').name="xyz1";
		              $('healthcareSite-hidden1').name="abcdxw";
		              $('fundingSponsorIdentifier').name="xyz1ab";
		              $('fundingSponIdentifierType').name="xyzwe";
		              $('deletedSponsor').value="delete";
	             }else{
	         	   	box2=$('fundingSponsorIdentifier');
	         	     if ((box2==null)||box2.value == '') {
	                	$('deletedSponsorIdentifier').value="delete";
	           		 } else {
		           		 $('healthcareSite-hidden1').value=$('healthcareSite-hidden').value;
	           		 }
          		 }
          	  }
         	  return continueSubmission;
     } 

   function manageRandomizedIndicatorSelectBox(box) {
       if (box.value == 'true') {
           Effect.OpenUp('randomizationTypeDiv');
           $("randomizationType").className="validate-notEmpty";
       }
       if (box.value == 'false') {
           Effect.CloseDown('randomizationTypeDiv');
           $("randomizationType").className="";
       }
   }
   
   function manageRandomizationTypeSelectBox(box) {
       if (box.value == '' && ($('study.randomizedIndicator').value =='true')) {
           $("randomizationType").className="validate-notEmpty";
       }
   }
     
        var sponsorSiteAutocompleterProps = {
            basename: "healthcareSite",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	if(obj.externalId != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}

            	return (obj.name+" ("+obj.ctepCode+")" + image)
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=sponsorSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
	    							hiddenField1=sponsorSiteAutocompleterProps.basename+"-hidden1"
	    							$(hiddenField1).value=selectedChoice.id;
			 }
        }
        var coCenterAutocompleterProps = {
            basename: "coCenter",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	if(obj.externalId != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}

            	return (obj.name+" ("+obj.ctepCode+")" + image)
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=coCenterAutocompleterProps.basename+"-hidden"
    								hiddenField1=coCenterAutocompleterProps.basename+"-hidden1"
	    							$(hiddenField).value=selectedChoice.id;
	    							$(hiddenField1).value=selectedChoice.id;
			}
		}
		
		var piCoCenterAutocompleterProps = {
            basename: "piCoCenter",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
            	if(obj.externalId != null){
            		image = '&nbsp;<img src="<chrome:imageUrl name="nci_icon.png"/>" alt="Calendar" width="17" height="16" border="0" align="middle"/>';
            	} else {
            		image = '';
            	}

            	return (obj.name+" ("+obj.ctepCode+")" + image)
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=piCoCenterAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
    								$('hcsInvestigator-hidden').value ='';
									$('hcsInvestigator-input').className = $('hcsInvestigator-input').className + ' pending-search'
    								$('hcsInvestigator-input').value ='(Begin typing here)';
			}
		}
       
        var principalInvestigatorAutocompleterProps = {
            basename: "hcsInvestigator",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchStudyOrganizationInvestigatorsGivenOrganizationId(text,$("piCoCenter-hidden").value, function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return obj.investigator.fullName + ' (' +obj.investigator.nciIdentifier +')' ;
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=principalInvestigatorAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			}
        }
        
        AutocompleterManager.addAutocompleter(coCenterAutocompleterProps);
        AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);
        AutocompleterManager.addAutocompleter(piCoCenterAutocompleterProps);
	    AutocompleterManager.addAutocompleter(principalInvestigatorAutocompleterProps);

		
    </script>
</head>
<body>
<%-- Can't use tags:tabForm b/c there are two boxes in the form --%>
<form:form id="${!empty param.embeddedStudy?'embedStudyForm':'command'}" method="post" name="studyDetails" cssClass="standard">
<tags:tabFields tab="${tab}" />
<input type="hidden" name="deletedSponsor" id="deletedSponsor" value=""/>
<input type="hidden" name="deletedSponsorIdentifier" id="deletedSponsorIdentifier" value=""/>
<chrome:box title="${tab.shortTitle}">
<tags:instructions code="study_details" />
<chrome:division id="study-details" title="Basic Details">
    <div class="leftpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="study.shortTitle"/></div>
            <div class="value">
            	<form:input path="study.shortTitleText" size="30" maxlength="30" cssClass="required validate-notEmpty" id="_shortTitle"/>
            	<tags:hoverHint keyProp="study.shortTitleText"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="study.longTitle"/></div>
            <div class="value">
            	<form:textarea path="study.longTitleText" rows="2" cols="30" cssClass="required validate-notEmpty&&maxlength1024" />
            	<tags:hoverHint keyProp="study.longTitleText"/>
            </div>
        </div>

        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.description"/></div>
            <div class="value">
            	<form:textarea path="study.descriptionText" rows="2" cols="30" cssClass="validate-maxlength2000" />
            	<tags:hoverHint keyProp="study.description"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><fmt:message key="study.precis"/></div>
            <div class="value">
            	<form:textarea path="study.precisText" rows="2" cols="30" cssClass="validate-maxlength200" />
            	<tags:hoverHint keyProp="study.precisText"/>
            </div>
        </div>
    </div>
    <div class="rightpanel">
        <div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.targetAccrual"/></div>
            <div class="value">
            	<form:input path="study.targetAccrualNumber" size="10" maxlength="6" cssClass="required validate-notEmpty&&numeric&&nonzero_numeric" />
            	<tags:hoverHint keyProp="study.targetAccrualNumber"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.type"/></div>
            <div class="value">
            	<form:select path="study.type" cssClass="required validate-notEmpty" >
                	<option value="">Please Select</option>
                	<form:options items="${typeRefData}" itemLabel="desc" itemValue="desc" />
            	</form:select>
            	<tags:hoverHint keyProp="study.type"/>
            </div>
        </div>
        <div class="row">
            <div class="label"><tags:requiredIndicator /><fmt:message key="study.phase"/></div>
            <div class="value">
            	<form:select path="study.phaseCode" cssClass="required validate-notEmpty" >
                	<option value="">Please Select</option>
                	<form:options items="${phaseCodeRefData}" itemLabel="desc" itemValue="desc" />
            	</form:select>
            	<tags:hoverHint keyProp="study.phaseCode"/>
            </div>
        </div>
		<div class="row">
              <div class="label"><tags:requiredIndicator /><fmt:message key="study.blinded"/></div>
			<c:choose>
    	        <c:when test="${not empty command.study.id}">
                    <div class="value">${command.study.blindedIndicator=="true"?"Yes":"No"}&nbsp;<tags:hoverHint keyProp="study.blindedIndicator"/></div>
        	    </c:when>
            	<c:otherwise>
		            <div class="value">
		            	<form:select path="study.blindedIndicator" onchange="blindedRandomization();" cssClass="required validate-notEmpty">
		                	<option value="">Please Select</option>
		                	<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
		            	</form:select>
		            	<tags:hoverHint keyProp="study.blindedIndicator"/></div>
        		</c:otherwise>
        	</c:choose>
        </div>
        <input type="hidden" name="study.multiInstitutionIndicator" value="true"/>
        <div class="row" <c:if test="${ (empty command.study.companionIndicator) || command.study.companionIndicator=='false' ||((!empty param.embeddedStudy) && command.study.companionIndicator=='true' && param.embeddedStudy=='true')}">style="display:none;"</c:if>>
	        <div class="label"><tags:requiredIndicator /><fmt:message key="study.standaloneIndicator"/></div>
	        <div class="value">
	        	<form:select path="study.standaloneIndicator" cssClass="required validate-notEmpty" >
	            	<option value="">Please Select</option>
	            	<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
	        	</form:select>
	        	<tags:hoverHint keyProp="study.standaloneIndicator"/>
	        </div>
		</div>
        <div class="row">
	        <div class="label">
	        	<tags:requiredIndicator />
	        	<fmt:message key="study.versionNameNumber"/>
	        </div>
	       <div class="value">
            	<form:input path="study.versionName" cssClass="required validate-notEmpty" ></form:input>
            	<tags:hoverHint keyProp="study.versionNameNumber"/>
            </div>
    	</div>
	</div>
</chrome:division>

<chrome:division title="Stratification & Randomization">
    <div class="leftpanel">
    		<div class="row">
         		<div class="label"><tags:requiredIndicator /><fmt:message key="study.stratified"/></div>	
         		<div class="value"><form:select path="study.stratificationIndicator" cssClass="required validate-notEmpty">
         		<option value="">Please Select</option>
         		<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
         		</form:select>
         		<tags:hoverHint keyProp="study.stratifiedIndicator"/>
         		</div>
         	</div>
	 </div>
	 <div class="rightpanel">
	 	<div class="row">
	            <div class="label"><tags:requiredIndicator />
	                <fmt:message key="study.randomized"/></div>
	            <div class="value"><form:select path="study.randomizedIndicator"
	                                            onchange="manageRandomizedIndicatorSelectBox(this);" 
	                                            cssClass="required validate-notEmpty"
	                                            disabled="${command.study.blindedIndicator == 'true'}" >
	                <option value="">Please Select</option>
	                <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
	            </form:select>
	            <tags:hoverHint keyProp="study.randomizedIndicator"/>
	         	</div>
	      </div>
	      <div id="randomizationTypeDiv" <c:if test="${ ((empty command.study.randomizedIndicator) || command.study.randomizedIndicator=='false') && 
                						command.study.blindedIndicator == 'false'}">style="display:none;"</c:if>>
            <div class="row">
                <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.type"/></div>
                <div class="value"><form:select id="randomizationType" path="study.randomizationType" onchange="manageRandomizationTypeSelectBox(this);"  disabled="${command.study.blindedIndicator == 'true'}">
                    <form:option label="Please Select" value=""/>
                    <form:option label="Book" value="BOOK"/>
                    <form:option label="Phone Call" value="PHONE_CALL"/>
                </form:select>
                <tags:hoverHint keyProp="study.randomizationType"/></div>
            </div>
        </div>
	 </div>
</chrome:division>

<chrome:division title="Coordinating Center">
	<tags:errors path="study.coordinatingCenterAssignedIdentifier"/> 
    <div id="coordinatingCenter">
    	<div class="leftpanel">
    		<div class="row">
		    	<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.name"/></div>
				<div class="value">
					<c:set var="_codeCoord" value="" />
					<c:set var="_nameCoord" value="" />
						<c:if test="${fn:length(command.study.studyCoordinatingCenters)>0}">				
							<c:set var="_codeCoord" value="(${command.study.studyCoordinatingCenters[0].healthcareSite.ctepCode})" />
							<c:set var="_nameCoord" value="${command.study.studyCoordinatingCenters[0].healthcareSite.name}" />
						</c:if>
						<tags:autocompleter cssClass="required validate-notEmpty" hintKey="study.healthcareSite.name" name="study.studyCoordinatingCenters[0].healthcareSite" displayValue="${_nameCoord} ${_codeCoord }" value="${command.study.studyCoordinatingCenters[0].healthcareSite.id }" basename="coCenter"></tags:autocompleter>
						<input type="hidden" id="coCenter-hidden1" name="study.organizationAssignedIdentifiers[0].healthcareSite"
														value="${command.study.organizationAssignedIdentifiers[0].healthcareSite.id}" />
				</div>
			</div>	
		</div>
		<div class="rightpanel">
        	<div class="row">
            	<div class="label"><tags:requiredIndicator /><fmt:message key="study.studyIdentifier"/></div>
                <div class="value">
                	<input type="text" name="study.organizationAssignedIdentifiers[0].value" size="33" maxlength="30" id="study.organizationAssignedIdentifiers[0].value"
								value="${command.study.organizationAssignedIdentifiers[0].value}" class="required validate-notEmpty" />
					<input type="hidden" name="study.organizationAssignedIdentifiers[0].type" value="COORDINATING_CENTER_IDENTIFIER"/>
					<c:if test="${empty command.study.id}">
						<input type="hidden" name="study.organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
					</c:if>
					<tags:hoverHint keyProp="study.coordinatingcenter.identifier"/>
				</div>
			</div>            
   		</div>
	</div>
</chrome:division>

<chrome:division title="Funding Sponsor">
	<tags:errors path="study.fundingSponsorAssignedIdentifier"/>
   <div id="fundingSponsor">
     <div class="leftpanel">
        <div class="row">
            <div class="label"><fmt:message key="c3pr.common.name"/></div>
            <div class="value">
				<c:set var="_code" value="" />
				<c:set var="_name" value="" />
				<c:if test="${fn:length(command.study.studyFundingSponsors)>0}">				
					<c:set var="_code" value="(${command.study.studyFundingSponsors[0].healthcareSite.ctepCode})" />
					<c:set var="_name" value="${command.study.studyFundingSponsors[0].healthcareSite.name}" />
				</c:if>
				<tags:autocompleter name="study.studyFundingSponsors[0].healthcareSite" displayValue="${_name} ${_code}" value="${fn:length(command.study.studyFundingSponsors)>0?command.study.studyFundingSponsors[0].healthcareSite.id:''}" basename="healthcareSite" hintKey="study.studyFundingSponsor"></tags:autocompleter>
			</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label"><fmt:message key="study.studyIdentifier"/></div>
            <div class="value">
            	<div id="fundingSponId">
	            	<input type="text" name="study.organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].value" size="33"
						maxlength="30" value="${command.study.fundingSponsorIdentifierIndex==-1?'':command.study.organizationAssignedIdentifiers[sponIndex==0?1:sponIndex].value}"
						id="fundingSponsorIdentifier" />
					<input type="hidden" id="healthcareSite-hidden1"
	                    name="study.organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].healthcareSite"
	                    value="${command.study.fundingSponsorIdentifierIndex==-1?'':command.study.organizationAssignedIdentifiers[sponIndex==0?1:sponIndex].healthcareSite.id}" />
					<input type="hidden" 
						name="study.organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].type" id="fundingSponIdentifierType" value="PROTOCOL_AUTHORITY_IDENTIFIER" />
					<tags:hoverHint keyProp="study.fundingsponsor.identifier"/>
				</div>
			</div>
        </div>
     </div>
   </div>
</chrome:division>

<chrome:division title="Principal Investigator">
	<tags:errors path="study.studyOrganizations[0].studyInvestigators"/> 
    <div id="principalInvestigator">
	    <div class="leftpanel">
			<div class="row">
				<c:set var="_codeOrgPI" value="" />
				<c:set var="_nameOrgPI" value="" />
				<c:if test="${command.study.principalInvestigatorStudyOrganization != null}">				
					<c:set var="_codeOrgPI" value="(${command.study.principalInvestigatorStudyOrganization.healthcareSite.ctepCode})" />
					<c:set var="_nameOrgPI" value="${command.study.principalInvestigatorStudyOrganization.healthcareSite.name}" />
				</c:if>
                <div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></div>
                <div class="value">
                	<tags:autocompleter name="piCoCenter-hidden" displayValue="${_nameOrgPI} ${_codeOrgPI}" value="${command.study.id==null?'':command.study.principalInvestigatorStudyOrganization.healthcareSite.id}" basename="piCoCenter" cssClass="required validate-notEmpty" />
				</div>
            </div> 
	    </div>
		<div class="rightpanel">               
			<div class="row">
				<c:set var="_codePI" value="" />
				<c:set var="_namePI" value="" />
				<c:if test="${command.study.principalInvestigatorStudyOrganization != null}">				
					<c:set var="_codePI" value="(${command.study.principalInvestigator.investigator.nciIdentifier})" />
					<c:set var="_namePI" value="${command.study.principalInvestigatorFullName}" />
				</c:if>
	            <div class="label"><tags:requiredIndicator /><fmt:message key="study.principalInvestigator"/></div>
	            <div class="value"> 
	            	<tags:autocompleter name="hcsInvestigator-hidden" displayValue="${_namePI} ${_codePI}" value="${command.study.id==null?'':command.study.principalInvestigator.id}" basename="hcsInvestigator" cssClass="required validate-notEmpty" hintKey="healthcareSitePI"/>
                 </div>            
		    </div>
    	</div>
    	<div class="division"></div>
    </div>
</chrome:division>
</chrome:box>
	<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
</form:form>
</body>
</html>
