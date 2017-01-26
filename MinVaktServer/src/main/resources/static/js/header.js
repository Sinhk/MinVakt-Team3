/**
 * Created by OlavH on 24-Jan-17.
 */

$(document).ready(function () {

    getNumChangeRequests(function (requests) {

        const notifications = $("#numberOfNotifications");
        notifications.text(requests);
        if(requests >0){
            notifications.addClass('new red');
        }

    })


});