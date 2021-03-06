$(document).ready(function () { // document ready

    /* initialize the external events
     -----------------------------------------------------------------*/
    $('#external-events .fc-event').each(function () {


        var title = $.trim($(this).text());
        const theTitle  = title;

        title = title.replace("U: ","");

        $(this).data('event', {
            title: theTitle, // use the element's text as the event title
            available: !this.id.includes("FRAVÆR"),
            startTime: title.split("-")[0],
            endTime: title.split("-")[1],
            //start_id: $.trim($(this).text()) == "Formiddagsvakt" ? 1 : $.trim($(this).text()) == "Ettermiddagsvakt" ? 2 : 3,
            save: true,
            stick: true // maintain when user navigates (see docs on the renderEvent method)
        });
        /* console.log("ID: "+this.id);

         console.log("UNAVAILABLE: "+this.id.includes("u"))
         */
        // make the event draggable using jQuery UI
        $(this).draggable({
            zIndex: 999,
            revert: true,      // will cause the event to go back to its
            revertDuration: 0  //  original position after the drag
        });


    });

    /* initialize the calendar
     -----------------------------------------------------------------*/

    $('#calendar').fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        displayEventTime: false,
        locale: "no",
        timezone: "UTC+1",
        selectable: true,
        resourceAreaWidth: 230,
        aspectRatio: 1.5,
        scrollTime: '00:00',
        header: {
            left: 'prev',
            center: 'title',
            right: 'next'
        },
        weekNumbers: true,
        firstDay: 1,
        defaultView: 'month',
        dayNamesShort: ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'],
        monthNames: ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'],
        weekNumberTitle: 'Uke',
        buttonText: {
            today: 'I dag',
            month: 'Måned',
            week: 'Uke',
            day: 'Dag',
            list: 'Liste'
        },
        editable: false, // enable draggable events
        droppable: true, // this allows things to be dropped onto the calendar

        eventRender: function (event, element) {

            /*console.log("----------render-------");
             console.log(event);
             console.log(element);*/

            element.append("<span class='closeon' id='closeon"+event.assignmentId+"'>[ X ]</span>");

            element.find("#closeon"+event.assignmentId).click(function () {

                swal({
                        title: "Er du sikker?",
                        text: "Du kan ikke angre denne handlingen",
                        type: "warning",
                        showCancelButton: true,
                        cancelButtonText: "AVBRYT",
                        cancelButtonColor: "#9e9e9e",
                        confirmButtonColor: "#0d47a1",
                        confirmButtonText: "SLETT",
                        closeOnConfirm: false
                    },

                    function () {

                        var events = $('#calendar').fullCalendar("clientEvents");

                        for(var i = 0; i< events.length; i++){

                            const event1 = events[i];

                            if(event1.assignmentId == event.assignmentId){

                                console.log(event1);

                                swal({
                                    title: "Slettet",
                                    text: "Tilgjengeligheten ble slettet",
                                    type: "success",
                                    confirmButtonText: "OK",
                                    confirmButtonColor: "#0d47a1",
                                });

                                /*swal("Slettet!", "Tilgjengeligheten ble slettet.", "success");*/

                                $('#calendar').fullCalendar('removeEvents', event1.id);

                                deleteShiftAssignment(event.assignmentId);

                            }

                        }

                        //location.reload();
                    });


            });
        },

        dayRender: function (date, cell) {

            date = date.toDate().toISOString().split("T")[0];

            getAllShifts(function (shifts) {

                var changed = false;

                for (var i = 0; i < shifts.length; i++) {

                    var shift = shifts[i];

                    if (shift.fromTime.split("T")[0] == date) {

                        //cell.css("background-color", "#4caf50");
                        changed = true;

                    }
                    //console.log(shift);

                }
                if (!changed) {
                    cell.css("background-color", "#e0e0e0"); // GREY
                }
            });

            getCurrentUser(function (currentUser) {

                var user_id = currentUser.employeeId;

                getShiftAssignmentsForUser(user_id, function (shiftAssignments) {

                    for (var i = 0; i < shiftAssignments.length; i++) {

                        const assignment = shiftAssignments[i];

                        getShiftWithId(assignment.shiftId, function (shift) {

                            if (shift.fromTime.split("T")[0] == date /*&& assignment.available*/ && !assignment.assigned) {

                                console.log(shift);
                                console.log(assignment);

                                const title = shift.fromTime.split("T")[1].substr(0, 5) + "-"+shift.toTime.split("T")[1].substr(0, 5);

                                $('#calendar').fullCalendar("renderEvent", {

                                    title: title+ (!assignment.available ? " U" : ""),
                                    id: assignment.id/*shift.fromTime.split("T")[1].substr(0, 5) == "06:00" ? 1 : shift.fromTime.split("T")[1].substr(0, 5) == "14:00" ? 2 : 3*/,
                                    start: shift.fromTime.split("T")[0],
                                    end: shift.toTime.split("T")[0],
                                    startTime: shift.fromTime.split("T")[1].substr(0, 5),
                                    endTime: shift.toTime.split("T")[1].substr(0, 5),
                                    doNotSave: true,
                                    assignmentId: assignment.id,
                                    backgroundColor: !assignment.available ? "#f44336" : /*responsible ? "#03a9f4" : */"#4caf50",
                                    stick: true // maintain when user navigates (see docs on the renderEvent method)
                                }, true)
                            }

                        })

                    }


                    //console.log("Amount of shifts: "+shifts.length);



                })
            })
        },

        drop: function (date, jsEvent, ui, resourceId) {
            console.log('drop', date.format(), resourceId);

            // is the "remove after drop" checkbox checked?
            if ($('#drop-remove').is(':checked')) {
                // if so, remove the element from the "Draggable Events" list
                $(this).remove();
            }
        },
        eventReceive: function (event) { // called when a proper external event is dropped
            console.log('eventReceive', event);
        },
        eventDrop: function (event) { // called when an event (already on the calendar) is moved
            console.log('eventDrop', event);
        },
        eventClick: function (event) {

            $('#calendar').fullCalendar('removeEvent', event.id);

            console.log(event);
            //$('#calendar').fullCalendar("removeEvent", event);
        }
    });

});

/*getAllUsers(function (users) {

 for (var i = 0; i < users.length; i++) {

 const user = users[i];

 //$('#calendar').fullCalendar('addResource', resource)

 getAssignedShiftsForUser(user.employeeId, funct ion (shiftsForUser) {

 for (var i = 0; i < shiftsForUser.length; i++) {

 const shift = shiftsForUser[i];

 //console.log(shift);

 userIsResponsibleForShift(shift.shiftId, user.employeeId, function (responsible) {

 const event = {

 id: shift.shiftId,
 resourceId: user.employeeId,
 start: shift.fromTime.split("T")[0],
 end: shift.toTime.split("T")[0],
 title: shift.fromTime.split("T")[1].substr(0, 5) + " - " + shift.toTime.split("T")[1].substr(0, 5),

 backgroundColor: responsible ? "#03a9f4" : "#4caf50",

 stick: true

 }

 $('#calendar').fullCalendar('renderEvent', event, true);
 })
 }
 })
 }
 });*/

$("#save").click(function () {

    console.log("---------------------------------------------SAVING---------------------------------------------");

    swal({
        title: "Tilgjengelighet registrert!",
        text: "Din tilgjengelighet er registrert.",
        type: "success",
        confirmButtonText: "OK",
        confirmButtonColor: "#0d47a1"
    });

    var events = $('#calendar').fullCalendar('clientEvents');

    getAllShifts(function (shifts) {

        for (var i = 0; i < events.length; i++) {

            const event = events[i];

            for (var j = 0; j < shifts.length; j++) {

                const shift = shifts[j];

                //console.log(event)
                //console.log(shift)

                const sameTime = event.startTime == shift.fromTime.split("T")[1].substr(0, 5) && event.endTime == shift.toTime.split("T")[1].substr(0, 5);

                const event_date = event.start.toISOString();
                const shift_date = shift.fromTime.split("T")[0];

                const sameDate = event_date == shift_date;
                //console.log();

                // Samme tid, samme dag

                //console.log("eventstart: "+event.start+" - type: "+shift_event_id+" - eventdate: "+event_date+" - shiftdate: "+shift_date)

                if (sameTime && sameDate && !event.doNotSave) {

                    getCurrentUser(function (currentUser) {

                        var user_id = currentUser.employeeId;
                        var shift_id = shift.shiftId;

                        console.log("***ADDING SHIFT TO USER***");
                        console.log(user_id+" - "+shift_id);

                        if (!event.available) {

                            addUserToShift(user_id, shift_id, false, false, function (data) {

                                console.log(data);
                                //location.reload();

                            })
                        }
                        else {

                            addUserToShift(user_id, shift_id, true, false, function (data) {

                                console.log(data);
                                //location.reload();
                            })
                        }
                    })

                }
            }
        }
        location.reload();
    })
});

function getUsersAndCreateResourceList(callback) {

    const res = [];

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            res.push({
                id: user.employeeId,
                title: user.firstName + " " + user.lastName,
            })
        }
        callback(res);
    })
}