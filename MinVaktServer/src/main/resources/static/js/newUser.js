$(document).ready(function () {
    addCategories();

    $("#button").click(function () {
        var first_name = $("#first_name1").val();
        var last_name = $("#last_name1").val();
        var email = $("#email1").val();
        var phone = $("#phone1").val();
        var positionPercentage = $("#positionPercentage").val();
        var category = $("#category-box").val();

        console.log(category);

        addUser(JSON.stringify({
            "firstName": first_name,
            "lastName": last_name,
            "email": email,
            "phone": phone,
            "positionPercentage": positionPercentage,
            "categoryId": category
        }), function (data) {

            console.log(data);

            swal({
                title: "Fullført!",
                text: "Bruker er opprettet.",
                type: "success",
                confirmButtonText: "Ok"
            });

        })
    });
});

    function addCategories() {
        $.getJSON("/category", function (data) {
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

function getCategory(email, callback) {

    $.ajax({
        url: "/users/"+email+"/category",
        type: "GET",

        success: function (data) {
            callback(data);

        },
        error: function (data) {
            console.log("Error: "+JSON.stringify(data));
        }
    });
}

