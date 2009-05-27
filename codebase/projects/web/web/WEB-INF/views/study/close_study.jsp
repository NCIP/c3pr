<%@ include file="taglibs.jsp"%>
<form:form id="closeStudyForm">
<chrome:box title="Close Study" id="closeStudyClass">
	<tags:instructions code="close_study" />
	<table>
		<tr>
			<th  width="5%"></th>
			<th><fmt:message key="study.possibleCloseStatus" /><tags:hoverHint keyProp="study.possibleCloseStatus" /></th>
		</tr>
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Closed_To_Accrual_And_Treatment" onclick="javascript:parent.setCloseStatus('Closed_To_Accrual_And_Treatment');"/>
			</td>
			<td align="left">Closed To Accrual And Treatment</td>
		</tr>		
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Closed_To_Accrual" onclick="javascript:parent.setCloseStatus('Closed_To_Accrual');"/>
			</td>
			<td align="left">Closed To Accrual</td>
		</tr>
		<c:if test="${closed == 'Temporarily_Close'}">
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Temporarily_Closed_To_Accrual_And_Treatment" onclick="javascript:parent.setCloseStatus('Temporarily_Closed_To_Accrual_And_Treatment');"/>
			</td>
			<td align="left">Temporarily Closed To Accrual And Treatment</td>
		</tr>
		<tr>
			<td>
				<input class="studyStatus" type="radio" value="Temporarily_Closed_To_Accrual" onclick="javascript:parent.setCloseStatus('Temporarily_Closed_To_Accrual');"/>
			</td>
			<td align="left">Temporarily Closed To Accrual</td>
		</tr>
		</c:if>
	</table>
</chrome:box>
<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button type="button" color="green" icon="save" value="parent.closeStudy()" />
	</span>
</div>
</form:form>