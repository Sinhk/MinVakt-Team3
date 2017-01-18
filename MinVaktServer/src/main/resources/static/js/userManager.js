/**
 * Created by OlavH on 18-Jan-17.
 */


function getHourThisWeek(user_id) {

    var hours;

    $.ajax({
        async: false,
        url: "users/"+user_id+"/getHoursThisWeek",
        type: "GET",

        success: function (data) {
            console.log("Success: /users/scheduled.GET "+data);

            hours = data;
        },
        error: function (data) {
            console.log("Error: "+data);
        }
    });

    return hours;



}