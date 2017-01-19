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


function getAllSuitableShifts(callback) {

    $.ajax({
        url: "/shifts/suitable",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {

            callback(data);

        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });


}

function toFullCalendarEventWithResource(event, resource) {

    var start = event.startDateTime;
    var end = event.endDateTime;

    var dateStart = new Date(start);
    var dateEnd = new Date(end);
    var responsible = getResponsibleUserForShift(event.shiftId);
    var available = eventIsAvailable(event.shiftId);
    console.log()
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
        available: available

    };
}

function toFullCalendarEvent(event) {

    //console.log(event)


    var start = event.startDateTime;
    var end = event.endDateTime;

    var dateStart = new Date(start);
    var dateEnd = new Date(end);


    var available = eventIsAvailable(event.shiftId);
    var responsible = getResponsibleUserForShift(event.shiftId);


    //console.log("Avdeling: "+event.comments);

    return {
        id: event.shiftId,
        title: start.split("T")[1].substr(0,3) + " -> " + end.split("T")[1].substr(0,3),
        start: dateStart,
        end: dateEnd,
        status: event.status,
        backgroundColor: available ? "#9B0300":"#3E9B85",
        available: available,
        avdeling: event.comments,
        isResponsible: responsible != undefined ? responsible.firstName +" "+responsible.lastName : ""
    };
}


function listToFullCalendarEventList(events, resourceList) {

    var list = [];

    //console.log("events: "+events);

    for(var i = 0; i<events.length; i++){

        var theEvents = events[i]; // dobbeliste av en eller annen grunn
        var resource = resourceList[i];

        for(var j = 0; j<theEvents.length; j++){

            list.push(toFullCalendarEventWithResource(theEvents[j], resource));

        }

        //if(event != undefined) list.push(toFullCalendarEvent(event, resource));


    }

    return list;
}

function getEventViaID(id, callback) {

    $.ajax({

            url: "/shifts/"+id,
            type: "GET",
            contentType: "Application/JSON",

            success: function (data) {
            //console.log("Success: /shifts/id.GET"+data);

            callback(data);
        },
        error: function (data) {
            console.log("Error: "+data);
        }

    })

}

function getAllEmployees(callback) {

    $.ajax({

        url: "/users/",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            //console.log("Success: /users.GET");

            callback(data);
        },
        error: function (data) {
            console.log("Error: "+data);
        }

    });
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
                //console.log("Success: /users.GET");
                shiftList.push(data);

            },
            error: function (data) {
                console.log("Error: "+data);
            }

        })
    }
    return shiftList;

}