$(document).ready(function () { // document ready
    var calendar = $('#calendar');
    calendar.fullCalendar({
        schedulerLicenseKey: 'CC-Attribution-NonCommercial-NoDerivatives',
        locale: "nb",
        selectable: true,
        resourceAreaWidth: 230,
        //editable: true,
        aspectRatio: 1.5,
        scrollTime: '00:00',
        header: {
            left: 'prev, promptResource today',
            center: 'title',
            right: 'next'
        },
        firstDay: 1,
        weekNumbers:true,
        defaultView: 'customWeek',
        views: {
            customWeek: {
                type: 'timeline',
                duration: {weeks: 1},
                slotDuration: {days: 1},
                buttonText: 'Vaktliste'
            }
        },

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
        }
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
                            title: (responsible ? " A" : " V" ),

                            backgroundColor: isResponsible ? "#00bcd4" : "#2196f3",

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

