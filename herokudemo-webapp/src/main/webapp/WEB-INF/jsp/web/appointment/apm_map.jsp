<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 13/10/2017
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    int apm_id = Integer.parseInt(request.getParameter("appointment_id"));
    boolean snapToRoad = Boolean.parseBoolean(request.getParameter("snapToRoad"));
%>
<html>
<head>
    <title>MAP TRACKER</title>
    <jsp:include page="../header.jsp"/>
</head>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <div class="row form-group">
            <label for="select-coord-type">Select coordinates type:</label>
            <select class="form-control" id="select-coord-type">
                <option value="no" <% if(!snapToRoad){ %>selected<% } %>>Original Coordinates</option>
                <option value="yes" <% if(snapToRoad){ %>selected<% } %>>Beautified Coordinates</option>
            </select>
        </div>

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
                        <td>Expected Start Date</td>
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
        <table class="table table-hover" id="details-table">
            <thead>
            <tr>
                <th>No</th>
                <th>Vehicle</th>
                <th>Predicted Vehicle</th>
                <th>Start Time</th>
                <th>End Time</th>
                <%--<th>Start Location</th>
                <th>End Location</th>--%>
                <th>Total Length (km)</th>
                <th>Average Velocity (km/h)</th>
                <th>Input Cost</th>
                <th>Estimate Cost</th>
                <th>Billing Image</th>
            </tr>
            </thead>
            <tbody>
            <%
                int i = 1;
            %>
            <c:forEach items="${dts}" var="dt">
                <tr>
                    <td><%= i %></td>
                    <td>${dt.vehicle_name}</td>
                    <td>${dt.predicted_vehicle}</td>
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

<%--
https://stackoverflow.com/questions/5868850/creating-list-of-objects-in-javascript
https://developers.google.com/maps/documentation/javascript/examples/polyline-simple
--%>


<script type="text/javascript">
    $('#select-coord-type').change(function(){
        var data = $(this).val();
        if(data == 'no'){
            window.location.replace("http://lvtn-server.herokuapp.com/appointment/details?appointment_id=<%= apm_id %>&snapToRoad=false");
        } else{
            window.location.replace("http://lvtn-server.herokuapp.com/appointment/details?appointment_id=<%= apm_id %>&snapToRoad=true");
        }
    });

    $(function() {
        $('.pop').on('click', function() {
            $('.imagepreview').attr('src', $(this).find('img').attr('src'));
            $('#imagemodal').modal('show');
        });
    });

    $(document).ready(function(){
        $('#details-table').DataTable();
    });

    function initMap() {

        var dt_arr = ${details_array};
        if(dt_arr.length > 0){
            var element = dt_arr[0].coords;
            var startLat = element[0].lat;
            var startLong = element[0].lng;
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 15,
                center: {lat : parseFloat(startLat), lng: parseFloat(startLong)},
                mapTypeId: 'terrain'
            });

            var Colors = [
                "#FF0000",
                "#00FF00",
                "#0000FF",
                "#FFFFFF",
                "#000000",
                "#FFFF00",
                "#00FFFF",
                "#FF00FF",

                "#FF0000",
                "#00FF00",
                "#0000FF",
                "#FFFFFF",
                "#000000",
                "#FFFF00",
                "#00FFFF",
                "#FF00FF"
            ];

            // Define the symbol, using one of the predefined paths ('CIRCLE')
            // supplied by the Google Maps JavaScript API.
            var lineSymbol = {
                path: google.maps.SymbolPath.CIRCLE,
                scale: 8,
                strokeColor: '#393', //Colors[i],
            };

            for (var i = 0; i < dt_arr.length; i++){

                var obj = dt_arr[i];
                var coords = obj.coords;
                // Draw lines
                var PathStyle = new google.maps.Polyline({
                    path: coords,
                    strokeColor: Colors[i],
                    strokeOpacity: 1.0,
                    strokeWeight: 6, // 2
                    icons: [{
                        icon: lineSymbol,
                        offset: '100%'
                    }],
                    map: map
                });
                animateCircle(PathStyle);

                // Draw markers
                var vhc_name = obj.vehicle_name;
                var start_time = obj.start_time;
                var end_time = obj.end_time;
                var avg_velocity = obj.avg_velocity + " km/h";
                var content =
                "Vehicle Name : " +  vhc_name +                         "<br>Start time : " + start_time +
                    "<br>End time : " + end_time +                          "<br>Average Velocity : " + avg_velocity;

                var marker = new google.maps.Marker({
                    position: coords[0],
                    map: map,
                    html: content
                });
                google.maps.event.addListener(marker, "click", function () {
                    infowindow.setContent(this.html);
                    infowindow.open(map, this);
                });
            }

            infowindow = new google.maps.InfoWindow({
                content: "loading..."
            });

            // Use the DOM setInterval() function to change the offset of the symbol
            // at fixed intervals.
            function animateCircle(line) {
                var count = 0;
                window.setInterval(function() {
                    count = (count + 1) % 200;

                    var icons = line.get('icons');
                    icons[0].offset = (count / 2) + '%';
                    line.set('icons', icons);
                }, 40); //20
            }
        } else{
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 3,
                center: {lat: 0, lng: -180},
                mapTypeId: 'terrain'
            });
        }
    }
</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDhlcbvdlgCkj5u5tLUqzeeyx0a3Dp_nlo&&callback=initMap">
</script>
</html>