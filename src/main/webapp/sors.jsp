<!DOCTYPE html>
<%@page import="com.jubaka.sors.appserver.serverSide.UserBase"%>
<%@page import="com.jubaka.sors.appserver.entities.User"%>
<%@page import="java.util.Date"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Carousel Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="style/css/bootstrap.min.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="style/js/ie-emulation-modes-warning.js"></script>


<script type="text/javascript">
function sign_in() {
var user =  document.getElementById("loginFld").value;
var pass =  document.getElementById("passFld").value;
document.getElementById("user").value=user;
document.getElementById("pass").value=pass;
document.forms["hiddenForm"].submit();

}

</script>

<style type="text/css"></style>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- Custom styles for this template -->
<link href="style/css/carousel.css" rel="stylesheet">
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
<style type="text/css">
#lleo_dialog, #lleo_dialog * {
	margin: 0 !important;
	padding: 0 !important;
	background: none !important;
	border: none 0 !important;
	position: static !important;
	vertical-align: baseline !important;
	font: normal 13px Arial, Helvetica !important;
	line-height: 15px !important;
	color: #000 !important;
	overflow: visible !important;
	width: auto !important;
	height: auto !important;
	float: none !important;
	visibility: visible !important;
	text-align: left !important;
	border-collapse: separate !important;
	border-spacing: 2px !important;
}

#lleo_dialog iframe {
	height: 0 !important;
	width: 0 !important;
}

#lleo_dialog {
	position: absolute !important;
	background: #fff !important;
	border: solid 1px #ccc !important;
	padding: 7px 0 0 !important;
	left: -999px;
	top: -999px;
	/*max-width: 450px !important;*/
	width: 440px !important;
	overflow: hidden;
	display: block !important;
	z-index: 999999999 !important;
	opacity: 0 !important;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.18) !important;
	-moz-border-radius: 3px !important;
	-webkit-border-radius: 3px !important;
	border-radius: 3px !important;
}

#lleo_dialog.lleo_show {
	opacity: 1 !important;
	-webkit-transition: opacity 0.3s !important;
}

#lleo_dialog input::-webkit-input-placeholder {
	color: #aaa !important;
}

#lleo_dialog .lleo_has_pic #lleo_word {
	margin-right: 80px !important;
}

#lleo_dialog #lleo_translationsCopntainer1 {
	position: relative !important;
}

#lleo_dialog #lleo_translationsCopntainer2 {
	padding: 7px 0 0 !important;
	vertical-align: middle !important;
}

#lleo_dialog #lleo_word {
	color: #000 !important;
	margin: 0 5px 2px 0 !important;
	/*float: left !important;*/
}

#lleo_dialog .lleo_has_sound #lleo_word {
	margin-left: 17px !important;
}

#lleo_dialog #lleo_text {
	font-weight: bold !important;
	color: #d56e00 !important;
	text-decoration: none !important;
	cursor: default !important;
}

#lleo_dialog #lleo_text.lleo_known {
	cursor: pointer !important;
	text-decoration: underline !important;
}

#lleo_dialog #lleo_closeBtn {
	position: absolute !important;
	right: 6px !important;
	top: 5px !important;
	line-height: 1px !important;
	text-decoration: none !important;
	font-weight: bold !important;
	font-size: 0 !important;
	color: #aaa !important;
	display: block !important;
	padding: 2px !important;
	z-index: 9999999999 !important;
	width: 7px !important;
	height: 7px !important;
	padding: 0 !important;
	margin: 0 !important;
}

#lleo_dialog #lleo_optionsBtn {
	position: absolute !important;
	right: 1px !important;
	top: 12px !important;
	line-height: 1px !important;
	text-decoration: none !important;
	font-weight: bold !important;
	font-size: 13px !important;
	color: #aaa !important;
	padding: 2px !important;
	display: none;
}

#lleo_dialog #lleo_optionsBtn img {
	width: 12px !important;
	height: 12px !important;
}

#lleo_dialog #lleo_sound {
	float: left !important;
	width: 16px !important;
	height: 16px !important;
	margin-left: 12px !important;
	background: 0 0 no-repeat !important;
	cursor: pointer !important;
	display: none !important;
}

#lleo_dialog .lleo_has_sound #lleo_sound {
	display: block !important;
}

#lleo_dialog #lleo_picOuter {
	position: absolute !important;
	float: right !important;
	right: 29px;
	top: 0;
	display: none !important;
	z-index: 9 !important;
}

#lleo_dialog .lleo_has_pic #lleo_picOuter {
	display: block !important;
}

#lleo_dialog #lleo_picOuter:hover {
	z-index: 11 !important;
}

#lleo_dialog #lleo_pic, #lleo_dialog #lleo_picBig {
	position: absolute !important;
	top: 0 !important;
	right: 0 !important;
	border: solid 2px #fff !important;
	-moz-border-radius: 2px !important;
	-webkit-border-radius: 2px !important;
	border-radius: 2px !important;
	z-index: 1 !important;
}

#lleo_dialog #lleo_pic {
	position: relative !important;
	border: none !important;
	width: 34px !important;
}

#lleo_dialog #lleo_picBig {
	box-shadow: -1px 2px 4px rgba(0, 0, 0, 0.3);
	z-index: 2 !important;
	opacity: 0 !important;
	visibility: hidden !important;
}

#lleo_dialog #lleo_picOuter:hover #lleo_picBig {
	visibility: visible !important;
	opacity: 1 !important;
	-webkit-transition: opacity 0.3s !important;
	-webkit-transition-delay: 0.3s !important;
}

#lleo_dialog #lleo_transcription {
	color: #486D85 !important;
	margin: 0 0 4px 29px !important;
	color: #aaaaaa !important;
}

#lleo_dialog .lleo_no_trans {
	color: #aaa !important;
}

#lleo_dialog .ll-translation-counter {
	float: right !important;
	font-size: 11px !important;
	color: #aaa !important;
	padding: 2px 2px 1px 10px !important;
}

#lleo_dialog .ll-translation-text {
	float: left !important;
	width: 80% !important;
}

#lleo_dialog #lleo_trans a {
	color: #3F669F !important;
	padding: 1px 4px !important;
	text-decoration: none !important;
	text-overflow: ellipsis !important;
	overflow: hidden !important;
}

#lleo_dialog .ll-translation-item {
	width: 100% !important;
	float: left !important;
	padding: 1px 4px;
	color: #3F669F !important;
	padding: 3px !important;
	border: solid 1px white !important;
	-moz-border-radius: 2px !important;
	-webkit-border-radius: 2px !important;
	border-radius: 2px !important;
}

#lleo_dialog .ll-translation-item:hover {
	border: solid 1px #9FC2C9 !important;
	background: #EDF4F6 !important;
	cursor: pointer !important;
}

#lleo_dialog .ll-translation-marker {
	margin: 0px 5px 2px 2px !important;
}

#lleo_dialog #lleo_icons {
	margin: 10px 0 7px !important;
	color: #aaa !important;
	line-height: 20px !important;
	font-size: 11px !important;
	clear: both !important;
	padding-left: 16px !important;
}

#lleo_icons a {
	display: inline-block !important;
	width: 16px !important;
	height: 16px !important;
	margin: 0 0 -2px 3px !important;
	text-decoration: none !important;
	background: 0 0 no-repeat !important;
	opacity: 0.5 !important;
}

#lleo_icons a:hover {
	opacity: 1 !important;
}

#lleo_icons a.lleo_google {
	background-position: -34px 0 !important;
}

#lleo_icons a.lleo_multitran {
	background-position: -64px 0 !important;
}

#lleo_icons a.lleo_lingvo {
	background-position: -51px 0 !important;
	width: 12px !important;
}

#lleo_icons a.lleo_dict {
	background-position: -17px 0 !important;
}

#lleo_icons a.lleo_linguee {
	background-position: -81px 0 !important;
}

#lleo_icons a.lleo_michaelis {
	background-position: -98px 0 !important;
}

#lleo_dialog #lleo_contextContainer {
	margin: 0 !important;
	padding: 3px 15px 3px 10px !important;
	background: -webkit-gradient(linear, left top, left bottom, from(#fff),
		to(#eee)) !important;
	border-bottom: solid 1px #ddd !important;
	border-top-left-radius: 3px !important;
	border-top-right-radius: 3px !important;
	display: none !important;
	overflow: hidden !important;
}

#lleo_dialog .lleo_has_context #lleo_contextContainer {
	display: block !important;
}

#lleo_dialog #lleo_context {
	color: #444 !important;
	text-shadow: 1px 1px 0 #f4f4f4 !important;
	line-height: 12px !important;
	font-size: 11px !important;
	margin-left: 2px !important;
}

#lleo_dialog #lleo_context b {
	line-height: 12px !important;
	color: #000 !important;
	font-weight: bold !important;
	font-size: 11px !important;
}

#lleo_dialog #lleo_gBrand {
	color: #aaa !important;
	font-size: 10px !important;
	/*padding-right: 52px !important;*/
	padding-bottom: 14px !important;
	margin: -3px 4px 0 4px !important;
	background: left bottom no-repeat !important;
	display: inline-block !important;
	float: right !important;
}

#lleo_dialog #lleo_gBrand.hidden {
	display: none !important;
}

#lleo_dialog #lleo_translateContextLink {
	color: #444 !important;
	text-shadow: 1px 1px 0 #f4f4f4 !important;
	background: -webkit-gradient(linear, left top, left bottom, from(#f4f4f4),
		to(#ddd)) !important;
	border: solid 1px !important;
	box-shadow: 1px 1px 0 #f6f6f6 !important;
	border-color: #999 #aaa #aaa #999 !important;
	-moz-border-radius: 2px !important;
	-webkit-border-radius: 2px !important;
	border-radius: 2px !important;
	padding: 0 3px !important;
	font-size: 11px !important;
	text-decoration: none !important;
	margin: 1px 5px 0 !important;
	display: inline-block !important;
	white-space: nowrap !important;
}

#lleo_dialog #lleo_translateContextLink:hover {
	background: #f8f8f8 !important;
}

#lleo_dialog #lleo_setTransForm {
	display: block !important;
	margin-top: 3px !important;
	padding-top: 5px !important;
	/* Set position and background because the form might be overlapped by an image when no translations */
	position: relative !important;
	background: #fff !important;
	z-index: 10 !important;
	padding-bottom: 10px !important;
	padding-left: 16px !important;
}

#lleo_dialog .lleo-custom-translation {
	padding: 4px 5px !important;
	border: solid 1px #ddd !important;
	-moz-border-radius: 2px !important;
	-webkit-border-radius: 2px !important;
	border-radius: 2px !important;
	width: 90% !important;
	min-width: 270px !important;
	background: -webkit-gradient(linear, 0 0, 0 20, from(#f1f1f1), to(#fff))
		!important;
	font: normal 13px Arial, Helvetica !important;
	line-height: 15px !important;
}

#lleo_dialog .lleo-custom-translation:hover {
	border: solid 1px #aaa !important;
}

#lleo_dialog .lleo-custom-translation:focus {
	background: #FFFEC9 !important;
}

#lleo_dialog *.hidden {
	display: none !important;
}

#lleo_dialog .infinitive {
	color: #D56E00 !important;
	text-decoration: none;
	border-bottom: 1px dotted #D56E00 !important;
}

#lleo_dialog .infinitive:hover {
	border: none !important;
}

#lleo_dialog #lleo_trans {
	zoom: 1;
	border-top: 1px solid #eeeeee !important;
	margin: 10px 0 0 !important;
	padding: 5px 30px 0 14px !important;
}

#lleo_dialog .lleo_clearfix {
	display: block !important;
	clear: both !important;
	visibility: hidden !important;
	height: 0 !important;
	font-size: 0 !important;
}

#lleo_dialog #lleo_markBlock {
	background: #eeeeee !important;
	cursor: pointer !important;
	border-bottom-left-radius: 3px !important;
	border-bottom-right-radius: 3px !important;
	border-collapse: separate !important;
	border-spacing: 2px !important;
}

#lleo_dialog #lleo_markBlock img {
	width: 14px !important;
	height: 14px !important;
}

#lleo_dialog #lleo_markBlock .icon-cell {
	padding: 5px 2px 5px 16px !important;
	height: 17px !important;
}

#lleo_dialog #lleo_markBlock .wide-cell {
	width: 100% !important;
}

#lleo_dialog #lleo_markBlock .text-cell {
	color: #999999 !important;
	font: normal 13px Arial, Helvetica !important;
	text-shadow: 0 1px #fff !important;
}

#lleo_dialog #lleo_markBlock td {
	vertical-align: middle !important;
	border-collapse: separate !important;
	border-spacing: 2px !important;
}

#lleo_dialog #lleo_picOuter table {
	width: 44px !important;
	position: absolute !important;
	right: 0 !important;
	vertical-align: middle !important;
}

#lleo_dialog #lleo_picOuter td {
	width: 38px !important;
	height: 38px !important;
	border: 1px solid #eeeeee !important;
	vertical-align: middle !important;
	text-align: center !important;
}

#lleo_dialog #lleo_picOuter td div {
	height: 38px !important;
	overflow: hidden !important;
}
</style>
<style type="text/css">

.mainLogo_unsigned {
	position: relative;
	left: 910px;
	width: 110px;
	padding: 0;
	margin: 0;
	z-index: 9;
}
.mainLogo_signed {
	position: relative;
	left: 925px;
	width: 110px;
	padding: 0;
	margin: 0;
	z-index: 9;
}

.ll-content-notification * {
	letter-spacing: normal !important;
	margin: 0 !important;
	padding: 0 !important;
	background: none !important;
	border: 0 !important;
	float: none !important;
	text-align: left !important;
	text-decoration: none !important;
	font: normal 15px 'Lucida Grande', 'Lucida Sans Unicode', Lucida, Arial,
		Helvetica, sans-serif !important;
}

.ll-content-notification {
	vertical-align: baseline !important;
	color: #000 !important;
	overflow: visible !important;
	visibility: visible !important;
	margin: 0 !important;
	padding: 0 !important;
	position: fixed !important;
	background: #fff !important;
	border: solid 1px #AAA !important;
	/*
    left: -999px;
    top: -999px;
	*/
	width: auto;
	/* width: 300px !important; */
	display: block;
	z-index: 999999999 !important;
	-webkit-box-shadow: 0 2px 4px rgba(0, 0, 0, 0.18) !important;
	-moz-box-shadow: 0 2px 4px rgba(0, 0, 0, 0.18) !important;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.18) !important;
	-webkit-border-radius: 3px !important;
	-moz-border-radius: 3px !important;
	border-radius: 3px !important;
	overflow: hidden !important;
	/* opacity: 0 !important; */
	transition: opacity 0.8s !important;
	-moz-transition: opacity 0.8s !important; /* Firefox 4 */
	-webkit-transition: opacity 0.8s !important; /* Safari and Chrome */
	-o-transition: opacity 0.8s !important; /* Opera */
	cursor: default !important;
}

.ll-content-notification-shown {
	opacity: 1 !important;
	transition: opacity 0.8s !important;
	-moz-transition: opacity 0.8s !important; /* Firefox 4 */
	-webkit-transition: opacity 0.8s !important; /* Safari and Chrome */
	-o-transition: opacity 0.8s !important; /* Opera */
}

.ll-content-notification-header {
	border: 0 !important;
	margin: 0 !important;
	background:
		url(data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAAgAAAABCAIAAABsYngUAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAadEVYdFNvZnR3YXJlAFBhaW50Lk5FVCB2My41LjEwMPRyoQAAABJJREFUGFdjePHmCxw9e/UZjgAVYhYtk8xZqAAAAABJRU5ErkJggg==)
		!important;
	border-bottom: solid 1px #CCC !important;
	padding: 1px 4px !important;
	min-height: 18px !important;
	width: 100% !important;
	-webkit-border-top-left-radius: 3px !important;
	-webkit-border-top-right-radius: 3px !important;
	-moz-border-radius-topleft: 3px !important;
	-moz-border-radius-topright: 3px !important;
	border-top-left-radius: 3px !important;
	border-top-right-radius: 3px !important;
	border-collapse: collapse !important;
	border-spacing: 0 !important;
}

.ll-content-notification-header-pic {
	border: 0 !important;
	margin: 0 !important;
	padding: 3px 0 0 3px !important;
	width: 20px !important;
	vertical-align: top !important;
	line-height: 1px !important;
}

.ll-content-notification-header-pic img {
	border: 0 !important;
	padding: 0 !important;
	margin: 0 !important;
	line-height: 1px !important;
}

.ll-content-notification-header-caption {
	font: normal 13px 'Lucida Grande', 'Lucida Sans Unicode', Lucida, Arial,
		Helvetica, sans-serif !important;
	font-weight: bold !important;
	line-height: 15px !important;
	color: #555 !important;
	float: left !important;
	text-shadow: none !important;
	letter-spacing: normal !important;
	white-space: normal !important;
	padding: 3px !important;
	margin: 0 !important;
}

.ll-content-notification-header-close {
	width: 15px !important;
	vertical-align: top !important;
	text-align: right !important;
	padding: 6px 5px 0 0 !important;
	margin: 0 !important;
	line-height: 1px !important;
}

.ll-content-notification-header-close img {
	border: 0 !important;
	width: 7px !important;
	height: 7px !important;
	margin: 0 !important;
	padding: 0 !important;
}

.ll-content-notification-content {
	margin: 0 !important;
	padding: 8px !important;
	float: left !important;
	overflow: hidden !important;
	width: auto !important;
}

.ll-content-notification-content-logo {
	float: left !important;
	height: 48px !important;
	width: 48px !important;
}

.ll-content-notification-content-main {
	margin-left: 60px !important;
	overflow: hidden !important;
	padding: 0 0 2px 0 !important;
	color: #333 !important;
	text-align: left !important;
	text-shadow: none !important;
	letter-spacing: normal !important;
	font: normal 13px 'Lucida Grande', 'Lucida Sans Unicode', Lucida, Arial,
		Helvetica, sans-serif !important;
	line-height: 15px !important;
	width: auto !important;
}

.ll-content-notification-content-header {
	text-align: left !important;
	text-decoration: none !important;
	font: bold 15px 'Lucida Grande', 'Lucida Sans Unicode', Lucida, Arial,
		Helvetica, sans-serif !important;
	line-height: 19px !important;
	margin: 0 0 4px 0 !important;
	padding: 0 !important;
	border: 0 !important;
	color: #333 !important;
	text-shadow: none !important;
	letter-spacing: normal !important;
	display: block !important;
	top: 0 !important;
	left: 0 !important;
}

.ll-content-notification-word {
	color: #d56e00 !important;
	font-weight: bold !important;
	font-size: 14px !important;
}
</style>
</head>
<!-- NAVBAR
================================================== -->
<body>

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
						<a class="navbar-brand" href="#">SORS</a>

					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
						<% if (session.getAttribute("user")!=null) {
								String user = (String) session.getAttribute("user");
								User uObj = UserBase.getInstance().getUser(user);
								if (uObj!=null)
								//	uObj.setLastLogin(new Date());
						
						%>
							<li><a href="BranchList">Processing</a></li>
							<li><a href="Nodes">Nodes</a></li>
							<li><a href="BranchView">View</a></li>
							
							<li style="margin-left: 715px;" class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown"><%= session.getAttribute("user").toString() %> <span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="Profile">Profile</a></li>
									<li><a href="#">1352345</a></li>
									<li><a href="#">Something else here</a></li>
									<li class="divider"></li>
									<li class="dropdown-header">Nav header</li>
									<li><a href="Auth?signout=true">Sign out</a></li>
							
								</ul></li>
							<% } else { %>
							<li class="active"><a href="#">About</a></li>
							<li><a href="#about">How to</a></li>
							<li><a href="#contact">Download</a></li>
							
							
						
							<li style="margin-left: 710px;">
							<!--  	<a data-toggle="modal" data-target="#regModal" style=" padding-right:2px; margin-left:2px; margin-right:2px; float: left;" href="#"> Register </a> -->
							<a  data-target="#regModal" style=" padding-right:2px; margin-left:2px; margin-right:2px; float: left;" href="signup.html"> Register </a> 
								<span style="margin-left:0px; margin-right:0px;margin-top: 15px; float: left;">/</span>
								<a data-toggle="modal" data-target="#signModal" style="padding-left:2px;padding-right:2px; margin-left:2px; margin-right:2px; float:left;" href="#">Sign in</a>
							</li>
							<% } %>
							
						</ul>


					</div>


				</div>

			</nav>
			<% if (session.getAttribute("user")!=null) { %>
			<img class="mainLogo_signed" alt="" src="style/mainLogo.png">
			<% } else { %> 
			<img class="mainLogo_unsigned" alt="" src="style/mainLogo.png"> <% } %>
		</div>
	</div>


	<!-- Carousel
    ================================================== -->
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class=""></li>
			<li data-target="#myCarousel" data-slide-to="1" class=""></li>
			<li data-target="#myCarousel" data-slide-to="2" class="active"></li>
		</ol>
		<div class="carousel-inner" role="listbox">
			<div class="item">
				<img
					src="data:image/gif;base64,R0lGODlhAQABAIAAAFVVVQAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="First slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Example headline.</h1>
						<p>
							Note: If you're viewing this page via a
							<code>file://</code>
							URL, the "next" and "previous" Glyphicon buttons on the left and
							right might not load/display properly due to web browser security
							rules.
						</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Sign
								up today</a>
						</p>
					</div>
				</div>
			</div>
			<div class="item">
				<img
					src="data:image/gif;base64,R0lGODlhAQABAIAAAFVVVQAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Second slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>Another example headline.</h1>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Learn
								more</a>
						</p>
					</div>
				</div>
			</div>
			<div class="item active">
				<img
					src="data:image/gif;base64,R0lGODlhAQABAIAAAFVVVQAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Third slide">
				<div class="container">
					<div class="carousel-caption">
						<h1>One more for good measure.</h1>
						<p>Cras justo odio, dapibus ac facilisis in, egestas eget
							quam. Donec id elit non mi porta gravida at eget metus. Nullam id
							dolor id nibh ultricies vehicula ut id elit.</p>
						<p>
							<a class="btn btn-lg btn-primary" href="#" role="button">Browse
								gallery</a>
						</p>
					</div>
				</div>
			</div>
		</div>
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"></span>
			<span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right"></span> <span
			class="sr-only">Next</span>
		</a>
	</div>
	<!-- /.carousel -->


	<!-- Marketing messaging and featurettes
    ================================================== -->
	<!-- Wrap the rest of the page in another container to center all the content. -->

	<div class="container marketing">

		<!-- Three columns of text below the carousel -->
		<div class="row">
			<div class="col-lg-4">
				<img class="img-circle"
					src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Generic placeholder image"
					style="width: 140px; height: 140px;">
				<h2>Heading	</h2>
				
				<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis
					euismod. Nullam id dolor id nibh ultricies vehicula ut id elit.
					Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
					Praesent commodo cursus magna.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						»</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="img-circle"
					src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Generic placeholder image"
					style="width: 140px; height: 140px;">
				<h2>Heading</h2>
				<p>Duis mollis, est non commodo luctus, nisi erat porttitor
					ligula, eget lacinia odio sem nec elit. Cras mattis consectetur
					purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo,
					tortor mauris condimentum nibh.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						»</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
			<div class="col-lg-4">
				<img class="img-circle"
					src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw=="
					alt="Generic placeholder image"
					style="width: 140px; height: 140px;">
				<h2>Heading</h2>
				<p>
					Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa justo sit amet risus.</p>
				<p>
					<a class="btn btn-default" href="#" role="button">View details
						»</a>
				</p>
			</div>
			<!-- /.col-lg-4 -->
		</div>
		<!-- /.row -->


		<!-- START THE FEATURETTES -->

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					First featurette heading. <span class="text-muted">It'll
						blow your mind.</span>
				</h2>
				<p class="lead">Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>
			</div>
			<div class="col-md-5">
				<img class="featurette-image img-responsive"
					data-src="holder.js/500x500/auto" alt="500x500"
					src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNTAwIiBoZWlnaHQ9IjUwMCIgdmlld0JveD0iMCAwIDUwMCA1MDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iNTAwIiBoZWlnaHQ9IjUwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE5MC4zMjAzMTI1IiB5PSIyNTAiIHN0eWxlPSJmaWxsOiNBQUFBQUE7Zm9udC13ZWlnaHQ6Ym9sZDtmb250LWZhbWlseTpBcmlhbCwgSGVsdmV0aWNhLCBPcGVuIFNhbnMsIHNhbnMtc2VyaWYsIG1vbm9zcGFjZTtmb250LXNpemU6MjNwdDtkb21pbmFudC1iYXNlbGluZTpjZW50cmFsIj41MDB4NTAwPC90ZXh0PjwvZz48L3N2Zz4="
					data-holder-rendered="true">
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-5">
				<img class="featurette-image img-responsive"
					data-src="holder.js/500x500/auto" alt="500x500"
					src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNTAwIiBoZWlnaHQ9IjUwMCIgdmlld0JveD0iMCAwIDUwMCA1MDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iNTAwIiBoZWlnaHQ9IjUwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE5MC4zMjAzMTI1IiB5PSIyNTAiIHN0eWxlPSJmaWxsOiNBQUFBQUE7Zm9udC13ZWlnaHQ6Ym9sZDtmb250LWZhbWlseTpBcmlhbCwgSGVsdmV0aWNhLCBPcGVuIFNhbnMsIHNhbnMtc2VyaWYsIG1vbm9zcGFjZTtmb250LXNpemU6MjNwdDtkb21pbmFudC1iYXNlbGluZTpjZW50cmFsIj41MDB4NTAwPC90ZXh0PjwvZz48L3N2Zz4="
					data-holder-rendered="true">
			</div>
			<div class="col-md-7">
				<h2 class="featurette-heading">
					Oh yeah, it's that good. <span class="text-muted">See for
						yourself.</span>
				</h2>
				<p class="lead">Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>
			</div>
		</div>

		<hr class="featurette-divider">

		<div class="row featurette">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					And lastly, this one. <span class="text-muted">Checkmate.</span>
				</h2>
				<p class="lead">Donec ullamcorper nulla non metus auctor
					fringilla. Vestibulum id ligula porta felis euismod semper.
					Praesent commodo cursus magna, vel scelerisque nisl consectetur.
					Fusce dapibus, tellus ac cursus commodo.</p>
			</div>
			<div class="col-md-5">
				<img class="featurette-image img-responsive"
					data-src="holder.js/500x500/auto" alt="500x500"
					src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNTAwIiBoZWlnaHQ9IjUwMCIgdmlld0JveD0iMCAwIDUwMCA1MDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjxkZWZzLz48cmVjdCB3aWR0aD0iNTAwIiBoZWlnaHQ9IjUwMCIgZmlsbD0iI0VFRUVFRSIvPjxnPjx0ZXh0IHg9IjE5MC4zMjAzMTI1IiB5PSIyNTAiIHN0eWxlPSJmaWxsOiNBQUFBQUE7Zm9udC13ZWlnaHQ6Ym9sZDtmb250LWZhbWlseTpBcmlhbCwgSGVsdmV0aWNhLCBPcGVuIFNhbnMsIHNhbnMtc2VyaWYsIG1vbm9zcGFjZTtmb250LXNpemU6MjNwdDtkb21pbmFudC1iYXNlbGluZTpjZW50cmFsIj41MDB4NTAwPC90ZXh0PjwvZz48L3N2Zz4="
					data-holder-rendered="true">
			</div>
		</div>

		<hr class="featurette-divider">

		<!-- /END THE FEATURETTES -->


		<!-- FOOTER -->
		<footer>
			<p class="pull-right">
				<a href="#">Back to top</a>
			</p>
			<p>
				© 2014 Company, Inc. · <a href="#">Privacy</a> · <a href="#">Terms</a>
			</p>
		</footer>

	</div>
	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<script src="style/js/docs.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="style/js/ie10-viewport-bug-workaround.js"></script>


	<div id="global-zeroclipboard-html-bridge"
		class="global-zeroclipboard-container"
		style="position: absolute; left: 0px; top: -9999px; width: 15px; height: 15px; z-index: 999999999;"
		title="" data-original-title="Copy to clipboard">
		<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
			id="global-zeroclipboard-flash-bridge" width="100%" height="100%">
			<param name="movie"
				value="/assets/flash/ZeroClipboard.swf?noCache=1415017241165">
			<param name="allowScriptAccess" value="sameDomain">
			<param name="scale" value="exactfit">
			<param name="loop" value="false">
			<param name="menu" value="false">
			<param name="quality" value="best">
			<param name="bgcolor" value="#ffffff">
			<param name="wmode" value="transparent">
			<param name="flashvars"
				value="trustedOrigins=getbootstrap.com%2C%2F%2Fgetbootstrap.com%2Chttp%3A%2F%2Fgetbootstrap.com">
			<embed src="/assets/flash/ZeroClipboard.swf?noCache=1415017241165"
				loop="false" menu="false" quality="best" bgcolor="#ffffff"
				width="100%" height="100%" name="global-zeroclipboard-flash-bridge"
				allowscriptaccess="sameDomain" allowfullscreen="false"
				type="application/x-shockwave-flash" wmode="transparent"
				pluginspage="http://www.macromedia.com/go/getflashplayer"
				flashvars="trustedOrigins=getbootstrap.com%2C%2F%2Fgetbootstrap.com%2Chttp%3A%2F%2Fgetbootstrap.com"
				scale="exactfit">
		</object>
	</div>
	<svg xmlns="http://www.w3.org/2000/svg" width="500" height="500"
		viewBox="0 0 500 500" preserveAspectRatio="none"
		style="visibility: hidden; position: absolute; top: -100%; left: -100%;">
		<defs></defs>
		<text x="0" y="23"
			style="font-weight:bold;font-size:23pt;font-family:Arial, Helvetica, Open Sans, sans-serif;dominant-baseline:middle">500x500</text></svg>

<!-- Register modal  -->

<div  class="modal fade" id="regModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div  class="modal-dialog">
    <div style=" left: 100px; width: 400px;" class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Register</h4>
      </div>
      <div class="modal-body">
        
        <div style="margin:10px;" class="input-group">
  			<span class="input-group-addon glyphicon glyphicon-envelope"></span>
  			<input type="text" class="form-control" placeholder="Email">
		</div>
		 <div style="margin:10px;" class="input-group">
  			<span class="input-group-addon glyphicon glyphicon-user"></span>
  			<input type="text" class="form-control" placeholder="Username">
		</div>
		
		 <div style="margin:10px;" class="input-group">
  			<span class="input-group-addon glyphicon glyphicon-lock"></span>
  			<input type="text" class="form-control" placeholder="Password">
		</div>
		
		 <div style="margin:10px;" class="input-group">
  			<span class="input-group-addon glyphicon glyphicon-lock"></span>
  			<input type="text" class="form-control" placeholder="Repeat password">
		</div>
		
		<button style="margin:10px; display:block; width: 350px;" type="button" class="btn btn-success">Register</button>
		
		
		
      
      </div>
      <div class="modal-footer">
       
      </div>
    </div>
  </div>
</div>

<!-- Sign in -->

<div  class="modal fade" id="signModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div  class="modal-dialog">
    <div style=" left: 100px; width: 400px;" class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">Sign in</h4>
      </div>
      <div class="modal-body">
        
        <div style="margin:10px;" class="input-group">
  			<span class="input-group-addon glyphicon glyphicon-envelope"></span>
  			<input id="loginFld" type="text" class="form-control" placeholder="NickName">
		</div>
		
		 <div style="margin:10px;" class="input-group">
  			<span class="input-group-addon glyphicon glyphicon-lock"></span>
  			<input type="password" id="passFld" class="form-control" placeholder="Password">
		</div>
		
		<button style="margin:10px;  display:block; width: 350px;"  onclick="sign_in()" type="button" class="btn btn-success">Sign in</button>
		
      </div>
      <div class="modal-footer">
       
      </div>
    </div>
  </div>
</div>
<form id="hiddenForm" name="hiddenForm" action="Auth" method="post">
 <input  id="user" name="user" type="hidden">
 <input  id="pass" name="pass" type="hidden">
</form>



</body>
</html>