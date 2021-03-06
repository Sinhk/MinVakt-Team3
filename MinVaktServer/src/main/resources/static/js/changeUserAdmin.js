/**
 * Created by elisabethmesoy on 25.01.2017.
 */

$(document).ready(function () {

    getCurrentUser(function (data) {


        $("#first_name1").val(data.firstName);

        $("#last_name1").val(data.lastName);

        $("#email1").val(data.email);

        $("#phone1").val(data.phone);

        $("#position_percentage1").val(data.positionPercentage);

    });

    $("#updateBtn").click(function () {

        getCurrentUser(function(user){

            console.log(user);

            var id = user.employeeId;

            var newFirstName = $("#first_name1").val();

            var newLastName = $("#last_name1").val();

            var newEmail = $("#email1").val();

            var newPhone = $("#phone1").val();

            var newPositionPercentage = $("#position_percentage1").val();

            changeUser(id, JSON.stringify({
                "firstName": newFirstName,
                "lastName": newLastName,
                "email": newEmail,
                "phone": newPhone,
                "positionPercentage": newPositionPercentage,
            }), function (data) {

                console.log(data.employeeId);

                swal({
                    title: "Oppdatert!",
                    text: "Brukerinformasjon er oppdatert.",
                    type: "success",
                    confirmButtonText: "Ok"
                });
            })
        })

    })
});