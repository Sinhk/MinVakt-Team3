$("#addShiftToUser").click(function () {


    var userId = $("#userId").val();
    var shiftId = $("#shiftId").val();

    console.log(userId+" - "+shiftId);

    $.ajax({
        url: "users/"+userId+"/shifts/"+shiftId,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "userId": userId,
            "shiftId": shiftId,
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log("You added shift to user - "+data);
        },
        error: function (data) {
            alert("Failed! " + data);
        },
        error: function (data1) {
            alert("Failed! " + data1);
        }
    });

});