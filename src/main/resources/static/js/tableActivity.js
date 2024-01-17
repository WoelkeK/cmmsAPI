
    window.onload = function() {
    document.querySelectorAll('tr[data-person-id]').forEach(function(row) {
        row.addEventListener('click', function() {
            var personId = this.getAttribute('data-person-id');
            // open modal and load person details
            document.querySelector('#myModal').style.display = "block";
            // or send ajax request to load person details
            // on success open the modal
        });
    });
    document.querySelector('#myModal .close').addEventListener('click', function() {
    document.querySelector('#myModal').style.display = "none";
});
}
