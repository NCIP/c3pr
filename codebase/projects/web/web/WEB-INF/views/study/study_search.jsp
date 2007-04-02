<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tabs" tagdir="/WEB-INF/tags/tabs"%>

<html>
<head>
<style type="text/css">
        .label { width: 12em; text-align: right; padding: 4px; }
</style>
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
</head>
<body>
<tabs:body title="">
<form:form id="searchForm" name="searchForm" method="post">
<table width="100%" border="0" cellspacing="5" cellpadding="0">
	<tr>
		<td valign="top">
		<tabs:division id="study-search">
		<table border="0" id="table1" cellspacing="0" cellpadding="5" width="100%">
			<tr>
				<td align="center" width="40%"/><font size="h3"><b>Study Search</b></font></td>
			</tr>
			<tr>
				<td align="center" width="40%"/>
					<input type="hidden" id="study"/>
					<form:input id="study-input" size="75" path="searchText"/>
					<tags:indicator id="study-indicator"/>
					<div id="study-choices" class="autocomplete"></div>
					<p id="study-selected" style="display: none">
						You've selected <span id="study-selected-name"></span>.
					</p>
				</td>
			</tr>
			<tr>
				<td width="50%" align="center">
					<input type="submit" value="Search"/>
					<input type="button" id="study-clear" value="Clear"/>
				</td>
			</tr>
			<tr>
				<td width="50%" align="center">
					<p id="instructions">
						Please search a Study (by short title or primary identifier)
					</p>
				</td>
			</tr>
		</table>
		</tabs:division>
		</td>
	</tr>
</table>
</form:form>
</tabs:body>
</body>
</html>
