/**
 * Created by kristinweiseth on 27.01.2017.
 */
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