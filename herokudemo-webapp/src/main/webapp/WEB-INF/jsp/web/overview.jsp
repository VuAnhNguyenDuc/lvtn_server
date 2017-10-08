<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
<title>Welcome!</title>

<script src="${contextRoot}/resources/js/overview.js"></script>

</head>
<body>
	<header>
		<h1>Welcome to your webapp:</h1>
	</header>
	<section>
		<p>This is a sample application that shows how to run a webapp with Hibernate and Spring MVC on Heroku!</p>
	</section>

	<!-- Form for adding names -->
	<form:form modelAttribute="addNameForm" >
		<form:errors id="errors" path="*" />
		<fieldset>
			<legend>Enter name:</legend>
			<form:input id="nameInput" path="name" autofocus="true" required="true" />
			<button type="submit" name="addName">Add name</button>
		</fieldset>
	</form:form>	

	<h3>Names stored in database:</h3>
	<ul>
		<!-- List the 15 first names in database -->
		<c:forEach items="${names}" var="item" end="15">
			<li>
				<c:out value="${item.name}" />
			</li>
		</c:forEach>
	</ul>
	
	<!-- Form that contains a delete all button if 5 items or more -->
	<c:if test="${fn:length(names) > 4}">
		<form:form>
			<fieldset>
				<button type="submit" name="deleteAll">Delete all</button>
			</fieldset>
		</form:form>
	</c:if>
</body>
</html>