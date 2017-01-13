$("#addShiftToUser").click(function () {

    var email = $("#email").val();
    var psw = $("#psw").val();
    var tlf = $("#tlf").val();
    var prcnt = $("#percentage").val();
    var date = $("#date").val();
    var preInter = $("#preInter").val();

    console.log(email+" - "+psw+" - "+tlf+" - "+prcnt);
    console.log(JSON.stringify({
        "email": email,
        "password": psw,
        "tlf":tlf,
        "positionPercentage":prcnt
    }));

    console.log(date+" - "+preInter);
    console.log(JSON.stringify({
        "date": date,
        "preInter": preInter,
    }));

    $.ajax({
        url: "users/addShiftToUser",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "email": email,
            "password": psw,
            "tlf":tlf,
            "positionPercentage":prcnt
        }),
        data1: JSON.stringify({
            "date": date,
            "preInter": preInter,
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