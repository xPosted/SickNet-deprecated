<!DOCTYPE html>
<%@page import="com.jubaka.sors.tcpAnalyse.Controller"%>
<%@page import="java.util.HashSet"%>
<%@page import="com.jubaka.sors.serverSide.ConnectionHandler"%>
<%@page import="com.jubaka.sors.beans.branch.SessionBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jubaka.sors.beans.branch.IPItemBean"%>
<%@page import="com.jubaka.sors.beans.branch.SubnetBean"%>
<%@page import="com.jubaka.sors.beans.branch.BranchBean"%>
<%@page import="com.jubaka.sors.sessions.Branch"%>
<html style="height: 2369px;">
<head>


<script src="style/js/jquery-2.1.4.js"></script>
<link href="style/css/carousel.css" rel="stylesheet">

<style>
.ip {
	color: #555;
	display: none;
	height: 100%;
	width: 100%;
	padding: 3px 0px 0px 0px;
	text-align: center;
	text-decoration: none;
}

.dnsName {
	white-space: nowrap;
	overflow-y: hidden;
	color: #555;
	display: inline-block;
	height: 100%;
	width: 100%;
	padding: 3px 0px 8px 0px;
	text-align: center;
	text-decoration: none;
}

.ipOct {
	padding: 0px 0px 0px 0px;
	margin: 0px 0px 10px 0px;
	display: inline-block;
	width: 15%;
	min-width: 15%;
	text-align: center;
}

#sessionViewGrid tr td {
	height: 25px;
}

#sessionViewGrid tr {
	height: 35px;
}

#resizable {
	border: 1px solid black;
	width: 150px;
	height: 150px;
	padding: 0.5em;
}

#resizable h3 {
	text-align: center;
	margin: 0;
}
</style>
<style type="text/css">
</style>

<style id="style-1-cropbar-clipper">/* Copyright 2014 Evernote Corporation. All rights reserved. */
.en-markup-crop-options {
	top: 18px !important;
	left: 50% !important;
	margin-left: -100px !important;
	width: 200px !important;
	border: 2px rgba(255, 255, 255, .38) solid !important;
	border-radius: 4px !important;
}

.en-markup-crop-options div div:first-of-type {
	margin-left: 0px !important;
}
</style>
<script type="text/javascript">
	var lastSelectedIP = null;
	var lastSelectedSessionId = null;
	var lastSelectedSessionLi = null;
	
	
	$(function() {
		
		$("#dnsBtn").fadeOut(0);
		var listGroupDiv = $("#ipblock_1");
		checkForDnsNames(listGroupDiv);
		listGroupDiv = $("#ipblock_0");
		checkForDnsNames(listGroupDiv);
		
	}) 
	
	function clickInverse(event) {
	//	$(this).find("span").val("fuck yea");
	
	}
	
	function checkForDnsNames(listGroupDiv) {
		
		var listItemDivArray = listGroupDiv.find("div.list-group-item").toArray();
	    for (var i=0; i<(listItemDivArray.length);i++) {
	    	var listItemDiv = $(listItemDivArray[i]);
	    	var a = listItemDiv.find("a");
			var dnsName = a.find("span.dnsName").text();
			var ip = a.attr("ip");
			if (ip == dnsName) {
				a.find("span.dnsName").css("display","none");
				a.find("span.ip").css("display","inline-block");
				listItemDiv.find("#dnsBtn").attr("active","false");
			}
			
	    }
		
	}
	
	function dnsBtnClick(event) {
		var dnsBtn = $(event.target);
		var div = dnsBtn.parents("div.list-group-item");
		var a = div.find("a");
		var targetIP = a.attr("ip");
		var spanDns = a.find("span.dnsName");
		var spanIP = a.find("span.ip");
		if (spanDns.css("display") == "inline-block") {
			spanDns.css("display","none");
			spanIP.css("display","inline-block");
			return;
			
		}
			
		if (spanIP.css("display") == "inline-block") {
			spanIP.css("display","none");
			spanDns.css("display","inline-block");
			return;
		}
			
		
	}
	
	function dnsBtnEnter(event) {
		
		
		var div = $(event.target);
		var img = div.find("#dnsBtn");
		if (img.attr("active") != "false")
			img.fadeToggle(100);
	}
	
	function dnsBtnLeave(event) {
		
		var div = $(event.target);
		var img = div.find("#dnsBtn");
		if (img.attr("active") != "false")
		img.fadeToggle(100);
	}
	
	function onFilter() {
		$('#srcPortFilter').val($('#srcPortField').val());
		$('#dstPortFilter').val($('#dstPortField').val());
		$('#srcHostFilter').val($('#srcHostField').val());
		$('#dstHostFilter').val($('#dstHostField').val());
		
		showSessionsAjax();
		
	}
	
	
	function onFilterModalShow() {
		$('#srcPortField').val($('#srcPortFilter').val());
		$('#dstPortField').val($('#dstPortFilter').val());
		$('#srcHostField').val($('#srcHostFilter').val());
		$('#dstHostField').val($('#dstHostFilter').val());
	}
	
	function sessionClick(event,id,real) {
//		alert(event);
//		alert(id);
//		alert(real);
		if (id==null) return;
		if (real == true) {
			
			if (lastSelectedSessionLi!=null) {
				lastSelectedSessionLi.className="";
			}
			lastSelectedSessionLi = document.getElementById(id);
			lastSelectedSessionLi.className = "active";
		
		}
		
	var selectedNet = document.getElementById("subnet").value;
		lastSelectedSessionId = id;
		var srcClassName = document.getElementById("srcDataLnk").className;
		var dstClassName = document.getElementById("dstDataLnk").className;
		var targetData;
		if (srcClassName.indexOf("active")!=-1) {
			targetData='src';
			
			
		}
		if (dstClassName.indexOf("active")!=-1) {
			targetData='dst';
		}
		
		
		
		
		
		$.ajax({

			// The URL for the request
			url : "GetSessionData",

			// The data to send (will be converted to a query string)
			data : {
				direction : targetData,
				nodeName : document.getElementById("nodeName").value,
				branchId : document.getElementById("branchId").value,
				subnet : selectedNet,
				sessionID : id
			},

			// Whether this is a POST or GET request
			type : "GET",

			// The type of data we expect back
			dataType : "text",

			// Code to run if the request succeeds;
			// the response is passed to the function
			success : function(text) {
				document.getElementById("dataContainer").innerHTML=text;
			},

			// Code to run if the request fails; the raw request and
			// status codes are passed to the function
			error : function(xhr, status, errorThrown) {
				alert("Sorry, there was a problem!");
				
			},

			// Code to run regardless of success or failure
			complete : function(xhr, status) {
				
			}
		});
		
		
	}
	function dataLoad(event,direction) {
		if (direction == "src") {
			document.getElementById("srcDataLnk").className="active";
			document.getElementById("dstDataLnk").className="";
		} else {
			document.getElementById("srcDataLnk").className="";
			document.getElementById("dstDataLnk").className="active";
		}
		sessionClick(event,lastSelectedSessionId,false);
		
	}
	
	function init() {
	setHeight();
	window.onresize=function() {
	
		setHeight();
		
	
	};
	}
	function clickTest() {
		var text = event.target.innerText; 
		alert(text);
	//	document.getElementById("submit").click();
		
	}
	
	function filterChange(e) {
		var parent = e.target.parentNode;
		
		
		var filterVal = e.target.id;
		if (filterVal=="Input") {
			document.getElementById("filterIn").value="true";
			document.getElementById("OutputLi").className="";
		}
		if (filterVal=="Output") {
			document.getElementById("filterIn").value="false";
			document.getElementById("InputLi").className="";
		}
		if (filterVal=="Active") {
			document.getElementById("filterActive").value="true";
			document.getElementById("SavedLi").className="";
		}
		if (filterVal=="All") {
			document.getElementById("filterActive").value="false";
			document.getElementById("ActiveLi").className="";
		}
		parent.className="active";
	//	reloadSessions();
		showSessionsAjax();
	}
	
	function reloadSessions() {
		document.forms["reqForm"].submit();
	}
	
	function showSessions(e) {
		var elem = $(e.target);
		var a;
		if ((elem.prop("tagName") == "BUTTON") || (elem.prop("tagName") == "IMG")) {
			return;
		}
		if (elem.prop("tagName") != "A") {
			div = elem.parents("div.list-group-item");
			a = div.find("a");
		} else {
			a = elem;
		}
		
		document.getElementById("ipaddr").value=a.attr("ip");
		a.addClass("active");
		if (lastSelectedIP != null) lastSelectedIP.removeClass("active"); 
		lastSelectedIP = a;
		//reloadSessions();
		showSessionsAjax();
		updateIPInfo(a.attr("ip"));
	}
	
	function updateIPInfo(ip) {
		
		var subnetVal = document.getElementById("subnet").value;
		var ipaddrVal = document.getElementById("ipaddr").value;
		$.ajax({
			  type: "GET",
			  url: "BuildIPinfoTemplate",
			  data: {nodeName : '<%=request.getParameter("nodeName")%>', branchId : '<%=request.getParameter("branchId")%>',
				subnet : subnetVal,
				ipaddr : ipaddrVal
			},
			dataType : "html",
			success : function(text) {
				var htmlElem = $('#IPinfoBlock')[0];
				var jqObj = $(htmlElem);
				jqObj.html(text);
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
			}
		});

	}

	function showSessionsAjax() {
		//	var data =document.getElementById('newImg').files;
		//	var formData = new FormData();
		//	formData.append("newImg",document.getElementById('newImg').files[0]);
		var form = $('#reqForm')[0];
		var formObj = $(form).serialize();

		//	var formData = new FormData(formObj);

		$.ajax({
			type : "GET",
			url : "BuildSessionsTemplate",
			data : formObj,
			processData : false,
			dataType : "html",
			success : function(text) {
				var htmlElem = $('#sessionBlock')[0];
				var jqObj = $(htmlElem);
				jqObj.html(text);
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
			}
		});
	}

	function test() {
		var th = document.getElementById("testid");

		alert(th.getAttribute("data-inCon"));
	}

	function setHeight() {
		var sesWidth = window.innerHeight - 360;
		var ipBlWidth = window.innerHeight - 480;
		var netHeight = window.innerHeight - 380;
		var bodyHeight = window.innerHeight - 210;

		var ipBl_0 = document.getElementById("ipblock_0");
		var ipBl_1 = document.getElementById("ipblock_1");
		var sesBl = document.getElementById("sessionBlock");
		var netLst = document.getElementById("netListBlock");
		var mainPan = document.getElementById("mainBodyPanel");

		sesBl.style.height = sesWidth + 'px';
		ipBl_0.style.maxHeight = ipBlWidth + 'px';
		ipBl_1.style.maxHeight = ipBlWidth + 'px';
		netLst.style.height = netHeight + 'px';
		mainPan.style.height = bodyHeight + 'px';
	}
	function listEvent() {

	}
</script>
<script>
	$(function() {
		$("#resizable").resizable();

	});
</script>


<link rel="stylesheet" href="style/css/bootstrap.min.css">
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

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body onload="init();">

	<%
		BranchBean bb = (BranchBean) request.getAttribute("branchBean");
		SubnetBean sb = (SubnetBean) request.getAttribute("subnetBean");
		IPItemBean ipInfo = (IPItemBean) request.getAttribute("ipBean");
		if (ipInfo == null)
			ipInfo = new IPItemBean();
		if (bb == null)
			request.getRequestDispatcher("NullHandler.jsp").forward(
					request, response);
		String filterIn = (String) request.getParameter("filterIn");
		String filterActive = (String) request.getParameter("filterActive");
		if (filterActive == null)
			filterActive = "false";
		if (filterIn == null)
			filterIn = "true";
	%>
	<input type="hidden" id="nodeName"
		value="<%=request.getParameter("nodeName")%>">
	<input type="hidden" id="branchID"
		value="<%=request.getParameter("branchId")%>">

	<jsp:directive.include file="MainMenu.jsp" /> <!-- add main menu -->

	<div>
		<div
			style="margin-top: 95px; margin-bottom: 10px; margin-left: auto; margin-right: auto; width: 1070px; border-bottom: 1px solid black; height: 50px;"
			class="panel panel-default">
			<ul style="list-style-type: none; width: 100%;">
				<li
					style="width: 50%; margin-top: 5px; font-size: 18pt; float: left;">

					Node: <a href="#">xPosted</a>
				</li>
				<li
					style="width: 50%; text-align: right; margin-top: 5px; font-size: 18pt; float: left;">
					<%=bb.getBib().getBranchName()%>
					<a  href="Statistic?branchId=<%=bb.getBib().getId()%>&nodeName=<%=bb.getBib().getNodeName()%>&type=global"> 
					<button class="btn btn-default" style="margin-left: 4px; margin-right: 5px; padding: 2px;"> 
						<img style="width: 32px;" alt="" src="style/statistic.png"> 
						
					</button>
					</a>
					<button onclick="document.getElementById('linkToOptions').click();"
						style="margin-left: 4px; margin-right: 10px; padding: 2px;"
						class="btn btn-default">
						<img style="width: 32px;" alt="" src="style/opt.png">
					</button> <a id="linkToOptions"
					href="BranchOptions?branchId=<%=bb.getBib().getId()%>&nodeName=<%=bb.getBib().getNodeName()%>"></a>
				</li>
			</ul>
		</div>

		<div id="mainBodyPanel"
			style="min-height: 460px; width: 1500px; margin-left: auto; margin-right: auto;">
			<div style="display: inline-block; float: left; width: 350px;">

				<div style="border-width: 3px; margin-bottom: 2px;"
					class="panel panel-default">
					<div class="panel-heading">Subnet info</div>
					<div class="panel-body">
						<table class="table table-condensed">
							<%
								if (sb != null) {
							%>

							<tr>
								<th>Subnet addr</th>
								<td><%=sb.getSubnet().getHostAddress()%>/<%=sb.getSubnetMask()%></td>
							</tr>
							<tr>
								<th>Ses/Addr</th>
								<td><%=sb.getSesCnt()%>/<%=sb.getAddrCnt()%></td>
							</tr>
							<tr>
								<th>Active Ses/Addr</th>
								<td><%=sb.getActiveSesCnt()%>/<%=sb.getActiveAddrCnt()%></td>
							</tr>
							<tr>
								<th>Up/Down (mB)</th>
								<td><%=Controller.processSize(sb.getDataSend(), 2)%> /<%=Controller.processSize(sb.getDataReceive(), 2)%></td>
							</tr>

							<%
								} else {
							%>

							<tr>
								<th>Ses/Addr</th>
								<td>0/0</td>
							</tr>
							<tr>
								<th>Active Ses/Addr</th>
								<td>0/0</td>
							</tr>
							<tr>
								<th>Up/Down (mB)</th>
								<td>0/0</td>
							</tr>
							<%
								}
							%>
						</table>
					</div>
				</div>

				<div id="netListBlock"
					style="text-align: center; min-height: 260px; width: 340px; margin-left: auto; margin-right: auto; overflow: auto;"
					class="list-group">
					<%
						for (SubnetBean item : bb.getSubnets()) {
					%>

					<a
						href="BranchView?nodeName=<%=bb.getBib().getNodeName()%>&branchId=<%=bb.getBib().getId()%>&subnet=<%=item.getSubnet().getHostAddress()%>"
						class="list-group-item"> <%=item.getSubnet().getHostAddress() + "/"
						+ item.getSubnetMask()%>
					</a>

					<%
						}
					%>


				</div>


			</div>

			<div
				style="float: left; margin-left: 5px; display: inline-block; width: 440px; height: 500px;">
				<div id="IPinfoBlock" class="panel panel-default">
					<div class="panel-heading">IP info</div>
					<table style="margin-bottom: 5px; border: 2px solid #ddd;"
						class="table table-condensed table-bordered">


						<tr>
							<th><%=ipInfo.getIp()%></th>
							<td><%=ipInfo.getDnsName()%></td>
						</tr>
						<tr>
							<th id="testid" data-inCon="true" data-outCon="false">activated</th>
							<td>
								<%
									SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss");
									String t = sdf.format(ipInfo.getActivated());
									out.println(t);
								%>
							</td>
						</tr>
						<tr>
							<th>data up/down (kb)</th>
							<td>
								<%
									String inIpDataCnt = ConnectionHandler.processSize(ipInfo
											.getDataUp(),2);
									out.println(inIpDataCnt);
								%>/<%
									String outIpDataCnt = ConnectionHandler.processSize(ipInfo
											.getDataDown(),2);
									out.println(outIpDataCnt);
								%>
							</td>
						</tr>
						<tr>
							<th>ses (act/svd)</th>
							<td><%=ipInfo.getActiveCount()%>/<%=ipInfo.getSavedCount()%></td>
						</tr>
						<tr>
							<th>conn-s (in/out)</th>
							<td><%=ipInfo.getInputCount()%>/<%=ipInfo.getOutputCount()%></td>
						</tr>
					</table>
				</div>
				<div>
					<div style="width: 220px; display: inline-block; float: left;">
						<div style="margin-right: 2px; margin-left: 5px;"
							class="panel panel-default">
							<div style="text-align: center;" class="panel-heading">IP
								online</div>
							<div id="ipblock_0"
								style="text-align: center; min-height: 50px; overflow: auto;"
								class="list-group">
								<%
									if (sb != null) {
										for (IPItemBean item : sb.getLiveIps()) {

											String[] ip = item.getIp().split("\\.");
								%>
								<div style="height: 50px; padding: 0px;"
									onmouseenter="dnsBtnEnter(event);"
									onmouseleave="dnsBtnLeave(event);" class="list-group-item">
									<span
										style="height: 15px; min-height: 15px; vertical-align: top; width: 100%; text-align: right; display: block;">
										<img onclick="dnsBtnClick(event);" id="dnsBtn"
										class="btn btn-default"
										style="display: none; border-radius: 7px; margin: 0px; padding: 0px; width: 15px; height: 15px;"
										alt="" src="style/reload.png">
									</span> <a
										style="cursor: pointer; text-decoration: none; display: inline-block; width: 100%; text-align: left; height: 81%; margin: 0px; padding: 0px 6px 0px 6px;"
										onclick="showSessions(event)" ip="<%=item.getIp()%>"> <span
										class="dnsName"><%=item.getDnsName()%></span> <span
										class="ip"> <span class="ipOct"><%=ip[0]%></span>. <span
											class="ipOct"> <%=ip[1]%></span>. <span class="ipOct"><%=ip[2]%></span>.
											<span class="ipOct"><%=ip[3]%></span>
									</span>


									</a>


								</div>

								<%
									}
									}
								%>
							</div>
						</div>
					</div>
					<div style="width: 220px; display: inline-block; float: left;">
						<div style="margin-left: 2px; margin-right: 5px;"
							class="panel panel-default">
							<div style="text-align: center;" class="panel-heading">IPs
							</div>
							<div id="ipblock_1"
								style="padding: 0px; text-align: center; min-height: 50px; overflow: auto;"
								class="list-group">

								<%
									if (sb != null) {
										for (IPItemBean item : sb.getIps()) {

											String[] ip = item.getIp().split("\\.");
								%>
								<div style="height: 50px; padding: 0px;"
									onmouseenter="dnsBtnEnter(event);"
									onmouseleave="dnsBtnLeave(event);" class="list-group-item">
									<span
										style="height: 15px; min-height: 15px; vertical-align: top; width: 100%; text-align: right; display: block;">
										<img onclick="dnsBtnClick(event);" id="dnsBtn"
										class="btn btn-default"
										style="display: none; border-radius: 7px; margin: 0px; padding: 0px; width: 15px; height: 15px;"
										alt="" src="style/reload.png">
									</span> <a
										style="cursor: pointer; text-decoration: none; display: inline-block; width: 100%; text-align: left; height: 81%; margin: 0px; padding: 0px 6px 0px 6px;"
										onclick="showSessions(event)" ip="<%=item.getIp()%>"> <span
										class="dnsName"><%=item.getDnsName()%></span> <span
										class="ip"> <span class="ipOct"><%=ip[0]%></span>. <span
											class="ipOct"> <%=ip[1]%></span>. <span class="ipOct"><%=ip[2]%></span>.
											<span class="ipOct"><%=ip[3]%></span>
									</span>


									</a>


								</div>



								<!-- 
								<a href="BranchView?nodeName=<%=bb.getBib().getNodeName()%>&branchId=<%=bb.getBib().getId()%>&subnet=<%=sb.getSubnet().getHostAddress()%>&ipaddr=<%=item.getIp()%>" class="<%String selectedIP = request.getParameter("ipaddr");
					if (selectedIP == null)
						selectedIP = "judaka";
					if (selectedIP.equals(item.getIp()))
						out.println("active");%> list-group-item"><%=item.getIp()%></a> 		
							-->

								<%
									}
									}
								%>

							</div>
						</div>
					</div>

				</div>

			</div>
			<div class="panel panel-default"
				style="min-height: 460px; margin-left: 5px; display: inline-block; width: 500px;">
				<div class="panel-heading">
					<font style="width: 20%">Session info</font>
					<div style="width: 80%; display: inline-block; text-align: right;">
						<button onclick="onFilterModalShow();" data-toggle="modal"
							data-target="#filterModal" class="btn btn-default">
							<img style="width: 25px;" alt="" src="style/filter_data.png">
						</button>
					</div>
				</div>
				<div
					style="border: 2px solid #139; border-bottom-width: 5px; margin-bottom: 5px; height: 100px;"
					class="panel panel-default">

					<div style="width: 247px; display: inline-block; float: left;">
						<ul class="nav nav-pills nav-stacked">

							<li style="cursor: pointer;" id="InputLi" role="presentation"
								class="active"><a id="Input" onclick="filterChange(event);">Input</a></li>
							<li style="cursor: pointer;" id="OutputLi" role="presentation"><a
								id="Output" onclick="filterChange(event);">Output</a></li>

						</ul>
					</div>
					<div style="width: 247px; display: inline-block; float: left;">
						<ul class="nav nav-pills nav-stacked">
							<li style="cursor: pointer;" id="ActiveLi" role="presentation"><a
								id="Active" onclick="filterChange(event);">Active</a></li>
							<li style="cursor: pointer;" id="SavedLi" role="presentation"
								class="active"><a id="All" onclick="filterChange(event);">Saved</a></li>

						</ul>
					</div>

				</div>

				<div id="sessionBlock" style="min-height: 330px; overflow: auto;">



				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">Session Data</div>
			<div class="panel-body">
				<ul class="nav nav-tabs">
					<li id="srcDataLnk" role="presentation" class="active"><a
						onclick="dataLoad(event,'src');">Source transmited</a></li>
					<li id="dstDataLnk" role="presentation"><a
						onclick="dataLoad(event,'dst');">Destination transmited</a></li>
					<li>
						<button onclick="document.getElementById('linkToFiles').click();"
							class="btn btn-default">
							Files <a id="linkToFiles"
								href="Files?brID=<%=bb.getBib().getId()%>&node=<%=bb.getBib().getNodeName()%>&path=/"></a>
						</button>
					</li>


				</ul>
				<div style="max-height: 500px;; overflow: auto;" id="dataContainer">

				</div>

			</div>

		</div>



		<!-- 	"Add net modal"	 -->
		<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Add net</h4>
					</div>
					<div class="modal-body">

						<div class="input-group">
							<span class="input-group-btn">
								<button class="btn btn-default" type="button">Add</button>
							</span> <input type="text" class="form-control">
						</div>
						<!-- /input-group -->

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

					</div>
				</div>
			</div>
		</div>




		<!-- 	"Filter modal"	 -->
		<div class="modal fade" id="filterModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div style="width: 500px;" class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Session filter</h4>
					</div>
					<div class="modal-body">
						<div class="panel panel-default">
							<div class="panel-heading">Port filter</div>
							<div class="panel-body">

								<table class="table table-default">
									<tr>
										<td>SRC port</td>
										<td><input style="float: left;display: inline-block; width: 50%;" id="srcPortField" type="text"
											class="form-control"> 
											
											<div style="display: inline-block; float: left;" class="btn-group" data-toggle="buttons">
											<label onclick="clickInverse(event);" id="lblInverse" class="btn btn-primary">
												<input type="checkbox" autocomplete="off"><span> Inverse: off </span>
												
										</label>
										</div>
										</td>
									</tr>
									<tr>
										<td>DST port</td>
										<td><input style="float: left;" id="dstPortField" type="text"
											class="form-control"> <label class="btn btn-primary">
												<input type="checkbox" autocomplete="off"> Inverse: off
												
										</label></td>
									</tr>
								</table>

							</div>



						</div>

						<div class="panel panel-default">
							<div class="panel-heading">IP filter</div>
							<div class="panel-body">

								<table class="table table-default">
									<tr>
										<td>SRC host</td>
										<td><input id="srcHostField" type="text"
											class="form-control"> <label class="btn btn-primary">
												<input type="checkbox" autocomplete="off"> Inverse: off
												
										</label></td>
									</tr>
									<tr>
										<td>DST host</td>
										<td><input id="dstHostField" type="text"
											class="form-control"> <label class="btn btn-primary">
												<input type="checkbox" autocomplete="off"> Inverse: off
												
										</label></td>
									</tr>
								</table>

							</div>



						</div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button onclick="onFilter();" type="button"
							class="btn btn-primary">Save changes</button>

					</div>
				</div>
			</div>
		</div>



		<!-- 	"Data modal"	 -->
		<div class="modal fade" id="filterModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div style="width: 500px;" class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Session filter</h4>
					</div>
					<div class="modal-body">
						<div class="panel panel-default"></div>



					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary">Save
							changes</button>

					</div>
				</div>
			</div>
		</div>
	</div>

	<form action="BranchView" name="reqForm" id="reqForm" method="get"
		enctype="application/x-www-form-urlencoded">
		<input type="hidden" id="srcPortFilter" name="srcP" value="*:">
		<input type="hidden" id="dstPortFilter" name="dstP" value="*:">
		<input type="hidden" id="srcHostFilter" name="srcH" value="*:">
		<input type="hidden" id="dstHostFilter" name="dstH" value="*:">

		<input type="hidden" id="filterIn" name="filterIn" value="true">
		<input type="hidden" id="filterActive" name="filterActive"
			value="false"> <input type="hidden" id="branchId"
			name="branchId" value="<%=bb.getBib().getId()%>"> <input
			type="hidden" id="subnet" name="subnet"
			value="<%if (sb != null)
				out.print(sb.getSubnet().getHostAddress());%>">
		<input type="hidden" id="ipaddr" name="ipaddr"
			value="<%if (ipInfo != null)
				out.print(ipInfo.getIp());%>"> <input
			type="hidden" id="nodeName" name="nodeName"
			value="<%=bb.getBib().getNodeName()%>">
	</form>



	<!--  service fields, do not delete -->
	<input id="net" type="hidden" name="net" value="">
	<input id="ip" type="hidden" name="ip" value="">
	<input id="filterIn" type="hidden" name="filterIn" value="">
	<input id="filterActive" type="hidden" name="filterActive" value="">


</body>
</html>