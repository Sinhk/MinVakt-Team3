/**
 * Created by OlavH on 20-Jan-17.
 */

function getAllChangeRequests(callback) {

    $.getJSON("/requestchange", function (data) {
        callback(data);
    })

}

function acceptChangeRequest(change_id) {

    $.ajax({
        url: "requestchange/"+change_id,
        type: "PUT",

        error: function (data) {
            console.log("Error: " + JSON.stringify(data));
        }
    });

}

function declineRequestChange(change_id) {

    $.ajax({
        url: "requestchange/"+change_id,
        type: "DELETE",

        error: function (data) {
            console.log("Error: " + JSON.stringify(data));
        }
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