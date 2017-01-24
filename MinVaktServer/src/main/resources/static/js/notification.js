/**
 * Created by elisabethmesoy on 23.01.2017.
 */

$(document).ready(function(){
    $('.collapsible').collapsible();

    getAllChangeRequests(function(requests){

        console.log(requests);

        for (var i = 0; i < requests.length; i++){

            const request = requests[i];

            console.log(request);

            getShiftWithId(request.shiftId, function (shift) {

                getUserById(request.oldEmployeeId, function (oldUser) {

                    getUserById(request.newEmployeeId, function (newUser) {

                        console.log(shift);
                        console.log(oldUser);
                        console.log(newUser);

                        document.getElementById("requestChangeNotificationsList").innerHTML +=

                            "<li class='collection-item avatar card-panel white' id="+request.requestId+"collapse"+">"+
                            "<div class='collapsible-header white black-text'><i class='material-icons circle blue lighten-2'>message</i><b>Vaktbytte</b></div>"+
                            "<div class='collapsible-body white black-text'><p>"+oldUser.firstName+" "+oldUser.lastName+" vil bytte vakt med "+newUser.firstName+" "+newUser.lastName+" fra "+shift.fromTime.split("T")[0]+" "+shift.fromTime.split("T")[1].substr(0,5)+" til "+shift.toTime.split("T")[0]+" "+shift.toTime.split("T")[1].substr(0,5)+"</p>" +
                                "<a class='waves-effect waves-light blue lighten-2 btn' id= accept"+request.requestId+">Godkjenn</a><a class='waves-effect waves-light blue lighten-2 btn' id='decline"+request.requestId+"'>Ikke godkjenn</a></div>"+
                            "</li>"

                        $("#accept"+request.requestId).click(function () {

                            $("#acceptButton").click();

                            swal("Vakten ble byttet", "", "success")
                            $("#"+request.requestId+"collapse").hide();

                            acceptChangeRequest(request.requestId);




                        })
                        $("#decline"+request.requestId).click(function () {

                            $("#declineButton").click();

                            swal("Vakten ble ikke byttet", "", "error");
                            $("#" + request.requestId + "collapse").hide();

                            declineRequestChange(request.requestId);
                        })
                    })
                })
            })
        }
    })
});

