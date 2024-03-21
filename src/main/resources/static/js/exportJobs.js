$(document).ready(function(){
    $('#downloadButton').on('click', function() {
        // Pokaż pasek postępu i ukryj przycisk
        $('#exportBar').show();
        $('#downloadButton').hide();

        // Ustaw URL dla pobierania danych
        var exportUrl = "/jobs/exportAll";

        // Inicjuj pobieranie pliku
        window.location.href = exportUrl;

        // Ukryj pasek postępu po pewnym czasie, zakładając, że rozpoczął się proces pobierania
        setTimeout(function() {
            $('#exportBar').hide();
            $('#downloadButton').show();
            $('#downloadModal').modal('hide');  // Ukryj okno modalne
        }, 10000);
    });
});