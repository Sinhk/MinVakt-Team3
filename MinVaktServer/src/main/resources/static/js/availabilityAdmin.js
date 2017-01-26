/**
 * Created by Stine on 26.01.17.
 */
$(document).ready(function () { // document ready
    var calendar = $('#calendar');

    calendar.fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        displayEventTime: false,
        droppable: true,
        locale: "nb",
        timezone: "UTC",
        selectable: true,
        editable: false,
        resourceAreaWidth: 230,

        aspectRatio: 1.5,
        scrollTime: '00:00',
        header: {
            left: 'prev, promptResource today',
            center: 'title',
            right: 'next'
        },
        firstDay: 1,
        weekNumbers: true,
        defaultView: 'customWeek',
        views: {
            customWeek: {
                dayNamesShort: ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'],
                type: 'timeline',
                duration: {weeks: 1},
                slotDuration: {days: 1},
                buttonText: 'Vaktliste'
            }
        },

        columnFormat: {
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


        resources: function (callback) {
            getUsersAndCreateResourceList(function (data) {
                callback(data);
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

        eventMouseover: function (calEvent, jsEvent) {
            var tooltip = '<div class="tooltipevent" style="width:180px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + ' Sykepleiere: <br> Helsefagarbeidere: ' + '</div>';
            var $tool = $(tooltip).appendTo('body');
            $(this).mouseover(function (e) {
                $(this).css('z-index', 10000);
                $tool.fadeIn('500');
                $tool.fadeTo('10', 1.9);
            }).mousemove(function (e) {
                $tool.css('top', e.pageY + 10);
                $tool.css('left', e.pageX + 20);
            });
        },
        eventMouseout: function (calEvent, jsEvent) {
            $(this).css('z-index', 8);
            $('.tooltipevent').remove();
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

            getShiftAssignmentsForUser(user.employeeId, function (shiftAssignments) {


                for (var i = 0; i < shiftAssignments.length; i++) {

                    const shiftAssignment = shiftAssignments[i];

                    getShiftWithId(shiftAssignment.shiftId, function (shift) {


                        console.log(shift);
                        console.log(shiftAssignment);


                        userIsResponsibleForShift(shift.shiftId, user.employeeId, function (responsible) {

                            const event = {

                                id: shift.shiftId,
                                resourceId: user.employeeId,
                                start: shift.fromTime.split("T")[0],
                                end: shift.toTime.split("T")[0],
                                title: shift.fromTime.split("T")[1].substr(0, 5) + " - " + shift.toTime.split("T")[1].substr(0, 5),

                                backgroundColor: shiftAssignment.assigned ? "#2196f3"
                                    : !shiftAssignment.available ? "#f44336"
                                    : responsible ? "#00bcd4" : "#4caf50",

                                stick: true

                            }

                            $('#calendar').fullCalendar('renderEvent', event, true);
                        })
                    })
                }
            })
        }
    });
});
