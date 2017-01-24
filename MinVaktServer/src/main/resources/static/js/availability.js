$(document).ready(function () {

    $('.modal-trigger').modal();
    // $(".side-nav").css("margin-top", $(".nav-wrapper").height());

    $('#calendar').fullCalendar({

        displayEventTime: false,
        locale: "no",
        timezone: "UTC",
        selectable: true,
        header: {
            left: 'prev, today',
            center: 'title',
            right: 'next'
        },
        firstDay: 1,
        weekNumbers: true,
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
        resources:[
            {
                id: 1,
                title: "Ingen Status", // normal farge
            },
            {
                id: 2,
                title: "Tilgjengelig",
                bakgroundColor: "#4caf50" //grønn

            },
            {
                id: 3,
                title: "Utilgjengelig",
                bakgroundColor: "#ff5722" // orangeish
            }
        ],
        select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format() +
                "\nEnd date: " + moment(end).format());

        },
        /*dayClick:function (data ) {
         console.log("You clicked: "+data);
         },*/

        eventMouseover: function (calEvent, jsEvent) {
            var tooltip = '<div class="tooltipevent" style="width:150px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + ' Tidspunkt: ' + calEvent.title + '<br> Avdeling: ' + calEvent.avdeling + '<br>  Ansvar: ' + calEvent.isResponsible + '</div>';
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


        eventClick: function (event, jsEvent) {
            var eventId = event.id;

            console.log($('#calendar').fullCalendar('clientEvents'));

            getShiftWithId(eventId, function (data) {

                console.log(data);

                //TODO NÅR MAN SKAL SETTE SEG TILGJENGELIG, BRUK DETTE
                getCurrentUser(function (user) {
                    addUserToShift(user.employeeId, data.shiftId, function (data) {

                        console.log(data);

                    })
                })

            });

        },
        /*eventMouseout: function(calEvent,jsEvent) {
         $("#tooltip").remove();
         }

         var eventId = event.id;

         var eventDB = getEventViaID(eventId);

         console.log(eventDB);

         }*/
    });

    getAllShifts(function (shifts) {

        //  console.log(shifts);

        for (var i = 0; i<shifts.length; i++){

            const shift = shifts[i];

            getCurrentUser(function (user) {
                getShiftAssignmentForShiftAndUser(shift.shiftId, user.employeeId, function (shiftAssignment) {

                    var fullCalendarEvent = {

                        id: event.shiftId,
                        title: start.split("T")[1].substr(0,3) + " -> " + end.split("T")[1].substr(0,3),
                        start: dateStart,
                        end: dateEnd,
                        resourceId: resource.id,
                        backgroundColor: responsible != undefined && responsible.employeeId == resource.id ? "#9B0300" : available ? "#3E9B85" : "#3F7F9B",
                        isResponsible: responsible,
                        /*available: shiftAssignment*/

                        //backgroundColor: event.responsible != undefined && event.responsible.employeeId == resource.id ? "#9B0300" : "#3E9B85"
                    };

                })

            })





            toFullCalendarEvent(shift, function (fullCalendarEvent) {

                $('#calendar').fullCalendar('renderEvent', fullCalendarEvent, /*sticky*/true);

            })

        }
        /*  getShiftsWithRequestChange(function (ch) {

         var shifts = shifts1.concat(ch);

         getAvailableShifts(function (availableshifts) {

         availableshifts.concat(shifts);*/
        /*var ev = shifts.map(toFullCalendarEvent);
         $('#calendar').fullCalendar('addEventSource', ev);*/
        /* });
         });*/
    });
});


