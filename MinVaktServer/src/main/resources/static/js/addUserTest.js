$("#addUser").click(function () {

    var email = $("#email").val();
    var psw = $("#psw").val();
    var tlf = $("#tlf").val();
    var prcnt = $("#percentage").val();

    console.log(email+" - "+psw+" - "+tlf+" - "+prcnt);
    console.log(JSON.stringify({
        "email": email,
        "password": psw,
        "tlf":tlf,
        "positionPercentage":prcnt
    }));

    $.ajax({
        url: "/users",
        type: "POST",
        contentType: "application/JSON",
        data: JSON.stringify({
            "email": email,
            "password": psw,
            "phone":tlf,
            "positionPercentage":prcnt
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function (data) {
            console.log("You added a user - "+JSON.stringify(data));
        },
        error: function (data) {
            alert("Failed! " + JSON.stringify(data));
        }
    });

});