/**
 * Created by elisabethmesoy on 23.01.2017.
 */

$(document).ready(function(){
    $('.collapsible').collapsible();

    $("#button1").click(function () {
        swal("Vakten ble byttet", "", "success")
        $('#collapse1').hide();
    });

    $("#button2").click(function () {
        swal("Vakten ble byttet", "", "success")
        $('#collapse2').hide();
    });

    $("#button3").click(function () {
        swal("Vakten ble ikke byttet", "", "error");
        $('#collapse1').hide();
    })

    $("#button4").click(function () {
        swal("Vakten ble ikke byttet", "", "error");
        $('#collapse2').hide();
    })
});

