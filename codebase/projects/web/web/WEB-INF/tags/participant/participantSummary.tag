<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style type="text/css">
        .labelR { text-align: right; padding: 4px;  font-weight: bold;}
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table1">
	<tr>
		<td valign="top" width="40%">
			<table>
				<tags:tdNameValue name="Name" value="${command.participant.fullName}" columnAttrName="class='labelR'"/>
				<tags:tdNameValue name="Birth date" value="${command.participant.birthDateStr}" columnAttrName="class='labelR'"/>
			</table>
		</td>
		<td valign="top">
			<table>
				<tags:tdNameValue name="Primary identifier" value="${command.participant.primaryIdentifierValue}" columnAttrName="width='50%' class='labelR'"/>
				<tags:tdNameValue name="Gender" value="${command.participant.administrativeGenderCode}" columnAttrName="class='labelR'"/>
			</table>
		</td>
	</tr>
</table>