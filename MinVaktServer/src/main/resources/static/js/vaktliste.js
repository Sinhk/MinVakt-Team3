$(document).ready(function () { // document ready
    var calendar = $('#calendar');
    calendar.fullCalendar({
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
        firstDay: 1,
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


