$(document).ready(function () { // document ready
    $(function() { // document ready


        /* initialize the external events
         -----------------------------------------------------------------*/

        $('#external-events .fc-event').each(function() {

            // store data so the calendar knows to render an event upon drop
            $(this).data('event', {
                title: $.trim($(this).text()), // use the element's text as the event title
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
                left: 'prev, promptResource today',
                center: 'title',
                right: 'customWeek, agendaWeek, next'
            },
            firstDay: 1,
            defaultView: 'month',
            views: {
                customWeek: {
                    type: 'timeline',
                    duration: {weeks: 1},
                    slotDuration: {days: 1},
                    buttonText: 'Vaktliste'
                }
            },
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
            }
        });

    });

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            //$('#calendar').fullCalendar('addResource', resource)

            getAssignedShiftsForUser(user.employeeId, function (shiftsForUser) {

                for (var i = 0; i < shiftsForUser.length; i++) {

                    const shift = shiftsForUser[i];

                    console.log(shift);

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
    });

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