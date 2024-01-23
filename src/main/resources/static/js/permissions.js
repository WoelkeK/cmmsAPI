 $(document).ready(function () {

        // When the modal is about to be shown
        $('#editModal').on('show.bs.modal', function () {
            // Copy value from main form checkbox fields to modal checkboxes
            $('#modalNReadable').prop('checked', $('#nRead').val() === 'true');
            $('#modalNEditable').prop('checked', $('#nEdit').val() === 'true');
            $('#modalNFullAccess').prop('checked', $('#nDelete').val() === 'true');
            $('#modalEReadable').prop('checked', $('#eRead').val() === 'true');
            $('#modalEEditable').prop('checked', $('#eEdit').val() === 'true');
            $('#modalEFullAccess').prop('checked', $('#eDelete').val() === 'true');
            $('#modalPReadable').prop('checked', $('#pRead').val() === 'true');
            $('#modalPEditable').prop('checked', $('#pEdit').val() === 'true');
            $('#modalPFullAccess').prop('checked', $('#pDelete').val() === 'true');
            $('#modalDReadable').prop('checked', $('#dRead').val() === 'true');
            $('#modalDEditable').prop('checked', $('#dEdit').val() === 'true');
            $('#modalDFullAccess').prop('checked', $('#dDelete').val() === 'true');
            $('#modalMReadable').prop('checked', $('#mRead').val() === 'true');
            $('#modalMEditable').prop('checked', $('#mEdit').val() === 'true');
            $('#modalMFullAccess').prop('checked', $('#mDelete').val() === 'true');
            $('#modalJReadable').prop('checked', $('#jRead').val() === 'true');
            $('#modalJEditable').prop('checked', $('#jEdit').val() === 'true');
            $('#modalJFullAccess').prop('checked', $('#jDelete').val() === 'true');
        });

        // When modal is about to be hidden
        $('#editModal').on('hide.bs.modal', function () {
            // Copy value from modal checkbox fields to main form checkboxes
            $('#nRead').val($('#modalNReadable').is(':checked'));
            $('#nEdit').val($('#modalNEditable').is(':checked'));
            $('#nDelete').val($('#modalNFullAccess').is(':checked'));
            $('#eRead').val($('#modalEReadable').is(':checked'));
            $('#eEdit').val($('#modalEEditable').is(':checked'));
            $('#eDelete').val($('#modalEFullAccess').is(':checked'));
            $('#pRead').val($('#modalPReadable').is(':checked'));
            $('#pEdit').val($('#modalPEditable').is(':checked'));
            $('#pDelete').val($('#modalPFullAccess').is(':checked'));
            $('#dRead').val($('#modalDReadable').is(':checked'));
            $('#dEdit').val($('#modalDEditable').is(':checked'));
            $('#dDelete').val($('#modalDFullAccess').is(':checked'));
            $('#mRead').val($('#modalMReadable').is(':checked'));
            $('#mEdit').val($('#modalMEditable').is(':checked'));
            $('#mDelete').val($('#modalMFullAccess').is(':checked'));
            $('#jRead').val($('#modalJReadable').is(':checked'));
            $('#jEdit').val($('#modalJEditable').is(':checked'));
            $('#jDelete').val($('#modalJFullAccess').is(':checked'));

        });
    });