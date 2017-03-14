/**
 * Created by root on 19.02.17.
 */

var lastIntevalId;
$(function() {
    console.log("niga niga niga niga niga");
});


function disableSubnetChart() {
    window.clearInterval(lastIntevalId);
}

function initSubnetChartKb(maxvalue) {
    console.log("initSubnetKb - ");
    console.log("maxvalue kb - "+maxvalue);

    if ( ! lastIntevalId) {
        $("#receivedBefore").val($("#chartData\\:justReceived").val());
    }

    if (lastIntevalId) {
        window.clearInterval(lastIntevalId);
    }
    var container = $("#flot-line-chart-moving");

    // Determine how many data points to keep based on the placeholder's initial size;
    // this gives us a nice high-res plot while avoiding more than one point per pixel.

    var maximum = container.outerWidth() / 2 || 300;

    //
    var data = [];
    function calcData() {
        var res = [];
        if ( ! data.length) {
            while (data.length < maximum) {
                data.push(0);
            }


        }
        data = data.slice(1);
        var y = $("#chartData\\:justReceived").val() -  $("#receivedBefore").val();
        console.log(" y calcData Kb"+y);
        data.push(y);
        for (var i = 0; i < data.length; ++i) {
            if (data[i] > 2048576) {
                disableSubnetChart();
                var strVal = (data[i] / 10).toFixed();
                var intVal = parseInt(strVal);
                initSubnetChartMb(data[i]+intVal);
                return;
            }
            if (data[i] > maxvalue) {
                disableSubnetChart();
                var strVal = (data[i] / 10).toFixed();
                var intVal = parseInt(strVal);
                initSubnetChartKb(data[i] + intVal);
                return;
            }
        }
        $("#receivedBefore").val($("#chartData\\:justReceived").val());


        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }
        return res;
    }

    series = [{
        data: calcData(),
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
            max: maxvalue,
            tickFormatter: function(val, axis) {
                return (val/1024).toFixed()+" Kb";
            }
        },
        legend: {
            show: true
        }
    });

    // Update the random dataset at 25FPS for a smoothly-animating chart

    lastIntevalId =  setInterval(function updateRandom() {
        console.log("some msg");
        series[0].data = calcData();
        plot.setData(series);
        plot.draw();
    }, 300);

}

function initSubnetChartMb(maxvalue) {
    console.log("-------------- Init SubnetChartMB------------------");
    console.log("maxvalue mb - "+maxvalue);

    if (lastIntevalId) {
        window.clearInterval(lastIntevalId);
    }
    var container = $("#flot-line-chart-moving");

    // Determine how many data points to keep based on the placeholder's initial size;
    // this gives us a nice high-res plot while avoiding more than one point per pixel.

    var maximum = container.outerWidth() / 2 || 300;

    //
    var data = [];

    function calcData() {
        var res = [];
        if ( ! data.length) {
            while (data.length < maximum) {
                data.push(0);
            }


        }
        data = data.slice(1);
        var y = $("#chartData\\:justReceived").val() -  $("#receivedBefore").val();
        console.log(" y calcData Mb"+y);
        data.push(y);
        for (var i = 0; i < data.length; ++i) {
            if (data[i] > maxvalue) {
                disableSubnetChart();
                var strVal = (data[i] / 10).toFixed();
                var intVal = parseInt(strVal);
                initSubnetChartMb(data[i] + intVal);
                return;
            }
        }
        $("#receivedBefore").val($("#chartData\\:justReceived").val());


        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }
        return res;
    }

    series = [{
        data: calcData(),
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
            max: maxvalue,
            tickFormatter: function(val, axis) {
                console.log("tickFormatter val = "+val)
                return (val/1048576).toFixed(1)+" Mb";
            }
        },
        legend: {
            show: true
        }
    });

    // Update the random dataset at 25FPS for a smoothly-animating chart

    lastIntevalId =  setInterval(function updateRandom() {
        console.log("some msg");
        series[0].data = calcData();
        plot.setData(series);
        plot.draw();
    }, 300);

}