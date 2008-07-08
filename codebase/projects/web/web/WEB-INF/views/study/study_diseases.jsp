<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="chrome" tagdir="/WEB-INF/tags/chrome" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>${tab.longTitle}</title>

<%--<tags:includeScriptaculous/>--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>
<script type="text/javascript">

function validatePage() {
    return true;
}

function fireAction(action, selected) {
    if (validatePage()) {
        addDiseasesToCart()
        document.getElementById('command')._target.name = '_noname';
        document.studyDiseasesForm._actionx.value = action;
        document.studyDiseasesForm._selected.value = selected;
        document.studyDiseasesForm.submit();
    }
}

function clearField(field) {
    field.value = "";
}

function hover(index)
{
    //var sel = $("disease-sub-category")
    //alert (sel.value)

}

var diseaseAutocompleterProps = {
    basename: "disease",
    isFreeTextAllowed: true,
    populator: function(autocompleter, text) {
        StudyAjaxFacade.matchDiseaseCategories(text, '', function(values) {
            autocompleter.setChoices(values)
        })
    },
    valueSelector: function(obj) {
        return obj.name
        // + "<b> ::</b> " + obj.id
    }
}


function acPostSelect(mode, selectedChoice) {
    //Element.update(mode.basename + "-selected-name", mode.valueSelector(selectedChoice))
    updateCategories(selectedChoice.id);
    $(mode.basename + "-hidden").value = selectedChoice.id;
    //$(mode.basename + '-selected').show()
    //new Effect.Highlight(mode.basename + "-selected")
}

function updateSelectedDisplay(mode) {
    if ($(mode.basename + "-hidden").value) {
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
        $(mode.basename + "-hidden").value = ""
        $(mode.basename + "-input").value = ""
    })
}


function updateCategories(id) {
    StudyAjaxFacade.matchDiseaseCategoriesByParentId(id, function(categories) {
        var sel = $("disease-sub-category")
        sel.size = categories.length < 10 ? categories.length + 2 : 10;
        //sel.size= 10
        sel.options.length = 0
        sel.options.add(new Option("All", ""))
        sel.options[0].selected = true;
        categories.each(function(cat) {
            var opt = new Option(cat.name, cat.id)
            sel.options.add(opt)
        })
        showDiseases()
    })

}

function showDiseases() {
    var categoryId = $("disease-sub-category").value
    var subCategorySelect = $("disease-sub-category")
    // If all is selected
    if (subCategorySelect.value == "") {
        var diseaseTermSelect = $("disease-term")
        diseaseTermSelect.options.length = 0
        diseaseTermSelect.size = 10
        diseaseTermSelect.options.add(new Option("All", ""))
        diseaseTermSelect.options[0].selected = true;
        //alert(subCategorySelect.length);
        for (i = 1; i < subCategorySelect.length; i++) {
            var catId = subCategorySelect.options[i].value

            StudyAjaxFacade.matchDiseaseTermsByCategoryId(catId, function(diseases) {

                diseases.each(function(cat) {
                    var opt = new Option(cat.ctepTerm, cat.id)
                    diseaseTermSelect.options.add(opt)
                })
            })
        }

    }
    else {
        StudyAjaxFacade.matchDiseaseTermsByCategoryId(categoryId, function(diseases) {
            var sel = $("disease-term")
            sel.size = diseases.length + 2;
            sel.options.length = 0
            sel.options.add(new Option("All", ""))
            sel.options[0].selected = true;
            diseases.each(function(cat) {
                var opt = new Option(cat.term, cat.id)
                sel.options.add(opt)
            })
        })
    }
}

/**
 * Copy Diseases from  [Diseases MultiSelect]
 *   to the [Selected Diseases MultiSelect]
 *
 */
function addDiseasesToCart() {
    var diseaseTerm = $("disease-term");
    var diseaseSelected = $("disease-sel");
    var diseaseSelectedHidden = $("disease-sel-hidden");
    if (diseaseSelected.options[0].value == "") {
        diseaseSelected.options.length = 0
    }
    // If all is selected  in the [Diseases MultiSelect]
    if (diseaseTerm.options[0].selected) {
        for (i = 1; i < diseaseTerm.length; i++)
        {
            var opt = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
            var opt1 = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
            diseaseSelected.options.add(opt)
            diseaseSelectedHidden.options.add(opt1)
        }
    }
    // If anything other than all is selected
    else {
        for (i = 1; i < diseaseTerm.length; i++)
        {
            if (diseaseTerm.options[i].selected) {
                var opt = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
                //var opt1 = new Option(diseaseTerm.options[i].text, diseaseTerm.options[i].value)
                diseaseSelected.options.add(opt)
                //diseaseSelectedHidden.options.add(opt1)
            }
        }
    }
    // Copy over [Selected Diseases MultiSelect] to [Hidden Selected Diseases MultiSelect]
    //selectAll(diseaseSelectedHidden)
    synchronizeSelects(diseaseSelected, diseaseSelectedHidden);
}

function synchronizeSelects(selectFrom, selectTo)
{
    // Delete everything from the target
    selectTo.options.length = 0;
    // iterate over the source and add to target
    for (i = 0; i < selectFrom.length; i++) {
        var opt = new Option(selectFrom.options[i].text, selectFrom.options[i].value)
        selectTo.options.add(opt)
        selectTo.options[i].selected = true;
    }
}

function removeDiseasesFromCart()
{
    var diseaseSelected = $("disease-sel");
    var diseaseSelectedHidden = $("disease-sel-hidden");

    for (i = 0; i < diseaseSelected.length; i++)
    {
        if (diseaseSelected.options[i].selected) {
            diseaseSelected.options[i] = null
        }
    }
    synchronizeSelects(diseaseSelected, diseaseSelectedHidden)

}

function populateSelectsOnLoad()
{

    if ($('disease-input').value.length > 0)
    {
        StudyAjaxFacade.matchDiseaseCategories($('disease-input').value, '', function(values) {
            updateCategories(values[0].id);
        })
    }
}

function handleClear(){
	$("disease-sub-category").options.length=0;
	$("disease-term").options.length=0;
}

Event.observe(window, "load", function() {
    $('disease-sel').style.display = 'none';
    $('disease-sel-hidden').style.display = 'none';

    acCreate(diseaseAutocompleterProps)
    updateSelectedDisplay(diseaseAutocompleterProps)

    Event.observe("disease-sub-category", "change", function() {
        showDiseases()
    })
    populateSelectsOnLoad();

    // Element.update("flow-next", "Continue &raquo;")
})

</script>
</head>
<body>
<!-- MAIN BODY STARTS HERE -->
<%-- Can't use tags:tabForm b/c there are two boxes in the form --%>
<form:form method="post" name="studyDiseasesForm" cssClass="standard">
    <tags:tabFields tab="${tab}"/>
    
    <table border="0" id="table1" cellspacing="10" width="100%">
    <tr>
    <td valign="top" width="45%">
    <chrome:box title="${tab.shortTitle}">

        <div>
            <input type="hidden" name="_actionx" value="">
            <input type="hidden" name="_selected" value="">
        </div>

        	<tags:errors path="studyDiseases"/>
            Search for a Disease Category<br>
            
            <input type="hidden" id="disease-hidden"/>
            <form:input size="45" id="disease-input" path="diseaseCategoryAsText" cssClass="autocomplete"/>
            <tags:hoverHint keyProp="study.diseaseCategoryAsText"/>
            <tags:indicator id="disease-indicator"/>
            <div id="disease-choices" class="autocomplete"></div>
            <input type="button" id="disease-clear" value="Clear" onClick="handleClear()"/>

            <p id="disease-selected" style="display: none"></p>

            <br><br>Select a Sub Category<br>
            <select multiple size="1" onmouseover="javascript:hover()" style="width:400px" id="disease-sub-category">
                <option value="">Please select a Category first</option>
            </select>

            <br><br>Diseases<br>
            <select multiple size="1" style="width:400px" id="disease-term">
                <option value="">Please select a Category first</option>
            </select> <span id="disease-selected-name"></span>
            <select multiple size="10" id="disease-sel">
                <option value="">No Selected Diseases</option>
            </select> <form:select id="disease-sel-hidden" size="1"
                                   path="diseaseTermIds">
        </form:select>
        
    </chrome:box>
	</td>
	
	<td valign="middle"><input type="button" value="Add Study Disease >>" onclick="fireAction('addStudyDisease','0');" alt="Add Study Disease"/></td>
	
	<td valign="top" width="45%">
    <chrome:box title="Selected Disease - ${fn:length(command.studyDiseases)}" id="diseases">
        <c:choose>
            <c:when test="${fn:length(command.studyDiseases) == 0}">
                No Diseases Selected
            </c:when>
            <c:otherwise>
                <table border="0" width="100%" class="tablecontent">
                    <tr>
                        <th scope="col">Disease Term</th>
                        <th scope="col">Primary</th>
                    </tr>
                    <c:forEach items="${command.studyDiseases}" var="studyDisease"
                               varStatus="status">
                        <tr>
                            <td class="alt"><a href="javascript:fireAction('removeStudyDisease',${status.index});">
                                <img src="<tags:imageUrl name="checkno.gif"/>" border="0" alt="remove"></a>&nbsp;
                                    ${studyDisease.diseaseTerm.ctepTerm}</td>
                            <td class="alt">
                                <form:checkbox path="studyDiseases[${status.index}].leadDisease"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </chrome:box>
    </td>
    </tr>
    
    <tr><td colspan="3"><tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}"/></td>
    </tr>
    </table>
    
</form:form>
<!-- MAIN BODY ENDS HERE -->
</body>
</html>