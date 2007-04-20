<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="target" required="true"%>
<%@attribute name="text" required="true"%>
<div class="tabcontrolsbuttons autoclear">
    <a id="flow-left" href="javascript:document.getElementById('flowredirect-target').name='_target${target }';document.getElementById('flowredirect').submit();">${text}</a>
</div>
