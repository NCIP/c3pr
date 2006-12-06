//  Title       : participant_select.js
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

function participantSelect(index, elementsBeforeList, elementsAfterCheckBox)
{
  var numIndex = new Number(index);
  numIndex = numIndex * (elementsAfterCheckBox + 1) + elementsBeforeList;

  // Total number of selected participants
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  // If the participant is being selected, increment counter
  if (document.forms[0].elements[numIndex].checked)
  {
    numSelected++;
    document.forms[0].hidNumSelected.value = numSelected;
  }
  // If the participant is not being selected, remove it from the list
  else
  {
    numSelected--;
    document.forms[0].hidNumSelected.value = numSelected;
  }
}

function sendToEdit()
{
  // Total number of selected participants
  var numSelected = new Number(document.forms[0].hidNumSelected.value);

  if(!(numSelected < 1))
  {
    if(!(numSelected > 1))
    {

      //document.forms[0].action = "participantEdit.jsp";
      document.forms[0].dmlOperation.value = "Update";
      document.forms[0].submit();
    }
    else
    {
      alert("Please select only one participant!");
    }
  }
  else
  {
    alert("Please select a participant!");
  }
}
