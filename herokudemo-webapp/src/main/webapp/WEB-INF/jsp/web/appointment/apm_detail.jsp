<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:30
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
    <title>APPOINTMENT DETAILS</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <p><button type="button" class="btn btn-primary" id="view-map">VIEW MAP</button></p>
        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover">
                <tbody>
                <tr>
                    <td>Appointment Name</td>
                    <td>${apm.name}</td>
                </tr>
                <tr>
                    <td>Manager Created</td>
                    <td>${mng}</td>
                </tr>
                <tr>
                    <td>Start Date</td>
                    <td>${apm.start_date_str}</td>
                </tr>
                <tr>
                    <td>End Date</td>
                    <td>${apm.end_date_str}</td>
                </tr>
                <tr>
                    <td>Users Participate</td>
                    <td>
                        <c:forEach items="${apm.users}" var="usr">
                            <ul>
                                <li>${usr.username}</li>
                            </ul>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Status</td>
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
                    <%--<td>${apm.status}</td>--%>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="table-responsive" style="width: 100%;">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Start Time</th>
                    <th>End Time</th>
                    <th>Start Location</th>
                    <th>End Location</th>
                    <th>Vehicle</th>
                    <th>Created By</th>
                    <th>Total Length (km)</th>
                    <th>Average Velocity (km/h)</th>
                    <th>Input Cost</th>
                    <th>Estimate Cost</th>
                    <th>Image Content</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${dts}" var="dt">
                    <tr>
                        <td>${dt.start_time_str}</td>
                        <td>${dt.end_time_str}</td>
                        <td>${dt.start_location}</td>
                        <td>${dt.end_location}</td>
                        <td>${dt.vehicle_name}</td>
                        <td>${dt.user_created_name}</td>
                        <td>${dt.total_length}</td>
                        <td>${dt.average_velocity}</td>
                        <td>${dt.input_cost}</td>
                        <td>${dt.estimate_cost}</td>
                        <td>Img</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>

<script>
    var host =  "http://lvtn-server.herokuapp.com/";
    $("#view-map").click(function () {
        var newUrl = host.concat("appointment/viewMap?id=",${apm.id});
        console.log(newUrl);
        var newTab = window.open(newUrl);
        if(newTab){
            newTab.focus();
        } else{
            alert("Cannot create a new tab");
        }
    });
</script>
</html>