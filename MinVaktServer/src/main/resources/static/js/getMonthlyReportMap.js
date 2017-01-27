$(document).ready(function(){

    getMonthlyReportMap(new Date().getMonth()+1, function (map) {

         console.log(map);

         getAllUsers(function (employees) {


             const div = document.getElementById("users");

             for (let i = 0; i < employees.length; i++) {

                 let employee = employees[i]; // Uten const faile alt

                 var nr = employee.employeeId;

                 div.innerHTML +=
                         "<tr>" +
                         "<td>" + employee.firstName + "</td>" +
                         "<td>" + employee.lastName + "</td>" +
                         "<td>" + employee.email + "</td>" +
                         "<td>" + (map[nr] == undefined ? "0" : map[nr])  + "</td>" +
                         "</tr>";
             }
         })
    })
})

 $("#new").click(function () {
        var email="";
        swal({
                    title: "Klar for innsending",
                    text: "Skriv inn e-posten til mottaker av den måndelige rapporten.",
                    type: "input",
                    input: "email",
                    showCancelButton: true,
                    cancelButtonText: "AVBRYT",
                    cancelButtonColor: "#9e9e9e",
                    confirmButtonText: "SEND",
                    confirmButtonColor: "#0d47a1",
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
                    swal ({
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
    })

