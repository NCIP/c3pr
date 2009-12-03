<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@attribute name="code" required="true" %>
<%@attribute name="heading" %>
<div class="instructions">
  <div class="summarylabel">${not empty heading ? heading : 'Instructions '}</div>
  <div class="summaryvalue"><spring:message code="${code}" text="There are no instructions for this section." /></div>
</div>

<c:set var="note"><spring:message code="${code}.note" text="dne" /></c:set>
<c:if test="${note ne 'dne'}">
	<div class="instructions">
	  <div class="summarylabel">Note</div>
	  <div class="summaryvalue"><spring:message code="${code}.note" /></div>
	</div>
</c:if>

<c:set var="disclaimer"><spring:message code="${code}.disclaimer" text="dne" /></c:set>
<c:if test="${disclaimer ne 'dne'}">
	<div class="instructions">
	  <div class="summarylabel" style="color:#990000;">Disclaimer</div>
	  <div class="summaryvalue"><spring:message code="${code}.disclaimer" /></div>
	</div>
</c:if>