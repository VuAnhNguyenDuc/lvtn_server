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
</head>
<body>
    <div id="map" style="width: 500px; height: 400px;"></div>
</body>
<script src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyDhlcbvdlgCkj5u5tLUqzeeyx0a3Dp_nlo&"
        type="text/javascript"></script>

<script type="text/javascript">

    jQuery(document).ready(function(){
        renderMap(${coords});
    });

    function getData(){
        $.ajax({
            type:"GET",
            url: "https://lvtn-server.herokuapp.com/ajax/getCoordinates",
            data : "appointmentid=${id}",
            dataType : "text",
            cache : false,
            success: function(result){
                console.log(result);
                var locations = $.parseJSON(result);
                renderMap(locations);
            },
            error: function (xhr) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });
        return temp;
    }

    function renderMap(locations){
        var startLat = locations[0].latitude;
        var startLong = locations[0].longtitude;
        var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 15,
            center: new google.maps.LatLng(startLat, startLong),
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        var infowindow = new google.maps.InfoWindow();

        var marker, i;

        for (i = 0; i < locations.length; i++) {
            marker = new google.maps.Marker({
                position: new google.maps.LatLng(locations[i].latitude, locations[i].longtitude),
                map: map
            });

            google.maps.event.addListener(marker, 'click', (function(marker, i) {
                return function() {
                    infowindow.setContent(locations[i][0]);
                    infowindow.open(map, marker);
                }
            })(marker, i));
        }
    }

    var locations = [
        ['Bondi Beach', -33.890542, 151.274856, 4],
        ['Coogee Beach', -33.923036, 151.259052, 5],
        ['Cronulla Beach', -34.028249, 151.157507, 3],
        ['Manly Beach', -33.80010128657071, 151.28747820854187, 2],
        ['Maroubra Beach', -33.950198, 151.259302, 1]
    ];
</script>
</html>