<%@attribute name="tab" required="true" type="edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.Tab" %>
<input type="hidden" id="targetPage" name="_target${tab.targetNumber}"/>
<input type="hidden" id="currentPage" name="_page${tab.number}"/>
