$(document).ready(function () {
    $('.modal').modal();
    // $(".side-nav").css("margin-top", $(".nav-wrapper").height());

    $('#calendar').fullCalendar({

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
        displayEventEnd: true,
        /*select: function (start, end, jsEvent, view) {

            console.log("Start date: " + moment(start).format() +
                "\nEnd date: " + moment(end).format());

        },*/
        /*dayClick:function (data ) {
         console.log("You clicked: "+data);
         },*/

        eventMouseover: function (calEvent, jsEvent) {
                    var tooltip = '<div class="tooltipevent" style="width:180px;height:70px;background:#e3f2fd;border-style:solid;border-color:#212121;border-width:1px;position:absolute;z-index:10001;">' + ' ' + ' Tidspunkt: ' +calEvent.start.format("HH:mm")+"-"+calEvent.end.format("HH:mm")+ '<br> Avdeling: ' + calEvent.avdeling + '<br>'+ 'Ansvar: ' + ((calEvent.isResponsible != undefined) ? (calEvent.isResponsible) : ("Ingen")) + '</div>';
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
            location.href = "#shiftDetailed";
            openDetails(event);
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

                //console.log(shift);

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

var htmlDet;

function renderDet(event) {
    $('#shiftDetail').html(htmlDet);
    $('#absent_btn').click(() => {
        registerAbsence(event.id);
    });
    $('#change_btn').click(() => {
        requestChange(event);
    });
    $('#close_btn').click(() => {
        $('#shiftDetail').modal('close');
    });
    if(event.start.isBefore(moment())) {
        $('#change_btn').addClass('disabled');
    }
    if(event.start.isBefore(moment().add(2,'h'))) {
        $('#absent_btn').addClass('disabled');
    }
    switchAdminView();
}

function openDetails(event) {
    $('#shiftDetail').modal('open');

    $.getJSON("/shifts/" + event.id, {detailed: true}).then((shift) => {
        if (headerTemplate == null) {
            let headerTemplateSource = $('#detailTitleTemplate').html();
            var headerTemplate = Handlebars.compile(headerTemplateSource);
        }
if(shift.responsible != undefined) {
    for (let i = shift.employees.length - 1; i >= 0; i--) {
        if (shift.employees[i].employeeId == shift.responsible.employeeId) {
            shift.employees.splice(i, 1);
        }
    }
}
        let content = {
            date: event.start.format("DD/MM"),
            from: event.start.format("HH:mm"),
            to: event.end.format("HH:mm"),
            department: event.avdeling,
            responsible: shift.responsible,
            employees: shift.employees
        };
        htmlDet = headerTemplate(content);
        renderDet(event);
    });
}

function requestChange(event) {
    $('#shiftDetail').html("<div class='modal-content'><div class='progress'><div class='indeterminate'></div></div></div>");

    $.getJSON("/shifts/" + event.id + "/possible_users").then((employees)=>{
        if (shiftChangeTemplate == null) {
            let shiftChangeTemplateSource = $('#shiftChangeTemplate').html();
            var shiftChangeTemplate = Handlebars.compile(shiftChangeTemplateSource);
        }

        let content = {
            date: event.start.format("DD/MM"),
            from: event.start.format("HH:mm"),
            to: event.end.format("HH:mm"),
            department: event.avdeling,
            responsible: event.responsible,
            employees: employees
        };

        let html = shiftChangeTemplate(content);
        $('#shiftDetail').html(html);

        $('#employee-box').change(() => {
            for(let i = 0; i <employees.length;i++) {
                if(employees[i].employeeId == $('#employee-box').val()){
                    $('#phoneNr').text(employees[i].phone);
                }
            }
            $('#change_confirm_btn').removeClass('disabled');
        });

        $('#cancel_change_btn').click(() => {
            console.log(htmlDet);
            renderDet(event);
        });
        $('#change_confirm_btn').click(() => {
            getCurrentUser(function (currentUser) {
                for(let i = 0; i <employees.length;i++) {
                    if(employees[i].employeeId == $('#employee-box').val()){
                       var newUser=  employees[i];
                    }
                }
                let date = event.start.format("DD/MM");
                let from = event.start.format("HH:mm");
                let to = event.end.format("HH:mm");

                requestChangeForShift(event.id, currentUser.employeeId, newUser.employeeId, function () {

                    swal("Forespørsel sendt", "Vaktbytte for vakt " +date+ " " + from + " -> " + to +
                        ". Fra "+ currentUser.firstName +" " + currentUser.lastName + " til " + newUser.firstName + " " + newUser.lastName, "success");

                    $('#shiftDetail').modal('close');
                })
            });
        });
    });
}

function registerAbsence(shiftId) {
    swal({
            title: "Årsak til fravær",
            text: "Skriv inn årsaken til ditt fravær under",
            type: "input",
            showCancelButton: true,
            cancelButtonText: "AVBRYT",
            cancelButtonColor: "#9e9e9e",
            confirmButtonColor: "#0d47a1",
            closeOnConfirm: false,
            animation: "slide-from-top",
            inputPlaceholder: "Skriv her",
            showLoaderOnConfirm: true
        },
        function (inputValue) {
            if (inputValue === false) return false;
            if (inputValue === "") {
                swal.showInputError("Du må skrive noe");
                return false
            }

            getCurrentUser(function (user) {
                changeUserAssignment(user.employeeId, shiftId, false, false, false, true, inputValue, function (data) {
                    console.log(data);
                    swal("Fravær meldt", "Kommentar: " + inputValue, "success");
                    $('#calendar').fullCalendar('removeEvents', shiftId);
                    $('#shiftDetail').modal('close');
                });
            });
        }
    );
}

$(document).ready(function () {

    if(new Date().getDate() == 1 ){ // Første dag i måneden

        console.log("First day of month");

        var email="";
        swal({
                title: "Klar for innsending",
                text: "Skriv inn e-posten til mottaker av den måndelige rapporten.",
                type: "input",
                input: "email",
                showCancelButton: true,
                cancelButtonText: "AVBRYT",
                confirmButtonText: "SEND",
                closeOnConfirm: false,
                animation: "slide-from-top",
                inputPlaceholder: "Skriv e-postadresse her"
            },
            function (inputValue) {
                if (inputValue === false) return false;
                if (inputValue === "") {
                    swal.showInputError("Du må skrive noe");
                    return false
                }
                email =inputValue;
                swal({
                    title: "Månedlig rapport innsendt",
                    text: "Du skrev: " + inputValue,
                    type: "success",
                    confirmButtonColor: "#0d47a1"
                })
            }
        ),

            getMonthlyReportMap(new Date().getMonth()+1, function (map) {

                console.log(map);

                getAllUsers(function (employees) {


                    const div = document.getElementById("users");
                    let text = "Liste over ansatte med timer jobbet:";
                    for (let i = 0; i < employees.length; i++) {

                        let employee = employees[i]; // Uten const faile alt

                        var nr = employee.employeeId;
                        text += "\n"+ employee.firstName + " " + employee.lastName + "/"+ employee.email+" : " +(map[nr] == undefined ? "0" : map[nr]);
                    }
                    sendTotalHours(email,text)

                })
            })

    }

})
