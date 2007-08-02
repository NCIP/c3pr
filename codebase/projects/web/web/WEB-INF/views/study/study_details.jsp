<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<tags:stylesheetLink name="tabbedflow" />
<tags:javascriptLink name="tabbedflow" />
<tags:includeScriptaculous />
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script type="text/javascript"><!--

        Effect.OpenUp = function(element) {
            element = $(element);
            new Effect.BlindDown(element, arguments[1] || {});
        }

        Effect.CloseDown = function(element) {

            element = $(element);
            new Effect.BlindUp(element, arguments[1] || {});
        }

        function manageSelectBox(box) {
            if (box.value == 'true') {
                Effect.OpenUp('cooperativeGroups');
            }
            if (box.value == 'false') {
                Effect.CloseDown('cooperativeGroups');
            }
        }
        submitPostProcess= function(formElement, continueSubmission){
        	if(formElement.id="command"){
        		box=document.getElementById('multiInstitutionIndicator');
	            if (box!=null && box.value == 'false') {
	            	new Element.update('cooperativeGroups','');
//	            	if(${command.multiInstitutionIndicator}){
//	            		$('deletionIndicator1').name="_deletedRow-studyCoordinatingCenters-0";
//	            		$('deletionIndicator2').name="_deletedRow-identifiers-1";
//	            	}
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
	    							$(hiddenField).value=selectedChoice.id;
								}
}
autoCompleters.push(coCenterAutocompleterProps);
autoCompleters.push(sponsorSiteAutocompleterProps);


    --></script>

</head>
<body>
<%-- Can't use tags:tabForm b/c there are two boxes in the form --%>
<form:form method="post" name="studyDetails" cssClass="standard">
	<tags:tabFields tab="${tab}" />
	<chrome:box title="${tab.shortTitle}">

		<chrome:division id="study-details" title="Basic Details">

			<div class="leftpanel">
			<div class="row">
			<div class="label">*Short Title:</div>
			<div class="value"><form:input path="shortTitleText" size="40"
				maxlength="30" cssClass="validate-notEmpty" /></div>
			</div>

			<div class="row">
			<div class="label">*Long Title:</div>
			<div class="value"><form:textarea path="longTitleText" rows="3"
				cols="40" cssClass="validate-notEmpty&&maxlength200" /></div>
			</div>

			<div class="row">
			<div class="label">Description:</div>
			<div class="value"><form:textarea path="descriptionText" rows="5"
				cols="40" cssClass="validate-maxlength2000" /></div>
			</div>

			<div class="row">

			<div class="label">Precis:</div>
			<div class="value"><form:textarea path="precisText" rows="2"
				cols="40" cssClass="validate-maxlength200" /></div>
			</div>
			</div>

			<div class="rightpanel">
			<div class="row">
			<div class="label">Target Accrual:</div>
			<div class="value"><form:input path="targetAccrualNumber" size="10"
				cssClass="validate-numeric" /></div>
			</div>

			<div class="row">
			<div class="label">*Type:</div>
			<div class="value"><form:select path="type"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options items="${typeRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></div>
			</div>

			<div class="row">
			<div class="label">*Status:</div>
			<div class="value"><form:select path="status"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options items="${statusRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></div>
			</div>

			<div class="row">
			<div class="label">*Phase:</div>
			<div class="value"><form:select path="phaseCode"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options items="${phaseCodeRefData}" itemLabel="desc"
					itemValue="desc" />
			</form:select></div>
			</div>

			<div class="row">
			<div class="label">Blinded:</div>
			<div class="value"><form:select path="blindedIndicator">
				<option value="">--Please Select--</option>
				<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
			</form:select></div>
			</div>

			<div class="row">
			<div class="label">*Sponsor:</div>
			<div class="value"><form:hidden id="healthcareSite-hidden"
				path="studyFundingSponsors[0].healthcareSite" /> <input
				id="healthcareSite-input" size="50" type="text"
				name="identifiers[0].source"
				value="${command.identifiers[0].source}" class="validate-notEmpty" />
			<tags:indicator id="healthcareSite-indicator" />
			<div id="healthcareSite-choices" class="autocomplete"></div>
			</div>
			</div>

			<div class="row">
			<div class="label">*Sponsor Study Identifier:</div>
			<div class="value"><form:input path="identifiers[0].value" size="30"
				maxlength="30" cssClass="validate-notEmpty" /> <input type="hidden"
				name="identifiers[0].type" value="Protocol Authority Identifier" />
			</div>
			</div>

			<div class="row">
			<div class="label">*Randomized:</div>
			<div class="value"><form:select path="randomizedIndicator"
				onchange="manageRandomizedIndicatorSelectBox(this);"
				cssClass="validate-notEmpty">
				<option value="">--Please Select--</option>
				<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
			</form:select></div>
			</div>


			<div id="randomizationType"
				<c:if test="${ (empty command.randomizedIndicator) || command.randomizedIndicator=='false'}">style="display:none;"</c:if>>

			<div class="row">
			<div class="label">Randomization Type:</div>
			<div class="value"><form:select path="randomizationType">
				<option value="">--Please Select--</option>
				<form:options items="${fn:split('BOOK,PHONE_CALL,CALL_OUT',',')}" />
			</form:select></div>
			</div>
			</div>
			</div>

		</chrome:division>


		<chrome:division title="Multi-Institutional Details">
			<c:choose>
				<c:when test="${not empty command.id}">
					<div class="row">
					<div class="label">*Multi-Institution:</div>
					<div class="value">${command.multiInstitutionIndicator=="true"?"Yes":"No"}</div>
					</div>
					<c:if test="${command.multiInstitutionIndicator=='true' }">
						<div class="row">
						<div class="label">*Coordinating Center:</div>
						<div class="value"><input type="hidden" id="coCenter-hidden"
							name="studyCoordinatingCenters[0].healthcareSite"
							value="${command.multiInstitutionIndicator=='true'?command.studyCoordinatingCenters[0].healthcareSite.id:'' }" />
						<input id="coCenter-input" size="50" type="text"
							name="identifiers[1].source"
							value="${command.multiInstitutionIndicator=='true'?command.identifiers[1].source:''}" />
						<tags:indicator id="coCenter-indicator" />
						<div id="coCenter-choices" class="autocomplete"></div>
						</div>
						</div>

						<div class="row">
						<div class="label"><span class="red">*</span> Coordinating Center
						Study Identifier:</div>
						<div class="value"><input type="text" name="identifiers[1].value"
							size="30" maxlength="30"
							value="${command.multiInstitutionIndicator=='true'?command.identifiers[1].value:''}" />
						<input type="hidden" name="identifiers[1].type"
							value="Coordinating Center Identifier" /></div>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<div class="row">
					<div class="label">*Multi-Institution:</div>
					<div class="value"><form:select path="multiInstitutionIndicator"
						onchange="manageSelectBox(this);" cssClass="validate-notEmpty">
						<option value="">--Please Select--</option>
						<form:options items="${yesNo}" itemLabel="desc" itemValue="code" />
					</form:select></div>
					</div>



					<!-- <input type="hidden" id="deletionIndicator1" name="abc" />
				<input type="hidden" id="deletionIndicator2" name="abc" /> -->
					<div id="cooperativeGroups"
						<c:if test="${ (empty command.multiInstitutionIndicator) || command.multiInstitutionIndicator=='false'}">style="display:none;"</c:if>>
					<div class="row">
					<div class="label">*Coordinating Center:</div>
					<div class="value"><input type="hidden" id="coCenter-hidden"
						name="studyCoordinatingCenters[0].healthcareSite"
						value="${command.multiInstitutionIndicator=='true'?command.studyCoordinatingCenters[0].healthcareSite.id:'' }" />
					<input id="coCenter-input" size="50" type="text"
						name="identifiers[1].source"
						value="${command.multiInstitutionIndicator=='true'?command.identifiers[1].source:''}" />
					<tags:indicator id="coCenter-indicator" />
					<div id="coCenter-choices" class="autocomplete"></div>
					</div>
					</div>

					<div class="row">
					<div class="label"><span class="red">*</span> Coordinating Center
					Study Identifier:</div>
					<div class="value"><input type="text" name="identifiers[1].value"
						size="30" maxlength="30"
						value="${command.multiInstitutionIndicator=='true'?command.identifiers[1].value:''}" />
					<input type="hidden" name="identifiers[1].type"
						value="Coordinating Center Identifier" /></div>
					</div>
					</div>
				</c:otherwise>
			</c:choose>
		</chrome:division>
		<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />

	</chrome:box>
</form:form>
</div>
</body>
</html>
