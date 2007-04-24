<script>
function makeEditable(this){

}
</script>
<%@attribute name="path" required="true"%>
<div id="div-${path }" onClick="makeEditable(this)">
<form:input path="${path}"/>
</div>
