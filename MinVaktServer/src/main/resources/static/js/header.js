/**
 * Created by OlavH on 24-Jan-17.
 */

$(document).ready(function () {

    getAllChangeRequests(function (requests) {

        $("#numberOfNotifications").text(requests.length);

    })


});