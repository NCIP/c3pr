<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="studyTags" tagdir="/WEB-INF/tags/study" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<tags:stylesheetLink name="subtabbedflow"/>
<script>
		function minimizeSubjectBox(msg){
		PanelCombo('SubjectBox');
		element=$$("#Subject .header h2")[0];
		new Element.update(element,msg);
		new Effect.Pulsate(element);
	}
	
	function toggleAddressSection(){
		var el = document.getElementById('addressSection');
		if ( el.style.display != 'none' ) {
			new Element.hide(el);
		}
		else {
			new Effect.Grow(el);
		}
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
  		document.getElementById('searchSubjectSpan').className="current";
  		document.getElementById('createSubjectSpan').className="tab";
  	
  		document.getElementById('searchSubjectDiv').style.display="";
  		document.getElementById('createSubjectDiv').style.display="none";
  	}
  	
  	function moveToCreateSubject(){
  	  	document.getElementById('searchSubjectSpan').className="tab";
  		document.getElementById('createSubjectSpan').className="current";
  		
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
			$('participant').value = id;	
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
	
	var handlerFail = function(t) {
		alert("handlerFail: Subject Creation Failed. Please Try Again");
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
		$("participant").value = id;
		minimizeSubjectBox("Selected Subject: " +name+ " (" + identifier + ")");	
	}  	
	
	ValidationManager.submitPostProcess= function(formElement, flag){
		if(formElement.name == 'createSubForm'){
			//for the create subject form we make an ajax submit and return false to avoid the html submit
			if(flag){
				new Ajax.Updater('temp','../participant/createParticipant', {method:'post', postBody:Form.serialize('createSubForm'),onSuccess:handlerFunc, onFailure:handlerFail});
			}
			return false;
		}else{
			//for all other forms(i.e. the main form on select_study_or_subject) we return true to ensure the html submit
			return flag;
		}		
	}
	
	function searchStudy(){

		if($("searchSubjectText").value != null && $("searchSubjectText").value != ""){
			new Element.show('searchSubjectInd');
			new Ajax.Updater('subjectSearchResults','../registration/searchParticipant', {method:'post', postBody:Form.serialize('searchSubjectForm'), onSuccess:callbackSubject, onFailure:callbackSubjectFail});	
		} else {
			ValidationManager.showError($("searchSubjectText"), "required");
		}		
	}
</script>

<tags:minimizablePanelBox title="Select A Subject" boxId="SubjectBox">
	<!-- subTabbedflow-->
	<table width="100%" border="0" cellspacing="0" cellpadding="0"	class="subFlowTabs">
		<tr>
			<td width="100%" id="tabDisplay">
			<a href="#" style="font-size:100%"><span id="searchSubjectSpan" class="current" onclick="moveToSearchSubject()">
			<img src="<tags:imageUrl name="subTabWhiteL.gif"/>" width="3" height="16" align="absmiddle" />			
			<b>Search Subject</b><img src="<tags:imageUrl name="subTabWhiteR.gif"/>" width="3" height="16" align="absmiddle" />
			</span></a>
			<a href="#"><span id="createSubjectSpan" class="tab" onclick="moveToCreateSubject()">
			<img src="<tags:imageUrl name="subTabGrayL.gif"/>" width="3" height="16" align="absmiddle" />
			<b>Create Subject</b><img src="<tags:imageUrl name="subTabGrayR.gif"/>" width="3" height="16" align="absmiddle" />
			</span></a>
			</td>
			<td><img src="<tags:imageUrl name="subTabSpacer.gif"/>" width="7" height="1" /></td>
		</tr>
	</table>
	<br/>

	<!-- start of search subject div-->	
	<div id="searchSubjectDiv">
	<form id="searchSubjectForm" action="" method="post">
	<input type="hidden" name="async" id="async" value="async">
		<div class="content">
            <div class="row">
                <div class="label">Search Subjects By:</div>
                <div class="value">
                	<select id="searchType" name="searchType">
						<option value="">--Please Select--</option>
						<c:forEach items="${searchTypeRefDataPrt}" var="searchTypePrt">
						 	<option value="${searchTypePrt.code}">${searchTypePrt.desc}</option>
						</c:forEach>
					</select>
                </div>		
            </div>
            <div class="row">
                <div class="label"><span class="required-indicator">Search Criteria:</span></div>
                <div class="value">
                	<input id="searchSubjectText" name="searchText" type="text" value="" size="25" class="validate-notEmpty" />
                	<input type="button" value="Search" 
                	onclick="searchStudy()"/>
                	<img id="searchSubjectInd" src="<tags:imageUrl name="indicator.white.gif"/>"
								alt="Indicator" align="absmiddle">
					<script>new Element.hide('searchSubjectInd');</script>
                </div>	
            </div>
        </div>        
    	<hr align="left" width="95%" />
    	<!--In order to ensure that the decorator is not applied to the dynamically (AJAX)inserted jsp
    		add the mapping to the excludes section of decorators.xml -->
    	<div id="subjectSearchResults" style="display:none;">
    	</div>
    </form>
	</div>    
	<!-- end of search subject div-->	
	
	<script>
	    var mrnAutocompleterProps = {
            basename: "mrnOrganization",
            populator: function(autocompleter, text) {
                 ParticipantAjaxFacade.matchHealthcareSites( text,function(values) {
                    autocompleter.setChoices(values)
                })
            },
            valueSelector: function(obj) {
                return obj.name
            },
             afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=inputElement.id.split("-")[0]+"-hidden";
	    							$(hiddenField).value=selectedChoice.id;
			}
        };
        AutocompleterManager.addAutocompleter(mrnAutocompleterProps);
	</script>
	
	<!--start of create subject div-->
	<div id="createSubjectDiv" style="display:none;">
	<div id="createSubjectDetailsDiv">		
	<form id="createSubForm" name="createSubForm">
	<input type="hidden" name="validate" id="validate" value="true"/>
	<input type="hidden" name="_finish" id="_action" value="">
	<input type="hidden" name="async" id="async" value="async">
	<div class="division " id="single-fields" >
    <div class="content">
		<table width="80%" border="0" cellspacing="0" cellpadding="0" id="details">
			<tr>
				<td width="40%" valign="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="1"
					id="table1">
					<tr>
						<td align="right"><span class="required-indicator"><b>First Name: &nbsp;</b></span></td>
						<td align="left"><input id="firstName" name="firstName" type="text" value="" class="validate-notEmpty"/>
						<span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Last Name:</b>&nbsp;</span></td>
						<td align="left"><input id="lastName" name="lastName" type="text" value="" class="validate-notEmpty"/>
						<span class="red">&nbsp;&nbsp;&nbsp;</span><em></em></td>
					</tr>
					<tr>
						<td align="right"><b>Middle Name:</b>&nbsp;</td>
						<td align="left"><input id="middleName" name="middleName" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td align="right"><em></em> <b>Maiden Name:</b>&nbsp;</td>
						<td align="left"><input id="maidenName" name="maidenName" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
					</tr>					
				</table>
				</td>
				<td width="40%" valign="top">
				<table width="100%" border="0" cellspacing="1" cellpadding="1" id="table1">
										<tr>
					<td align="right"><span class="required-indicator"><b>Gender:</b></span>
						&nbsp;</td>
						<td align="left">
							<select id="administrativeGenderCode" name="administrativeGenderCode" class="validate-notEmpty">
									<option value="">--Please Select--</option>
								<c:forEach items="${administrativeGenderCode}" var="administrativeGenderCode" varStatus="loop">
								<c:if test="${!empty administrativeGenderCode.desc}">
									<option value="${administrativeGenderCode.code}">${administrativeGenderCode.desc}</option>
								</c:if>									
								</c:forEach>
							</select>		
						</td>
					</tr>
					<tr>
						<td align="right"><span class="required-indicator"><b>Birth Date:</b></span>&nbsp;</td>
						<td><input id="birthDate" name="birthDate" type="text" value="" class="validate-notEmpty"/>&nbsp;(mm/dd/yyyy)&nbsp;&nbsp;<span
							class="red"><em></em></span></td>
					</tr>
					<tr>
						<td align="right"><em></em><b>Ethnicity:</b> &nbsp;</td>
						<td align="left">							
							<select id="ethnicGroupCode" name="ethnicGroupCode">
									<option value="">--Please Select--</option>
								<c:forEach items="${ethnicGroupCode}" var="ethnicGroupCode" varStatus="loop">
								<c:if test="${!empty ethnicGroupCode.desc}">
									<option value="${ethnicGroupCode.code}">${ethnicGroupCode.desc}</option>
								</c:if>									
								</c:forEach>
							</select>						
						</td>
					</tr>
					<tr>
						<td align="right"><em></em><b>Race(s):</b> &nbsp;</td>
						<td align="left">
							<select id="raceCode" name="raceCode">
									<option value="">--Please Select--</option>
								<c:forEach items="${raceCode}" var="raceCode" varStatus="loop">
								<c:if test="${!empty raceCode.desc}">
									<option value="${raceCode.code}">${raceCode.desc}</option>
								</c:if>									
								</c:forEach>
							</select>		
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		
		<!--start of adding identifiers-->		
		<hr align="left" width="95%">
		<tags:identifiers identifiersTypes="${identifiersTypeRefData}" displaySys="false" />
		<!--end of adding identifiers-->
		
		<!--start of address section-->
		<p id="instructions"><a href="#" onclick="toggleAddressSection()">Address & Contact Info</a></p>
		<div id="addressSection" style="display:none;">
		<div class="division " id="single-fields" >
		<div class="content">
		<table width="80%" border="0" cellspacing="1" cellpadding="1" id="table1">
			<tr>
				<td width="150" align="right"><em></em> <b>Street Address:</b>&nbsp;</td>
				<td width align="left"><input id="streetAddress" name="streetAddress" type="text" value="" size="58"/>&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td width="150" align="right"><em></em> <b>City:</b>&nbsp;</td>
				<td align="left"><input id="city" name="city" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td width="150" align="right"><span class="data"><em></em> <b>State:</b>&nbsp;</span></td>
				<td align="left"><input id="stateCode" name="stateCode" type="text" value=""/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b>Zip:</b>&nbsp;<input id="postalCode" name="postalCode" type="text" value=""/></td>
			</tr>
			<tr>
				<td width="150" align="right"><em></em><em></em> <b>Country:</b>&nbsp;</td>
				<td align="left"><input id="countryCode" name="countryCode" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>

		<table border="0" width="60%" cellspacing="1" cellpadding="1">
			<tr>
				<td width="150" align="right"><em></em><em></em> <b>Email:</b>&nbsp;</td>
				<td align="left"><input id="email" name="email" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td width="150" align="right"><em></em><em></em> <b>Phone:</b>&nbsp;</td>
				<td align="left"><input id="phone" name="phone" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td width="150" align="right"><em></em><em></em> <b>Fax:</b>&nbsp;</td>
				<td align="left"><input id="fax" name="fax" type="text" value=""/>&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
		</div>
		</div>
		</div> 
		<!--end of div id="addressSection"-->
		
		<div align="center">
		<input type="button" class="tab0" value="Save" onclick="document.createSubForm.submit();"/>
		</div>
    </div>
	</div>	
	</form>
	
	<!--Identifiers section thats supposed to be outside the form-->
	<tags:identifiersDummyRows identifiersTypes="${identifiersTypeRefData}"/>
   	<!--Identifiers section thats supposed to be outside the form-->
	</div>
	<!--end of createSubjectDetailsDiv -->
	

	<div id="succesfulCreateDiv" style="display:none;">
		<h3><font color="green"><div id="subject-message"></div></font></h3>
	</div>

	</div>
	<!--end of create subject div-->
</tags:minimizablePanelBox>