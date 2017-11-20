<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 20/11/2017
  Time: 14:32
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
    <title>SPECIAL PLACES</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <p><a href="/specialPlace/insert" class="btn btn-primary">ADD NEW PLACE</a></p>
        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover" id="special-places-table">
                <thead>
                <tr>
                    <th>No</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Range (in meters)</th>
                    <th>Latitude</th>
                    <th>Longitude</th>
                    <th>Status</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <%
                    int i = 1;
                %>
                <c:forEach items="${sps}" var="sp">
                    <tr>
                        <td><%= i %></td>
                        <td>${sp.name}</td>
                        <td>${sp.type}</td>
                        <td>${sp.range}</td>
                        <td>${sp.latitude}</td>
                        <td>${sp.longitude}</td>
                        <td>
                            <c:choose>
                                <c:when test="${sp.status == 1}">
                                    Active
                                </c:when>
                                <c:otherwise>
                                    Inactive
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/specialPlace/update?id=${sp.id}" class="btn btn-danger">Update</a>
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
        $('#special-places-table').DataTable();
    });
</script>
</html>