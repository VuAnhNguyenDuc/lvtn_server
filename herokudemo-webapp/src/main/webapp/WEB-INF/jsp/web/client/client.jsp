<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 25/10/2017
  Time: 14:13
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
    <title>CLIENT</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <p><a href="/client/insert" class="btn btn-primary">ADD NEW CLIENT</a></p>
        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover" id="clients-table">
                <thead>
                <tr>
                    <th>No</th>
                    <th>Client Name</th>
                    <th>Email</th>
                    <th>Phone Number</th>
                    <th>Address</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    int i = 1;
                %>
                <c:forEach items="${cls}" var="cl">
                    <tr>
                        <td><%= i %></td>
                        <td>${cl.name}</td>
                        <td>${cl.email}</td>
                        <td>${cl.phone_number}</td>
                        <td>${cl.address}</td>
                        <td>
                            <a href="/client/update?client_id=${cl.id}" class="btn btn-danger">Update</a>
                        </td>
                    </tr>
                    <% i = i + 1; %>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
<script type="application/javascript">
    $(document).ready(function(){
        $('#clients-table').DataTable();
    });
</script>
</html>