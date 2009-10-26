<%@ include file="taglibs.jsp"%>
<html>
<head>
	<style>
		*{zoom:1}

		div.row div.newlabel {
			font-weight: normal;
			float: left;
			margin-left: 0.5em;
			margin-right: 0.5em;
			text-align: right; /* default width; pages might override */
			width: 19em;
			font-weight:bold;
		}
	</style>
<!--[if lte IE 7]>
	<style>
		#workflow-tabs {
			top:-15px;
		}
		#workflow-tabs li.selected {
			margin-top:-4px;
			padding-top:4px;
		}
		#workflow-tabs li.selected a{
			padding-bottom:1px;
			padding-top:1px;
		}
	</style>
<![endif]-->
    <title><studyTags:htmlTitle study="${command.study}" /></title>
    <tags:dwrJavascriptLink objects="StudyAjaxFacade" />
    <script type="text/javascript">
    var consentRowInserterProps = {
    	    add_row_division_id: "consent", 	        /* this id belongs to element where the row would be appended to */
    	    skeleton_row_division_id: "dummy-row-consent",
    	    initialIndex: ${fn:length(command.study.consents)},                            /* this is the initial count of the rows when the page is loaded  */
    	    isAdmin: ${isAdmin == 'true'},
    	    path: "study.consents"                               /* this is the path of the collection that holds the rows  */
    	};

    RowManager.addRowInseter(consentRowInserterProps);
	</script>
</head>
<body>
<form:form id="consentForm">
	<chrome:box title="Consent">
	<tags:tabFields tab="${tab}" />
	<tags:instructions code="study_consents" />
	<tags:errors path="study.consents" />
	<chrome:division>
		<div class="row">
			<div class="newlabel"><tags:requiredIndicator/><fmt:message key="study.consentRequired"/></div>
			<div class="value">
				  <form:select path="study.consentRequired" cssClass="validate-notEmpty" >
				  	<form:options items="${consentRequired}" itemLabel="desc" itemValue="code" />
				  </form:select>
				  <tags:hoverHint keyProp="study.consentRequired" />
			</div>
		</div>
	</chrome:division>
<br>
<!-- CONSENT TABLE START -->
<table id="consent" width="50%" class="tablecontent">
	<tr>
		<th><tags:requiredIndicator/><fmt:message key="registration.consentVersion"/></th>
		<th></th>
	</tr>
    <c:forEach items="${command.study.consents}" var="consent"  varStatus="status" >
    	<tr id="consent-${status.index}">
			<td>${consent.name}</td>
			<td width="10%">
				<a href="javascript:RowManager.deleteRow(consentRowInserterProps,${status.index},'${consent.id==null?'HC#':'ID#'}${consent.id==null?consent.hashCode:consent.id}');">
					<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
				</a>
			</td>
		</tr>
    </c:forEach>
</table>
<br>
<div align="left">
	<tags:button type="button" color="blue" icon="add" value="Add Consent" onclick="RowManager.addRow(consentRowInserterProps);" size="small"/>
    <br>
</div>
</chrome:box>
<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" />
</form:form>
<!-- DUMMY SECIION START -->
<div id="dummy-row-consent" style="display:none;">
<table>
	<tr>
		<td><input id="consents[PAGE.ROW.INDEX].name" name="study.consents[PAGE.ROW.INDEX].name" type="text" size="60" class="validate-notEmpty" /></td>
		<td><a href="javascript:RowManager.deleteRow(consentRowInserterProps,PAGE.ROW.INDEX, -1);"><img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>
<!-- DUMMY SECIION END -->
</body>
</html>