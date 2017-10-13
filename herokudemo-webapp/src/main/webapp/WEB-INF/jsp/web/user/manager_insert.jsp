<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 13/10/2017
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>ADD NEW MANAGER</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="row" style="height: 15vh;"></div>
        <form:form modelAttribute="managerForm" method="post">

            <div class="form-group>">
                <label for="username">Username: </label>
                <form:input path="username" type="text" class="form-control" id="username"/>
                <form:errors path="username" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="password">Password: </label>
                <form:input path="password" type="text" class="form-control" id="password"/>
                <form:errors path="password" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="email">Email: </label>
                <form:input path="email" type="text" class="form-control" id="email"/>
                <form:errors path="email" cssClass="form-error" class="form-control"/>
            </div>
            <div class="form-group">
                <c:if test="${not empty error}">
                    <label class="form-error">${error}</label>
                </c:if>
            </div>
            <div class="text-center" style="margin-top : 60px; margin-bottom: 60px;">
                <button type="submit" class="btn btn-primary">
                    ADD
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>