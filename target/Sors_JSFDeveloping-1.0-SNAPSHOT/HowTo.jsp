<!DOCTYPE html>
<%@page import="com.jubaka.sors.serverSide.UserBase"%>
<%@page import="com.jubaka.sors.serverSide.User"%>
<%@page import="java.util.Date"%>
<html>
<head>
<meta charset="UTF-8">
<script src="style/js/jquery-2.1.4.js"></script>
<link rel="stylesheet" href="style/css/bootstrap.css">
<link href="style/css/carousel.css" rel="stylesheet">
<title>Insert title here</title>
<style type="text/css">
.mainLogo_signed {
	position: relative;
	left: 925px;
	width: 110px;
	padding: 0;
	margin: 0;
	z-index: 9;
}
.mainLogo_unsigned {
	position: relative;
	left: 910px;
	width: 110px;
	padding: 0;
	margin: 0;
	z-index: 9;
}

</style>
</head>
<body>
<div class="navbar-wrapper">
		<div class="container">

			<nav
				style="margin-top: 5mm; z-index: 1; width: 1170px; position: absolute;"
				class="navbar navbar-inverse navbar-static-top" role="navigation">
				<div class="container">
					<div class="navbar-header">
						<button type="button"  class="navbar-toggle collapsed"
							data-toggle="collapse" data-target="#navbar"
							aria-expanded="false" aria-controls="navbar">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">SORS</a>

					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
						<% if (session.getAttribute("user")!=null) {
								String user = (String) session.getAttribute("user");
								User uObj = UserBase.getInstance().getUser(user);
								if (uObj!=null)
									uObj.setLastLogin(new Date());
						
						%>
							<li><a href="BranchList">Processing</a></li>
							<li><a href="Nodes">Nodes</a></li>
							<li><a href="BranchView">View</a></li>
							
							<li style="margin-left: 715px;" class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"><%= session.getAttribute("user").toString() %> <span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="Profile">Profile</a></li>
									<li><a href="#">1352345</a></li>
									<li><a href="#">Something else here</a></li>
									<li class="divider"></li>
									<li class="dropdown-header">Nav header</li>
									<li><a href="Auth?signout=true">Sign out</a></li>
							
								</ul></li>
							<% } else { %>
							<li ><a href="#">About</a></li>
							<li class="active"><a  href="#">How to</a></li>
							<li><a href="#contact">Download</a></li>
							
							
						
							<li style="margin-left: 710px;">
							<!--  	<a data-toggle="modal" data-target="#regModal" style=" padding-right:2px; margin-left:2px; margin-right:2px; float: left;" href="#"> Register </a> -->
							<a  data-target="#regModal" style=" padding-right:2px; margin-left:2px; margin-right:2px; float: left;" href="signup.html"> Register </a> 
								<span style="margin-left:0px; margin-right:0px;margin-top: 15px; float: left;">/</span>
								<a data-toggle="modal" data-target="#signModal" style="padding-left:2px;padding-right:2px; margin-left:2px; margin-right:2px; float:left;" href="#">Sign in</a>
							</li>
							<% } %>
							
						</ul>


					</div>


				</div>

			</nav>
			<% if (session.getAttribute("user")!=null) { %>
			<img class="mainLogo_signed" alt="" src="style/mainLogo.png">
			<% } else { %> 
			<img class="mainLogo_unsigned" alt="" src="style/mainLogo.png"> <% } %>
		</div>
	</div>
	<div style="background-color: #5A5A5A; height: 120px;">
		
	</div>
		<div style="width: 1000px; margin-left: auto; margin-right: auto; " class="panel panel-default">
			SORS - project that will help you to control all network activity of your devices wherever you are!
			<br>
			It consist of two part. The first one is Java Desktop application that capture network packets and analyze it.
			<br>
			Desktop application is distributed like a runnable jar file, so you can run it with command 
			<br>
			'#sudo java -jar /path/to/Sors.jar'
			<br>
			
			</div>
	
	
</body>
</html>