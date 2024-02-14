
    document.addEventListener('DOMContentLoaded', function () {
        // No initial call here, as we want to trigger on user selection only


    });

    function selectMachine() {
        var departmentId = document.getElementById('departmentId').value;

        // Make AJAX request to fetch machines
        fetch('/api/machines/byDepartment?departmentId=' + departmentId)
            .then(response => response.json())
            .then(data => {
                displayMachineList(data);
            })
            .catch(error => console.error('Error:', error));
    }

    function displayMachineList(machines) {
        var machineSelect = document.getElementById('machineId');
        var selectedMachineValueInput = document.getElementById('selectedMachineValue');
        var jobstatusField = document.getElementById("jobStatus");
        var viewable = document.getElementsByClassName("viewable");



        machineSelect.innerHTML = ''; // Clear previous options
        var option1 = document.createElement('option');
        option1.text = "Wybierz";
        option1.value ="";
        machineSelect.appendChild(option1);

       if(machines.length>0){
        machineSelect.style.visibility = "visible";
        jobstatusField.style.visibility = "visible";

        for(let i=0; i<viewable.length; i++){
        viewable[i].style.visibility="visible";
        }

       }else{
        machineSelect.style.visibility = "hidden";
        jobstatusField.style.visibility = "hidden";

         for(let i=0; i<viewable.length; i++){
                 viewable[i].style.visibility="hidden";
                 }
       }
        // Create a list of machines



            machines.forEach(function (machine) {
            var option2 = document.createElement('option');
            option2.value = machine.id;
            option2.text = machine.name;
            option2.selected;
            machineSelect.appendChild(option2);

        });
    }

    function updateMachineIdAttribute(selectedMachineValue) {
        // Update the hidden input field with the selected machine value
        document.getElementById('selectedMachineValue').value = selectedMachineValue;
    }

    function disableSelectBoxJob() {
console.log('disableSelectBoxJob')
var field1Value = document.getElementById("jobStatus").value;
var field2Select = document.getElementById("hideDate");


if ((field1Value === "PRZEGLĄD") || (field1Value === "SZKOLENIE")) {
field2Select.style.visibility = "visible";
} else {
field2Select.style.visibility = "hidden";
}
}

        function disableCycleJob() {
console.log('disableCycleJob')
var field3Value = document.getElementById("repeatable").value;
var field4Select = document.getElementById("hideOffset");

if (field3Value === "TAK") {
field4Select.style.visibility = "visible";
} else {
field4Select.style.visibility = "hidden";
}
}
