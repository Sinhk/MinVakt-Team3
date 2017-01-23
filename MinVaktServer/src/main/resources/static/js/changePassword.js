/**
 * Created by Stine on 23.01.17.
 */
$(document).ready(function() {

    $("#changePasswordButton").click(function () {

        getCurrentUserId(function(id) {

            const oldPsw = $("#old_psw").val();
            const newPsw = $("#new_psw").val();

            changePassword(id, oldPsw, newPsw, function(data){

                var regex = /(?=.*[!@#$%^&*_=+-])(?=.*[a-z])(?=.*[A-Z]).{8,}/;

                if (regex.test(oldPsw) == true){
                    swal({
                        title: "Oppdatert!",
                        text: "Ditt passord er oppdatert.",
                        type: "success",
                        confirmButtonText: "Ok"
                    });
                } else {
                    swal({
                        title: "Noe gikk galt!",
                        text: "Ditt passord ble ikke oppdatert, prøv igjen",
                        type: "error",
                        confirmButtonText: "Ok"
                    });
                    return false;
                }
            })
        })
    })
});