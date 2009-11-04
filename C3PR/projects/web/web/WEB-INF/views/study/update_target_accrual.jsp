<%@ include file="taglibs.jsp"%>
<form:form id="targetAccrualForm">
	<div>
<chrome:box title="Update Target Accrual" id="updateTargetAccrualClass">
	<div class="row">
		<div class="label"><tags:requiredIndicator /><fmt:message key="c3pr.common.targetAccrual"/></div>
		<div class="value">
			<input type="text" name="study.targetAccrualNumber" id="targetAccrualNumber" class="validate-NUMERIC&&notEmpty" />
            <tags:hoverHint keyProp="study.targetAccrualNumber"/>
		</div>
	</div>
</chrome:box>
<div class="flow-buttons">
   	<span class="next">
   		<tags:button type="button" color="red" icon="x" value="Cancel" onclick="closePopup();" />
		<tags:button type="submit" color="green" icon="save" value="Save" />
	</span>
</div>
</div>
</form:form>
