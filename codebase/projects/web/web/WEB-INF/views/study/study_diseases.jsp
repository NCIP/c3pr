<%--
 Copyright Duke Comprehensive Cancer Center and SemanticBits
 
 Distributed under the OSI-approved BSD 3-Clause License.
 See https://github.com/NCIP/c3pr/LICENSE.txt for details.
--%>
<%@ include file="taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><studyTags:htmlTitle study="${command.study}" /></title>
<%--<tags:includeScriptaculous/>--%>
<tags:dwrJavascriptLink objects="StudyAjaxFacade"/>
<script type="text/javascript">
	var CategorySelector = Class.create();
	
	Object.extend(CategorySelector.prototype, {
	initialize: function() {
		this.win = null;
        this.termList = new Array();
    },
	
	showWindow:function(wUrl, wTitle, wWidth, wHeight){
		win = new Window({
            className:"alphacube",
            destroyOnClose:true,
            title:wTitle,
            width:wWidth,
            height:wHeight,
            recenterAuto:true,
            resizable: false,
            minimizable : false,
            maximizable: false
        });
		this.win = win;
		win.setContent('chooseCategory');
        win.showCenter(true);
	},

	finishMultiTermsSelection:function() {
        var selectedDiseasTerms ='' ;
        var selectedTerms = $$('input.AddedTermXYZ');
        selectedTerms.each(function(el) {
            if (el.checked) {
            	selectedDiseasTerms = selectedDiseasTerms + el.value +',';
            }
        });
        catSel.addStudyDisease(selectedDiseasTerms);
        Windows.close(this.win.getId());
        catSel.termList = new Array();
        $('disease-subcategories').innerHTML = "";
        $('disease-added-terms').innerHTML = "";
        return;
	},

	addStudyDisease:function(selectedTerms){
		 <tags:tabMethod method="addStudyDiseases" viewName="/study/asynchronous/study_disease_section" divElement="'studyDiseases'" formName="'tabMethodForm'" javaScriptParam="'selectedDiseaseTerms='+selectedTerms+'&_doNotSave=true'" onComplete="hideDiseaseIndicator"/> ;
	},
	
    cancelTermsSelection:function(){
		Windows.close(this.win.getId());
		terms.options.length=0;
		categories.selectedIndex = -1;
		hideDiseaseIndicator();
	},

	addTerms:function (id){
        StudyAjaxFacade.getDiseaseTerms(id, function(diseaseTerms) {
        	diseaseTerms.each(function(diseaseTerm) {
              var termName = (diseaseTerm.ctepTerm.length > 30 ? diseaseTerm.ctepTerm.substring(0, 30) + "..." : diseaseTerm.ctepTerm);
              catSel.addTerm("disease-added-terms", diseaseTerm.id, termName, diseaseTerm.ctepTerm);
            });
        });
	},

    addTerm: function(ulID, termID, termText, title) {
        if (catSel.termList[termID]) {
            return;
        }
        ul = document.getElementById(ulID);
        
        checkbox = document.createElement("input");
        checkbox.type = 'checkbox';
        checkbox.name = termText;
        checkbox.defaultChecked = true;
        checkbox.value = termID;
        checkbox.id = "chkID" + termID;
        checkbox.setAttribute("id", "chk" + termID);

        a = document.createElement("a");
        a.appendChild(document.createTextNode(termText));

        a.id = "addedTerm" + termID;
        a.setAttribute("id", "addedTerm" + termID);

        a.setAttribute("title", title);
        a.title = title;
        
        li = document.createElement("li");
        li.appendChild(checkbox);
        li.appendChild(a);
        ul.appendChild(li)

        catSel.termList[termID] = true;
        $("liTerm" + termID).addClassName("term-disabled");
        $("addedTerm" + termID).addClassName("disease-added-terms");
        $("chk" + termID).addClassName("AddedTermXYZ");

    },

    showSubCategories: function(id){
        var selectedCategories = $$('a.disease-category-selected');
        selectedCategories.each(function(el) {
            el.removeClassName("disease-category-selected");
        });

        var selectedCategories = $$('li.li-category-selected');
        selectedCategories.each(function(el) {
            el.removeClassName("li-category-selected");
        });

        $("category_" + id).addClassName("disease-category-selected");
        $("li_" + id).addClassName("li-category-selected");
        $('disease-subcategories').innerHTML = "";
        $('disease-terms').innerHTML = "";
		
        catId = id; 
        StudyAjaxFacade.getChildCategories(catId, function(childCategories) {
            childCategories.each(function(childCategory) {
              var childCategoryName = (childCategory.name.length > 30 ? childCategory.name.substring(0, 30) + "..." : childCategory.name);
              catSel.showDiseaseTerms("disease-subcategories", childCategory.id, childCategoryName, childCategory.name);
            })
        });
        return;
	},

	showDiseaseTerms: function(ulID, ilID, ilText, title){
		ul = document.getElementById(ulID);
        a = document.createElement("a");
        a.appendChild(document.createTextNode(ilText));
        a.setAttribute("onclick", "catSel.showDiseaseTermDetail('disease-terms', " + ilID + ", '" + ilText + "')");
        a.onclick = function() {
            eval("catSel.showDiseaseTermDetail('disease-terms', " + ilID + ", '" + ilText + "')");
        }
        a.setAttribute("id", "subcategory_" + ilID);
        a.id = "subcategory_" + ilID;

        a.setAttribute("title", title);
        a.title = title;

        li = document.createElement("li");
        li.setAttribute("id", "subcategoryli_" + ilID);
        li.id = "subcategoryli_" + ilID;
        li.appendChild(a);
        ul.appendChild(li);

	},

	showDiseaseTermDetail :function(ulID, ilID, ilText) {

		var selectedSubcategories = $$('a.disease-subcategory-selected');
        selectedSubcategories.each(function(el) {
            el.removeClassName("disease-subcategory-selected");
        });

        var selectedSubcategories = $$('li.li-subcategory-selected');
        selectedSubcategories.each(function(el) {
            el.removeClassName("li-subcategory-selected");
        });
        
		$("subcategory_" + ilID).addClassName("disease-subcategory-selected");
        $("subcategoryli_" + ilID).addClassName("li-subcategory-selected");
        $('disease-terms').innerHTML = "";
        
		subCatId = ilID;
        StudyAjaxFacade.getDiseaseTerms(subCatId, function(diseaseTerms) {
        	diseaseTerms.each(function(diseaseTerm) {
              var termName = (diseaseTerm.ctepTerm.length > 30 ? diseaseTerm.ctepTerm.substring(0, 30) + "..." : diseaseTerm.ctepTerm);
              catSel.addLIToUL("disease-terms", diseaseTerm.id, termName, diseaseTerm.ctepTerm);
            })
        });
        catSel.addLIToUL("disease-terms", subCatId, 'Add All', 'Add All');
     },

 	addSingleDisease:function(){
    	diseaseTerm = $('diseaseTerm-hidden').value ;
    	catSel.addStudyDisease(diseaseTerm);
    	$('diseaseTerm-hidden').value='' ;
    	$('diseaseTerm-input').value='' ;
	},	

    addLIToUL: function(ulID, ilID, ilText, title) {
        ul = document.getElementById(ulID);
        a = document.createElement("a");
        a.appendChild(document.createTextNode(ilText));
		if(ilText == 'Add All'){
			a.setAttribute("onClick", "catSel.addTerms(" + ilID + ")");
	        a.onclick = function() {
	            eval("catSel.addTerms(" + ilID + ")");
	        }
		}else{
			a.setAttribute("onClick", "catSel.addTerm('disease-added-terms', " + ilID + ", '" + ilText + "', '" + title + "')");
	        a.onclick = function() {
	            eval("catSel.addTerm('disease-added-terms', " + ilID + ", '" + ilText + "', '" + title + "')");
	        }
		}
        

        a.setAttribute("id", "liTerm" + ilID);
        a.id = "liTerm" + ilID;

        a.setAttribute("title", title);
        a.title = title;
        
        li = document.createElement("li");
        li.appendChild(a);
        ul.appendChild(li);

        $("liTerm" + ilID).addClassName("disease-category");
        if (catSel.termList[ilID]) {
            $("liTerm" + ilID).addClassName("term-disabled");
        }
    },
		
    showCategoryBox:function(){
 			this.showWindow('', '', 1000, 580 );
 	}
	});

	function initalizeCategorySelector(){
		catSel = new CategorySelector();
	}
 	
    initalizeCategorySelector();

    function deleteStudyDiseases(diseaseTerm){
		 <tags:tabMethod method="deleteStudyDiseases" viewName="/study/asynchronous/study_disease_section" divElement="'studyDiseases'" formName="'tabMethodForm'" javaScriptParam="'diseaseTermId='+diseaseTerm" onComplete="hideDiseaseIndicator"/> ;
	}

	function hideDiseaseIndicator(){
		$('diseaseIndicator').hide();
	}

    var diseaseTermAutocompleterProps = {
		basename: "diseaseTerm",
        populator: function(autocompleter, text) {
            StudyAjaxFacade.matchDiseaseTerms(text,function(values) {
                autocompleter.setChoices(values)
            })
        },
        valueSelector: function(obj) {
			return obj.ctepTerm
        },
        afterUpdateElement: function(inputElement, selectedElement, selectedChoice) {
			hiddenField=diseaseTermAutocompleterProps.basename+"-hidden"
			$(hiddenField).value=selectedChoice.id;
		}
     }
     AutocompleterManager.addAutocompleter(diseaseTermAutocompleterProps);
</script>
</head>
<body>
<tags:instructions code="study_diseases" />
<form:form>
	<tags:tabFields tab="${tab}" />
	<chrome:box title="Diseases">
	<div class="row">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<tags:autocompleter name="axxxxyyy" displayValue="" value="" basename="diseaseTerm" ></tags:autocompleter>
		<tags:button size="small" type="button" color="blue" icon="add" value="Add Disease" id="addSingleDiseaseBtn" onclick="$('diseaseIndicator').show();catSel.addSingleDisease();"/>
		<tags:button size="small" type="button" color="blue" icon="add multiple" value="Add Diseases" id="addMultipleDiseaseBtn" onclick="$('diseaseIndicator').show();catSel.showCategoryBox();"/>
		<tags:button size="small" type="button" color="red" icon="x" value="Remove All Diseases" id="removeAllDiseaseBtn" onclick="$('diseaseIndicator').show();deleteStudyDiseases('');"/>
		<img id="diseaseIndicator" src="<tags:imageUrl name="indicator.white.gif"/>" alt="Indicator" align="middle" style="display:none"/>
	</div>
	<br>	
	<div id="studyDiseases">
		<table class="tablecontent" width="90%">
			<tr>
				<th width="32%">Disease Category</th>
				<th width="32%">Disease Sub Category</th>
				<th width="32%">Disease</th>
				<th width="4%"></th>
			</tr>
			<c:choose>
				<c:when test="${fn:length(command.study.studyDiseases) == 0}">
					<tr>
						<td colspan="4"><fmt:message key="study.disease.noDisease" /></td>
					<tr>
				</c:when>
				<c:otherwise>
					<c:forEach items="${command.study.studyDiseases}" var="studyDisease" varStatus="status">
						<tr>
							<td>${studyDisease.diseaseTerm.category.parentCategory.name}</td>
							<td>${studyDisease.diseaseTerm.category.name}</td>
							<td>${studyDisease.diseaseTerm.ctepTerm}</td>
							<td valign="top" align="left">
			                    <a href="javascript:deleteStudyDiseases('${studyDisease.diseaseTerm.id}');">
			                    	<img src="<tags:imageUrl name="checkno.gif"/>" border="0">
			                    </a>
			                </td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			
		</table>
		</div>	
	</chrome:box>
	<tags:tabControls tab="${tab}" flow="${flow}" willSave="${willSave}" >
			<jsp:attribute name="localButtons">
			<c:if test="${!empty param.parentStudyFlow}">
			<tags:button type="button" color="blue" icon="back" value="Return to parent" onclick="returnToParentUsingButton('${param.parentStudyFlow}', '${command.study.parentStudyAssociations[0].parentStudy.id}')" />
			</c:if>
		</jsp:attribute>
</tags:tabControls>
	
</form:form>
	
  	<div style="display:none">
    <div id="chooseCategory">
        <chrome:box title="Select Diseases">

        <table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr bgcolor="#E4E4E4">
            <td align="left" width="25%"><h2 class="title">Disease Categories</h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="25%"><h2 class="title">Disease Sub Categories&nbsp;<span style='font-size:12px;'></span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="25%"><h2 class="title">Diseases&nbsp;<span style='font-size:12px;'>(Click to add)</span></h2></td>
            <td align="left" width="1px"><img src="<c:url value="/images/chrome/spacer.gif" />"></td>
            <td align="left" width="25%"><h2 class="title">Selected Diseases</h2></td>
        </tr>
        <tr>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="categories" class="disease-category">
                    <c:forEach var="cat" items="${diseaseCategories}">
                    	<c:if test="${fn:length(cat.name) > 30}">
                    		<c:set var="catName" value="${fn:substring(cat.name,0,30)}......"> </c:set>
                    	</c:if>
                        <li id="li_${cat.id}">
                        	<a id="category_${cat.id}" onclick='catSel.showSubCategories(${cat.id});' class='disease-category' title="${cat.name}">${fn:length(cat.name) > 30 ? catName : cat.name}</a>
                        </li>
                    </c:forEach>
                </ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="disease-subcategories" class="disease-category"></ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top">
                <div style="overflow:auto; height:460px;">
                <ul id="disease-terms" class="disease-category"></ul>
                </div>
            </td>
            <td align="left" bgcolor="gray"></td>
            <td align="left" valign="top"><div style="overflow:auto; height:460px;"><ul id="disease-added-terms" class="disease-category"></ul></div></td>
        </tr>
        <tr>
            <td colspan="6" style="text-align:right;">
            </td>
            <td colspan="1" style="text-align:center;">
                    <c:if test="${empty localButtons}">
                        <tags:button color="green" value="Add Terms" icon="add" onclick="catSel.finishMultiTermsSelection()" />
                    </c:if>
            </td>
        </tr>
        </table>
        
        </chrome:box>
    </div>
	</div>
<!-- the hidden window for category popup -->

<style>
    ul.disease-category {
        cursor:pointer;
        margin: 5px;
        padding-left: 0px;
		list-style-type:none;
    }
	
	ul#categories li a {
		margin-left:5px;
	}
	
    a.disease-category {
        font-size:9pt;
        cursor:pointer;
        color:black;
    }

    a.disease-category-selected {
        font-size:9pt;
        cursor:pointer;
        line-height:26px;
    }

    li.li-category-selected {
        background-image:url(/c3pr/images/chrome/cat-arrow.png);
		background-repeat:no-repeat;
    }

    li.li-category {
    }

    a.disease-category:hover {
        font-size:9pt;
        cursor:pointer;
        color:blue;
		text-decoration:underline;
    }

    ul.disease-added-terms, a.disease-added-terms {
        font-size:9pt;
        cursor:pointer;
        margin: 0px;
        padding-left: 5px;
    }

    #disease-added-terms {
        list-style-type: none;
    }

    a.disease-added-terms:hover {
        cursor:pointer;
    }

    a.term-disabled {
        font-size:9pt;
        color:#cccccc;
        cursor:pointer;
    }

    a.term-disabled:hover {
        font-size:9pt;
        color:#cccccc;
        cursor:pointer;
    }
    
    a.disease-subcategory-selected {
        font-size:9pt;
        cursor:pointer;
        line-height:26px;
    }

    li.li-subcategory-selected {
        background-image:url(/c3pr/images/chrome/cat-arrow.png);
		background-repeat:no-repeat;
    }
</style>

</body>
</html>
