document.querySelector('form').addEventListener('submit', function(event) {
    var machineSelect = document.getElementById('machineId');
    var departmentSelect = document.getElementById('departmentId');
    var employeeSelect = document.getElementById('employeeId');
    var errorElement1 = document.getElementById('error1');
    var errorElement2 = document.getElementById('error2');

    if (machineSelect.value == "") {
        // Prevent the form from being submitted
        event.preventDefault();
        // Display error message
        errorElement1.textContent = "Proszę wybrać urządzenie.";
    } else {
        // Clear any error message
        errorElement1.textContent = '';
    }
    if (departmentSelect.value == "") {
        // Prevent the form from being submitted
        event.preventDefault();
        // Display error message
        errorElement2.textContent = "Proszę wybrać wydział.";
    } else {
        // Clear any error message
        errorElement2.textContent = '';
    }
});