$("#addShiftToUser").click(function () {

    var int1 = $("#shift_id").val();
    var int2 = $("#user_id").val();


    console.log(int1+" - "+int2);

    $.ajax({
        url:  "shifts/"+int1,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            "int1": int1,
            "int2": int2,
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log("You added shift to user - "+JSON.stringify(data));
        },
        error: function (data) {
            alert("Failed! " + data);
        },
        error: function (data1) {
            alert("Failed! " + data1);
        }
    });

});