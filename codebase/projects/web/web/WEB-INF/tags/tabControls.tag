<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- must specify either tab & flow or tabNumber and isLast --%>
<%@attribute name="tab" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@attribute name="flow" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" %>
<%@attribute name="tabNumber"%>
<%@attribute name="isLast"%>
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
		                <input type="submit" id="flow-prev" class="tab${backToTab}" value="&laquo; ${willSave ? 'Save &amp; ' : ''}Back"/>
		            </c:if>
		        </span>
		        <span class="next">
					<c:if test="${tabNumber == goToTab}">
						<c:set var="isLast" value="true"></c:set>
						<input type="hidden" name="_finish" value="true">
					</c:if>	
		        	 <c:if test="${not isLast && willSave}">
		                <input type="submit" id="flow-update" class="tab${tabNumber}" value="Save"/>
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
		            <input type="submit" id="${isLast == 'true'?'flow-finish':'flow-next'}" class="tab${goToTab}" value="${continueLabel} &raquo;"/>
		        </span>
		    </div> 
		</c:when>
		<c:otherwise>
			<div class="flow-buttons">
		        <span class="prev">
		            <c:if test="${tabNumber > 0}">
		                <input type="submit" id="flow-prev" class="tab${tabNumber - 1}" value="&laquo; ${willSave ? 'Save &amp; ' : ''}Back"/>
		            </c:if>
		        </span>
		        <span class="next">
		            <%-- <input type="reset" value="Reset tab"/> 
		
		            <c:if test="${not isLast}">
		                <input type="submit" id="flow-update" class="tab${tabNumber}" value="${willSave ? 'Save' : 'Update'}"/>--%>
		            <c:if test="${not isLast && willSave}">
			            <input type="submit" id="flow-update" class="tab${tabNumber}" value="Save"/>
		            </c:if>
					<c:if test="${empty continueLabel || continueLabel==''}">
			            <c:set var="continueLabel" value="${isLast || willSave ? 'Save' : ''}"/>
			            <c:if test="${not empty continueLabel && not isLast}">
			                <c:set var="continueLabel" value="${continueLabel} &amp; "/>
			            </c:if>
			            <c:if test="${not isLast}">
			                <c:set var="continueLabel" value="${continueLabel}Continue"/>
			            </c:if>
					</c:if>            
		            <input type="submit" id="flow-next"  value="${continueLabel} &raquo;"/>
		        </span>
			</div>
		</c:otherwise>
	</c:choose>
</div>