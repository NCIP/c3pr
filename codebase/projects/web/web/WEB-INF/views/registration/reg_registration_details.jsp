<%@ include file="taglibs.jsp"%>
<html>
<head>
    <title><registrationTags:htmlTitle registration="${command.studySubject}" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<tags:dwrJavascriptLink objects="anatomicDiseaseSite" />
<c:set var="hasInv" value="${fn:length(command.studySubject.studySite.activeStudyInvestigators)>0}"/>
<script>
function manageField(box){
	if(box.value=='' && box.selectedIndex!=0){
		new Effect.Appear('otherDisease');
	}else{
		$('otherDisease').style.display="none";
	}
}

function managePhysicianField(box){
	if(box.value=='' && box.selectedIndex!=0){
		new Effect.Appear('otherTreatingPhysician');
	}else{
		$('otherTreatingPhysician').style.display="none";
	}
}

var ajaxDiseaseSite="";
var diseaseSiteAutocompleterProps = {
	basename: "diseaseSite",
	isFreeTextAllowed: true,
    populator: function(autocompleter, text) {
			        anatomicDiseaseSite.matchDiseaseSites(text, function(values) {
																    	autocompleter.setChoices(values)
																	   })
			    },
    valueSelector: function(obj) {
						return obj.name
			    	},
    afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
    								hiddenField=diseaseSiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
	    							ajaxDiseaseSite=selectedChoice.name;
								}
}
AutocompleterManager.addAutocompleter(diseaseSiteAutocompleterProps);
ValidationManager.submitPostProcess=function(formElement, flag){
							if(formElement.id!='command' || !flag)
								return flag;
							if(${hasInv} && $("treatingPhysician").value=="" && $("studySubject.otherTreatingPhysician").value==""){
								ValidationManager.Error($("studySubject.otherTreatingPhysician"));
								ValidationManager.showError($("studySubject.otherTreatingPhysician"),"required");
								return false;
							}
							if($("studyDiseaseSelect").value!=""){
								$('otherDisease').value="";
							}
							return flag;
						}

var CategorySelector = Class.create();
Object.extend(CategorySelector.prototype, {
initialize: function() {
	this.win = null;
    this.termList = new Array();
},

showWindow:function(wUrl, wTitle, wWidth, wHeight){
	win = new Window({
        className:"alphacube",
        destroyOnClose:true,
        title:wTitle,
        width:wWidth,
        height:wHeight,
        recenterAuto:true,
        resizable: false,
        minimizable : false,
        maximizable: false,
    });
	this.win = win;
	win.setContent('chooseCategory');
    win.showCenter(true);
},

finishMultiTermsSelection:function() {
	hideDiseaseIndicator();																																
    var selectedTerm = $('chk');
      if (selectedTerm.checked) {
    	  $('diseaseSite-hidden').value= selectedTerm.value;
    	  $('diseaseSite-input').value= selectedTerm.name;
      }
    Windows.close(this.win.getId());
    catSel.termList = new Array();
    $('disease-subcategories').innerHTML = "";
    $('disease-level3Sites').innerHTML = "";
    $('disease-level4Sites').innerHTML = "";
    $('disease-added-terms').innerHTML = "";
    var selectedCategories = $$('a.disease-category-selected');
    selectedCategories.each(function(el) {
        el.ClassName("disease-category-selected");
    });

    var selectedCategories = $$('li.li-category-selected');
    selectedCategories.each(function(el) {
        el.ClassName("li-category-selected");
    });
    return;
},

addLevel4Site: function(ulID, termID, termText, title) {
	$('disease-added-terms').innerHTML = "";
	var selecedSites = $$('a.l3-site-selected');
	selecedSites.each(function(e1){
		e1.ClassName("l3-site-selected");
	});

	$("subcategoryli_" + termID).addClassName("l3-site-selected");

	//$("subcategoryli_" + ilID).addClassName("li-subcategory-selected");
	
//	$("liTerm" + termID).addClassName("term-selected");
    ul = document.getElementById(ulID);

    checkbox = document.createElement("input");
    checkbox.type = 'checkbox';
    checkbox.name = termText;
    checkbox.defaultChecked = true;
    checkbox.value = termID;
    checkbox.id = "chkID" + termID;
    checkbox.setAttribute("id", "chk");

    a = document.createElement("a");
    a.appendChild(document.createTextNode(termText));

    a.id = "addedTerm" + termID;
    a.setAttribute("id", "addedTerm" + termID);

    a.setAttribute("title", title);
    a.title = title;

    li = document.createElement("li");
    li.appendChild(checkbox);
    li.appendChild(a);
    ul.appendChild(li)

    $("addedTerm" + termID).addClassName("disease-added-terms");
    $("chk").addClassName("AddedTermXYZ");

},

showLevel2DiseaseSites: function(id){
    var selectedCategories = $$('a.disease-category-selected');
    selectedCategories.each(function(el) {
        el.ClassName("disease-category-selected");
    });

    var selectedCategories = $$('li.li-category-selected');
    selectedCategories.each(function(el) {
        el.ClassName("li-category-selected");
    });

    $("category_" + id).addClassName("disease-category-selected");
    $("li_" + id).addClassName("li-category-selected");
    $('disease-subcategories').innerHTML = "";
    $('disease-level3Sites').innerHTML = "";
    $('disease-level4Sites').innerHTML = "";

    catId = id;
    anatomicDiseaseSite.getLevel2DiseaseSiteCategories(catId, function(childCategories) {
        childCategories.each(function(childCategory) {
          var childCategoryName = (childCategory.name.length > 18 ? childCategory.code+" " +childCategory.name.substring(0, 18) + "..." : childCategory.code+" " +childCategory.name);
          catSel.showLevel2DiseaseSitesDetails("disease-subcategories", childCategory.id, childCategoryName, childCategory.name);
        })
    });
    return;
},

showLevel2DiseaseSitesDetails: function(ulID, ilID, ilText, title){
	ul = document.getElementById(ulID);
    a = document.createElement("a");
    a.appendChild(document.createTextNode(ilText));
    a.setAttribute("onclick", "catSel.showLevel3DiseaseSites('disease-level3Sites', " + ilID + ", '" + ilText + "','" + title + "')");
    a.onclick = function() {
        eval("catSel.showLevel3DiseaseSites('disease-level3Sites', " + ilID + ", '" + ilText + "','" + title + "')");
    }
    a.setAttribute("id", "subcategory_" + ilID);
    a.id = "subcategory_" + ilID;

    a.setAttribute("title", title);
    a.title = title;

    li = document.createElement("li");
    li.setAttribute("id", "subcategoryli_" + ilID);
    li.appendChild(a);
    ul.appendChild(li);

},

showLevel3DiseaseSites :function(ulID, ilID, ilText,title) {

	var selectedSubcategories = $$('a.disease-subcategory-selected');
    selectedSubcategories.each(function(el) {
        el.ClassName("disease-subcategory-selected");
    });

    var selectedSubcategories = $$('li.li-subcategory-selected');
    selectedSubcategories.each(function(el) {
        el.ClassName("li-subcategory-selected");
    });

//	$("subcategory_" + ilID).addClassName("disease-subcategory-selected");
    $("subcategoryli_" + ilID).addClassName("li-subcategory-selected");

    $('disease-level3Sites').innerHTML = "";
    $('disease-level4Sites').innerHTML = "";

    level2Id = ilID;
    anatomicDiseaseSite.getLevel3DiseaseSiteCategories(level2Id, function(childCategories) {
        childCategories.each(function(childCategory) {
        	var childCategoryName = (childCategory.name.length > 18 ? childCategory.code+" " +childCategory.name.substring(0, 18) + "..." : childCategory.code+" " +childCategory.name);
          catSel.showLevel3DiseaseSitesDetails("disease-level3Sites", childCategory.id, childCategoryName, childCategory.name);
        })
    });
    return;
 },



showLevel3DiseaseSitesDetails: function(ulID, ilID, ilText, title){
	ul = document.getElementById(ulID);
    a = document.createElement("a");
    a.appendChild(document.createTextNode(ilText));
    a.setAttribute("onclick", "catSel.showLevel4DiseaseSites('disease-level4Sites', " + ilID + ", '" + ilText + "','" + title + "')");
    a.onclick = function() {
        eval("catSel.showLevel4DiseaseSites('disease-level4Sites', " + ilID + ", '" + ilText + "','" + title + "')");
    }
    a.setAttribute("id", "level3Disease_" + ilID);
    a.id = "level3Disease_" + ilID;

    a.setAttribute("title", title);
    a.title = title;

    li = document.createElement("li");
    li.setAttribute("id", "level3Diseasel3_" + ilID);
    li.id = "level3Diseasel3_" + ilID;
    li.appendChild(a);
    ul.appendChild(li);

},

showLevel4DiseaseSites :function(ulID, ilID, ilText,title) {

	var selectedSubcategories = $$('a.disease-subcategory-selected');
    selectedSubcategories.each(function(el) {
        el.ClassName("disease-subcategory-selected");
    });

    var selectedLevel3DiseaseSites = $$('li.li-subcategory-selected');
    selectedLevel3DiseaseSites.each(function(el) {
    //    el.ClassName("li-subcategory-selected");
    });

//	$("subcategory_" + ilID).addClassName("disease-subcategory-selected");
    $("level3Diseasel3_" + ilID).addClassName("li-subcategory-selected");
    $('disease-level4Sites').innerHTML = "";

    level3Id = ilID;
    anatomicDiseaseSite.getLevel4DiseaseSiteCategories(level3Id, function(childCategories) {
        childCategories.each(function(childCategory) {
        	var childCategoryName = (childCategory.name.length > 18 ? childCategory.code+" " +childCategory.name.substring(0, 18) + "..." : childCategory.code+" " +childCategory.name);
          catSel.addLIToUL("disease-level4Sites", childCategory.id, childCategoryName, childCategory.name);
        })
    });
    return;
 },

addLIToUL: function(ulID, ilID, ilText, title) {
    ul = document.getElementById(ulID);
    a = document.createElement("a");
    a.appendChild(document.createTextNode(ilText));
	a.setAttribute("onClick", "catSel.addLevel4Site('disease-added-terms', " + ilID + ", '" + ilText + "', '" + title + "')");
       a.onclick = function() {
           eval("catSel.addLevel4Site('disease-added-terms', " + ilID + ", '" + ilText + "', '" + title + "')");
       }
    a.setAttribute("id", "liTerm" + ilID);
    a.id = "liTerm" + ilID;

    a.setAttribute("title", title);
    a.title = title;

    li = document.createElement("li");
    li.setAttribute("id", "subcategoryli_" + ilID);
    
 //   $("subcategoryli_" + ilID).addClassName("li-subcategory-selected");
    li.appendChild(a);
    ul.appendChild(li);
},

showCategoryBox:function(){
			this.showWindow('', '', 1000, 580 );
	}
});

function initalizeCategorySelector(){
	catSel = new CategorySelector();
}

initalizeCategorySelector();

function deleteStudyDiseases(diseaseTerm){
	 <tags:tabMethod method="deleteStudyDiseases" viewName="/study/asynchronous/study_disease_section" divElement="'studyDiseases'" formName="'tabMethodForm'" javaScriptParam="'diseaseTermId='+diseaseTerm" onComplete="hideDiseaseIndicator"/> ;
}

function hideDiseaseIndicator(){
	$('diseaseIndicator').hide();
}

function hideDiseaseIndicator(){
	$('diseaseIndicator').hide();
}

function setVersion(box,index){
	cv = document.getElementById('consent'-index);
	alert(cv.value);
	icv = document.getElementById('consentToSet'-index);
	alert(icv.value);
	if (box.checked) {
		icv.value=cv.value;
    }else {
    	icv.value="";
    }
}

checkRegistrationDate = function(cal){
	cal.hide();
	if($('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value != null &&
			$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value != ''){
		Element.show('consentSignedDate-indicator');
		$('updateStudyVersion').value='false';
		$('dontSave').value='false';
		$('consentSignedDate').value=$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value;
		<tags:tabMethod method="validateRegistrationDate" viewName="/registration/asynchronous/checkRegistrationDate" divElement="'checkRegistrationDateDiv'" formName="'studyVersionForm'" onComplete="displayStudyVersionError"/>
	}
}

displayStudyVersionError = function(){
	Element.hide('consentSignedDate-indicator');
	if($('checkRegistrationDateDiv').innerHTML.replace(/^\s+|\s+$/g,'') != ""){	
		win = new Window({ width:600, height:200 ,className :"mac_os_x" , 
				title: "Message" , minimizable:false, maximizable:false ,
				zIndex:100 , hideEffect:Element.hide, showEffect:Element.show}) 
		win.setContent('checkRegistrationDateDiv') ;
		win.showCenter(true);
	}
}

function closePopup(){
	win.close();
	$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value="";
	$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').focus();
	ValidationManager.Error("studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate");
}

function changeStudyVersion(){
	$('updateStudyVersion').value="true";
	$('consentSignedDate').value=$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value;
	$('dontSave').remove();
	$('studyVersionForm').submit();
}

</script>
<style>
	#single-fields-interior div.row div.label {
		width:22em;
	}
	#single-fields-interior div.row div.value {
		margin-left:23em;
	}
	#main {
		top:35px;
	}


</style>
</head>
<body>
<c:choose>
<c:when test="${alreadyRegistered!=null}">
	<tags:panelBox>
	<form id="manage" name="manage" action="../registration/manageRegistration" method="get">
	<input type="hidden" name="registrationId" id="manage_registrationId" value=""/>
	</form>
	<font color="red">The participant is already registered on this epoch. If you want to move this subject to another epoch of this study,
	please use Manage Registration module. You can navigate to Manage Registration by searching the registration and then clicking on the registration record.
	</font>
	</tags:panelBox>
</c:when>
<c:otherwise>
<div id="checkRegistrationDateDiv" style="display: none;">
</div>
<form:form id="studyVersionForm">
	<form:errors path="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate">
		<div id="checkRegistrationDateDivInline">
		</div>
		<script>
			<tags:tabMethod method="validateRegistrationDate" viewName="/registration/asynchronous/checkRegistrationDate" divElement="'checkRegistrationDateInline'" javaScriptParam="'consentSignedDate=${command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate}'"  formName="'invalidSubmitForm'"/>
		</script>
	</form:errors>
	<input type="hidden" id="updateStudyVersion" name="updateStudyVersion" value="false"/>
	<input type="hidden" id="consentSignedDate" name="consentSignedDate"/>
	<input type="hidden" id="dontSave" name="dontSave" value="true"/>
</form:form>
<tags:formPanelBox tab="${tab}" flow="${flow}">
<%--<tags:instructions code="enrollment_details" />--%>

	<c:choose>
		<c:when test="${command.studySubject.scheduledEpoch.epoch.reservationIndicator == 'true'}">
			<c:set var="reservingEpoch" value="true"/>
		</c:when>
		<c:otherwise>
			<c:set var="reservingEpoch" value="z"/>
		</c:otherwise>
	</c:choose>
	
	<c:if test="${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator == 'false'}">
		<div class="row">
			<div class="label"><tags:requiredIndicator /><b>${command.studySubject.scheduledEpoch.epoch.name} epoch start date</b></div>
			<div class="value">
				<form:input path="studySubject.scheduledEpoch.startDate" cssClass='validate-notEmpty validate-DATE' size="18"/>
				<a href="#" id="studySubject.scheduledEpoch.startDate-calbutton">
				    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.scheduledEpoch.startDate"/>
				<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "studySubject.scheduledEpoch.startDate",
			                button      : "studySubject.scheduledEpoch.startDate-calbutton",
			                ifFormat    : "%m/%d/%Y", // TODO: get this from the configuration
			                weekNumbers : false,
			            }
			        );
				</script>
			</div>
		</div>
	</c:if>
	
	<c:if test="${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator == 'true'}">
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="registration.startDate"/></div>
			<div class="value">
				<form:input path="studySubject.startDate" cssClass='validate-notEmpty validate-DATE' size="18"/>
				<a href="#" id="studySubject.startDate-calbutton">
				    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.startDate"/>
				<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "studySubject.startDate",
			                button      : "studySubject.startDate-calbutton",
			                ifFormat    : "%m/%d/%Y", // TODO: get this from the configuration
			                weekNumbers : false,
			            }
			        );
				</script>
			</div>
		</div>
	</c:if>
	<c:if test="${fn:length(command.studySubject.studySite.study.consents) == 1}">
		<input type="hidden" name="studySubject.consentVersion" id="consent" value="${command.studySubject.studySite.studySiteStudyVersion.studyVersion.consents[0].id}"/>
		<input type="hidden" name="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].consent" id="consentToSet" 
			value="${command.studySubject.studySite.studySiteStudyVersion.studyVersion.consents[0].id}"/>
			
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentSignedDate"/></div>
			<div class="value">
				<form:input path="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate" cssClass='validate-notEmpty validate-DATE' size="18"/>
				<a href="#" id="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate-calbutton">
				    <img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/>
				<span id="consentSignedDate-indicator">
				<img src="<c:url value="/images/indicator.white.gif"/>" alt="activity indicator"/>
				validating study version ...
				</span>
				<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate",
			                button      : "studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate-calbutton",
			                ifFormat    : "%m/%d/%Y", // TODO: get this from the configuration
			                weekNumbers : false,
			                onClose     : checkRegistrationDate
			            }
			        );
			        Element.hide('consentSignedDate-indicator');
				</script>
				</div>
		</div>
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="registration.currentConsentVersionIs"/> <em>${command.studySubject.studySubjectStudyVersion.studySiteStudyVersion.studyVersion.consents[0].name}</em></div>
			<div class="value">
				<input type="checkbox" name="studySubject.currentVersionIndicator" value="true" onclick="setVersion(this,0);"
					${(fn:length(command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions) == 1 && 
									!empty command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].consent) ? "checked" : ""}/>
				<tags:hoverHint keyProp="studySubject.informedConsentSigned"/></div>
		</div>
	</c:if>
	<div class="row">
		<div class="label"><c:if test="${hasInv}"><tags:requiredIndicator /></c:if><fmt:message key="registration.enrollingPhysician"/></div>
		<div class="value">
		<c:choose>
		<c:when test="${hasInv}">
			<select id ="treatingPhysician" name="studySubject.treatingPhysician">
				<option value="">Please select...</option>
				<c:forEach items="${command.studySubject.studySite.activeStudyInvestigators}" var="activeInv">
					<option value="${activeInv.id }" ${!empty command.studySubject.treatingPhysician && command.studySubject.treatingPhysician.id==activeInv.id?'selected':''}>${activeInv.healthcareSiteInvestigator.investigator.fullName }</option>
				</c:forEach>
			</select>
			<%--<form:select path="studySubject.treatingPhysician">
				<option value="">Please Select</option>
				<form:options
					items="${command.studySubject.studySite.activeStudyInvestigators}" itemLabel="healthcareSiteInvestigator.investigator.fullName" itemValue="id" />
			</form:select>--%>
			<c:if test="${empty command.studySubject.otherTreatingPhysician }">
				<c:set var="physicianStyle" value="display: none;"></c:set>
			</c:if>
			<form:input path="studySubject.otherTreatingPhysician" cssStyle="${physicianStyle}"/>
		</c:when>
		<c:otherwise>
		No active physician found
		</c:otherwise>
		</c:choose>
		<tags:hoverHint keyProp="studySubject.treatingPhysician"/>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.primaryDisease"/></div>
		<div class="value">
			<form:select id="studyDiseaseSelect" path="studySubject.diseaseHistory.studyDisease" onchange="manageField(this);">
				<option value="">Please select...</option>
				<form:options items="${command.studySubject.studySite.study.studyDiseases}" itemLabel="diseaseTerm.term" itemValue="id"/>
				<option value="">Other</option>
			</form:select>
			<tags:hoverHint keyProp="studySubject.primaryDisease"/>
			<c:if test="${empty command.studySubject.diseaseHistory.otherPrimaryDiseaseCode}">
				<c:set var="diseaseStyle" value="display:none;"></c:set>
			</c:if>
			<span id="otherDisease" style="${diseaseStyle}">
				<form:input id="otherDisease" path="studySubject.diseaseHistory.otherPrimaryDiseaseCode" />
				<tags:hoverHint keyProp="studySubject.otherDisease"/>
			</span>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.primaryDiseaseSite"/></div>
		<div class="value">
			<form:input id="diseaseSite-input" path="studySubject.diseaseHistory.otherPrimaryDiseaseSiteCode" cssClass="autocomplete"/>
			<form:hidden id="diseaseSite-hidden" path="studySubject.diseaseHistory.icd9DiseaseSite"/>
			<tags:indicator id="diseaseSite-indicator"/>
			<div id="diseaseSite-choices" class="autocomplete" style="display: none;"></div>
			<tags:hoverHint keyProp="studySubject.diseaseSite"/>
			<tags:button size="small" type="button" color="blue" icon="add" value="Select Disease Site" id="addSingleDiseaseBtn" onclick="$('diseaseIndicator').show();catSel.showCategoryBox();"/>
			<img id="diseaseIndicator" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none"/>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.paymentMethod"/></div>
		<div class="value">
			<form:select id="paymentMethod" path="studySubject.paymentMethod">
				<option value="">Please select...</option>
				<form:options items="${paymentMethods}" itemLabel="desc" itemValue="code"/>
			</form:select>
			<tags:hoverHint keyProp="studySubject.primaryDisease"/>
		</div>
	</div>
<!-- MAIN BODY ENDS HERE -->
<!--  CONSENT DIV BEGINS -->

<c:if test="${fn:length(command.studySubject.studySite.study.consents) > 1}">
<chrome:division title="Consents">
	<table class="tablecontent">
		<tr>
		  <th>
          	<fmt:message key="c3pr.common.name"/>
          	<tags:hoverHint keyProp="study.consent.name" />
          </th>
          <th>
          	<fmt:message key="registration.consentSignedDate"/>
          	<tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate" />
          </th>
           <th width="30%">
          	Signed
          	<tags:hoverHint keyProp="study.consent.consentVersion.name" />
          </th>
		</tr>
		<c:forEach items="${command.studySubject.studySite.study.consents}" var="consent" varStatus="status">
		
			<input type="hidden" name="studySubject.consentVersion" id="consent-${status.index }" value="${command.studySubject.studySite.studySiteStudyVersion.studyVersion.consents[status.index].id}"/>
			<input type="hidden" name="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[${status.index }].consent" id="consentToSet-${status.index }" 
			value="${command.studySubject.studySite.studySiteStudyVersion.studyVersion.consents[status.index].id}"/>
			<tr>
				<td>
					${consent.name}
				</td>
				<td>
					<tags:dateInput path="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[${status.index}].informedConsentSignedDate" />
				</td>
				<td>
					<input type="checkbox" name="studySubject.currentVersionIndicator-${status.index }" value="true" onclick="setVersion(this,${status.index});"
						${!empty command.studySubject.studySubjectStudyVersion.studySubjectConsentVersions[status.index].consent ? "checked" : ""}/>
					<tags:hoverHint keyProp="studySubject.informedConsentSigned"/></div>
				</td>
			</tr>
		</c:forEach>
	</table>
</chrome:division>
</c:if>
</tags:formPanelBox>
<div style="display:none">
    <div id="chooseCategory">
        <chrome:box title="ICD9 Disease Sites">

        <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr bgcolor="#E4E4E4">
            <td align="left" width="20%"><h2 class="title">Disease Site Categories</h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="20%"><h2 class="title">Disease Site Subcategories&nbsp;<span style='font-size:12px;'></span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="20%"><h2 class="title">Disease Sites&nbsp;<span style='font-size:12px;'></span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="20%"><h2 class="title">Disease Sites&nbsp;<span style='font-size:12px;'></span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="20%"><h2 class="title">Selected Disease Site</h2></td>
        </tr>
        <tr>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="categories" class="disease-category">
                    <c:forEach var="cat" items="${diseaseSiteCategories}">
                    	<c:choose>
                    	<c:when test="${fn:length(cat.name) > 18}">
                    		<c:set var="catName" value="[${cat.code} ] ${fn:substring(cat.name,0,18)}..."> </c:set>
                    	</c:when>
                    	<c:otherwise>
                    		<c:set var="catName" value="[${cat.code} ] ${cat.name}"> </c:set>
                    	</c:otherwise>
                    	</c:choose>
                        <li id="li_${cat.id}">
                        	<a id="category_${cat.id}" onclick='catSel.showLevel2DiseaseSites(${cat.id});' class='disease-category' title="${cat.name}">${catName}</a>
                        </li>
                    </c:forEach>
                </ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="disease-subcategories" class="disease-category"></ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="disease-level3Sites" class="disease-category"></ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="disease-level4Sites" class="disease-category"></ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top"><div style="overflow:auto; height:460px;"><ul id="disease-added-terms" class="disease-category"></ul></div></td>
        </tr>
        <tr>
            <td colspan="6" style="text-align:right;">
            </td>
            <td colspan="3" style="text-align:center;">
                    <c:if test="${empty localButtons}">
                        <tags:button color="green" value="Select Disease Site" icon="add" onclick="catSel.finishMultiTermsSelection()" />
                    </c:if>
            </td>
        </tr>
        </table>

        </chrome:box>
    </div>
	</div>
<!-- the hidden window for category popup -->

<style>
    ul.disease-category {
        cursor:pointer;
        margin: 5px;
        padding-left: 0px;
		list-style-type:none;
    }

	ul#categories li a l3 {
		margin-left:5px;
	}

    a.disease-category {
        font-size:9pt;
        cursor:pointer;
        color:black;
    }

    a.disease-category-selected {
        font-size:9pt;
        cursor:pointer;
        line-height:26px;
    }

    li.li-category-selected {
        background-image:url(/c3pr/images/chrome/cat-small-arrow.png);
		background-repeat:no-repeat;
    }
    
    l3.l3-site-selected {
        background-image:url(/c3pr/images/chrome/cat-small-arrow.png);
		background-repeat:no-repeat;
		line-height:26px;
    }

    li.li-category {
    	cursor:pointer;
        margin: 5px;
        padding-left: 0px;
		list-style-type:none;
    }
    
    l3.l3-site {
     margin-right: 5px;
    }

    a.disease-category:hover {
        font-size:9pt;
        cursor:pointer;
        color:blue;
		text-decoration:underline;
    }

    ul.disease-added-terms, a.disease-added-terms {
        font-size:9pt;
        cursor:pointer;
        margin: 0px;
        padding-left: 5px;
    }

    #disease-added-terms {
        list-style-type: none;
    }

    a.disease-added-terms:hover {
        cursor:pointer;
    }

    a.term-disabled {
        font-size:9pt;
        color:#cccccc;
        cursor:pointer;
    }
    a.term-selected {
		line-height:26px;
		background-image:url(/c3pr/images/chrome/cat-small-arrow.png);
    }

    a.term-disabled:hover {
        font-size:9pt;
        color:#cccccc;
        cursor:pointer;
    }

    a.disease-subcategory-selected {
        font-size:9pt;
        cursor:pointer;
        line-height:26px;
    }

    li.li-subcategory-selected {
        background-image:url(/c3pr/images/chrome/cat-small-arrow.png);
		background-repeat:no-repeat;
		line-height:26px;
    }
    
    l3.l3-site-selected {
        background-image:url(/c3pr/images/chrome/cat-small-arrow.png);
		background-repeat:no-repeat;
		line-height:26px;
    }
</style>
</c:otherwise>
</c:choose>
<script type="text/javascript">
if($('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value == null ||
		$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value == ''){
		$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value = '${consentSignedDate}';
	}
</script>
</body>
</html>
