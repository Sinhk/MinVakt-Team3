/**
 * Created by klk94 on 12.01.2017.
 */

/**
 * Created by OlavH on 11-Jan-17.
 */
$("#getEmailbutton").click(function () {

    var getEmail = $("#getEmail").val();

    $.ajax({
        url: "users/user",
        type: "POST",
        contentType: "Text/plain",
        data: getEmail,

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});