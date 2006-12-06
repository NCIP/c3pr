//  Title       : registration_select.js
//  Created By  : Jin Soo Choi
//  Created     : 06/11/2003
//  Description :

function checkSelection(elementsBeforeList, elementsAfterCheckBox)
{
  var blnReturnValue = true;
  var numElements = new Number();
  var numStartIndex = new Number(elementsBeforeList);
  var numSkipElements = new Number(elementsAfterCheckBox);

  // Total number of selected registrations
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  numElements = document.forms[0].length;

  if (document.forms[0].hidSelectAll.value == "true")
  {
    for (count=numStartIndex; count < numElements; count++)
    {
      if ((document.forms[0].elements[count].checked) &&
          (document.forms[0].elements[count].name != "chkSelectAll"))
      {
        if (checkConsolidator(count))
        {
          if (document.forms[0].hidGlobalConsignor.value == "")
          {
            document.forms[0].hidGlobalConsignor.value = document.forms[0].elements[count+1].value;
            document.forms[0].hidGlobalSupplier.value = document.forms[0].elements[count+2].value;
          }

          if (document.forms[0].elements[count+1].value != document.forms[0].hidGlobalConsignor.value)
          {
            alert("You cannot group together pulls with different Consignor DoDAACs.  Please reselect.");
            document.forms[0].hidGlobalConsignor.value = "";
            document.forms[0].hidGlobalSupplier.value = "";
            blnReturnValue = false;
            numSelected--;
            document.forms[0].hidNumSelected.value = numSelected;
            document.forms[0].elements[count].checked = false;
            document.forms[0].elements[count].focus();
            break;
          }
          else
          {
           // Only limit supplier selection if user is transporter and the booking id is null
           // bc otherwise, db search will take of it
            if ((document.frmConsolidators.hidUser.value == "T") && (document.forms[0].hidBookingId.value == ""))
            {
              if (document.frmConsolidators.hidInputConsolidatorInd.value == "N")
              {
                if (document.forms[0].elements[count+2].value != document.forms[0].hidGlobalSupplier.value)
                {
                  alert("You cannot group together pulls with different Suppliers.  Please reselect.");
                  document.forms[0].hidGlobalConsignor.value = "";
                  document.forms[0].hidGlobalSupplier.value = "";
                  blnReturnValue = false;
                  numSelected--;
                  document.forms[0].hidNumSelected.value = numSelected;
                  document.forms[0].elements[count].checked = false;
                  document.forms[0].elements[count].focus();
                  break;
                }
                else
                {
                  // This registration is ok, skip over the hidden vars
                  count = count + numSkipElements;
                }
              }
              else
              {
                // This registration is ok, don't check the supplier because this is a consolidation
                count = count + numSkipElements;
              }
            }
            else
            {
              count = count + numSkipElements;
            }
          }
        }
        else
        {
          blnReturnValue = false;
          numSelected--;
          document.forms[0].hidNumSelected.value = numSelected;
          break;
        }
      }
    }
  }

  if (blnReturnValue)
  {
    if (numSelected == 0)
    {
      alert("Please select an registration to continue.");
      blnReturnValue = false;
    }
  }
  return (blnReturnValue);
}


function addRegistration(elementsBeforeList, elementsAfterCheckBox)
{
  var strBookingStatus = new String(document.forms[0].hidBookingStatus.value);

  if (checkSelection(elementsBeforeList, elementsAfterCheckBox))
  {
    if(strBookingStatus == "A")
    {
      if (document.forms[0].hidUserType.value == "TR")
      {
        document.forms[0].action = "booking_creation2.jsp";
      }
      else
      {
        document.forms[0].action = "registration_booking_input2.jsp";
      }
      document.forms[0].submit();
    }
    else
    {
      if (document.forms[0].hidUserType.value == "TR")
      {
        document.forms[0].action = "booking_creation.jsp";
      }
      else
      {
        document.forms[0].action = "registration_booking_input.jsp";
      }
      document.forms[0].submit();
    }
  }
}

function removeRegistration(elementsBeforeList, elementsAfterCheckBox)
{
  // Total number of selected registrations
  var numSelected = new Number(document.forms[0].hidNumSelected.value);
  // Total number of registrations
  var numRecords = new Number(document.forms[0].hidNumRecords.value);

  var strBookingStatus = new String(document.forms[0].hidBookingStatus.value);

  if ((document.forms[0].hidSelectAll.value == "false") && (numSelected < numRecords))
  {
    if (checkSelection(elementsBeforeList, elementsAfterCheckBox))
    {
      if(strBookingStatus == "A")
      {
        if (document.forms[0].hidUserType.value == "TR")
        {
          document.forms[0].action = "booking_creation2.jsp";
        }
        else
        {
          document.forms[0].action = "registration_booking_input2.jsp";
        }
        document.forms[0].submit();
      }
      else
      {
        if (document.forms[0].hidUserType.value == "TR")
        {
          document.forms[0].action = "booking_creation.jsp";
        }
        else
        {
          document.forms[0].action = "registration_booking_input.jsp";
        }
        document.forms[0].submit();
      }
    }
  }
  else
  {
    alert("You cannot select all registrations for removal.  Please reselect.");
  }
}

function createBooking(elementsBeforeList, elementsAfterCheckBox)
{
  if (checkSelection(elementsBeforeList, elementsAfterCheckBox))
  {
    if (document.frmConsolidators.hidInputConsolidatorInd.value == "Y")
    {
      document.forms[0].hidConsolidatorValue.value =
        document.frmConsolidators.optConsolidator.value;
    }
    document.forms[0].action = "registration_select_subset.jsp";
    document.forms[0].submit();
  }
}

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

function sendToEdit(task)
{
  // Total number of selected registrations
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  if (task == ("EDIT"))
  {
    if(!(numSelected < 1))
    {
      if(!(numSelected > 1))
      {
        document.forms[0].action = "registration_edit.jsp";
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
  else
  {
    document.forms[0].action = "registration_edit.jsp";
    document.forms[0].submit();
  }
}

function sendToRDC(protocolId, clinicalPpId)
{
  alert("i am here --- sendToRDC");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=J2eeRDC.fmx&userid=ctdev/oradev!246@ctdev&width=1280&height=770");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=rdc.fmx&userid=ctdev/oradev!246@ctdev&width=1280&height=770");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=rdc.fmx+username_p=cdr_bld+password_p=secret+database=ctdev+called_form_p=rdc.fmx+j2ee_server_url_p=http://156.40.131.29:8082/cpr/registration_select.jsp", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc?Username=cdr_bld+Password=secret+Database=ctdev", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc+Username=cdr_bld+Password=secret+Database=ctdev", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?form=rdc.fmx+Username=cdr_bld+Password=secret+Database=ctdev", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //window.open("http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc+username=cdr_bld+password=secret+", "Jin", "toolbar=yes,location=yes,directories=no,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
  //document.forms[0].action = "http://octrials-dev.nci.nih.gov/servlet/f60servlet?config=rdc";
  document.forms[0].hidProtocolId.value = protocolId;
  document.forms[0].hidClinicalPpId.value = clinicalPpId;
  alert(document.forms[0].hidProtocolId.value);
  alert(document.forms[0].hidClinicalPpId.value);
  document.forms[0].action = "openJ2ee.jsp";
  document.forms[0].target = "_blank";
  document.forms[0].submit();
  //window.open("openJ2ee.jsp");
}
