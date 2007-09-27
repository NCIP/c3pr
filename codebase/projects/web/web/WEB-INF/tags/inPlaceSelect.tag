<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="path" required="true"%>
<%@attribute name="commanSepOptVal" required="true"%>
<%@attribute name="value" required="true"%>
<%@attribute name="pathToGet"%>
<span id="${path}-id">${value}</span>
<script type="text/javascript">
	var editor_${path}=new Ajax.InPlaceCollectionEditor('${path}-id', document.URL,
								{collection: ${commanSepOptVal},
			  						callback: function(form, value) {
	 														 		return '_asynchronous=true&_asyncMethodName=doInPlaceEdit&_ajaxInPlaceEditParam=${path}&_pathToGet=${pathToGet}&${path}=' + escape(value);
	 														  	}
								}
							);    						
</script>