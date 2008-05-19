<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
	<c:set var="sponIndex" value="${command.fundingSponsorIdentifierIndex==-1?fn:length(command.organizationAssignedIdentifiers):command.fundingSponsorIdentifierIndex}"></c:set>
    <tags:stylesheetLink name="tabbedflow" />
    <tags:javascriptLink name="tabbedflow" />
    <tags:includeScriptaculous />
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
    <script type="text/javascript">
    
    function blindedRandomization(){
    	var bIndicator=document.getElementById('blindedIndicator');
    	var rIndicator=document.getElementById('randomizedIndicator');
    	var rType=document.getElementById('randomizationType');
    	var rTypeDiv=document.getElementById('randomizationTypeDiv');

    	if(bIndicator.value == 'true'){
    		rIndicator.value = 'true';
    		rIndicator.disabled = true;
    		rTypeDiv.style.display = "";
    		rType.value="PHONE_CALL";
    		rType.disabled = true;
    	} else {
    		rIndicator.value = 'false';
    		rIndicator.disabled = false;
    		rTypeDiv.style.display = "none";
    		rType.value="";
    		rType.disabled = false;
    	}
    }

        Effect.OpenUp = function(element) {
            element = $(element);
            new Effect.BlindDown(element, arguments[1] || {});
        }

        Effect.CloseDown = function(element) {

            element = $(element);
            new Effect.BlindUp(element, arguments[1] || {});
        }

        ValidationManager.submitPostProcess= function(formElement, continueSubmission){
           if(formElement.id="command"){
                 box1=document.getElementById('healthcareSite-hidden');
               	if(document.getElementById('randomizedIndicator').value =='false'){
               	new Element.update('randomizationTypeDiv','');
               	};
                 if((box1==null)||box1.value == '') {
	                 document.getElementById('healthcareSite-input').name="abcd";
	                 document.getElementById('healthcareSite-hidden').name="xyz1";
	                 document.getElementById('healthcareSite-hidden1').name="abcdxw";
	                 document.getElementById('fundingSponsorIdentifier').name="xyz1ab";
	                 document.getElementById('fundingSponIdentifierType').name="xyzwe";
	                 document.getElementById('deletedSponsor').value="delete";
                 }
                 else{
             	   	box2=document.getElementById('healthcareSite-hidden1');
	                if ((box2==null)||box2.value == '') {
	                   document.getElementById('deletedSponsorIdentifier').value="delete";
	             }
	             }
             }
             return continueSubmission;
         } 

        function manageRandomizedIndicatorSelectBox(box) {
            if (box.value == 'true') {
                Effect.OpenUp('randomizationTypeDiv');
                document.getElementById("randomizationType").className="validate-notEmpty";
            }
            if (box.value == 'false') {
                Effect.CloseDown('randomizationTypeDiv');
                document.getElementById("randomizationType").className="";
            }
        }
        
        function manageRandomizationTypeSelectBox(box) {
            if (box.value == '' && (document.getElementById('randomizedIndicator').value =='true')) {
                document.getElementById("randomizationType").className="validate-notEmpty";
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
                return obj.name
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
                return obj.name
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=coCenterAutocompleterProps.basename+"-hidden"
    								hiddenField1=coCenterAutocompleterProps.basename+"-hidden1"
	    							$(hiddenField).value=selectedChoice.id;
	    							$(hiddenField1).value=selectedChoice.id;
			}
		}
		
		<%--var piCoCenterAutocompleterProps = {
            basename: "piCoCenter",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchHealthcareSites(text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return obj.name
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=piCoCenterAutocompleterProps.basename+"-hidden"
    								hiddenField1=piCoCenterAutocompleterProps.basename+"-hidden1"
	    							$(hiddenField).value=selectedChoice.id;
	    							$(hiddenField1).value=selectedChoice.id;
			}
		}--%>
       
        var principalInvestigatorAutocompleterProps = {
            basename: "investigator0",
            populator: function(autocompleter, text) {
                StudyAjaxFacade.matchStudyOrganizationInvestigatorsGivenOrganizationId(text,document.getElementById("coCenter-hidden").value, function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return obj.investigator.fullName
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=principalInvestigatorAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			}
        }
        
        AutocompleterManager.addAutocompleter(coCenterAutocompleterProps);
        AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);
        <%--AutocompleterManager.addAutocompleter(piCoCenterAutocompleterProps);--%>
	    AutocompleterManager.addAutocompleter(principalInvestigatorAutocompleterProps);


    </script>
</head>
<body>
<%-- Can't use tags:tabForm b/c there are two boxes in the form --%>
<form:form method="post" name="studyDetails" cssClass="standard">
<tags:tabFields tab="${tab}" />

<div>
    <input type="hidden" name="deletedSponsor" id="deletedSponsor" value=""/>
    <div>
    <input type="hidden" name="deletedSponsorIdentifier" id="deletedSponsorIdentifier" value=""/>
</div>

<chrome:box title="${tab.shortTitle}">

<chrome:division id="study-details" title="Basic Details">

    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                Short Title:</div>
            <div class="value"><form:input path="shortTitleText" size="43"
                                           maxlength="30" cssClass="validate-notEmpty" />
            <tags:hoverHint keyProp="study.shortTitleText"/>
            </div>
        </div>

        <div class="row">
            <div class="label required-indicator">
                Long Title:</div>
            <div class="value"><form:textarea path="longTitleText" rows="2"
                                              cols="40" cssClass="validate-notEmpty&&maxlength200" />
            <tags:hoverHint keyProp="study.longTitleText"/>
            </div>
            
        </div>

        <div class="row">
            <div class="label">Description:</div>
            <div class="value"><form:textarea path="descriptionText" rows="2"
                                              cols="40" cssClass="validate-maxlength2000" />
            <tags:hoverHint keyProp="study.description"/></div>
        </div>

        <div class="row">
            <div class="label">Precis:</div>
            <div class="value"><form:textarea path="precisText" rows="2"
                                              cols="40" cssClass="validate-maxlength200" />
            <tags:hoverHint keyProp="study.precisText"/>
            </div>
        </div>
    </div>

    <div class="rightpanel">
        <div class="row">
            <div class="label required-indicator">Target Accrual:</div>
            <div class="value"><form:input path="targetAccrualNumber" size="10" maxlength="6"
                                           cssClass="validate-notEmpty&&numeric&&nonzero_numeric" />
            <tags:hoverHint keyProp="study.targetAccrualNumber"/></div>
        </div>

        <div class="row">
            <div class="label required-indicator">
                Type:</div>
            <div class="value"><form:select path="type"
                                            cssClass="validate-notEmpty" >
                <option value="">Please Select</option>
                <form:options items="${typeRefData}" itemLabel="desc"
                              itemValue="desc" />
            </form:select>
            <tags:hoverHint keyProp="study.type"/></div>
        </div>
      
        <div class="row">
            <div class="label required-indicator">
                Phase:</div>
            <div class="value"><form:select path="phaseCode"
                                            cssClass="validate-notEmpty" >
                <option value="">Please Select</option>
                <form:options items="${phaseCodeRefData}" itemLabel="desc"
                              itemValue="desc" />
            </form:select>
            <tags:hoverHint keyProp="study.phaseCode"/></div>
        </div>

		<c:choose>
            <c:when test="${not empty command.id}">
                <div class="row">
                    <div class="label">
                        Blinded:</div>
                    <div class="value">${command.blindedIndicator=="true"?"Yes":"No"}&nbsp;<tags:hoverHint keyProp="study.blindedIndicator"/></div>
                </div>
            </c:when>
            <c:otherwise>
		        <div class="row">
		            <div class="label">Blinded:</div>
		            <div class="value"><form:select path="blindedIndicator" onchange="blindedRandomization();">
		                <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
		            </form:select>
		            <tags:hoverHint keyProp="study.blindedIndicator"/></div>
		        </div>
        	</c:otherwise>
        </c:choose>
        
         <c:choose>
            <c:when test="${not empty command.id}">
                <div class="row">
                    <div class="label required-indicator">
                        Multi-Institutional:</div>
                    <div class="value">${command.multiInstitutionIndicator=="true"?"Yes":"No"}&nbsp;<tags:hoverHint keyProp="study.multiInstitutionIndicator"/></div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="label required-indicator">
                        Multi-Institutional:</div>
                    <div class="value"><form:select path="multiInstitutionIndicator"
                                                   cssClass="validate-notEmpty" >
                        <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                        <option value="" selected="selected">Please Select</option>
                    </form:select> <tags:hoverHint keyProp="study.multiInstitutionIndicator"/></div>
                </div>
            </c:otherwise>
         </c:choose>

         <div class="row">
             <div class="label required-indicator">Consent Version/Date:</div>
             <div class="value">
             <tags:dateInput path="consentVersion" validateDate="false"/><em> (mm/dd/yyyy)</em>
             <tags:hoverHint keyProp="study.consentVersion"/></div>
         </div>
        
    </div>
</chrome:division>

<chrome:division title="Stratification & Randomization">
    <div class="leftpanel">
    
    		<div class="row">
         		<div class="label required-indicator">Stratified:</div>	
         		<div class="value"><form:select path="stratificationIndicator" cssClass="validate-notEmpty">
         		<option value="">Please Select</option>
         		<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
         		</form:select>
         		<tags:hoverHint keyProp="study.stratifiedIndicator"/>
         		</div>
         	</div>
         	
	        <div class="row">
		            <div class="label required-indicator">
		                Randomized:</div>
		            <div class="value"><form:select path="randomizedIndicator"
		                                            onchange="manageRandomizedIndicatorSelectBox(this);" 
		                                            cssClass="validate-notEmpty"
		                                            disabled="${command.blindedIndicator == 'true'}" >
		                <option value="">Please Select</option>
		                <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
		            </form:select>
		            <tags:hoverHint keyProp="study.randomizedIndicator"/>
		            </div>
	            </div>
    </div>
	<div class="rightpanel">
        <div id="randomizationTypeDiv"
                <c:if test="${ ((empty command.randomizedIndicator) || command.randomizedIndicator=='false') && 
                						command.blindedIndicator == 'false'}">style="display:none;"</c:if>>

            <div class="row">
                <div class="label required-indicator">Type:</div>
                <div class="value"><form:select id="randomizationType" path="randomizationType" onchange="manageRandomizationTypeSelectBox(this);"  disabled="${command.blindedIndicator == 'true'}">
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
<tags:errors path="coordinatingCenterAssignedIdentifier"/> 

         <div id="coordinatingCenter">
            	<div class="leftpanel">
                	 <div class="row">
		                        <div class="label required-indicator">Name:</div>
		                        <div class="value"><input type="hidden" id="coCenter-hidden"
								name="studyCoordinatingCenters[0].healthcareSite"
								value="${command.studyCoordinatingCenters[0].healthcareSite.id }" />
								<input type="hidden" id="coCenter-hidden1"
									name="organizationAssignedIdentifiers[0].healthcareSite"
									value="${command.organizationAssignedIdentifiers[0].healthcareSite.id}" />
								<input id="coCenter-input" size="50" type="text"
								name="studyCoordinatingCenters[0].healthcareSite.name"
								value="${command.studyCoordinatingCenters[0].healthcareSite.name}" class="autocomplete validate-notEmpty" />
								<tags:hoverHint keyProp="study.healthcareSite.name"/>
							<tags:indicator id="coCenter-indicator" />
							<div id="coCenter-choices" class="autocomplete"></div>
							</div>
                    </div>	
                    <div class="row">
	                       <div class="label required-indicator">Principal Investigator:</div>
	                       <div class="value"> <form:hidden id="investigator0-hidden"
                                path="studyCoordinatingCenters[0].studyInvestigators[0].healthcareSiteInvestigator"/>
		                   		<input type="text" id="investigator0-input" size="30"
		                          		value="${command.studyCoordinatingCenters[0].studyInvestigators[0].healthcareSiteInvestigator.investigator.fullName}" class="autocomplete validate-notEmpty"/>
		                   		<tags:indicator id="investigator0-indicator"/>
		                   		<div id="investigator0-choices" class="autocomplete"></div>
		                   		<input type="hidden" name="studyCoordinatingCenters[0].studyInvestigators[0].roleCode"
								  		value="Principal Investigator"/>
						   		<input type="hidden" name="studyCoordinatingCenters[0].studyInvestigators[0].statusCode" value="Active"/>
						   		<tags:hoverHint keyProp="study.healthcareSiteInvestigator"/>
	                		</div>
	            	</div>			 
			    </div>
			    
				<div class="rightpanel">
                    <div class="row">
                        <div class="label required-indicator">Identifier:</div>
                        <div class="value">
                        	<input type="text" name="organizationAssignedIdentifiers[0].value" 
								size="30" maxlength="30"
								value="${command.organizationAssignedIdentifiers[0].value}" class="validate-notEmpty" />
							<input type="hidden" name="organizationAssignedIdentifiers[0].type"
								value="Coordinating Center Identifier"/>
							<c:if test="${empty command.id}">
								<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
							</c:if>
							<tags:hoverHint keyProp="study.coordinatingcenter.identifier"/>
						</div>
					</div>            
          		</div>
          		
          		<div class="leftpanel">
          			
    			</div>
			</div>
</chrome:division>

<chrome:division title="Funding Sponsor">
<tags:errors path="fundingSponsorAssignedIdentifier"/>

   <div id="fundingSponsor">
     <div class="leftpanel">
        <div class="row">
            <div class="label">Name:</div>
            <div class="value">
            	<input type="text" id="healthcareSite-input" size="50"
            		name="aaaxxx" 
            		value="${fn:length(command.studyFundingSponsors)>0?command.studyFundingSponsors[0].healthcareSite.name:''}"
            		class="autocomplete" />
				<input type="hidden" id="healthcareSite-hidden"
            		name="studyFundingSponsors[0].healthcareSite"
            		value="${fn:length(command.studyFundingSponsors)>0?command.studyFundingSponsors[0].healthcareSite.id:''}"/>            		
			<tags:indicator id="healthcareSite-indicator" />
			<tags:hoverHint keyProp="study.studyFundingSponsor"/>
			<div id="healthcareSite-choices" class="autocomplete"></div>
			</div>
        </div>
	</div>
	<div class="rightpanel">
        <div class="row">
            <div class="label">Identifier:</div>
            <div class="value">
            	<div id="fundingSponId">
	            	<input type="text" name="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].value" size="30"
						maxlength="30" value="${command.fundingSponsorIdentifierIndex==-1?'':command.organizationAssignedIdentifiers[sponIndex==0?1:sponIndex].value}"
						id="fundingSponsorIdentifier" />
					<input type="hidden" id="healthcareSite-hidden1"
	                    name="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].healthcareSite"
	                    value="${command.fundingSponsorIdentifierIndex==-1?'':command.organizationAssignedIdentifiers[sponIndex==0?1:sponIndex].healthcareSite.id}" />
					<input type="hidden" 
						name="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].type" id="fundingSponIdentifierType" value="Protocol Authority Identifier" />
					<tags:hoverHint keyProp="study.fundingsponsor.identifier"/>
				</div>
			</div>
        </div>
     </div>
   </div>
</chrome:division>

<%-- <chrome:division title="Principal Investigator">
    <div id="principalInvestigator">
    <div class="leftpanel">
  				<div class="row">
                        <div class="label">Name:</div>
                        <div class="value"><input type="hidden" id="piCoCenter-hidden"
						name="studyCoordinatingCenters[0].healthcareSite"
						value="${command.studyCoordinatingCenters[0].healthcareSite.id }" />
						<input type="hidden" id="piCoCenter-hidden1"
							name="organizationAssignedIdentifiers[0].healthcareSite"
							value="${command.organizationAssignedIdentifiers[0].healthcareSite.id}" />
						<input id="piCoCenter-input" size="50" type="text"
						name="studyCoordinatingCenters[0].healthcareSite.name"
						value="${command.studyCoordinatingCenters[0].healthcareSite.name}" class="autocomplete" />
						<tags:indicator id="piCoCenter-indicator" />
						<div id="piCoCenter-choices" class="autocomplete"></div>
						</div>
                   </div> 

    </div>
	<div class="rightpanel">               
         					
				 <div class="row">
	                        <div class="label">Principal Investigator:</div>
	                       <div class="value"> <form:hidden id="investigator0-hidden"
                                path="studyCoordinatingCenters[0].studyInvestigators[0].healthcareSiteInvestigator"/>
                   <input type="text" id="investigator0-input" size="30"
                          value="${command.studyCoordinatingCenters[0].studyInvestigators[0].healthcareSiteInvestigator.investigator.fullName}" class="autocomplete"/>
                   <tags:indicator id="investigator0-indicator"/>
                   <div id="investigator0-choices" class="autocomplete"></div>
                   <input type="hidden" name="studyCoordinatingCenters[0].studyInvestigators[0].roleCode"
					value="Principal Investigator"/>
					<input type="hidden" name="studyCoordinatingCenters[0].studyInvestigators[0].statusCode" value="Active"/>
                   </div>            
          </div>
    </div>
</chrome:division>
--%>

<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />

</chrome:box>
</form:form>

</body>
</html>
