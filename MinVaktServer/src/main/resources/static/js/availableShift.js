

$(document).ready(function(){

    getCurrentUser(function (user) {

        getShiftsForUser(user.employeeId, function (shifts) {

            for(var i = 0; i < shifts.length; i++) {

                const shift = shifts[i];

                document.getElementById("category-box").innerHTML +=

                    "<option value= '"+shift.shiftId+"' id = shift"+shift.shiftId+">"+shift.fromTime.split("T")[0]+" "+shift.fromTime.split("T")[1].substr(0,5)+"</option>"
            }
        })
    })

    var body = document.getElementById("table1");


    getAvailableShifts(function(data) {
        var shifts = data
        for(var i = 0; i<shifts.length; i++) {

            var shift = shifts[i];
               console.log(shifts);
            body.innerHTML += "<tr>" +
                "<td><input type='checkbox' id='test5' />" +
                "<label for='test5'></label></td>" +
                "<td>"+shift.fromTime.split('T')[0]+"</td>" +
                "<td>"+shift.fromTime.split('T')[1].substr(0,5)+"</td>" +
                "</tr>";
        }
    });


    $("#button").click(function () {

        var selected = table1.getElementsByClassName('selected');

        console.log(selected);


        swal({  title: "Success",
                text: "Du har ønsket deg vakten",
                type: "success",   showCancelButton: false,
                closeOnConfirm: false,
                animation: "slide-from-top",
        }
        );

    });

    function highlight(e) {
        if (selected[0]) selected[0].className = '';
        e.target.parentNode.className = 'selected';
    }

    var table1 = document.getElementById('table1'),
        selected = table1.getElementsByClassName('selected');
    table1.onclick = highlight;




    $("#table1 tr").click(function (event) {
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
