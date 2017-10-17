<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:29
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
    <title>APPOINTMENT</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th>Appointment Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Status</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    int i = 1;
                %>
                <c:forEach items="${apms}" var="apm">
                    <tr>
                        <td><%= i %></td>
                        <td>${apm.appointment_name}</td>
                        <td>${apm.start_date_str}</td>
                        <td>${apm.end_date_str}</td>
                        <td>
                            <c:choose>
                                <c:when test="${apm.status == 1}">
                                    Active
                                </c:when>
                                <c:otherwise>
                                    Inactive
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/appointment/details?appointment_id=${apm.appointment_id}" class="btn btn-primary">Details</a>
                        </td>
                    </tr>
                    <% i = i + 1; %>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>