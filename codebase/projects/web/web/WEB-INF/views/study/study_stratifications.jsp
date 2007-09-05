<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<script>		                
	function getStratumGroups(epochCountIndex){
		<tags:tabMethod method="generateStratumGroups" viewName="/study/asynchronous/strat_combinations" divElement="'sgCombinations_'+epochCountIndex" 
		                javaScriptParam="'epochCountIndex='+epochCountIndex"  />        
	}
	
	function clear(epochCountIndex){
		<tags:tabMethod method="clearStratumGroups" viewName="/study/asynchronous/strat_combinations" divElement="'sgCombinations_'+epochCountIndex" 
		                javaScriptParam="'epochCountIndex='+epochCountIndex"  /> 
	}
</script>
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
                epochCountIndex: ${epochCount.index},
                postProcessRowDeletion: function(object){
	                clear(object.epochCountIndex);                	
			    }
            };
            var stratRowInserterProps_${epochCount.index} = {
                nested_row_inserter: startAnsRowInserterProps_${epochCount.index},
                add_row_division_id: "epoch-${epochCount.index }",
                skeleton_row_division_id: "dummy-strat-${epochCount.index}",
                initialIndex: ${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria)},
                path: "treatmentEpochs[${epochCount.index }].stratificationCriteria",
                epochCountIndex: ${epochCount.index},
                postProcessRowInsertion: function(object){
                	clear(object.epochCountIndex);                	
			    }
            };
            RowManager.addRowInseter(stratRowInserterProps_${epochCount.index});
            RowManager.registerRowInserters();
        </script>
        <tags:minimizablePanelBox title="${epoch.name}" boxId="${epoch.name}">
            <p id="instructions">
                <input type="button" value="Add Stratification Factor" onclick="RowManager.addRow(stratRowInserterProps_${epochCount.index});"/>
            </p>
            <br>

            <table id="epoch-${epochCount.index }" class="tablecontent">
                <tr>
                    <th></th>
                    <th><span class="required-indicator">Stratification Question</span></th>
                    <th></th>
                    <th><span class="required-indicator">Stratification Answer</span></th>
                </tr>
                <c:forEach items="${command.treatmentEpochs[epochCount.index].stratificationCriteria}" var="strat" varStatus="status">
                    <script>
                        RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}).updateIndex(${fn:length(command.treatmentEpochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers)});
                    </script>
                    <tr id="epoch-${epochCount.index }-${status.index }">
                        <td class="alt"><a
                                href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},${status.index });">
                            <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Delete"></a>
                        </td>
                        <td>
                            <form:textarea path="treatmentEpochs[${epochCount.index }].stratificationCriteria[${status.index}].questionText" rows="1"
                                           cols="60" cssClass="validate-notEmpty"/>
                        </td>
                        <td>
                            <input type="button" value="Add Answer"
                                   onclick="RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},${status.index}));"/>
                        </td>
                        <td>
                            <table class="tablecontent" id="table1" width="50%">
                                <tr>
                                    <th></th>
                                    <th></th>
                                </tr>
                                <c:forEach var="answer" varStatus="statusAns"
                                           items="${command.treatmentEpochs[epochCount.index].stratificationCriteria[status.index].permissibleAnswers}">
                                    <tr id="table1-${statusAns.index }">
                                        <td class="alt">
                                            <form:input
                                                    path="treatmentEpochs[${epochCount.index }].stratificationCriteria[${status.index}].permissibleAnswers[${statusAns.index}].permissibleAnswer"
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
        <br /><hr align="left" width="95%">    
        <div id="stratumButton"><br/>    
        	<input type='button' onclick='getStratumGroups("${epochCount.index}")' value='Generate Stratum Groups'/>   
		</div>	    
		<!--stratum groups combinations display section-->
		<script>
			var stratumGroupRowInserter_${epochCount.index} = {
			    add_row_division_id: "stratumGroupTable1_${epochCount.index}", 	        
			    skeleton_row_division_id: "dummy-row",
			    initialIndex: -1,
			    path: "treatmentEpochs[${epochCount.index}].stratumGroups"
			};
			
			// will do this for edit flow to get tables on load 
			//	getStratumGroups("${epochCount.index}"); 
		</script>
		<br/>
		<div id="sgCombinations_${epochCount.index}">		
		</div>
		<!--stratum groups combinations display section--> 
		<c:if test="${fn:length(epoch.stratificationCriteria) > 0}">   
        	<script>getStratumGroups(0);</script>
        </c:if>
        
        </tags:minimizablePanelBox>
    </c:forEach>
    
    <tags:tabControls tab="${tab}" flow="${flow}" localButtons="${localButtons}" willSave="${willSave}"/>
</form:form>

<c:forEach items="${command.treatmentEpochs}" var="epoch" varStatus="epochCount">
    <div id="dummy-strat-${epochCount.index }" style="display:none">
        <table>
            <tr>
                <td><a
                        href="javascript:RowManager.deleteRow(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX);">
                    <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="Delete"></a>
                </td>
                <td>
                    <input type="hidden" name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionNumber"/>
                    <textarea name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].questionText" rows="1"
                              cols="60" class="validate-notEmpty"></textarea>
                </td>
                <td>
                    <input type="button" value="Add Answer"
                           onclick="RowManager.addRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX));"/>
                </td>
                <td>
                    <table class="tablecontent" id="table1" width="50%">
                        <tr>
                            <th></th>
                            <th></th    >
                        </tr>
                        <tr id="table1-0">
                            <td>
                                <input type="text" name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[0].permissibleAnswer"
                                       size="30" class="validate-notEmpty"/></td>
                            <td><a
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
                <td>
                    <input type="text" name="treatmentEpochs[${epochCount.index }].stratificationCriteria[PAGE.ROW.INDEX].permissibleAnswers[NESTED.PAGE.ROW.INDEX].permissibleAnswer"
                           size="30" class="validate-notEmpty"/></td>
                <td><a
                        href="javascript:RowManager.deleteRow(RowManager.getNestedRowInserter(stratRowInserterProps_${epochCount.index},PAGE.ROW.INDEX),NESTED.PAGE.ROW.INDEX);">
                    <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
            </tr>
        </table>
    </div>
</c:forEach>
</body>
</html>