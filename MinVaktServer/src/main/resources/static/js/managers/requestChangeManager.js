/**
 * Created by OlavH on 20-Jan-17.
 */

function requestChangeForShift(shift_id, user1_id, user2_id, callback) {

    // TODO ny controller
    $.ajax({
        url: "/requestchange/",
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