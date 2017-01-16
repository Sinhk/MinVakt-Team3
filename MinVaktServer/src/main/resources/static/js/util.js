/**
 * Created by OlavH on 16-Jan-17.
 */

function parseLocalDateTimeToDate(localdatetime) {

    return new Date(localdatetime);

}
/*

function toFullCalendarEvent(event) {

    var start = event.startDateTime;
    var end = event.endDateTime;

    var dateStart = new Date(start[0], start[1], start[2], start[3], start[4]);
    var dateEnd = new Date(end[0], end[1], end[2], end[3], end[4]);


    var full = {
        id: event.id,
        title: dateStart+" - "+dateEnd,
        start: dateStart,
        end: dateEnd
    };

    return full;

}*/


function getAllSuitableShifts() {

    var shifts;

    $.ajax({
        async: false,
        url: "/shifts/suitable",
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
