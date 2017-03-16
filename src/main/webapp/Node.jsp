<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="com.jubaka.sors.appserver.beans.branch.BranchStatBean"%>
<%@page import="com.jubaka.sors.appserver.beans.SecPolicy"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jubaka.sors.appserver.serverSide.ConnectionHandler"%>
<%@page import="com.jubaka.sors.appserver.beans.InfoBean"%>
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

<script type="text/javascript">
	function onIfaceClick(evt) {
		var ifsBtn = document.getElementById("ifsBtn");
		ifsBtn.innerHTML = evt.target.innerHTML+" <span class=\"caret\">";
		document.getElementById("uploadBtn").className = "btn btn-primary";
		$("#ifsBtn").attr("value",evt.target.innerHTML);
	}

	function onBodyLoad() {
		$("#pathToFile").change(fileSelect);
	}
	function fileSelect() {
	//	var selectedFile = document.getElementById("pathToFile");
		document.getElementById("uploadBtn").className = "btn btn-primary";

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
		document.getElementById("uploadProgress").style.width = status + '%';
		document.getElementById("uploadProgress").innerHTML = status + '%';
		//   alert("Progress");
	}

	function rBranchSrcClick(evt) {
		if (evt.target.id == "ifsBranchSrc") {
			$("#fileSelectBtn").attr('disabled',true);
			$("#ifsDropDownDiv :button").attr('disabled',false);
			
		}
		if (evt.target.id == "fsBranchSrc") {
			$("#ifsDropDownDiv :button").attr('disabled',true);
			$("#fileSelectBtn").attr('disabled',false);
		};
	}
	function upload() {
		//	window.alert("upload");
		//	document.forms["uploadForm"].submit();
		
		var branchName = document.getElementById("txtBranchName").value;
		var nodeName = document.getElementById("nodeName").innerHTML;
		var owner = document.getElementById("owner").innerHTML;
			document.getElementById("uploadProgress").style.width = '0%';
			document.getElementById("uploadProgress").innerHTML = '0%';
		
		var ifsBranchSrc = document.getElementById("ifsBranchSrc");
		var fsBranchSrc = document.getElementById("fsBranchSrc");
		
		
		if (ifsBranchSrc.checked) {
			
			var ifsBtn = document.getElementById("ifsBtn");
			var fd = new FormData();
			var xhr = new XMLHttpRequest();
			xhr.upload.addEventListener("progress", uploadProgress, false);
			xhr.addEventListener("load", uploadComplete, false);
			xhr.addEventListener("error", uploadFailed, false);
			xhr.addEventListener("abort", uploadCanceled, false);
			xhr.open("POST", "CreateBranch?nodeName=" + owner + "@" + nodeName+"&iface="+ifsBtn.value+"&bname="+branchName);
			xhr.send(fd);
		}
		if (fsBranchSrc.checked) {
			alert("fs");
			
			var fd = new FormData();
			fd.append("pathToFile", document.getElementById('pathToFile').files[0]);
			var xhr = new XMLHttpRequest();
			xhr.upload.addEventListener("progress", uploadProgress, false);
			xhr.addEventListener("load", uploadComplete, false);
			xhr.addEventListener("error", uploadFailed, false);
			xhr.addEventListener("abort", uploadCanceled, false);
			xhr.open("POST", "CreateBranch?nodeName=" + owner + "@" + nodeName);
			xhr.send(fd);
		}
		

	}
</script>

</head>
<body onload="onBodyLoad();">

	<%
		Object ibObj = request.getAttribute("infoBean");
		InfoBean ib = null;
		if ((ibObj != null) || (ibObj instanceof InfoBean)) {
			ib = (InfoBean) ibObj;
		}
		Object spObj = request.getAttribute("secPolicy");
		SecPolicy sp = null;
		if (spObj instanceof SecPolicy) {
			sp = (SecPolicy) spObj;
		}
		Object ifsObj = request.getAttribute("ifs");
		List<String> ifs = null;
		if (ifsObj instanceof List<?>) {
			ifs = (List<String>) ifsObj;
		}
	%>

	<jsp:directive.include file="MainMenu.jsp" /> <!-- add main menu -->
	
	<div style="margin-top: 95px; width: 100%;">
		<div style="width: 1000px; margin-left: auto; margin-right: auto;">
			<div style="border-radius: 10;" class="panel panel-default">
				<div class="panel-body">
					<h2 id="nodeName" style="float: left;"><%=ib.getNodeName()%></h2>
					<div align="right">
						<span
							style="margin-top: 5px; margin-bottom: px; display: inline-block; position: relative;">

							<button
								class="btn btn-default <%if (sp.isDenyLoad())
				out.println("disabled");%>"
								data-toggle="modal" data-target="#uploadModal">
								<h4>Upload</h4>
							</button> <a href="BranchList">
								<button class="btn btn-default">
									<h4>View dumps</h4>
								</button>
						</a>
						</span>
					</div>
				</div>
			</div>
		</div>


		<!-- modal create branch -->
		<div style="width: 100%; border: 2px solid black;" class="modal fade"
			id="uploadModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div style="width: 650px;" class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Modal title</h4>
					</div>
					<div class="modal-body">
						<form action="/TCPanalyze/Upload" id="uploadForm"
							enctype="multipart/form-data" name="uploadForm" method="post">
							<input style="display: none; float: left;" type="file"
								id="pathToFile" name="pathToFile" class="">
						</form>

						<table class="table table-default">
							<tr>
								<td rowspan="2" style="width: 300px;"><input id="txtBranchName" type="text"
									class="form-control" placeholder="Enter branch name here..."
									aria-describedby="basic-addon1">
								<td><input type="radio" id="ifsBranchSrc" class="collapsed" data-toggle="collapse" data-target="#progressWell" aria-expanded="false" aria-controls="progressWell"
									onclick="rBranchSrcClick(event)" name="rBranchSrc"
									style="float: left; width: 35px; margin: 5px;" aria-label="...">

									<!-- Split button -->
									<div class="btn-group" id="ifsDropDownDiv" style=" margin-left:10px; width: 80%;">
										<button id="ifsBtn" type="button" style="width: 100%;" class="btn btn-default dropdown-toggle"
											data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											[iface name] <span class="caret"></span>
										</button>
										<ul class="dropdown-menu">
										<% if (ifs!=null)
											for (String dev : ifs) {
											%>
											<li ><a onclick="onIfaceClick(event)"><%= dev %></a></li>
										<%  } %>
										</ul>
									</div></td>

							</tr>
							<tr>
								<td><input type="radio" id="fsBranchSrc" data-toggle="collapse" class="" data-target="#progressWell" aria-expanded="false" aria-controls="progressWell"
									onclick="rBranchSrcClick(event)"
									style="float: left; width: 35px; margin: 5px;"
									name="rBranchSrc" aria-label="...">
									<button
										style="margin-bottom: 5px; width: 80%; margin-left: 10px; float: left;" id="fileSelectBtn" 
										class="btn btn-default"
										onclick="document.getElementById('pathToFile').click();">Choose
										file</button>
								</td>
							</tr>

						</table>
						
						
						<div class="progress collapse in" id="progressWell">
							<div id="uploadProgress" name="uploadProgress"
								class="progress-bar" role="progressbar" aria-valuenow="4"
								aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">
								0%</div>

						</div>
						<div
							style="position: relative; display: inline-block; padding: 0; margin-bottom: 5px;">
							<input type="checkbox"> Start processing after upload

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button id="uploadBtn" type="button" data-dismiss="modal"
								class="disabled btn btn-primary" onclick="upload()">Create</button>
						</div>

					</div>
				</div>
			</div>
		</div>



		<div style="width: 1000px; margin-left: auto; margin-right: auto;">
			<div style="display: inline-block; width: 500px; float: left;">
				<table style="" class="tableNodeProp table">
					<caption>NODE Properties</caption>

					<tbody>
						<tr>
							<th>IP</th>
							<td><%=ib.getPubAddr().getHostName()%></td>
						</tr>
						<tr>
							<th>Node owner</th>
							<td id="owner"><%=ib.getOwner()%></td>
						</tr>
						<tr>
							<th>Associated Date</th>
							<td>
								<%
									SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
									out.println(sdf.format(ib.getConnectedDate()));
								%>
							</td>
						</tr>
						<tr>
							<th>Active</th>
							<td>True</td>
						</tr>

						<tr>
							<th>Maximum dump size</th>
							<td>
								<%
									String size = ConnectionHandler.processSize(sp.getDumpSize(),2);
									out.println(size);
								%>
							</td>
						</tr>
						<tr>
							<th>CPU count</th>
							<td><%=ib.getProcCount()%></td>
						</tr>
						<tr>
							<th>Memory count</th>
							<td>
								<%
									size = ConnectionHandler.processSize(ib.getMaxMem(),2);
									out.println(size);
								%>
							</td>
						</tr>
						<tr>
							<th>Status</th>
							<td>
								<%
									if (ib.getStatus() == -1)
										out.println("Live only");
									if (ib.getStatus() == 0)
										out.println("Remote only");
									if (ib.getStatus() == 1)
										out.println("Both");
								%>
							</td>
						</tr>
						<tr>
							<th>Disk space total</th>
							<td><%=ConnectionHandler.processSize(ib.getHomeMax(),2)%></td>
						</tr>
						<tr>
							<th>Disk space available</th>
							<td><%=ConnectionHandler.processSize(ib.getHomeMax()
					- ib.getHomeUsed(),2)%></td>
						</tr>

					</tbody>
				</table>

				<div class="panel panel-default">
					<div class="panel-body">
						<h3 style="text-align: center;">IP history</h3>
						<ul class="list-group">

							<li style="border: 0; padding: 0;" class="list-group-item"><button
									class="btn btn-default btn-lg btn-block">btn</button></li>
							<li style="border: 0; padding: 0;" class="list-group-item"><button
									class="btn btn-default btn-lg btn-block">btn</button></li>

						</ul>

					</div>
				</div>
			</div>
			<div
				style="margin-left: 20px; display: inline-block; float: left; width: 400px;">
				<div>
					<table class="statNodeTab table table-bordered">
						<%
							BranchStatBean bsb = (BranchStatBean) request
									.getAttribute("branchStat");
						%>

						<tr>
							<th>Total branch processed</th>
							<td><%=bsb.getBranchProcessed()%></td>
						</tr>
						<tr>
							<th>Total branch length</th>
							<td>
								<%
									double len = bsb.getTotalBranchLen();
									String lenStr = ConnectionHandler.processSize(len,2);
									out.println(lenStr);
								%>
							</td>
						</tr>
						<tr>
							<th>Available branch count</th>
							<td><%=bsb.getAvailableBranchCount()%></td>
						</tr>
						<tr>
							<th>Available branch length</th>
							<td>
								<%
									len = bsb.getAvailableBranchLen();
									lenStr = ConnectionHandler.processSize(len,2);
									out.println(lenStr);
								%>
							</td>
						</tr>
						<tr>
							<th>Branch loaded by user</th>
							<td><%=bsb.getAvailableBranchByUser()%></td>
						</tr>
						<tr>
							<th>Branch loaded by user length</th>
							<td>
								<%
									len = bsb.getAvailableBranchByUserLen();
									lenStr = ConnectionHandler.processSize(len,2);
									out.println(lenStr);
								%>
							</td>
						</tr>


						<tr>
							<th>OS/arch</th>
							<td><%=ib.getOsArch()%></td>
						</tr>
						<tr>
							<th>Description</th>
							<td style="text-align: justify;"><%=ib.getDesc()%></td>
						</tr>

					</table>
					<button class="btn btn-default" type="button"
						data-toggle="collapse" data-target="#permissions"
						aria-expanded="false" aria-controls="permissions"
						style="width: 100%; display: block;">permissions</button>
					<div class="collapse" id="permissions">
						<div>
							<table class="table table-default">

								<tbody>
									<tr>
										<td>Home size limit
										<td><%=ConnectionHandler.processSize(sp.getHomeMax(),2)%>
									</tr>
									<tr>
										<td>Dump size limit
										<td><%=ConnectionHandler.processSize(sp.getDumpSize(),2)%>
									</tr>
									<tr>
										<td>Dump count limit
										<td><%=sp.getDumpCountLim()%>
									</tr>
									<tr>
										<td>Dump processing
										<td>
											<%
												if (sp.isDenyLoad())
													out.println("OFF");
												else
													out.println("ON");
											%>
										
									</tr>
									<tr>
										<td>View live capture
										<td>
											<%
												if (sp.isDenyViewLive())
													out.println("OFF");
												else
													out.println("ON");
											%>
										
									</tr>
									<tr>
										<td>View third-party dumps
										<td>
											<%
												if (sp.isDenyViewThird())
													out.println("OFF");
												else
													out.println("ON");
											%>
										
									</tr>


								</tbody>

							</table>
						</div>
					</div>
				</div>

			</div>


		</div>



	</div>

</body>



</html>