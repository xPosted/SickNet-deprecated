<!DOCTYPE html>
<%@page import="com.jubaka.sors.appserver.serverSide.ConnectionHandler"%>
<%@page import="com.jubaka.sors.beans.InfoBean"%>
<%@page import="com.jubaka.sors.beans.branch.BranchInfoBean"%>
<%@page import="com.jubaka.sors.beans.branch.IPItemBean"%>
<%@page import="com.jubaka.sors.beans.branch.SubnetBean"%>
<%@page import="com.jubaka.sors.beans.branch.SubnetBeanList"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>


<link href="style/css/carousel.css" rel="stylesheet">
<link rel="stylesheet" href="style/css/bootstrap.css">
<link rel="stylesheet" href="style/jstree/themes/default/style.min.css" />

<script src="style/js/jquery-2.1.4.js"></script>
<script src="style/jstree/jstree.js"></script>
<script src="style/js/bootstrap.min.js"></script>



<style type="text/css">
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
var currentObj;
	function set() {
		var in_any = document.getElementById("in_any");
		var in_list = document.getElementById("in_list");
		var in_list_text = document.getElementById("in_list_text");
		
		var out_any = document.getElementById("out_any");
		var out_list = document.getElementById("out_list");
		var out_list_text = document.getElementById("out_list_text");
		
		if (in_any.disabled==true) in_any.disabled=false;
		if (in_list.disabled==true) in_list.disabled=false;
		if (in_list_text.disabled==true) in_list_text.disabled=false;
		
		if (out_any.disabled==true) out_any.disabled=false;
		if (out_list.disabled==true) out_list.disabled=false;
		if (out_list_text.disabled==true) out_list_text.disabled=false;
		
	}
	
	function branchAction(command) {
		
		var selectedAddr = currentObj;
		$.ajax({

			// The URL for the request
			url : "BranchOptions",

			// The data to send (will be converted to a query string)
			data : {
				branchId : <%=request.getParameter("branchId")%>,
				nodeName : document.getElementById("nodename").innerHTML.trim(),
				action : command,
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
			//	alert("The request is complete!");
				if (command=="start") {
					$("#stateImg").attr("src","style/processing.gif");
					$("#stateImg").width("200px");
				}
				if (command=="stop") {
					$("#stateImg").attr("src","style/complete.jpg");
					$("#stateImg").width("200px");
				}
			}
		});
	}
	
	function applyChanges() {
		var inP;
		if (document.getElementById("in_any").checked==true) {
			inP="-1";
		}
		if (document.getElementById("in_list").checked==true) {
			inP=document.getElementById("in_list_text").value;
		}
		alert(inP);
		
		var outP;
		if (document.getElementById("out_any").checked==true) {
			outP="-1";
		}
		if (document.getElementById("out_list").checked==true) {
			outP=document.getElementById("out_list_text").value;
		}
		alert(outP);
		
		
		var selectedAddr = currentObj;
		$.ajax({

			// The URL for the request
			url : "BranchOptions",

			// The data to send (will be converted to a query string)
			data : {
				branchId : <%=request.getParameter("brid")%>,
				nodeName : document.getElementById("nodename").innerHTML.trim(),
				object : selectedAddr,
				inP : inP,
				outP : outP
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
	
	$(function() {
		$('#jstree_test').jstree();
		$('#jstree_test').on("select_node.jstree", function (e,data) {
			
			currentObj=data.instance.get_node(data.selected[0]).text;
			  aj(data.instance.get_node(data.selected[0]).text)
			  
			});
			 
	});
	
	function aj(selectedAddr) {
		
		$.ajax({

			// The URL for the request
			url : "BranchOptions",

			// The data to send (will be converted to a query string)
			data : {
				branchId : <%=request.getParameter("brid")%>,
				nodeName : document.getElementById("nodename").innerHTML.trim(),
				object : selectedAddr
				
			},

			// Whether this is a POST or GET request
			type : "GET",

			// The type of data we expect back
			dataType : "text",

			// Code to run if the request succeeds;
			// the response is passed to the function
			success : function(text) {
				if (text.indexOf("capture_not_set")!=-1) {
					document.getElementById("in_any").disabled=true;
					document.getElementById("in_list").disabled=true;
					document.getElementById("in_list_text").disabled=true;
					document.getElementById("out_any").disabled=true;
					document.getElementById("out_list").disabled=true;
					document.getElementById("out_list_text").disabled=true;
					
					return;
				}
				
				document.getElementById("in_any").disabled=false;
				document.getElementById("in_list").disabled=false;
				document.getElementById("in_list_text").disabled=false;
				document.getElementById("out_any").disabled=false;
				document.getElementById("out_list").disabled=false;
				document.getElementById("out_list_text").disabled=false;
				
				var splited = text.split("_");
				if (splited[0].indexOf("in")!=-1) {
					
					var inPorts = splited[0].substring(splited[0].indexOf("[")+1,splited[0].indexOf("]"));
					inPorts = inPorts.trim();
					if (inPorts.indexOf("-1")!=-1) {
						document.getElementById("in_any").checked=true;
						document.getElementById("in_list").checked=false;
						document.getElementById("in_list_text").disabled=true;
						alert("all sessions are captureing");
					} else {
						document.getElementById("in_any").checked=false;
						document.getElementById("in_list").checked=true;
						document.getElementById("in_list_text").disabled=false;
						document.getElementById("in_list_text").value=inPorts;
					}
					
				}
				
				if (splited[1].indexOf("out")!=-1) {
					
					var outPorts = splited[1].substring(splited[1].indexOf("[")+1,splited[1].indexOf("]"));
					outPorts = outPorts.trim();
					if (outPorts.indexOf("-1")!=-1) {
						document.getElementById("out_any").checked=true;
						document.getElementById("out_list").checked=false;
						document.getElementById("out_list_text").disabled=true;
						alert("all sessions are captureing");
					} else {
						document.getElementById("out_any").checked=false;
						document.getElementById("out_list").checked=true;
						document.getElementById("out_list_text").disabled=false;
						document.getElementById("out_list_text").value=outPorts;
					}
					
				}
				
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
				
			}
		});
	}
	function jquery_test(ip) {
		$.ajax({

			// The URL for the request
			url : "jquery_test",

			// The data to send (will be converted to a query string)
			data : {
				id : 123,
				ip : ip
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
	function add() {
		var list = document.getElementById("listGroup");
		var a = list.getElementsByTagName("a");
		for (i = 0; i < a.length; i++) {
			var clas = a.item(i).className;
			if (clas.contains("active")) {

				list.innerHTML = list.innerHTML
						+ "<a href=\"#\" class=\"list-group-item\">New elem</a>"
			}
		}
	}
	function removeElem() {

		var list = document.getElementById("listGroup");
		var a = list.getElementsByTagName("a");
		for (i = 0; i < a.length; i++) {
			var clas = a.item(i).className;
			alert(clas);
			if (clas.indexOf("active") != -1) {
				alert("active");
				var ip = a.item(i).innerHTML;
				alert(ip);
				jquery_test(ip);
				var child = a.item(i);
				list.removeChild(child);

			}
		}
	}
</script>
</head>
<body onload="onBodyLoad();">
<div style="display: none;">
	<label id="nodename"><%=request.getParameter("nodeName")%></label>
</div>
<%
	SubnetBeanList sbl = (SubnetBeanList) request.getAttribute("sbl");
	BranchInfoBean bib = (BranchInfoBean) request.getAttribute("bib");
	InfoBean ib = (InfoBean) request.getAttribute("ib");
%>

	<div class="navbar-wrapper">
		<div class="container">

			<nav
				style="margin-top: 5mm; z-index: 1; width: 1170px; position: absolute;"
				class="navbar navbar-inverse navbar-static-top" role="navigation">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target="#navbar"
							aria-expanded="false" aria-controls="navbar">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="sors.jsp">SORS</a>

					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
							<li><a href="BranchList">Processing</a></li>
							<li><a href="Nodes">Nodes</a></li>
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
	<div style="margin-top:95px; margin-left: auto; margin-right: auto; width: 60%;"
		class="panel panel-default">
		<div style="height: 45px;" class="panel-heading">
			<div align="left" style="width: 70%; float: left;">
				<a href='BranchView?branchId=<%=request.getParameter("branchId") %>&nodeName=<%=request.getParameter("nodeName") %>'> <%= bib.getBranchName() %> </a>  <img style="width: 30px;" src="style/mainLogo.png">
				<a href='NodeView?nodeName=<%= request.getParameter("nodeName")  %>'> <%= bib.getNodeName() %> </a> 
			</div>
			<div align="right" style="width: 30%; float: left;">Properties</div>
		</div>

	</div>
	<div class="panel"
		style="height: 340px; margin-left: auto; margin-right: auto; width: 60%;">
		<div style="height: 100%; float: none;" class="panel">
			<div style="height: 100%; width: 70%; float: left;">
				<table class="table table-default">
					<caption>Branch info</caption>
					<tbody>
						<tr>
							<th>Branch NAME</th>
							<td><%=bib.getBranchName() %> </td>
						</tr>
						<tr>
							<th>Branch Owner</th>
							<td><%= bib.getUserName()%></td>
						</tr>
						<tr>
							<th>Node</th>
							<td><%=ib.getNodeName() %> </td>
						</tr>
						<tr>
							<th>Creation date</th>
							<td><%= bib.getTime() %> </td>
						</tr>
						<tr>
						<% if (bib.getFileName() == null) {%>
							<th>Interface</th>
							<td><%=bib.getIface() %> </td>
							
							<% } else { %>
							<th>Dump size</th>
							<td><%=ConnectionHandler.processSize( bib.getUploadSize(),2) %> </td>
							<% } %>
						</tr>
						<tr>
							<th>Home size</th>
							<td> </td>
						</tr>
						<tr>
							<th>Captured data size</th>
							<td></td>
						</tr>
						<tr>
							<th>Home size</th>
							<td></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div style="height: 100%; width: 30%; float: left;"
				class="panel panel-default">
				<div style="height: 90px;">
				<% if (bib.getState()==0) { %>
					<img id="stateImg" style="width: 100px; float: left;" alt=""
						src="style/ready.jpg"> <font>Ready to start</font>
					<% }  %>
					<% if (bib.getState()==1) { %>
					<img id="stateImg" style="margin:10px; width: 200px; float: left;" alt=""
						src="style/processing.gif"> <font></font>
					<% }  %>
					<% if (bib.getState()==-1) { %>
					<img id="stateImg" style="margin:10px; width: 200px; float: left;" alt=""
						src="style/complete.jpg"> <font></font>
					<% }  %>
					
				</div>
				<div>
					<% if (bib.getState()==0) {%>
					<button style="display: block; width: 90%; margin: 5%;"
						 onclick="branchAction('start')" class="btn btn-default">Start processing</button>
					<% } if (bib.getState()==1) { %>
					<button style="display: block; width: 90%; margin: 5%;"
						 onclick="branchAction('stop')" class="btn btn-default">Stop processing</button>
						<% } %>
					
					<button style="display: block; width: 90%; margin: 5%;"
						class="btn btn-default">Delete captured data</button>
					<button style="display: block; width: 90%; margin: 5%;"
						class="btn btn-default">Delete branch</button>
				</div>
			</div>
		</div>

		<div class="panel">
			<font>Data capture</font>
			<hr style="margin-top: 5px; margin-bottom: 15px;" width="5px;">

			<table class="table table-default">
				<tr>
					<td style="width: 1150px; height: 400px; overflow-y: scroll;">
						<div id="jstree_test">
							<ul>
							<%	for (SubnetBean net : sbl.getNets() ) {%>
								<li><%= net.getSubnet().getHostName()  %>
									<ul>
										<% for (IPItemBean ipBean : net.getIps()) { %>
										<li><%= ipBean.getDnsName() %> </li>
										<% } %>
									</ul>
								</li>
								<% } %>
							</ul>
						</div>


						<button onclick="add();">add</button>
						<button onclick="removeElem();">remove</button>
					<!--	<button onclick="jquery_test();">jquery test</button> -->
					</td>
					<td>
						<div  style="width: 350px;" class="panel panel-default">
							<div align="center" class="panel-heading">Incoming session
							</div>
							<div class="panel-body">
								<div
									style="margin-left: 10px; margin-right: 10px; 5 px; margin-bottom: 10px;"
									class="row">
									<div style="width: 100%;"col-lg-6">
										<div class="input-group">
											<span class="input-group-addon"> <input
												id="in_any" type="checkbox" aria-label="..." disabled="disabled">
											</span> <font
												style="vertical-align: middle; text-align: center; display: table-cell; border: 1px solid #CCC;">any
												port</font>
										</div>
										<!-- /input-group -->
									</div>
									<!-- /.col-lg-6 -->
								</div>

								<div
									style="margin-left: 10px; margin-right: 10px; margin-top: 5px; margin-bottom: 10px;"
									class="row">
									<div style="width: 100%;"col-lg-6">
										<div class="input-group">
											<span class="input-group-addon"> <input
												id="in_list" type="checkbox" aria-label="..." disabled="disabled"> only for:
											</span> <input id="in_list_text" type="text" class="form-control" aria-label="..." disabled="disabled">
										</div>
										<!-- /input-group -->
									</div>
									<!-- /.col-lg-6 -->
								</div>

							</div>
						</div>
						
						<div style="width: 350px;" class="panel panel-default">
							<div align="center" class="panel-heading">Output session
							</div>
							<div class="panel-body">
								<div
									style="margin-left: 10px; margin-right: 10px; 5 px; margin-bottom: 10px;"
									class="row">
									<div style="width: 100%;"col-lg-6">
										<div class="input-group">
											<span class="input-group-addon"> <input
												id="out_any" type="checkbox" aria-label="..." disabled="disabled">
											</span> <font
												style="vertical-align: middle; text-align: center; display: table-cell; border: 1px solid #CCC;">any
												port</font>
										</div>
										<!-- /input-group -->
									</div>
									<!-- /.col-lg-6 -->
								</div>

								<div
									style="margin-left: 10px; margin-right: 10px; margin-top: 5px; margin-bottom: 10px;"
									class="row">
									<div style="width: 100%;"col-lg-6">
										<div class="input-group">
											<span class="input-group-addon"> <input
												id="out_list" type="checkbox" aria-label="..." disabled="disabled"> only for:
											</span> <input id="out_list_text" type="text" class="form-control" aria-label="..." disabled="disabled">
										</div>
										<!-- /input-group -->
									</div>
									<!-- /.col-lg-6 -->
								</div>

							</div>
						</div>
						<div style=" width:100%;">
							<button onclick="set();" id="setCap" style="margin-left:40%; margin-right:10px; float:left;"  class="btn btn-default" >Set</button>
							<button onclick="applyChanges();" id="saveCap" style="float:left;"  class="btn btn-default" >Save chages</button>
						</div>
					</td>
				</tr>
			</table>

		</div>
	</div>
	
	
	

</body>
</html>