    /**
 * Created by OlavH on 18-Jan-17.
 */

function getCurrentUserId(callback) {

    $.ajax({
        url: "/users/current",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {

            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}

function getAllUsers(callback) {

    $.getJSON("/users", function(data){callback(data)});

}
function addUser(jsonUser, callback) {

    $.ajax({
        url: "/users",
        type: "POST",
        contentType: "Application/JSON",
        data: jsonUser,
        success: function (data) {

            callback(data);
        },
        error: function (data) {
            console.log("Error: " + JSON.stringify(data));
        }
    });

}

function removeUser(user_id, callback) {

    $.ajax({
        url: "/users/"+user_id,
        type: "DELETE",

        success: function (data) {

                },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}

function getUserById(user_id, callback) {

    $.getJSON("/users/"+user_id, function(data){callback(data)});

}

function changeUser(user_id, jsonUser, callback) {

    $.ajax({
        url: "/users/"+user_id,
        type: "PUT",
        data: jsonUser,
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}
function changePassword(user_id, oldPass, newPass, callback) {

    $.ajax({
        url: "/users/"+user_id+"/password",
        type: "PUT",
        contentType: "Application/JSON",
        data: JSON.stringify({
            string1: oldPass,
            string2: newPass
        }),
        success: function (data) {
            callback(data);
        },
        error: function (error) {
            console.log("Error: " + data);
            callback(error);
        }
    });
}

function sendNewPassword(email, callback) {

    $.ajax({
        url: "/users/"+email+"/getNewPassword",
        type: "PUT",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}



function getShiftsForUser(user_id, callback) {

    $.getJSON("/users/"+user_id+"/shifts", function(data){callback(data)})

}

function getShiftsInRangeForUser(user_id, startDate, endDate, callback) {

    $.getJSON("/users/"+user_id+"/shifts/inrange?startDate="+startDate+"&endDate="+endDate, function(){callback(data)});

}

function getAssignedShiftsForUser(user_id, callback) {

    $.getJSON("/users/"+user_id+"/shifts/assigned", function (data) {
        callback(data);
    });

}
function getScheduledShiftsForCurrentUser(callback) {

    getCurrentUserId(function (id) {

        getAssignedShiftsForUser(id, function (assignedShifts) {

            callback(assignedShifts);

        })
    })

}

function getUsersThatCanBeResponsibleForShift(shift_id, callback) {

    $.getJSON("users/"+shift_id+"/responsible", function(data){callback(data)});

}


function getHoursThisWeekForUser(user_id, callback) {

    $.ajax({
        url: "users/"+user_id+"/hours",
        type: "GET",
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + JSON.stringify(data));
        }
    });
}