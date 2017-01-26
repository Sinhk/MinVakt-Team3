

$(document).ready(function(){

    var body = document.getElementById("table1");
    var shifts;
        getAvailableShifts(function(data) {
        shifts = data
        });


    getAllAvailableShifts(function () {
        for(var i = 0; i<shifts.length; i++) {

            var shift = shifts[i];

            body.innerHTML += "<tr>" +
                "<td><input type='checkbox' id='indeterminate-checkbox' />" +
                "<label for='indeterminate-checkbox'></label></td>" +
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