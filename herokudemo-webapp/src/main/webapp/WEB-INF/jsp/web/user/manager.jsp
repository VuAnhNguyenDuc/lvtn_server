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
    <title>MANAGER</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <p><a href="/manager/insert" class="btn btn-primary">ADD NEW MANAGER</a></p>
        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover" id="managers-table">
                <thead>
                <tr>
                    <th>No</th>
                    <th>Manager Name</th>
                    <th>Email</th>
                    <th>Employees Managed</th>
                    <th>Status</th>
                    <th></th>
                    <%--<th></th>--%>
                </tr>
                </thead>
                <tbody>
                <%
                    int i = 1;
                %>
                <c:forEach items="${mngs}" var="mng">
                    <tr>
                        <td><%= i %></td>
                        <td>${mng.full_name}</td>
                        <td>${mng.email}</td>
                        <td>
                            <c:forEach items="${mng.employees}" var="emp">
                                <ul>
                                    <li>${emp.full_name}</li>
                                </ul>
                            </c:forEach>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${mng.status == 1}">
                                    Active
                                </c:when>
                                <c:otherwise>
                                    Inactive
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/user/details?id=${mng.user_id}&type=manager" class="btn btn-primary">Details</a>
                            <a href="/manager/update?id=${mng.user_id}" class="btn btn-danger">Update</a>
                        </td>
                        <%--<td>

                        </td>--%>
                    </tr>
                    <%
                        i = i + 1;
                    %>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
<script type="application/javascript">
    $(document).ready(function(){
        $('#managers-table').DataTable();
    });
</script>
</html>