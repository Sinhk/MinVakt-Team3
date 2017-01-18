/**
 * Created by klk94 on 13.01.2017.
 */



$("#changeShiftFromUserToUser").click(function () {




    $.ajax({
        url: "shifts/"+shift_id,
        type: "PUT",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "userId1": userId1,
            "userId2": userId2,
        }),

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});