/**
 * Created by Stine on 19.01.17.
 */
$(document).ready(function() {
    $('.modal').modal();
/*
    $('#newpw').click(function() {
        swal({
            title: "Passord sendt!",
            type: "success",
            closeOnConfirm: true,
            animation: "slide-from-top",
            confirmButtonText: "Ok"
        });
    });
    */
    $('#newpw').click(function () {
            var email = $("#email_input").val();
                console.log("fg");
                sendNewPassword(email,function(svar) {

                   if(svar==true){
                   swal({
                    title: "Passord sendt!",
                        type: "success",
                        closeOnConfirm: true,
                        animation: "slide-from-top",
                        confirmButtonText: "Ok"
                    });
                   }else {
                   swal({
                   title: "Email har ikke en bruker!",
                       type: "error",
                       closeOnConfirm: true,
                       animation: "slide-from-top",
                       confirmButtonText: "Ok"
                   });
                   }
                   }
                );
        });

});

