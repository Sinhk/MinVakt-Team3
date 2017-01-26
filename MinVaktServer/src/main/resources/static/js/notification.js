/**
 * Created by elisabethmesoy on 23.01.2017.
 */

$(document).ready(function () {
    $('.collapsible').collapsible();

    getAllChangeRequests(function (requests) {
        for (let i = 0; i < requests.length; i++) {

            const request = requests[i];

            const oldUser = request.oldEmployee;
            const newUser = request.newEmployee;
            const shift = request.shift;
            const allowed = request.allowed;
            const missing = request.hasOwnProperty('missing');
            const from = moment(shift.fromTime);
            const newUserName = newUser.firstName + " " + newUser.lastName;
            document.getElementById("requestChangeNotificationsList").innerHTML +=

                "<li class='collection-item avatar card-panel white' id=" + request.requestId + "collapse" + ">" +
                "<div class='collapsible-header white black-text'><i class='material-icons circle red'>message</i><b>Vaktbytte: " + from.format("DD/MM") + "</b></div>" +
                "<div class='collapsible-body white black-text'>" + oldUser.firstName + " " + oldUser.lastName + " vil bytte vakt med " + newUserName +
                "<br> Vakt: Fra " + from.format("HH:mm") + " til " + moment(shift.toTime).format("HH:mm") + ", " + from.format("DD/MM/YYYY") +
                ((missing)?"<br> Dette fører til for lite "+ request.missing:"")+
                ((!missing && !allowed)?"<br><div class='red-text'> " + newUserName +" er allerede registrert for denne vakta</div>":"") +
                ((request.overtime) ? ("<br><div class='red-text'>Dette fører til overtid</div><br>" ) : ("<br>")) +
                    "<a class='waves-effect waves-light blue darken-4 btn'  onclick='acceptChange(" + request.requestId + ")' id= 'accept" + request.requestId + "'>Godkjenn</a>" +
                    "<a class='waves-effect waves-light blue darken-4 btn' onclick='declineChange(" + request.requestId + ")' id='decline" + request.requestId + "'>Ikke godkjenn</a></div>" +
                    "</li>";
            if (!allowed) {
                $('#accept' + request.requestId).addClass('disabled');
            }
        }
    });
});

function acceptChange(id) {
    acceptChangeRequest(id).then(() => {
        swal("Vakten ble byttet", "", "success");
        $("#" + id + "collapse").hide();
    });

}

function declineChange(id) {
    declineRequestChange(id);

    swal("Vakten ble ikke byttet", "", "error");
    $("#" + id + "collapse").hide();
}

