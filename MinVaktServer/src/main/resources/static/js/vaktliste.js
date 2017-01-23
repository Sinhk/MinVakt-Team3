$(document).ready(function () { // document ready
    var calendar = $('#calendar');
    calendar.fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        displayEventTime: false,
        locale: "no",
        timezone: "UTC",
        selectable: true,
        resourceAreaWidth: 230,
        //editable: true,
        aspectRatio: 1.5,
        scrollTime: '00:00',
        header: {
            left: 'prev, promptResource today',
            center: 'title',
            right: 'customWeek, next'
        },
        firstDay: 1,
        defaultView: 'customWeek',
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
        resourceLabelText: 'Ansatte',
        resources: function (callback) {
            $.getJSON("/users/resource", function (res) {
                callback(res)
            });
        },

        eventClick: function (event, jsEvent, view) {

            var eventId = event.id;

            var eventDB = getEventViaID(eventId);

            console.log(eventDB);
            console.log(event);

        },

        select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format() +
                "\nEnd date: " + moment(end).format());
        }
    });

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];
            console.log(user)
            var resource = {
                id: user.employeeId,
                title: user.firstName + " " + user.lastName,
            }

            //$('#calendar').fullCalendar('addResource', resource)

            getShiftsForUser(user.employeeId, function (shiftsForUser) {


                for (var i = 0; i < shiftsForUser.length; i++) {

                    const shift = shiftsForUser[i];

                    console.log(shift);

                    const event = {

                        id: shift.shiftId,
                        resourceId: user.employeeId,
                        start: shift.fromTime.split("T")[0],
                        end: shift.toTime.split("T")[0],
                        title: shift.fromTime.split("T")[1].substr(0, 5) + " - " + shift.toTime.split("T")[1].substr(0, 5),

                        stick: true

                    }

                    $('#calendar').fullCalendar('renderEvent', event, true);
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
});

