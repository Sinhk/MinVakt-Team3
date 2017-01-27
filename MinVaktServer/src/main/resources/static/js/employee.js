$(document).ready(function () {


    getCategories().then((categories) => {

        getAllUsers(function (employees) {

            const div = document.getElementById("employeeBody");
            for (let i = 0; i < employees.length; i++) {

                let employee = employees[i]; // Uten const faile alt

                getHoursThisWeekForUser(employee.employeeId, function (hours) {

                    div.innerHTML +=
                        "<tr>" +
                        "<td>" + employee.firstName + "</td>" +
                        "<td>" + employee.lastName + "</td>" +
                        "<td>" + categories.get(employee.categoryId).categoryName + "</td>" +
                        "<td>" + employee.positionPercentage + "%</td>" +
                        "<td>" + employee.phone + "</td>" +
                        "<td>" + hours + " timer</td>" +
                        "<td>" + employee.email + "</td>" +
                        "<td> <button class='waves-effect waves-light btn blue darken-4' onClick=\"removeButton("+employee.employeeId+")\">Slett</button>"+"</td>" +
                        "</tr>";
                });
            }
        });
    });


    function myFunction() {
        var input, filter, table, tr, td, i;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            if (td) {
                if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
});
