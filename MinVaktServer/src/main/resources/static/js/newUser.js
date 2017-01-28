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
        }), function () {
            swal({
                title: "Bruker opprettet!",
                text: "Passord sendt på e-post.",
                type: "success",
                confirmButtonText: "OK",
                confirmButtonColor: "#0d47a1"
            });

            $('#newUserForm ')[0].reset();
        });
    }
}

function addCategories() {
    $.getJSON("/category", function (data) {
        var $box = $("#category-box");
        $box.empty();
        $box.append($("<option disabled selected></option>").attr("value", '').text('Velg stilling'));
        $.each(data, function (key, value) {
            $box.append($("<option></option>").val(value.categoryId).text(value.categoryName));
        })
    })
}
