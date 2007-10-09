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
                 box1=document.getElementById('healthcareSite-input');
               
                 if((box1==null)||box1.value == '')
                 	new Element.update('fundingSponsor',"<input type='hidden' name='deletedSponsor'/>");
                 else{
             	   box2=document.getElementById('organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].value');
	                 if ((box2==null)||box2.value == '') {
	                     new Element.update('fundingSponId',"<input type='hidden' name='deletedSponsorIdentifier'/>");
	                 }
	             }
             }
             return continueSubmission;
         } 

        function manageRandomizedIndicatorSelectBox(box) {
            if (box.value == 'true') {
                //		document.getElementById('randomizationType').style.display='none';
                Effect.OpenUp('randomizationType');
            }
            if (box.value == 'false') {
                //		document.getElementById('randomizationType').style.display='none';
                Effect.CloseDown('randomizationType');
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
        AutocompleterManager.addAutocompleter(coCenterAutocompleterProps);
        AutocompleterManager.addAutocompleter(sponsorSiteAutocompleterProps);


    </script>

</head>
<body>
<%-- Can't use tags:tabForm b/c there are two boxes in the form --%>
<form:form method="post" name="studyDetails" cssClass="standard">
<tags:tabFields tab="${tab}" />

<c:set var="disabled" value="${flowType != 'CREATE_STUDY'}" scope="request" />
<chrome:box title="${tab.shortTitle}">

<chrome:division id="study-details" title="Basic Details">

    <div class="leftpanel">
        <div class="row">
            <div class="label required-indicator">
                Short Title:</div>
            <div class="value"><form:input path="shortTitleText" size="41"
                                           maxlength="30" cssClass="validate-notEmpty" disabled="${disabled}"/></div>
        </div>

        <div class="row">
            <div class="label required-indicator">
                Long Title:</div>
            <div class="value"><form:textarea path="longTitleText" rows="2"
                                              cols="40" cssClass="validate-notEmpty&&maxlength200" disabled="${disabled}"/></div>
        </div>

        <div class="row">
            <div class="label">Description:</div>
            <div class="value"><form:textarea path="descriptionText" rows="2"
                                              cols="40" cssClass="validate-maxlength2000" disabled="${disabled}"/></div>
        </div>

        <div class="row">
            <div class="label">Precis:</div>
            <div class="value"><form:textarea path="precisText" rows="2"
                                              cols="40" cssClass="validate-maxlength200" disabled="${disabled}"/></div>
        </div>
    </div>

    <div class="rightpanel">
        <div class="row">
            <div class="label">Target Accrual:</div>
            <div class="value"><form:input path="targetAccrualNumber" size="10"
                                           cssClass="validate-numeric" disabled="${disabled}"/></div>
        </div>

        <div class="row">
            <div class="label required-indicator">
                Type:</div>
            <div class="value"><form:select path="type"
                                            cssClass="validate-notEmpty" disabled="${disabled}">
                <option value="">--Please Select--</option>
                <form:options items="${typeRefData}" itemLabel="desc"
                              itemValue="desc" />
            </form:select></div>
        </div>

      
        <div class="row">
            <div class="label required-indicator">
                Phase:</div>
            <div class="value"><form:select path="phaseCode"
                                            cssClass="validate-notEmpty" disabled="${disabled}">
                <option value="">--Please Select--</option>
                <form:options items="${phaseCodeRefData}" itemLabel="desc"
                              itemValue="desc" />
            </form:select></div>
        </div>

        <div class="row">
            <div class="label">Blinded:</div>
            <div class="value"><form:select path="blindedIndicator" disabled="${disabled}">
                <option value="">--Please Select--</option>
                <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
            </form:select></div>
        </div>
        
         <c:choose>
            <c:when test="${not empty command.id}">
                <div class="row">
                    <div class="label required-indicator">
                        Multi-Institutional:</div>
                    <div class="value">${command.multiInstitutionIndicator=="true"?"Yes":"No"}</div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <div class="label required-indicator">
                        Multi-Institutional:</div>
                    <div class="value"><form:select path="multiInstitutionIndicator"
                                                   cssClass="validate-notEmpty" disabled="${disabled}">
                        <option value="">--Please Select--</option>
                        <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
                    </form:select></div>
                </div>
            </c:otherwise>
         </c:choose>

        <div id="consentVersion">
            <div class="row">
                <div class="label required-indicator">Consent Version:</div>
                <div class="value"><form:input path="consentVersion" size="10" cssClass="validate-notEmpty" disabled="${disabled}"/></div>
            </div>
        </div>
        
    </div>
</chrome:division>


<chrome:division title="Coordinating Center Details">
    <div class="leftpanel">


         <div id="coordinatingCenter">
                	 <div class="row">
		                        <div class="label required-indicator">Coordinating Center:</div>
		                        <div class="value"><input type="hidden" id="coCenter-hidden"
								name="studyCoordinatingCenters[0].healthcareSite"
								value="${command.studyCoordinatingCenters[0].healthcareSite.id }" class="validate-notEmpty" />
								<input type="hidden" id="coCenter-hidden1"
									name="organizationAssignedIdentifiers[0].healthcareSite"
									value="${command.organizationAssignedIdentifiers[0].healthcareSite.id}" />
								<input id="coCenter-input" size="50" type="text"
								name="organizationAssignedIdentifiers[0].healthcareSite.name"
								value="${command.organizationAssignedIdentifiers[0].healthcareSite.name}" class="validate-notEmpty" disabled="${disabled}"/>
							<tags:indicator id="coCenter-indicator" />
							<div id="coCenter-choices" class="autocomplete"></div>
							</div>
                    </div>

                    <div class="row">
                        <div class="label required-indicator">Coordinating Center
                            Study Identifier:</div>
                        <div class="value"><input type="text" name="organizationAssignedIdentifiers[0].value" 
						size="30" maxlength="30"
						value="${command.organizationAssignedIdentifiers[0].value}" class="validate-notEmpty" disabled="${disabled}"/>
					<input type="hidden" name="organizationAssignedIdentifiers[0].type"
						value="Coordinating Center Identifier"/>
						<input type="hidden" name="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/></div>
					</div>
          </div>
           

    </div>
</chrome:division>


<chrome:division title="Randomization Details">
    <div class="leftpanel">

        <div class="row">
            <div class="label required-indicator">
                Randomized:</div>
            <div class="value"><form:select path="randomizedIndicator"
                                            onchange="manageRandomizedIndicatorSelectBox(this);"
                                            cssClass="validate-notEmpty" disabled="${disabled}">
                <option value="">--Please Select--</option>
                <form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
            </form:select></div>
        </div>


        <div id="randomizationType"
                <c:if test="${ (empty command.randomizedIndicator) || command.randomizedIndicator=='false'}">style="display:none;"</c:if>>

            <div class="row">
                <div class="label">Randomization Type:</div>
                <div class="value"><form:select path="randomizationType" disabled="${disabled}">
                    <form:option label="--Please Select--" value=""/>
                    <form:option label="Book" value="BOOK"/>
                    <form:option label="Call Out" value="CALL_OUT"/>
                    <form:option label="Phone Call" value="PHONE_CALL"/>
                </form:select></div>
            </div>
        </div>

    </div>
</chrome:division>

<chrome:division title="Funding Sponsor Details">
    <div class="leftpanel">
     <div id="fundingSponsor">
        <div class="row">
            <div class="label">
                Funding Sponsor:</div>
            <div class="value">
            	<input type="text" id="healthcareSite-input" size="50"
            		name="aaaxxx" disabled="${disabled}"
            		value="${fn:length(command.studyFundingSponsors)>0?command.studyFundingSponsors[0].healthcareSite.name:''}"/>
				<input type="hidden" id="healthcareSite-hidden"
            		name="studyFundingSponsors[0].healthcareSite"
            		value="${fn:length(command.studyFundingSponsors)>0?command.studyFundingSponsors[0].healthcareSite.id:''}"/>            		
			<tags:indicator id="healthcareSite-indicator" />
			<div id="healthcareSite-choices" class="autocomplete"></div>
			</div>
        </div>

        <div class="row">
            <div class="label">
                Funding Sponsor Study Identifier:</div>
            <div class="value">
            	<div id="fundingSponId">
	            	<input type="text" name="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].value" size="30"
						maxlength="30" value="${command.fundingSponsorIdentifierIndex==-1?'':command.organizationAssignedIdentifiers[sponIndex==0?1:sponIndex].value}"
						id="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].value" disabled="${disabled}"/>
					<input type="hidden" id="healthcareSite-hidden1"
	                    name="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].healthcareSite"
	                    value="${command.fundingSponsorIdentifierIndex==-1?'':command.organizationAssignedIdentifiers[sponIndex==0?1:sponIndex].healthcareSite.id}" />
					<input type="hidden" 
						name="organizationAssignedIdentifiers[${sponIndex==0?1:sponIndex}].type" value="Protocol Authority Identifier" />
				</div>
			</div>
        </div>
        </div>

    </div>
</chrome:division>




<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />

</chrome:box>
</form:form>

</body>
</html>
