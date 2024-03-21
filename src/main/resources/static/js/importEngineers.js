$(document).ready(function(){
    $('#uploadButton').on('click', function() {
        var formData = new FormData($('#uploadForm')[0]);
        $.ajax({
            xhr: function() {
                var xhr = new window.XMLHttpRequest();

                // Upload progress
                xhr.upload.addEventListener("progress", function(evt) {
                    if (evt.lengthComputable) {
                        var percentComplete = (evt.loaded / evt.total) * 100;
                        // Update the progress bar's width
                        $("#progressBar div").css('width', percentComplete + '%');
                    }
                }, false);
                return xhr;
            },
            url: "/engineers/upload",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            beforeSend: function() {
                $('#progressBar').show();
                $('#uploadButton').hide();
                // Reset the progress bar at the start of the request
                $("#progressBar div").css('width', '0%');
            },
            success: function() {
                // Server responded successfully. Do whatever you need to do here.

                $('#uploadModal').modal('hide');
                location.reload();
            },
            complete: function() {
                // Now hide the progress bar
                $('#progressBar').hide();
                $('#uploadButton').show();
            },
            error: function() {
                alert("Błąd wgrywania pliku na serwer!");
                // If there is an error, you may want to reset the progress bar
                $("#progressBar div").css('width', '0%');
            }
        });
    });
});