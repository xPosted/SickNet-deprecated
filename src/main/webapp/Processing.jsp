<!DOCTYPE html>
<%@page import="com.jubaka.sors.appserver.servlet.Nodes"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jubaka.sors.appserver.serverSide.ConnectionHandler"%>
<%@page import="com.jubaka.sors.appserver.beans.branch.BranchInfoBean"%>
<%@page import="java.util.HashSet"%>
<html>
<head>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<link rel="stylesheet" href="style/css/bootstrap.css">
<script src="style/js/bootstrap.min.js"></script>

<meta charset="UTF-8">
<title>Insert title here</title>
<link href="style/css/carousel.css" rel="stylesheet">
<style type="text/css">

.statisticTable {
	color: #ACACAD;
	 width: 250px;
}
.mainTableDiv {
	margin-top: 10px;
		text-align: center;
	width: 100%;
	height: 100%; 
}

.statisticTableTr {
	padding-bottom: 5px;
	padding-left: 20px;
	padding-right: 20px;
	padding-top: 5px;
	border-top: 1px solid black;
	display: block;
	
}
.statisticTableTd {
	width: 100%; 
	text-align: justify;
}

.statisticDiv {

	display: inline-block;
	width: 100%;
	border-bottom: 2px solid black;
	padding-top: 10px;
	border-radius: 6px;
}
.bodyContainer {
	width: 1000px;
	margin-left: auto;
	margin-right: auto;
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
</head>
<body style="">
<%
Integer ownCount = (Integer) request.getAttribute("ownCount");
Integer nodeCount = (Integer) request.getAttribute("nodeCount");
Integer liveCount = (Integer) request.getAttribute("liveCount");
HashSet<BranchInfoBean> brLst = (HashSet<BranchInfoBean>) request.getAttribute("branchList");

%>

	<jsp:directive.include file="MainMenu.jsp" /> <!-- add main menu -->
	
	
	
	<div style="margin-top: 95px;" class="bodyContainer">
		<div  class="statisticDiv panel-default">
			<!-- first column  -->
			<div class="panel panel-default" style="padding: 10px; margin-right: 200px; margin-left:30px; float: left;">
				Found <span class="badge"><%= brLst.size() %></span> captures on <span class="badge"> <%= nodeCount %> </span> Nodes 
			</div>
			
			<!-- second column  -->
			<div style="margin-right: 30px; margin-left:200px; float: left;">
				<table  class="statisticTable">
					<tr class="statisticTableTr">
							<td class="statisticTableTd">Own captures</td>
							<td class="statisticTableTd"><%= ownCount %></td>
					</tr>
					
					<tr class="statisticTableTr">
							<td class="statisticTableTd">Live captures</td>
							<td class="statisticTableTd"><%= liveCount %></td>
					</tr>
				</table>
			</div>
		
		</div>
		
		<div class="mainTableDiv">
			<div>
				<table 	style="width: 100%;" class="table-hover">
				<caption> Processing data info </caption>
				<thead>
					<tr>
						<th > Name
						<th > Location
						<th > Status
						<th> Size
						<th > Action
						<th > Init Date
					</tr>
				</thead>
				<tbody>
				<%   
					for (BranchInfoBean bib : brLst) {
				%>
					<tr>
						<th> <a href="BranchView?nodeName=<%=bib.getNodeName()%>&branchId=<%=bib.getId()%>">  <%=bib.getBranchName()%> </a>
						<th> <a href="NodeView?nodeName=<%=bib.getNodeName()%>"><%=bib.getNodeName()%></a> 
						<th> <%if (bib.getIface()==null) out.println("Live");
								else out.println("PreWrited"); %>
						<th> <% 
								String sz = ConnectionHandler.processSize(bib.getUploadSize(),2);
								out.println(sz); %>
						<th> <input type="button" value="Stop/Delete">
						<th> <%SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
								out.println(sdf.format(bib.getTime())); %>
						
					</tr>
					<%	} %>
				
				</tbody>
				</table>
			</div>
		</div>
	
	
	</div>
	
</body>
</html>