/**
 * Created by Stine on 23.01.17.
 */
$(document).ready(function() {

    getCurrentUserId(function(id){

        getUserById(id, function(data){

            console.log(data)

            $("#first_name1").val(data.firstName);

            $("#last_name1").val(data.lastName);

            $("#email1").val(data.email);

            $("#phone1").val(data.phone);

        })
    });



    $('#buttonUpdate').click(function() {
        swal({
            title: "Oppdatert!!",
            text: "Din brukerinformasjon er oppdatert.",
            type: "success",
            confirmButtonText: "Ok"
        });
    })
});