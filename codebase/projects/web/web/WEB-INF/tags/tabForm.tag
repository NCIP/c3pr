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
<%@attribute name="displayErrors"%>
<%@attribute name="htmlContent"%>
<%@attribute name="instructions" fragment="true" %>
<%@attribute name="singleFields" fragment="true" %>
<%@attribute name="repeatingFields" fragment="true" %>
<%@attribute name="localButtons" fragment="true" %>


<c:if test="${empty displayErrors}"><c:set var="displayErrors" value="${true}"/> </c:if>
<c:if test="${empty willSave}"><c:set var="willSave" value="${true}"/></c:if>
<form:form name="${formName}" enctype="${enctype}">
<chrome:box title="${empty title ? tab.shortTitle : title}" id="${boxId}" cssClass="${boxClass}" htmlContent="${htmlContent}">
    <chrome:flashMessage/>
        <tags:tabFields tab="${tab}"/>
        <chrome:division id="single-fields">
            <c:if test="${not empty instructions}"><p class="instructions"><jsp:invoke fragment="instructions"/></p></c:if>
            <c:if test="${displayErrors == 'true'}"> <tags:errors path="*"/></c:if>
            <jsp:invoke fragment="singleFields"/>
        </chrome:division>
        <jsp:invoke fragment="repeatingFields"/>
         <div class="division"></div>
</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
  </form:form>