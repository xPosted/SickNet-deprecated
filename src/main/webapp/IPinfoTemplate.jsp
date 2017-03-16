


<%@page import="com.jubaka.sors.beans.branch.IPItemBean"%>
<%@page import="com.jubaka.sors.appserver.serverSide.ConnectionHandler"%>
<%@page import="java.text.SimpleDateFormat"%>

<%
	IPItemBean ipInfo = (IPItemBean) request.getAttribute("ipBean");
%>
<div  class="panel-heading">IP info</div>
				<table style="margin-bottom:5px; border: 2px solid #ddd;"
					class="table table-condensed table-bordered">
					
		
					<tr>
						<th><%= ipInfo.getIp() %></th>
						<td><%= ipInfo.getDnsName() %></td>
					</tr>
					<tr>
						<th id="testid" data-inCon="true" data-outCon="false">activated</th>
						<td><%
							SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss");
							String t = sdf.format(ipInfo.getActivated());
							out.println(t);
						 %></td>
					</tr>
					<tr>
						<th>data up/down (kb)</th>
						<td><%String inIpDataCnt=ConnectionHandler.processSize(ipInfo.getDataUp(),2);
								out.println(inIpDataCnt); %>/<%String outIpDataCnt = ConnectionHandler.processSize(ipInfo.getDataDown(),2);
									out.println(outIpDataCnt);%></td>
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