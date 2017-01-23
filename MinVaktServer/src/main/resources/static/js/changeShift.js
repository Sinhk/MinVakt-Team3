/**
 * Created by kristinweiseth on 18.01.2017.
 */
$(document).ready(function(){

    getAllUsers(function (employees) {

        for(var i = 0; i<employees.length; i++){

            var employee = employees[i];

            getCategory(employee.email, function (category) {

                var div = document.getElementById('employeeBody');

                getHoursThisWeekForUser(employee.employeeId, function (hours) {

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
                });
            });
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