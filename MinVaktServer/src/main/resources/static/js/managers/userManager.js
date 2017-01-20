/**
 * Created by OlavH on 18-Jan-17.
 */
function getAllUsers(callback) {

    $.getJSON("/users",callback(data));

}
function addUser(jsonUser, category_id, callback) {

    $.ajax({
        url: "/users",
        type: "POST",
        contentType: "Application/JSON",
        data: jsonUser,
        data2: category_id,
        success: function (data) {

            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });

}

function removeUser(user_id, callback) {

    $.ajax({
        url: "/users/",
        type: "DELETE",
        data: user_id,
        success: function (data) {

            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });

}

function getUserById(user_id, callback) {

    $.getJSON("/users/"+user_id, callback(data));


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
        data: JSON.stringify({
            string1: oldPass,
            string2: newPass
        }),
        success: function (data) {
            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });
}
function getShiftsForUser(user_id, callback) {

    $.getJSON("/users/"+user_id+"/shifts", callback(data))

}

function getShiftsInRangeForUser(user_id, startDate, endDate, callback) {

    $.getJSON("/users/"+user_id+"/shifts/inrange?startDate="+startDate+"&endDate="+endDate, callback(data));

}
function getScheduledShiftsForCurrentUser(callback) {

    $.getJSON("/users/scheduled", callback(data));

}

function getUsersThatCanBeResponsibleForShift(shift_id, callback) {

    $.getJSON("users/"+shift_id+"/responsible", callback(data));

}

function getHoursThisWeekForUser(user_id, callback) {

    $.getJSON("users/"+user_id+"/getHoursThisWeek", callback(data));

}