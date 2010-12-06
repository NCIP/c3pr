<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@taglib uri="http://jawr.net/tags" prefix="jwr" %>

<html>
<head>
    <title>Registration Search</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>${tab.longTitle}</title>
	<tags:dwrJavascriptLink objects="CommonAjaxFacade" />
<script>
	function validateAndSubmitForm(){
		if($('enrollingsite-hidden').value == ""){
			$('enrollingsite-identifier-type').value="" ;
		}
		
		if($('studyorganization-hidden').value == "" || $('studyorganization-type').value == ""){
			$('studyorganization-identifier-type').value="" ;
			$('studyorganization-hidden').value = "";
			$('studyorganization-type').value = "";
		}else {
			if($('studyorganization-type').value=="SCC" || $('studyorganization-type').value=="SFS" ){
				$('studyorganization-contextObject').value='StudyOrganization';
			} else if($('studyorganization-type').value=="SST")  {
				$('studyorganization-contextObject').value='EnrollingSite' ;
			}
		}
		 $('search').submit();	
	}
	
	function showAgeTextBox(selectbox){
		if (selectbox.value == 'between') {
	        $('age2').style.display="" ;   
	        $('and').style.display="" ;   
	    }else{
	    	$('age2').style.display="none" ;
	    	$('and').style.display="none" ;
	    	$('age2').value="" ;
	    	$('date2').value="" ;
	    }
	}
	
	function calculateBirthDate(ageField, dateField){
		var age = ageField.value;
		today = new Date();
		//dateStr=today.getDate();
		//monthStr=today.getMonth();
		yearStr=today.getFullYear();
		birthYear = yearStr - age ;
		//$(dateField).value = monthStr+"/"+dateStr+"/"+birthYear ;
		$(dateField).value = "01/01/"+birthYear ;
	}
	function showExtraCriteriaForStudy(){
		Element.show('advancedStudyOption1');
		Element.show('advancedStudyOption2');
		Element.hide('showStudyOptions');
	}

	function hideExtraCriteriaForStudy(){
		Element.hide('advancedStudyOption1');
		Element.hide('advancedStudyOption2');
		Element.show('showStudyOptions');
	}

	function showExtraCriteriaForParticipant(){
		Element.show('advancedParticipantOption1');
		Element.show('advancedParticipantOption2');
		Element.hide('showParticipantOptions');
	}

	function hideExtraCriteriaForParticipant(){
		Element.hide('advancedParticipantOption1');
		Element.hide('advancedParticipantOption2');
		Element.show('showParticipantOptions');
	}


	function showExtraCriteriaForRegistration(){
		Element.show('advancedRegistrationOption1');
		Element.show('advancedRegistrationOption2');
		Element.hide('showRegistrationOptions');
	}

	function hideExtraCriteriaForRegistration(){
		Element.hide('advancedRegistrationOption1');
		Element.hide('advancedRegistrationOption2');
		Element.show('showRegistrationOptions');
	}

	function selectAllEpochReasonsOfThisType(thisElement){
		var offEpochClass = "form ." + thisElement.className;
		$$(offEpochClass).each(function(e)
			{
				e.selected="selected";
			}
		);

	}
	
	var enrollingSiteAutocompleterProps = {
        basename: "enrollingsite",
        populator: function(autocompleter, text) {
            CommonAjaxFacade.matchHealthcareSites(text,function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.name+" ("+obj.ctepCode+")" )
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=enrollingSiteAutocompleterProps.basename+"-hidden"
    							$(hiddenField).value=selectedChoice.id;
		 }
    }
	
	var diseaseSiteAutocompleterProps = {
        basename: "diseasesite",
        populator: function(autocompleter, text) {
            CommonAjaxFacade.matchDiseaseSites(text,function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.name+" ("+obj.code+")" )
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=diseaseSiteAutocompleterProps.basename+"-hidden"
    							$(hiddenField).value=selectedChoice.code;
		 }
    }
	
	var diseaseAutocompleterProps = {
        basename: "diseaseterm",
        populator: function(autocompleter, text) {
            CommonAjaxFacade.matchDiseaseTerms(text,function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.ctepTerm)
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=diseaseAutocompleterProps.basename+"-hidden"
    							$(hiddenField).value=selectedChoice.ctepTerm;
		 }
    }
	
	var treatingPhysicianAutocompleterProps = {
        basename: "physician",
        populator: function(autocompleter, text) {
            CommonAjaxFacade.matchInvestigators(text,function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.fullName+" ("+obj.assignedIdentifier+")" ) 
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=treatingPhysicianAutocompleterProps.basename+"-hidden"
    							$(hiddenField).value=selectedChoice.assignedIdentifier;
		 }
    }
	
	var studyOrgAutocompleterProps = {
        basename: "studyorganization",
        populator: function(autocompleter, text) {
            CommonAjaxFacade.matchHealthcareSites(text,function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
        	return (obj.name+" ("+obj.ctepCode+")" )
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
								hiddenField=studyOrgAutocompleterProps.basename+"-hidden"
    							$(hiddenField).value=selectedChoice.ctepCode;
		 }
    }
	
	AutocompleterManager.addAutocompleter(enrollingSiteAutocompleterProps);
	AutocompleterManager.addAutocompleter(diseaseSiteAutocompleterProps);
	AutocompleterManager.addAutocompleter(diseaseAutocompleterProps);
	AutocompleterManager.addAutocompleter(treatingPhysicianAutocompleterProps);
	AutocompleterManager.addAutocompleter(studyOrgAutocompleterProps);
	

</script>
<style type="text/css">
#search td {
color:white;
}

</style>
</head>
<body>
<tags:instructions code="registration_search_report"/>
<chrome:box title="Search Registration">
<form:form id="search" method="post">
<chrome:division title="Subject Criteria">
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.firstName"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[0].objectName" value="edu.duke.cabig.c3pr.domain.StudySubjectDemographics"/>
          		<input type="hidden" name="searchCriteriaList[0].attributeName" value="firstName" />
          		<input type="hidden" name="searchCriteriaList[0].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[0].values" />
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.lastName"/></div>
        	<div class="value">
        		<input type="hidden" name="searchCriteriaList[1].objectName" value="edu.duke.cabig.c3pr.domain.StudySubjectDemographics"/>
          		<input type="hidden" name="searchCriteriaList[1].attributeName" value="lastName" />
          		<input type="hidden" name="searchCriteriaList[1].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[1].values" />
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="hidden" name="searchCriteriaList[2].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
        		<input type="hidden" name="searchCriteriaList[2].contextObjectName" value="Subject" />
          		<input type="hidden" name="searchCriteriaList[2].attributeName" value="value" />
          		<input type="hidden" name="searchCriteriaList[2].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[2].values" />
        	</div>
        </div>
        <div class="row" id="showParticipantOptions">
        	<div class="value">
        		<a  href="javascript:showExtraCriteriaForParticipant();">+ show more options</a>
        	</div>
        </div>
        <div id="advancedParticipantOption1" style="display:none">
        <div class="row" >
        	<div class="label"><fmt:message key="participant.gender"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[3].objectName" value="edu.duke.cabig.c3pr.domain.StudySubjectDemographics"/>
          		<input type="hidden" name="searchCriteriaList[3].attributeName" value="administrativeGenderCode" />
          		<input type="hidden" name="searchCriteriaList[3].predicate" value="in"/>
  	            <select id="administrativeGenderCode" size="4" multiple="multiple" name="searchCriteriaList[3].values">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${administrativeGenderCode}" var="administrativeGenderCode">
                       <c:if test="${!empty administrativeGenderCode.desc}">
                           <option value="${administrativeGenderCode.code}">${administrativeGenderCode.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="participant.ethnicity"/></div>
	       	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[4].objectName" value="edu.duke.cabig.c3pr.domain.StudySubjectDemographics"/>
          		<input type="hidden" name="searchCriteriaList[4].attributeName" value="ethnicGroupCode" />
          		<input type="hidden" name="searchCriteriaList[4].predicate" value="in"/>
  	            <select id="ethnicGroupCodes" size="4" multiple="multiple" name="searchCriteriaList[4].values">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${ethnicGroupCodes}" var="ethnicGroupCode">
                       <c:if test="${!empty ethnicGroupCode.desc}">
                           <option value="${ethnicGroupCode.code}">${ethnicGroupCode.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="value">
        		<a   href="javascript:hideExtraCriteriaForParticipant();">- hide options</a>
        	</div>
        </div>
         </div>
    </div>
    <div class="rightpanel">
    	<div class="row" >
	       	<div class="label"><fmt:message key="participant.race" /></div>
   	        <div class="value">
   	        	<input type="hidden" name="searchCriteriaList[5].objectName" value="edu.duke.cabig.c3pr.domain.RaceCodeAssociation"/>
          		<input type="hidden" name="searchCriteriaList[5].attributeName" value="raceCode.code"/ >
          		<input type="hidden" name="searchCriteriaList[5].predicate" value="in"/>
  	            <select id="raceCodes" size="4" multiple="multiple" name="searchCriteriaList[5].values" >
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${raceCodes}" var="raceCode">
                       <c:if test="${!empty raceCode.desc}">
                           <option value="${raceCode.code}">${raceCode.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.age"/></div>
   	        <div class="value">
   	        	<input type="hidden" name="searchCriteriaList[6].objectName" value="edu.duke.cabig.c3pr.domain.StudySubjectDemographics"/>
        		<input type="hidden" name="searchCriteriaList[6].attributeName" value="birthDate" />
   	        	<select id="age" name="searchCriteriaList[6].predicate" onchange="showAgeTextBox(this);">
                   <option value="" selected="selected">Please Select</option>
                   <option value="<">Older than</option>
                   <option value="<=">Older than and Equal to</option>
                   <option value=">">Younger than</option>
                   <option value=">=">Younger than and Equal to</option>
                   <option value="=">Equal to</option>
                   <option value="between">Between</option>
                </select>
                <input id="age1" type="text"  size="5" name="age1" onkeyup="calculateBirthDate(this, 'date1')"/>
                <input type="text" style="display:none;border: none" value="and" id="and" size="1px" readonly="readonly"> 
             	<input id="age2" type="text"  size="5" name="age2" style="display:none;margin-top: 5px; margin-left: 190px" onkeyup="calculateBirthDate(this, 'date2')"/>
             	<!-- later date should come first -->
             	<input id="date2" type="hidden"  name="searchCriteriaList[6].values" style="display:none"/>
             	<input id="date1" type="hidden"  name="searchCriteriaList[6].values"  />
   	    	</div>
        </div>
        <div id="advancedParticipantOption2" style="display:none">
        <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.city"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[7].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[7].attributeName" value="city" />
         	<input type="hidden" name="searchCriteriaList[7].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[7].values" />
      	</div>
      </div>
      <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.state"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[8].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[8].attributeName" value="stateCode" />
         	<input type="hidden" name="searchCriteriaList[8].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[8].values" />
      	</div>
      </div>
      <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.zip"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[9].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[9].attributeName" value="postalCode" />
         	<input type="hidden" name="searchCriteriaList[9].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[9].values" />
      	</div>
      </div>
      <div class="row" >
     	<div class="label"><fmt:message key="c3pr.common.country"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[10].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[10].attributeName" value="countryCode" />
         	<input type="hidden" name="searchCriteriaList[10].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[10].values" />
      	</div>
      </div>
      <div class="divison"></div>
      </div>
     </div>
</chrome:division>
<chrome:division title="Study Criteria" >
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.title"/></div>
          	<div class="value">
        		<input type="hidden" name="searchCriteriaList[11].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
          		<input type="hidden" name="searchCriteriaList[11].attributeName" value="shortTitleText" />
          		<input type="hidden" name="searchCriteriaList[11].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[11].values" />
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"></fmt:message></div>
          	<div class="value">
        		<input type="hidden" name="searchCriteriaList[12].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
        		<input type="hidden" name="searchCriteriaList[12].contextObjectName" value="Study" />
          		<input type="hidden" name="searchCriteriaList[12].attributeName" value="value" />
          		<input type="hidden" name="searchCriteriaList[12].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[12].values" />
        	</div>
        </div>
        <div class="row" id="showStudyOptions">
        	<div class="value">
        		<a  href="javascript:showExtraCriteriaForStudy();">+ show more options</a>
        	</div>
        </div>
        <div id="advancedStudyOption1" style="display:none">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.type"/></div>
        	<div class="value">
        		<input type="hidden" name="searchCriteriaList[13].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
          		<input type="hidden" name="searchCriteriaList[13].predicate" value="in"/>
          		<input type="hidden" name="searchCriteriaList[13].attributeName" value="type" />
  	            <select id="studyType" size="4" multiple="multiple" name="searchCriteriaList[13].values" >
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${typeRefData}" var="studyType">
                       <c:if test="${!empty studyType.desc}">
                           <option value="${studyType.code}">${studyType.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="value">
        		<a   href="javascript:hideExtraCriteriaForStudy();">- hide options</a>
        	</div>
        </div>
        </div>
    </div>
    <div class="rightpanel">
		<div class="row" >
        	<div class="label"><fmt:message key="study.phase"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[14].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
          		<input type="hidden" name="searchCriteriaList[14].predicate" value="in"/>
          		<input type="hidden" name="searchCriteriaList[14].attributeName" value="typeInternal" />
  	            <select id="studyPhaseCode" size="4" multiple="multiple">
                   <option value="" name="searchCriteriaList[14].values" selected="selected">All</option>
                   <c:forEach items="${phaseCodeRefData}" var="studyPhaseCode">
                       <c:if test="${!empty studyPhaseCode.desc}">
                           <option value="${studyPhaseCode.code}">${studyPhaseCode.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div id="advancedStudyOption2" style="display:none">
			<div class="row" >
	        	<div class="label">
	        		<input type="hidden" name="searchCriteriaList[15].objectName" value="edu.duke.cabig.c3pr.domain.StudyOrganization"/>
          			<input type="hidden" name="searchCriteriaList[15].attributeName" value="type" />
          			<input type="hidden" name="searchCriteriaList[15].predicate" value="="/>
	        		<select id="studyorganization-type" name="searchCriteriaList[15].values" >
	        			<option value="" selected="selected">Please Select</option>
	        			<option value="">Any</option>
	                    <option value="SCC">Coordinating Center</option>
	                    <option value="SFS">Funding Sponsor</option>
	                    <option value="SST">Study Site</option>
                	</select>
	        	</div>
	        	
	          	<div class="value">
	          		<input type="hidden" name="searchCriteriaList[16].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
	          		<input type="hidden" name="searchCriteriaList[16].contextObjectName" id="studyorganization-contextObject" value="" />
          			<input type="hidden" name="searchCriteriaList[16].attributeName" value="typeInternal" />
          			<input type="hidden" name="searchCriteriaList[16].predicate" value="="/>
	       			<input id="studyorganization-identifier-type" type="hidden" name="searchCriteriaList[16].values" value="CTEP"  />
	       			
	       			<input type="hidden" name="searchCriteriaList[17].objectName" value="edu.duke.cabig.c3pr.domain.HealthcareSite"/>
		       		<input type="hidden" name="searchCriteriaList[17].contextObjectName" value="StudyCoordinatingCenter"/>
	   	        	<input type="hidden" name="searchCriteriaList[17].predicate" value="="/>
	          		<input type="hidden" name="searchCriteriaList[17].attributeName" value="id" />
	          		<input type="hidden" id="studyorganization-hidden" name="searchCriteriaList[17].values"/>
					<tags:autocompleter name="studyorganization" displayValue="" value="" basename="studyorganization"></tags:autocompleter>
	          		
	        </div>
        </div>	
     </div>
</chrome:division>
<chrome:division title="Registration Criteria">
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="hidden" name="searchCriteriaList[18].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
        		<input type="hidden" name="searchCriteriaList[18].contextObjectName" value="StudySubject" />
          		<input type="hidden" name="searchCriteriaList[18].attributeName" value="value" />
          		<input type="hidden" name="searchCriteriaList[18].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[18].values" />
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.currentStatus"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[19].objectName" value="edu.duke.cabig.c3pr.domain.StudySubject"/>
          		<input type="hidden" name="searchCriteriaList[19].attributeName" value="regWorkflowStatus.code" />
          		<input type="hidden" name="searchCriteriaList[19].predicate" value="in"/>
        		<select id="registrationStatus" name="searchCriteriaList[19].values" size="4" multiple="multiple">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${registrationStatusRefData}" var="registrationStatus">
                       <c:if test="${!empty registrationStatus.value && registrationStatus.value != 'Invalid'}">
                           <option value="${registrationStatus.key}">${registrationStatus.value}</option>
                       </c:if>
                   </c:forEach>
                </select>
        	</div>
        </div>
        <div class="row" id="showRegistrationOptions">
        	<div class="value">
        		<a  href="javascript:showExtraCriteriaForRegistration();">+ show more options</a>
        	</div>
        </div>
        <div id="advancedRegistrationOption1" style="display:none">
        <div class="row" >
        	<div class="label"><fmt:message key="registration.paymentMethod"/></div>
          	<div class="value" >
          		<input type="hidden" name="searchCriteriaList[20].objectName" value="edu.duke.cabig.c3pr.domain.StudySubject"/>
          		<input type="hidden" name="searchCriteriaList[20].attributeName" value="paymentMethod" />
          		<input type="hidden" name="searchCriteriaList[20].predicate" value="in"/>
        		<select id="paymentMethods" name="searchCriteriaList[20].values" size="4" multiple="multiple">
                   <option value="" selected="selected">Any</option>
                   <c:forEach items="${paymentMethods}" var="paymentMethod">
                       <c:if test="${!empty paymentMethod.desc}">
                           <option value="${paymentMethod.code}">${paymentMethod.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        
        <div class="row" >
        	<div class="label"><fmt:message key="study.epoch.type"/></div>
          	<div class="value" >
          		<input type="hidden" name="searchCriteriaList[27].objectName" value="edu.duke.cabig.c3pr.domain.Epoch"/>
          		<input type="hidden" name="searchCriteriaList[27].attributeName" value="type.code" />
          		<input type="hidden" name="searchCriteriaList[27].predicate" value="in"/>
        		<select id="epochType" name="searchCriteriaList[27].values" size="4" multiple="multiple">
                   <option value="" selected="selected">Any</option>
                   <option value="SCREENING">Screening</option>
                   <option value="TREATMENT">Treatment</option>
                   <option value="FOLLOWUP">Follow-up</option>
	               <option value="RESERVING"> Reserving</option>
                </select>
   	    	</div>
        </div>
        
         <div class="row" >
        	<div class="label"><fmt:message key="scheduledEpoch.offEpochReasonText"/></div>
          	<div class="value" >
          		<input type="hidden" name="searchCriteriaList[28].objectName" value="edu.duke.cabig.c3pr.domain.Reason"/>
          		<input type="hidden" name="searchCriteriaList[28].attributeName" value="code" />
          		<input type="hidden" name="searchCriteriaList[28].predicate" value="in"/>
        		<select id="offEpochReason" name="searchCriteriaList[28].values" size="4" multiple="multiple">
                   <option value="" selected="selected">Any</option>
                   <option class ="offScreeningEpochReasonClass" value="" onclick="selectAllEpochReasonsOfThisType(this);" >--- Off Screening Epoch Reasons ---</option>
                   <c:forEach items="${offScreeningEpochReasons}" var="offScreeningReason">
                           <option class="offScreeningEpochReasonClass" value="${offScreeningReason.code}">${offScreeningReason.description}</option>
                   </c:forEach>
                   
                   <option class ="offReservingEpochReasonClass" value="" onclick="selectAllEpochReasonsOfThisType(this);" >--- Off Reserving Epoch Reasons ---</option>
                   <c:forEach items="${offReservingEpochReasons}" var="offReservingEpochReason">
                           <option class ="offReservingEpochReasonClass" value="${offReservingEpochReason.code}">${offReservingEpochReason.description}</option>
                   </c:forEach>
                   
                   <option class ="offTreatmentEpochReasonClass" value="" onclick="selectAllEpochReasonsOfThisType(this);" >--- Off Treatment Epoch Reasons ---</option>
                   <c:forEach items="${offTreatmentEpochReasons}" var="offTreatmentEpochReason">
                           <option class ="offTreatmentEpochReasonClass" value="${offTreatmentEpochReason.code}">${offTreatmentEpochReason.description}</option>
                   </c:forEach>
                   
                   <option class ="offFollowupEpochReasonClass" value="" onclick="selectAllEpochReasonsOfThisType(this);" >--- Off Follow-up Epoch Reasons ---</option>
                   <c:forEach items="${offFollowupEpochReasons}" var="offFollowupEpochReason">
                           <option class ="offFollowupEpochReasonClass" value="${offFollowupEpochReason.code}">${offFollowupEpochReason.description}</option>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        
        
        
        <div class="row" >
        	<div class="value">
        		<a   href="javascript:hideExtraCriteriaForRegistration();">- hide options</a>
        	</div>
        </div>
        </div>
    </div>
    <div class="rightpanel">
    	<div class="row" >
	       	<div class="label"><fmt:message key="registration.consentSignedDate"/></div>
   	        <div class="value">
   	        	<select id="consentDate" name="searchCriteriaList[21].predicate">
                   <option value="" selected="selected">Please Select</option>
                   <option value=">">Later than</option>
                   <option value=">=">Later than and Equal to</option>
                   <option value="<">Prior to</option>
                   <option value="<=">Prior to and Equal to</option>
                   <option value="=">Equal to</option>
                </select>
       			<input type="hidden" name="searchCriteriaList[21].objectName" value="edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion"/>
          		<input type="hidden" name="searchCriteriaList[21].attributeName" value="informedConsentSignedTimestamp" />
          		<input type="text" name="searchCriteriaList[21].values" size="10" id="consentSignedDate" class="date validate-DATE" />
            	<a href="#" id="consentSignedDate-calbutton">
           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           		</a>
           		<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "consentSignedDate",
			                button      : "consentSignedDate-calbutton",
			                ifFormat    : "%m/%d/%Y", 
			                weekNumbers : false
			            }
			        );
				</script>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="registration.startDate"/></div>
   	        <div class="value">
   	        	<select id="startDate" name="searchCriteriaList[22].predicate">
                   <option value="" selected="selected">Please Select</option>
                   <option value=">">Later than</option>
                   <option value=">=">Later than and Equal to</option>
                   <option value="<">Prior to</option>
                   <option value="<=">Prior to and Equal to</option>
                   <option value="=">Equal to</option>
                </select>
       			<input type="hidden" name="searchCriteriaList[22].objectName" value="edu.duke.cabig.c3pr.domain.StudySubject"/>
          		<input type="hidden" name="searchCriteriaList[22].attributeName" value="startDate" />
          		<input type="text" name="searchCriteriaList[22].values" size="10" id="registrationStartDate" class="date validate-DATE" />
            	<a href="#" id="registrationStartDate-calbutton">
           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           		</a>
           		<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "registrationStartDate",
			                button      : "registrationStartDate-calbutton",
			                ifFormat    : "%m/%d/%Y", 
			                weekNumbers : false
			            }
			        );
				</script>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="registration.enrollingPhysician"/></div>
   	        <div class="value">
   	        	<input type="hidden" name="searchCriteriaList[23].objectName" value="edu.duke.cabig.c3pr.domain.Investigator"/>
   	        	<input type="hidden" name="searchCriteriaList[23].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[23].attributeName" value="assignedIdentifier" />
          		<input type="hidden" id="physician-hidden" name="searchCriteriaList[23].values"/>
				<tags:autocompleter name="physician" displayValue="" value="" basename="physician"></tags:autocompleter>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.disease"/></div>
	       	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[24].objectName" value="edu.duke.cabig.c3pr.domain.DiseaseTerm"/>
   	        	<input type="hidden" name="searchCriteriaList[24].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[24].attributeName" value="ctepTerm" />
          		<input type="hidden" id="diseaseterm-hidden" name="searchCriteriaList[24].values"/>
				<tags:autocompleter name="diseaseterm" displayValue="" value="" basename="diseaseterm"></tags:autocompleter>
   	    	</div>
        </div>
        <div id="advancedRegistrationOption2" style="display:none">
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.diseaseSite"/></div>
	       	<div class="value">
  	            <input type="hidden" name="searchCriteriaList[25].objectName" value="edu.duke.cabig.c3pr.domain.ICD9DiseaseSite"/>
   	        	<input type="hidden" name="searchCriteriaList[25].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[25].attributeName" value="code" />
          		<%-- Autocompleter Section --%>	
				<input type="hidden" id="diseasesite-hidden" name="searchCriteriaList[25].values"/>
				<tags:autocompleter name="diseasesite" displayValue="" value="" basename="diseasesite"></tags:autocompleter>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="registration.enrollingSite"/></div>
	       	<div class="value">
          		<input type="hidden" name="searchCriteriaList[26].objectName" value="edu.duke.cabig.c3pr.domain.HealthcareSite"/>
          		<input type="hidden" name="searchCriteriaList[26].contextObjectName" value="EnrollingSite" />
       			<input type="hidden" name="searchCriteriaList[26].predicate" value="="/>
       			<input type="hidden" name="searchCriteriaList[26].attributeName" value="id" />
       			<input id="enrollingsite-identifier-type" type="hidden" name="searchCriteriaList[26].values" value="CTEP"  />
				<%-- Autocompleter Section --%>	
				<input type="hidden" id="enrollingsite-hidden" name="searchCriteriaList[26].values"/>
				<tags:autocompleter name="enrollingsite" displayValue="" value="" basename="enrollingsite"></tags:autocompleter>
   	    	</div>
        </div>
      <div class="divison"></div>
      </div>
     </div>
</chrome:division>
<chrome:division>
<br>
<div  align="center">
	<tags:button id="searchRegistration" type="button" icon="search" size="small" color="blue" value="Search Registration" onclick="validateAndSubmitForm();"/>
	<tags:button type="reset" size="small" color="blue" value="Clear" />
</div>
</chrome:division>
</form:form>
</chrome:box>
</body>
</html>