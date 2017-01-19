/**
 * Created by OlavH on 17-Jan-17.
 */

function addShiftToUser(shiftId, userId) {

}

function getAllScheduledShiftsForUser(callback) {
    $.ajax({
        url: "users/scheduled",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: /users/scheduled.GET "+data);
            callback(data);
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
}

function getStatusForShiftAndUser(shift_id, user_id) {

    var status;

    $.ajax({
        async:false,
        url: "shifts/"+shift_id+"/"+user_id+"/status",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: /users/scheduled.GET: "+data);

            status = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
    return status;

}
function setStatusForShiftAndUser(shift_id, user_id, status) {


    $.ajax({
        url: "shifts/"+shift_id+"/"+user_id+"/status",
        type: "PUT",
        contentType: "text/plain",
        data: status,

        success: function (data) {
            console.log("Success: "+data);
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });

}

function userIsResponsibleForShift(shift_id, user_id) {

    var isResponsible;

    $.ajax({
        async: false,
        url: "shifts/"+shift_id+"/"+user_id+"/responsible",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {

            isResponsible = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
    return isResponsible;

}

function getResponsibleUserForShift(shift_id) {


    var isResponsible;

    $.ajax({
        async: false,
        url: "shifts/"+shift_id+"/responsible",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {

            isResponsible = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
    return isResponsible;

}

function getResponsibleUsersForShifts(shifts) {

    var list = [];

    for(var i = 0; i < shifts.length; i++){

        var employeeShifts = shifts[i];

        for(var i = 0; i < employeeShifts.length; i++) {
            list.push(getResponsibleUserForShift(employeeShifts[i].shiftId));
            console.log(employeeShifts[i]);
        }

    }
    return list;

}

function setResponsibleUserForShift(shift_id, user_id, boolean) {


    $.ajax({
        async: false,
        url: "shifts/"+shift_id+"/"+user_id+"/responsible",
        type: "PUT",
        contentType: "Application/JSON",
        data: boolean,

        success: function (data) {
            console.log("Success: " +data);
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
}

function changeShiftFromUserToUser(fromUser_id, toUser_id, shift_id) {

    console.log(fromUser_id+" - "+toUser_id+" - "+shift_id);

    $.ajax({
        async: false,
        url: "shifts/"+shift_id+"/changeUser",
        type: "PUT",
        contentType: "Application/JSON",
        data: JSON.stringify({
            "int1": fromUser_id,
            "int2": toUser_id
        }),

        success: function (data) {
            console.log("Success: "+JSON.stringify(data))
        },

        error: function (data) {
            console.log("Error: "+JSON.stringify(data));
        }
    });


}

function getShiftsByDay(day) {

    var list;

    $.ajax({
        async: false,
        url: "shifts/byday",
        type: "GET",
        contentType: "text/plain",
        data: day,

        success: function (data) {
            console.log("Success: "+JSON.stringify(data))
            list = data;
        },

        error: function (data) {
            console.log("Error: "+JSON.stringify(data));
        }
    });

    return list;

}

function eventIsAvailable(event_id) {

    var is;

    $.ajax({
        url: "shifts/"+event_id+"/isAvailable",
        type: "GET",
        type: "GET",

        success: function (data) {
            is = data;
        },

        error: function (data) {
            console.log("Error: eventIsAvailable");
        }
    });
    return is;
}
function getShiftsWithRequestChange(callback) {
    $.ajax({
        url: "shifts/requestchange",
        type: "GET",

        success: function (data) {
            console.log("Success: "+JSON.stringify(data));
            callback(data);
        },

        error: function (data) {
            console.log("Error: "+JSON.stringify(data));
        }
    });
}

function requestChange(shift_id, user1_id, user2_id) {

    $.ajax({
        async: false,
        url: "/"+shift_id+"/users/"+user1_id+"/requestchange/"+user2_id,
        type: "POST",

        success: function (data) {
            console.log("Success: "+JSON.stringify(data));
        },

        error: function (data) {
            console.log("Error: "+JSON.stringify(data));
        }
    });

}
function getAvaiableShifts() {

    var list;

    $.ajax({
        async: false,
        url: "/shifts/available",
        type: "GET",

        success: function (data) {
            console.log("Success: "+JSON.stringify(data));
            list = data;
        },

        error: function (data) {
            console.log("Error: "+JSON.stringify(data));
        }
    });
    return list;

}