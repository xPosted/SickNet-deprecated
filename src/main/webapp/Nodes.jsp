<!DOCTYPE html>
<%@page import="com.jubaka.sors.appserver.serverSide.ConnectionHandler"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jubaka.sors.appserver.beans.InfoBean"%>
<%@page import="java.util.Set"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script src="style/js/jquery-2.1.4.js"></script>
<link rel="stylesheet" href="style/css/bootstrap.css">
<link href="style/css/carousel.css" rel="stylesheet">
<script src="style/js/bootstrap.min.js"></script>
<script type="text/javascript">
function mouseEnter(event) {

	event.target.style.background = "#d9e1f3";

	
}
function mouseLeave(event) {

	event.target.style.background = "";
	
}

function init() {
	$("#pathToFile").change(fileSelect);
	var mainTable = document.getElementById("mainTable");
	var rows = mainTable.rows;
	for (i=0;i<rows.length; i++) {
		rows[i].onclick=function(event) {
			onRowClick(event);
		};
	}
}
function onRowClick(event) {
	
	
	var row = event.target.parentNode;
	var cells = row.cells;
	var a = cells[0].getElementsByTagName("a");
	var owner = cells[7].innerHTML;
	var arch = cells[8].innerHTML;
	var loaded = cells[9].innerHTML;
	document.getElementById("nodeName").innerHTML=a[0].innerHTML;
	document.getElementById("nodeOwner").innerHTML=owner;
	document.getElementById("nodeArch").innerHTML=arch;
	document.getElementById("nodeLoaded").innerHTML=loaded;
	document.getElementById("uploadNodeName").innerHTML=a[0].innerHTML;

}

	function showCloseIcon(event) {
		var icon = document.getElementById(event.target.id + "_closeIcon");
		icon.style.visibility = "visible";

	}

	function hideCloseIcon(event) {
		var icon = document.getElementById(event.target.id + "_closeIcon");
		icon.style.visibility = "hidden";

	}

	function closeCol(event) {
		
		var icon_id = event.target.id;
		var mas = icon_id.split("_");
	
		var thid = mas[0];
		var th = document.getElementById(thid);
		var elems = document.getElementsByClassName(thid+"_data");
		
		th.style.display="none";
		for (var i=0;i<elems.length;i++) {
			elems[i].style.display="none";
		}
		
		
		
		
	}
	function fileSelect() {
		var selectedFile = document.getElementById("pathToFile");
		var selFileNameView = document.getElementById("selFileNameView");
		selFileNameView.innerHTML=selectedFile.value;
		document.getElementById("uploadBtn").className="btn btn-primary";
		
	}

	function uploadComplete(evt) {
	        /* This event is raised when the server send back a response */
	        alert(evt.target.responseText);
	      }

	      function uploadFailed(evt) {
	        alert("There was an error attempting to upload the file.");
	      }

	      function uploadCanceled(evt) {
	        alert("The upload has been canceled by the user or the browser dropped the connection.");
	      }
	      
	      
	function uploadProgress(evt) {
		var one = parseInt(evt.total / 100);
		var status = parseInt(evt.loaded / one);
		document.getElementById("uploadProgress").style.width=status+'%';
		document.getElementById("uploadProgress").innerHTML=status+'%';
	    //   alert("Progress");
	      }
	      
	      
	function upload() {
//		window.alert("upload");
//		document.forms["uploadForm"].submit();
		
		var nodeName = document.getElementById("nodeName").innerHTML;
		var owner = document.getElementById("owner").innerHTML;
		document.getElementById("uploadProgress").style.width='0%';
		document.getElementById("uploadProgress").innerHTML='0%';
		 var fd = new FormData();
	        fd.append("pathToFile", document.getElementById('pathToFile').files[0]);
	        var xhr = new XMLHttpRequest();
	        xhr.upload.addEventListener("progress", uploadProgress, false);
	        xhr.addEventListener("load", uploadComplete, false);
	        xhr.addEventListener("error", uploadFailed, false);
	        xhr.addEventListener("abort", uploadCanceled, false);
	        xhr.open("POST", "Upload?nodeName="+owner+"@"+nodeName);
	        xhr.send(fd);
	        
		
	}
</script>

<style type="text/css">
.mainLogo_signed {
	position: relative;
	left: 925px;
	width: 110px;
	padding: 0;
	margin: 0;
	z-index: 9;
}
.nodeTable {
	width: 100%;
}

.closeIcon {
	visibility: hidden;
	border-radius: 50; 
	border: 0;
}

table.nodeTable td {
	text-align: center;
	padding: 10px;
	border-bottom: 1px solid black;
}

table.nodeTable th {
	text-align: center;
	padding: 10px;
}
</style>


<title>SORS processing</title>
</head>
<body onload="init();" class="bodyStyle">
<%
	Set<InfoBean> ibset =(Set<InfoBean>) request.getAttribute("InfoBeanSet");
%>

	<jsp:directive.include file="MainMenu.jsp" /> <!-- add main menu -->

	<div style="margin-top:95px; width: 100%;">
		<div style="margin-left: auto; margin-right: auto; width: 1000px;"
			class="panel panel-default">
			<div style="padding-top: 0px; padding-bottom: 0px;" class="panel-body">

				
							<div class="input-group" style="margin-top:7px; width:300px; float:left;">
								<input type="text" class="form-control"> <span
									class="input-group-btn">
									<button style="height: 34px;" class="btn btn-default"
										type="button">Search</button>
								</span>
							</div>
						
							<ul  style="display:block; text-decoration:none; float:left; margin-left: 280px; margin-bottom: 0px;" >
							<% 
							String pub = request.getParameter("public");
							if (pub==null) pub="false";
							if (pub.equalsIgnoreCase("true")) {
							%>
								<li  style="display:block; float: left; background-color: gray;"><a href="Nodes?public=true" style="text-decoration: none; display: block; padding:15px; color: graytext;">Public</a></li>
								<li  style="display:block; float: left;"><a href="Nodes?public=false" style="text-decoration: none; display: block; padding:15px; color: graytext;">Own</a> </li>
							
							<% } else { %>
								<li style="display:block; float: left;"><a href="Nodes?public=true" style="text-decoration: none; display: block; padding:15px; color: graytext;">Public</a></li>
								<li style="display:block; float: left; background-color: gray;"><a href="Nodes?public=false" style="text-decoration: none; display: block; padding:15px; color: graytext;">Own</a> </li>
							<% } %>
							</ul>
						
							<!-- /input-group -->
							<div style=" margin-top:7px; position:relative; margin-left:20px; float:left;">
								<label style="display: inline-block; font-family: sans-serif; font-size: 13pt;"
									class="label label-default">SORT by</label>
								<button type="button" style="display: inline-block;"
									class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									btnValue<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">

									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li class="divider"></li>
									<li><a href="#">Separated link</a></li>

								</ul>
							</div>
			
			</div>
		</div>

<div class="panel panel-default" style="margin-left:140px; position: fixed; width: 220px; float: left; height: 250px;">	
<div class="panel-heading" style="text-align: center;" id="nodeName"> [Node name]</div>
<div class="panel-body">
<table style="width: 180px;" class="table table-default">
<tr>
	<th> Owner:
	<td id="nodeOwner"> 
</tr>
<tr>
	<th>Arch:
	<td id="nodeArch"> 
</tr>
<tr>
	<th>Loaded cnt:
	<td id="nodeLoaded">
</tr>
</table>

<button data-toggle="modal" data-target="#uploadModal" style="margin-left: 120px;"  class="btn btn-default">Upload</button>
</div>
</div>

		<div style="width: 770px; margin-left: 365px; margin-right: auto;">
			<table id="mainTable" class="nodeTable table table-default">
				<caption>Available nodeServerEndpoints</caption>
				<thead>
					<tr>
						<th id="col1" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div  style="text-align: right;">
								<button id="col1_closeIcon" onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col1_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>Node name
						<th id="col3" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div style="text-align: right;">
								<button id="col3_closeIcon"  onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col3_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>Status
						<th id="col4" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div style="text-align: right;">
								<button id="col4_closeIcon"  onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col4_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>Associated date
						
						<th id="col7" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div style="text-align: right;">
								<button id="col7_closeIcon"  onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col7_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>CPU count
						<th id="col8" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div style="text-align: right;">
								<button id="col8_closeIcon"  onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col8_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>Mem. count
						<th id="col9" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div style="text-align: right;">
								<button id="col9_closeIcon"  onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col9_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>Live capture
						<th id="col10" onmouseleave="hideCloseIcon(event);"
							onmouseenter="showCloseIcon(event);">
							<div style="text-align: right;">
								<button id="col10_closeIcon"  onclick="closeCol(event);" type="button"
									class="closeIcon btn-default">
									<span id="col10_closeIcon_glyph" style="display: block;"
										class="glyphicon glyphicon-remove-circle"></span>
								</button>
							</div>Avalilable space
					</tr>
				</thead>
				<tbody>
						<%
							
							for (InfoBean ib : ibset) {
						 %>
						 
					<tr onmouseenter="mouseEnter(event);" onmouseleave="mouseLeave(event);">
						<div > 
							<td class="col1_data"><a href="NodeView?nodeName=<%=ib.getOwner()+"@"+ib.getNodeName()%>"><%=ib.getNodeName()%></a>
						<td class="col3_data"><%
						if (ib.getStatus()==-1) out.println("Live only"); 
						if (ib.getStatus()==0) out.println("Remote only");
						if (ib.getStatus()==1) out.println("Both");
						 %>
						<td class="col4_data"><% SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
						String date = sdf.format(ib.getConnectedDate());
						out.println(date);
						 %>
						<td class="col7_data"><%=ib.getProcCount()%>
						<td class="col8_data"><% 
						
						String maxMemView = ConnectionHandler.processSize(ib.getMaxMem(),2);
						out.println(maxMemView); %></td>
						<td class="col9_data">Delete this fld
						<td class="col10_data">  <%  
						Double homeAvailable = ib.getHomeMax()-ib.getHomeUsed();
						String strSizeView = ConnectionHandler.processSize(homeAvailable,2);
						out.println(strSizeView);
						
						 %></td>
						 <td style="display: none;"><%=ib.getOwner() %> </td>
						 <td style="display: none;"><%=ib.getOsArch() %> </td>
						 <td style="display: none;"><%=ib.getCurrentDumpCount() %> </td>
						</div>
						
						
					</tr>
						 
						 <% } %>
					
				</tbody>

			</table>
		</div>

	</div>
	
	
		<!-- modalUpload -->
	<div style="width: 100%; border: 2px solid black;" class="modal fade"
			id="uploadModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div style="width: 650px;" class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="uploadModalTitle">uploading to <font style="font-style: italic;" id="uploadNodeName"></font> </h4>
					</div>
					<div class="modal-body">
						<form action="Upload" id="uploadForm" enctype="multipart/form-data" name="uploadForm" method="post" >
							<input style="display: none; float: left;" type="file" id="pathToFile" name="pathToFile" class="">
						</form>
						
						<button style="margin-bottom:5px; width:20%; margin-left: 10px; float: left;" class="btn btn-default" onclick="document.getElementById('pathToFile').click();">Choose file</button>
						<div style="width: 75%; float: left; " align="right"> <font id="selFileNameView">No file selected</font></div>
						
						TCPana
						<table class="table table-default">
							<tr>
								<th>Branch name
								<td><input type="text" class="form-control" placeholder="Enter branch name here..." aria-describedby="basic-addon1">
							</tr>
							<tr>
								
							</tr>
						</table>
						
						<div class="progress">
							<div id ="uploadProgress" name="uploadProgress" class="progress-bar" role="progressbar" aria-valuenow="4"
								aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">
								0%</div>
								
						</div>
						
						<div class="modal-footer">
						<div
							align="left" style="float: left; position: relative; display: inline-block; padding: 0; margin-bottom: 5px;">
							<input type="checkbox">
							Start processing after upload 

						</div>
						<div style="margin-left: 41%; float: left;" align="right" >
								<button   type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button  id="uploadBtn" type="button" class="disabled btn btn-primary" onclick="upload()">Upload</button>
						</div>
							
						</div>
						
					</div>
				</div>
			</div>
		</div>
	
	
	
</body>
</html>