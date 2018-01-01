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
    <title>USER INFOS</title>
    <jsp:include page="../header.jsp"/>
</head>
<body>
    <jsp:include page="../mobile_nav.jsp"/>
    <jsp:include page="../side_nav.jsp"/>
    <div class="col-sm-9 col-lg-9 col-sm-12 col-xs-12" style="padding-top: 30px">
        <table class="table">
            <tbody>
            <%--<tr>
                <td>Full name</td>
                <td>${emp.full_name}</td>
            </tr>--%>
            <tr>
                <td>Total amount of money used (thousands vnđ)</td>
                <td id="total_cost"></td>
            </tr>
            <tr>
                <td>Total amount of money estimated (thousands vnđ) </td>
                <td id="estimate_cost_total"></td>
            </tr>
            <tr>
                <td>Warning percentage </td>
                <td id="warning_percentage"></td>
            </tr>
            </tbody>
        </table>

        <table class="table table-hover" id="detail-list">
            <thead>
                <tr>
                    <th>No</th>
                    <th>Appointment Name</th>
                    <th>Vehicle</th>
                    <th>Predicted Vehicle</th>
                    <th>Length</th>
                    <th>Input cost</th>
                    <th>Estimate cost</th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="result-list">

            </tbody>
        </table>

        <div style="height: 20px"></div>
    </div>
</body>
<script type="application/javascript">

    var host =  "http://lvtn-server.herokuapp.com/";

    $(document).ready(function () {
        $.ajax({
            type:"GET",
            url: "http://lvtn-server.herokuapp.com/ajax/user/infos",
            data : "id=${id}",
            dataType : "text",
            cache : false,
            success: function(result){
                var dataArr = $.parseJSON(result);
                var data = dataArr.vehicles;
                console.log(result);
                populateResultList(data);
            },
            error: function (xhr) {
                var err = eval("(" + xhr.responseText + ")");
                alert(err.Message);
            }
        });
        $('#detail-list').DataTable();
    });

    function populateResultList(data){
        var count = 0;
        var total_cost = 0;
        var estimate_cost_total = 0;
        var warning_percentage = 0;
        var warning = 0;
        for(var i = 0; i < data.length; i++) {
            var obj = data[i];
            if(obj.predicted_vehicle != null){
                count++;
                var new_line = document.createElement("tr");
                new_line.innerHTML += "<td>" + count + "</td>\n";
                new_line.innerHTML += "<td>" + obj.appointment_name + "</td>\n";
                new_line.innerHTML += "<td>" + obj.vehicle_name + "</td>\n";
                new_line.innerHTML += "<td>" + obj.predicted_vehicle + "</td>\n";
                new_line.innerHTML += "<td>" + obj.length.toFixed(2) + "</td>\n";
                new_line.innerHTML += "<td>" + obj.input_cost + "</td>\n";
                new_line.innerHTML += "<td>" + obj.estimate_cost.toFixed(2) + "</td>\n";
                if (obj.warning) {
                    new_line.innerHTML += "<td><span style='color:red'>Warning</td>\n";
                    warning++;
                } else{
                    new_line.innerHTML += "<td></td>\n";
                }
                total_cost += obj.input_cost;
                estimate_cost_total += obj.estimate_cost;
                $("#result-list").append(new_line);
            }
        }
        warning_percentage = (warning*100/count);
        $("#total_cost").html(total_cost.toFixed(2));
        $("#estimate_cost_total").html(estimate_cost_total.toFixed(2));
        $("#warning_percentage").html(warning_percentage.toFixed(2) + "%");
    }


</script>
</html>