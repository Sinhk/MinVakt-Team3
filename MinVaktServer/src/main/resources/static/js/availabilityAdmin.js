$(document).ready(function () { // document ready
    var calendar = $('#calendar');

    /* initialize the external events
     -----------------------------------------------------------------*/

    $('#external-events .fc-event').each(function () {

        // store data so the calendar knows to render an event upon drop
        $(this).data('event', {
            title: $.trim($(this).text()), // use the element's text as the event title
            responsible: this.id.includes("ANSVAR"),
            start_id: $.trim($(this).text()) == "Formiddagsvakt" ? 1 : $.trim($(this).text()) == "Ettermiddagsvakt" ? 2 : 3,
            save: true,
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

        /*
         defaultView: 'agendaWeek',
         views: {
         type: 'costumeWeek',
         }
         }*/
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
        /*
         eventRender: function(event, element) {
         element.append( "<span class='closeon'>[ X ]</span>" );

         element.find(".closeon").click(function() {

         /*swal({
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
         },*/

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

            });


            swal({  title: "Er du sikker på at du vil slette vakten?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "SLETT",
                    cancelButtonText: "AVBRYT",
                    closeOnConfirm: false,
                    closeOnCancel: false },
                function(isConfirm){
                    if (isConfirm) {
                        swal({
                            title: "Slettet",
                            text: "Vakten din har blitt slettet",
                            type: "success",
                            confirmButtonText: "OK",
                            confirmButtonColor: "#0d47a1"
                        });

                        $('#calendar').fullCalendar('removeEvents', event._id);

                        var shiftAssignmentId = event.id;

                        getShiftAssignmentByShiftAssignmentId(shiftAssignmentId, function (shiftAssignment) {

                            var user_id = shiftAssignment.employeeId;
                            var shift_id = shiftAssignment.shiftId;

                            console.log(shiftAssignment);

                            changeUserAssignment(user_id, shift_id, true, false, false, false, "", function (data) {

                                console.log(data);
                                console.log("user: " + user_id + " - shift: " + shift_id+" RESPONSIBLE")

                            })

                        })
                    }
                    else {
                        swal ({
                            title: "Avbrutt",
                            text: "Vakten din ble ikke slettet",
                            type: "error",
                            confirmButtonText: "OK",
                            confirmButtonColor: "#0d47a1"
                        });
                        /*swal("Avbrutt", "Vakten din ble ikke slettet", "error"); */
                    }
                });




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

                var responsible = shift.responsibleEmployeeId == shiftAssignment.employeeId;

                const event = {

                    id: shiftAssignment.id,
                    resourceId: shiftAssignment.employeeId,
                    start: shift.fromTime.split("T")[0],
                    end: shift.toTime.split("T")[0],
                    title: shift.fromTime.split("T")[1].substr(0, 2) + " - " + shift.toTime.split("T")[1].substr(0, 2) +
                    (responsible ? " A"
                        : shiftAssignment.assigned ? " V"
                        : !shiftAssignment.available ? " U"
                        : " T"),

                    backgroundColor: responsible ? "#00bcd4"
                        : shiftAssignment.assigned ? "#2196f3"
                        : !shiftAssignment.available ? "#f44336"
                        : "#4caf50",

                    stick: true,

                };
                $('#calendar').fullCalendar('renderEvent', event, true);

            })




        }

    });

    /*function renderEvents() {

     console.log("RENDERING EVENTS")
     calendar.fullCalendar({
     eventRender: function (event, element) {

     console.log(event);
     console.log(element);

     element.append("<span class='closeon'>[ X ]</span>");

     element.find(".closeon").click(function () {

     swal({
     title: "Er du sikker?",
     text: "Du kan ikke angre denne handlingen.",
     type: "warning",
     showCancelButton: true,
     confirmButtonColor: "#DD6B55",
     confirmButtonText: "Ja, slett den!",
     closeOnConfirm: false
     },

     function () {
     $('#calendar').fullCalendar('removeEvents', event._id);
     swal("Slettet!", "Tilgjengeligheten ble slettet.", "success");


     });
     });
     },
     });

     }*/

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

/*
$("#save").click(function () {

    console.log("---------------------------------------------SAVING---------------------------------------------");

    swal("Den nye timelisten ble lagret", "", "success")

    var events = $('#calendar').fullCalendar('clientEvents');

    getAllShifts(function (shifts) {

        for (var i = 0; i < events.length; i++) {

            const event = events[i];

            for (var j = 0; j < shifts.length; j++) {

                const shift = shifts[j];

                //console.log(event)
                //console.log(shift)

                const shift_event_id = shift.fromTime.split("T")[1].substr(0, 5) == "06:00" ? 1 : shift.fromTime.split("T")[1].substr(0, 5) == "14:00" ? 2 : 3;

                const event_date = event.start.toISOString()
                const shift_date = shift.fromTime.split("T")[0];
                //console.log();

                // Samme tid, samme dag

                console.log("eventstart: " + event.start_id + " - type: " + shift_event_id + " - eventdate: " + event_date + " - shiftdate: " + shift_date)

                if (event.start_id == shift_event_id && event.start && event_date == shift_date && event.save) {


                    const user_id = event.resourceId;
                    const shift_id = shift.shiftId;


                    if (event.responsible) {

                        changeUserAssignment(user_id, shift_id, true, true, true, false, "", function (data) {

                            console.log(data);
                            console.log("user: " + user_id + " - shift: " + shift_id + " RESPONSIBLE")

                        })
                    }
                    else {

                        changeUserAssignment(user_id, shift_id, true, false, true, false, "", function (data) {

                            console.log(data);
                            console.log("user: " + user_id + " - shift: " + shift_id + "IKKE RESPONSIBLE")
                        })
                    }
                    //location.reload();

                }
            }
        }
    })
})*/

function getUsersAndCreateResourceList(callback) {

    const res = [];

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            // Hack fordi sync ikke funka
            res.push({
                id: user.employeeId,
                title: user.firstName + " " + user.lastName + ", " + (user.categoryId == 1 ? "Administrasjon" : user.categoryId == 2 ? "Sykepleier" : user.categoryId == 3 ? "Helsefagarbeider" : "Assistent"),

            })
        }
        callback(res);


    })

}
