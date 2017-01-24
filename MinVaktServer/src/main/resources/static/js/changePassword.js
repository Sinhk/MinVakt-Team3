/**
 * Created by Stine on 23.01.17.
 */

var password = document.getElementById("new_psw")
    , confirm_password = document.getElementById("new_psw_confirm");

function validatePassword() {
    if (password.value != confirm_password.value) {
        confirm_password.setCustomValidity("Passordene er forskjellige");
    } else {
        confirm_password.setCustomValidity('');
    }
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;

function submitPwChange() {
    if ($('#pwChangeForm')[0].checkValidity()) {
            const oldPsw = $("#old_psw").val();
            const newPsw = $("#new_psw").val();

        changePassword(oldPsw, newPsw, (status) => {
                if (status == 200) {
                    swal({
                        title: "Oppdatert!",
                        text: "Ditt passord er endret.",
                        type: "success",
                        confirmButtonText: "Ok"
                    });
                } else { //if (data.status == 304) 
                    swal({
                        title: "418 I'm a teapot",
                        text: "Gammelt passord er feil",
                        //type: "error",
                        imageUrl: "images/teapot.jpg",
                        confirmButtonText: "Ok"
                    });
                }
            });
        }
}