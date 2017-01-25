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


function toFullCalendarEventWithResource(event, resource) {
    var start = event.fromTime;
    var end = event.toTime;

    var dateStart = new Date(start);
    var dateEnd = new Date(end);
    var available = eventIsAvailable(event.shiftId);
    getResponsibleUserForShift(event.shiftId, function (responsible) {
        console.log();
        //console.log("responsible: "+JSON.stringify(responsible));
        //console.log("Avdeling: "+event.comments);
        return {
            id: event.shiftId || 0,
            title: start.split("T")[1].substr(0,3) + " -> " + end.split("T")[1].substr(0,3),
            start: dateStart,
            end: dateEnd,
            resourceId: resource.id,
            backgroundColor: responsible != undefined && responsible.employeeId == resource.id ? "#9B0300" : available ? "#3E9B85" : "#3F7F9B",
            isResponsible: responsible,
            available: available,

            //backgroundColor: event.responsible != undefined && event.responsible.employeeId == resource.id ? "#9B0300" : "#3E9B85"
        };
    });

}



function toFullCalendarEvent(event, callback) {

    console.log(event);
    if (event != undefined) {

        var start = event.fromTime;
        var end = event.toTime;

        var dateStart = new Date(start);
        var dateEnd = new Date(end);
        var department=getDepartmentofShift(event.shiftId,function(name) {department=name});


        shiftIsAvailable(event.shiftId, function (available) {

            getResponsibleUserForShift(event.shiftId, function (responsible) {

                //console.log(start + " - " + end + " - " + dateStart + " - " + dateEnd + " - " + available + " - " + responsible)

                //console.log("Avdeling: "+event.comments);

                callback( {

                    id: event.shiftId,
                    title: start.split("T")[1].substr(0, 3) + " -> " + end.split("T")[1].substr(0, 3),
                    start: dateStart,
                    end: dateEnd,
                    //backgroundColor: available ? "#9B0300" : "#3E9B85",
                    available: available,
                    //TODO avdeling
                    avdeling: department,
                    isResponsible: responsible != undefined ? responsible.firstName + " " + responsible.lastName : "Ingen"
                });

            });
        });
    }
}

function getDepartmentofShift(shiftId,callback) {

    $.ajax({
            async: true,
            url: "/shifts/" +shiftId + "/department",
            type: "GET",
            contentType: "Text/Plain",
            success: function (data) {
            console.log("Sucsess: " + data);
                      callback(data);

            },
            error: function (data) {
                console.log("Error: " + data);
            }

    })
}

function listToFullCalendarEventList(events, resourceList) {

    var list = [];

    //console.log("events: "+events);

    for (var i = 0; i < events.length; i++) {

        var event = events[i]; // dobbeliste av en eller annen grunn
        var resource = resourceList[i];

        list.push(toFullCalendarEventWithResource(event, resource));
        //if(event != undefined) list.push(toFullCalendarEvent(event, resource));
    }

    return list;
}

function userListToResourceList(userlist) {

    var resourceList = [];

    for (var i = 0; i < userlist.length; i++) {

        resourceList.push({id: userlist[i].employeeId, title: userlist[i].firstName + " " + userlist[i].lastName})

    }
    return resourceList;

}

function getShiftsByEmployee(userlist) {

    function getShiftsByEmployee(userlist) { // TODO sync

        var shiftList = [];

        for (var i = 0; i < userlist.length; i++) {

            $.ajax({
                async: false,
                url: "/users/" + userlist[i].employeeId + "/shifts",
                type: "GET",
                contentType: "Application/JSON",

                success: function (data) {
                    //console.log("Success: /users.GET");
                    shiftList.push(data);

                },
                error: function (data) {
                    console.log("Error: " + data);
                }

            })
        }
        return shiftList;

    }

}

function userListToResourceList(userlist) {

    var resourceList = [];

    for (var i = 0; i < userlist.length; i++) {

        resourceList.push({id: userlist[i].employeeId, title: userlist[i].firstName + " " + userlist[i].lastName});

    }
    return resourceList;

}