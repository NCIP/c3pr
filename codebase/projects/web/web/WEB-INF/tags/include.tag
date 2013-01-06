<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jwr:style src="/csslib/${skinName}.zcss" />
<jwr:script src="/jslib/c3pr.zjs" useRandomParam="false" />
<jwr:script src="/jslib/js${skinName}.zjs"  useRandomParam="false" />
<jwr:style src="/css/commonui.zcss" />

<%--
<!--[if !IE]><!-->
<jwr:style src="/csslib/notie.zcss" />
<!-- <![endif]-->
--%>

<!--[if IE]>
<jwr:style src="/csslib/ie.zcss" />
<![endif]-->

