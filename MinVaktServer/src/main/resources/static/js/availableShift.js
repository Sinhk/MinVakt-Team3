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
   






