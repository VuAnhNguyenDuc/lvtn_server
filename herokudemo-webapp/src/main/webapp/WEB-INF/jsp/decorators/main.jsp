<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js ie6" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en">
<!--<![endif]-->

<c:set value="${pageContext.request.contextPath}" var="contextRoot" />

<head>

<title>Webapp Demo - <decorator:title /></title>
<style type="text/css">
@import "${contextRoot}/resources/css/global.css";

@import
	"${contextRoot}/resources/css/cupertino/jquery-ui-1.8.21.custom.css";

@import "${contextRoot}/resources/css/andreas.css";
</style>

<!-- JQuery -->
<script src="${contextRoot}/resources/js/jquery-1.7.2.min.js"></script>
<script src="${contextRoot}/resources/js/contest.js"></script>
<script src="${contextRoot}/resources/js/jquery-ui-1.8.21.custom.min.js"></script>

<script src="${contextRoot}/resources/js/main.js"></script>
<script src="${contextRoot}/resources/js/jquery-ui-timepicker-addon.js"></script>

<decorator:head />
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="description" content="" />

</head>

<body>
	<section id="site">
		<header id="siteBanner">
			<a class="top-banner" href="/"><h1>Heroku webapp demo</h1></a>
		</header>
		<nav id="siteNavigation">
			<ul>
			<!-- To get the menu item to show as active, add class when the appropriate pages are loaded.  -->
				<a href="/web/overview" id="overviewMenuItem"><li>Main</li></a>
				<a href="#" id="infoMenuItem"><li>Info</li></a>
			</ul>
		</nav>
		<section id="siteMain">
			<decorator:body />
		</section>
		<footer id="siteFooter"> </footer>
	</section>

</body>
</html>