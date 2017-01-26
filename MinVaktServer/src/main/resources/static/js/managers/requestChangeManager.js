/**
 * Created by OlavH on 20-Jan-17.
 */

function getNumChangeRequests(callback) {

    $.getJSON("/requestchange",{count: true}, function (data) {
        callback(data);
    })

}

function getAllChangeRequests(callback) {

    $.getJSON("/requestchange", function (data) {
        callback(data);
    })

}

function acceptChangeRequest(change_id) {

    return $.ajax({
        url: "requestchange/"+change_id,
        type: "PUT",
    });

}

function declineRequestChange(change_id) {

    return $.ajax({
        url: "requestchange/"+change_id,
        type: "DELETE",
    });

}

function requestChangeForShift(shift_id, user1_id, user2_id, callback) {
    
    $.ajax({
        url: "requestchange/?shift_id="+shift_id+"&user1_id="+user1_id+"&user2_id="+user2_id,
        type: "POST",
        contentType: "Application/JSON",

        success: function (data) {

            callback(data);
        },
        error: function (data) {
            console.log("Error: " + data);
        }
    });

}

function isOkChangeRequest (request_id,callback) {
$.ajax({
        url: "/requestchange/"+request_id+"/isOkChangeRequest",
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
