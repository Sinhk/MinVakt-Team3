$(document).ready(function() { // document ready

    getAllEmployees(function (employees) {

        /*var events = getShiftsByEmployee(employees);
        var resourceList = userListToResourceList(employees);*/

/*
        $('#calendar').fullCalendar({
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
                    duration: { weeks: 1 },
                    slotDuration: {days: 1},
                    buttonText: 'Vaktliste'
                }
            },
            dayNamesShort: ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'],
            monthNames: ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'],
            weekNumberTitle: 'Uke',
            buttonText: {
                today:    'I dag',
                month:    'Måned',
                week:     'Uke',
                day:      'Dag',
                list:     'Liste'
            },
            resourceLabelText: 'Ansatte',
            resources: resourceList,

            events: listToFullCalendarEventList(events, resourceList),
    $.getJSON("/shifts/between", function (shifts) {
        var ev = toFullCalendarEventsWithResource(shifts);
        $('#calendar').fullCalendar('addEventSource', ev);
    });
*/
    getAllShifts(function (events) {

        $('#calendar').fullCalendar({
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
                    duration: { weeks: 1 },
                    slotDuration: {days: 1},
                    buttonText: 'Vaktliste'
                }
            },
            dayNamesShort: ['Søndag', 'Mandag', 'Tirsdag', 'Onsdag', 'Torsdag', 'Fredag', 'Lørdag'],
            monthNames: ['Januar', 'Februar', 'Mars', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Desember'],
            weekNumberTitle: 'Uke',
            buttonText: {
                today:    'I dag',
                month:    'Måned',
                week:     'Uke',
                day:      'Dag',
                list:     'Liste'
            },
            resourceLabelText: 'Ansatte',
            resources: function (callback) {
                $.getJSON("/users/resource", function (res) {
                    callback(res)
                });
            },
            events: listToFullCalendarEventList(events,$('#calendar').fullCalendar().resources),


            eventClick: function( event, jsEvent, view ) {

                var eventId = event.id;

                var eventDB = getEventViaID(eventId);

                console.log(eventDB);

            },

            select: function (start, end, jsEvent, view) {

                console.log("Start date: " + moment(start).format()+
                    "\nEnd date: " + moment(end).format());

            }

        });

    });

    })





    //console.log(events);


    //console.log("Responsible users: "+getResponsibleUsersForShifts(events));




});
