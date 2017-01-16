/**
 * Created by klk94 on 13.01.2017.
 */

$("#removeShiftFromUserButton").click(function () {

    var user_id = $("#user_id").val();
    var shift_id = $("#shift_id").val();


    $.ajax({
        url: "users/"+user_id+"/shifts/"+shift_id,
        type: "DELETE",
        contentType: "Text/plain",

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});