<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="printPageLink">
	<script>
		function launchPrint(){
			var windowRef = window.open("/c3pr/print_view.jsp", "Print Window", "scrollbars=yes,menubar=no,width=730,height=600,toolbar=no");
			windowRef.focus()
		}
	</script>
		<a href="#">
			<img src="/c3pr/templates/mocha/images/printer.png" style="vertical-align:middle;" alt="" onclick="launchPrint()"/> Print this page
		</a>
</div>
