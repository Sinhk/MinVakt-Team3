/**
 * Created by Stine on 23.01.17.
 */
$(document).ready(function () {

    getCurrentUser(function (data) {

        $("#first_name1").val(data.firstName);

        $("#last_name1").val(data.lastName);

        $("#phone1").val(data.phone);

    });
});

function checkChangeUser() {
    if ($('#changeUserForm')[0].checkValidity()) {

        getCurrentUser(function(user){

            console.log(user);

            const id = user.employeeId;

            const newFirstName = $("#first_name1").val();

            const newLastName = $("#last_name1").val();

            const newPhone = $("#phone1").val();

            changeUser(id, JSON.stringify({
                "firstName": newFirstName,
                "lastName": newLastName,
                "phone": newPhone,
                }), function (data) {

                console.log(data.employeeId);

                swal({
                    title: "Oppdatert!",
                    text: "Brukerinformasjon er oppdatert.",
                    type: "success",
                    confirmButtonText: "OK",
                    confirmButtonColor: "#0d47a1"
                });
                },(data) => {

                   console.log(data);
                   console.log(data.status);
                   if (data.status == 200) {
                       swal({
                           title: "Bruker laget!",
                           text: "Ditt passord sendt på e-post.",
                           type: "success",
                           confirmButtonText: "OK",
                           confirmButtonColor: "#0d47a1"
                       });
                   } else { //if (data.status == 304)
                       swal({
                           title: "Feil input",
                           text: "Sjekk input linje med rød strek",
                           type: "error",
                           confirmButtonText: "OK",
                           confirmButtonColor: "#0d47a1"
                       });
                   }
                }
            )
        })
    }
}