$(document).ready(function(){

    $('.modal-trigger').modal();
     // $(".side-nav").css("margin-top", $(".nav-wrapper").height());

    var shifts1 = getAllScheduledShiftsForUser();

    var requestChangeShifts = getShiftsWithRequestChange();

    var shifts = shifts1.concat(requestChangeShifts);

    console.log(shifts);

    var fullCalendarEvents = [];

    for(var i = 0; i<shifts.length; i++){

        fullCalendarEvents.push(toFullCalendarEvent(shifts[i]));

    }

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
        events: fullCalendarEvents,

        eventMouseover: function(calEvent, jsEvent) {
            var tooltip = '<div class="tooltipevent" style="width:130px;height:100px;background:#aed0ea;position:absolute;z-index:10001;">' + ' ' + 'Title: ' + calEvent.title + 'Ansvarsvakt: ' + calEvent.type + '</div>';
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
        }


        /*eventClick: function(calEvent,jsEvent) {
            xOffset = 10;
            yOffset = 30;
            $("body").append(calEvent.tooltip);
            $("#tooltip")
                .css("top",(jsEvent.clientY - xOffset) + "px")
                .css("left",(jsEvent.clientX + yOffset) + "px")
        },
    eventMouseout: function(calEvent,jsEvent) {
            $("#tooltip").remove();
        }

            var eventId = event.id;

            var eventDB = getEventViaID(eventId);

            console.log(eventDB);

        }*/
    });
});