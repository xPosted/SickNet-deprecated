<%@page import="com.jubaka.sors.serverSide.ExtFilter"%>
<%@page import="java.util.Random"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jubaka.sors.beans.branch.IPItemBean"%>
<%@page import="com.jubaka.sors.beans.branch.SubnetBean"%>
<%@page import="com.jubaka.sors.beans.branch.BranchBean"%>
<%@page import="com.jubaka.sors.serverSide.ConnectionHandler"%>
<%@page import="com.jubaka.sors.beans.branch.SessionBean"%>
<%@page import="java.util.HashSet"%>
<%BranchBean bb = (BranchBean) request.getAttribute("branchBean"); 
	SubnetBean sb = (SubnetBean) request.getAttribute("subnetBean");
	IPItemBean ipInfo = (IPItemBean) request.getAttribute("ipBean");
	
	if (ipInfo==null) ipInfo = new IPItemBean();
	if (bb==null) request.getRequestDispatcher("NullHandler.jsp").forward(request, response);
	String filterIn = (String)request.getParameter("filterIn");
	String filterActive = (String) request.getParameter("filterActive");
	if (filterActive==null) filterActive="false";
	if (filterIn==null) filterIn="true";
	String srcPortFilter = request.getParameter("srcP");
	String dstPortFilter = request.getParameter("dstP");
	String srcHostFilter = request.getParameter("srcH");
	String dstHostFilter = request.getParameter("dstH");
	
	
%>
					<ul class="nav nav-stacked nav-pills">
					<% 
						SimpleDateFormat sdf = new SimpleDateFormat("dd.MM HH:mm:ss");
 						 HashSet<SessionBean> set = null;
 						if (filterActive.equals("false") & filterIn.equals("false")) set = ipInfo.getStoredOutSes(); 
 						if (filterActive.equals("false") & filterIn.equals("true")) set = ipInfo.getStoredInSes(); 
 						if (filterActive.equals("true") & filterIn.equals("false")) set = ipInfo.getActiveOutSes(); 
 						if (filterActive.equals("true") & filterIn.equals("true")) set = ipInfo.getActiveInSes();
 						
 						ExtFilter eFilter = new ExtFilter(srcPortFilter,dstPortFilter,srcHostFilter,dstHostFilter);
 						 for (SessionBean sesItem : set) {
 							 if (!eFilter.checkSession(sesItem)) continue;
 						 Long sessionId = sesItem.getEstablished().getTime();
 						 %>
 						 <li id="<%=sessionId.toString() %>" class=""  role="presentation" style="cursor: pointer; border-radius:10px; border: 2px solid #ddd;" ><a onclick="sessionClick(event,'<%=sessionId%>',true)">
 						 <div>
 						 			<table id="sessionViewGrid" style=" font-size:11pt; width:100%; height: 90px;" class=" table-condensed">
 						 				<tr style=" border-bottom: 1px solid #ddd;">
 						 					<td><%= sdf.format(sesItem.getEstablished()) %></td>
 						 					<td style="font-weight:bold;"><%= sesItem.getSrcIP() %> </td>
 						 					<td><% if (sesItem.getHttpBuf() != null) {%> <img style="border-radius: 7px; margin:0px; padding: 0px; width: 40px; height: 15px;" src="style/http.jpg">
 						 					<% } %>
 						 					<td><% if (sesItem.getEstablished().getTime() == 0 ) {%> <img style="border-radius: 7px; margin:0px; padding: 0px; width: 40px; height: 15px;" src="style/replace.png">
 						 					<% } %>
 						 					<td style="font-weight:bold; text-align: right;"><%= sesItem.getDstIP() %></td>
 						 				</tr>
 						 				<tr>
 						 				 	<td><% if (sesItem.getClosed()==null)
 						 				 		out.println("Online"); else out.println("Terminated");
 						 				 	 %></td>
 						 				 	<td colspan="2"><% String srcLen = ConnectionHandler.processSize(sesItem.getSrcDataLen(),2);
 						 				 			out.println(srcLen); %>/<% String dstLen = ConnectionHandler.processSize(sesItem.getDstDataLen(),2);
 						 				 			out.println(dstLen); %></td>
 						 				 	
 						 
 						 				 	<td colspan="2" style="width:100%; text-align: right;">TCP (src/dst): <%= sesItem.getSrcP() %>/<%= sesItem.getDstP() %></td>
 						 				</tr>
 						 				
 						 			</table>
 						 		
 						 		</div>	
 						 </a></li>
 						 <% } %>
 						 </ul>