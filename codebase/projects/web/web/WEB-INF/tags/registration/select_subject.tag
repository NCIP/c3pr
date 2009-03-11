<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
		function minimizeSubjectBox(msg){
		PanelCombo('SubjectBox');
		displaySubjectMessage(msg,true);
	}
	
	function displaySubjectMessage(message,pulsateFlag){
		element=$$("#Subject .header h2")[0];
		new Element.update(element,message);
		pulsateFlag?(!is_ie?new Effect.Pulsate(element):null):null;
	}
	
  	function clearField(field) {
    	field.value = "";
    }
        
//  	var instanceRowInserterProps = {
//       add_row_division_id: "mytable", 	        		/* this id belongs to element where the row would be appended to */
//        skeleton_row_division_id: "dummy-row",
//        initialIndex: 1,    /* this is the initial count of the rows when the page is loaded  */
//        path: "identifiers",                                /* this is the path of the collection that holds the rows  */
//    };
//   RowManager.addRowInseter(instanceRowInserterProps);
  	
  	function moveToSearchSubject(){
  		document.getElementById('searchSubject_btn').className="current";
  		document.getElementById('createSubject_btn').className="fifthlevelTab";
  	
  		document.getElementById('searchSubjectDiv').style.display="";
  		document.getElementById('createSubjectDiv').style.display="none";
  	}
  	
  	function moveToCreateSubject(){
  	  	document.getElementById('searchSubject_btn').className="fifthlevelTab";
  		document.getElementById('createSubject_btn').className="current";
  		
  		document.getElementById('searchSubjectDiv').style.display="none";
  		document.getElementById('createSubjectDiv').style.display="";
  		<!--sending a blank request to createParticipantController which bombs the first time-->
  		<!--coz the command isnt instantiated. Workaround is to send a dummy request in advance and get-->
  		<!--it to instantiate it in advance so the createSubject wont fail the first time-->
  		new Ajax.Request('../participant/createParticipant', {method:'get', asynchronous:true});
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
	
	/* handlers for searchSubject flwo */
	  	
	function postProcessSubjectSelection(id, name, identifier){
		$('studySubject.participant').value = id;
		minimizeSubjectBox("Selected Subject: " +name+ " (" + identifier + ")");	
	}  	
	
	ValidationManager.submitPostProcess= function(formElement, flag){
		
		if(formElement.name == 'createSubForm'){
			//for the create subject form we make an ajax submit and return false to avoid the html submit
			var raceCodeFlag=false;
		for(i=1 ; i<8 ; i++){
			if($('raceCodes'+i).checked){
				raceCodeFlag=true;
				break;
			}
		}
		if(!raceCodeFlag){
			ValidationManager.removeError($("raceCodes"))
			ValidationManager.showError($("raceCodes"), "required")	
			return false;
		}
		if(flag){
			new Ajax.Updater('temp','../personAndOrganization/participant/createParticipant', {method:'post', postBody:Form.serialize('createSubForm'),onSuccess:handlerFunc, onFailure:handlerFail});
		}
		return false;
		}else{
			//for all other forms(i.e. the main form on select_study_or_subject) we return true to ensure the html submit
			return flag;
		}		
	}
	
	function searchParticipant(){
			new Element.show('searchSubjectInd');
			new Ajax.Updater('subjectSearchResults','../registration/searchParticipant', {method:'post', postBody:Form.serialize('searchSubjectForm'), onSuccess:callbackSubject, onFailure:callbackSubjectFail});	
	}
</script>
<tags:minimizablePanelBox title="Select Subject" boxId="SubjectBox">
    <!-- subTabbedflow--><a href="javascript:moveToCreateSubject()" id="createSubject_btn" class="current"><span><img src="<tags:imageUrl name="icons/searchParticipantController_icon.png"/>" alt="" />Create Subject</span></a><a href="javascript:moveToSearchSubject()" id="searchSubject_btn" class="fifthlevelTab"><span id="searchSubjectSpan"><img src="<tags:imageUrl name="icons/search.png"/>" alt="" /> Search for Subject</span></a>
    <br/>
    <!-- start of search subject div-->
    <div id="searchSubjectDiv" style="display:none;">
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
    <script>
        var mrnAutocompleterProps = {
            basename: "mrnOrganization",
            populator: function(autocompleter, text){
                ParticipantAjaxFacade.matchHealthcareSites(text, function(values){
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj){
                return (obj.name + " (" + obj.nciInstituteCode + ")")
            },
            afterUpdateElement: function(inputElement, selectedElement, selectedChoice){
                hiddenField = inputElement.id.split("-")[0] + "-hidden";
                $(hiddenField).value = selectedChoice.id;
            }
        };
        AutocompleterManager.addAutocompleter(mrnAutocompleterProps);
    </script>
    <!--start of create subject div-->
    <div id="createSubjectDiv" style="">
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
                                                <input id="firstName" name="firstName" type="text" value="" class="validate-notEmpty"/><span class="red">&nbsp;&nbsp;&nbsp;</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="c3pr.common.lastName"/>
                                            </div>
                                            <div class="value">
                                                <input id="lastName" name="lastName" type="text" value="" class="validate-notEmpty"/><span class="red">&nbsp;&nbsp;&nbsp;</span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <fmt:message key="c3pr.common.middleName"/>
                                            </div>
                                            <div class="value">
                                                <input id="middleName" name="middleName" type="text" value=""/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <fmt:message key="c3pr.common.maidenName"/>
                                            </div>
                                            <div class="value">
                                                <input id="maidenName" name="maidenName" type="text" value=""/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="participant.gender"/>
                                            </div>
                                            <div class="value">
                                                <select id="administrativeGenderCode" name="administrativeGenderCode" class="validate-notEmpty">
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
                                                <input id="birthDate" name="birthDate" type="text" value="" class="validate-notEmpty&&DATE"/>&nbsp;(mm/dd/yyyy)&nbsp;&nbsp;<span class="red"></span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="rightpanel">
                                        <div class="row">
                                            <div class="label">
                                                <tags:requiredIndicator/><fmt:message key="participant.ethnicity"/>
                                            </div>
                                            <div class="value">
                                                <select id="ethnicGroupCode" name="ethnicGroupCode" class="validate-notEmpty">
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
                                                <input id="raceCodes1" name="raceCodes" type="checkbox" value="Asian"/> Asian
                                                <br>
                                                <input id="raceCodes2" name="raceCodes" type="checkbox" value="Black_or_African_American"/> Black or African American
                                                <br>
                                                <input id="raceCodes3" name="raceCodes" type="checkbox" value="White"/> White
                                                <br>
                                                <input id="raceCodes4" name="raceCodes" type="checkbox" value="American_Indian_or_Alaska_Native"/> American Indian or Alaska Native
                                                <br>
                                                <input id="raceCodes5" name="raceCodes" type="checkbox" value="Native_Hawaiian_or_Pacific_Islander"/> Native Hawaiian or Pacific Islander
                                                <br>
                                                <input id="raceCodes6" name="raceCodes" type="checkbox" value="Not_Reported"/> Not reported
                                                <br>
                                                <input id="raceCodes7" name="raceCodes" type="checkbox" value="Unknown"/> Unknown
                                            </div>
                                        </div>
                                    </div>
									<!--start of adding identifiers-->
                            <br>
                            <tags:identifiers identifiersTypes="${identifiersTypeRefData}" displaySys="false" /><!--end of adding identifiers--><!--start of address section
                            <p id="instructions"><a href="#" onclick="toggleAddressSection()">Address & Contact Info</a></p>-->
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
                                                            <input id="streetAddress" name="streetAddress" type="text" value="" size="45"/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.city"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="city" name="city" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <span class="data"><fmt:message key="c3pr.common.state"/></span>
                                                        </div>
                                                        <div class="value">
                                                            <input id="stateCode" name="stateCode" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.zip"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="postalCode" name="postalCode" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.country"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="countryCode" name="countryCode" type="text" value=""/>
                                                        </div>
                                                    </div>
													<div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.phone"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="phone" name="phone" type="text" value=""/>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.fax"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="fax" name="fax" type="text" value=""/>
                                                        </div>
                                                    </div>
													<div class="row">
                                                        <div class="label">
                                                            <fmt:message key="c3pr.common.email"/>
                                                        </div>
                                                        <div class="value">
                                                            <input id="email" name="email" type="text" value="" size="30"/>
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
    <!--end of create subject div-->
</tags:minimizablePanelBox>
