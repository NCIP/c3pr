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
<%--  <tags:dwrJavascriptLink objects="createReport"/>
      <tags:dwrJavascriptLink objects="reportCommand"/>
--%>
<script>

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
        		<input type="text"  size="25">
        		<tags:hoverHint keyProp="study.title"/>
        	</div>
        </div>
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
        	<div class="label"><fmt:message key="study.phase"/></div>
          	<div class="value">
  	            <select id="studyPhaseCode" size="4" multiple="multiple" >
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${phaseCodeRefData}" var="studyPhaseCode">
                       <c:if test="${!empty studyPhaseCode.desc}">
                           <option value="${studyPhaseCode.code}">${studyPhaseCode.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.currentStatus"/></div>
          	<div class="value">
        		<select id="studyStatus" size="4" multiple="multiple">
                   <option value="" selected="selected">All</option>
                   <c:forEach items="${studyStatusRefData}" var="studyStatus">
                       <c:if test="${!empty studyStatus.desc}">
                           <option value="${studyStatus.code}">${studyStatus.desc}</option>
                       </c:if>
                   </c:forEach>
                </select>
        	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="c3pr.common.identifier"/></div>
          	<div class="value">
        		<input type="text"  size="25">
        	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.disease"/></div>
	       	<div class="value">
  	           	<input type="text"  size="35" class="autocomplete">
   	    	</div>
        </div>
    </div>
    <div class="rightpanel">
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.investigator"/></div>
   	        <div class="value">
         	   	<input type="text"  size="30" class="autocomplete">
         	   	<input type="checkbox" id="PIindicator" name="PIIndicator" >
         	   	<b>PI Only	</b>
   	    	</div>
        </div>
        <div class="row" >
	       	<div class="label"><fmt:message key="c3pr.common.personnel"/></div>
   	        <div class="value">
   	        	<input type="text"  size="35" class="autocomplete">
   	    	</div>
        </div>
        <div class="row" >
        	<div class="label"><fmt:message key="dashboard.coordinatingCenter"/></div>
          	<div class="value">
        		<input type="text"  size="35" class="autocomplete">
        	</div>
        </div>
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
   	    	</div>
        </div>
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
        	<div class="label"><fmt:message key="study.openDate"/>&nbsp;<b>after</b></div>
          	<div class="value"> 
        		<input type="text"  size="10">
        		<a href="#" id="calbutton">
    				<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a>
				<b>and before</b>
				<input type="text"  size="10">
				<a href="#" id="calbutton">
    				<img src="<chrome:imageUrl name="b-calendar.gif"/>" alt="Calendar" width="17" height="16" border="0" align="absmiddle" />
				</a> 
        	</div>
        </div>
        <div class="row" >
        	<div class="leftpanel" >
				<div class="label"><fmt:message key="study.site.irbExpired"/></div>
	        	<div class="value">
	        		<input type="checkbox" id="irbExpired" name="irbExpired" >
	        	</div>	        	
        	</div>
        	<div class="rightpanel" >
				<div class="label"><fmt:message key="study.therapeuticIntentIndicator"/></div>
	        	<div class="value">
	        		<input type="checkbox" id="therapeuticIntentIndicator" name="therapeuticIntentIndicator" >
	        	</div>        		
        	</div>
        </div>
        <div class="row" >
        	<div class="leftpanel"  style="width: 40%">
        		<div class="label"><fmt:message key="study.pendingAmendment"/></div>
		       	<div class="value">
		       		<input type="checkbox" id="pendingAmendment" name="pendingAmendment" >
		       	</div>
	        </div>
	        <div class="rightpanel" style="width: 60%">
				<div class="labelCompanion"><fmt:message key="study.includeEmbeddedCompanionStudies"/></div>
	          	<div class="value">
	          		<input type="checkbox" id="includeCompanion" name="includeCompanion" >
	        	</div>		        
	        </div>	
       	</div>
       <div class="row" >
       		<div class="rightpanel" style="width: 60%">
		       	<div class="label" style="width: 16em;"><fmt:message key="study.pendingSiteAmendment"/></div>
		       	<div class="value">
		       		<input type="checkbox" id="studySiteAmendment" name="studySiteAmendment" >
		       		<tags:hoverHint keyProp="study.pendingSiteAmendment"/>
		       	</div>
	       	</div>
       </div>
     </div>	
</chrome:division>
<chrome:division>
<br>
<div  align="center">
	<tags:button type="button" icon="search" size="small" color="blue" value="Search Study" />
	<tags:button type="button" size="small" color="blue" value="Clear" />
</div>
</chrome:division>
</form:form>
</chrome:box>
</body>
</html>