/**
 * Created by elisabethmesoy on 19.01.2017.
 */

$(document).ready(function(){

    $("#button").click(function(){


        swal({  title: "Årsak til fravær",
                text: "Skriv inn årsaken til ditt fravær under",
                type: "input",   showCancelButton: true,
                closeOnConfirm: false,
                animation: "slide-from-top",
                inputPlaceholder: "Skriv her" },
            function(inputValue){
                if (inputValue === false) return false;
                if (inputValue === "") {
                    swal.showInputError("Du må skrive noe");
                    return false
                }
                swal("Kommentar sendt", "Du skrev: " + inputValue, "success");
            }
        );

    })


    getCurrentUser(function (user) {

        getShiftsForUser(user.employeeId, function (shifts) {

            for(var i = 0; i < shifts.length; i++) {

                const shift = shifts[i];

                document.getElementById("category-box").innerHTML +=

                    "<option value= '"+shift.shiftId+"' id = shift"+shift.shiftId+">"+shift.fromTime.split("T")[0]+" "+shift.fromTime.split("T")[1].substr(0,5)+"</option>"
            }
        })
    })

});
