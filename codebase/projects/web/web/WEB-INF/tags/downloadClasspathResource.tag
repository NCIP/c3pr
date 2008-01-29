<!--specify filename to download. File should be on classpath-->
<%@attribute name="filename" type="java.lang.String" required="true" %>
<%@attribute name="label" type="java.lang.String" required="true" %>
 <a class="downloadLinks" href="downloadClasspathResource?file=${filename}">Download ${label}</a>