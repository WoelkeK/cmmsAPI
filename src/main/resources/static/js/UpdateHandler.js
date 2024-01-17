window.addEventListener('DOMContentLoaded', function() {

    var modal = document.getElementById("myModal");
    var span = document.getElementsByClassName("close")[0];

    document.querySelectorAll('tr[id^="row"]').forEach(function(row) {
        row.addEventListener('mouseover', function() {
            this.classList.add('row-hover');
        });

        row.addEventListener('mouseout', function() {
            this.classList.remove('row-hover');
        });

        row.addEventListener('click', function() {
            var reportId = this.firstElementChild.textContent; // get reportId

            // Here you have reportId, and you can use it to populate modal,
            // set it to hidden input in the form or send an AJAX request

            // this is an example of how you can add it to a hidden field in the form
            document.getElementById('modal-report-id').value = reportId;

            modal.style.display = "block";
        });
    });

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});

function changeStatus() {
    var reportId = document.getElementById('modal-report-id').value;
    var row = document.getElementById('row' + reportId);
    row.style.display = 'none';
}