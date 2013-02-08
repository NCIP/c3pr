<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See http://ncip.github.com/c3pr/LICENSE.txt for details.
--%>
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
stratumGroupRowInserter_${epochCountIndex}.initialIndex= ${fn:length(command.study.epochs[epochCountIndex].stratumGroups)};
RowManager.registerRowInserter(stratumGroupRowInserter_${epochCountIndex});
</script>

<table border="1" class="tablecontent" id="stratumGroupTable1_${epochCountIndex}" width="50%">
 <tr>
     <th>Stratum Group Number</th>
     <th>Stratum Group</th>
     <th></th>
 </tr>

 <c:forEach var="stratumGroup" varStatus="statusStratumGroup" items="${command.study.epochs[epochCountIndex].stratumGroups}">
     <tr id="stratumGroupTable1_${epochCountIndex}-${statusStratumGroup.index }">
         <td class="alt">
<!--     <input type="text" id="epochs[${epochCountIndex}].stratumGroups[${statusStratumGroup.index}].stratumGroupNumber"
             name="study.epochs[${epochCountIndex}].stratumGroups[${statusStratumGroup.index}].stratumGroupNumber"
             value="${command.study.epochs[epochCountIndex].stratumGroups[statusStratumGroup.index].stratumGroupNumber}" size="1" class="required validate-notEmpty"/> -->
             
             <tags:inPlaceEdit value="${command.study.epochs[epochCountIndex].stratumGroups[statusStratumGroup.index].stratumGroupNumber}"
             				   path="${command.study.epochs[epochCountIndex].stratumGroups[statusStratumGroup.index].stratumGroupNumber}" />        
                     </td>
         <td class="alt">
         	${command.study.epochs[epochCountIndex].stratumGroups[statusStratumGroup.index].answerCombinations}
             </td>
         <td class="alt"><a
                 href="javascript:RowManager.deleteRow(stratumGroupRowInserter_${epochCountIndex},${statusStratumGroup.index},${stratumGroup.hashCode});">
             <img src="<tags:imageUrl name="checkno.gif"/>" border="0"></a></td>
     </tr>
 </c:forEach>
 </table>
<br/>
</body>
</html>
