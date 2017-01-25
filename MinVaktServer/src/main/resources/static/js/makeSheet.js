$(document).ready(function () { // document ready
    var calendar = $('#calendar');

    /* initialize the external events
     -----------------------------------------------------------------*/

    $('#external-events .fc-event').each(function() {

        // store data so the calendar knows to render an event upon drop
        $(this).data('event', {
            title: $.trim($(this).text()), // use the element's text as the event title
            responsible: this.id.includes("ANSVAR"),
            stick: true // maintain when user navigates (see docs on the renderEvent method)
        });

        // make the event draggable using jQuery UI
        $(this).draggable({
            zIndex: 999,
            revert: true,      // will cause the event to go back to its
            revertDuration: 0  //  original position after the drag
        });

    });

    calendar.fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        displayEventTime: false,
        droppable: true,
        locale: "no",
        timezone: "UTC+1",
        selectable: true,
        resourceAreaWidth: 230,
        editable: false,
        aspectRatio: 1.5,
        scrollTime: '00:00',
        header: {
            left: 'prev, promptResource today',
            center: 'title',
            right: 'next'
        },
        firstDay:1,
        defaultView: 'customWeek',
        views: {
            customWeek: {
                type: 'timeline',
                duration: {weeks: 1},
                slotDuration: {days: 1},
                buttonText: 'Vaktliste'
            }
        },

        /*
        defaultView: 'agendaWeek',
        views: {
            type: 'costumeWeek',
        }
    }*/
        columnFormat:{
            month: 'ddd',
            week: 'ddd d/M',
            day: 'dddd d/M'
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

        resourceLabelText: 'Ansatte',

        resources: function(callback){
            getUsersAndCreateResourceList(function (data) {
                callback(data);
            })
        },
        /*
        resourceLabelText: 'Ansatte',
        resources: function(callback){
            getUsersAndCreateResourceList(function (data) {
                callback(data);
            })
        },

        resourceLabelText: 'Stilling',
        resources: function(callback){

        },*/

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

        eventClick: function (event, jsEvent, view) {

            var eventId = event.id;

            getShiftWithId(eventId, function (shift) {

                console.log(shift);
                console.log(event);

            })

        },

        select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format() +
                "\nEnd date: " + moment(end).format());
        }
    });

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            //$('#calendar').fullCalendar('addResource', resource)


            getShiftsForUser(user.employeeId, function (shiftsForUser) {

                for (var i = 0; i < shiftsForUser.length; i++) {

                    const shift = shiftsForUser[i];

                    console.log(shift);

                    getShiftAssignmentForShiftAndUser(shift.shiftId, user.employeeId, function (shiftAssignment) {

                        userIsResponsibleForShift(shift.shiftId, user.employeeId, function (responsible) {

                            const event = {

                                id: shift.shiftId,
                                resourceId: user.employeeId,
                                start: shift.fromTime.split("T")[0],
                                end: shift.toTime.split("T")[0],
                                title: shift.fromTime.split("T")[1].substr(0, 5) + " - " + shift.toTime.split("T")[1].substr(0, 5),

                                backgroundColor: !shiftAssignment.available ? "#f44336" : responsible ? "#03a9f4" : "#4caf50",

                                stick: true

                            }

                            $('#calendar').fullCalendar('renderEvent', event, true);
                        })
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

/*function getUsersAndCreateResourceList(callback) {

    const res =[];

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            getCategory(user.email, function (category) {

                const userobj = {
                    id: user.employeeId,
                    title: user.firstName + " " + user.lastName + ", "+category,
                    employee: user.firstName + " " + user.lastName,
                    position: category.categoryName

                };
                res.push(userobj)
            })
        }
        callback(res);
    })

}*/

function getUsersAndCreateResourceList(callback) {

    const res = [];

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

                // Hack fordi sync ikke funka
                res.push({
                    id: user.employeeId,
                    title: user.firstName + " " + user.lastName+", "+(user.categoryId == 1 ? "Administrasjon" : user.categoryId == 2 ? "Sykepleier" : user.categoryId == 3 ? "Helsefagarbeider" : "Assistent"),

                })


        }
        callback(res);


    })

}
