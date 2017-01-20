/**
 * Created by elisabethmesoy on 19.01.2017.
 */

$(document).ready(function(){
    $("#button").click(function () {

        swal({   title: "Årsak til fravær",
                text: "Skriv inn årsaken til ditt fravær under",
                type: "input",   showCancelButton: true,
                closeOnConfirm: false,
                animation: "slide-from-top",
                inputPlaceholder: "Skriv her" },
            function(inputValue){
                if (inputValue === false) return false;
                if (inputValue === "") {
                    swal.showInputError("Du må skrive noe :(");
                    return false
                }
                swal("Kommentar sendt", "Du skrev: " + inputValue, "success");
            });

    });

    $("#table tr").click(function(){
        $(this).addClass('selected').siblings().removeClass('selected');
        var value=$(this).find('td:first').html();
        alert(value);
    });

    $('.ok').on('click', function(e){
        alert($("#table tr.selected td:first").html());
    });

});
