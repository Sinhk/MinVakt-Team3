/**
 * Created by klk94 on 12.01.2017.
 */

$("#getUsersForShiftButton").click(function () {

    var shift_id = $("#getShift_id").val();


    $.ajax({
        url: "shifts/"+shift_id+"/users",
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