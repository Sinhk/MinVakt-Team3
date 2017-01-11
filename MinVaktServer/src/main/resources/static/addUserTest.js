$("#addUser").click(function () {

    var email = $("#email").val();
    var psw = $("#psw").val();
    var tlf = $("#tlf").val();
    var prcnt = $("#percentage").val();

    $.ajax({
        url: "users",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "email": email,
            "password": psw,
            "tlf":tlf,
            "positionPercentage":prcnt
        }),
        /*beforeSend: function(x) {
         x.setRequestHeader('Authorization', 'Bearer ' );
         },*/

        success: function () {
            console.log("You added a user");
        },
        error: function (data) {
            alert("Failed! " + data);
        }
    });

});