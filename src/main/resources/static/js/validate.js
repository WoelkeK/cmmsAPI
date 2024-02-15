document.querySelector('form').addEventListener('submit', function(event) {

    let fields = document.getElementsByClassName('validate');
    let errors = document.getElementsByClassName('error');

    for(let i=0; i<fields.length; i++){

    if(fields[i].value ==""){
         event.preventDefault();
            // Display error message
            errors[i].textContent = "Należy uzupełnić, pole wymagane!.";
        } else {
            // Clear any error message
            errors[i].textContent = '';
    }
    }
});