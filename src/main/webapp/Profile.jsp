<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.jubaka.sors.serverSide.User"%>
<html>
<head>
<link href="style/css/carousel.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="style/css/bootstrap.css">
<script src="style/js/bootstrap.min.js"></script>
<style type="text/css">
.tableNodeProp th {
	width: 220px;
	text-align: right;
	border-right: 2px solid #ddd;
}

.statNodeTab {
	margin-top: 15px;
}

.statNodeTab th {
	text-align: right;
	width: 200;
	border-right: 2px solid #ddd;
}

.statNodeTab td {
	text-align: center;
}

.tableNodeProp td {
	text-align: center;
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

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<% User uObj = (User) request.getAttribute("uObj"); %>

<jsp:directive.include file="MainMenu.jsp" /> <!-- add main menu -->
	
	<div style="margin-top: 95px;" align="center">
	<div  style="width: 1000px;" class="panel panel-default">
		<div style="text-align: left; padding-left: 25px;" class="panel-heading">
			<h3>User information</h3> 
		</div>
		<div class="panel-body">
			<div style="float:left;" >
				<img style="width: 200px; height: 200px;" align="left" alt="foto" src="ImageProcessor?img=<%= uObj.getImage() %>">
			</div>
			<div style="width:60%; float:left;">
				
					<h4 style="text-align: left; padding-left:0px; margin-left:10%; margin-bottom: 2px;">
						General info
					</h4>
					<hr style="margin-left:6%; margin-top:0px;  padding-top:0px; background: -moz-linear-gradient(right,#fff,gray); height: 2px; width: 65%;">
					
				
				
				<table style="margin-left: 10%;" class="table table-default">
					<tr>
						<th>NickName
						<td><%=	uObj.getNickName() %>
					</tr>
					<tr>
						<th>first name
						<td> <%=uObj.getFirstName() %>
					</tr>
					<tr>
						<th>second name
						<td><%= uObj.getSecondName() %>
					</tr>
					<tr>
						<th>email
						<td><%=uObj.getEmail() %>
					</tr>
					<tr>
						<th>phone
						<td><%= uObj.getPhone() %>
					</tr>
					<tr>
						<th>join date
						<td><% 
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
						Date jDate =  uObj.getJoinDate();
						out.println(sdf.format(jDate));
						%>
					</tr>
					<tr>
						<th>last login
						<td><% 
						Date d = uObj.getLastLogin();
						if (d==null) d = new Date();
						out.println(sdf.format(d));
						%>
					</tr>
					
				</table>
				<button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#UserNodeInfo" aria-expanded="false" aria-controls="#UserNodeInfo">
				More Info
				</button>
				<div class="collapse" id="UserNodeInfo">
					
					<h4 style="text-align: left; padding-left:0px; margin-left:10%; margin-bottom: 2px;">
						Node info
					</h4>
					<hr style="margin-left:6%; margin-top:0px;  padding-top:0px; background: -moz-linear-gradient(right,#fff,gray); height: 2px; width: 65%;">
					<table style="margin-left: 10%;" class="table table-default">
						<tr>
							<th>Unique nodeServerEndpoints count</th>
							<td>0</td>
						</tr>
						<tr>
							<th>Total nodeServerEndpoints uptime</th>
							<td>0</td>
						</tr>
						<tr>
							<th>Total dumps processed</th>
							<td>0</td>
						</tr>
						<tr>
							<th>Total dump len</th>
							<td>0</td>
						</tr>
					</table>
					
					
					<div>
					<h4 style="text-align: left; padding-left:0px; margin-left:10%; margin-bottom: 2px;">
						Public nodeServerEndpoint info
					</h4>
					<hr style="margin-left:6%; margin-top:0px;  padding-top:0px; background: -moz-linear-gradient(right,#fff,gray); height: 2px; width: 65%;">
					<table style="margin-left: 10%;" class="table table-default">
						<tr>
							<th>Unique nodeServerEndpoints count</th>
							<td>0</td>
						</tr>
						<tr>
							<th>Total nodeServerEndpoints uptime</th>
							<td>0</td>
						</tr>
						<tr>
							<th>Total dumps processed</th>
							<td>0</td>
						</tr>
						<tr>
							<th>Total dump len</th>
							<td>0</td>
						</tr>
					</table>
					
				</div>
				</div>
				
				
				
			</div>
			
		</div>
		
	</div>
	</div>
	
	
</body>
</html>