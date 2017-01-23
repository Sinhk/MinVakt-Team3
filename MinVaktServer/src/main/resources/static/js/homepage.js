$(document).ready(function(){

    $('.modal-trigger').modal();
     // $(".side-nav").css("margin-top", $(".nav-wrapper").height());

    getScheduledShiftsForCurrentUser(function (shifts1) {

      /*  getShiftsWithRequestChange(function (ch) {

            var shifts = shifts1.concat(ch);

            getAvailableShifts(function (availableshifts) {

                availableshifts.concat(shifts);*/
                var ev = shifts1.map(toFullCalendarEvent);
                $('#calendar').fullCalendar('addEventSource',ev);
           /* });
        });*/
    });

    $('#calendar').fullCalendar({

        displayEventTime: false,
        locale: "no",
        timezone: "UTC",
        selectable: true,
        header: {
            left:'prev, today',
            center:'title',
            right:'next'
        },
        firstDay: 1,
        weekNumbers: true,
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
        select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format()+
                "\nEnd date: " + moment(end).format());

},
/*dayClick:function (data ) {
    console.log("You clicked: "+data);
},*/

eventMouseover: function(calEvent, jsEvent) {
    var tooltip = '<div class="tooltipevent" style="width:150px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + ' Tidspunkt: ' + calEvent.title + '<br> Avdeling: ' +calEvent.avdeling+ '<br>  Ansvar: '+calEvent.isResponsible+'</div>';
    var $tool = $(tooltip).appendTo('body');
    $(this).mouseover(function(e) {
        $(this).css('z-index', 10000);
        $tool.fadeIn('500');
        $tool.fadeTo('10', 1.9);
    }).mousemove(function(e) {
        $tool.css('top', e.pageY + 10);
        $tool.css('left', e.pageX + 20);
    });
},
eventMouseout: function(calEvent, jsEvent) {
    $(this).css('z-index', 8);
    $('.tooltipevent').remove();
},


        eventClick: function(event,jsEvent) {
            var eventId = event.id;

            getShiftWithId(eventId, function (data) {

                console.log(data);

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
});


