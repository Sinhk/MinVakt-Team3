/**
 * Created by OlavH on 11-Jan-17.
 */

$("#logInButton").click(function () {

    var email = $("#emailInput").val();
    var password = $("#passwordInput").val();

    console.log("Pressed log on button");

    $.ajax({
        url: "users/login",
        type: "GET",
        contentType: "application/json",
        data: JSON.stringify({
            "email": email,
            "password": password
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log(data + " - Logged inn succesfully!");
        },
        error: function (data) {
            alert("Failed! " + data);
        }
    });

});