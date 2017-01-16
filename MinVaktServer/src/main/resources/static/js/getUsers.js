/**
 * Created by klk94 on 13.01.2017.
 */



$("#getShiftsButton").click(function () {

    $.ajax({
        url: "/users",
        type: "Get",
        contentType: "Application/JSON",


        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});