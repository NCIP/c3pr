<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layout"%>

<div class="tabpane">
    <tabs:levelTwoTabs tab="${tab}" flow="${flow}"/>
    <div class="tabcontent workArea">      
        <tabs:tabControls tabNumber="${tab.number}" isLast="${tab.number < flow.tabCount - 1}"/>
    </div>
</div>
<form:form id="flowredirect">
    <input type="hidden" name="_target${tab.targetNumber}" id="flowredirect-target"/>
    <input type="hidden" name="_page${tab.number}"/>
</form:form>