/**
 * Created by OlavH on 16-Jan-17.
 */
$(document).ready(function() {
    // page is now ready, initialize the calendar...
    console.log("Opening calendar..");

    var shifts = getAllShifts();

    console.log(shifts);

    var fullCalendarEvents = [];


    for(var i = 0; i<shifts.length; i++){

        fullCalendarEvents.push(toFullCalendarEvent(shifts[i]));

    }

    $('#calendar').fullCalendar({
        // put your options and callbacks here

        dayClick:function (data ) {

            console.log("You clicked: "+data);

        },
        events: fullCalendarEvents



    });

    for(var i = 0; i<shifts.length; i++) {

        $('#calendar').fullCalendar( 'renderEvent', fullCalendarEvents[i], true);

        console.log(fullCalendarEvents[i]);

    }



});

function getAllShifts() {

    var shifts;

    $.ajax({
        async: false,
        url: "/shifts",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: /shifts.GET");

            shifts = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });

    return shifts;

}

function toFullCalendarEvent(event) {

    var start = event.startDateTime;
    var end = event.endDateTime;

    var dateStart = new Date(start[0], start[1], start[2], start[3], start[4]);
    var dateEnd = new Date(end[0], end[1], end[2], end[3], end[4]);

    console.log(event);

    var full = {
        title: dateStart+" - "+dateEnd,
        start: dateStart,
        end: dateEnd
    };

    return full;

}