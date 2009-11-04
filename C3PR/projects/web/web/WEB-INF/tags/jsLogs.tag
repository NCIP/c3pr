<%@taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="debug"%>
<c:if test="${empty debug || debug}">
<script>
RowManager.debug=true;
ValidationManager.debug=true;
</script>
<chrome:body>
<div class="box">
    <!-- header -->
    <div class="header"><div class="background-L"><div class="background-R">
      <h2>Javascript Logs</h2>
    </div></div></div>
    <!-- end header -->

    <!-- inner border -->
    <div class="border-T"><div class="border-L"><div class="border-R"><div class="border-B"><div class="border-TL"><div class="border-TR"><div class="border-BL"><div class="border-BR">
        <div class="interior">
            <div class="content">
				<div class="division">
			        <h3>Validation Logs</h3>
				    <div id="ValidationManagerLog" class="content">
				    </div>
				</div>
				<div class="division">
			        <h3>RowManager Logs</h3>
				    <div id="workAreaLog" class="content">
				    </div>
				</div>
            </div>
        </div>
    </div></div></div></div></div></div></div></div>
    <!-- end inner border -->
</div>
</chrome:body>
</c:if>
<!-- end box -->