$(document).ready(function () {
    addCategories();


    $("#button").click(function () {
        var first_name1 = $("#first_name1").val();
        var last_name1 = $("#last_name1").val();
        var email = $("#email1").val();
        var phone1 = $("#phone1").val();
        var positionPercentage = $("#positionPercentage").val();
        var category = $("#category-box").val();

        newUser(first_name1, last_name1, email, phone1, positionPercentage, category);
    });
});

    function addCategories() {
        $.getJSON("/category",function (data) {
            var $box = $("#category-box");
            $box.empty();
            $box.append($("<option disabled selected></option>").attr("value",'').text('Velg stilling'))
            $.each(data,function (key, value) {
                $box.append($("<option></option>").val(value.categoryId).text(value.categoryName));
            })
        })
    }

    function newUser(first_name, last_name, email, phone, positionPercentage, categoryId) {
        $.ajax({
            url: "/users/"+categoryId,
            type: "POST",
            contentType: "Application/JSON",
            data: JSON.stringify({
                "firstName": first_name,
                "lastName": last_name,
                "email": email,
                "phone": phone,
                "positionPercentage": positionPercentage
            }),
            success: function (data) {
                console.log("Success");
                return data;
            },
            error: function (data) {
                console.log("Error: " + data);
                return data;
            }
        });
    }


    function changeCategory(email1, category) {

    $.ajax({
        async: false,
        url: "/users/"+email1+"/changeCategory",
        type: "POST",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "email1":email1,
            "categry":category

            }),
            success: function (data) {
                console.log("Success");
                return true;
            },
            error: function (data) {
                console.log("Error: " + data);
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