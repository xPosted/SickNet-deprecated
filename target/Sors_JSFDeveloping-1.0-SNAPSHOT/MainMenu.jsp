<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String uri = request.getRequestURI();
String pageName = uri.substring(uri.lastIndexOf("/")+1);
%>
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
						<a class="navbar-brand" href="sors.jsp">SORS</a>

					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
							<li <% if (pageName.startsWith("Processing"))  {%> class="active" <% } %>><a href="BranchList">Processing</a></li>
							<li <% if (pageName.startsWith("Nodes"))  {%> class="active" <% } %>><a href="Nodes">Nodes</a></li>
							<li <% if (pageName.startsWith("BranchView"))  {%> class="active" <% } %>><a href="BranchView">View</a></li>
							
							<li style="margin-left: 715px;" class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"><%= session.getAttribute("user").toString() %> <span class="caret"></span></a>
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