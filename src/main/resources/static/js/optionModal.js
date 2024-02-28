 $(document).ready(function () {

        // When the modal is about to be shown
        $('#optionModal').on('show.bs.modal', function () {
            // Copy value from main form checkbox fields to modal checkboxes
            $('#status').prop('checked', $('#status').val() === 'true');
        });

        // When modal is about to be hidden
        $('#status').on('hide.bs.modal', function () {
            // Copy value from modal checkbox fields to main form checkboxes
            $('#status').val($('#status').is(':checked'));

        });
    });
