//  Title       : registration_multi_select.js
//  Created By  : Jin Soo Choi
//  Created     : 06/11/2003
//  Description :

function selectAll()
{
  var numSelected = new Number();
  var numElements = new Number(document.forms[0].length);

  for (count=0; count < numElements; count++)
  {
    if (document.forms[0].elements[count].type == "checkbox")
    {
      document.forms[0].elements[count].checked = document.forms[0].chkSelectAll.checked;
      numSelected++;
    }
  }

  // When click on button, only do loop if haven't checked values already
  document.forms[0].hidSelectAll.value = document.forms[0].chkSelectAll.checked;

  if (document.forms[0].chkSelectAll.checked)
  {
    // Subtract one for the select all box
    document.forms[0].hidNumSelected.value = numSelected-1;
  }
  else
  {
    document.forms[0].hidNumSelected.value = "0";
  }
}

function registrationSelect(index, elementsBeforeList, elementsAfterCheckBox)
{
  var numIndex = new Number(index);
  numIndex = numIndex * (elementsAfterCheckBox + 1) + elementsBeforeList;

  // Total number of selected registrations
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  // If the registration is being selected, increment counter
  if (document.forms[0].elements[numIndex].checked)
  {
    numSelected++;
    document.forms[0].hidNumSelected.value = numSelected;
  }
  // If the registration is not being selected, remove it from the list
  else
  {
    numSelected--;
    document.forms[0].hidNumSelected.value = numSelected;
  }
}

function sendToEdit()
{
  // Total number of selected registrations
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  if(!(numSelected < 1))
  {
    if(!(numSelected > 1))
    {
      document.forms[0].action = "registration_select.jsp";
      document.forms[0].submit();
    }
    else
    {
      alert("Please select only one registration!");
    }
  }
  else
  {
    alert("Please select a registration!");
  }
}

function sendToFullInfo()
{
  // Total number of selected registrations
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  if(!(numSelected < 1))
  {
    if(!(numSelected > 1))
    {
      //document.forms[0].action = "registration_select.jsp";
      document.forms[0].submit();
    }
    else
    {
      alert("Please select only one registration!");
    }
  }
  else
  {
    alert("Please select a registration!");
  }
}

function sendToRDC()
{
  window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=J2eeRDC.fmx&userid=ctdev/oradev!246@ctdev&width=1280&height=770");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=rdc.fmx&userid=ctdev/oradev!246@ctdev&width=1280&height=770");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=rdc.fmx+username_p=cdr_bld+password_p=secret+database=ctdev+called_form_p=rdc.fmx+j2ee_server_url_p=http://156.40.131.29:8082/cpr/registration_select.jsp", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc?Username=cdr_bld+Password=secret+Database=ctdev", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc+Username=cdr_bld+Password=secret+Database=ctdev", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=rdc.fmx+Username=cdr_bld+Password=secret+Database=ctdev", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc+username=cdr_bld+password=secret+", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //document.forms[0].action = "http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc";
  //document.forms[0].submit();
}
