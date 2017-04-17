
$(function () {
    google.charts.load('current', {packages: ['corechart']});
});


$(function(){

            new Clipboard('.copy-text');
        });


function drawChart() {

    var chartData = $(".chartData").val();
    var dataModel = new google.visualization.DataTable(chartData);

    var options = {
        title: 'Payload in second',
        hAxis: {title: 'Time'},
        vAxis: {title: 'Paylaoad'},
        legend: 'none',
        width: '850',
        height: '400'
    };
    var chart = new google.visualization.LineChart(document.getElementById('sessionModalChart'));
    chart.draw(dataModel, options);
}

        function updateEventListeners() {
            // Collapse ibox function
            $('.collapse-link').click(function () {
                var ibox = $(this).closest('div.ibox');
                var button = $(this).find('i');
                var content = ibox.find('div.ibox-content');
                content.slideToggle(200);
                button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
                ibox.toggleClass('').toggleClass('border-bottom');
                setTimeout(function () {
                    ibox.resize();
                    ibox.find('[id^=map-]').resize();
                }, 50);
            });

            // Close ibox function
            $('.close-link').click(function () {
                var content = $(this).closest('div.ibox');
                content.remove();
            });
        }

        $(document).ready(function() {
            console.log('niga before');
            var lineData = {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [
                    {
                        label: "Example dataset",
                        fillColor: "rgba(220,220,220,0.5)",
                        strokeColor: "rgba(220,220,220,1)",
                        pointColor: "rgba(220,220,220,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(220,220,220,1)",
                        data: [65, 59, 80, 81, 56, 55, 40]
                    },
                    {
                        label: "Example dataset",
                        fillColor: "rgba(26,179,148,0.5)",
                        strokeColor: "rgba(26,179,148,0.7)",
                        pointColor: "rgba(26,179,148,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(26,179,148,1)",
                        data: [28, 48, 40, 19, 86, 27, 90]
                    }
                ]
            };

            var lineOptions = {
                scaleShowGridLines: true,
                scaleGridLineColor: "rgba(0,0,0,.05)",
                scaleGridLineWidth: 1,
                bezierCurve: true,
                bezierCurveTension: 0.4,
                pointDot: true,
                pointDotRadius: 4,
                pointDotStrokeWidth: 1,
                pointHitDetectionRadius: 20,
                datasetStroke: true,
                datasetStrokeWidth: 2,
                datasetFill: true,
                responsive: true,
            };

            var chartObj =  $('.line');
            if (chartObj !== null && chartObj !== 'undefined') chartObj.peity('line',{height: 30, width: 100, fill: '#5791BC'});
            console.log('niga');
            frame_visiblePart.onresize = function(){
                var height = $('body').height() -$('.navbar-static-top').outerHeight() - $('#blockBofore').outerHeight() -$('.title_sort').outerHeight() - 74;
            $('#filterViewDirect').css('max-height', height);
            }
            
            $(window).resize(function heightAndScroll(){
                var height = $('body').height() -$('.navbar-static-top').outerHeight() - $('#blockBofore').outerHeight() -$('.title_sort').outerHeight() - 74;
            $('#filterViewDirect').css('max-height', height);
            });

        });


$("#subnetExpander").click(function() {
    $('#subnetInfoBody').slideToggle(1000);
    $('#subnetInfoTitle').slideToggle(1000);
    resizeBody();

});
$('#ipExpander').click(function(){
	$("#ipInfoBody").slideToggle(1000);
	$('#ipInfoTitle').slideToggle(1000);
})
$('#sort_ip_collapse').click(function(){
	$("#sort_ip_body_collapse").slideToggle(800);
})

$('#showSelect').click(function(){
	$("#subnetList").slideToggle(500);
})
$('.select_ip__value').click(function(){
	var elem = $('.select_ip__value');
	$("#subnetList").slideToggle(500);
	$('#showSelect')
})

$(window).resize(function(){
	resizeBody();
})
function resizeBody(){
	/**/
	if($('#subnetInfoBody').outerHeight() > 5){
		height = $('#subnetInfoBody').outerHeight();
		$('#ipInfoBody').outerHeight(height);
		$('#box_with_buttons').outerHeight(height);

	}
}


//showSelect



function downToWindow() {
    var height = $('body').height() - $('.navbar-static-top').outerHeight() - $('#blockBofore').outerHeight() - $('.title_sort').outerHeight() - 74;
    $('#filterViewDirect').css('max-height', height);
}


$(function() {

    var container = $("#flot-line-chart-moving");
    if (container.length == 0) return;
    console.log('build chart _0' + container.length);
    // Determine how many data points to keep based on the placeholder's initial size;
    // this gives us a nice high-res plot while avoiding more than one point per pixel.

    var maximum = container.outerWidth() / 1 || 300;

    //  container.css(display, 'none');

    //

    var data = [];

    function getRandomData() {

        if (data.length) {
            data = data.slice(1);
        }

        while (data.length < maximum) {
            var previous = data.length ? data[data.length - 1] : 50;
            var y = previous + Math.random() * 10 - 5;
            data.push(y < 0 ? 0 : y > 100 ? 100 : y);
        }

        // zip the generated y values with the x values

        var res = [];
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }

        return res;
    }

    series = [{
        data: getRandomData(),
        lines: {
            fill: true
        }
    }];


    var plot = $.plot(container, series, {
        grid: {

            color: "#999999",
            tickColor: "#D4D4D4",
            borderWidth:0,
            minBorderMargin: 20,
            labelMargin: 10,
            backgroundColor: {
                colors: ["#ffffff", "#ffffff"]
            },
            margin: {
                top: 8,
                bottom: 20,
                left: 20
            },
            markings: function(axes) {
                var markings = [];
                var xaxis = axes.xaxis;
                for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
                    markings.push({
                        xaxis: {
                            from: x,
                            to: x + xaxis.tickSize
                        },
                        color: "#fff"
                    });
                }
                return markings;
            }
        },
        colors: ["#1ab394"],
        xaxis: {
            tickFormatter: function() {
                return "";
            }
        },
        yaxis: {
            min: 0,
            max: 110
        },
        legend: {
            show: true
        }
    });

    // Update the random dataset at 25FPS for a smoothly-animating chart

    setInterval(function updateRandom() {
        series[0].data = getRandomData();
        plot.setData(series);
        plot.draw();
    }, 300);

});

$(function() {


    var container = $("#flot-line-chart-moving2");
    if (container.length == 0) return;

    // Determine how many data points to keep based on the placeholder's initial size;
    // this gives us a nice high-res plot while avoiding more than one point per pixel.

    var maximum = container.outerWidth() / 1 || 300;

      //  container.css(display, 'none');

    //

    var data = [];

    function getRandomData() {

        if (data.length) {
            data = data.slice(1);
        }

        while (data.length < maximum) {
            var previous = data.length ? data[data.length - 1] : 50;
            var y = previous + Math.random() * 10 - 5;
            data.push(y < 0 ? 0 : y > 100 ? 100 : y);
        }

        // zip the generated y values with the x values

        var res = [];
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }

        return res;
    }

    series = [{
        data: getRandomData(),
        lines: {
            fill: true
        }
    }];


    var plot = $.plot(container, series, {
        grid: {

            color: "#999999",
            tickColor: "#D4D4D4",
            borderWidth:0,
            minBorderMargin: 20,
            labelMargin: 10,
            backgroundColor: {
                colors: ["#ffffff", "#ffffff"]
            },
            margin: {
                top: 8,
                bottom: 20,
                left: 20
            },
            markings: function(axes) {
                var markings = [];
                var xaxis = axes.xaxis;
                for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
                    markings.push({
                        xaxis: {
                            from: x,
                            to: x + xaxis.tickSize
                        },
                        color: "#fff"
                    });
                }
                return markings;
            }
        },
        colors: ["#1ab394"],
        xaxis: {
            tickFormatter: function() {
                return "";
            }
        },
        yaxis: {
            min: 0,
            max: 110
        },
        legend: {
            show: true
        }
    });

    // Update the random dataset at 25FPS for a smoothly-animating chart

    setInterval(function updateRandom() {
        series[0].data = getRandomData();
        plot.setData(series);
        plot.draw();
    }, 50);

});


function mySearchFunc() {
    // Declare variables
    console.log('niga')
    var input, filter, table, tr,tds, td, i, j, show;
    input = document.getElementById("searchIn");
    filter = input.value.toUpperCase();
    table = document.getElementsByClassName("footable")[0];
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
