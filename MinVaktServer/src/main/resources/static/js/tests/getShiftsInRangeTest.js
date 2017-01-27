/**
 * Created by OlavH on 13-Jan-17.
 */

$("#getShiftsButton").click(function () {

    var user_id = $("#userId").val();

    var startDate = $("#startDateInput").val();
    var endDate = $("#enddateinput").val();

    $.ajax({
        url: "/users/"+user_id+"/shifts/inrange",
        type: "GET",
        data: JSON.stringify({
            "startDate":startDate,
            "endDate":endDate
        }),
        success: function (data) {
            console.log("Success: "+JSON.stringify(data));
        },
        error: function (data) {
            console.log("Error: "+JSON.stringify(data))
        }
    })

});



