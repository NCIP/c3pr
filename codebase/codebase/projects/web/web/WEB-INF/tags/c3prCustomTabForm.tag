<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@attribute name="tab" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<%@attribute name="flow" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Flow" %>
<%@attribute name="willSave"%>
<%@attribute name="title"%>
<%@attribute name="formName"%>
<%@attribute name="enctype"%>
<%@attribute name="boxId"%>
<%@attribute name="boxClass" %>
<%@attribute name="instructions" fragment="true" %>
<%@attribute name="singleFields" fragment="true" %>
<%@attribute name="repeatingFields" fragment="true" %>
<%@attribute name="localButtons" fragment="true" %>
<%@attribute name="needReset"%>
<%@attribute name="tabNumber"%>
<%@attribute name="isLast"%>
<%-- added continueLabel attribute to customize the continue label display text. If not provided it will default to 'Save' or 'Save and Continue'--%>
<%@attribute name="continueLabel"%>
<c:set var="tabNumber" value="${empty tabNumber ? tab.number : tabNumber}"/>
<c:set var="isLast" value="${empty isLast ? not (tab.number < flow.tabCount - 1) : isLast}"/>

<c:set var="needReset" value="${empty needReset ? true : needReset}"/>
<c:if test="${empty willSave}"><c:set var="willSave" value="${true}"/></c:if>
<chrome:box title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}">
    <chrome:flashMessage/>
    <form:form name="${formName}" enctype="${enctype}">
        <tags:tabFields tab="${tab}"/>
        <chrome:division id="single-fields">
            <c:if test="${not empty instructions}"><p class="instructions"><jsp:invoke fragment="instructions"/></p></c:if>
            <tags:errors path="*"/>
            <jsp:invoke fragment="singleFields"/>
        </chrome:division>
        <jsp:invoke fragment="repeatingFields"/>
        <div class="content buttons autoclear">
    <div class="local-buttons">
        <jsp:invoke fragment="localButtons"/>
    </div>
    <div class="flow-buttons">
        <span class="prev">
            <c:if test="${tabNumber > 0}">
                <input type="submit" id="flow-prev" class="tab${tabNumber - 1}" value="&laquo; ${willSave ? 'Save &amp; ' : ''}Back"/>
            </c:if>
        </span>
        <span class="next">
        	<c:if test="${needReset == true}">
            	<input type="reset" value="Reset tab"/>
            </c:if>

            <c:if test="${not isLast}">
                <input type="submit" id="flow-update" class="tab${tabNumber}" value="${willSave ? 'Save' : 'Update'}"/>
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
            <input type="submit" id="flow-next" value="${continueLabel} &raquo;"/>
        </span>
    </div>
</div>
    </form:form>
</chrome:box>
