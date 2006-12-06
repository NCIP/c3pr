/*
 Use through Commons to disable/enable the state attribute depending on the
 country is from USA or Canada
 */
function checkCountry(form, country)
    {
        var US="231";
        var CAN="40";
        if(country.value==US || country.value==CAN || country.value=='')
        {
            document.forms[form].stateCode.disabled=false;
        }
        else
        {
            document.forms[form].stateCode.value="";
            document.forms[form].stateCode.disabled=true;
        }
    }

/*
 Use in employmentForm.jsp to select or deselect the employer
 */
function setEmployer(obj){
        len = document.employmentForm.elements.length;
        var i=0;
        if(obj!=1){
            document.employmentForm.employerName.value="";
        }
        if(obj!=2){
            document.employmentForm.nihInstitute.value="";
            document.employmentForm.recognizedEmployer.value="";
            }

        for( i=0 ; i<len ; i++) {
            e=document.employmentForm.elements[i];
            if (e.type == 'radio' && e.name == 'employer') {
                if(e.value==obj)
                   e.checked=true;

            }
          }
}

/*
Use in personalInfo.jsp to reset the birthdate if the dobWithheld is check
*/
function dobWithheld(element){
    if(element.checked)
        document.profileForm.birthDate.value="";
}

/**
 Prevent the user from submitting twice
*/
var submitFlag = true;
function processForm(){
	if (submitFlag==false){
		alert("The form has been submitted already, please wait for the response");
                return false;
	}
      submitFlag=false;
}

/**
 Confirm if the user would like to cancel.
*/
function cancel_confirm()
{
    if(confirm("Are you sure you would like to Cancel? All changes will be lost.")){
	return true;}
      else return false;
}


function getObj(n, d) { //v4.0
  var p,i,x;
    if(!d) d=document; 
	if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document;
	 n=n.substring(0,p);
	 }
	
  if(!(x=d[n])&&d.all) x=d.all[n];
  for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=getObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n);
   return x;
}

function openWin(psUrl, pbShowToolbars, popW, popH) 
{
        if ( pbShowToolbars )
                lsToolbarString = "toolbar=yes,location=yes,";
        else
                lsToolbarString = "toolbar=no,location=no,";
				
		if (popW && popH) {
			newWidth = popW;
			newHeight = popH;
		}
		else {
			newWidth = screen.availWidth;
        	newHeight = screen.availHeight;
		}
        
        var newWin = window.open(
                                psUrl,
                                "popupWindow",
                                lsToolbarString + "directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,screenX=0,screenY=0,width=" + newWidth + ",outerwidth=" + newWidth + ",height=" + newHeight + ",outerheight=" + newHeight);
                                
        if (top.popupWindow == null)
                top.popupWindow = newWin;
}

function showIcon(iconName) {
	if (detectBrowser() == "Failed")
		return ;
	var imgDir = "images";
	var str = "";
	
	str += "<td width=12 align=\"right\" valign=\"middle\">";
	str += "<div id=\""+ iconName +"plusIcon\" name=\""+ iconName +"plusIcon\" style=\"display:'none'\">";
	str += "<a href=\"javascript:ShowBlock('" + iconName + "','')\">";
	str += "<img src=\"" + curDir + imgDir + "/plus.gif\" width=\"12\" height=\"12\" alt=\"Show Expanded View\" border=\"0\"></a></div>";
	str += "<div id=\""+ iconName +"minusIcon\" name=\""+ iconName +"minusIcon\">";
	str += "<a href=\"javascript:ShowBlock('"+ iconName + "','none')\">";
	str += "<img src=\""+ curDir + imgDir + "/minus.gif\" width=12 height=12 alt=\"Show Basic View\" border=0 name=\""+ iconName +"titleBlockCollapse\"></a></div></td>";
	
	document.write(str);
}

function ShowBlock(blockID,arg,iconID,iconID2) {
	if (detectBrowser() == "Failed")
		return ;
	//alert("show block " + blockID);
	var blockObj;
	var iconObj;

	blockObj = getObj(blockID);
	blockObj.style.display = arg;
	
	if (arg == "none") {
		iconObj = getObj(blockID + "plusIcon");
		iconObj.style.display="";
		iconObj = getObj(blockID + "minusIcon");
		iconObj.style.display="none";
	}
	else {
		iconObj = getObj(blockID + "minusIcon");
		iconObj.style.display = "";
		iconObj = getObj(blockID + "plusIcon");
		iconObj.style.display = "none";
	}
}

function ConfirmDelete() {
	confirm("Are you sure you want to delete this record?");
}


function detectBrowser() {
	if (navigator.appName.indexOf("Internet Explorer") != -1)
		return "IE";
		//alert("IE Browser: " + navigator.appCodeName + ", appName:" + navigator.appName + ", Version:" + navigator.appVersion);
	else
		//alert("non IE Browser: " + navigator.appCodeName + ", appname:" + navigator.appName + ", Version:" + navigator.appVersion);
		if (navigator.appVersion.indexOf("5") == -1)
			//alert("This browser may not support this page");
			return "Failed";
		else
			return "Other";
}


function showSection(sectionName) {
	displayAllSections("none");
	getObj(sectionName).style.display="";
}

function displayAllSections(arg) {
	var displayArg = "";
	
	if (arg=="none")
		displayArg = "none";
		
	getObj("GrantID").style.display = displayArg;
	getObj("PIName").style.display = displayArg;
	getObj("Institution").style.display = displayArg;
	getObj("Meeting").style.display = displayArg;
	getObj("Assignment").style.display = displayArg;
	getObj("GrantDates").style.display = displayArg;
	getObj("GrantDocumentStatus").style.display = displayArg;
	getObj("AdministrativeCoding").style.display = displayArg;
	getObj("AwardBudgetData").style.display = displayArg;
	getObj("TextSearch").style.display = displayArg;
}

function hideChild(childrenName) {
	var childDisplay;
	if (getObj(childrenName).src.indexOf("minus.gif") > 0) {
		childDisplay = "none";
		getObj(childrenName).src = "../images/plus.gif";
	}
	else {
		getObj(childrenName).src = "../images/minus.gif";
		childDisplay = "";
	}
	
	for (i=1;i<200;i++) {
		if (getObj(childrenName+"-"+i)) {
			getObj(childrenName+"-"+i).style.display=childDisplay;
		}
	}
}