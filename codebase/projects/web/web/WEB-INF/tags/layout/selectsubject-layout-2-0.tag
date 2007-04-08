<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="body">
	<%--<c:choose>
		<c:when test="${empty actionReturnType }">
			<tr>
		        <td>
					<tags:panel id="SearchSubject" title="Search Subject">		        
		        	<c:choose>
		        		<c:when test="${!empty studySiteId}">
			        		<tags:search action="searchParticipant?inRegistration=true&studySiteId=${studySiteId}"/>
			           	</c:when>
		        		<c:otherwise>
			        		<tags:search action="searchParticipant?inRegistration=true"/>
		        		</c:otherwise>
		        	</c:choose>
	        		</tags:panel>		        	
		       	</td>
			</tr>
			<tr>
		        <td>
		        	<c:choose>
		        		<c:when test="${!empty studySiteId}">
			        		<tags:panel id="CreateSubject" title="Create Subject" display="false" 
			        				url="createParticipant?inRegistration=true&studySiteId=${studySiteId}">
				        	</tags:panel>
			        	</c:when>
		        		<c:otherwise>
							<tags:panel id="CreateSubject" title="Create Subject" display="false" 
				        				url="createParticipant?inRegistration=true">
					        </tags:panel>		        		
		        		</c:otherwise>
		        	</c:choose>
		       	</td>
			</tr>
		</c:when>
		<c:when test="${actionReturnType=='SearchResults' }">
			<tr>
		        <td>
		        	<tags:panel id="SearchSubject" title="Search Subject">
		        	<c:choose>
		        		<c:when test="${!empty studySiteId}">
				        	<tags:search action="searchParticipant?inRegistration=true&studySiteId=${studySiteId}"/>
			           	</c:when>
		        		<c:otherwise>
			        		<tags:search action="searchParticipant?inRegistration=true"/>
		        		</c:otherwise>
		        	</c:choose>
		        	<jsp:doBody/>
		       		</tags:panel>
		       	</td>
			</tr>
			<tr>
		        <td>
		        	<c:choose>
		        		<c:when test="${!empty studySiteId}">
			        		<tags:panel id="CreateSubject" title="Create Subject" display="false" 
			        				url="createParticipant?inRegistration=true&studySiteId=${studySiteId}">
				        	</tags:panel>
			        	</c:when>
		        		<c:otherwise>
							<tags:panel id="CreateSubject" title="Create Subject" display="false" 
				        				url="createParticipant?inRegistration=true">
					        </tags:panel>		        		
		        		</c:otherwise>
		        	</c:choose>
		       	</td>
			</tr>
		</c:when>--%>
	<c:choose>
		<c:when test="${actionReturnType=='CreateParticipant' }">
			<tr>
		        <td>
		        	<c:choose>
		        		<c:when test="${!empty studySiteId}">
			        		<tags:panel id="SearchSubject" title="Search Subject" display="false" 
			        				url="searchParticipant?inRegistration=true&studySiteId=${studySiteId}">
				        	</tags:panel>
			        	</c:when>
		        		<c:otherwise>
							<tags:panel id="SearchSubject" title="Search Subject" display="false" 
			        				url="searchParticipant?inRegistration=true">
					        </tags:panel>		        		
		        		</c:otherwise>
		        	</c:choose>
		       	</td>
			</tr>
			<tr>
		        <td>
			        <tags:panel id="CreateSubject" title="Create Subject">
					    <tabs:levelTwoTabsSubFlow tab="${tab}" flow="${flow}"/>
					    <div class="tabcontent workArea">
					        <div class="body">
					            <jsp:doBody/>
					        </div>
					        <tabs:tabControls tabNumber="${tab.number}" isLast="${tab.number < flow.tabCount - 1}"/>
					    </div>
   			        </tags:panel>					
	       		</td>
			</tr>        
		</c:when>
		<c:otherwise>
			<tr>
		        <td>
		        	<tags:panel id="SearchSubject" title="Search Subject">
			        	<jsp:doBody/>
		       		</tags:panel>
		       	</td>
			</tr>
			<tr>
		        <td>
		        	<c:choose>
		        		<c:when test="${!empty studySiteId}">
			        		<tags:panel id="CreateSubject" title="Create Subject" display="false" 
			        				url="createParticipant?inRegistration=true&studySiteId=${studySiteId}">
				        	</tags:panel>
			        	</c:when>
		        		<c:otherwise>
							<tags:panel id="CreateSubject" title="Create Subject" display="false" 
				        				url="createParticipant?inRegistration=true">
					        </tags:panel>		        		
		        		</c:otherwise>
		        	</c:choose>
		       	</td>
			</tr>
		</c:otherwise>
	</c:choose>     
</table>