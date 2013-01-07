<%--
Copyright Duke Comprehensive Cancer Center and SemanticBits
 
  Distributed under the OSI-approved BSD 3-Clause License.
  See  https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>

<html>
<head>
    <title>Subject Search</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>${tab.longTitle}</title>
	<tags:dwrJavascriptLink objects="CommonAjaxFacade" />
<script>
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
		
		if(selectbox.value == ""){
			$('age1').style.display="none";
			$('age1').value="";
		} else {
			$('age1').style.display="";
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
	var organizationAutocompleterProps = {
	        basename: "identifierOrganization",
	        populator: function(autocompleter, text) {
	            CommonAjaxFacade.matchHealthcareSites(text,function(values) {
	                autocompleter.setChoices(values)
	            })
	        },
	        valueSelector: function(obj) {
	        	return (obj.name + '(' + obj.ctepCode +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=organizationAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
	    }
	AutocompleterManager.addAutocompleter(organizationAutocompleterProps);
	function validateAndSubmitForm(){
		if($('identifierOrganization-hidden').value != "" && $('identifierSystemName').value == ""){
			$('identifierValue').value="edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier" ;
			$('identifierType').value="edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier" ;
		} else if($('identifierOrganization-hidden').value == "" && $('identifierSystemName').value != ""){
			$('identifierValue').value="edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier" ;
			$('identifierType').value="edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier" ;
		}
		 $('search').submit();	
	}
</script>
<style type="text/css">
#search td {
color:white;
}
</style>
</head>
<body>
<tags:instructions code="participant_search_report"/>
<chrome:box title="Search Subject">
<form:form id="search" method="post">
<chrome:division>
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.firstName"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[0].objectName" value="edu.duke.cabig.c3pr.domain.Participant"/>
          		<input type="hidden" name="searchCriteriaList[0].attributeName" value="firstName" />
          		<input type="hidden" name="searchCriteriaList[0].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[0].values" />
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.lastName"/></div>
        	<div class="value">
        		<input type="hidden" name="searchCriteriaList[1].objectName" value="edu.duke.cabig.c3pr.domain.Participant"/>
          		<input type="hidden" name="searchCriteriaList[1].attributeName" value="lastName" />
          		<input type="hidden" name="searchCriteriaList[1].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[1].values" />
        	</div>
        </div>
        
         <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.assigningAuthority"/></div>
          	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[11].objectName" value="edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier"/>
   	        	<input type="hidden" name="searchCriteriaList[11].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[11].attributeName" value="healthcareSite.id" />
          		<input type="hidden" id="identifierOrganization-hidden" name="searchCriteriaList[11].values"/>
				<tags:autocompleter name="identifierOrganization" displayValue="" 	value="" basename="identifierOrganization"></tags:autocompleter>
   	    	</div>
        </div>
         <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.systemName"/></div>
          	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[12].objectName" value="edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier"/>
   	        	<input type="hidden" name="searchCriteriaList[12].predicate" value="like"/>
          		<input type="hidden" name="searchCriteriaList[12].attributeName" value="systemName" />
          		<input type="text" id="identifierSystemName" size="12" name="searchCriteriaList[12].values" />
          		
   	    	</div>
        </div>
         <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifierType"/></div>
          	<div class="value">
	       		<input type="hidden" id="identifierType" name="searchCriteriaList[13].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
   	        	<input type="hidden" name="searchCriteriaList[13].predicate" value="in"/>
          		<input type="hidden" name="searchCriteriaList[13].attributeName" value="typeInternal" />
          		 <select id="identifiersType" size="4" multiple="multiple" name="searchCriteriaList[13].values">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${identifiersType}" var="identifiersType">
                       <c:if test="${!empty identifiersType.code}">
                           <option value="${identifiersType.code}">${identifiersType.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="hidden" id="identifierValue" name="searchCriteriaList[2].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
          		<input type="hidden" name="searchCriteriaList[2].attributeName" value="value" />
          		<input type="hidden" name="searchCriteriaList[2].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[2].values" />
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="participant.gender"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[3].objectName" value="edu.duke.cabig.c3pr.domain.Participant"/>
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
	       		<input type="hidden" name="searchCriteriaList[4].objectName" value="edu.duke.cabig.c3pr.domain.Participant"/>
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
   	        	<input type="hidden" name="searchCriteriaList[10].objectName" value="edu.duke.cabig.c3pr.domain.Participant"/>
        		<input type="hidden" name="searchCriteriaList[10].attributeName" value="birthDate" />
   	        	<select id="age" name="searchCriteriaList[10].predicate" onchange="showAgeTextBox(this);">
                   <option value="" selected="selected">Please Select</option>
                   <option value="<">Older than</option>
                   <option value="<=">Older than and Equal to</option>
                   <option value=">">Younger than</option>
                   <option value=">=">Younger than and Equal to</option>
                   <option value="=">Equal to</option>
                   <option value="between">Between</option>
                </select>
                <input id="age1" type="text"  size="5" name="age1" onkeyup="calculateBirthDate(this, 'date1')" style="display:none"/>
                <input type="text" style="display:none;border: none" value="and" id="and" size="1px" readonly="readonly"> 
             	<input id="age2" type="text"  size="5" name="age2" style="display:none;margin-top: 5px; margin-left: 190px" onkeyup="calculateBirthDate(this, 'date2')"/>
             	<!-- later date should come first -->
             	<input id="date2" type="hidden"  name="searchCriteriaList[10].values" style="display:none"/>
             	<input id="date1" type="hidden"  name="searchCriteriaList[10].values"  />
   	    	</div>
        </div>
        <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.city"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[6].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[6].attributeName" value="city" />
         	<input type="hidden" name="searchCriteriaList[6].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[6].values" />
      	</div>
      </div>
      <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.state"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[7].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[7].attributeName" value="stateCode" />
         	<input type="hidden" name="searchCriteriaList[7].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[7].values" />
      	</div>
      </div>
      <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.zip"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[8].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[8].attributeName" value="postalCode" />
         	<input type="hidden" name="searchCriteriaList[8].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[8].values" />
      	</div>
      </div>
      <div class="row" >
     	<div class="label"><fmt:message key="c3pr.common.country"/></div>
      	<div class="value">
      		<input type="hidden" name="searchCriteriaList[9].objectName" value="edu.duke.cabig.c3pr.domain.Address"/>
        	<input type="hidden" name="searchCriteriaList[9].attributeName" value="countryCode" />
         	<input type="hidden" name="searchCriteriaList[9].predicate" value="like"/>
       		<input type="text"  size="25" name="searchCriteriaList[9].values" />
      	</div>
      </div>
      <div class="divison"></div>
     </div>
</chrome:division>
<chrome:division>
<br>
<div  align="center">
	<tags:button id="searchRegistration" type="button" icon="search" size="small" color="blue" value="Search Subject" onclick="validateAndSubmitForm();"/>
	<tags:button type="reset" size="small" color="blue" value="Clear" />
</div>
</chrome:division>
</form:form>
</chrome:box>
</body>
</html>
