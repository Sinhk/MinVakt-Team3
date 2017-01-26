$(document).ready(function () {
    $('.modal-trigger').modal();
    // $(".side-nav").css("margin-top", $(".nav-wrapper").height());

    $('#calendar').fullCalendar({

        //displayEventTime: false,
        locale: "no",
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
        /*select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format() +
                "\nEnd date: " + moment(end).format());

        },*/
        /*dayClick:function (data ) {
         console.log("You clicked: "+data);
         },*/

        eventMouseover: function (calEvent, jsEvent) {
            var tooltip = '<div class="tooltipevent" style="width:180px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + ' Tidspunkt: ' + calEvent.title + '<br> Avdeling: ' + calEvent.avdeling + '<br>  Ansvar: ' + calEvent.isResponsible + '</div>';
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
            console.log(event);
            openDetails(event);
            /*var eventId = event.id;

            getShiftWithId(eventId, function (data) {

                console.log(data);

            });*/

        },
        /*eventMouseout: function(calEvent,jsEvent) {
         $("#tooltip").remove();
         }

         var eventId = event.id;

         var eventDB = getEventViaID(eventId);

         console.log(eventDB);

         }*/
    });
    switchAdminViewHomePage();
    document.addEventListener('viewChange', switchAdminViewHomePage);
});

function switchAdminViewHomePage() {
    let admin = JSON.parse(sessionStorage.admin);

    let calendar = $('#calendar');
    calendar.fullCalendar('removeEvents');
    if(admin){
        let from = calendar.fullCalendar('getView').intervalStart;
        let to = calendar.fullCalendar('getView').intervalEnd;
        console.log(from.toISOString());
        /*$.ajaxSetup({
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8"
        });*/
        $.getJSON("/shifts/limited", {from: from.toISOString(), to: to.toISOString()}).then((shifts) => {
                let eventsPr = shifts.map(toFullCalendarEventPromise);
            Promise.all(eventsPr).then((events) => {
                calendar.fullCalendar('addEventSource', events);
            });

            }
        );
    }else{
        getScheduledShiftsForCurrentUser(function (shifts) {
            for (let i = 0; i<shifts.length; i++){
                const shift = shifts[i];

                toFullCalendarEvent(shift, function (fullCalendarEvent) {
                    calendar.fullCalendar('renderEvent', fullCalendarEvent, /*sticky*/true);
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
    }
}

function openDetails(event) {
    $('#calendar').addClass('hide');
    $('#shiftDetail').removeClass('hide');
    console.log(event);
}
