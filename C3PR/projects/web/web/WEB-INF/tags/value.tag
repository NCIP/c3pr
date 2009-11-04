<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="value" type="java.lang.String" required="true"%>
<%@attribute name="message" type="java.lang.String" %> <!-- this message will be displayed when value id blank -->
<div class="value"> 
	<c:choose>
		<c:when test="${value==null||value==''}">
				<span class="no-selection">
					<c:choose>
						<c:when test="${message==null||message==''}">
							Not specified
						</c:when>
						<c:otherwise>
							${message}							
						</c:otherwise>
					</c:choose>
				</span>
		</c:when>
		<c:otherwise>
			${value }
		</c:otherwise>
	</c:choose>
</div>