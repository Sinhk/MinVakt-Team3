$(document).ready(function(){


    getAllEmployees(function (employees) {

        for(var i = 0; i<employees.length; i++){

            var employee = employees[i];

            var category = getCategory(employee.email);

            var div = document.getElementById('employeeBody');

            var hours = getHourThisWeek(employee.employeeId);

            div.innerHTML +=
                "<tr>"+
                "<td>"+employee.firstName+"</td>"+
                "<td>"+employee.lastName+"</td>"+
                "<td>"+category.categoryName+"</td>"+
                "<td>"+employee.positionPercentage+"%</td>"+
                "<td>"+employee.phone+"</td>"+
                "<td>"+hours+" time(r)</td>"+
                "<td>"+employee.email+"</td>"+
                "</tr>"

        }
    });



    $("#myTable").tablesorter();
    $("#myTable").tablesorter( {sortList: [[0,0], [1,0]]} );
    /*
    $(".modal-trigger").click(function(e){
        e.preventDefault();
        dataModal = $(this).attr("data-modal");
        $("#" + dataModal).css({"display":"block"});
    });

    $(".close-modal, .modal-sandbox").click(function(){
        $(".modal").css({"display":"none"});
    });
*/


});