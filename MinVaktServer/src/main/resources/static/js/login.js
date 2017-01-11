/**
 * Created by OlavH on 11-Jan-17.
 */

$("#logInButton").click(function () {

    var email = $("#emailInput").val();
    var password = $("#passwordInput").val();

    console.log("Pressed log on button - "+email+" - "+password);
    console.log(JSON.stringify({
        "email": email,
        "password": password
    }));

    $.ajax({
        url: "users/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "email": email,
            "password": password
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log("Password: "+data);
        },
        error: function (data) {
            alert("Failed! " + data);
        }
    });

});