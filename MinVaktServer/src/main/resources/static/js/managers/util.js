function parseLocalDateTimeToDate(localdatetime) {

    return new Date(localdatetime);

}


function toFullCalendarEventWithResource(event, resource) {
    var start = event.fromTime;
    var end = event.toTime;

    var dateStart = new Date(start);
    var dateEnd = new Date(end);
    var available = eventIsAvailable(event.shiftId);
    getResponsibleUserForShift(event.shiftId, function (responsible) {
        console.log();
        //console.log("responsible: "+JSON.stringify(responsible));
        //console.log("Avdeling: "+event.comments);
        return {
            id: event.shiftId || 0,
            title: start.split("T")[1].substr(0,3) + " -> " + end.split("T")[1].substr(0,3),
            start: dateStart,
            end: dateEnd,
            resourceId: resource.id,
            backgroundColor: responsible != undefined && responsible.employeeId == resource.id ? "#9B0300" : available ? "#3E9B85" : "#3F7F9B",
            isResponsible: responsible,
            available: available
        };
    });

}


function toFullCalendarEventPromise(shift) {
    const start = shift.fromTime;
    const end = shift.toTime;

    const dateStart = moment(start);
    const dateEnd = moment(end);
    return getDepartmentName(shift.departmentId).then((department) => {
        let responsible = "Ingen";
        if (shift.hasOwnProperty('responsible')){
            responsible = shift.responsible.firstName + " " + shift.responsible.lastName;
        }
        
        return Promise.resolve({
                id: shift.shiftId,
                title: "",//start.split("T")[1].substr(0, 5) + " - " + end.split("T")[1].substr(0, 5),// + ": " + resFullName,
                start: dateStart,
                end: dateEnd,
                //backgroundColor: available ? "#9B0300" : "#3E9B85",
                //available: available,
                avdeling: department,
                responsible: responsible
            });
             });/*.catch((error)=>{
            console.log(error);
        });*/
        //});
    //});
}
function toAvailableEventPromise(event) {
        const dateStart = moment(event.fromTime);
        const dateEnd = moment(event.toTime);

        return getDepartmentName(event.departmentId).then((department)=>{
                return Promise.resolve( {
                    id: event.shiftId,
                    title: "Avdeling: " + department,// + ": " + resFullName,
                    start: dateStart,
                    end: dateEnd
                });
           // });
        });
}

function getDepartmentName(departmentId) {
    return getDepartments().then((departments)=>{
        return Promise.resolve(departments.filter((dep)=>dep.departmentId == departmentId)[0].departmentName);
    })

}
function getDepartments(){
    if(sessionStorage.getItem("departments") === null){
        return $.getJSON("/departments").then((data)=>{
            sessionStorage.departments = JSON.stringify(data);
            return Promise.resolve(JSON.parse(sessionStorage.departments));
            }
        )
    }else{return Promise.resolve(JSON.parse(sessionStorage.departments));}
}

function getDepartmentofShift(shiftId,callback) {

    $.ajax({
            async: true,
            url: "/shifts/" +shiftId + "/department",
            type: "GET",
            contentType: "Text/Plain",
            success: function (data) {
            console.log("Sucsess: " + data);
                      callback(data);

            },
            error: function (data) {
                console.log("Error: " + data);
            }

    })
}

function listToFullCalendarEventList(events, resourceList) {

    var list = [];

    //console.log("events: "+events);

    for (var i = 0; i < events.length; i++) {

        var event = events[i]; // dobbeliste av en eller annen grunn
        var resource = resourceList[i];

        list.push(toFullCalendarEventWithResource(event, resource));
        //if(event != undefined) list.push(toFullCalendarEvent(event, resource));
    }

    return list;
}

function userListToResourceList(userlist) {

    var resourceList = [];

    for (var i = 0; i < userlist.length; i++) {

        resourceList.push({id: userlist[i].employeeId, title: userlist[i].firstName + " " + userlist[i].lastName})

    }
    return resourceList;

}

function getShiftsByEmployee(userlist) {

    function getShiftsByEmployee(userlist) { // TODO sync

        var shiftList = [];

        for (var i = 0; i < userlist.length; i++) {

            $.ajax({
                async: false,
                url: "/users/" + userlist[i].employeeId + "/shifts",
                type: "GET",
                contentType: "Application/JSON",

                success: function (data) {
                    //console.log("Success: /users.GET");
                    shiftList.push(data);

                },
                error: function (data) {
                    console.log("Error: " + data);
                }

            })
        }
        return shiftList;

    }

}

function userListToResourceList(userlist) {

    var resourceList = [];

    for (var i = 0; i < userlist.length; i++) {

        resourceList.push({id: userlist[i].employeeId, title: userlist[i].firstName + " " + userlist[i].lastName});

    }
    return resourceList;

}

function toFullCalendarEvent(event, callback) {
    if (event != undefined) {
        const start = event.fromTime;
        const end = event.toTime;

        const dateStart = moment(start);
        const dateEnd = moment(end);
        getDepartmentName(event.departmentId).then((department)=>{

            getResponsibleUserForShift(event.shiftId, function (responsible) {

                getCurrentUser(function (user) {

                    const resFullName = responsible.firstName + " " + responsible.lastName;

                    const res = responsible.employeeId == user.employeeId;

                    callback( {

                        id: event.shiftId,
                        title: /*start.split("T")[1].substr(0, 2) + " - " + end.split("T")[1].substr(0, 2)+*/(res ? " A" : " V"),// + ": " + resFullName,
                        start: dateStart,
                        end: dateEnd,
                        //backgroundColor: available ? "#9B0300" : "#3E9B85",
                        //available: available,
                        avdeling: department,
                        responsible: (responsible != undefined ? resFullName : "Ingen"),
                        backgroundColor: res ? "#00bcd4" : "#2196f3",

                    });

                })
            });
        });
    }
}

