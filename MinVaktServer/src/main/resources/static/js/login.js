/**
 * Created by Stine on 19.01.17.
 */
$(document).ready(function() {
    $('.modal').modal();

    $('#newpw').click(function() {
        swal({
            title: "Passord sendt!",
            type: "success",
            closeOnConfirm: true,
            animation: "slide-from-top",
            confirmButtonText: "Ok"
        });
    });

});