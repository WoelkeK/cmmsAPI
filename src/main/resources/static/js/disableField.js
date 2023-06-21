
       function disableSelectBox() {
       console.log('disableSelectBox')
var field1Value = document.getElementById("status").value;
var field2Select = document.getElementById("employee");

if ((field1Value === "MAGAZYN_SERWEROWNIA") || (field1Value === "MAGAZYN_SZAFA") || (field1Value === "ZUTYLIZOWANY") || (field1Value === "DO_UTYLIZACJI")) {
field2Select.disabled = true;
field2Select.value = "";
} else {
field2Select.disabled = false;
}
}
