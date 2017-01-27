$(document).ready(function () { // document ready
    var calendar = $('#calendar');
    calendar.fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        displayEventTime: false,
        locale: "nb",
        timezone: "UTC",
        selectable: true,
        resourceAreaWidth: 230,
        //editable: true,
        aspectRatio: 1.5,
        scrollTime: '00:00',
        header: {
            left: 'prev, promptResource today',
            center: 'title',
            right: 'next'
        },
        firstDay: 1,
        weekNumbers:true,
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

    getAllShiftAssignments(function (shiftAssignments) {

        for (var i = 0; i < shiftAssignments.length; i++) {

            const shiftAssignment = shiftAssignments[i];

            getShiftWithId(shiftAssignment.shiftId, function (shift) {

                const responsible = shift.responsibleEmployeeId == shiftAssignment.employeeId;

                console.log(shift)



                const event = {

                    id: shiftAssignment.shiftId,
                    resourceId: shiftAssignment.employeeId,
                    start: shift.fromTime.split("T")[0],
                    end: shift.toTime.split("T")[0],
                    title: shift.fromTime.split("T")[1].substr(0, 2) + " - " + shift.toTime.split("T")[1].substr(0, 2) + (responsible ? " A":" V" ),

                    backgroundColor: responsible ? "#00bcd4" : "#2196f3",

                    stick: true
                }

                console.log(event);

                $('#calendar').fullCalendar('renderEvent', event, true);

            })
        }
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

