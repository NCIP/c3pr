<%@ include file="taglibs.jsp"%>

<html>
	<head>
    <title><studyTags:htmlTitle study="${command}" /></title>   
	</head>
	<body>
	
	<script>
	</script>
	    <chrome:box title="Confirmation" autopad="true">
	            	<div class="row" >
	                    <h2><font color="green">Study successfully created.</font></h2>
	                </div>
	                 <div class="row" >
	                 	<table class="tablecontent" width="60%">
							<tr>
								<td width="35%" class="alt" align="left"><b>Short Title<b></td>
								<td class="alt" align="left">${command.trimmedShortTitleText}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>Coordinating Center Study Identifier<b></td>
								<td class="alt" align="left">${command.primaryIdentifier}</td>
							</tr>
							<tr>
								<td width="35%" class="alt" align="left"><b>Study Status<b></td>
								<td class="alt" align="left">${command.coordinatingCenterStudyStatus.code}</td>
							</tr>
						</table>
	                 </div>
			<br/>
	    </chrome:box>
	    <div <c:if test="${command.companionIndicator=='true'}">style="display:none;"</c:if>>
	    <chrome:box title="Companion Studies" autopad="true">
		    <table class="tablecontent" width="60%">
		        <tr>
		            <th width="50%" scope="col" align="left"><b>Companion Study Short Title</b></th>
		            <th width="25%" scope="col" align="left"><b>Status</b></th>
		            <th width="25%" scope="col" align="left"><b>Mandatory</b></th>
		        </tr>
		        <c:forEach items="${command.companionStudyAssociations}" var="companionStudyAssociation">
		            <tr>
		                <td class="alt">${companionStudyAssociation.companionStudy.shortTitleText}</td>
		                <td class="alt">${companionStudyAssociation.companionStudy.coordinatingCenterStudyStatus.code}</td>
		                <td class="alt">${companionStudyAssociation.mandatoryIndicator=="true"?"Yes":"No"}</td>
		                <td class="alt">
		                <input type="button" id="editCompanionStudy" value="Edit" onclick="javascript:document.location='<c:url value='/pages/study/editCompanionStudy?studyId=${companionStudyAssociation.companionStudy.id}' />'"/>
		                </td>

		   	        </tr>	           
		        </c:forEach>
		    </table>
	    </chrome:box>
	    </div>
	    <div class="content buttons autoclear">
	<div class="flow-buttons">
		<span class="next"> 
			<input type="button" value="Manage Study" id="manageStudy" onclick="javascript:document.location='<c:url value='/pages/study/viewStudy?studyId=${command.id}' />'" />
		</span>
	</div>
	</div>
	</body>
</html>
