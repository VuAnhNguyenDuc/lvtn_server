<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 10:16
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
    <title>Login Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <style type="text/css">
        .form-error{
            color:red !important;
            font-weight: 700 !important;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row" style="height: 15vh;"></div>
    <h2 class="text-center">LVTN SERVER</h2>
    <form:form modelAttribute="loginForm" action="/login" method="post">

        <div class="form-group>">
            <label for="username">Username: </label>
            <form:input type="text" class="form-control" id="username" placeholder="Username...." path="username"/>
            <form:errors class="form-control" path="username" cssClass="form-error"/>
        </div>

        <div class="form-group>">
            <label for="password">Password: </label>
            <form:input type="password" class="form-control" id="password" placeholder="Password...." path="password"/>
            <form:errors class="form-control" path="password" cssClass="form-error"/>
        </div>

        <div class="form-group">
            <c:if test="${not empty error}">
                <label class="form-error">${result}</label>
            </c:if>
        </div>

        <div class="text-center" style="margin-top: 60px;">
            <button type="submit" class="btn btn-primary text-center">LOGIN</button>
        </div>

    </form:form>
</div>
</body>
<script type="application/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="application/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>