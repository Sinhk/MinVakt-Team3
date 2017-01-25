$(document).ready(function () { // document ready
    $(function() { // document ready


        /* initialize the external events
         -----------------------------------------------------------------*/

        $('#external-events .fc-event').each(function() {

            // store data so the calendar knows to render an event upon drop
            $(this).data('event', {
                title: $.trim($(this).text()), // use the element's text as the event title
                id: $.trim($(this).text()) == "Formiddagsvakt" ? 1 : $.trim($(this).text()) == "Ettermiddagsvakt" ? 2 : 3,
                stick: true // maintain when user navigates (see docs on the renderEvent method)
            });

            // make the event draggable using jQuery UI
            $(this).draggable({
                zIndex: 999,
                revert: true,      // will cause the event to go back to its
                revertDuration: 0  //  original position after the drag
            });

        });

        $('#unavailable-events .fc-event-u').each(function() {

            // store data so the calendar knows to render an event upon drop
            $(this).data('event', {
                title: $.trim($(this).text()), // use the element's text as the event title
                id: $.trim($(this).text()) == "Formiddagsvakt" ? 1 : $.trim($(this).text()) == "Ettermiddagsvakt" ? 2 : 3,
                stick: true // maintain when user navigates (see docs on the renderEvent method)
            });

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
            //editable: true,
            aspectRatio: 1.5,
            scrollTime: '00:00',
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            weekNumbers:true,
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
            editable: true, // enable draggable events
            droppable: true, // this allows things to be dropped onto the calendar

            eventRender: function(event, element) {
                element.append( "<span class='closeon'>[ X ]</span>" );

                element.find(".closeon").click(function() {

                    swal({
                            title: "Are you sure?",
                            text: "You will not be able to recover this imaginary file!",
                            type: "warning",
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "Yes, delete it!",
                            closeOnConfirm: false
                        },

                        function(){
                            $('#calendar').fullCalendar('removeEvents',event._id);
                            swal("Deleted!", "Your imaginary file has been deleted.", "success");
                        });



                });
            },

            dayRender: function (date, cell) {

                date = date.toDate().toISOString().split("T")[0];

                getAllShifts(function (shifts) {

                    var changed = false;

                    for(var i = 0; i<shifts.length; i++){

                        var shift = shifts[i];

                        if(shift.fromTime.split("T")[0] == date){

                            cell.css("background-color", "#4caf50"); // GREEN
                            changed = true;

                        }

                        //console.log(shift);

                    }
                    if(!changed){
                        cell.css("background-color", "#ef5350"); // RED
                    }

                })

                getCurrentUser(function (currentUser) {

                    var user_id = currentUser.employeeId;

                    getShiftsForUser(user_id, function (shifts) {

                        //console.log("Amount of shifts: "+shifts.length);

                        for(var i = 0; i<shifts.length; i++){

                            const shift = shifts[i];

                            getShiftAssignmentForShiftAndUser(shift.shiftId, user_id, function (assignment) {

                                console.log(assignment);

                                if(shift.fromTime.split("T")[0] == date && assignment.available && !assignment.assigned) {

                                    $('#calendar').fullCalendar("renderEvent",{

                                            title: shift.fromTime.split("T")[1].substr(0,5) == "06:00" ? "Formiddagsvakt" : shift.fromTime.split("T")[1].substr(0,5) == "14:00" ? "Ettermiddagsvakt" : "Nattvakt",  // use the element's text as the event title
                                            id: shift.fromTime.split("T")[1].substr(0,5) == "06:00" ? 1 : shift.fromTime.split("T")[1].substr(0,5) == "14:00" ? 2 : 3,
                                            start: shift.fromTime.split("T")[0],
                                            end: shift.toTime.split("T")[0],
                                            doNotSave: true,
                                            stick: true // maintain when user navigates (see docs on the renderEvent method)
                                        }, true)
                                }
                            })
                        }
                    })
                })
            },

            drop: function(date, jsEvent, ui, resourceId) {
                console.log('drop', date.format(), resourceId);

                // is the "remove after drop" checkbox checked?
                if ($('#drop-remove').is(':checked')) {
                    // if so, remove the element from the "Draggable Events" list
                    $(this).remove();
                }
            },
            eventReceive: function(event) { // called when a proper external event is dropped
                console.log('eventReceive', event);
            },
            eventDrop: function(event) { // called when an event (already on the calendar) is moved
                console.log('eventDrop', event);
            },
            eventClick: function (event) {

                console.log(event);

            }
        });

    });

    /*getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            //$('#calendar').fullCalendar('addResource', resource)

            getAssignedShiftsForUser(user.employeeId, function (shiftsForUser) {

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

    $("#saveButton").click(function () {

        console.log("---------------------------------------------SAVING---------------------------------------------");

        // TODO Syk swal greie her

        var events = $('#calendar').fullCalendar('clientEvents');

        getAllShifts(function (shifts) {

            for(var i = 0; i < events.length; i++){

                const event = events[i];

                for(var j = 0; j < shifts.length; j++) {

                    const shift = shifts[j];

                    //console.log(event)
                    //console.log(shift)

                    const shift_event_id = shift.fromTime.split("T")[1].substr(0,5) == "06:00" ? 1 : shift.fromTime.split("T")[1].substr(0,5) == "14:00" ? 2 : 3;

                    const event_date = event.start.toISOString()
                    const shift_date = shift.fromTime.split("T")[0];
                    //console.log();

                    // Samme tid, samme dag
                    if(event.id == shift_event_id && event.start && event_date == shift_date && !event.doNotSave){
                        console.log(event)
                        console.log(shift)


                        getCurrentUser(function (currentUser) {

                            var user_id = currentUser.employeeId;
                            var shift_id = shift.shiftId;

                            addUserToShift(user_id, shift_id, function (data) {

                                console.log(data);

                            })

                        })

                    }

                }



            }

        })


    })

    /*getAllAssignedShifts(function (assignedShifts) {

     for (var i = 0; i < assignedShifts.length; i++) {

     const nonAssignedShift = assignedShifts[i];

     console.log(nonAssignedShift);

     getUserById(nonAssignedShift.employeeId, function (user) {

     getShiftWithId(nonAssignedShift.shiftId, function (shift) {

     console.log(user);
     console.log(shift);

     var resource = {
     id: user.employeeId,
     title: user.firstName + " " + user.lastName,
     }

     $('#calendar').fullCalendar('addResource', resource)


     var event = {

     id: shift.shiftId,
     resourceId: user.employeeId,
     start: shift.fromTime.split("T")[0],
     end: shift.toTime.split("T")[0],
     title: shift.fromTime.split("T")[1].substr(0, 5) + " - " + shift.toTime.split("T")[1].substr(0, 5),

     stick: true

     }

     $('#calendar').fullCalendar('renderEvent', event, true);




     })
     })
     }
     })*/
    /*getAllShifts(function (events) {
     calendar.fullCalendar('addEventSource', listToFullCalendarEventList(events, calendar.fullCalendar('getResources')));
     });*/

    // legg til nåværende tilgjengelighet

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