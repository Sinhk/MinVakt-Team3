$("#addShiftToUser").click(function () {

    var int1 = $("#shift_id").val();
    var int2 = $("#user_id").val();


    console.log(int1+" - "+int2);

   addUserToShift(int2, int1,function (data) {

       console.log(JSON.stringify(data))


   })

});