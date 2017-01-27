$(document).ready(function () {
    addCategories();
});
function addUserForm() {
    if ($('#newUserForm')[0].checkValidity()) {
            const first_name = $("#first_name1").val();
            const last_name = $("#last_name1").val();
            const email = $("#email1").val();
            const phone = $("#phone1").val();
            const positionPercentage = $("#positionPercentage").val();
            const category = $("#category-box").val();

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
                  confirmButtonText: "OK",
                  confirmButtonColor: "#0d47a1"
              });

          },(data) => {
        console.log(data);
        console.log(data.status);
        if (data.status == 200) {
            swal({
                title: "Bruker laget!",
                text: "Ditt passord sendt på e-post.",
                type: "success",
                confirmButtonText: "OK",
                confirmButtonColor: "#0d47a1"
            });
        } else { //if (data.status == 304)
            swal({
                title: "Feil input",
                text: "Sjekk input linje med rød strek",
                type: "error",
                confirmButtonText: "OK",
                confirmButtonColor: "#0d47a1"
            });
        }
    });
    }
}

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
