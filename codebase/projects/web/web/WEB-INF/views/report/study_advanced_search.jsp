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
    <title>Study Search</title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<title>${tab.longTitle}</title>
	<tags:dwrJavascriptLink objects="CommonAjaxFacade" />
<script>

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

	var investigatorAutocompleterProps = {
	        basename: "investigator",
	        populator: function(autocompleter, text) {
	            CommonAjaxFacade.matchInvestigators(text,function(values) {
	                autocompleter.setChoices(values)
	            })
	        },
	        valueSelector: function(obj) {
	        	return (obj.fullName + '(' + obj.assignedIdentifier +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=investigatorAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.assignedIdentifier;
			 }
	    }

	var studyPersonAutocompleterProps = {
	        basename: "studyPerson",
	        populator: function(autocompleter, text) {
	            CommonAjaxFacade.matchStudyPersonnel(text,function(values) {
	                autocompleter.setChoices(values)
	            })
	        },
	        valueSelector: function(obj) {
	        	return (obj.fullName + '(' + obj.assignedIdentifier +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=studyPersonAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.assignedIdentifier;
			 }
	    }

	var coordinatingCenterAutocompleterProps = {
	        basename: "coordinatingCenter",
	        populator: function(autocompleter, text) {
	            CommonAjaxFacade.matchHealthcareSites(text,function(values) {
	                autocompleter.setChoices(values)
	            })
	        },
	        valueSelector: function(obj) {
	        	return (obj.name + '(' + obj.ctepCode +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=coordinatingCenterAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
	    }

	var fundingSponsorAutocompleterProps = {
	        basename: "fundingSponsor",
	        populator: function(autocompleter, text) {
	            CommonAjaxFacade.matchHealthcareSites(text,function(values) {
	                autocompleter.setChoices(values)
	            })
	        },
	        valueSelector: function(obj) {
	        	return (obj.name + '(' + obj.ctepCode +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=fundingSponsorAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
	    }

	var studySiteAutocompleterProps = {
	        basename: "studySite",
	        populator: function(autocompleter, text) {
	            CommonAjaxFacade.matchHealthcareSites(text,function(values) {
	                autocompleter.setChoices(values)
	            })
	        },
	        valueSelector: function(obj) {
	        	return (obj.name + '(' + obj.ctepCode +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=studySiteAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.id;
			 }
	    }

AutocompleterManager.addAutocompleter(diseaseAutocompleterProps);
AutocompleterManager.addAutocompleter(investigatorAutocompleterProps);
AutocompleterManager.addAutocompleter(studyPersonAutocompleterProps);
AutocompleterManager.addAutocompleter(coordinatingCenterAutocompleterProps);
AutocompleterManager.addAutocompleter(fundingSponsorAutocompleterProps);
AutocompleterManager.addAutocompleter(studySiteAutocompleterProps);


function resetScreen(){
	alert("Reset functionality not yet implemented");
}

</script>
<style type="text/css">
#search td {
color:white;
}

div.row div.labelCompanion {
float:left;
font-weight:bold;
margin-left:0.5em;
text-align:right;
width:16em;
}
</style>
</head>
<body>
<tags:instructions code="study_search_report"/>
<chrome:box title="Search Study">
<form:form id="search" method="post">
<chrome:division>
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.title"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[0].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
          		<input type="hidden" name="searchCriteriaList[0].attributeName" value="shortTitleText" />
          		<input type="hidden" name="searchCriteriaList[0].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[0].values" />
        		<tags:hoverHint keyProp="study.title"/>
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.type"/></div>
        	<div class="value">
        		<input type="hidden" name="searchCriteriaList[1].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
          		<input type="hidden" name="searchCriteriaList[1].attributeName" value="type" />
          		<input type="hidden" name="searchCriteriaList[1].predicate" value="in"/>
  	            <select id="studyType" size="4" multiple="multiple" name="searchCriteriaList[1].values">
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
        	<div class="label"><fmt:message key="study.phase"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[2].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
          		<input type="hidden" name="searchCriteriaList[2].attributeName" value="phaseCode" />
          		<input type="hidden" name="searchCriteriaList[2].predicate" value="in"/>
  	            <select id="studyPhaseCode" size="4" multiple="multiple" name="searchCriteriaList[2].values">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${phaseCodeRefData}" var="studyPhaseCode">
                       <c:if test="${!empty studyPhaseCode.desc}">
                           <option value="${studyPhaseCode.desc}">${studyPhaseCode.desc}</option>  
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.currentStatus"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[3].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
          		<input type="hidden" name="searchCriteriaList[3].attributeName" value="coordinatingCenterStudyStatus.code" />
          		<input type="hidden" name="searchCriteriaList[3].predicate" value="in"/>
        		<select id="studyStatus" size="4" multiple="multiple" name="searchCriteriaList[3].values">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${statusRefDate}" var="studyStatus">
                       <c:if test="${!empty studyStatus}">
                           <option value="${studyStatus.key}">${studyStatus.value}</option>
                       </c:if>
                   </c:forEach>
                </select>
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
          		<input type="hidden" name="searchCriteriaList[4].objectName" value="edu.duke.cabig.c3pr.domain.Identifier"/>
          		<input type="hidden" name="searchCriteriaList[4].contextObjectName" value="Study" />
          		<input type="hidden" name="searchCriteriaList[4].attributeName" value="value" />
          		<input type="hidden" name="searchCriteriaList[4].predicate" value="like"/>
        		<input type="text"  size="25" name="searchCriteriaList[4].values" />
        	</div>
        </div>
         <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.disease"/></div>
	       	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[5].objectName" value="edu.duke.cabig.c3pr.domain.DiseaseTerm"/>
   	        	<input type="hidden" name="searchCriteriaList[5].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[5].attributeName" value="ctepTerm" />
          		<input type="hidden" id="diseaseterm-hidden" name="searchCriteriaList[5].values"/>
				<tags:autocompleter name="diseaseterm" displayValue="" value="" basename="diseaseterm"></tags:autocompleter>
   	    	</div>
        </div>
    </div>
    <div class="rightpanel">
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.investigator"/></div>
	       	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[6].objectName" value="edu.duke.cabig.c3pr.domain.Investigator"/>
   	        	<input type="hidden" name="searchCriteriaList[6].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[6].attributeName" value="assignedIdentifier" />
          		<input type="hidden" id="investigator-hidden" name="searchCriteriaList[6].values"/>
				<tags:autocompleter name="investigator" displayValue="" value="" basename="investigator"></tags:autocompleter>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.personnel"/></div>
   	        <div class="value">
	       		<input type="hidden" name="searchCriteriaList[7].objectName" value="edu.duke.cabig.c3pr.domain.ResearchStaff"/>
   	        	<input type="hidden" name="searchCriteriaList[7].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[7].attributeName" value="assignedIdentifier" />
          		<input type="hidden" id="studyPerson-hidden" name="searchCriteriaList[7].values"/>
				<tags:autocompleter name="studyPerson" displayValue="" value="" basename="studyPerson"></tags:autocompleter>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="dashboard.coordinatingCenter"/></div>
          	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[8].objectName" value="edu.duke.cabig.c3pr.domain.HealthcareSite"/>
	       		<input type="hidden" name="searchCriteriaList[8].contextObjectName" value="StudyCoordinatingCenter"/>
   	        	<input type="hidden" name="searchCriteriaList[8].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[8].attributeName" value="id" />
          		<input type="hidden" id="coordinatingCenter-hidden" name="searchCriteriaList[8].values"/>
				<tags:autocompleter name="coordinatingCenter" displayValue="" 	value="" basename="coordinatingCenter"></tags:autocompleter>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="study.sponsor"/></div>
          	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[9].objectName" value="edu.duke.cabig.c3pr.domain.HealthcareSite"/>
	       		<input type="hidden" name="searchCriteriaList[9].contextObjectName" value="StudyFundingSponsor"/>
   	        	<input type="hidden" name="searchCriteriaList[9].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[9].attributeName" value="id" />
          		<input type="hidden" id="fundingSponsor-hidden" name="searchCriteriaList[9].values"/>
				<tags:autocompleter name="fundingSponsor" displayValue="" value="" basename="fundingSponsor"></tags:autocompleter>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="study.site"/></div>
          	<div class="value">
	       		<input type="hidden" name="searchCriteriaList[10].objectName" value="edu.duke.cabig.c3pr.domain.HealthcareSite"/>
	       		<input type="hidden" name="searchCriteriaList[10].contextObjectName" value="StudySite"/>
   	        	<input type="hidden" name="searchCriteriaList[10].predicate" value="="/>
          		<input type="hidden" name="searchCriteriaList[10].attributeName" value="id" />
          		<input type="hidden" id="studySite-hidden" name="searchCriteriaList[10].values"/>
				<tags:autocompleter name="studySite" displayValue="" value="" basename="studySite"></tags:autocompleter>
   	    	</div>
        </div>
</chrome:division>
<chrome:division>
<br>
<div  align="center">
	<tags:button type="submit" icon="search" size="small" color="blue" value="Search Study"/>
</div>
</chrome:division>
</form:form>
</chrome:box>
</body>
</html>