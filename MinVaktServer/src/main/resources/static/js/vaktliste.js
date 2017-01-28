$(document).ready(function () { // document ready
    var calendar = $('#calendar');
    calendar.fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        locale: "nb",
        selectable: true,
        //editable: true,
        scrollTime: '00:00',
        header: {
            left: 'prev, promptResource today',
            center: 'title',
            right: 'next'
        },
        weekNumbers:true,
        defaultView: 'timelineWeek',
        slotDuration: {days: 1},
        /*views: {
            customWeek: {
                type: 'timeline',
                duration: {weeks: 1},
                buttonText: 'Vaktliste'
            }
        },*/
        displayEventTime: true,
        displayEventEnd: true,
        resourceLabelText: 'Ansatte',
        resources: function(callback){
            getUsersAndCreateResourceList(function (data) {
                callback(data);
            })
        },
        viewRender: function( view ){
            renderEvents(view);
        },
        select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format() +
                "\nEnd date: " + moment(end).format());
        },

        eventMouseover: function (calEvent, jsEvent) {
            var tooltip = '<div class="tooltipevent" style="width:180px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + ' Tidspunkt: ' +calEvent.start.format("HH:mm")+"-"+calEvent.end.format("HH:mm")+ '<br> Avdeling: ' + calEvent.department + '<br>'+ 'Ansvar: ' +calEvent.responsible + '</div>';
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


    });

});

function renderEvents(view) {
    let calendar = $('#calendar');
    calendar.fullCalendar('removeEvents');
    let from = view.intervalStart;
    let to = view.intervalEnd;
    $.getJSON("/shifts/limited", {from: from.toISOString(), to: to.toISOString(), detailed: true}).then((shifts) => {
        let events = [];
            shifts.forEach((shift) => {
                    const start = moment(shift.fromTime);
                    const end = moment(shift.toTime);
                    let responsible = "Ingen";
                    if (shift.hasOwnProperty('responsible')) {
                        responsible = shift.responsible.firstName + " " + shift.responsible.lastName;
                    }

                    shift.employees.forEach((employee) => {
                        const isResponsible = shift.responsibleEmployeeId == employee.employeeId;
                        events.push(
                            {
                                id: shift.shiftId,
                                resourceId: employee.employeeId,
                                start: start,
                                end: end,
                                title: (isResponsible ? " A" : " V" ),
                                department: shift.department.departmentName,
                                backgroundColor: isResponsible ? "#00bcd4" : "#2196f3",
                                responsible: responsible,
                                stick: true
                            });

                });
            });
        calendar.fullCalendar('addEventSource', events);
        }
    );
}

function getUsersAndCreateResourceList(callback) {

    const res = [];

    getAllUsers(function (users) {

        for (var i = 0; i < users.length; i++) {

            const user = users[i];

            res.push({
                id: user.employeeId,
                title: user.firstName + " " + user.lastName,
            })
        }
        callback(res);
    })

}

