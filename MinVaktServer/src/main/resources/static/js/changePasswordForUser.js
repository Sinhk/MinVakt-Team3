/**
 * Created by klk94 on 13.01.2017.
 */


$("#changePasswordForUser").click(function () {

    var user_id = $("#userId").val();
    var oldPassAttempt =$("#oldPassAttempt").val();
    var newPassAttempt =$("#newPassAttempt").val();




    $.ajax({
        url: "users/"+user_id+"/changepassword",
        type: "PUT",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "oldPassAttempt": oldPassAttempt,
            "newPassAttempt": newPassAttempt,
        }),

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});