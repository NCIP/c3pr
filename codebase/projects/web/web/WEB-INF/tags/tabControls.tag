<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%-- must specify either tab & flow or tabNumber and isLast --%>
<%@attribute name="tab" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" %>
<%@attribute name="tabNumber"%>
<%@attribute name="isLast"%>
<%@attribute name="isSummaryPage"%>
<%@attribute name="willSave"%>
<%@attribute name="goToTab"%>
<%@attribute name="backToTab"%>
<%@attribute name="customButton"%>
<%-- added continueLabel attribute to customize the continue label display text. If not provided it will default to 'Save' or 'Save and Continue'--%>
<%@attribute name="continueLabel"%>
<%@attribute name="localButtons" fragment="true" %>
<c:set var="tabNumber" value="${empty tabNumber ? tab.number : tabNumber}"/>
<c:set var="isLast" value="${empty isLast ? not (tab.number < flow.tabCount - 1) : isLast}"/>
<!-- following attributes to customize the button paint logic and go to desired tab -->
<c:set var="customButton" value="${param.customButton}"/>
<c:set var="backToTab" value="${backToTab}"/>
<c:set var="goToTab" value="${goToTab}"/>
<div class="content buttons autoclear">
    <div class="local-buttons">
        <jsp:invoke fragment="localButtons"/>
    </div>
	<c:choose>
		<c:when test="${not empty customButton}">
			<div class="flow-buttons">
		        <span class="prev">
		            <c:if test="${tabNumber > 0 && tabNumber != backToTab }">
		                <tags:button type="submit" color="blue" id="flow-prev" cssClass="tab${backToTab}" value="${willSave && ((empty isSummaryPage || (!empty isSummaryPage && !isSummaryPage)))? 'Save &amp; ' : ''}Back" icon="${willSave && ((empty isSummaryPage || (!empty isSummaryPage && !isSummaryPage)))? 'Save &amp; ' : ''}Back"/>
		            </c:if>
		        </span>
		        <span class="next">
					<c:if test="${tabNumber == goToTab}">
						<c:set var="isLast" value="true"></c:set>
						<input type="hidden" name="_finish" value="true">
					</c:if>	
		        	 <c:if test="${not isLast && willSave}">
		                <tags:button markupWithTag="button" color="blue" id="flow-update" cssClass="tab${tabNumber}" value="Save" icon="Save"/>
		            </c:if>
					<c:if test="${empty continueLabel || continueLabel==''}">
			            <c:set var="continueLabel" value="${isLast || willSave ? 'Save' : ''}"/>
					</c:if>			        
    				<c:if test="${not empty continueLabel && not isLast && goToTab > tabNumber}">
		                <c:set var="continueLabel" value="${continueLabel} &amp; "/>
		            </c:if>
		            <c:if test="${not isLast }">
		                <c:set var="continueLabel" value="${continueLabel}Continue"/>
		            </c:if>
		            <tags:button type="submit" color="green" id="${isLast == 'true'?'flow-finish':'flow-next'}" cssClass="tab${goToTab}" value="${continueLabel}" icon="${continueLabel}"/>
		        </span>
		    </div> 
		</c:when>
		<c:otherwise>
			<div class="flow-buttons">
		        <span class="prev">
		            <c:if test="${tabNumber > 0 }">
		                <tags:button type="submit" color="blue" id="flow-prev" cssClass="tab${tabNumber - 1}" value="${willSave && ((empty isSummaryPage || (!empty isSummaryPage && !isSummaryPage)))? 'Save &amp; ' : ''}Back" icon="${willSave && ((empty isSummaryPage || (!empty isSummaryPage && !isSummaryPage)))? 'Save &amp; ' : ''}Back"/>
		            </c:if>
		        </span>
		        <span class="next">
		            <c:if test="${not isLast && willSave}">
			            <tags:button type="submit" color="blue" id="flow-update" cssClass="tab${tabNumber}" value="Save" icon="save"/>
		            </c:if>
					<c:if test="${empty continueLabel || continueLabel==''}">
			            <c:set var="continueLabel" value="${isLast || willSave ? 'Save' : ''}"/>
			            <c:if test="${not isLast && not empty continueLabel}">
			                <c:set var="continueLabel" value="${continueLabel} &amp; Continue"/>
			            </c:if>
			            <c:if test="${not isLast && empty continueLabel}">
			                <c:set var="continueLabel" value="Continue"/>
			            </c:if>
					</c:if>
					<c:choose>
						<c:when	test="${continueLabel == 'Save' || continueLabel == 'Back' || continueLabel == 'Save &amp; Back' || continueLabel == 'Save &amp; Continue' || continueLabel == 'Continue'}">            
		           		 <tags:button type="submit" color="green" id="flow-next"  value="${continueLabel}" icon="${continueLabel}"/>
						</c:when>
						<c:otherwise>
							<tags:button type="submit" color="green" id="flow-next"  value="${continueLabel}" icon="check"/>
						</c:otherwise>
					</c:choose>
		        </span>
			</div>
		</c:otherwise>
	</c:choose>
</div>