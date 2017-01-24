/**
 * Created by kristinweiseth on 18.01.2017.
 */
$(document).ready(function () {

    $("#myTable").tablesorter();
    $("#myTable").tablesorter({sortList: [[0, 0], [1, 0]]});

    getAllShiftForCurrentUser(function (shifts) {

        console.log("Nr of shifts: " + shifts.length);

        for (var i = 0; i < shifts.length; i++) {

            const shift = shifts[i];

            const body = document.getElementById("dateBody");

            body.innerHTML +=
                "<tr>" +
                "<td><input type='checkbox' id='indeterminate-checkbox' />" +
                "<label for='indeterminate-checkbox'></label></td>" +
                "<td id = 'shiftId' style = 'display:none'>'+shift.shiftId'</td>" +
                "<td>" + shift.fromTime.split('T')[0] + "</td>" +
                "<td>" + shift.fromTime.split('T')[1].substr(0, 5) + "</td>" +
                "<td>" + shift.toTime.split('T')[1].substr(0, 5) + "</td>" +
                "</tr>";
        }

    });

    getCategories().then((categories) => {

        getAllUsers(function (employees) {

            const div = document.getElementById('employeeBody');
            for (let i = 0; i < employees.length; i++) {

                let employee = employees[i];

                getHoursThisWeekForUser(employee.employeeId, function (hours) {

                    div.innerHTML +=
                        "<tr>" +
                        "<td id = 'userId' style='display: none'>" + employee.employeeId + "</td>" +
                        "<td>" + employee.firstName + "</td>" +
                        "<td>" + employee.lastName + "</td>" +
                        "<td>" + categories.get(employee.categoryId).categoryName + "</td>" +
                        "<td>" + employee.positionPercentage + "%</td>" +
                        "<td>" + hours + " time(r)</td>" +
                        "</tr>"

                });
            }
        });
    });

    $("#button").click(function () {

        //var selected = employeeBody.getElementsByClassName('selected');

        getCurrentUser(function (user) {

            var toUser = $("tr.selected td:first").html();
            var fromUser = user.employeeId;

            console.log($("tr.checked td:first").html());

            console.log("fromUser: " + fromUser + " - toUser: " + toUser);

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


    });

    /*$("#sendButton").click(function() {

     var shift_id = $("#shiftIdInput").val();
     var user_id = $("#userIdInput").val();
     var user2_id = $("#userId2Input").val();

     requestChangeForShift(shift_id, user_id, user2_id);

     });*/


    function highlight(e) {
        if (selected[0]) selected[0].className = '';
        e.target.parentNode.className = 'selected';
    }

    var employeeBody = document.getElementById('employeeBody'),
        selected = employeeBody.getElementsByClassName('selected');


    $("#myTable1 tr").click(function (event) {
        if (event.target.type !== 'checkbox') {
            $(':checkbox', this).trigger('click');
        }
    });

    $("input[type='checkbox']").change(function (e) {
        if ($(this).is(":checked")) {
            $(this).closest('tr').addClass("highlight_row");
        } else {
            $(this).closest('tr').removeClass("highlight_row");
        }
    });
});