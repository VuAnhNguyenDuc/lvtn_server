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

        var dt_arr = ${details_array};
        if(dt_arr.length > 0){
            var element = dt_arr[0].coords;
            var startLat = element[0].lat;
            var startLong = element[0].lng;
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 3,
                center: {lat : parseFloat(startLat), lng: parseFloat(startLong)},
                mapTypeId: 'terrain'
            });
            /*var flightPlanCoordinates = [
                {lat: 37.772, lng: -122.214},
                {lat: 21.291, lng: -157.821},
                {lat: -18.142, lng: 178.431},
                {lat: -27.467, lng: 153.027}
            ];
            var v1 = [
                {lat: 37.772, lng: -122.214},
                {lat: 21.291, lng: -157.821},
            ];
            var v2 = [
                {lat: 21.291, lng: -157.821},
                {lat: -18.142, lng: 178.431}
            ];
            var v3 = [
                {lat: -18.142, lng: 178.431},
                {lat: -27.467, lng: 153.027}
            ];
            var total = [];
            total.push(v1);
            total.push(v2);
            total.push(v3);*/

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
                strokeColor: '#393'
            };

            for (var i = 0; i < dt_arr.length; i++){
                var obj = dt_arr[i];
                var coords = obj.coords;
                // Draw lines
                var PathStyle = new google.maps.Polyline({
                    path: coords,
                    strokeColor: Colors[i],
                    strokeOpacity: 1.0,
                    strokeWeight: 2,
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
                }, 20);
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