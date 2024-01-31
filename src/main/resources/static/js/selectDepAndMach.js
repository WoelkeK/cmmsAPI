
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

        machineSelect.innerHTML = ''; // Clear previous options

       if(machines.length>0){
        machineSelect.style.visibility = "visible";
        jobstatusField.style.visibility = "visible";
       }else{
        machineSelect.style.visibility = "hidden";
        jobstatusField.style.visibility = "hidden";
       }
        // Create a list of machines
            machines.forEach(function (machine) {

            var option = document.createElement('option');
            option.value = machine.id;
            option.text = machine.name;
            machineSelect.appendChild(option);

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
