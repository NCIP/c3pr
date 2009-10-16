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
								var consentsNumber = ${fn:length(command.studySubject.studySite.study.consents)};
								 atLeastOneConsentSelected = false;
								 allConsentsSelected = true;
									for(var i=0; i <consentsNumber; i++){
										var informedConsentDate = document.getElementById("studySubject.studySubjectStudyVersion.studySubjectConsentVersions["+i+"].informedConsentSignedDate");
										if (informedConsentDate.value != null && informedConsentDate.value != ''){
											atLeastOneConsentSelected=true;
										}else{
											allConsentsSelected = false;
										}
									}
							if (${command.studySubject.studySite.study.consentRequired == 'ONE'}){
									var error = document.getElementById("errorMsg1");
									if(!atLeastOneConsentSelected){
										if(${fn:length(command.studySubject.studySite.study.consents) > 1}){
											flag=false;
											error.innerHTML="<span id='sid1' style='color:#C35617'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; At least one consent needs to be signed.</span><br/>";
										} else {
											flag=false;
											error.innerHTML="<span id='sid1' style='color:#C35617'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Consent is required.</span><br/>";
										}
										error.style.display="";
									}else {error.style.display= "none" ;}
							} else if (${command.studySubject.studySite.study.consentRequired == 'ALL'}){
								var error = document.getElementById("errorMsg1");
									if(!allConsentsSelected){
										flag=false;
										error.innerHTML="<span id='sid1' style='color:#C35617'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; All consents need to be signed.</span><br/>";
										error.style.display="";
									} else {error.style.display="none";}
							}
									
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
	if($('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value != null &&
			$('studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate').value != ''){
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
	Effect.CloseDown('studyVersionDiv');
}

function changeStudyVersion(){
	Effect.CloseDown('studyVersionDiv');
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
	<div id="errorMsg1" style="display:none">
	</div>
	<div id="studyVersionDiv">
	<c:if test="${not empty canEnroll && !canEnroll}">
		<c:choose>
		<c:when test="${empty studyVersion}">
				<img src="<tags:imageUrl name="stop_sign.png" />" alt="Stop!" style="float:left; margin-right:30px; margin-left:80px;" />
				<div style="font-size:20px; margin-bottom:5px;">Invalid</div>
				<div>
					Cannot register subject. Site is/was not accruing on the given consent signed date because the site is/was not having the valid IRB approval for the study version.
				</div>
			<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="blue" icon="Close" value="Close" onclick="closePopup();" />
		</div>
		</c:when>
		<c:otherwise>
			<div style="padding-top: 20px">
				<img src="<tags:imageUrl name="error.png" />" alt="Alert!" style="float:left; margin-right:30px; margin-left:30px;" />
				<fmt:message key="REGISTRATION.STUDYVERSION.ERROR.FOUND.VALID" />
				<ul style="padding-left:150px;">
					<li><fmt:message key="study.versionNameNumber" /> : ${studyVersion.name}</li>
					<li><fmt:message key="study.version.date" /> : ${studyVersion.versionDateStr}</li>
				</ul>
			</div>
			<div align="right" style="padding-top: 10px">
			<tags:button type="button" color="red" icon="Cancel" value="Cancel" onclick="closePopup();" /> &nbsp;&nbsp;&nbsp;
			<tags:button type="button" color="green" icon="Save &amp; Continue" value="Continue" onclick="changeStudyVersion()" />
		</div>
		</c:otherwise>
		</c:choose>
		<hr>
	</c:if>
	</div>

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
			<div class="label"><tags:requiredIndicator /><fmt:message key="scheduledEpoch.startDate"/></div>
			<div class="value">
				<tags:dateInput path="studySubject.scheduledEpoch.startDate" validateDate="true" cssClass='validate-notEmpty'/>
			</div>
		</div>
	</c:if>
	
	<c:if test="${command.studySubject.scheduledEpoch.epoch.enrollmentIndicator == 'true'}">
		<div class="row">
			<div class="label"><tags:requiredIndicator /><fmt:message key="registration.startDate"/></div>
			<div class="value">
				<tags:dateInput path="studySubject.startDate" validateDate="true" cssClass='validate-notEmpty'/>
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
				<tags:dateInput path="studySubject.studySubjectStudyVersion.studySubjectConsentVersions[0].informedConsentSignedDate" validateDate="true" cssClass='validate-notEmpty'/>
			</div>
		</div>
	</c:if>
	<div class="row">
		<div class="label"><c:if test="${hasInv}"><tags:requiredIndicator /></c:if><fmt:message key="registration.enrollingPhysician"/></div>
		<div class="value">
		<c:choose>
		<c:when test="${hasInv}">
			<select id ="treatingPhysician" name="studySubject.treatingPhysician" class="validate-notEmpty">
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
