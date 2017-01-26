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

                const shift_id = $("#category-box").val();

                const user_id = $("#category-box1").val();


                getCurrentUser(function (currentUser) {

                    console.log(shift_id+" - "+currentUser.employeeId+" - "+user_id);

                    requestChangeForShift(shift_id, currentUser.employeeId, user_id, function (data) {

                        document.getElementById("shift"+shift_id).parentElement.removeChild(document.getElementById("shift"+shift_id));
                    })
                })
            });
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

    getAllUsers(function (users) {

        for(var i = 0; i < users.length; i++) {

            const user = users[i];

            document.getElementById("category-box1").innerHTML +=

                "<option value= '"+user.employeeId+"' id='user"+user.employeeId+"'>"+user.firstName+" "+user.lastName+"</option>"
        }



    })


})