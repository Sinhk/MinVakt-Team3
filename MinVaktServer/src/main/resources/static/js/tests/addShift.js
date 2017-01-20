/**
 * Created by klk94 on 12.01.2017.
 */
$("#addShift").click(function () {



    var startD = $("#startD").val();
    var startY = $("#startY").val();
    var startH = $("#startH").val();
    var startMonth = $("#startMonth").val();
    var startMin = $("#startMin").val();

    var endD = $("#endD").val();
    var endY = $("#endY").val();
    var endH = $("#endH").val();
    var endMonth = $("#endMonth").val();
    var endMin = $("#endMin").val();

    $.ajax({
        url: "/shifts",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "startD": startD,
            "startY": startY,
            "startH": startH,
            "startMonth": startMonth,
            "startMin":startMin,
            "endD": endD,
            "endY": endY,
            "endH": endH,
            "endMonth":endMonth,
            "endMin": endMin,
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log("You added shift - "+data);
        },
        error: function (data) {
            alert("Failed! " + data);
        },
    });

});