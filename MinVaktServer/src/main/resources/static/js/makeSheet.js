$(document).ready(function () { // document ready
    var calendar = $('#calendar');

    /* initialize the external events
     -----------------------------------------------------------------*/

    $('#external-events .fc-event').each(function () {

        // store data so the calendar knows to render an event upon drop

        var title = $.trim($(this).text());
        const theTitle  = title;

        title = title.replace("A: ","");


        $(this).data('event', {
            title: theTitle, // use the element's text as the event title
            responsible: this.id.includes("ANSVAR"),
            startTime: title.split("-")[0],
            endTime: title.split("-")[1],
            //start_id: $.trim($(this).text()) == "Formiddagsvakt" ? 1 : $.trim($(this).text()) == "Ettermiddagsvakt" ? 2 : 3,
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
            var tooltip = '<div class="tooltipevent" style="width:180px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + calEvent.textPrint + '</div>';
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


            swal({  title: "Er du sikker på at du vil slette vakten?",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Slett",
                    cancelButtonText: "Avbryt",
                    closeOnConfirm: false,
                    closeOnCancel: false },
                function(isConfirm){
                    if (isConfirm) {
                        swal("Slettet", "Vakten din har blitt slettet", "success");

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
                        swal("Avbrutt", "Vakten din ble ikke slettet", "error");   }
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
                getAmountOnShift(shift.shiftId, function(missingList) {
                     let text = "";
                     for (var i = 0; i < missingList.length; i++) {
                        if(missingList[i].categoryId == 2) {
                           text +="Sykepleiere: " + missingList[i].countAssigned +"/"+ missingList[i].countRequired;
                        } else if(missingList[i].categoryId==3) {
                            text +="\nHelsefagarbeider: " + missingList[i].countAssigned +"/"+ missingList[i].countRequired;
                        }
                     }

                    const event = {

                        id: shiftAssignment.id,
                        resourceId: shiftAssignment.employeeId,
                        start: shift.fromTime.split("T")[0],
                        end: shift.toTime.split("T")[0],
                        startTime: shift.fromTime.split("T")[1].substr(0, 5),
                        endTime: shift.toTime.split("T")[1].substr(0, 5),
                        title: shift.fromTime.split("T")[1].substr(0, 2) + " - " + shift.toTime.split("T")[1].substr(0, 2) +
                            (responsible ? " A"
                            : shiftAssignment.assigned ? " V"
                            : !shiftAssignment.available ? " U"
                            : " T"),
                        textPrint: text,
                        backgroundColor: responsible ? "#00bcd4"
                            : shiftAssignment.assigned ? "#2196f3"
                            : !shiftAssignment.available ? "#f44336"
                            : "#4caf50",

                        stick: true,

                    }
                    $('#calendar').fullCalendar('renderEvent', event, true);

                })


            })
        }
    })
});

$("#save").click(function () {

    console.log("---------------------------------------------SAVING---------------------------------------------");

    swal("Den nye timelisten ble lagret", "", "success")

    var events = $('#calendar').fullCalendar('clientEvents');

    // FIX Sykt dårlig optimized / dårlig lagd
    getAllShifts(function (shifts) { // Alle shifts

        for (var i = 0; i < events.length; i++) {

            const event = events[i];

            for (var j = 0; j < shifts.length; j++) {

                const shift = shifts[j];


                //console.log(shift) // from DB

                const sameTime = event.startTime == shift.fromTime.split("T")[1].substr(0, 5) && event.endTime == shift.toTime.split("T")[1].substr(0, 5);

                const event_date = event.start.toISOString()
                const shift_date = shift.fromTime.split("T")[0];

                const sameDate = event_date == shift_date;
                //console.log();

                // Samme tid, samme dag

                if (event.save && event_date == shift_date) {

                    console.log(event)
                    console.log(shift);

                    console.log(sameTime);

                }
                /*console.log(event.start._d);
                 console.log(event.start._d.getDate());
                 console.log(event.start.toISOString());*/


                //console.log("Sametime:"  +sameTime+ "eventstart: " + event.start_id + " - type: " + shift_event_id + " - eventdate: " + event_date + " - shiftdate: " + shift_date)

                if (sameTime && sameDate && event.save) {

                    console.log("-----SAVING THIS EVENT-----")
                    const user_id = event.resourceId;
                    const shift_id = shift.shiftId;

                    console.log(user_id +" - "+shift_id);


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
    }})
})

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
