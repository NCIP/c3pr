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
<%--  <tags:dwrJavascriptLink objects="createReport"/>
      <tags:dwrJavascriptLink objects="reportCommand"/>
--%>
<script>
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
<chrome:division title="Study Criteria" >
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.title"/></div>
          	<div class="value">
        		<input type="text"  size="25">
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="text"  size="25">
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
  	            <select id="studyType" size="4" multiple="multiple">
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
        	<div class="label"><fmt:message key="dashboard.coordinatingCenter"/></div>
          	<div class="value">
        		<input type="text"  size="35" class="autocomplete">
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
        	<div class="label"><fmt:message key="study.sponsor"/></div>
          	<div class="value">
        		<input type="text"  size="35" class="autocomplete">
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="study.site"/></div>
          	<div class="value">
        		<input type="text"  size="35" class="autocomplete"  >
        	</div>
        </div>
        <div id="advancedStudyOption2" style="display:none">
        <div class="row" >
        	<div class="label"><fmt:message key="study.phase"/></div>
          	<div class="value">
  	            <select id="studyPhaseCode" size="4" multiple="multiple">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${phaseCodeRefData}" var="studyPhaseCode">
                       <c:if test="${!empty studyPhaseCode.desc}">
                           <option value="${studyPhaseCode.code}">${studyPhaseCode.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        </div>	
     </div>
</chrome:division>
<chrome:division title="Subject Criteria">
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.firstName"/></div>
          	<div class="value">
        		<input type="text"  size="25">
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.lastName"/></div>
        	<div class="value">
        		<input type="text"  size="25">
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="text"  size="25">
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
  	            <select id="administrativeGenderCode" size="4" multiple="multiple">
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
  	            <select id="ethnicGroupCodes" size="4" multiple="multiple">
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
  	            <select id="raceCodes" size="4" multiple="multiple">
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
   	        	<select id="age">
                   <option value="">Please select</option>
                   <option value="">Older than</option>
                   <option value="">Younger than</option>
                   <option value="">Equal to</option>
                </select>
       			<input type="text"  size="5">
   	    	</div>
        </div>
        <div id="advancedParticipantOption2" style="display:none">
        <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.city"/></div>
      	<div class="value">
      		<input type="text"  size="25">
      	</div>
      </div>
      <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.state"/></div>
      	<div class="value">
      		<input type="text"  size="25">
      	</div>
      </div>
      <div class="row" >
      	<div class="label"><fmt:message key="c3pr.common.zip"/></div>
      	<div class="value">
      		<input type="text"  size="25">
      	</div>
      </div>
      <div class="row" >
     	<div class="label"><fmt:message key="c3pr.common.country"/></div>
      	<div class="value">
      		<input type="text"  size="25">
      	</div>
      </div>
      <div class="divison"></div>
      </div>
     </div>
</chrome:division>
<chrome:division title="Registration Criteria">
	<div class="leftpanel">
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="text"  size="25">
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.currentStatus"/></div>
          	<div class="value">
        		<select id="registrationStatus" size="4" multiple="multiple">
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
  	            <select id="paymentMethods" size="4" multiple="multiple">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${paymentMethods}" var="paymentMethod">
                       <c:if test="${!empty paymentMethod.desc}">
                           <option value="${paymentMethod.code}">${paymentMethod.desc}</option>
                       </c:if>
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
   	        	<select id="consentSignedDate">
                   <option value="" selected="selected">Please select</option>
                   <option value="">Prior to</option>
                   <option value="">Later than</option>
                   <option value="">Equal to</option>
                </select>
       			<input type="text"  size="10">
       			<a href="#" id="calbutton">
    				<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="registration.startDate"/></div>
   	        <div class="value">
   	        	<select id="registrationDate">
                   <option value="" selected="selected">Please select</option>
                   <option value="">Prior to</option>
                   <option value="">Later than</option>
                   <option value="">Equal to</option>
                </select>
       			<input type="text"  size="10">
       			<a href="#" id="calbutton">
    				<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="registration.enrollingPhysician"/></div>
   	        <div class="value">
         	   	<input type="text"  size="35" class="autocomplete">
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.disease"/></div>
	       	<div class="value">
  	           	<input type="text"  size="35" class="autocomplete">
   	    	</div>
        </div>
        <div id="advancedRegistrationOption2" style="display:none">
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.diseaseSite"/></div>
	       	<div class="value">
  	           	<input type="text"  size="35" class="autocomplete">
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="registration.enrollingSite"/></div>
	       	<div class="value">
  	           	<input type="text"  size="35" class="autocomplete">
   	    	</div>
        </div>
      <div class="divison"></div>
      </div>
     </div>
</chrome:division>
<chrome:division>
<br>
<div  align="center">
	<tags:button id="searchRegistration" type="button" icon="search" size="small" color="blue" value="Search Registration" />
	
	
	
	<tags:button type="button" size="small" color="blue" value="Clear" />
</div>
</chrome:division>
</form:form>
</chrome:box>
</body>
</html>