<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		                <input type="image" id="flow-prev" class="tab${backToTab}" value="&laquo; ${willSave ? 'Save &amp; ' : ''}Back" src="/c3pr/images/flow-buttons/${willSave ? 'save' : ''}back_btn.png"/>
		            </c:if>
		        </span>
		        <span class="next">
					<c:if test="${tabNumber == goToTab}">
						<c:set var="isLast" value="true"></c:set>
						<input type="hidden" name="_finish" value="true">
					</c:if>	
		        	 <c:if test="${not isLast && willSave}">
		                <input type="image" id="flow-update" class="tab${tabNumber}" value="Save" src="/c3pr/images/flow-buttons/save_btn.png"/>
		            </c:if>
					<c:if test="${empty continueLabel || continueLabel==''}">
			            <c:set var="continueLabel" value="${isLast || willSave ? 'save' : ''}"/>
					</c:if>			        
    				<%--<c:if test="${not empty continueLabel && not isLast && goToTab > tabNumber}">
		                <c:set var="continueLabel" value="${continueLabel} &amp; "/>
		            </c:if>--%>
		            <c:if test="${not isLast }">
		                <c:set var="continueLabel" value="${continueLabel}continue"/>
		            </c:if>
		            <input type="image" id="${isLast == 'true'?'flow-finish':'flow-next'}" class="tab${goToTab}" value="${continueLabel} &raquo;" src="/c3pr/images/flow-buttons/${continueLabel}_btn.png"/>
		        </span>
		    </div> 
		</c:when>
		<c:otherwise>
			<div class="flow-buttons">
		        <span class="prev">
		            <c:if test="${tabNumber > 0 && (empty isSummaryPage || (empty isSummaryPage && !isSummaryPage)) }">
		                <input type="image" id="flow-prev" class="tab${tabNumber - 1}" value="&laquo; ${willSave ? 'Save &amp; ' : ''}Back" src="/c3pr/images/flow-buttons/${willSave ? 'save' : ''}back_btn.png"/>
		            </c:if>
		        </span>
		        <span class="next">
		            <c:if test="${not isLast && willSave}">
			            <input type="image" id="flow-update" class="tab${tabNumber}" value="Save" src="/c3pr/images/flow-buttons/save_btn.png"/>
		            </c:if>
					<c:if test="${empty continueLabel || continueLabel==''}">
			            <c:set var="continueLabel" value="${isLast || willSave ? 'save' : ''}"/>
			            <c:if test="${not isLast}">
			                <c:set var="continueLabel" value="${continueLabel}continue"/>
			            </c:if>
					</c:if>
					<c:choose>
						<c:when	test="${continueLabel == 'save' || continueLabel == 'back' || continueLabel == 'saveback' || continueLabel == 'savecontinue'}">            
		           		 <input type="image" id="flow-next"  value="${continueLabel}" src="/c3pr/images/flow-buttons/${continueLabel}_btn.png"/>
						</c:when>
						<c:otherwise>
							<input type="submit" id="flow-next"  value="${continueLabel}" />
						</c:otherwise>
					</c:choose>
		        </span>
			</div>
		</c:otherwise>
	</c:choose>
</div>