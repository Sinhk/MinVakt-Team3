/**
 * Created by OlavH on 16-Jan-17.
 */

$("#addUser").click(function () {

    var firstname = $("#first_name");
    var lastname= $("#lastname");

    var email = $("#email").val();
    var tlf = $("#phone").val();
    var prcnt = $("#positionPercentage").val();

    $.ajax({
        url: "/users",
        type: "POST",
        contentType: "application/JSON",
        data: JSON.stringify({
            "firstName":firstname,
            "lastName":lastname,
            "email": email,
            "phone":tlf,
            "positionPercentage":prcnt
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log("You added a user - "+JSON.stringify(data));
        },
        error: function (data) {
            alert("Failed! " + JSON.stringify(data));
        }
    });

});