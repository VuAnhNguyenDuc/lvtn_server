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
        <div id="map" style="width: 100%; height: 600px;"></div>
    </div>
</body>

<%--
https://stackoverflow.com/questions/5868850/creating-list-of-objects-in-javascript
https://developers.google.com/maps/documentation/javascript/examples/polyline-simple
--%>

<script type="text/javascript">

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
            zoom: 10,
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