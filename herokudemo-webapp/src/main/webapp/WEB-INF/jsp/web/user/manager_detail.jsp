<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:42
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
    <title>MANAGER DETAILS</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <table class="table">
            <%--<thead>
            <tr>
                <th>Thuộc tính</th>
                <th>Giá trị</th>
            </tr>
            </thead>--%>
            <tbody>
            <tr>
                <td>Username</td>
                <td>${mng.username}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${mng.email}</td>
            </tr>
            <tr>
                <td>Total amount of appointment taken</td>
                <td>${total}</td>
            </tr>
            </tbody>
        </table>

        <p>Amount of appointment taken compare by months in a year : </p>
        <p>Please input a year</p>
        <input type="number" id="yearInput" />
        <button type="button" class="btn btn-primary" id="month-list">View as chart</button>
        <button type="button" class="btn btn-success" id="month-chart">View as list</button>

        <p>Amount of appointment taken throughout a period : </p>
        <p>Please input the start year</p>
        <input type="number" id="from" />
        <p>Please input the end year</p>
        <input type="number" id="to" />
        <button type="button" class="btn btn-primary" id="year-list">View as chart</button>
        <button type="button" class="btn btn-success" id="year-chart">View as list</button>


        <div id="result-list">

        </div>
    </div>
</body>
</html>