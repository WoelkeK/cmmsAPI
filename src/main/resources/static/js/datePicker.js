var pickerInput = document.getElementById("pickerInput");
var datePicker;

// handle opening the modal and creating the date picker
document.getElementById('openModalButton').addEventListener('click', function() {
    document.getElementById('modal').style.remove('hidden');
    datePicker = flatpickr(pickerInput, {
        enableTime: true,
        dateFormat: "Y-m-d H:i",
    });
});

// handle closing the modal and saving the date to the hidden field
document.getElementById('closeButton').addEventListener('click', function() {
    document.getElementById('modal').classList.add('hidden');
    document.getElementById('dateTimeField').value = pickerInput.value;
    datePicker.destroy();
});