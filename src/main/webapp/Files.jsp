<%@page import="com.jubaka.sors.beans.branch.SessionBean"%>
<%@page import="com.jubaka.sors.sessions.Session"%>
<%@page import="com.jubaka.sors.serverSide.ConnectionHandler"%>
<%@page import="com.jubaka.sors.beans.FileBean"%>
<%@page import="com.jubaka.sors.beans.DirectoryBean"%>
<%@page import="com.jubaka.sors.beans.FileListBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="style/css/bootstrap.css">
<link href="style/css/carousel.css" rel="stylesheet">
<script src="style/js/bootstrap.min.js"></script>
<script src="style/js/jquery-2.1.4.js"></script>
<script type="text/javascript">
	function fileDelete(sorsPath) {
		alert(sorsPath);
		$.ajax({

			// The URL for the request
			url : "jquery_test",

			// The data to send (will be converted to a query string)
			data : {
				id : 123,
				ip : 'sdsdfg'
			},

			// Whether this is a POST or GET request
			type : "GET",

			// The type of data we expect back
			dataType : "text",

			// Code to run if the request succeeds;
			// the response is passed to the function
			success : function(text) {
				alert(text);
			},

			// Code to run if the request fails; the raw request and
			// status codes are passed to the function
			error : function(xhr, status, errorThrown) {
				alert("Sorry, there was a problem!");
				console.log("Error: " + errorThrown);
				console.log("Status: " + status);
				console.dir(xhr);
			},

			// Code to run regardless of success or failure
			complete : function(xhr, status) {
				alert("The request is complete!");
			}
		});
	}

	function showSessionModal(fName,srcIP,dstIP,srcPort,dstPort,srcDataLen,dstDataLen,timeStamp) {
		document.getElementById("srcIP").innerHTML=srcIP;
		document.getElementById("dstIP").innerHTML=dstIP;
		document.getElementById("srcPort").innerHTML=srcPort;
		document.getElementById("dstPort").innerHTML=dstPort;
		document.getElementById("srcDataLen").innerHTML=srcDataLen;
		document.getElementById("dstDataLen").innerHTML=dstDataLen;
		document.getElementById("timeStamp").innerHTML=timeStamp;
		
		document.getElementById("modalFileName").innerHTML = fName;
	}
	function onItemMouseLeave(event) {
		event.target.style.background = "";
	}
	function onItemMouseEnter(event) {
		event.target.style.background = "#d9e1f3";
	}
</script>

<style type="text/css">

#stat tr td {
border-top: none;
padding: 3px;
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	FileListBean flb = (FileListBean) request.getAttribute("FileListBean");

%>
	<div class="navbar-wrapper">
		<div class="container">

			<nav
				style="margin-top: 5mm; z-index: 1; width: 1170px; position: absolute;"
				class="navbar navbar-inverse navbar-static-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="sors.jsp">SORS</a>

				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li><a href="BranchList">Processing</a></li>
						<li class="active"><a href="Nodes">Nodes</a></li>
						<li><a href="BranchView">View</a></li>

						<li style="margin-left: 715px;" class="dropdown"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"><%=session.getAttribute("user").toString()%>
								<span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="Profile">Profile</a></li>
								<li class="divider"></li>
								<li><a href="Auth?signout=true">Sign out</a></li>
							</ul></li>
					</ul>


				</div>


			</div>

			</nav>
			<img class="mainLogo_signed" alt="" src="style/mainLogo.png">

		</div>
	</div>

	<div class="panel"
		style="margin-top: 95px; margin-bottom: 10px; margin-left: auto; margin-right: auto; width: 990px;">
		<div style="padding-left: 5px; padding-top: 5px;" class="panel-body">
			<div  style="float: left; width: 600px;">
			<table style="margin-top: 30px;" class="table table-default">
				<thead>
					<tr>
					<%	
					String path = "/";
					String[] splitedFullPath = flb.getMainDir().getFullPath().split("/"); 
					
					%>
						<th style="font-size: 16px;"> 
						/<a href="Files?nodeServerEndpoint=<%=request.getParameter("nodeServerEndpoint")%>&brID=<%=request.getParameter("brID")%>&path=/">root</a>
						<% for (String item : splitedFullPath) { 
							if (item.trim().equals("")) continue;
								path=path+item+"/";
						%>
						
								/ <a href="Files?nodeServerEndpoint=<%=request.getParameter("nodeServerEndpoint")%>&brID=<%=request.getParameter("brID")%>&path=<%=path%>"><%=item %></a>
						
						 <% } %>
			
						 </th>
					</tr>
					<tr>
						<td> 	
							<ul style="list-style-type: none;">
								<li style="padding:0px; margin-left:5px;  float: left;">size: <%=ConnectionHandler.processSize(flb.getMainDir().getSize(),2) %>
								<li style="margin-left:15px; float: left;">items: <%= flb.getMainDir().getDirCount()+flb.getMainDir().getFileCount() %>
								
							</ul>
						</td>
					</tr>
				</thead>
			</table>
			</div>
			
			<div id="stat" style=" margin-left:15px; float: left;  width: 300px;" >
				<table class="table table-default">
				<thead>
					<tr>
						<th><%=request.getParameter("nodeServerEndpoint")%>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Files count:
						<td><%=flb.getFileCount() %>
					</tr>
					<tr>
						<td>Total size:
						<td><%= ConnectionHandler.getInstance().processSize(flb.getSize(),2) %>
					</tr>
					<tr>
						<td><%= flb.getDate() %>
					</tr>
					</tbody>
				</table>
			</div>
		</div>

	</div>

	<div style="width: 990px; margin-left: auto; margin-right: auto;">
		<ul style="border-top-width: 1px; border-top-style:solid; border-top-color: #DDD; padding-left: 0px; list-style-type: none;">
		<% if (flb.getMainDir().getParent()!=null) { %>
		<li style="height: 35px;">
				<div style="float: left; padding: 5px;">
					<img style="width: 25px;" alt="" src="style/folder.jpg">
				</div>
				<div style="width: 300px; float: left; padding: 5px;"><a href="Files?nodeServerEndpoint=<%=request.getParameter("nodeServerEndpoint")%>&brID=<%=request.getParameter("brID")%>&path=<%=flb.getMainDir().getParent().getFullPath()%>"> .. </a>  </div>
				<div style="padding-top:5px; text-align:center; width: 190px; float: left;">document</div>
				<div style="padding-top:5px; text-align:center;width: 200px; float: left;">65 K</div>
				<div style="padding-top:5px; width: 200px; float:left;">
					<div style="width:130px; margin-left: auto; margin-right: auto;"> 
					<button style="float: left;"><img alt="fuck off" style="width: 20px;" src="style/download.png"></button>
					<button style="float: left;"><img alt="" style="width: 20px;" src="style/file_delete.png"></button>
					<button data-toggle="modal" data-target="#sessionInfoModal" style="float: left;"><img alt="" style="width: 20px;" src="style/info_btn.png"></button>
					</div>
				</div>
			</li> 
			<% } %>
		<% 
			for (DirectoryBean dir : flb.getMainDir().getDirs()) {
		%>
			<li style="height: 35px;">
				<div style="float: left; padding: 5px;">
					<img style="width: 25px;" alt="" src="style/folder.jpg">
				</div>
				<div style="width: 300px; float: left; padding: 5px;"><a href="Files?nodeServerEndpoint=<%=request.getParameter("nodeServerEndpoint")%>&brID=<%=request.getParameter("brID")%>&path=<%=dir.getFullPath()%>"> <%=dir.getName()%> </a>  </div>
				<div style="padding-top:5px; text-align:center; width: 190px; float: left;">folder</div>
				<div style="padding-top:5px; text-align:center;width: 200px; float: left;"><%=ConnectionHandler.processSize(dir.getSize(),2) %></div>
				<div style="padding-top:5px; width: 200px; float:left;">
					<div style="width:130px; margin-left: auto; margin-right: auto;"> 
					<button style="float: left;"><img alt="fuck off" style="width: 20px;" src="style/download.png"></button>
					<button onclick="fileDelete('<%= dir.getFullPath()%>')" style="float: left;"><img alt="" style="width: 20px;" src="style/file_delete.png"></button>
					</div>
				</div>
			</li> 
			<%
				}
			%>
			<%
				for (FileBean f : flb.getMainDir().getFiles()) {
			%>
			<li onmouseleave="onItemMouseLeave(event)" onmouseenter="onItemMouseEnter(event);" style=" height: 35px;">
			
				<div style="float: left; padding: 5px;">
					<img style="width: 25px;" alt="" src="style/file_icon.jpg">
				</div>
				<div style="width: 300px; float: left; padding: 5px;"><a href="StreamTest?nodeServerEndpoint=<%=request.getParameter("nodeServerEndpoint")%>&brID=<%=request.getParameter("brID")%>&path=<%=f.getFullPath()%>&fileName=<%=f.getName()%>"><%=f.getName() %></a></div>
				<div style="padding-top:5px; text-align:center; width: 190px; float: left;">document</div>
				<div style="padding-top:5px; text-align:center;width: 200px; float: left;"><%= ConnectionHandler.processSize(f.getSize(),2)%></div>
				<div style="padding-top:5px; width: 200px; float:left;">
					<div style="width:130px; margin-left: auto; margin-right: auto;"> 
					<button style="float: left;"><img alt="fuck off" style="width: 20px;" src="style/download.png"></button>
					<button onclick="fileDelete('<%=f.getFullPath() %>');" style="float: left;"><img alt="" style="width: 20px;" src="style/file_delete.png"></button>
					<% SessionBean s = f.getSession();
						ConnectionHandler ch = ConnectionHandler.getInstance();
					%>
					<button onclick="showSessionModal('<%=f.getName() %>','<%=s.getSrcIP()%>','<%=s.getDstIP() %>','<%=s.getSrcP() %>','<%=s.getDstP() %>','<%= ch.processSize(s.getSrcDataLen(),2) %>','<%= ch.processSize(s.getDstDataLen(),2) %>','<%=s.getEstablished()%>');" data-target="#sessionInfoModal" data-toggle="modal"  style="float: left;"><img alt="" style="width: 20px;" src="style/info_btn.png"></button>
					</div>
				</div>
			</li> 
			<% } %>
			<li style=" height: 35px; background-color: #EEf0f3">
				<div style="float: left; padding: 5px;">
					<img style="width: 25px;" alt="" src="style/folder.jpg">
				</div>
				<div style="width: 300px; float: left; padding: 5px;">file1.jsp</div>
				<div style="padding-top:5px; text-align:center; width: 190px; float: left;">document</div>
				<div style="padding-top:5px; text-align:center;width: 200px; float: left;">65 K</div>
				<div style="padding-top:5px; width: 200px; float:left;">
					<div style="width:130px; margin-left: auto; margin-right: auto;"> 
					<button style="float: left;"><img alt="fuck off" style="width: 20px;" src="style/download.png"></button>
					<button  style="float: left;"><img alt="" style="width: 20px;" src="style/file_delete.png"></button>
					<button style="float: left;"><img alt="" style="width: 20px;" src="style/info_btn.png"></button>
					</div>
				</div>
			</li> 
		</ul>
	</div>

<div id="sessionInfoModal" class="modal fade">
  <div style=" width: 450px;"  class="modal-dialog">
    <div  class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Session info</h4>
      </div>
      <div style="height: 400px;" class="modal-body">
      <div id="modalFileName" style="margin-bottom: 0px; height: 35px; text-align: center;"  class="panel panel-default">
      	
      		fileName.jpg
      </div>
     
      
     
      <div style="margin-bottom: 0px;" class="panel panel-default" style="height: 40px;">
       <table>
      	<tr>
      		<td style="width: 170px; text-align: center;">SRC
      		<td><img style="width: 70px; height: 30px;"  src="style/arrow.png"></img>
      		<td style="width: 170px; text-align: center;">DST
      	</tr>
      </table>
  </div>
      		
     
      <div class="panel panel-default" style="width: 180px;  float: left;">
      	<table class="table table-default">
      		<tr>
      			<td id="srcIP" style="text-align: center;"> 172.16.0.1
      		</tr>
      		<tr>
      			<td id="srcPort" style="text-align: center;"> 4573
      		</tr>
      		<tr>
      			<td id="srcDataLen" style="text-align: center;"> 80 M
      		</tr>
      	</table>
      </div>
      
      <div class="panel panel-default" style="width: 55px;  float: left;">
      	<table class="table table-default">
      		<tr>
      			<td style="text-align: center; background-color: #65B6DB;"> IP
      		</tr>
      		<tr>
      			<td style="text-align: center; background-color: #65B6DB;"> Port
      		</tr>
      		<tr>
      			<td style="text-align: center; background-color: #65B6DB; padding: 2px; font-size: 12px;"> Data <br> count
      		</tr>
      	</table>
      </div>
      
    <div class="panel panel-default" style="width: 180px; float: left;">
      	<table class="table table-default">
      		<tr>
      			<td id="dstIP" style="text-align: center;"> 172.16.0.1
      		</tr>
      		<tr>
      			<td id="dstPort" style="text-align: center;"> 4573
      		</tr>
      		<tr>
      			<td id="dstDataLen" style="text-align: center;"> 80 M
      		</tr>
      	</table>
      	
      </div>
      <div id="timeStamp" class="panel panel-default">
      	
      </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>