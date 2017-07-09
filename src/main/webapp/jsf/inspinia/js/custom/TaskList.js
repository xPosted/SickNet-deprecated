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

function mySearchFunc() {
    // Declare variables
    console.log('niga')
    var input, filter, table, tr,tds, td, i, j, show;
    input = document.getElementById("searchIn");
    filter = input.value.toUpperCase();
    table = document.getElementById("tasksTable");
    tr = table.getElementsByTagName("tr");


    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
        if ($(tr[i]).hasClass('footable-row-detail')) continue;
        show = false;
        tds = tr[i].getElementsByTagName("td");
        for (j = 0; j<tds.length; j++) {
            td=tds[j];
            if (td) {
                if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    show = true;
                }
            }
        }
        if (show) {
            tr[i].style.display = "";
        } else {
            tr[i].style.cssText = 'display: none !important';
            console.log('niga off >>>'+tr[i].innerHTML);
        }



    }
}