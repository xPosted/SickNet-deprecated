
google.charts.load('current', {packages: ['corechart']});


    var dataModel = new google.visualization.DataTable();
    dataModel.addColumn('number', 'Time');
    dataModel.addColumn('number', 'Data IN');
    dataModel.addColumn('number', 'Data OUT');

    var chartData = $('.sessionChartStr').val();

    var data = google.visualization.arrayToDataTable([
            ['Time', 'Data IN', 'Data OUT'],
            #{taskViewBean.chartDataStr}
]);


    var options = {
        title: 'Company Performance',
        hAxis: {
            title: 'Time'
        },
        vAxis: {
            title: 'Payload',
            ticks: [#{taskViewBean.humanValues}]

        },
        backgroundColor: '#f1f8e9'
    };
    var chart = new google.visualization.LineChart(document.getElementById('sessionModalChart'));
    chart.draw(data, options);

