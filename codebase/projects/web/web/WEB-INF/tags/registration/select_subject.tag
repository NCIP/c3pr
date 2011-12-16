<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c3pr" uri="http://edu.duke.cabig.c3pr.web/c3pr" %>
<tags:dwrJavascriptLink objects="ParticipantAjaxFacade" />
<tags:dwrJavascriptLink objects="StudyAjaxFacade" />
<script type="text/javascript">
		
  	function clearField(field) {
    	field.value = "";
    }
        
  	function moveToSearchSubject(){
  		document.getElementById('searchSubject_btn').className="fifthlevelTab-current";
  		document.getElementById('createSubject_btn').className="fifthlevelTab";
  	
  		document.getElementById('searchSubjectDiv').style.display="";
  		document.getElementById('createSubjectDiv').style.display="none";
  	}
  	
  	function moveToCreateSubject(){
  	  	document.getElementById('searchSubject_btn').className="fifthlevelTab";
  		document.getElementById('createSubject_btn').className="fifthlevelTab-current";
  		
  		document.getElementById('searchSubjectDiv').style.display="none";
  		document.getElementById('createSubjectDiv').style.display="";
  		<!--sending a blank request to createParticipantController which bombs the first time-->
  		<!--coz the command isnt instantiated. Workaround is to send a dummy request in advance and get-->
  		<!--it to instantiate it in advance so the createSubject wont fail the first time-->
  		new Ajax.Request('../participant/createParticipant', {method:'get', asynchronous:true});
  	}
  	var healthcareSiteAutocompleterProps = {
  		    basename: "healthcareSite",
  		    populator: function(autocompleter, text) {

  		        StudyAjaxFacade.matchHealthcareSites( text,function(values) {
  		            autocompleter.setChoices(values)
  		        })
  		    },
  		    valueSelector: function(obj) {
  		        return (obj.name + " (" + obj.primaryIdentifier + ")")
  		    },
  		    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
  		    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
  			    							$(hiddenField).value=selectedChoice.id;
  											}
  		}
    var mrnAutocompleterProps = {
        basename: "mrnOrganization",
        populator: function(autocompleter, text){
            ParticipantAjaxFacade.matchHealthcareSites(text, function(values){
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj){
            return (obj.name + " (" + obj.primaryIdentifier + ")")
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice){
            hiddenField = inputElement.id.split("-")[0] + "-hidden";
            $(hiddenField).value = selectedChoice.id;
        }
    };
  	var participantAutocompleterProps = {
             basename: "familyMember",
             populator: function(autocompleter, text) {
            	 ParticipantAjaxFacade.matchParticipants( text,0,function(values) {
                     autocompleter.setChoices(values)
                 })
             },
             valueSelector: function(obj) {
             	return (obj.fullName +" ("+obj.primaryIdentifierValue+")" )
             },
              afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
     								hiddenField=inputElement.id.split("-")[0]+"-hidden";
      							$(hiddenField).value=selectedChoice.id;
  		}
     };
  	 var familyMemberRowInserterProps = {
             add_row_division_id: "familyMembersTable", 	        /* this id belongs to element where the row would be appended to */
             skeleton_row_division_id: "dummy-familyMember",
             initialIndex: ${fn:length(command.studySubject.participant.relatedTo)},                            /* this is the initial count of the rows when the page is loaded  */
             path: "participant.relatedTo",                               /* this is the path of the collection that holds the rows  */
             postProcessRowInsertion: function(object){
 				        clonedRowInserter=Object.clone(participantAutocompleterProps);
 						clonedRowInserter.basename=clonedRowInserter.basename+object.localIndex;
 						AutocompleterManager.registerAutoCompleter(clonedRowInserter);
 				    },
 		    onLoadRowInitialize: function(object, currentRowIndex){
 				clonedRowInserter=Object.clone(participantAutocompleterProps);
 				clonedRowInserter.basename=clonedRowInserter.basename+currentRowIndex;
 				AutocompleterManager.registerAutoCompleter(clonedRowInserter);
 		    }
      };
    RowManager.addRowInseter(familyMemberRowInserterProps);
    AutocompleterManager.addAutocompleter(mrnAutocompleterProps);

  	 var organizationIdentifierRowInserterProps = {
             add_row_division_id: "organizationIdentifiersTable", 	        /* this id belongs to element where the row would be appended to */
             skeleton_row_division_id: "dummy-organizationIdentifierRow",
             initialIndex: ${command.studySubject.participant.MRN!=null?fn:length(command.studySubject.participant.organizationAssignedIdentifiers):fn:length(command.studySubject.participant.organizationAssignedIdentifiers)+1},                            /* this is the initial count of the rows when the page is loaded  */
             path: "studySubject.participant.organizationAssignedIdentifiers",                               /* this is the path of the collection that holds the rows  */
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

  	RowManager.addRowInseter(organizationIdentifierRowInserterProps);

  	function manageIdentifierRadio(element){
  		$$("form .identifierRadios").each(function(e)
  											{
  												e.checked=false;
  												var indicatorValue = $(e.id+"-hidden").value;
  												$(e.id+"-hidden").value="false"
  											}
  										);
  		$('organizationAssignedIdentifiers[0].primaryIndicator').value = false;
  		element.checked=true;
  		$(element.id+"-hidden").value="true";
  	}
  	
 	
  	/* handlers for  create Subject flow */
	var handlerFunc = function(t) {
		if(t.responseText != null && t.responseText != ''){
			var ret=t.responseText
		    var name=ret.substr(0,ret.indexOf("||"));
		    var id=ret.substr(ret.indexOf("||")+2);
			$('studySubject.participant').value = id;	
			document.getElementById("subject-message").innerHTML = "Selected Subject: " +name;
			message="Selected Subject: " +name;
			minimizeSubjectBox(message);
			var elMsg = document.getElementById('succesfulCreateDiv');
			var elDetails = document.getElementById('createSubjectDetailsDiv');
			new Element.hide(elDetails);
			new Effect.Grow(elMsg);
		} else {
			alert("handlerFunc: Subject Creation Failed. Please Try Again");
		}		
	}
	
	function toggleAddressSection(){
	 var el = document.getElementById('addressSection');
		if ( el.style.display != 'none' ) {
			new Effect.BlindUp(el);
		}
		else {
			new Effect.BlindDown(el);
		}
	}
	
	var handlerFail = function(t) {
		var d = $('errorsOpenDiv');
		Dialog.alert(d.innerHTML, 
		{width:500, height:200, okLabel: "close", ok:function(win) {debug("validate alert panel"); return true;}});
	}
	/* handlers for  create Subject flow */
	
	/* handlers for searchSubject flwo */
	function callbackSubject(t){
		new Element.hide('searchSubjectInd');
		var resultDiv = document.getElementById("subjectSearchResults");
		resultDiv.innerHTML = t.responseText;
		new Effect.SlideDown(resultDiv);
	}	
	
	function callbackSubjectFail(t){
		alert("subject search failed");
	}
	
	
	
	ValidationManager.submitPostProcess= function(formElement, flag){
		
		if(formElement.name == 'createSubForm'){
			//for the create subject form we make an ajax submit and return false to avoid the html submit
			var raceCodeFlag=false;
			for(i=0 ; i<7 ; i++){
				if($('raceCodes'+i).checked){
					raceCodeFlag=true;
					break;
				}
			}
			if(!raceCodeFlag){
				ValidationManager.removeError($("raceCodes"))
				ValidationManager.showError($("raceCodes"), "Missing Field")	
				return false;
			}
			if(flag){
				new Ajax.Updater('temp','../personAndOrganization/participant/createParticipant', {method:'post', postBody:Form.serialize('createSubForm'),onSuccess:handlerFunc, onFailure:handlerFail});
			}
			return false;
		}else if(formElement.id == 'command' && !flag){
			errorBag="";
			if($('studySiteStudyVersionId').value==null || $('studySiteStudyVersionId').value==""){
				errorBag = errorBag + ("<ul class='errors'><li>Missing study and/or study site</li></ul>");
			}
			if($('studySubject.participant').value==null || $('studySubject.participant').value==""){
				errorBag = errorBag + ("<ul class='errors'><li>Missing subject</li></ul>");
			}
			if($('epochElement').value==null || $('epochElement').value==""){
				errorBag = errorBag + ("<ul class='errors'><li>Missing epoch</li></ul>");
			}
			Element.update("topLevelErrorDisplay", errorBag);
			Element.show("topLevelErrorDisplay");
			$("topLevelErrorDisplay").focus();
		}
		else{
			//for all other forms(i.e. the main form on select_study_or_subject) we return true to ensure the html submit
			return flag;
		}		
	}
	
	function searchParticipant(){
			new Element.show('searchSubjectInd');
			new Ajax.Updater('subjectSearchResults','../registration/searchParticipant', {method:'post', postBody:Form.serialize('searchSubjectForm'), onSuccess:callbackSubject, onFailure:callbackSubjectFail});	
	}

	var confirmWin ;

	 function confirmationPopup(idParamString){
		 $('updateParticipantForm').action= "../personAndOrganization/participant/editParticipant?" + idParamString;
	    	confirmWin = new Window({className :"mac_os_x", title: "Confirm", 
				hideEffect:Element.hide, 
				zIndex:100, width:400, height:180 , minimizable:false, maximizable:false,
				showEffect:Element.show 
				}); 
			confirmWin.setContent($('confirmationMessage')) ;
			confirmWin.showCenter(true);
	 }

	function updateParticipant(){
		$('fromCreateRegistration').value="true";
		$('studySiteStudyVersionIdFromCreateReg').value = $('studySiteStudyVersionId').value ;
		$('studySearchType').value = $('searchType').value ;
		$('studySearchText').value = $('searchText').value ;
		$('searchedForStudy').value = $('searchedStudy').value ;
		$('updateParticipantForm').submit();
    	}
</script>

<form action="../personAndOrganization/participant/editParticipant" method="post" id="updateParticipantForm" name="updateParticipantForm">
	 <input type="hidden" id="fromCreateRegistration" name="fromCreateRegistration" value="true"/>
	 <input type="hidden" id="studySiteStudyVersionIdFromCreateReg" name="studySiteStudyVersionIdFromCreateReg" value=""/>
	 <input type="hidden" id="studySearchType" name="studySearchType" value=""/>
	 <input type="hidden" id="studySearchText" name="studySearchText" value=""/>
	 <input type="hidden" name="searchedForStudy" id="searchedForStudy" value="false">
	 <input type="hidden" name="_target0" id="_target" value="0"/>
</form>

<tags:minimizablePanelBox title="Select Subject" boxId="SubjectBox">
    <!-- subTabbedflow-->
    	<a href="javascript:moveToSearchSubject()" id="searchSubject_btn" class="fifthlevelTab-current">
    		<span id="searchSubjectSpan"><img src="<tags:imageUrl name="icons/search.png"/>" alt="" /> Search for Subject</span>
    	</a>
    	<c3pr:checkprivilege hasPrivileges="UI_SUBJECT_CREATE">
	    	<a href="javascript:moveToCreateSubject()" id="createSubject_btn" class="fifthlevelTab">
	    		<span><img src="<tags:imageUrl name="icons/searchParticipantController_icon.png"/>" alt="" />Create Subject</span>
	    	</a>
	    </c3pr:checkprivilege>
    <br/>
    <!-- start of search subject div-->
    <div id="searchSubjectDiv">
        <div style="border:2px solid #AC8139; padding-top:10px; padding-bottom:10px; margin-top:4px; background-color:Beige;">
            <form id="searchSubjectForm" action="" method="post">
                <input type="hidden" name="async" id="async" value="async">
                <div class="content">
                    <div class="row">
                        <div class="label">
                            <fmt:message key="registration.searchSubjectsBy"/>
                        </div>
                        <div class="value">
                            <select id="searchType" name="searchType">
                                <c:forEach items="${searchTypeRefDataPrt}" var="searchTypePrt">
                                    <option value="${searchTypePrt.code}">${searchTypePrt.desc}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="label">
                            <span class="label"><fmt:message key="c3pr.common.searchCriteria"/></span>
                        </div>
                        <div class="value">
                            <input id="searchSubjectText" name="searchText" type="text" value="" size="25" class="value" /><tags:button icon="search" type="button" value="Search" size="small" color="blue" onclick="searchParticipant()"/>&nbsp;<img id="searchSubjectInd" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="absmiddle">
                            <script>
                                new Element.hide('searchSubjectInd');
                            </script>
                        </div>
                    </div>
                </div>
                <!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
                add the mapping to the excludes section of decorators.xml -->
                <div id="subjectSearchResults" style="display:none;">
                </div>
            </form>
        </div>
    </div>
    <!-- end of search subject div-->
    <!--start of create subject div-->
    <div id="createSubjectDiv"  style="display:none;">
        <div style="border:2px solid #AC8139; padding-top:10px; padding-bottom:10px; margin-top:4px; background-color:Beige;">
            <div id="createSubjectDetailsDiv">
                <form id="createSubForm" name="createSubForm">
                    <input type="hidden" name="validate" id="validate" value="true"/><input type="hidden" name="_finish" id="_action" value=""><input type="hidden" name="_page" value="0"><input type="hidden" name="async" id="async" value="async">
                    <div class="division " id="single-fields">
                        <div class="content">
                            <div class="leftpanel">
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="c3pr.common.firstName"/>
                                            </div>
                                            <div class="value">
                                                <input id="firstName" name="participant.firstName" type="text" value="" class="required validate-notEmpty"/><span class="red">&nbsp;&nbsp;&nbsp;</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="c3pr.common.lastName"/>
                                            </div>
                                            <div class="value">
                                                <input id="lastName" name="participant.lastName" type="text" value="" class="required validate-notEmpty"/><span class="red">&nbsp;&nbsp;&nbsp;</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <fmt:message key="c3pr.common.middleName"/>
                                            </div>
                                            <div class="value">
                                                <input id="middleName" name="participant.middleName" type="text" value=""/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <fmt:message key="c3pr.common.maidenName"/>
                                            </div>
                                            <div class="value">
                                                <input id="maidenName" name="participant.maidenName" type="text" value=""/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="participant.gender"/>
                                            </div>
                                            <div class="value">
                                                <select id="administrativeGenderCode" name="participant.administrativeGenderCode" class="required validate-notEmpty">
                                                    <option value="">Please select...</option>
                                                    <c:forEach items="${administrativeGenderCode}" var="administrativeGenderCode" varStatus="loop">
                                                        <c:if test="${!empty administrativeGenderCode.desc}">
                                                            <option value="${administrativeGenderCode.code}">${administrativeGenderCode.desc}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="participant.birthDate"/>
                                            </div>
                                            <div class="value">
                                                <input id="birthDate" name="participant.birthDate" type="text" value="" class="required validate-notEmpty$$DATE"/>&nbsp;(mm/dd/yyyy)&nbsp;&nbsp;<span class="red"></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="rightpanel">
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="participant.ethnicity"/>
                                            </div>
                                            <div class="value">
                                                <select id="ethnicGroupCode" name="participant.ethnicGroupCode" class="required validate-notEmpty">
                                                    <option value="">Please select...</option>
                                                    <c:forEach items="${ethnicGroupCode}" var="ethnicGroupCode" varStatus="loop">
                                                        <c:if test="${!empty ethnicGroupCode.desc}">
                                                            <option value="${ethnicGroupCode.code}">${ethnicGroupCode.desc}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>
                                                <tags:hoverHint keyProp="subject.ethnicGroupCode"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="participant.race"/>
                                            </div>
                                            <div class="value">
                                            	<c:forEach items="${raceCodes}" var="raceCode" varStatus="raceCodeStatus">
				                            		<input id="raceCodes${raceCodeStatus.index}" name="raceCodeHolderList[${raceCodeStatus.index}].raceCode" type="checkbox" value="${raceCode.name}"/> ${raceCode.displayName}
                                                	<br>
				                            	</c:forEach>
                                                <span id="raceCodes" style="display:inline"></span>
                                            </div>
                                        </div>
                                    </div>
									<!--start of adding identifiers-->
                            <br>
         <chrome:division title="Primary Identifier">
		<div class="leftpanel">
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.organization"/></div>
			<div class="value">
				<c:choose>
					<c:when test="${c3pr:hasAllSiteAccess('UI_SUBJECT_UPDATE')}">
						<input type="hidden" id="mrnOrganization-hidden" name="participant.organizationAssignedIdentifiers[0].healthcareSite" />
					<input id="mrnOrganization-input" size="36" type="text" name="abcxyz"
					 class="autocomplete required validate-notEmpty" />
					<tags:indicator id="mrnOrganization-indicator" />
					<div id="mrnOrganization-choices" class="autocomplete" style="display: none;"><tags:hoverHint keyProp="subject.MRN.organization"/></div>
					</c:when>
					<c:otherwise>
						<select name="participant.organizationAssignedIdentifiers[0].healthcareSite" class="required validate-notEmpty" style="width: 260px;">
							<tags:userOrgOptions preSelectedSiteId="${command.participant.organizationAssignedIdentifiers[0].healthcareSite.id}" privilege="UI_SUBJECT_UPDATE"/>
						</select>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></div>
			<div class="value">
				<input type="text" id="organizationAssignedIdentifiers[0].value" name="participant.organizationAssignedIdentifiers[0].value" size="30" maxlength="30" class="required validate-notEmpty" />
				<tags:hoverHint keyProp="subject.MRN.value"/>
				<input type="hidden" name="participant.organizationAssignedIdentifiers[0].primaryIndicator" id="organizationAssignedIdentifiers[0].primaryIndicator" value="true"/>
			</div>
		</div>
		</div>
		<div class="rightpanel">
			<div class="row">
				<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/></div>
				<div class="value">
					<select name="participant.organizationAssignedIdentifiers[0].type"  class="valueOk validate-notEmpty">
						<c:forEach var="identifierType" items="${orgIdentifiersTypeRefData}">
							<option value="${identifierType.code}">${identifierType.desc}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
	 		
</chrome:division>
	<chrome:division title="Familial Relationships">
			<table id="familyMembersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
				<tr id="hfamilyMember" <c:if test="${fn:length(command.studySubject.participant.relatedTo)==0}"> style="display:none"</c:if>>
					<th><tags:requiredIndicator /><fmt:message key="participant.subjectName"/></th>
					<th><tags:requiredIndicator /><fmt:message key="participant.familialRelationship.name"/></th>
					<th></th>
				</tr>
					<c:forEach items="${command.studySubject.participant.relatedTo}" varStatus="familyMemberStatus" var="familyMember">
						<c:set var="_identifier" value="(${command.studySubject.participant.relatedTo[familyMemberStatus.index].secondaryParticipant.primaryIdentifierValue})" />
						<c:set var="_name" value="${command.studySubject.participant.relatedTo[familyMemberStatus.index].secondaryParticipant.fullName}" />
						<tr
							id="familyMembersTable-${familyMemberStatus.index}">
							<td class="alt"><input type="hidden"
								id="familyMember${familyMemberStatus.index}-hidden"
								name="participant.relatedTo[${familyMemberStatus.index}].secondaryParticipant"
								value="${command.studySubject.participant.relatedTo[familyMemberStatus.index].secondaryParticipant.id}" />
								<input type="hidden"
								id="relation-category-familyMember${familyMemberStatus.index}-hidden"
								name="participant.relatedTo[${familyMemberStatus.index}].category" value="FAMILIAL" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="familyMember${familyMemberStatus.index}-input" size="38"
								value='<c:out value="${_name} ${_identifier}" />'/>
							<tags:indicator
								id="familyMember${familyMemberStatus.index}-indicator" /> 
							<div id="familyMember${familyMemberStatus.index}-choices"
								class="autocomplete"  style="display: none;"></div>
							</td>
							<td class="alt"><form:select
								path="participant.relatedTo[${familyMemberStatus.index}].name"
								cssClass="required validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${familialRelationshipNames}" itemLabel="value"
									itemValue="key" />
							</form:select></td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(familyMemberRowInserterProps,${familyMemberStatus.index},'${familyMember.id==null?'HC#':'ID#'}${familyMember.id==null?familyMember.hashCode:familyMember.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
			<div align="right">
			<tags:button type="button" color="blue" icon="add" value="Add Family Member" 
				onclick="$('hfamilyMember').show();javascript:RowManager.addRow(familyMemberRowInserterProps);" size="small"/>
			</div>
		</chrome:division>
         <chrome:division title="Organization Assigned Identifiers" minimize="true" divIdToBeMinimized="idSection">
			<div id="idSection" style="display:none;">		
				<table id="organizationIdentifiersTable" border="0"
					cellspacing="0" cellpadding="0" class="tablecontent">
					<tr id="hOrganizationAssignedIdentifier" <c:if test="${fn:length(command.studySubject.participant.organizationAssignedIdentifiers) < 2}">style="display:none;"</c:if>>
						<th><span
							class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.assigningAuthority"/></span><tags:hoverHint keyProp="identifier.organization"/></th>
						<th><span class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.identifierType"/>
						</span><tags:hoverHint keyProp="identifier.type"/></th>
						<th><span class=""><tags:requiredIndicator /><fmt:message key="c3pr.common.identifier"/></span><tags:hoverHint keyProp="identifier.value"/></th>
						<th><fmt:message key="c3pr.common.primaryIndicator"/><tags:hoverHint keyProp="study.healthcareSite.primaryIndicator"/></th>
						<th ></th>
					</tr>
					<c:forEach items="${command.studySubject.participant.organizationAssignedIdentifiers}" begin="1"
						varStatus="organizationStatus" var="orgId">
						<tr
							id="organizationIdentifiersTable-${organizationStatus.index}">
							<c:set var="_code" value="" />
							<c:set var="_name" value="" />
							<c:set var="_code" value="(${command.studySubject.participant.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.primaryIdentifier})" />
							<c:set var="_name" value="${command.studySubject.participant.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.name}" />
							<td class="alt"><input type="hidden"
								id="healthcareSite${organizationStatus.index}-hidden"
								name="studySubject.participant.organizationAssignedIdentifiers[${organizationStatus.index}].healthcareSite"
								value="${command.studySubject.participant.organizationAssignedIdentifiers[organizationStatus.index].healthcareSite.id}" />
							<input class="autocomplete validate-notEmpty" type="text"
								id="healthcareSite${organizationStatus.index}-input" size="50"
								value='<c:out value="${_name} ${_code}" />'/>
							<tags:indicator
								id="healthcareSite${organizationStatus.index}-indicator" /> 
							<div id="healthcareSite${organizationStatus.index}-choices"
								class="autocomplete"  style="display: none;"></div>
							</td>
							<td class="alt"><form:select
								path="studySubject.participant.organizationAssignedIdentifiers[${organizationStatus.index}].type"
								cssClass="required validate-notEmpty">
								<option value="">Please Select</option>
								<form:options items="${orgIdentifiersTypeRefData}" itemLabel="desc"
									itemValue="code" />
							</form:select></td>
							<td class="alt"><form:input
								path="studySubject.participant.organizationAssignedIdentifiers[${organizationStatus.index}].value"
								cssClass="required validate-notEmpty" /></td>
							<td>
								<form:hidden path="studySubject.participant.organizationAssignedIdentifiers[${organizationStatus.index}].primaryIndicator" id="identifier-org-${organizationStatus.index}-hidden"/>
								<input type="radio" class="identifierRadios" id="identifier-org-${organizationStatus.index}" onclick="manageIdentifierRadio(this);"
								<c:if test="${orgId.primaryIndicator}"> checked </c:if>/>
							</td>
							<td class="alt"><a
								href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,${organizationStatus.index},'${orgId.id==null?'HC#':'ID#'}${orgId.id==null?orgId.hashCode:orgId.id}');"><img
								src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<tags:button type="button" color="blue" icon="add" value="Add Identifier" onclick="$('hOrganizationAssignedIdentifier').show();javascript:RowManager.addRow(organizationIdentifierRowInserterProps);" size="small"/>
			</div>
		</chrome:division>

                            <p id="instructions"><a href="#" onclick="toggleAddressSection()">
                            <chrome:division title="Address & Contact Info" minimize="true" divIdToBeMinimized="addressSection">
                                <div id="addressSection" style="display:none;">
                                    <div class="division " id="single-fields">
                                        <table>
                                            <tr>
                                                <td>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.streetAddress"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="streetAddress" name="participant.address.streetAddress" type="text" value="" size="45"/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.city"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="city" name="participant.address.city" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <span class="data"><fmt:message key="c3pr.common.state"/></span>
                                                        </div>
                                                        <div class="value">
                                                            <input id="stateCode" name="participant.address.stateCode" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.zip"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="postalCode" name="participant.address.postalCode" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.country"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="countryCode" name="participant.address.countryCode" type="text" value=""/>
                                                        </div>
                                                    </div>
													<div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.phone"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="phone" name="participant.phone" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.fax"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="fax" name="participant.fax" type="text" value=""/>
                                                        </div>
                                                    </div>
													<div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.email"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="email" name="participant.email" type="text" value="" size="30"/>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </chrome:division><!--end of div id="addressSection"-->
                            <div align="right">
                            	<tags:button type="button" color="green" icon="subject" value="Create Subject" onclick="document.createSubForm.submit();" />
                                <!--<input type="button" class="tab0" value="Create this Subject" onclick="document.createSubForm.submit();"/>-->
                            </div>
                        </div>
                    </div>
                </form><!--Identifiers section thats supposed to be outside the form--><tags:identifiersDummyRows identifiersTypes="${identifiersTypeRefData}"/><!--Identifiers section thats supposed to be outside the form-->
            </div><!--end of createSubjectDetailsDiv -->
        </div>
        <div id="succesfulCreateDiv" style="display:none;">
            <h3>
                <font color="green">
                    <div id="subject-message">
                    </div>
                </font>
            </h3>
        </div>
    </div>
	<div id="errorsOpenDiv" style="display:none">
		<div class="value" align="left">
			<font size="2" face="Verdana" color="red">
				Subject with this MRN already exists for the same Organization.
			</font>
		</div>
		
		<br>
	</div>
	<div id="dummy-organizationIdentifierRow" style="display:none;">
<table>
	<tr>
		<td class="alt"><input type="hidden"
			id="healthcareSitePAGE.ROW.INDEX-hidden"
			name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="healthcareSitePAGE.ROW.INDEX-input" size="50"
			value="${command.studySubject.participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}" />
		 <tags:indicator
			id="healthcareSitePAGE.ROW.INDEX-indicator" />
		<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"  style="display: none;"></div>
		</td>

		<td class="alt"><select
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${orgIdentifiersTypeRefData}" var="id">
				<option value="${id.code}">${id.desc}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><input
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value"
			name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
			onfocus="javascript:clearField(this)" class="required validate-notEmpty" /></td>
		<td>
			<input type="radio"	id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" class="identifierRadios"
			name="organizationAssignedIdentifiers.primaryIndicator-PAGE.ROW.INDEX" onclick="manageIdentifierRadio(this);"/>
			<input type="hidden" name="participant.organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" 
			id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator-hidden"/>
		</td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>
<div id="dummy-familyMember" style="display:none;">
<table>
	<tr>
		<td class="alt"> <input type="hidden"
			id="relation-category-familyMemberPAGE.ROW.INDEX-hidden"
			name="participant.relatedTo[PAGE.ROW.INDEX].category" value="FAMILIAL" />
			<input type="hidden" id="familyMemberPAGE.ROW.INDEX-hidden"
			name="participant.relatedTo[PAGE.ROW.INDEX].secondaryParticipant" />
		<input class="autocomplete validate-notEmpty" type="text"
			id="familyMemberPAGE.ROW.INDEX-input" size="38"
			value="${command.studySubject.participant.relatedTo[PAGE.ROW.INDEX].secondaryParticipant.fullName}" />
		 <tags:indicator
			id="familyMemberPAGE.ROW.INDEX-indicator" />
		<div id="familyMemberPAGE.ROW.INDEX-choices" class="autocomplete"  style="display: none;"></div>
		</td>

		<td class="alt"><select
			id="familialRelationships[PAGE.ROW.INDEX].name"
			name="participant.relatedTo[PAGE.ROW.INDEX].name"
			class="required validate-notEmpty">
			<option value="">Please Select</option>
			<c:forEach items="${familialRelationshipNames}" var="id">
				<option value="${id.key}">${id.value}</option>
			</c:forEach>
		</select></td>
		<td class="alt"><a
			href="javascript:RowManager.deleteRow(familyMemberRowInserterProps,PAGE.ROW.INDEX,-1);"><img
			src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>


<div style="display:none;">
<div id="confirmationMessage" style="padding: 15px;">
	<img src="<tags:imageUrl name="error-yellow.png" />" alt="" style="vertical-align:middle;" /> <fmt:message key="REGISTRATION.CREATE.REDIRECT.EDITSUBJECT.WARNING"/>
	<div id="actionButtons">
		<div class="flow-buttons">
	   	<span class="next">
	   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="confirmWin.close();" />
			<tags:button type="button" color="green" icon="save" onclick="updateParticipant();" value="Continue" />
		</span>
		</div>
	</div>
</div>
</div>
    <!--end of create subject div-->
</tags:minimizablePanelBox>
