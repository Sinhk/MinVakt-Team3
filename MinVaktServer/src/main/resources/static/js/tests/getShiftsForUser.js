/**
 * Created by klk94 on 12.01.2017.
 */

$("#getShiftsForUser").click(function () {

    var user_id = $("#userId").val();


    $.ajax({
        url: "users/"+user_id+"/shifts",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: "+JSON.stringify(data));
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});