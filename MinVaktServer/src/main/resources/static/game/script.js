/**
 * Created by OlavH on 13-Sep-16.
 */

$(document).ready(function () {

    console.log("Starting up...");

    var docHeight = $(window).innerHeight()-100;
    var docWidth = $(window).innerWidth()-100;

    var randomNumber = Math.random();

    //$("#view").fadeOut();

    $("#square").hover(function () {
        // $(this).fadeOut(500);
        var randHeight = docHeight*Math.random();
        var randWidth = docWidth*Math.random();

        $(this).animate( {
            top: randHeight,
            left: randWidth
        },{queue: false, duration: 1000});
        // $(this).fadeIn(500);
    },function (){});

    $("#square").click(function () {

        $(this).css("background-color","red");

        /*$(this).animate({
            opacity: 1,
            color: "red",
            rotate: "180"
        });*/

        $(this).fadeOut({queue: false, duration: 1000});

    });

    });



/*
 div(square) inside div(view)
 */