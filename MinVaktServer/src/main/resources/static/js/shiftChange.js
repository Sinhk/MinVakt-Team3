/**
 * Created by elisabethmesoy on 24.01.2017.
 */

$(document).ready(function(){

    $("#button").click(function () {
        swal({
                title: "Bytting av vakt",
                /*kan legge inn "sikker på at du vil bytte din vakt den: " + datoen på vakten du valgte + "med" + navn på person du vil bytte med*/
                text: "Sikker på at du vil bytte denne vakten?",
                /*type: "warning",*/
                showCancelButton: true,
                confirmButtonColor: "#4caf50 ",
                confirmButtonText: "Ja",
                closeOnConfirm: false
            },
            function () {

                swal("Forespøsel om vaktbytte sendt!", " ", "success");
            });
    })

})