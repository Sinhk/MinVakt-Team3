/**
 * Created by Stine on 17.01.17.
 */
$(document).ready(function(){

    /*for(var i = 0; i<330; i++){

        var day = (17+i)%29;
        var date = (day+1) >= 10 ? (day+1) : "0"+(day+1)
        $.ajax({
            url: "/shifts",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "startD": date,
                "startY": 2017,
                "startH": "06",
                "startMonth": Math.floor((i/29)+1) > 9 ? Math.floor((i/29)+1) : "0"+Math.floor((i/29)+1) ,
                "startMin":"00",
                "endD": date,
                "endY": 2017,
                "endH": 14,
                "endMonth":Math.floor((i/29)+1) > 9 ? Math.floor((i/29)+1) : "0"+Math.floor((i/29)+1),
                "endMin": "00",
            }),
            success: function () {
                console.log("Added shif");
            }
        });

        $.ajax({
            url: "/shifts",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "startD": date,
                "startY": 2017,
                "startH": 14,
                "startMonth": Math.floor((i/29)+1) > 9 ? Math.floor((i/29)+1) : "0"+Math.floor((i/29)+1),
                "startMin":"00",
                "endD": date,
                "endY": 2017,
                "endH": 22,
                "endMonth":Math.floor((i/29)+1) > 9 ? Math.floor((i/29)+1) : "0"+Math.floor((i/29)+1),
                "endMin": "00",
            }),
            success: function () {
                console.log("Added shif");
            }
        });
        $.ajax({
            url: "/shifts",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                "startD": date,
                "startY": 2017,
                "startH": 22,
                "startMonth":Math.floor((i/29)+1) > 9 ? Math.floor((i/29)+1) : "0"+Math.floor((i/29)+1),
                "startMin":"00",
                "endD": date+1,
                "endY": 2017,
                "endH": "06",
                "endMonth":Math.floor((i/29)+1) > 9 ? Math.floor((i/29)+1) : "0"+Math.floor((i/29)+1),
                "endMin": "00"
            }),
            success: function () {
                console.log("Added shif");
            }
        });

    }*/


    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    /*$('.modal').modal({
     dismissible: true, // Modal can be dismissed by clicking outside of the modal
     opacity: .5, // Opacity of modal background
     in_duration: 300, // Transition in duration
     out_duration: 200, // Transition out duration
     starting_top: '4%', // Starting top style attribute
     ending_top: '10%', // Ending top style attribute
     ready: function(modal, trigger) { // Callback for Modal open. Modal and trigger parameters available.
     alert("Ready");
     console.log(modal, trigger);
     },
     complete: function() { alert('Closed'); } // Callback for Modal close
     }
     );*/


    var shifts = getAllSuitableShifts();

    //console.log(shifts);

    var fullCalendarEvents = [];

    for(var i = 0; i < /*shifts.length*/60; i++){

        fullCalendarEvents.push(toFullCalendarEvent(shifts[i]));

    }

    $('#calendar').fullCalendar({

        locale: "no",
        timezone: "UTC",
        displayEventTime: false,
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
        dayClick:function (data ) {
            console.log("You clicked: "+data);
        },
        events: fullCalendarEvents,

        eventClick: function( event, jsEvent, view ) {

            console.log(event);
            alert("*Insert CSS stuff here*\n[SETT SOM TILGJENGELIG]");

        }
    });

    /*for(var i = 0; i<shifts.length; i++) {

     $('#calendar').fullCalendar( 'renderEvent', fullCalendarEvents[i], true);

     }*/
});