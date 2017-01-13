$(document).ready(function(){
    $('#calendar').fullCalendar({
        header: {
            left:'prev',
            center:'title',
            right:'next'
        },
        columnFormat: 'dd',
        eventLimit: 2,
        eventClick: function(calEvent, jsEvent, view) {
            $(this).html('<img src="'+calEvent.image+'" />');
        }
    });
    $('#calendar').fullCalendar('renderEvent', { title: 'Test Event 1', start: '2016-11-05', end: '2016-11-09', image: 'https://s-media-cache-ak0.pinimg.com/236x/38/cb/d0/38cbd0f6b73df4414c6831806b65a070.jpg' }, true);
    $('#calendar').fullCalendar('renderEvent', { title: 'Test Event 2', start: '2016-11-07', end: '2016-11-12', image: 'https://s-media-cache-ak0.pinimg.com/236x/38/cb/d0/38cbd0f6b73df4414c6831806b65a070.jpg' }, true);
    $('#calendar').fullCalendar('renderEvent', { title: 'Test Event 3', start: '2016-11-09', image: 'https://s-media-cache-ak0.pinimg.com/236x/38/cb/d0/38cbd0f6b73df4414c6831806b65a070.jpg' }, true);
});