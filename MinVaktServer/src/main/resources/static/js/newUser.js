$("#button").click(function () {
    var first_name1 = $("#first_name1").val();
    var last_name1 = $("#last_name1").val();
    var email = $("#email1").val();
    var phone1 = $("#phone1").val();
    var positionPercentage = $("#positionPercentage").val();
    var category = $("#category").val();

    newUser(first_name1,last_name1,email,phone1,positionPercentage);
    changeCategory(email1,category);
});

function newUser(first_name1,last_name1,email,phone1,positionPercentage) {


    $.ajax({
        async: false,
        url: "/users",
        type: "POST",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "first_name1":first_name1,
            "last_name1":last_name1,
            "email": email,
            "phone1":phone1,
            "positionPercentage":positionPercentage,
        }),
        success: function (data) {
            console.log("Success");
            return data;
        },
        error: function (data) {
            console.log("Error: "+data);
            return data;
        }
    });
}


function changeCategory(email1,category) {



    $.ajax({
        async: false,
        url: "/users/"+email1+"/changeCategory",
        type: "POST",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "email1":email1,
            "categry":category,

        }),
        success: function (data) {
            console.log("Success");
            return true;
        },
        error: function (data) {
            console.log("Error: "+data);
            return false;
        }
    });
}

function getCategory(email) {


    var c;

    $.ajax({
        async: false,
        url: "/users/"+email+"/getCategory",
        type: "GET",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "email":email,

        }),
        success: function (data) {
            console.log("Success");

            c = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
    return c;
}