
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jubaka.sors.beans.branch.IPItemBean"%>
<%@page import="com.jubaka.sors.beans.branch.SessionBean"%>
<%@page import="com.jubaka.sors.sessions.Data_bean"%>
<%@page import="javax.swing.JOptionPane"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.util.List"%>
<html>
<head>


 <link href="style/css/bootstrap.min.css" rel="stylesheet">
  <link href="style/css/carousel.css" rel="stylesheet">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="style/js/bootstrap.min.js"></script>
    <script src="style/js/docs.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="style/js/ie10-viewport-bug-workaround.js"></script>
<style type="text/css">
.mainLogo {
position: relative;
left: 1010px;
width: 110;
padding: 0;
margin: 0;

z-index: 9;


}



</style> 
 
 
<meta http-equiv="Content-Type" content="text/html;
charset=UTF-8">
<title>TcpAnalyze</title>
</head>
<body>

<div class="navbar-wrapper">
      <div class="container">

        <nav style="margin-top: 5mm; z-index: 1; width: 1170px; position: absolute;" class="navbar navbar-inverse navbar-static-top" role="navigation">
          <div  class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">SORS</a>
            
            </div>
            <div id="navbar"  class="navbar-collapse collapse">
              <ul   class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li>
                	
                  </ul>
                </li>
              <li>  </li>
              </ul>
              
             
            </div>
           
            
          </div>
         
        </nav>
 		<img class="mainLogo" alt="" src="style/mainLogo.png">
      </div>
    </div>

	
<div style="margin-top: 10%">
	<table  cellpadding="2" align="center">
		<tr>
			<td style="width: 190px; vertical-align: top; text-align: center">

				<table   width=100% border=1 cellspacing="1">
					<tr>
						<td colspan="2">
							<h4> Data provider ip:<p>
								<%
									out.println(request.getSession().getAttribute("ip"));
								%>

							</h4>
						</td>
					</tr>
					<tr>
						<%			 
								Integer liveSes=0;
								Integer liveAddr=0;
								Integer AddrCount=0;
								Integer SesCount=0; 
								long down=0; 
								long up=0; 
								if (request.getSession().getAttribute("ipDataBean")!=null)	{
									Data_bean bean = (Data_bean) request.getSession().getAttribute("ipDataBean");
									liveSes=bean.getLiveSes();
									liveAddr = bean.getLiveAddr();
									AddrCount = bean.getAddrCount();
									SesCount = bean.getSesCount();
									down = bean.getDown();
									up = bean.getUp();
									
								}					
						%>

						<td align=left>Ses/Addr
						<td><%= SesCount+"/"+AddrCount %>
					</tr>
					<tr>
						<td align=left>Active Ses/Addr
						<td><%= liveSes+"/"+liveAddr %>
					</tr>
					<tr>
						<td align=left>UP/Down
						<td><%= up+"/"+down %>
					</tr>



				</table>
				<hr>

				<table   width=100% border=1>
					<%
						Integer i = 0;
						List<String> netList = (List<String>) session
								.getAttribute("netlist");
						for (String net : netList) {
					%>

					<tr>
						<td style="background-color: rgb(255, 255, 255);"><b>
								<h3>
									<a href="/TCPanalyze/OneSrv?action=<%="getIPlst_" + net%> ">
										<%
 	session.setAttribute("selectedNet", net);
 		out.println(net);
 %>
									</a>
								</h3>
						</b></td>

					</tr>

					<%
						i++;
						}
					%>




				</table>

			</td>



			<td valign="top">

				<table   width="300" cellspacing="1">
					<tr>
					<td colspan="2">
												<% IPinfo_bean ipInfo = (IPinfo_bean) request.getSession().getAttribute("ipInfo"); %> 
												
						<table cellpadding="1" cellspacing="1" border="1" width=360 style="height: 157px;">
							<tr>
								<td align="left"><b>
									
									
										<%= ipInfo.getIp() %>
								
								<td align="right"><b><%= InetAddress.getByName(ipInfo.getIp()).getCanonicalHostName() %>
							</tr>
							<tr>
								<td align="left">activated
								<td align="right"><% 
								
								Date creationDate = ipInfo.getActivated();
    							SimpleDateFormat date_format = new SimpleDateFormat("dd-M, HH:mm");
    							
								%><%= date_format.format(creationDate) %>
							</tr>
							<tr>
								<td align="left">data (up/down)
								<td align="right"><%= ipInfo.getDataUp()+"/"+ipInfo.getDataDown() %>
							</tr>
							<tr>
								<td align="left">ses (act/svd)
								<td align="right"><%= ipInfo.getActiveCount()+"/"+ipInfo.getSavedCount() %>
							</tr>
							<tr>
								<td align="left">conn-s (in/out)
								<td align="right"><%= ipInfo.getInputCount()+"/"+ipInfo.getOutputCount() %>
							</tr>
							
							
							
							

						</table>

					</tr>

					<tr>
						<td style="vertical-align: top; text-align: center">
							<table width="180" border="1" cellspacing="1" cellpadding="5">
								<caption>ActiveIP</caption>

								<%
						if (request.getSession().getAttribute("ipDataBean") != null) {
							Data_bean bean = (Data_bean) request.getSession().getAttribute("ipDataBean");
							for (String addr : bean.getLiveData()) {
					%>
								<tr>
									<td align="left"><i> <a
											href="/TCPanalyze/OneSrv?action=getSessions_<%=addr%>"><%=addr%></a>
									</i></td>
								</tr>

								<%
						}
						}
					%>

							</table>
						</td>
						<td style="vertical-align: top; text-align: center;">
							<table   width=180 border=1 cellspacing="1" cellpadding="5">
								<caption>AllIP</caption>
								<%
					
						if (request.getSession().getAttribute("ipDataBean") != null) {
							Data_bean bean = (Data_bean) request.getSession().getAttribute("ipDataBean");
							
							for (String addr : bean.getipData()) {
					%>

								<tr>
									<td align="right"><i> <a
											href="/TCPanalyze/OneSrv?action=getSessions_<%=addr%>"> <%=addr%>
										</a>
									</i></td>
								</tr>
								<%
						}
						}
					%>


							</table>
					</tr>
				</table>

			</td>






			<td style="vertical-align: top;">
				<table   border="2" cellspacing="4" width="400">
					<caption>Session data</caption>

					<tr>

						<td>
							<form method="get" action="/TCPanalyze/Filter">
								<table cellpadding="0" cellspacing="0" border="2"
									style="width: 100%;">
									<caption>Filter</caption>
									<tr>
										<td><input type="radio" name="IOfilter" value="INPUT">INPUT

										</td>
										<td><input type="radio" name="IOfilter" value="OUTPUT">OUTPUT
										</td>



									</tr>

									<tr>
										<td><input type="radio" name="StateFilter" value="Active">Active

										
										<td><input type="radio" name="StateFilter" value="Saved">Saved

										
									</tr>
									<tr>

										<td colspan="2"><input type="submit"
											value="APPLY filter">
									</tr>



								</table>
							</form>

						</td>>

					</tr>
					<tr>
						<td>
							<%
								if (request.getSession().getAttribute("sesView") != null) {
									HashSet<sessionBean> sesBuf = (HashSet<sessionBean>) request.getSession()
											.getAttribute("sesView");
									for (sessionBean item : sesBuf) {
							%>



							<table style="width: 100%" border="1" cellspacing="1">
								<tr>
									<td>
										<table style="width: 100%" cellspacing="0" cellpadding="0"
											border=0>
											<tr>
												<td><i> <%
												
												SimpleDateFormat sdf = new SimpleDateFormat("dd-mm hh:mm:ss");
												out.println( sdf.format(item.getEstablished()));%></i><%=" "+ item.getSrcIP()  %>
												</td>
												<td style="text-align: right;"><%=item.getDstIP()%></td>

											</tr>


										</table>

									</td>
								</tr>
								<tr>
									<td>
										<table width=100%>
											<tr>
												<td><i> <%
												if (item.getClosed()==null) out.println("online"); else
												out.println( sdf.format( item.getClosed()));%>
												</i></td>

												<td style="text-align: center;"><i> <%=item.getSrcDataLen() + "/" + item.getDstDataLen()%>
														kb
												</i></td>

												<td style="text-align: right;"><i> TCP(src/dst): <%=item.getSrcP() + "/" + item.getDstP()%></i></td>

											</tr>

										</table>

									</td>
								</tr>
							</table> <% }} %>
						</td>

					</tr>

				</table>
			</td>


		</tr>


	</table>

</div>
</body>
</html>