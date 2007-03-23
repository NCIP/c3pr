<%@tag%><%@attribute name="action" required="true"%>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<tags:stylesheetLink name="tabbedflow"/>
<tags:javascriptLink name="tabbedflow"/>
<tags:includeScriptaculous/>
<tags:dwrJavascriptLink objects="createStudy"/>

<script>
/// AJAX
var studyAutocompleterProps = {
    basename: "study",
    populator: function(autocompleter, text) {
	createStudy.matchStudies(text, function(values) {
	    autocompleter.setChoices(values)
	})
    },
    valueSelector: function(obj) {
    return obj.shortTitleText
    }
}

function acPostSelect(mode, selectedChoice) {
    Element.update(mode.basename + "-selected-name", mode.valueSelector(selectedChoice))
    $(mode.basename).value = selectedChoice.id;
    $(mode.basename + '-selected').show()
    new Effect.Highlight(mode.basename + "-selected")
}

function updateSelectedDisplay(mode) {
    if ($(mode.basename).value) {
	Element.update(mode.basename + "-selected-name", $(mode.basename + "-input").value)
	$(mode.basename + '-selected').show()
    }
}

function acCreate(mode) {
    new Autocompleter.DWR(mode.basename + "-input", mode.basename + "-choices",
	mode.populator, {
	valueSelector: mode.valueSelector,
	afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
	    acPostSelect(mode, selectedChoice)
	},
	indicator: mode.basename + "-indicator"
    })
    Event.observe(mode.basename + "-clear", "click", function() {
	$(mode.basename + "-selected").hide()
	$(mode.basename).value = ""
	$(mode.basename + "-input").value = ""
    })
}

Event.observe(window, "load", function() {
    acCreate(studyAutocompleterProps)
    updateSelectedDisplay(studyAutocompleterProps)
   // Element.update("flow-next", "Continue &raquo;")
})
</script>
<!-- STUDY SEARCH START HERE -->
<tabs:body title="Search Study">
<form:form id="searchForm" name="searchForm" method="post" action="${action}">
<table width="100%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td>
		<tabs:division id="study-search">
		<table border="0" width="100%" id="table1" cellspacing="0" cellpadding="5" >
			<tr>
				<td align="right" width="90%"/>
					<input type="hidden" id="study"/>
					<input type=text id="study-input" size="60" name="searchText"/>
					<tags:indicator id="study-indicator"/>
					<div id="study-choices" class="autocomplete"></div>
					<p id="study-selected" style="display: none">
						You've selected <span id="study-selected-name"></span>.
					</p>
				</td>
				<td align="right" width="10%">
					<input type="submit" value="Search"/>&nbsp;&nbsp;
					<input type="button" id="study-clear" value="Clear"/>
				</td>
			</tr>			
		</table>
		</tabs:division>
		</td>
	</tr>
</table>
</form:form>
</tabs:body>
<!-- STUDY SEARCH ENDS HERE -->