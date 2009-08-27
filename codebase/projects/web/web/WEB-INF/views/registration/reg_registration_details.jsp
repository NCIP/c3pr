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
								ValidationManager.removeError($("studySubject.otherTreatingPhysician"));
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
    var selectedDiseasTerms ='' ;
    var selectedTerms = $$('input.AddedTermXYZ');
    selectedTerms.each(function(el) {
        if (el.checked) {
        	selectedDiseasTerms = selectedDiseasTerms + el.value +',';
        }
    });
    catSel.addStudyDisease(selectedDiseasTerms);
    Windows.close(this.win.getId());
    catSel.termList = new Array();
    $('disease-subcategories').innerHTML = "";
    $('disease-added-terms').innerHTML = "";
    return;
},

addStudyDisease:function(selectedTerms){
	 <tags:tabMethod method="addStudyDiseases" viewName="/study/asynchronous/study_disease_section" divElement="'studyDiseases'" formName="'tabMethodForm'" javaScriptParam="'selectedDiseaseTerms='+selectedTerms" onComplete="hideDiseaseIndicator"/> ;
},

cancelTermsSelection:function(){
	Windows.close(this.win.getId());
	terms.options.length=0;
	categories.selectedIndex = -1;
	hideDiseaseIndicator();
},

addLevel4DiseaseSites:function (id){
	anatomicDiseaseSite.getLevel4DiseaseSiteCategories(id, function(diseaseTerms) {
    	diseaseTerms.each(function(diseaseTerm) {
          var termName = (diseaseTerm.ctepTerm.length > 30 ? diseaseTerm.ctepTerm.substring(0, 30) + "..." : diseaseTerm.ctepTerm);
          catSel.addLevel4Site("disease-added-terms", diseaseTerm.id, termName, diseaseTerm.ctepTerm);
        });
    });
},

addLevel4Site: function(ulID, termID, termText, title) {
    if (catSel.termList[termID]) {
        return;
    }
    ul = document.getElementById(ulID);
    
    checkbox = document.createElement("input");
    checkbox.type = 'checkbox';
    checkbox.name = termText;
    checkbox.defaultChecked = true;
    checkbox.value = termID;
    checkbox.id = "chkID" + termID;
    checkbox.setAttribute("id", "chk" + termID);

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

    catSel.termList[termID] = true;
    $("liTerm" + termID).addClassName("term-disabled");
    $("addedTerm" + termID).addClassName("disease-added-terms");
    $("chk" + termID).addClassName("AddedTermXYZ");

},

showLevel1DiseaseSites: function(id){
    var selectedCategories = $$('a.disease-category-selected');
    selectedCategories.each(function(el) {
        el.removeClassName("disease-category-selected");
    });

    var selectedCategories = $$('li.li-category-selected');
    selectedCategories.each(function(el) {
        el.removeClassName("li-category-selected");
    });

    $("category_" + id).addClassName("disease-category-selected");
    $("li_" + id).addClassName("li-category-selected");
    $('disease-subcategories').innerHTML = "";
    $('disease-terms').innerHTML = "";
	
    catId = id; 
    anatomicDiseaseSite.getLevel2DiseaseSiteCategories(catId, function(childCategories) {
        childCategories.each(function(childCategory) {
          var childCategoryName = (childCategory.name.length > 30 ? childCategory.name.substring(0, 30) + "..." : childCategory.name);
          catSel.showLevel3DiseaseSites("disease-level3Sites", childCategory.id, childCategoryName, childCategory.name);
     //     catSel.showDiseaseTerms("disease-subcategories", childCategory.id, childCategoryName, childCategory.name);
        })
    });
    return;
},

showLevel3DiseaseSites: function(id){
	ul = document.getElementById(ulID);
    a = document.createElement("a");
    a.appendChild(document.createTextNode(ilText));
    a.setAttribute("onclick", "catSel.showDiseaseTermDetail('disease-terms', " + ilID + ", '" + ilText + "')");
    a.onclick = function() {
        eval("catSel.showDiseaseTermDetail('disease-terms', " + ilID + ", '" + ilText + "')");
    }
    a.setAttribute("id", "subcategory_" + ilID);
    a.id = "subcategory_" + ilID;

    a.setAttribute("title", title);
    a.title = title;

    li = document.createElement("li");
    li.setAttribute("id", "subcategoryli_" + ilID);
    li.id = "subcategoryli_" + ilID;
    li.appendChild(a);
    ul.appendChild(li);

},

showDiseaseTerms: function(ulID, ilID, ilText, title){
	ul = document.getElementById(ulID);
    a = document.createElement("a");
    a.appendChild(document.createTextNode(ilText));
    a.setAttribute("onclick", "catSel.showDiseaseTermDetail('disease-terms', " + ilID + ", '" + ilText + "')");
    a.onclick = function() {
        eval("catSel.showDiseaseTermDetail('disease-terms', " + ilID + ", '" + ilText + "')");
    }
    a.setAttribute("id", "subcategory_" + ilID);
    a.id = "subcategory_" + ilID;

    a.setAttribute("title", title);
    a.title = title;

    li = document.createElement("li");
    li.setAttribute("id", "subcategoryli_" + ilID);
    li.id = "subcategoryli_" + ilID;
    li.appendChild(a);
    ul.appendChild(li);

},

showDiseaseTermDetail :function(ulID, ilID, ilText) {

	var selectedSubcategories = $$('a.disease-subcategory-selected');
    selectedSubcategories.each(function(el) {
        el.removeClassName("disease-subcategory-selected");
    });

    var selectedSubcategories = $$('li.li-subcategory-selected');
    selectedSubcategories.each(function(el) {
        el.removeClassName("li-subcategory-selected");
    });
    
	$("subcategory_" + ilID).addClassName("disease-subcategory-selected");
    $("subcategoryli_" + ilID).addClassName("li-subcategory-selected");
    $('disease-terms').innerHTML = "";
    
	subCatId = ilID;
    StudyAjaxFacade.getDiseaseTerms(subCatId, function(diseaseTerms) {
    	diseaseTerms.each(function(diseaseTerm) {
          var termName = (diseaseTerm.ctepTerm.length > 30 ? diseaseTerm.ctepTerm.substring(0, 30) + "..." : diseaseTerm.ctepTerm);
          catSel.addLIToUL("disease-terms", diseaseTerm.id, termName, diseaseTerm.ctepTerm);
        })
    });
    catSel.addLIToUL("disease-terms", subCatId, 'Add All', 'Add All');
 },

	addSingleDisease:function(){
	diseaseTerm = $('diseaseTerm-hidden').value ;
	catSel.addStudyDisease(diseaseTerm);
	$('diseaseTerm-hidden').value='' ;
	$('diseaseTerm-input').value='' ;
},	

addLIToUL: function(ulID, ilID, ilText, title) {
    ul = document.getElementById(ulID);
    a = document.createElement("a");
    a.appendChild(document.createTextNode(ilText));
	if(ilText == 'Add All'){
		a.setAttribute("onClick", "catSel.addLevel4DiseaseSites(" + ilID + ")");
        a.onclick = function() {
            eval("catSel.addLevel4DiseaseSites(" + ilID + ")");
        }
	}else{
		a.setAttribute("onClick", "catSel.addLevel4Site('disease-added-terms', " + ilID + ", '" + ilText + "', '" + title + "')");
        a.onclick = function() {
            eval("catSel.addLevel4Site('disease-added-terms', " + ilID + ", '" + ilText + "', '" + title + "')");
        }
	}
    

    a.setAttribute("id", "liTerm" + ilID);
    a.id = "liTerm" + ilID;

    a.setAttribute("title", title);
    a.title = title;
    
    li = document.createElement("li");
    li.appendChild(a);
    ul.appendChild(li);

    $("liTerm" + ilID).addClassName("disease-category");
    if (catSel.termList[ilID]) {
        $("liTerm" + ilID).addClassName("term-disabled");
    }
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
<tags:formPanelBox tab="${tab}" flow="${flow}">
<%--<tags:instructions code="enrollment_details" />--%>
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentSignedDate"/></div>
		<div class="value"><tags:dateInput path="studySubject.informedConsentSignedDate" /><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.informedConsentFormSignedDate"/></div>
	</div>
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="registration.consentVersion"/></div>
		<div class="value">
			<select id ="consentVersionOptions" name="studySubject.informedConsentVersion">
				<c:if test="${!isConsentPresent}">
					<option value="">Please select...</option>
				</c:if>
				<option value="${command.studySubject.studySite.study.consentVersion}" ${command.studySubject.studySite.study.consentVersion==command.studySubject.informedConsentVersion?'selected':'' }>${command.studySubject.studySite.study.consentVersion}</option>
				<c:forEach items="${command.studySubject.studySite.study.studyAmendments}" var="amendment">
					<c:if test="${!empty amendment.consentVersion && amendment.consentVersion!=''}">
					<option value="${amendment.consentVersion }" ${amendment.consentVersion==command.studySubject.informedConsentVersion?'selected':'' }>${amendment.consentVersion }</option>
					</c:if>
				</c:forEach>
			</select><em>(<fmt:message key="registration.currentConsentVersionIs"/> ${command.studySubject.studySite.study.latestConsentVersion})</em>
		</div>
	</div>
	<div class="row">
		<div class="label"><fmt:message key="registration.startDate"/></div>
		<div class="value"><tags:dateInput path="studySubject.startDate" /><em> (mm/dd/yyyy)</em><tags:hoverHint keyProp="studySubject.startDate"/></div>
	</div>
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
			<tags:button size="small" type="button" color="blue" icon="add" value="Add Disease Site" id="addSingleDiseaseBtn" onclick="$('diseaseIndicator').show();catSel.showCategoryBox();"/>
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
</tags:formPanelBox>
<div style="display:none">
    <div id="chooseCategory">
        <chrome:box title="Select Diseases">

        <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr bgcolor="#E4E4E4">
            <td align="left" width="25%"><h2 class="title">Disease Site Categories</h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="25%"><h2 class="title">Disease Site Sub Categories&nbsp;<span style='font-size:12px;'></span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="25%"><h2 class="title">Disease Sites&nbsp;<span style='font-size:12px;'>(Click to add)</span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="25%"><h2 class="title">Selected Disease Sites</h2></td>
        </tr>
        <tr>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="categories" class="disease-category">
                    <c:forEach var="cat" items="${diseaseSiteCategories}">
                    	<c:if test="${fn:length(cat.name) > 30}">
                    		<c:set var="catName" value="${fn:substring(cat.name,0,30)}......"> </c:set>
                    	</c:if>
                        <li id="li_${cat.id}">
                        	<a id="category_${cat.id}" onclick='catSel.showLevel1DiseaseSites(${cat.id});' class='disease-category' title="${cat.name}">${fn:length(cat.name) > 30 ? catName : cat.name}</a>
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
                <ul id="disease-terms" class="disease-category"></ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top"><div style="overflow:auto; height:460px;"><ul id="disease-added-terms" class="disease-category"></ul></div></td>
        </tr>
        <tr>
            <td colspan="6" style="text-align:right;">
            </td>
            <td colspan="1" style="text-align:center;">
                    <c:if test="${empty localButtons}">
                        <tags:button color="green" value="Add Terms" icon="add" onclick="catSel.finishMultiTermsSelection()" />
                    </c:if>
            </td>
        </tr>
        </table>
        
        </chrome:box>
    </div>
	</div>
<!-- the hidden window for category popup -->
</c:otherwise>
</c:choose>
</body>
</html>
