<%@page import="com.jubaka.sors.appserver.beans.branch.BranchInfoBean"%>
<%@page import="com.jubaka.sors.appserver.beans.InfoBean"%>
<%@page import="javax.swing.table.DefaultTableModel"%>
<%@page import="com.jubaka.sors.appserver.serverSide.ConnectionHandler"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.TreeSet"%>
<%@page import="org.jfree.data.time.RegularTimePeriod"%>
<%@page import="java.util.Collection"%>
<%@page import="org.jfree.data.time.TimeSeries"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="style/css/carousel.css" rel="stylesheet">
<link rel="stylesheet" href="style/css/bootstrap.min.css">
<script src="style/js/jquery-2.1.4.js"></script>
<script src="style/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

<style type="text/css">
.table-hover tbody tr:hover td, .table-hover tbody tr:hover th {
  background-color: #red;
}

.mainLogo_signed {
	position: relative;
	left: 925px;
	width: 110px;
	padding: 0;
	margin: 0;
	z-index: 9;
}
</style>
<script type="text/javascript">
var subType='<%= request.getAttribute("SubType")%>';
google.charts.load('current', {packages: ['corechart']});
google.charts.setOnLoadCallback(drawChart);
$(function() {
	
	$(".clickable-row").click(function() {
		
		var brId = getParameterByName("branchId");
		var nodeName = getParameterByName("nodeName");
		var type = getParameterByName("type");
		if (type.indexOf("global") > -1) {
			var net =  $("#dataField_0").text();
			net = net.trim();
			
			window.document.location = "Statistic?branchId="+brId+"&nodeName="+nodeName+"&type=net&net="+net;
		//	alert("niga");
		}
		
		if (type.indexOf("net") > -1) {
			var ip =  $("#dataField_0").text();
			ip = ip.trim();
			alert(nodeName);
			window.document.location = "Statistic?branchId="+brId+"&nodeName="+nodeName+"&type=ip&ip="+ip;
		//	alert("niga");
		}
		
    });
	
		$("tr.tableRow").click(function() {
			var row = $(this);
			if (row.hasClass("active") == true) {
				$().ajax();
			}
			row.addClass("active");
		});
	
});
	
function getParameterByName(name, url) {
    if (!url) url = window.location.href;

    name = name.replace(/[\[\]]/g, "\\$&");// This is just to avoid case sensitiveness for query parameter name
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
	
	function drawChart() {
		var dataModel = new  google.visualization.DataTable();
		dataModel.addColumn('number', 'Time');
		dataModel.addColumn('number','Data IN');
		dataModel.addColumn('number','Data OUT');
		<% 
		// java code
			TimeSeries tsIn = (TimeSeries) request.getAttribute("tsIn");
			TimeSeries tsOut = (TimeSeries) request.getAttribute("tsOut");
			Collection<RegularTimePeriod> pdsIn = (Collection<RegularTimePeriod>) tsIn.getTimePeriods();
			Collection<RegularTimePeriod> pdsOut = (Collection<RegularTimePeriod>) tsOut.getTimePeriods();
			
			TreeSet<RegularTimePeriod> periods = new TreeSet<RegularTimePeriod>();
			for (RegularTimePeriod rtp : pdsIn) periods.add(rtp);
			for (RegularTimePeriod rtp : pdsOut) periods.add(rtp);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM");
			HashSet<Double> values = new HashSet<Double>();
			
		%>
		 var data =  google.visualization.arrayToDataTable([
		                                                   ['Year', 'Sales', 'Expenses'],
		 <%	for (RegularTimePeriod rtp : periods) {	%>
		                                                   ['<%=sdf.format(rtp.getEnd())%>', <%=tsIn.getValue(rtp) %>,   <%=tsOut.getValue(rtp)%>],
		                                                   
		 <% 
			 Number tsInVal = tsIn.getValue(rtp);
			 Number tsOutVal = tsOut.getValue(rtp);  
			 if (tsInVal != null)	values.add(tsIn.getValue(rtp).doubleValue());
			 if (tsOutVal != null)	values.add(tsOut.getValue(rtp).doubleValue());
		 }                                              
		  %>
		                                                   ]);

		 
		 var options = {
				 title: 'Company Performance',
			        hAxis: {
			          title: 'Time'
			        },
			        vAxis: {
			          title: 'Popularity',
			        	 ticks: [
			        	         <% 
			        	         String format;
			        	         for (Double val : values) { 
			        	        	format = ConnectionHandler.processSize(val,2);
			        	         %>
			        	 	        
			        	         {v:<%=val%>, f:'<%= format %>'}, 
			        	         <% } %>
			        	         ]
			        },
			        backgroundColor: '#f1f8e9'
			      };
		  var chart = new google.visualization.LineChart(document.getElementById('chartDiv'));
			 chart.draw(data, options);


	}
</script>

</head>
<body>
<%  

InfoBean nodeInfoBean = (InfoBean) request.getAttribute("nodeInfoBean");
BranchInfoBean branchInfoBean = (BranchInfoBean) request.getAttribute("branchInfoBean");
DefaultTableModel model = (DefaultTableModel) request.getAttribute("TableModel");

%>
<jsp:directive.include file="MainMenu.jsp" /> <!-- add main menu -->
	
	<div style="margin-left: auto; margin-right: auto; width: 950px; margin-top: 95px; padding-left: 10px; padding-right: 10px; padding-bottom: 5px; padding-top: 10px;" class="panel panel-default">
		<div style="display: block; font-size: 18pt;">
			<ul style="width: 100%;">
				<li style="width: 600px; display: inline-block; float: left;"> 
					<a> <%=branchInfoBean.getBranchName()%></a>  / <a> <%=nodeInfoBean.getNodeName() %> </a> @ <a><%=nodeInfoBean.getOwner() %></a> 
				</li>
				<li style="float: left; width: 220px; display: inline-block; text-align: right;"> 
					Type: Global
				</li>
				<li style="margin-left: 10px; display: inline-block;">
					<button type="button" class="btn btn-default" aria-label="Show other properties" data-toggle="collapse" href="#moreInfo" aria-expanded="false" aria-controls="moreInfo">
 						 <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
					</button>
				</li>
				
			</ul>
		</div>
		
		<div class="panel-default collapse" style="font-size: 18pt;" id="moreInfo">
			<table class="table table-default">
				<tr>
					<td>
						Branch owner:
					</td>
					<td>
						 <i> jubaka </i>
					</td>
				</tr>
				<tr>
					<td>
						Interface: 
					</td>
					<td>
						 <i> eth0 </i>
					</td>
				</tr>
				<tr>
					<td>
						Max 
					</td>
					<td>
						16.07.05 17:55:34 - 5 MB/s 
					</td>
				</tr>
				<tr>
					<td>
						Min 
					</td>
					<td>
						16.07.05 11:52:11 - 0 MB/s 
					</td>
				</tr>
			
			</table>
		</div>
		
	</div>

	<div id="chartDiv" style="height: 400px; width: 100%;" class="panel panel-default">
	
	</div>
	<div class="panel panel-body"style="width: 80%; margin-left: auto; margin-right: auto; margin-top: 15px;">
		<table class="table table-hover">
			<thead>
				<tr>
			<% for (Integer i=0;i<model.getColumnCount();i++) { %>
					<th><%= model.getColumnName(i) %>
			<% } %>
				</tr>
			</thead>
			<tbody>
			<% for (Integer i=0;i<model.getRowCount();i++) { %>
				
				<tr class="tableRow clickable-row" data-href='Nodes'>
				
					<% for (Integer j=0;j<model.getColumnCount();j++) { %>
					<td id="dataField_<%= j %>" > <%= model.getValueAt(i, j) %>
					
					<% } %>
				
				</tr>
				
			<% } %>
			</tbody>
		</table>
	</div>
	
</body>
</html>