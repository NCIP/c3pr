<SCRIPT LANGUAGE="JavaScript">


function validateSSN(field){
  var ssnStr = field.value.length
   if(ssnStr>0){
     return SSNValidation(field)

   }else{
     return true
   }
}
function SSNValidation(ssn_f) {
  ssn = ssn_f.value
var matchArr = ssn.match(/^(\d{3})-?\d{2}-?\d{4}$/);
var numDashes = ssn.split('-').length - 1;
if (matchArr == null || numDashes == 1 || numDashes == 0) {
alert('Invalid SSN. Must be 9 digits in the format NNN-NN-NNNN.');
msg = "does not appear to be valid";
 ssn_f.focus()
 return false
}
else
if (parseInt(matchArr[1],10)==0) {
alert("Invalid SSN: SSN's can't start with 000.");
msg = "does not appear to be valid";
  ssn_f.focus()
  return false
}
else {
msg = "appears to be valid";
  return true
alert(ssn + "\r\n\r\n" + msg + " Social Security Number.");
   }
}

</script>
