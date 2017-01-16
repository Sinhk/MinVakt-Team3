/**
 * Created by klk94 on 12.01.2017.
 */

/**
 * Created by OlavH on 11-Jan-17.
 */
$("#getEmailButton").click(function () {

    var getEmail = $("#getEmail").val();

    $.ajax({
        url: "users/"+getEmail+"/",
        type: "GET",
        contentType: "Text/plain",

        success: function (data) {
            console.log("Success: "+JSON.stringify(data));
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});