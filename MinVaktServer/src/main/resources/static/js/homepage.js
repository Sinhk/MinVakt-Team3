$(document).ready(function(){
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    /*$('.modal').modal({
            dismissible: true, // Modal can be dismissed by clicking outside of the modal
            opacity: .5, // Opacity of modal background
            in_duration: 300, // Transition in duration
            out_duration: 200, // Transition out duration
            starting_top: '4%', // Starting top style attribute
            ending_top: '10%', // Ending top style attribute
            ready: function(modal, trigger) { // Callback for Modal open. Modal and trigger parameters available.
                alert("Ready");
                console.log(modal, trigger);
            },
            complete: function() { alert('Closed'); } // Callback for Modal close
        }
    );*/

    $('.modal-trigger').leanModal();



    var shifts = getAllSuitableShifts();

    console.log(shifts);

    var fullCalendarEvents = [];

    for(var i = 0; i<shifts.length; i++){

        fullCalendarEvents.push(toFullCalendarEvent(shifts[i]));

    }

    $('#calendar').fullCalendar({

        displayEventTime: false,
        locale: "no",
        timezone: "UTC",
        selectable: true,
        header: {
            left:'prev, today',
            center:'title',
            right:'next'
        },
        firstDay: 1,
        weekNumbers: true,
        dayNamesShort: ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'],
        monthNames: ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'],
        weekNumberTitle: 'Uke',
        buttonText: {
            today:    'I dag',
            month:    'Måned',
            week:     'Uke',
            day:      'Dag',
            list:     'Liste'
        },
        select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format()+
            "\nEnd date: " + moment(end).format());

        },
        /*dayClick:function (data ) {
            console.log("You clicked: "+data);
        },*/
        events: fullCalendarEvents,

        eventClick: function( event, jsEvent, view ) {

            var eventId = event.id;

            var eventDB = getEventViaID(eventId);

            console.log(eventDB);

        }
    });
});