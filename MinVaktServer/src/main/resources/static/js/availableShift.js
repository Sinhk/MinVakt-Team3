

$(document).ready(function(){
    const calendar = $('#calendar');
    $.getJSON("/shifts/available").then((shifts) =>{
        let eventsPr =  shifts.map(toAvailableEventPromise);
        Promise.all(eventsPr).then((events) => {
            calendar.fullCalendar('addEventSource', events);
        });
    });

    calendar.fullCalendar({
        locale: "no",
        selectable: true,
        header: {
            left: 'prev, today',
            center: 'title',
            right: 'next'
        },
        firstDay: 1,
        weekNumbers: true,
        dayNames: ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'],
        dayNamesShort: ['Søn', 'Man', 'Tir', 'Ons', 'Tor', 'Fre', 'Lør'],
        monthNames: ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'],
        weekNumberTitle: 'Uke',
        buttonText: {
            today: 'I dag'
        },
        defaultView: 'listMonth',
        noEventsMessage: 'Ingen ledige vakter',
        eventClick: function(calEvent){
            newWish(calEvent);
        }


    });
});

function newWish(event) {
    console.log(event.start.dayOfYear());
    swal({
            title: "Vil du ønske deg denne vakten?",
            text: "Vakt: " + event.start.format('DD/MM') + event.start.format("HH:mm") + " - " + event.end.format("HH:mm"),
            showCancelButton: true,
            //confirmButtonColor: "#11dd07",
            confirmButtonText: "Ja",
            cancelButtonText: "Nei",
            closeOnConfirm: false,
            showLoaderOnConfirm: true,
        },
        function(){
            $.post("/shifts/"+event.id+"/wish").then(()=> {
                swal("Vaktønske er registrert","","success");
                $('#calendar').fullCalendar('removeEvents', function (eventa) {
                    return event.start.dayOfYear() === eventa.start.dayOfYear();
                });
            }).catch(()=>{
                swal("Noe gikk galt","","error")
            });

        });
}

/*
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
*/

   






