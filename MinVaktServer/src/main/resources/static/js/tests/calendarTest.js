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

        locale: "nb",
        timezone: "local",
        dayClick:function (data ) {
            console.log("You clicked: "+data);
        },
        events: fullCalendarEvents,

        eventClick: function( event, jsEvent, view ) {

            console.log(event);

        }




    });

    for(var i = 0; i<shifts.length; i++) {

        $('#calendar').fullCalendar( 'renderEvent', fullCalendarEvents[i], true);

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

    var dateStart = new Date(start);
    var dateEnd = new Date(end);

    var full = {
        title: start.split("T")[1]+" -> "+end.split("T")[1],
        start: dateStart,
        end: dateEnd
    };


    return full;

}