<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@attribute name="identifiersTypes" required="true" type="java.util.Collection"%>

<div id="dummy-row" style="display:none;">
    <table>
        <tr>
            <td class="alt"><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName"
                                    name="systemAssignedIdentifiers[PAGE.ROW.INDEX].systemName" type="text"
                                    class="validate-notEmpty"/>
            </td>
            <td class="alt"><select id="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                    name="systemAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                    class="validate-notEmpty">
                <option value="">--Please Select--</option>
                <c:forEach items="${identifiersTypes}" var="id">
                    <option value="${id.desc}">${id.desc}</option>
                </c:forEach>
            </select>
            </td>
            <td class="alt"><input id="systemAssignedIdentifiers[PAGE.ROW.INDEX].value" name="systemAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
                                   onfocus="javascript:clearField(this)" class="validate-notEmpty"/></td>
            <td class="alt"><input type="radio" id="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="systemAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
                                   value="true"/></td>
            <td class="alt"><a href="javascript:RowManager.deleteRow(systemIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
        </tr>
    </table>
</div>
<div id="dummy-row-organizationIdentifier" style="display:none;">
    <table>
        <tr>
                          
             <td class="alt">
            	<input type="hidden" id="healthcareSitePAGE.ROW.INDEX-hidden"
                    name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite"/>
            	<input class="autocomplete validate-notEmpty" type="text" id="healthcareSitePAGE.ROW.INDEX-input"
                   size="50"
                   value="${command.organizationAssignedIdentifiers[PAGE.ROW.INDEX].healthcareSite.name}"/>
            	<input type="button" id="healthcareSitePAGE.ROW.INDEX-clear"
                    value="Clear"/>
               	<tags:indicator id="healthcareSitePAGE.ROW.INDEX-indicator"/>
              	<div id="healthcareSitePAGE.ROW.INDEX-choices" class="autocomplete"></div>
        	</td>
            
            <td class="alt"><select id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                    name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].type"
                                    class="validate-notEmpty">
                <option value="">--Please Select--</option>
                <c:forEach items="${identifiersTypes}" var="id">
                    <option value="${id.desc}">${id.desc}</option>
                </c:forEach>
            </select>
            </td>
            <td class="alt"><input id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].value" type="text"
                                   onfocus="javascript:clearField(this)" class="validate-notEmpty"/></td>
            <td class="alt"><input type="radio" id="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator" name="organizationAssignedIdentifiers[PAGE.ROW.INDEX].primaryIndicator"
                                   value="true"/></td>
            <td class="alt"><a href="javascript:RowManager.deleteRow(organizationIdentifierRowInserterProps,PAGE.ROW.INDEX);"><img
                    src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
        </tr>
    </table>
</div>