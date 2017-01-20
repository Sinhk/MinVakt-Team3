/**
 * Created by klk94 on 13.01.2017.
 */



$("#changePositionPercentagebutton").click(function () {
    var newPercentage = $("#newPercentage").val();
    var user_id = $("#user_id").val();

    $.ajax({
        url: "users/"+user_id+"/changePositionPercentage",
        type: "PUT",
        data: newPercentage,


        success: function (data) {
            console.log("Success: "+data);
            
        },
        error: function (data) {
            console.log("Error: "+data)
        }
    })

});