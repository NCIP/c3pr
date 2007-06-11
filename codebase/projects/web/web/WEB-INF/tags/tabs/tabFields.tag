<%@attribute name="tab" required="true" type="gov.nih.nci.cabig.ctms.web.tabs.Tab" %>
<input type="hidden" id="targetPage" name="_target${tab.targetNumber}"/>
<input type="hidden" id="currentPage" name="_page${tab.number}"/>
