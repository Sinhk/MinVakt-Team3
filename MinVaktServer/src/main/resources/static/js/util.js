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

function toFullCalendarEvent(event, resource) {

    var start = event.startDateTime;
    var end = event.endDateTime;

    var dateStart = new Date(start);
    var dateEnd = new Date(end);

    return {
        id: event.shiftId,
        title: start.split("T")[1] + " -> " + end.split("T")[1],
        start: dateStart,
        end: dateEnd,
        resourceId: resource.id
    };
}

function listToFullCalendarEventList(events, resourceList) {

    var list = [];

    console.log("events: "+events);

    for(var i = 0; i<events.length; i++){

        var theEvents = events[i]; // dobbeliste av en eller annen grunn
        var resource = resourceList[i];

        for(var j = 0; j<theEvents.length; j++){

            list.push(toFullCalendarEvent(theEvents[j], resource));

        }

        //if(event != undefined) list.push(toFullCalendarEvent(event, resource));


    }

    return list;
}

function getEventViaID(id) {

    var event;

    $.ajax({

        async: false,
            url: "/shifts/"+id,
            type: "GET",
            contentType: "Application/JSON",

            success: function (data) {
            console.log("Success: /shifts/id.GET");

            event = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }

    })
    return event;

}

function getAllEmployees() {

    var users;

    $.ajax({

        async: false,
        url: "/users/",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: /users.GET");

            users = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }

    });

    return users;
}

function userListToResourceList(userlist) {

    var resourceList = [];

    for(var i = 0; i<userlist.length; i++){

        resourceList.push({id:userlist[i].employeeId,title:userlist[i].firstName +" "+ userlist[i].lastName})

    }
    return resourceList;

}

function getShiftsByEmployee(userlist) {


    var shiftList = [];

    for(var i = 0; i<userlist.length; i++){

        $.ajax({

            async: false,
            url: "/users/"+userlist[i].employeeId+"/shifts",
            type: "GET",
            contentType: "Application/JSON",

            success: function (data) {
                console.log("Success: /users.GET");
                shiftList.push(data);

            },
            error: function (data) {
                console.log("Error: "+data);
            }

        })
    }
    return shiftList;

}