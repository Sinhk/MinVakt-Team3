/**
 * Created by OlavH on 24-Jan-17.
 */

$(document).ready(function () {

    getNumChangeRequests(function (requests) {

        $("#numberOfNotifications").text(requests);

    })


});