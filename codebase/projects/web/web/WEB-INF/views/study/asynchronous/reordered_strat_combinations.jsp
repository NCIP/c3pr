<%--
Copyright Duke Comprehensive Cancer Center and SemanticBits
 
  Distributed under the OSI-approved BSD 3-Clause License.
  See  https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="epochCountIndex" value="${param.epochCountIndex}" />

	<div id="sgCombinations_${epochCountIndex}"><!--This part is loaded onload and is updated with new content when generate str grps is clicked-->
	<chrome:division title="Stratum Groups (drag/drop the groups to re-order)">
	<!--stratum groups combinations display section-->
	
	<br />			
		<c:if test="${fn:length(command.study.epochs[epochCountIndex].stratificationCriteria) > 0}">
		
		<table width="75%">
		<tr><td width="80%">
			<table border="1" class="tablecontent" width="100%">
			<tr>
				<th><img src="<tags:imageUrl name="b-arrowUp.gif"/>" border="0" style="visibility:hidden"></th>
				<th><img src="<tags:imageUrl name="b-arrowDown.gif"/>" border="0" style="visibility:hidden"></th>
				<th width="30%">Group Number&nbsp;<tags:hoverHint id="study.stratumGroup.stratumGroupNumber-${epochCountIndex}" keyProp="study.stratumGroup.stratumGroupNumber"/></th>					
				<th width="65%">Answer Combination&nbsp;<tags:hoverHint id="study.stratumGroup.answerCombinations-${epochCountIndex}" keyProp="study.stratumGroup.answerCombinations"/></th>
				<th width="5%"></th>
			</tr>
			</table>
			<table id="sgCombinationsTable_${epochCountIndex}" border="1" class="tablecontent"  width="100%">
				<tbody id="sortablelist_${epochCountIndex}">
					<c:forEach var="stratumGroup" varStatus="statusStratumGroup"
						items="${command.study.epochs[epochCountIndex].stratumGroups}">	
						
						<c:if test="${command.study.epochs[epochCountIndex].stratificationIndicator == 'true' }">					
						<tr id="stratumGroupTable1_${epochCountIndex}-${statusStratumGroup.index}" style="cursor:move">
							<td>
								<c:if test="${statusStratumGroup.index != 0}">
									<a href="javascript:moveElementUpforList('sortablelist_${epochCountIndex}', '${epochCountIndex}-${statusStratumGroup.index}');updateStartumGroups('sortablelist_${epochCountIndex}');" >
									<img src="<tags:imageUrl name="b-arrowUp.gif"/>" border="0"></a>
								</c:if>
							</td>
							<td>
								<c:if test="${fn:length(command.study.epochs[epochCountIndex].stratumGroups) != statusStratumGroup.index + 1}">
									<a href="javascript:moveElementDownforList('sortablelist_${epochCountIndex}','${epochCountIndex}-${statusStratumGroup.index}');updateStartumGroups('sortablelist_${epochCountIndex}');" >
									<img src="<tags:imageUrl name="b-arrowDown.gif"/>" border="0"></a>
								</c:if>
							</td>
							<td width="30%">
								${stratumGroup.stratumGroupNumber}
							</td>					
							<td width="65%">${stratumGroup.answerCombinations}</td>
							<td width="5%">
							<a href="javascript:deleteStratumGroup('${command.study.epochs[epochCountIndex].id}', '${stratumGroup.id}');">
								<img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a>
							</td>					
						</tr>
						</c:if>
					</c:forEach>					
				</tbody>
			</table>
		</td>
		<td align="left" width="20%"><img id="reorderGroupsInd-${epochCountIndex}" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" style="display:none">
		</td></tr>
		</table>
		
		</c:if><!--This part is loaded onload and is updated with new content when generate str grps is clicked-->
		<script type="text/javascript" language="javascript">
	  		Sortable.create('sortablelist_${epochCountIndex}',{tag:'TR', constraint:false, onUpdate:postProcessDragDrop});
		</script>
		<br>
	</chrome:division>
	</div>
	


