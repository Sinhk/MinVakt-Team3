/**
 * Created by OlavH on 16-Jan-17.
 */


$(document).ready(function() {
    // page is now ready, initialize the calendar...
    console.log("Opening calendar..");

    var shifts = getAllShifts();

    var fullCalendarEvents = [];

    shifts.forEach(function (shift) {
        fullCalendarEvents.add(toFullCalendarEvent(shift));
        console.log(toFullCalendarEvent(shift));
    });

    console.log(fullCalendarEvents);

    $('#calendar').fullCalendar({
        // put your options and callbacks here

        dayClick:function (data ) {

            console.log("You clicked: "+data);

        },
        events: fullCalendarEvents



    });


});

function getAllShifts() {

    $.ajax({
        url: "/shifts",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: "+JSON.stringify(data));

            return data;
        },
        error: function (data) {
            console.log("Error: "+data);
            return [];
        }
    });
    return [];

}