<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tags:parsedStylesheetLink name="tables"/>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %> 
<%--stylesheets--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!--[if IE]>
<tags:stylesheetLink name="ie"/>
<![endif]-->


<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/calendar-blue.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/common.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/common-search.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/debug.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/extremecomponents.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/fields.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/hint.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/search.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/styles.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/tabbedflow.css"/>
<link rel="stylesheet" type="text/css" href="/c3pr/templates/${skinName}/tigra-menu/tigra-menu.css"/>
<tags:stylesheetLink name="prototype_window/themes/default"/>
<tags:stylesheetLink name="prototype_window/themes/alert"/>
<tags:stylesheetLink name="prototype_window/themes/alert_lite"/>
<tags:stylesheetLink name="prototype_window/themes/alphacube"/>
<tags:stylesheetLink name="prototype_window/themes/darkx"/>
<tags:stylesheetLink name="prototype_window/themes/debug"/>
<tags:stylesheetLink name="prototype_window/themes/mac_os_x"/>
<tags:stylesheetLink name="prototype_window/themes/mac_os_x_dialog"/>
<tags:stylesheetLink name="prototype_window/themes/nuncio"/>
<tags:stylesheetLink name="prototype_window/themes/spread"/>
<tags:stylesheetLink name="lightview"/>



<jwr:script src="/jslib/c3pr.js" />
<jwr:script src="/jslib/js${skinName}.js" />

<!--[if IE]>
<%--<jwr:style src="/csslib/ie.css" />--%>
<![endif]-->