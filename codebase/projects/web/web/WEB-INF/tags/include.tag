<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:parsedStylesheetLink name="tables"/>

<jwr:style src="/csslib/${skinName}.css" />
<jwr:script src="/jslib/c3pr.js" />

<!--[if IE]>
<jwr:style src="/csslib/ie.css" />
<![endif]-->
