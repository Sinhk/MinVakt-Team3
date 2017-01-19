/**
 * Created by OlavH on 18-Jan-17.
 */


function getHourThisWeek(user, callback) {
    $.ajax({
        url: "users/" + user.employeeId + "/getHoursThisWeek",
        type: "GET",

        success: function (data) {
            console.log("Success: /users/scheduled.GET "+data);

            callback(user, data);
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });
}