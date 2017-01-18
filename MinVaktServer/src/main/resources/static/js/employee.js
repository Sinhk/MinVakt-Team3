
    $(document).ready(function()
        {
            $("#myTable").tablesorter();
            $("#myTable").tablesorter( {sortList: [[0,0], [1,0]]} );
            $(".modal-trigger").click(function(e){
            e.preventDefault();
            dataModal = $(this).attr("data-modal");
            $("#" + dataModal).css({"display":"block"});
        });

        $(".close-modal, .modal-sandbox").click(function(){
            $(".modal").css({"display":"none"});
        });
    });