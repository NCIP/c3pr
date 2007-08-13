<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="method" required="true"%>
<%@attribute name="divElement" required="true"%>
<%@attribute name="viewName"%>
<%@attribute name="formName"%>
<%@attribute name="params"%>
<%@attribute name="onSuccess"%>
<%@attribute name="onComplete"%>
<%@attribute name="onFailure"%>
<c:set var="formId" value="${empty formName?'command':formName}"></c:set>
<c:set var="callbackOpts" value=""></c:set>
<c:if test="${! empty onComplete}">
<c:set var="callbackOpts" value="${callbackOpts}onComplete:${onComplete },"></c:set>
</c:if>
<c:if test="${! empty onSuccess}">
<c:set var="callbackOpts" value="${callbackOpts}onSuccess:${onSuccess },"></c:set>
</c:if>
<c:if test="${! empty onFailure}">
<c:set var="callbackOpts" value="${callbackOpts}onFailure:${onFailure },"></c:set>
</c:if>

new Ajax.Updater(${divElement},$("${formId}").action, 
					{parameters:"decorator=nullDecorator&_asynchronous=true&_asyncMethodName=${method}&_asyncViewName=${viewName}&"+Form.serialize('${formId}')+"&${params}",
					${callbackOpts} asynchronous:true, evalScripts:true});