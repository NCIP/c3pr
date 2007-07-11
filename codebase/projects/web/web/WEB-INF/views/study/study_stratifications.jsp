<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
</head>

<body>
<form:form method="post" name="form">
<tags:tabFields tab="${tab}"/>
<div>
    <input type="hidden" id="_action" name="_action" value="">
    <input type="hidden" id="_selectedEpoch" name="_selectedEpoch" value="">
    <input type="hidden" id="_selectedStratification" name="_selectedStratification" value="">
    <input type="hidden" id="_selectedAnswer" name="_selectedAnswer" value="">
</div>
<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
	<script>
		var startAnsRowInserterProps_${epochCount.index}= {
			add_row_division_id: "table1",
			skeleton_row_division_id: "dummy-strat-ans-${epochCount.index }",
			initialIndex: 1,
			row_index_indicator: "NESTED.PAGE.ROW.INDEX",
			path: "treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers",
		};
		var stratRowInserterProps_${epochCount.index} = {
			nested_row_inserter: startAnsRowInserterProps_${epochCount.index},
			add_row_division_id: "epoch-${epochCount.index }",
			skeleton_row_division_id: "dummy-strat-${epochCount.index}",
			initialIndex: ${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria)},
			path: "treatmentEpochs[${epochCount.index }].stratificationCriteria",
		};	
		rowInserters.push(stratRowInserterProps_${epochCount.index});
		registerRowInserters();
	</script>
	<tags:minimizablePanelBox title="${epoch.name} : ${epoch.descriptionText }" boxId="${epoch.name}">
	  <p id="instructions">
             Add Stratatification factor:
             <a href="javascript:RowManager.addRow(stratRowInserterProps_${epochCount.index});"><img
                     src="<tags:imageUrl name="checkyes.gif"/>" border="0" alt="Add"></a>&nbsp;&nbsp
         </p>
         <br>

         <table border="0" cellspacing="0" cellpadding="0" id="epoch-${epochCount.index }" class="mytable">
             <tr>
                 <th scope="col" align="left"></th>
                 <th scope="col" align="left"><b>Stratification Factor <span class="red">*</span></b></th>
                 <th scope="col" align="left"><b>add&nbsp;answers<span class="red">*</span></b></th>
                 <th scope="col" align="left"></th>
             </tr>
             <c:forEach items="${command.epochs[epochCount.index].stratificationCriteria}" var="strat" varStatus="status">
                <script>
                    RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}).updateIndex(${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers)});
				</script>
                 <tr id="epoch-${epochCount.index }-${status.index }">
                     <td class="alt"><a
                             href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},${status.index });">
                         <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Delete"></a>
                     </td>
                     <td class="alt">
                         <form:hidden path="epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionNumber"/>
                         <form:textarea path="epochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText" rows="1"
                                        cols="60" cssClass="validate-notEmpty"/>
                     </td>
                     <td class="alt"><a href="javascript:RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}));">
                         <img src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
                     <td>
                         <table border="0" cellspacing="0" cellpadding="0" id="table1" width="50%">
                             <tr>
                                 <td class="alt">Answer</td>
                                 <td class="alt"></td>
                             </tr>
                             <c:forEach var="answer" varStatus="statusAns"
                                        items="${command.epochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers}">
                                 <tr id="table1-${statusAns.index }">
                                     <td class="alt">
                                         <form:input
                                                 path="epochs[${epochCount.index }].stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
                                                 size="30" cssClass="validate-notEmpty"/></td>
                                     <td class="alt"><a
                                             href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}),${statusAns.index });">
                                         <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                                 </tr>
                             </c:forEach>
                         </table>
                     </td>
                 </tr>
             </c:forEach>
         </table>
    </tags:minimizablePanelBox>
</c:forEach>
<tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>
<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
<div id="dummy-strat-${epochCount.index }" style="display:none">
<table>
	<tr>
	    <td class="alt"><a
	            href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX);">
	        <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Delete"></a>
	    </td>
	    <td class="alt">
	        <input type="hidden" name="epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionNumber"/>
	        <textarea name="epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionText" rows="1"
	                       cols="60" cssClass="validate-notEmpty"></textarea>
	    </td>
	    <td class="alt"><a href="javascript:RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX));">
	        <img src="<tags:imageUrl name="checkyes.gif"/>" border="0"></a></td>
	    <td>
	        <table border="0" cellspacing="0" cellpadding="0" id="table1" width="50%">
	            <tr>
	                <td class="alt">Answer</td>
	                <td class="alt"></td>
	            </tr>
                <tr id="table1-0">
                    <td class="alt">
                        <input type="text" name="epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[0].permissibleAnswer"
                                size="30" cssClass="validate-notEmpty"/></td>
                    <td class="alt"><a
                            href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX),0);">
                        <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
                </tr>
	        </table>
	    </td>
	</tr>
</table>
</div>
<div id="dummy-strat-ans-${epochCount.index }" style="display:none">
<table>
	<tr>
	    <td class="alt">
	        <input type="text" name="epochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[NESTED.PAGE.ROW.INDEX].permissibleAnswer"
	                size="30" cssClass="validate-notEmpty"/></td>
	    <td class="alt"><a
	            href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX);">
	        <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
	</tr>
</table>
</div>
</c:forEach>
</body>
</html>