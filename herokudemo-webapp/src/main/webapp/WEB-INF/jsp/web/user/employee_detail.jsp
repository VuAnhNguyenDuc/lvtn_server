<%--
  Created by IntelliJ IDEA.
  User: anh.ndv
  Date: 11/10/2017
  Time: 14:42
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
    <title>EMPLOYEE DETAILS</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <table class="table">
            <tbody>
            <tr>
                <td>Username</td>
                <td>${emp.username}</td>
            </tr>
            <tr>
                <td>Full name</td>
                <td>${emp.full_name}</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${emp.email}</td>
            </tr>
            <tr>
                <td>Manager Name</td>
                <td>${emp.manager_name}</td>
            </tr>
            <tr>
                <td>Total amount of appointment taken</td>
                <td>${total}</td>
            </tr>
            </tbody>
        </table>

        <div class="form-group">
            <label for="select-time">Select period:</label>
            <select class="form-control" id="select-time">
                <option value="month">By months within a year</option>
                <option value="year">By many years</option>
            </select>
        </div>

        <div id="month-div">
            <p>Please input a year</p>
            <input type="number" id="yearInput"/>
        </div>

        <div id="year-div" hidden>
            <p>Please input the start year</p>
            <input type="number" id="from"/>
            <p>Please input the end year</p>
            <input type="number" id="to"/>
        </div>
        <br><br><br>
        <button type="button" class="btn btn-primary" id="chart-btn">View appointment statistic as chart</button>
        <button type="button" class="btn btn-success" id="list-btn">View appointment statistic as list</button>
        <button type="button" class="btn btn-info" id="cost-chart-btn">View cost statistic as chart</button>
        <a href="http://lvtn-server.herokuapp.com/user/infos?id=${id}" target="_blank" class="btn btn-warning">View cost statistic as list</a>

        <div style="height: 20px"></div>

        <div id="result-list">

        </div>

        <div style="height: 20px"></div>
    </div>
</body>
<script type="application/javascript">
    $('#select-time').change(function(){
        var data = $(this).val();
        if(data == 'month'){
            $('#month-div').show();
            $('#year-div').css("display","none");
        } else{
            $('#year-div').show();
            $('#month-div').css("display","none");
        }
    });

    var host = "http://lvtn-server.herokuapp.com/";

    $("#list-btn").click(function(){
        var period = $("#select-time").val();

        if(period == 'month'){
            var yearInput = $("#yearInput").val();
            if(yearInput == ""){
                alert("please input valid year value");
            } else{
                $.ajax({
                    type:"GET",
                    url: "http://lvtn-server.herokuapp.com/ajax/appointment/month",
                    data : "id=${emp.user_id}&isCreated=false&year="+yearInput,
                    dataType : "text",
                    cache : false,
                    success: function(result){
                        var dataArr = $.parseJSON(result);
                        $("#result-list").html("");
                        $("#result-list").html(populateResultList(dataArr));
                        $('#apm-list').DataTable();
                    },
                    error: function (xhr) {
                        var err = eval("(" + xhr.responseText + ")");
                        alert(err.Message);
                    }
                });
            }
        } else{
            var from = $("#from").val();
            var to = $("#to").val();
            if(from != "" && to != ""){
                $.ajax({
                    type:"GET",
                    url: "http://lvtn-server.herokuapp.com/ajax/appointment/year",
                    data : "id=${emp.user_id}&from="+from+"&to="+to+"&isCreated=false",
                    dataType : "text",
                    cache : false,
                    success: function(result){
                        var dataArr = $.parseJSON(result);
                        $("#result-list").html("");
                        $("#result-list").html(populateResultList(dataArr));
                        $('#apm-list').DataTable();
                    },
                    error: function (xhr) {
                        var err = eval("(" + xhr.responseText + ")");
                        alert(err.Message);
                    }
                });
            } else{
                alert("Please input year value");
            }
        }
    });

    $("#chart-btn").click(function(){
        var type = $("#select-appointment").val();
        var period = $("#select-time").val();

        if(period == 'month'){
            var yearInput = $("#yearInput").val();
            if(yearInput != ""){
                var newUrl = host.concat("user/chart/month?id=",${emp.user_id},"&year=",yearInput,"&isCreated=false");

                var newTab = window.open(newUrl);
                if(newTab){
                    newTab.focus();
                } else{
                    alert("Cannot create a new tab");
                }
            } else{
                alert("Please input year value");
            }
        } else if(period == 'year'){
            var from = $("#from").val();
            var to = $("#to").val();
            if(from != "" && to != ""){
                var newUrl = host.concat("user/chart/year?id=",${emp.user_id},"&from=",from,"&to=",to,"&isCreated=false");
                console.log(newUrl);
                var newTab = window.open(newUrl);
                if(newTab){
                    newTab.focus();
                } else{
                    alert("Cannot create a new tab");
                }
            } else{
                alert("Please input year values");
            }
        }
    });

    $("#cost-chart-btn").click(function () {
        var period = $("#select-time").val();

        if(period == 'month'){
            var yearInput = $("#yearInput").val();
            if(yearInput != ""){
                var newUrl = host.concat("user/infos/month?id=",${emp.user_id},"&year=",yearInput);
                var newTab = window.open(newUrl);
                if(newTab){
                    newTab.focus();
                } else{
                    alert("Cannot create a new tab");
                }
            } else{
                alert("Please input year value");
            }
        } else if(period == 'year'){
            var from = $("#from").val();
            var to = $("#to").val();
            if(from != "" && to != ""){
                var newUrl = host.concat("user/infos/year?id=",${emp.user_id},"&from=",from,"&to=",to);
                var newTab = window.open(newUrl);
                if(newTab){
                    newTab.focus();
                } else{
                    alert("Cannot create a new tab");
                }
            } else{
                alert("Please input year values");
            }
        }
    });

    function populateResultList(data){
        var table = "";
        var table_body = "";
        var table_head = "<table class=\"table table-hover\" id=\"apm-list\">\n" +
            "        <thead>\n" +
            "        <tr>\n" +
            "            <th>No</th>\n" +
            "            <th>Appointment Name</th>\n" +
            "            <th>Start Date</th>\n" +
            "            <th>End Date</th>\n" +
            "            <th>Status</th>\n" +
            "        </tr>\n" +
            "        </thead>\n" +
            "        <tbody>";
        for(var i = 0; i < data.length; i++){
            var obj = data[i];
            table_body += "<tr>\n" +
                "                <td>"+(i+1)+"</td>\n" +
                "                <td><a href=\""+host+"appointment/details?appointment_id="+obj.appointment_id+"&snapToRoad=false\">"+obj.appointment_name+"</a></td>\n" +
                "                <td>"+obj.start_date+"</td>\n" +
                "                <td>"+obj.end_date+"</td>\n" +
                "                <td>"+obj.status+"</td>\n" +
                "            </tr>\n";
        }
        var table_end = "</tbody>\n" +
            "    </table>";

        table = table_head + table_body + table_end;
        return table;
    }

    jQuery(document).ready(function($) {
        $(".clickable-row").click(function() {
            window.location = $(this).data("href");
        });
    });

</script>
</html>