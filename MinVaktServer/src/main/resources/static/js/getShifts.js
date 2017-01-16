/**
 * Created by klk94 on 13.01.2017.
 */


$("#getShiftsButton").click(function () {

    $.ajax({
        url: "/shifts",
        type: "Get",
        contentType: "Text/plain",

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});