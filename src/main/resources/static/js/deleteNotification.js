    let selectedId = null;

    function openDeleteAllModal() {
        console.log("openDeleteAll ");
    let modal2 = document.getElementById('deleteAllModal');
    modal2.style.display = "block";
}

    function closeAllModal() {
    let modal2 = document.getElementById('deleteAllModal');
    modal2.style.display = "none";
}

    function executeDeleteAll() {
        console.log("executeDeleteAll()");
    fetch(`/notifications/deleteAll`, {
        method: 'GET'
    })
        .then(response => location.reload());
}

    function openDeleteModal(id) {
        console.log("openDeleteModal " + id);
    // The id value comes from the data-id attribute
    selectedId = id;
    let modal = document.getElementById('deleteModal');
    modal.style.display = "block";
}

    function closeModal() {
    let modal = document.getElementById('deleteModal');
    modal.style.display = "none";
}

    function executeDelete() {
        console.log("executeDelete()");
    fetch(`/api/notifications/delete/${selectedId}`, {
        method: 'DELETE'
    })
        .then(response => location.reload());
}


    window.onload = function() {

    let deleteButtons = document.getElementsByClassName('deleteBtn');
    for(let btn of deleteButtons) {
    btn.addEventListener('click', function(e) {
    e.preventDefault();
    openDeleteModal(this.getAttribute('data-id'));
});
}


    let deleteAllButtons = document.getElementsByClassName('deleteAllBtn');
    for(let btn of deleteAllButtons) {
    btn.addEventListener('click', function(e) {
    e.preventDefault();
    openDeleteAllModal();
});
}


    // Confirm the Delete
    document.getElementById('confirmDelete').addEventListener('click', function() {
    executeDelete();
});


    // Confirm the Delete
    document.getElementById('confirmDeleteAll').addEventListener('click', function() {
    executeDeleteAll();
});

}
