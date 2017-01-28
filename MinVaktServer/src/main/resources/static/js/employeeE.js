/**
 * Created by elisabethmesoy on 25.01.2017.
 */
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
                        "<td>" + employee.phone + "</td>" +
                        "<td>" + hours + " timer</td>" +
                        "</tr>";
                });
            }
        });
    });
});