<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 12/10/2017
  Time: 17:11
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
    <title>EMPLOYEE</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <p><a href="/employee/insert" class="btn btn-primary">ADD NEW EMPLOYEE</a></p>
        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>No</th>
                    <th>Employee Name</th>
                    <th>Email</th>
                    <th>Employee's Manager</th>
                    <th>Status</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    int i = 1;
                %>
                <c:forEach items="${emps}" var="emp">
                    <tr>
                        <td><%= i %></td>
                        <td>${emp.username}</td>
                        <td>${emp.email}</td>
                        <td>${emp.manager_name}</td>
                        <td>
                            <c:choose>
                                <c:when test="${emp.status == 1}">
                                    Active
                                </c:when>
                                <c:otherwise>
                                    Inactive
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/user/details?id=${emp.user_id}&type=employee" class="btn btn-primary">Details</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>