$(document).ready(function () {
    addCategories();

    $("#button").click(function () {
        var first_name = $("#first_name1").val();
        var last_name = $("#last_name1").val();
        var email = $("#email1").val();
        var phone = $("#phone1").val();
        var positionPercentage = $("#positionPercentage").val();
        var category = $("#category-box").val();

        addUser(JSON.stringify({
            "firstName": first_name,
            "lastName": last_name,
            "email": email,
            "phone": phone,
            "positionPercentage": positionPercentage
        }), category, function (data) {

            //Send epost, data = passordet

        })
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