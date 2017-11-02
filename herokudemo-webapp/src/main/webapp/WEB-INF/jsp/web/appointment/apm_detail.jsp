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
        <div class="row">
            <div class="col-sm-6 col-lg-6 col-md-6 col-xs-12">
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
                        <td>Total Cost (thousands vnđ)</td>
                        <td>${apm.total_cost}</td>
                    </tr>
                    <tr>
                        <td>Users Participate</td>
                        <td>
                            <c:forEach items="${apm.users}" var="usr">
                                <ul>
                                    <li>${usr.fullname}</li>
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
                                <c:when test="${apm.status == -1}">
                                    <p style="color:red">Warning</p>
                                </c:when>
                                <c:otherwise>
                                    Finished
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="col-sm-6 col-lg-6 col-md-6 col-xs-12" id="map" style="min-height:400px">
            </div>
        </div>
    </div>
    <div class="container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>No</th>
                <th>Vehicle</th>
                <th>Start Time</th>
                <th>End Time</th>
                <%--<th>Start Location</th>
                <th>End Location</th>--%>
                <th>Total Length (km)</th>
                <th>Average Velocity (km/h)</th>
                <th>Input Cost</th>
                <th>Estimate Cost</th>
                <th>Image Content</th>
            </tr>
            </thead>
            <tbody>
            <%
                int i = 1;
            %>
            <c:forEach items="${dts}" var="dt">
                <tr>
                    <td><%= i %></td>
                    <td><a href="/appointment/details?appointment_id=${apm.id}&detail_id=${dt.id}">${dt.vehicle_name}</a></td>
                    <td>${dt.start_time_str}</td>
                    <td>${dt.end_time_str}</td>
                    <%--<td>${dt.start_location}</td>
                    <td>${dt.end_location}</td>--%>
                    <td>${dt.total_length}</td>
                    <td>${dt.average_velocity}</td>
                    <c:choose>
                        <c:when test="${dt.warning}">
                            <td><p style="color:red">${dt.input_cost}</p></td>
                        </c:when>
                        <c:otherwise>
                            <td>${dt.input_cost}</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${dt.estimate_cost}</td>
                    <td>
                        <a href="#" class="pop">
                            <img src="http://upload.wikimedia.org/wikipedia/commons/2/22/Turkish_Van_Cat.jpg" style="width: 400px; height: 264px;" hidden>
                            Click to view
                        </a>
                    </td>
                </tr>
                <% i = i + 1; %>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="modal fade" id="imagemodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <img src="" class="imagepreview" style="width: 100%;" >
                </div>
            </div>
        </div>
    </div>
</body>

<script>
    /*var host =  "http://lvtn-server.herokuapp.com/";
    $("#view-map").click(function () {
        var newUrl = host.concat("appointment/viewMap?id=",*/<%--${apm.id}--%>/*);
        console.log(newUrl);
        var newTab = window.open(newUrl);
        if(newTab){
            newTab.focus();
        } else{
            alert("Cannot create a new tab");
        }
    });*/
    $(function() {
        $('.pop').on('click', function() {
            $('.imagepreview').attr('src', $(this).find('img').attr('src'));
            $('#imagemodal').modal('show');
        });
    });

    function initMap() {
        /*var coordinates = [
            {lat: 37.772, lng: -122.214},
            {lat: 21.291, lng: -157.821},
            {lat: -18.142, lng: 178.431},
            {lat: -27.467, lng: 153.027}
        ];*/
        var coordinates = ${coords};
        console.log(coordinates);
        var startLat = coordinates[0].lat;
        var startLong = coordinates[0].lng;
        console.log("lat = " + startLat);
        console.log("long = " + startLong);

        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 20,
            center: {lat: startLat, lng: startLong},
            mapTypeId: 'terrain'
        });

        var flightPath = new google.maps.Polyline({
            path: coordinates,
            geodesic: true,
            strokeColor: '#FF0000',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });

        flightPath.setMap(map);
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDhlcbvdlgCkj5u5tLUqzeeyx0a3Dp_nlo&&callback=initMap">
</script>
</html>