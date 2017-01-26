/**
 * Created by OlavH on 17-Jan-17.
 */

function getAllShifts(callback) {

    $.getJSON("/shifts", function(data){callback(data)})

}

function getAllAssignedShifts(callback) {

    $.getJSON("/shifts/assigned", function (data) {callback(data)});

}

function getAllNonAssignedShifts(callback) {

    $.getJSON("/shifts/nonassigned", function (data) {callback(data)});

}

function getShiftWithId(id, callback) {

    $.getJSON("/shifts/"+id, function(data){callback(data)});

}

function getShiftAssignmentForShiftAndUser(shift_id, user_id, callback) {

    $.getJSON("/shifts/"+shift_id+"/details/"+user_id, function (data) {
        callback(data);
    })

}

function addShift(dateinfo, callback) {

    $.ajax({
        url: "/shifts",
        type: "POST",
        contentType: "Application/JSON",
        data: dateinfo,
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });

}

function getUsersForShift(shift_id, callback) {

    $.getJSON("shifts/"+shift_id+"/users", function(data){callback(data)});

}

function addUserToShift(user_id, shift_id, available, responsible, callback) {

    $.ajax({
        url: "/shifts/"+shift_id+"/users?user_id="+user_id+"&available="+available+"&responsible="+responsible,
        type: "POST",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + JSON.stringify(data));
        }
    });
}
function changeUserAssignment(user_id, shift_id, available, responsible, assigned, absent, comment, callback) {

    $.ajax({
        url: "/shifts/"+shift_id+"/users?user_id="+user_id+"&available="+available+"&responsible="+responsible+"&assigned="+assigned+"&absent="+absent+"&comment="+comment,
        type: "PUT",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + JSON.stringify(data));
        }
    });
}


function getShiftAssignmentsForShift(shift_id, callback) {

    $.getJSON("shifts/"+shift_id+"/details", function(data){callback(data)});

}


/*function shiftIsAvailable(shift_id, callback) {

    $.ajax({
        url: "/shifts/"+shift_id+"/available",
        type: "GET",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}*/ //USE THE ONE BELLOW

function getAvailableShifts(callback) {

    $.getJSON("/shifts/available", function(data){callback(data)});

}



function userIsResponsibleForShift(shift_id, user_id, callback) {

    getResponsibleUserForShift(shift_id, function (res) {

        callback(res.employeeId == user_id);

    })


}

function getResponsibleUserForShift(shift_id, callback) {


    $.ajax({
        url: "shifts/"+shift_id+"/responsible",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {

            callback(data)
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });

}

function getResponsibleUsersForShifts(shifts) {

    var list = [];

    for(var i = 0; i < shifts.length; i++){

        var employeeShifts = shifts[i];

        for(var j = 0; j < employeeShifts.length; j++) {
            getResponsibleUserForShift(employeeShifts[j].shiftId, function (user) {
                list.push(user);
            });
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
        url: "shifts/"+event_id+"/available",
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
/*  already made?
function getAvailableShifts() {

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
*/

function getAllSuitableShifts(callback) {

    $.getJSON("/shifts/suitable", function(data){callback(data)});

}

function getAllShiftForCurrentUser(callback) {

    getCurrentUser(function (user) {

        getShiftsForUser(user.employeeId, function (shifts) {

            callback(shifts);

        })
    })
}

function deleteShiftAssignment(id) {

    $.ajax({
        url: "/shifts/shiftassignments/"+id,
        type: "DELETE",
        success: function (data) {
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });

}
function getShiftAssignmentsForUser(user_id, callback) {

    $.ajax({
        url: "/shifts/shiftassignments/?user_id="+user_id,
        type: "GET",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}

function getShiftByShiftAssignmentId(shiftAssignmentId, callback) {

    $.ajax({
        url: "/shifts/shiftassignments/"+shiftAssignmentId,
        type: "GET",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}

function getMissingPerShiftCategory(shift_id,callback) {

    $.ajax({
           url: "/shifts/"+shift_id+"/getAmountOnShiftWithRequired",
            type: "GET",
            success: function (data) {
                callback(data);
            },
            error: function (data) {
                console.log("Error: " + data);
            }
    });
}

