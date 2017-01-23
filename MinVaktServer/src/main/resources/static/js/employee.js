$(document).ready(function(){

    getAllUsers(function (employees) {

        for(var i = 0; i<employees.length; i++){

            let employee = employees[i]; // Uten const faile alt

            console.log(employee);

            getHoursThisWeekForUser(employee.employeeId, function (hours) {

                getCategory(employee.email, function (category) {

                    var div = document.getElementById("employeeBody");

                    div.innerHTML +=
                        "<tr>" +
                        "<td>" + employee.firstName + "</td>" +
                        "<td>" + employee.lastName + "</td>" +
                        "<td>" + category.categoryName + "</td>" +
                        "<td>" + employee.positionPercentage + "%</td>" +
                        "<td>" + employee.phone + "</td>" +
                        "<td>" + hours + " timer</td>" +
                        "<td>" + employee.email + "</td>" +
                        "</tr>"

                })
            });
        }
    });



    function myFunction() {
        var input, filter, table, tr, td, i;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");

        for(i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[0];
            if(td) {
                if(td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
    $("#myTable").tablesorter();
    $("#myTable").tablesorter( {sortList: [[0,0], [1,0]]} );
});