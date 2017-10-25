<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 25/10/2017
  Time: 14:19
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
    <title>UPDATE CLIENT</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="row" style="height: 15vh;"></div>
        <form:form modelAttribute="client" method="post">

            <div class="form-group>">
                <label for="name">Client name: </label>
                <form:input path="name" type="text" class="form-control" id="name" value="${client.name}"/>
                <form:errors path="name" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="email">Email: </label>
                <form:input path="email" type="text" class="form-control" id="email" value="${client.email}"/>
                <form:errors path="email" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="phone_number">Phone number: </label>
                <form:input path="phone_number" type="text" class="form-control" id="phone_number" value="${client.phone_number}"/>
                <form:errors path="phone_number" cssClass="form-error" class="form-control"/>
            </div>

            <div class="form-group>">
                <label for="address">Address: </label>
                <form:input path="address" type="text" class="form-control" id="address" value="${client.address}"/>
                <form:errors path="address" cssClass="form-error" class="form-control"/>
            </div>
            <div class="form-group">
                <c:if test="${not empty error}">
                    <label class="form-error">${error}</label>
                </c:if>
            </div>
            <div class="text-center" style="margin-top : 60px; margin-bottom: 60px;">
                <button type="submit" class="btn btn-primary">
                    UPDATE
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>