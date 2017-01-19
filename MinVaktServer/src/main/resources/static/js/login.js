/**
 * Created by Stine on 19.01.17.
 */
$(document).ready(function() {
    $('.modal').modal();

    $('.btn').click(function() {
        swal({
            title: "Passord sendt!",
            type: "success",
            confirmButtonText: "Ok"
        });
    });
});