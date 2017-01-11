/**
 * Created by OlavH on 11-Jan-17.
 */
$("#removeEmailButton").click(function () {

    var email = $("#removeEmailInput").val();

    $.ajax({
        url: "users/removeUser",
        type: "POST",
        contentType: "Text/plain",
        data: email,

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});