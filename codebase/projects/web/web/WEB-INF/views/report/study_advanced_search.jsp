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

	function setValueTrueIfChecked(element){
			attributeId = element.id + '-hidden';
			if(element.checked == true){
				$(attributeId).value = true;
			}else {
				$(attributeId).value = false;
			}
	}
	
	function setValueFalseIfChecked(element){
		attributeId = element.id + '-hidden';
		if(element.checked == true){
			$(attributeId).value = false;
		}else {
			$(attributeId).value = true;
		}
	}

	function managePendingAmendmentCheckBox(element){
		attributeId = element.id + '-hidden';
		if(element.checked == true){
			$(attributeId).value = false;
			$('versionStatus-hidden').value= "IN";
		}else {
			$(attributeId).value = true;
			$('versionStatus-hidden').value= "";
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
	        	return (obj.researchStaff.fullName + ' (' + obj.researchStaff.assignedIdentifier +')')
	        },
	        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
									hiddenField=studyPersonAutocompleterProps.basename+"-hidden"
	    							$(hiddenField).value=selectedChoice.researchStaff.assignedIdentifier;
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

function validateAndSubmitForm(){
        if($('versionDateFirst').value == "" && $('versionDateLast').value == "") {
				$('originalIndicatorStudyOpen-hidden').value="" ;
				$('coordinatingCenterStudyStatusStudyOpen-hidden').value="" ;
 	 	}
        if($('siteActivationDateFirst').value == "" && $('siteActivationDateLast').value == "") {
			$('activeStudyStatus-hidden').value="" ;
	 	}	
    $('search').submit();	
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
        	<div class="label"><fmt:message key="study.studyShortTitle"/></div>
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
    </div>
    <div class="rightpanel">
    	<div class="row" >
        	<div class="label"><fmt:message key="study.studyIdentifier"/></div>
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
			<div class="label"><fmt:message key="study.therapeuticIntentIndicator"/></div>
        	<div class="value">
        		<input type="hidden" name="searchCriteriaList[12].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
  	        		<input type="hidden" name="searchCriteriaList[12].predicate" value="="/>
         			<input type="hidden" name="searchCriteriaList[12].attributeName" value="therapeuticIntentIndicator" />
         			<input type="hidden" id="therapeuticIntentIndicator-hidden" name="searchCriteriaList[12].values"/>
        		<input type="checkbox" id="therapeuticIntentIndicator" name="therapeuticIntentIndicator" onclick="setValueTrueIfChecked(this);">
        	</div>        		
        </div>
        
    </div>
      
</chrome:division>
<chrome:division title="Coordinating Center Criteria" >
	<div class="leftpanel">
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
        <%-- 
         <div class="row" >
       	    <div class="label"><fmt:message key="c3pr.common.targetAccrualPercentage"/></div>
	  	        <div class="value">
	  	        	<select id="age">
	                  <option value="" selected="selected">Please select</option>
	                  <option value="">Greater than</option>
	                  <option value="">Less than</option>
	                  <option value="">Equal to</option>
	               </select>
	      			<input type="text"  size="5">
	  	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="study.bookExhaustionPercentage"/></div>
   	        <div class="value">
   	        	<select id="age">
                   <option value="" selected="selected">Please select</option>
                   <option value="">Greater than</option>
                   <option value="">Less than</option>
                   <option value="">Equal to</option>
                </select>
       			<input type="text"  size="5">
       			<tags:hoverHint keyProp="study.randomization.book.exhaustion.percentage"/>
   	    	</div>
        </div> --%>
		
    </div>
    <div class="rightpanel">
    	<div class="row" >
        	<div class="label"><fmt:message key="study.openDate"/>&nbsp;<b>after</b></div>
          	<div class="value"> 
          		
          		<input type="hidden" name="searchCriteriaList[17].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
	        	<input type="hidden" name="searchCriteriaList[17].predicate" value="="/>
       			<input type="hidden" name="searchCriteriaList[17].attributeName" value="originalIndicator" />
       			<input type="hidden" id="originalIndicatorStudyOpen-hidden" name="searchCriteriaList[17].values" value="true"/>
       			
       			
       			<input type="hidden" name="searchCriteriaList[18].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
          		<input type="hidden" name="searchCriteriaList[18].attributeName" value="coordinatingCenterStudyStatus.code" />
          		<input type="hidden" name="searchCriteriaList[18].predicate" value="!="/>
          		<input type="hidden" id="coordinatingCenterStudyStatusStudyOpen-hidden" name="searchCriteriaList[18].values" value="PENDING"/>
       			
        		<input type="hidden" name="searchCriteriaList[15].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
          		<input type="hidden" name="searchCriteriaList[15].attributeName" value="versionDate" />
          		<input type="hidden" name="searchCriteriaList[15].predicate" value=">"/>
          		<input type="text" name="searchCriteriaList[15].values" size="10" id="versionDateFirst" class="date validate-DATE" />
            	<a href="#" id="versionDateFirst-calbutton">
           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           		</a>
           		<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "versionDateFirst",
			                button      : "versionDateFirst-calbutton",
			                ifFormat    : "%m/%d/%Y", 
			                weekNumbers : false
			            }
			        );
				</script>
				<b>and before</b>
				<input type="hidden" name="searchCriteriaList[16].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
          		<input type="hidden" name="searchCriteriaList[16].attributeName" value="versionDate" />
          		<input type="hidden" name="searchCriteriaList[16].predicate" value="<"/>
          		<input type="text" name="searchCriteriaList[16].values" size="10" id="versionDateLast" class="date validate-DATE" />
            	<a href="#" id="versionDateLast-calbutton">
           	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
           		</a>
           		<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "versionDateLast",
			                button      : "versionDateLast-calbutton",
			                ifFormat    : "%m/%d/%Y", 
			                weekNumbers : false
			            }
			        );
				</script>
        	</div>
        </div>
        <div class="row" >
       		<div class="label"><fmt:message key="study.pendingAmendment"/></div>
	       	<div class="value">
	       		
	       		<input type="hidden" name="searchCriteriaList[13].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
	        	<input type="hidden" name="searchCriteriaList[13].predicate" value="="/>
       			<input type="hidden" name="searchCriteriaList[13].attributeName" value=versionStatus.code />
       			<input type="hidden" id="versionStatus-hidden" name="searchCriteriaList[13].values" value=""/>
	       	
	       	
	       		<input type="hidden" name="searchCriteriaList[14].objectName" value="edu.duke.cabig.c3pr.domain.StudyVersion"/>
 	        	<input type="hidden" name="searchCriteriaList[14].predicate" value="="/>
        		<input type="hidden" name="searchCriteriaList[14].attributeName" value="originalIndicator" />
        		<input type="hidden" id="originalIndicator-hidden" name="searchCriteriaList[14].values"/>
        		<input type="checkbox" id="originalIndicator" name="originalIndicator" onclick="managePendingAmendmentCheckBox(this);">
	       	</div>
        </div>	
    </div>
</chrome:division>
<chrome:division title="Study Site Criteria" >
	<div class="leftpanel">
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
	<%-- 	<div class="row" >
       		<div class="label"><fmt:message key="c3pr.common.targetAccrualPercentage"/></div>
  	        <div class="value">
  	        	<select id="age">
                  <option value="" selected="selected">Please select</option>
                  <option value="">Greater than</option>
                  <option value="">Less than</option>
                  <option value="">Equal to</option>
               </select>
      			<input type="text"  size="5">
  	    	</div>
       </div>
       
       --%>
       
        
    </div>
    <div class="rightpanel">
    	<div class="row" >
       		<div class="label"><fmt:message key="site.activationDate"/>&nbsp;<b>after</b></div>
         	<div class="value"> 
         		
         		<input type="hidden" name="searchCriteriaList[19].objectName" value="edu.duke.cabig.c3pr.domain.SiteStatusHistory"/>
        		<input type="hidden" name="searchCriteriaList[19].predicate" value="="/>
      			<input type="hidden" name="searchCriteriaList[19].attributeName" value="" />
      			<input type="hidden" id="originalIndicatorStudyOpen-hidden" name="searchCriteriaList[19].values" value=""/>
      			
      			
      			<input type="hidden" name="searchCriteriaList[20].objectName" value="edu.duke.cabig.c3pr.domain.SiteStatusHistory"/>
         		<input type="hidden" name="searchCriteriaList[20].attributeName" value="siteStudyStatus.code" />
         		<input type="hidden" name="searchCriteriaList[20].predicate" value="="/>
         		<input type="hidden" id="activeStudyStatus-hidden" name="searchCriteriaList[20].values" value="ACTIVE"/>
      			
       			<input type="hidden" name="searchCriteriaList[21].objectName" value="edu.duke.cabig.c3pr.domain.SiteStatusHistory"/>
         		<input type="hidden" name="searchCriteriaList[21].attributeName" value="startDate" />
         		<input type="hidden" name="searchCriteriaList[21].predicate" value=">"/>
         		<input type="text" name="searchCriteriaList[21].values" size="10" id="siteActivationDateFirst" class="date validate-DATE" />
           		<a href="#" id="versionDateFirst-calbutton">
          	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
          		</a>
	          		<script type="text/javascript">
						Calendar.setup(
				            {
				                inputField  : "siteActivationDateFirst",
				                button      : "siteActivationDateFirst-calbutton",
				                ifFormat    : "%m/%d/%Y", 
				                weekNumbers : false
				            }
				        );
					</script>
					<b>and before</b>
				<input type="hidden" name="searchCriteriaList[22].objectName" value="edu.duke.cabig.c3pr.domain.SiteStatusHistory"/>
         		<input type="hidden" name="searchCriteriaList[22].attributeName" value="endDate" />
         		<input type="hidden" name="searchCriteriaList[22].predicate" value="<"/>
         		<input type="text" name="searchCriteriaList[22].values" size="10" id="siteActivationDateLast" class="date validate-DATE" />
           		<a href="#" id="versionDateLast-calbutton">
          	   		<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="top"/>
          		</a>
          		<script type="text/javascript">
					Calendar.setup(
			            {
			                inputField  : "siteActivationDateLast",
			                button      : "siteActivationDateLast-calbutton",
			                ifFormat    : "%m/%d/%Y", 
			                weekNumbers : false
			            }
		        	);
				</script>
       		</div>
       	</div>
       	<%-- 
    	<div class="row" >
	       	<div class="label" style="width: 16em;"><fmt:message key="study.pendingSiteAmendment"/></div>
	       	<div class="value">
	       		<input type="checkbox" id="studySiteAmendment" name="studySiteAmendment" >
	       		<tags:hoverHint keyProp="study.pendingSiteAmendment"/>
	       	</div>
        </div> --%>
      	<div class="row" >
				<div class="label"><fmt:message key="study.site.irbExpired"/></div>
	        	<div class="value">
	        		<input type="hidden" name="searchCriteriaList[11].objectName" value="edu.duke.cabig.c3pr.domain.Study"/>
   	        		<input type="hidden" name="searchCriteriaList[11].predicate" value="="/>
          			<input type="hidden" name="searchCriteriaList[11].attributeName" value="irbExpired" />
          			<input type="hidden" id="irbExpired-hidden" name="searchCriteriaList[11].values"/>
	        		<input type="checkbox" id="irbExpired" name="irbExpired" onclick="setValueTrueIfChecked(this);">
	        	</div>	        	
        </div>
    </div>
</chrome:division>
<chrome:division>
<br>
<div  align="center">
	<tags:button type="submit" icon="search" size="small" color="blue" value="Search Study" onclick="validateAndSubmitForm();"/>
	<tags:button type="reset" size="small" color="blue" value="Clear" />
</div>
</chrome:division>
</form:form>
</chrome:box>
</body>
</html>