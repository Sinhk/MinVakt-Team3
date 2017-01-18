/**
 * Created by OlavH on 17-Jan-17.
 */

function addShiftToUser(shiftId, userId) {



}

function getAllScheduledShiftsForUser() {

    var list;

    $.ajax({
        async: false,
        url: "users/scheduled",
        type: "GET",
        contentType: "Application/JSON",

        success: function (data) {
            console.log("Success: /users/scheduled.GET "+data);

            list = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });

    return list;
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