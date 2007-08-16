<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
</head>
<body>
<script>
stratumGroupRowInserter_${epochCountIndex}.initialIndex= ${fn:length(command.treatmentEpochs[epochCountIndex].stratumGroups)};
RowManager.registerRowInserter(stratumGroupRowInserter_${epochCountIndex});
</script>

<table border="1" class="tablecontent" id="stratumGroupTable1_${epochCountIndex}" width="50%">
 <tr>
     <th>Stratum Group Number</th>
     <th>Stratum Group</th>
     <th></th>
 </tr>

 <c:forEach var="stratumGroup" varStatus="statusStratumGroup" items="${command.treatmentEpochs[epochCountIndex].stratumGroups}">
     <tr id="stratumGroupTable1_${epochCountIndex}-${statusStratumGroup.index }">
         <td class="alt">
<!--     <input type="text" id="treatmentEpochs[${epochCountIndex}].stratumGroups[${statusStratumGroup.index}].stratumGroupNumber"
             name="treatmentEpochs[${epochCountIndex}].stratumGroups[${statusStratumGroup.index}].stratumGroupNumber"
             value="${command.treatmentEpochs[epochCountIndex].stratumGroups[statusStratumGroup.index].stratumGroupNumber}" size="1" class="validate-notEmpty"/> -->
             
             <tags:inPlaceEdit value="${command.treatmentEpochs[epochCountIndex].stratumGroups[statusStratumGroup.index].stratumGroupNumber}"
             				   path="${command.treatmentEpochs[epochCountIndex].stratumGroups[statusStratumGroup.index].stratumGroupNumber}" />        
                     </td>
         <td class="alt">
         	${command.treatmentEpochs[epochCountIndex].stratumGroups[statusStratumGroup.index].answerCombinations}
             </td>
         <td class="alt"><a
                 href="javascript:RowManager.deleteRow(stratumGroupRowInserter_${epochCountIndex},${statusStratumGroup.index });">
             <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
     </tr>
 </c:forEach>
 </table>
<br/>
</body>
</html>
