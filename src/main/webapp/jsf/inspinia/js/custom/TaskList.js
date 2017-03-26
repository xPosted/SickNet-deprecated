$("#hide_button").click(function(){
    var blockToHide = $("#nodes_content__widget");

    blockToHide.toggle("right", function(){
        var button = $("#hide_button");
        if(button.hasClass("fa-chevron-right")){
            button.removeClass("fa-chevron-right");
            button.addClass("fa-chevron-left");
        }else{
            button.removeClass("fa-chevron-left");
            button.addClass("fa-chevron-right");
        }
    });
   /* .toggleClass("fa-chevron-left");*/
})